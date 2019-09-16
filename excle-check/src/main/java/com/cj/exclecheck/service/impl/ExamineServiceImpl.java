package com.cj.exclecheck.service.impl;

import com.cj.common.entity.LogCheck;
import com.cj.common.entity.Station;
import com.cj.common.entity.TableRecord;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.MemoryData;
import com.cj.common.utils.excle.ImportExeclUtil;
import com.cj.core.domain.Pager;
import com.cj.exclecheck.entity.RespExamineResult;
import com.cj.exclecheck.entity.RespTableInfo;
import com.cj.exclecheck.entity.RespUnaudited;
import com.cj.exclecheck.entity.TaskAreaAndStation;
import com.cj.exclecheck.mapper.ExamineMapper;
import com.cj.exclecheck.service.ExamineService;
import com.cj.exclecheck.util.ExamineUtil;
import com.cj.exclepublic.domain.FindOrganizationResp;
import com.cj.exclepublic.service.ExcleService;
import com.cj.exclepublic.service.PowerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 审批流程模块
 * Created by XD on 2018/10/23.
 */
@Service
@Transactional
public class ExamineServiceImpl implements ExamineService {

    @Autowired(required = false)
    private ExamineMapper examineMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PowerService powerService;

    @Autowired
    private ExcleService excleService;


    /**
     * 提交审核
     * 检查场站的表填完没有
     *
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult submit(Map map, HttpServletRequest request) {
        ApiResult apiResult;
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级

        String year = (String) map.get("year");//获取前端传入年份
        Integer stationId = (Integer) map.get("stationId");//获取前端传入场站id

        FindOrganizationResp findOrganizationResp = powerService.findOrganization(Long.valueOf(stationId));

        String local = request.getLocalAddr(); //取得服务器IP
        int prot = request.getLocalPort(); //取得服务器端口
        String token = MemoryData.getTokenMap().get(adminId.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("token",token);
        String url = "http://"+local + ":" + prot + "/api/v1/log/checklog/logs";
        String year2 = ImportExeclUtil.DateUtil.dateToStr(ImportExeclUtil.DateUtil.strToDate(year, ImportExeclUtil.DateUtil.YYYY), ImportExeclUtil.DateUtil.YYYY_MM_DDHHMMSS);



        if (stationId != null && "场站级".equals(roleDescName)){//场站级 提交  检查这个场站的表有没有提交完成
            //根据场站id 查询作业区id 和 气矿id
            Map map2 = this.examineMapper.findTaskAreaAndFactoryId(stationId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId(findOrganizationResp.getFactoryId());
            logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
            logCheck.setStationId(findOrganizationResp.getStationId());
            logCheck.setDate(year);

            logCheck.setOperatorId(adminId);//设置审核人id

            //检查这个场站的基本信息表  状态是否为 1,21
            TableRecord tableRecord = this.examineMapper.findTableRecord(year,stationId);
            if (tableRecord==null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("提交失败,基本信息统计表未填写");
                //手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return apiResult;
            }else {
                if("11".equals(tableRecord.getCheckType())){
                    apiResult = ApiResult.SUCCESS();
                    apiResult.setMsg(year+"年表格，已审核通过");
                    return apiResult;
                }
                if (!"1".equals(tableRecord.getCheckType()) && !"21".equals(tableRecord.getCheckType())){
                    //1-场站填写未提交到作业区（场站可编辑）3-作业区审核未通过被驳回（场站可编辑）
                    apiResult = ApiResult.SUCCESS();
                    apiResult.setMsg(year+"年表格，已提交至作业区");
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }
            }

            //根据场站id 查询工艺分类名称
            String factoryTypeName = this.examineMapper.findFactoryTypeName(stationId);
            //需要填写的表集合
            List<String> tabaleList = ExamineUtil.addTableByStation(factoryTypeName);
            //根据场站id和年份查询已经填过的表
            List<String> tableNames = this.examineMapper.findTableNameList(stationId, year);
            //筛选出 未填写的表
            String s = ExamineUtil.getTableName(tabaleList, tableNames);
            if (s!=null && !"".equals(s)){
                //表未填完
                apiResult = ApiResult.FAIL();
                apiResult.setMsg(
                        "还有未填写的表：" + s);
                //手动回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return apiResult;
            }
            //表已经填写完成  修改状态为   2-场站已提交到作业区A未审核（场站不可编辑，作业区可编辑）
            //根据年份和场站id  修改审核状态 为 2
            int i = this.examineMapper.updateTableRecordCheckType(stationId,year);
            if (i > 0){
                logCheck.setCheckMsg("已提交");
                logCheck.setRoleGradeName("场站级");

                excleService.addLogCheck(logCheck,request);

                apiResult = ApiResult.SUCCESS();
                apiResult.setMsg(findOrganizationResp.getFactoryName()+findOrganizationResp.getTaskAreaName()+findOrganizationResp.getStationName()+"("+findOrganizationResp.getFactoryTypeName()+") "+
                        year+"年表格，提交成功");
                return apiResult;
            }else {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("成功条数：0");
                return apiResult;
            }

        }

        if ("作业区级A".equals(roleDescName)){
            //根据adminId查询管理的作业区id
            Integer taskAreaId = this.examineMapper.findTaskAreaIdByAdminId(adminId);
            if (taskAreaId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("未查询到账号绑定的作业区");
                return apiResult;
            }
            //查询作业区id和气矿id
//            Map map2 = this.examineMapper.findFactoryId(taskAreaId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId(findOrganizationResp.getFactoryId());
            logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
            logCheck.setDate(year);//设置年份
            logCheck.setOperatorId(adminId);//设置审核人id

            //查询所管辖的作业区下所有的场站id和场站名称
            List<Station> stationList = this.examineMapper.findStationList(adminId);
            if (stationList==null || stationList.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("未查询到账号所关联的作业区或场站");
                return apiResult;
            }
            for (Station station:stationList){
                //获取场站id
                Integer id = station.getStationId().intValue();

                //根据场站id 查询附属信息
                Map stationInfo = examineMapper.findStationById(Long.valueOf(id));

                //检查这个场站的基础信息表 状态是否为 3  这里只校验一张表，因为场站下的表 状态都是批量修改
                TableRecord tableRecord = this.examineMapper.findTableRecord(year,id);
                if (tableRecord==null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg(stationInfo.get("factory_name") + " " + stationInfo.get("task_area_name") + " " + station.getStationName() + " 提交失败，请检查所属场站是否完成提交");
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }else {
                    if ("4".equals(tableRecord.getCheckType()) || "6".equals(tableRecord.getCheckType()) || "8".equals(tableRecord.getCheckType()) || "10".equals(tableRecord.getCheckType())){
                        apiResult = ApiResult.SUCCESS();
                        apiResult.setMsg("已提交，请检查提交结果。");
                        return apiResult;
                    }else if("11".equals(tableRecord.getCheckType())){
                        apiResult = ApiResult.SUCCESS();
                        apiResult.setMsg("已提交并审核通过。");
                        return apiResult;
                    }else if (!"3".equals(tableRecord.getCheckType())){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("表格是否提交至作业区，且审核通过");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                }
                //根据场站id 查询工艺分类名称
                String factoryTypeName = this.examineMapper.findFactoryTypeName(id);
                //需要填写的表集合
                List<String> tabaleList = ExamineUtil.addTableByTaskArea(factoryTypeName);
                //根据场站id和年份查询已经填过的表 不筛选type
                List<String> tableNames = this.examineMapper.findTableNameListNotType(id, year);
                //筛选出 未填写的表
                String s = ExamineUtil.getTableName(tabaleList, tableNames);
                s = s==null?"":s;
                //判断 台账是否填写 2选一   C值 是否填写 3选1
                List<String> s1 = this.examineMapper.findTableName(id,year,"环境风险台账%");
                if (s1==null || s1.size()==0 || s1.get(0)==null || "".equals(s1.get(0))){
                    s += " 环境风险台账（作业区填写）、";
                }
                if ("含油水管线|输水管线|含硫化氢管线".indexOf(factoryTypeName) != -1){
                    List<String> s2 = this.examineMapper.findTableName(id,year,"C值%");
                    if (s2==null || s2.size() == 0 || s1.get(0)==null || "".equals(s1.get(0))){
                        s += " C值（作业区填写）、";
                    }
                }

                // 环境风险是否填写 今年和上年
                Integer i = this.examineMapper.findEnvironmental(id,year,"2");//查询 风险防控本年度表
                if (i==0){
                    s += " 风险防控本年度表（作业区填写）、";
                }
                int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                Integer i1 = this.examineMapper.findEnvironmental(id, String.valueOf(lastYear),"3");//查询 风险防控上年度表
                if (i1==0){
                    s += " 风险防控上年度表（作业区填写）";
                }

                if (s!=""){
                    //表未填完
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("还有未填写的表：" + s);
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }
                //表已经填写完成  修改状态为  4-作业区已提交（已审核通过）气矿未审核（作业区不可编辑，气矿可编辑）， 3 ->4
                //根据年份和场站id  修改审核状态 为 4
                int j = this.examineMapper.updateTableRecordCheckTypeSex(id,year);
                if (j==0){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("审核状态异常");
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }
                //风险防控上年度表 修改审核状态 为 4
                int k = this.examineMapper.updateLasetYearTable(id,String.valueOf(lastYear));
                if (k==0){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("上年度风险防控表审核状态异常");
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }
            }
            //新增日志
            logCheck.setCheckMsg("已提交");
            logCheck.setRoleGradeName("作业区级A");

            excleService.addLogCheck(logCheck,request);

            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("提交成功");
            return apiResult;
        }

        if ("作业区级B".equals(roleDescName)){
            //根据adminId查询管理的作业区id
            Integer taskAreaId = this.examineMapper.findTaskAreaIdByAdminId(adminId);
            if (taskAreaId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("未查询到账号绑定的作业区");
                return apiResult;
            }
            //查询作业区id和气矿id
//            Map map2 = this.examineMapper.findFactoryId(taskAreaId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId(findOrganizationResp.getFactoryId());
            logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
            logCheck.setDate(year);
            logCheck.setOperatorId(adminId);//设置审核人id

            //查询所管辖的作业区下所有的场站id和场站名称
            List<Station> stationList = this.examineMapper.findStationList(adminId);
            if (stationList==null || stationList.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("未查询到账号所关联的作业区或场站");
                return apiResult;
            }
            for (Station station:stationList){
                //获取场站id
                Integer id = station.getStationId().intValue();

                //根据场站id 查询附属信息
                Map stationInfo = examineMapper.findStationById(Long.valueOf(id));

                //检查这个场站的基础信息表 状态是否为 5  这里只校验一张表，因为场站下的表 状态都是批量修改 5 -> 6
                TableRecord tableRecord = this.examineMapper.findTableRecord(year,id);
                if (tableRecord==null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("提交失败，请检查所辖场站是否都已提交表格");
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }else {
                    if ("6".equals(tableRecord.getCheckType()) || "8".equals(tableRecord.getCheckType()) || "10".equals(tableRecord.getCheckType())){
                        apiResult = ApiResult.SUCCESS();
                        apiResult.setMsg("已提交，请检查提交结果。");
                        return apiResult;
                    }else if("11".equals(tableRecord.getCheckType())){
                        apiResult = ApiResult.SUCCESS();
                        apiResult.setMsg("已提交并审核通过。");
                        return apiResult;
                    }else if (!"5".equals(tableRecord.getCheckType())){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("表格是否提交至作业区，且审核通过");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                }

                int lastYear = Integer.valueOf(year) - 1;//上一年 年份


                //根据年份和场站id  修改审核状态 为 5 -> 6
                int j = this.examineMapper.updateTableRecordCheckTypeSexB(id,year);
                if (j==0){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("审核状态异常");
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }
                //风险防控上年度表 修改审核状态 为 5 -> 6
                int k = this.examineMapper.updateLasetYearTableB(id,String.valueOf(lastYear));
                if (k==0){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("上年度风险防控表审核状态异常");
                    //手动回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return apiResult;
                }
            }
            //新增日志
            logCheck.setCheckMsg("已提交");
            logCheck.setRoleGradeName("作业区级B");
            excleService.addLogCheck(logCheck,request);


            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("提交成功");
            return apiResult;
        }





        if ("气矿级A".equals(roleDescName)){
            //查询所管辖下所有的作业区id和名称 及 下属场站id和场站名称
            List<TaskAreaAndStation> list = this.examineMapper.findTaskAreaAndStation(adminId);
            if (list==null || list.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("未查询到账号所关联的气矿或作业区");
                return apiResult;
            }
            //遍历作业区
            for (TaskAreaAndStation taskAreaAndStation:list){
                if (taskAreaAndStation != null){
                    Long taskAreaId = taskAreaAndStation.getTaskAreaId();//作业区id
                    String taskAreaName = taskAreaAndStation.getTaskAreaName();//作业区名称
                    for (Station station:taskAreaAndStation.getStationList()){
                        if (station != null){
                            Long id = station.getStationId();//场站id

                            //根据场站id 查询附属信息
                            Map stationInfo = examineMapper.findStationById(id);

                            //检查这个场站的表 状态是否为 7  这里只校验一张表，因为场站下的表 状态都是批量修改
                            TableRecord tableRecord = this.examineMapper.findTableRecord(year,id.intValue());
                            if (tableRecord==null){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("提交失败，请检查" +taskAreaName+ "是否已提交表格");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }else {
                                if ("8".equals(tableRecord.getCheckType()) || "10".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.FAIL();
                                    apiResult.setMsg("已提交，请检查提交结果。");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                                if ("11".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.SUCCESS();
                                    apiResult.setMsg("已提交并审核通过。");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                                if (!"7".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.FAIL();
                                    apiResult.setMsg("请检查" +taskAreaName+ "是否已提交表格");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                            }
                            int lastYear = Integer.valueOf(year) - 1;//上一年 年份

                            //根据年份和场站id  7 的修改审核状态 为 8
                            int j = this.examineMapper.updateTableRecordCheckTypeSexQA(id,year,"7","56451","8");
                            if (j==0){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("审核状态异常");
                                //手动回滚事务
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }
                            //风险防控上年度表  修改审核状态 7 为 8
                            int k = this.examineMapper.updateLasetYearTableQA(id,String.valueOf(lastYear),"7","56451","8");
                            if (k==0){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("上年度风险防控表审核状态异常");
                                //手动回滚事务
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }
                        }
                    }
                }
            }

            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId(findOrganizationResp.getFactoryId());
            //logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
            logCheck.setDate(year);//设置年份
            logCheck.setOperatorId(adminId);//设置审核人id

            //新增日志
            logCheck.setCheckMsg("已提交");
            logCheck.setRoleGradeName("气矿级A");
            excleService.addLogCheck(logCheck,request);

            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("提交成功");
            return apiResult;

        }







        if ("气矿级B".equals(roleDescName)){
            //查询所管辖下所有的作业区id和名称 及 下属场站id和场站名称
            List<TaskAreaAndStation> list = this.examineMapper.findTaskAreaAndStation(adminId);
            if (list==null || list.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("未查询到账号所关联的气矿或作业区");
                return apiResult;
            }
            //遍历作业区
            for (TaskAreaAndStation taskAreaAndStation:list){
                if (taskAreaAndStation != null){
                    Long taskAreaId = taskAreaAndStation.getTaskAreaId();//作业区id
                    String taskAreaName = taskAreaAndStation.getTaskAreaName();//作业区名称
                    for (Station station:taskAreaAndStation.getStationList()){
                        if (station != null){
                            Long id = station.getStationId();//场站id

                            //根据场站id 查询附属信息
                            Map stationInfo = examineMapper.findStationById(id);

                            //检查这个场站的表 状态是否为 9  这里只校验一张表，因为场站下的表 状态都是批量修改
                            TableRecord tableRecord = this.examineMapper.findTableRecord(year,id.intValue());
                            if (tableRecord==null){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("提交失败，请检查" +taskAreaName+ "是否已经完成提交");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }else {
                                if ("10".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.FAIL();
                                    apiResult.setMsg("已提交，请检查提交结果。");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                                if ("11".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.SUCCESS();
                                    apiResult.setMsg("已提交并审核通过。");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                                if (!"9".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.FAIL();
                                    apiResult.setMsg("不可提交，请检查" +taskAreaName+ "是否已提交表格");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                            }
                            int lastYear = Integer.valueOf(year) - 1;//上一年 年份

                            //根据年份和场站id 9 的修改审核状态 为 10
                            int j = this.examineMapper.updateTableRecordCheckTypeSexQA(id,year,"9","53413","10");
                            if (j==0){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("审核状态异常");
                                //手动回滚事务
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }
                            //风险防控上年度表 修改审核状态 9 为 10
                            int k = this.examineMapper.updateLasetYearTableQA(id,String.valueOf(lastYear),"9","53413","10");
                            if (k==0){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("上年度风险防控表审核状态异常");
                                //手动回滚事务
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }
                        }
                    }
                }
            }

            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId(findOrganizationResp.getFactoryId());
            //logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
            logCheck.setDate(year);//设置年份
            logCheck.setOperatorId(adminId);//设置审核人id

            //新增日志
            logCheck.setCheckMsg("已提交");
            logCheck.setRoleGradeName("气矿级B");

            excleService.addLogCheck(logCheck,request);

            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("提交成功");
            return apiResult;

        }




        //气矿C只通过
       /* if ("气矿级C".equals(roleDescName)){
            //查询所管辖下所有的作业区id和名称 及 下属场站id和场站名称
            List<TaskAreaAndStation> list = this.examineMapper.findTaskAreaAndStation(adminId);
            if (list==null || list.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("未查询到账号所关联的气矿或作业区");
                return apiResult;
            }
            //遍历作业区
            for (TaskAreaAndStation taskAreaAndStation:list){
                if (taskAreaAndStation != null){
                    Long taskAreaId = taskAreaAndStation.getTaskAreaId();//作业区id
                    String taskAreaName = taskAreaAndStation.getTaskAreaName();//作业区名称
                    for (Station station:taskAreaAndStation.getStationList()){
                        if (station != null){
                            Long id = station.getStationId();//场站id

                            //根据场站id 查询附属信息
                            Map stationInfo = examineMapper.findStationById(id);

                            //检查这个场站的表 状态是否为 10  这里只校验一张表，因为场站下的表 状态都是批量修改
                            TableRecord tableRecord = this.examineMapper.findTableRecord(year,id.intValue());
                            if (tableRecord==null){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("通过失败，请检查" +taskAreaName+ "是否已经完成提交");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }else {

                                if ("12".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.SUCCESS();
                                    apiResult.setMsg("已审核通过。");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                                if (!"10".equals(tableRecord.getCheckType())){
                                    apiResult = ApiResult.FAIL();
                                    apiResult.setMsg("不可通过，请检查" +taskAreaName+ " 是否已经完成提交");
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return apiResult;
                                }
                            }
                            int lastYear = Integer.valueOf(year) - 1;//上一年 年份

                            //根据年份和场站id 10 的修改审核状态 为 12
                            int j = this.examineMapper.updateTableRecordCheckTypeSexQA(id,year,"354531","10","12");
                            if (j==0){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg(stationInfo.get("factory_name") + " " + stationInfo.get("task_area_name") + " " + station.getStationName() + " 审核状态异常");
                                //手动回滚事务
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }
                            //风险防控上年度表 修改审核状态 为 12
                            int k = this.examineMapper.updateLasetYearTableQA(id,String.valueOf(lastYear),"354531","10","12");
                            if (k==0){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg(stationInfo.get("factory_name") + " " + stationInfo.get("task_area_name") + " " + station.getStationName() + " 风险防控上年度表,审核状态异常");
                                //手动回滚事务
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return apiResult;
                            }
                        }
                    }
                }
            }

            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId(findOrganizationResp.getFactoryId());
            //logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
            logCheck.setDate(year);//设置年份
            logCheck.setOperatorId(adminId);//设置审核人id

            //新增日志
            logCheck.setCheckMsg("已通过");

            excleService.addLogCheck(logCheck,request);

            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("提交成功");
            return apiResult;

        }
*/




        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


    /**
     * 审核 通过或驳回
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult examine(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级

        Map map4 = (Map) map.get("map");//前端多封装了一层

        String year = (String) map4.get("year");//获取前端传入年份
        //String year = String.valueOf(year1);

        Integer stationId = (Integer) map4.get("stationId");//获取前端传入场站id
        Integer taskAreaId = (Integer) map4.get("taskAreaId");//获取前端传入作业区id
        Integer result = (Integer) map4.get("result");//获取审核结果(1-通过  2-驳回)

        String local = request.getLocalAddr(); //取得服务器IP
        int prot = request.getLocalPort(); //取得服务器端口
        String token = MemoryData.getTokenMap().get(adminId.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("token",token);
        String url = "http://"+local + ":" + prot + "/api/v1/log/checklog/logs";

        String year2 = ImportExeclUtil.DateUtil.dateToStr(ImportExeclUtil.DateUtil.strToDate(year, ImportExeclUtil.DateUtil.YYYY), ImportExeclUtil.DateUtil.YYYY_MM_DDHHMMSS);

        if (stationId != null && "作业区级A".equals(roleDescName)){
            //根据场站id 查询作业区id 和 气矿id
            Map map2 = this.examineMapper.findTaskAreaAndFactoryId(stationId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId((Long) map2.get("factory_id"));
            logCheck.setTaskAreaId((Long) map2.get("task_area_id"));
            logCheck.setStationId(Long.valueOf(stationId));
            logCheck.setOperatorId(adminId);//设置审核人id
            logCheck.setRoleGradeName("场站级");
            logCheck.setDate(year);


            //作业区审核场站
            if (result == 1){//审核通过
                //把状态 为2,22的改成3
                int i = this.examineMapper.updateExamineCheckType(stationId,year,"2","22","3");

                //把上年度表  2,22的改成3
                int k = this.examineMapper.updateLasetYearTableByS(stationId,Integer.valueOf(year) - 1,"2","22","3");
                if (i>0){
                    logCheck.setCheckMsg("通过");
                    excleService.addLogCheck(logCheck,request);
                }

            }else {//驳回 这个场站下  这个场站下  场站级填的表 修改状态 从 2 => 21
                String reason = (String) map4.get("reason");//驳回原因
                logCheck.setCheckOpinion(reason);
                int i = this.examineMapper.updateCheckTypeByStation(stationId,year,"2","22","21");

                if (i>0){
                    logCheck.setCheckMsg("驳回");
                    excleService.addLogCheck(logCheck,request);
                }
            }
        }



        if (taskAreaId != null && "作业区级B".equals(roleDescName)){
            //根据场站id 查询作业区id 和 气矿id
            Map map2 = this.examineMapper.findFactoryId(taskAreaId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId((Long) map2.get("factory_id"));
            logCheck.setTaskAreaId((Long) map2.get("task_area_id"));
            //logCheck.setStationId((Long) map2.get("station_id"));
            logCheck.setOperatorId(adminId);//设置审核人id
            logCheck.setDate(year);
            logCheck.setRoleGradeName("作业区级A");


           /* //作业区审核场站
            if (result == 1){//审核通过
                //把状态 为 4 23 ->5
                int i = this.examineMapper.updateExamineCheckType(stationId,year,"4","23","5");

                //把上年度表  4 23 ->5
                int k = this.examineMapper.updateLasetYearTableByS(stationId,Integer.valueOf(year) - 1,"4","23","5");
                if (i>0){
                    logCheck.setCheckMsg("通过");
                    excleService.addLogCheck(logCheck,request);
                }

            }else {//驳回 这个场站下  这个场站下  场站级填的表 修改状态 从 4 -> 22
                String reason = (String) map4.get("reason");//驳回原因
                logCheck.setCheckOpinion(reason);
                int i = this.examineMapper.updateExamineCheckType(stationId,year,"4","73541","22");
                //驳回上年度表
                this.examineMapper.updateLasetYearTableByS(stationId,Integer.valueOf(year) - 1,"4","73541","22");

                if (i>0){
                    logCheck.setCheckMsg("驳回");
                    excleService.addLogCheck(logCheck,request);
                }
            }*/

            //查询作业区下所有场站
            List<Station> stationList = this.examineMapper.findStationListByAreaId(taskAreaId);
            if (stationList == null || stationList.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("该作业区下没有绑定的场站");
                return apiResult;
            }
            //作业区B审核
            if (result == 1){//审核通过
                for (Station station:stationList){
                    Long id = station.getStationId();//获取场站id
                    //把状态4 23 ->5
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "4", "23","5");
                    //风险防控上年度表 修改审核状态 4 23 ->5
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"4","23","5");
                    if (i==0){
                        System.out.println(station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println(station.getStationName() + "上年度风险防控表审核状态异常");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + "上年度风险防控表审核状态异常");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 审核成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("通过");
                excleService.addLogCheck(logCheck,request);


            }else {//驳回   气矿驳回给作业区  把这个作业区所有表修改状态  包括风险防控上年度表
                String reason = (String) map4.get("reason");//驳回原因
                logCheck.setCheckOpinion(reason);
                //修改状态  这个作业区下所有场站的所有表  状态 从4 -> 22
                for (Station station:stationList) {
                    Long id = station.getStationId();//获取场站id
                    //把状态 为6变成23
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "4", "23","22");
                    //风险防控上年度表 修改审核状态4 -> 22
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"4","23","22");
                    if (i==0){
                        System.out.println("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println("驳回失败 " + station.getStationName() + " 风险防控上年度表 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + " 风险防控上年度表 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 驳回成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("驳回");
                excleService.addLogCheck(logCheck,request);

            }




        }

        if (taskAreaId != null && "气矿级A".equals(roleDescName)){
            //查询作业区id和气矿id
            Map map2 = this.examineMapper.findFactoryId(taskAreaId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId((Long) map2.get("factory_id"));
            logCheck.setTaskAreaId(Long.valueOf(taskAreaId));
            logCheck.setOperatorId(adminId);//设置审核人id
            logCheck.setDate(year);
            logCheck.setRoleGradeName("作业区级B");

            //查询作业区下所有场站
            List<Station> stationList = this.examineMapper.findStationListByAreaId(taskAreaId);
            if (stationList == null || stationList.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("该作业区下没有绑定的场站");
                return apiResult;
            }
            //气矿审作业区
            if (result == 1){//审核通过
                for (Station station:stationList){
                    Long id = station.getStationId();//获取场站id
                    //把状态 6 24 ->7
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "6", "24","7");
                    //风险防控上年度表 修改审核状态  6 24 ->7
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"6","24","7");
                    if (i==0){
                        System.out.println(station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println(station.getStationName() + "上年度风险防控表审核状态异常");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + "上年度风险防控表审核状态异常");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 审核成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("通过");
                excleService.addLogCheck(logCheck,request);


            }else {//驳回   气矿驳回给作业区  把这个作业区所有表修改状态  包括风险防控上年度表
                String reason = (String) map4.get("reason");//驳回原因
                logCheck.setCheckOpinion(reason);
                //修改状态  这个作业区下所有场站的所有表  状态 从6变成23
                for (Station station:stationList) {
                    Long id = station.getStationId();//获取场站id
                    //把状态 为6变成23
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "6", "24","23");
                    //风险防控上年度表 修改审核状态 6变成23
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"6","24","23");
                    if (i==0){
                        System.out.println("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println("驳回失败 " + station.getStationName() + "上年度风险防控表审核状态异常");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + "上年度风险防控表审核状态异常");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 驳回成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("驳回");
                excleService.addLogCheck(logCheck,request);

            }
        }



        if (taskAreaId != null && "气矿级B".equals(roleDescName)){
            //查询作业区id和气矿id
            Map map2 = this.examineMapper.findFactoryId(taskAreaId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId((Long) map2.get("factory_id"));
            //logCheck.setTaskAreaId((Long) map2.get("task_area_id"));
            logCheck.setDate(year);
            logCheck.setOperatorId(adminId);//设置审核人id
            logCheck.setRoleGradeName("气矿级A");

            //查询气矿下所有场站
            List<Station> stationList =  this.examineMapper.findStationListByFactoryId((Long) map2.get("factory_id"));
            if (stationList == null || stationList.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("该作业区下没有绑定的场站");
                return apiResult;
            }
            //气矿审作业区
            if (result == 1){//审核通过
                for (Station station:stationList){
                    Long id = station.getStationId();//获取场站id
                    //把状态 8 25 -> 9
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "8", "25","9");
                    //风险防控上年度表 修改审核状态  8 25 -> 9
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"8","25","9");
                    if (i==0){
                        System.out.println(station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println(station.getStationName() + "上年度风险防控表审核状态异常");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + "上年度风险防控表审核状态异常");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 审核成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("通过");
                excleService.addLogCheck(logCheck,request);


            }else {//驳回   气矿驳回给作业区  把这个作业区所有表修改状态  包括风险防控上年度表
                String reason = (String) map4.get("reason");//驳回原因
                logCheck.setCheckOpinion(reason);
                //修改状态  这个作业区下所有场站的所有表  状态 从8 25 ->24
                for (Station station:stationList) {
                    Long id = station.getStationId();//获取场站id
                    //把状态 为8变成24
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "8", "25","24");
                    //风险防控上年度表 修改审核状态 8 25变成24
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"8","25","24");
                    if (i==0){
                        System.out.println("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println("驳回失败 " + station.getStationName() + "上年度风险防控表审核状态异常");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + "上年度风险防控表审核状态异常");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 驳回成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("驳回");
                excleService.addLogCheck(logCheck,request);

            }
        }




        if (taskAreaId != null && "气矿级C".equals(roleDescName)){
            //查询作业区id和气矿id
            Map map2 = this.examineMapper.findFactoryId(taskAreaId);
            LogCheck logCheck = new LogCheck();//创建日志对象
            logCheck.setFactoryId((Long) map2.get("factory_id"));
            //logCheck.setTaskAreaId((Long) map2.get("task_area_id"));
            logCheck.setOperatorId(adminId);//设置审核人id
            logCheck.setRoleGradeName("气矿级B");
            logCheck.setDate(year);

            //查询气矿下所有场站
            List<Station> stationList = this.examineMapper.findStationListByFactoryId((Long) map2.get("factory_id"));
            if (stationList == null || stationList.size()==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("该作业区下没有绑定的场站");
                return apiResult;
            }
            //气矿审作业区
            if (result == 1){//审核通过
                for (Station station:stationList){
                    Long id = station.getStationId();//获取场站id
                    //把状态 10 -> 11
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "10", "54551","11");
                    //风险防控上年度表 修改审核状态 10 -> 11
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"10","54551","11");
                    if (i==0){
                        System.out.println(station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println(station.getStationName() + "上年度风险防控表审核状态异常");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg(station.getStationName() + "上年度风险防控表审核状态异常");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 审核成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("通过");
                excleService.addLogCheck(logCheck,request);


            }else {//驳回   气矿驳回给作业区  把这个作业区所有表修改状态  包括风险防控上年度表
                String reason = (String) map4.get("reason");//驳回原因
                logCheck.setCheckOpinion(reason);
                //修改状态  这个作业区下所有场站的所有表  状态 从10变成25
                for (Station station:stationList) {
                    Long id = station.getStationId();//获取场站id
                    //把状态 为8变成24
                    int i = this.examineMapper.updateExamineCheckType(id.intValue(), year, "10", "54534","25");
                    //风险防控上年度表 修改审核状态 10变成25
                    int lastYear = Integer.valueOf(year) - 1;//上一年 年份
                    int j = this.examineMapper.updateExamineLasetYearTable(id.intValue(),lastYear,"10","54354","25");
                    if (i==0){
                        System.out.println("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + " 审核状态不符合");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (j==0){
                        System.out.println("驳回失败 " + station.getStationName() + "上年度风险防控表审核状态异常");
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("驳回失败 " + station.getStationName() + "上年度风险防控表审核状态异常");
                        //手动回滚事务
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return apiResult;
                    }
                    if (i>0 && j>0){
                        System.out.println(station.getStationName() + " 驳回成功，表个数:" + i+j);
                    }
                }
                //新增日志
                logCheck.setCheckMsg("驳回");
                excleService.addLogCheck(logCheck,request);

            }
        }



        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 查询审核结果
     * @param
     * @param request
     * @return
     */
    @Override
    public ApiResult examineResult(String year, HttpServletRequest request) {
        ApiResult apiResult;

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级


        List<RespExamineResult> list = new ArrayList<>();//返回对象
        if ("场站级".equals(roleDescName)){
            //查询该账号所管理的场站id
            List<Long> stationIdList = this.examineMapper.findStationIdList(adminId);
            if (stationIdList!=null){
                for (Long stationId:stationIdList){
                    //查询带有场站id 且 是 作业区级A的审核日志
                    /*RespExamineResult log = this.examineMapper.findLogByZA(stationId,year,"作业区级A");
                    if (log != null){
                        //查询提交时间
                        log.setSubmitTime(this.examineMapper.findSubmitDate(stationId,year));
                        list.add(log);
                    }else {
                        //若没有上级单位的日志  则查询是否有自己的日志 并且 日志内容是 已提交
                        RespExamineResult log1 = this.examineMapper.findLogByC(stationId,year,"场站级");
                        log.setSubmitTime(this.examineMapper.findSubmitDate(stationId,year));
                        list.add(log);
                    }*/

                    RespExamineResult log = this.examineMapper.findLogByCA(stationId,year,"场站级");
                    //查询提交时间
                    if (log != null){
                        log.setSubmitTime(this.examineMapper.findSubmitDate(stationId,year));
                        list.add(log);
                    }

                }
            }
        }
        if ("作业区级A".equals(roleDescName)){
            //查询该账号所管理的作业区id  只有一个
            Long taskAreaId = this.examineMapper.findTaskAreaId(adminId);
            if (taskAreaId==null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定作业区");
                return apiResult;
            }
            //根据作业区id  查询审批结果
            RespExamineResult log = this.examineMapper.findLogByZA(taskAreaId,year,"作业区级A");
            if (log != null){
                //查询提交时间
                log.setSubmitTime(this.examineMapper.findSubmitDateByArea(taskAreaId,year,"作业区级A"));
                list.add(log);
            }

        }

        if ("作业区级B".equals(roleDescName)){
            //查询该账号所管理的作业区id  只有一个
            Long taskAreaId = this.examineMapper.findTaskAreaId(adminId);
            if (taskAreaId==null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定作业区");
                return apiResult;
            }
            //根据作业区id  查询审批结果
            RespExamineResult log = this.examineMapper.findLogByZA(taskAreaId,year,"作业区级B");
            if (log != null){
                //查询提交时间
                log.setSubmitTime(this.examineMapper.findSubmitDateByArea(taskAreaId,year,"作业区级B"));
                list.add(log);
            }
        }


        if ("气矿级A".equals(roleDescName)){
            //查询该账号所管理的气矿id  只有一个
            Long factoryId = this.examineMapper.findFactoryAdminId(adminId);
            if (factoryId==null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定作业区");
                return apiResult;
            }
            //根据作业区id  查询审批结果
            RespExamineResult log = this.examineMapper.findLogByQA(factoryId,year,"气矿级A");
            if (log != null){
                //查询提交时间
                log.setSubmitTime(this.examineMapper.findSubmitDateByFactory(factoryId,year,"气矿级A"));
                list.add(log);
            }
        }



        if ("气矿级B".equals(roleDescName)){
            //查询该账号所管理的气矿id  只有一个
            Long factoryId = this.examineMapper.findFactoryAdminId(adminId);
            if (factoryId==null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定作业区");
                return apiResult;
            }
            //根据作业区id  查询审批结果
            RespExamineResult log = this.examineMapper.findLogByQA(factoryId,year,"气矿级B");
            if (log != null){
                //查询提交时间
                log.setSubmitTime(this.examineMapper.findSubmitDateByFactory(factoryId,year,"气矿级B"));
                list.add(log);
            }
        }



        //如果是 已提交 则清空 审核人
        if (list!=null && list.size()!=0){
            for (RespExamineResult info:list){
                if (info!=null && "已提交".equals(info.getCheckMsg())){
                    info.setFullName("");
                }
            }
        }

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }


    /**
     * 查询待审核
     *
     *
     */
    @Override
    public ApiResult unaudited(String year,  HttpServletRequest request,int currentPage) {
        ApiResult apiResult;

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级

        List<RespUnaudited> list = new ArrayList<>();

        if ("作业区级A".equals(roleDescName)){
            //作业区级 查看未审核场站的

            //查询作业区下所管理的场站id集合
            List<Long> stationIdList = this.examineMapper.findStationIdListByAdminId(adminId);
            if (stationIdList!=null && stationIdList.size()!=0){
                for (Long stationId:stationIdList){


                    //根据场站id 查询这个场站有没有 已提交待审核的表(状态为2)
                    int i = this.examineMapper.findTableByStationId(stationId,year,"2");
                    if (i>0){//只要有表 是2的状态  这个场站就是待审核
                        RespUnaudited info = new RespUnaudited();
                        info.setStationId(stationId);
                        Date submitTime = this.examineMapper.findSubmitDate(stationId,year);
                        info.setSubmitTime(submitTime);

                        //查询场站其余信息
                        info.setYear(year);//年份
                        Map map = this.examineMapper.findStationById(stationId);
                        info.setFactoryName((String) map.get("factory_name"));
                        info.setTaskAreaName((String) map.get("task_area_name"));
                        info.setStationName((String) map.get("station_name"));
                        info.setFactoryTypeName((String) map.get("factory_type_name"));
                        info.setCheckMsg("待审核");
                        list.add(info);

                    }

                    //根据场站id 查询这个场站有没有 22-作业区B已审核未通过被驳回  作业区A可继续驳回
                    int j = this.examineMapper.findTableByStationIdByS(stationId,year,"22");
                    if (j>0){//只要有表 是22的状态  作业区可继续驳回
                        RespUnaudited info = new RespUnaudited();
                        info.setStationId(stationId);
                        Date submitTime = this.examineMapper.findSubmitDate(stationId,year);
                        info.setSubmitTime(submitTime);

                        //查询场站其余信息
                        info.setYear(year);//年份
                        Map map = this.examineMapper.findStationById(stationId);
                        info.setFactoryName((String) map.get("factory_name"));
                        info.setTaskAreaName((String) map.get("task_area_name"));
                        info.setStationName((String) map.get("station_name"));
                        info.setFactoryTypeName((String) map.get("factory_type_name"));
                        info.setCheckMsg("待审核");
                        list.add(info);

                    }

                    //如果这个场站没有待审核 则查询审核结果
                    if (i==0 && j==0){
                        RespExamineResult info1 = this.examineMapper.findExamineResultById(stationId,year);
                        if (info1!=null){
                            //查询提交时间
                            Date submitDate = this.examineMapper.findSubmitDate(stationId,year);
                            info1.setSubmitTime(submitDate);

                            RespUnaudited info = new RespUnaudited();
                            info.setFactoryName(info1.getFactoryName());
                            info.setTaskAreaId(info1.getTaskAreaId());
                            info.setTaskAreaName(info1.getTaskAreaName());
                            info.setStationId(info1.getStationId());
                            info.setStationName(info1.getStationName());
                            info.setFactoryTypeName(info1.getFactoryTypeName());
                            info.setSubmitTime(info1.getSubmitTime());
                            info.setFullName(info1.getFullName());
                            info.setCheckMsg(info1.getCheckMsg());
                            info.setCheckOpinion(info1.getCheckOpinion());
                            list.add(info);
                        }
                    }

                }


            }

        }



        /*if ("作业区级A".equals(roleDescName) && stationId!=null){
            //作业区级 查看未审核场站的

            //根据场站id 查询这个场站有没有 已提交待审核的表(状态为2)
            int i = this.examineMapper.findTableByStationId(stationId,year,"2");
            if (i>0){//只要有表 是2的状态  这个场站就是待审核
                RespUnaudited info = new RespUnaudited();
                info.setStationId(stationId);
                Date submitTime = this.examineMapper.findSubmitDate(stationId,year);
                info.setSubmitTime(submitTime);

                //查询场站其余信息
                info.setYear(year);//年份
                Map map = this.examineMapper.findStationById(stationId);
                info.setFactoryName((String) map.get("factory_name"));
                info.setTaskAreaName((String) map.get("task_area_name"));
                info.setStationName((String) map.get("station_name"));
                info.setFactoryTypeName((String) map.get("factory_type_name"));
                info.setCheckMsg("待审核");
                list.add(info);

            }

            //根据场站id 查询这个场站有没有 22-作业区B已审核未通过被驳回  作业区A可继续驳回
            int j = this.examineMapper.findTableByStationIdByS(stationId,year,"22");
            if (j>0){//只要有表 是22的状态  作业区可继续驳回
                RespUnaudited info = new RespUnaudited();
                info.setStationId(stationId);
                Date submitTime = this.examineMapper.findSubmitDate(stationId,year);
                info.setSubmitTime(submitTime);

                //查询场站其余信息
                info.setYear(year);//年份
                Map map = this.examineMapper.findStationById(stationId);
                info.setFactoryName((String) map.get("factory_name"));
                info.setTaskAreaName((String) map.get("task_area_name"));
                info.setStationName((String) map.get("station_name"));
                info.setFactoryTypeName((String) map.get("factory_type_name"));
                info.setCheckMsg("待审核");
                list.add(info);

            }

            //查询所管理的场站id集合
            if (list.size()==0){
                RespExamineResult info1 = this.examineMapper.findExamineResultById(stationId,year);
                if (info1!=null){
                    //查询提交时间
                    Date submitDate = this.examineMapper.findSubmitDate(stationId,year);
                    info1.setSubmitTime(submitDate);

                    RespUnaudited info = new RespUnaudited();
                    info.setFactoryName(info1.getFactoryName());
                    info.setTaskAreaId(info1.getTaskAreaId());
                    info.setTaskAreaName(info1.getTaskAreaName());
                    info.setStationId(info1.getStationId());
                    info.setStationName(info1.getStationName());
                    info.setFactoryTypeName(info1.getFactoryTypeName());
                    info.setSubmitTime(info1.getSubmitTime());
                    info.setFullName(info1.getFullName());
                    info.setCheckMsg(info1.getCheckMsg());
                    info.setCheckOpinion(info1.getCheckOpinion());
                    list.add(info);
                }
            }

            apiResult = ApiResult.SUCCESS();
            apiResult.setData(list);
            return apiResult;
        }
*/




        if ("作业区级B".equals(roleDescName)){
            //查询该账号管理的作业区Id
            Long taskAreaId1 = this.examineMapper.findTaskAreaId(adminId);
            //作业区级B 查看未审核作业区的
            //查询该作业区下 所有的 场站id
            List<Station> stationList = this.examineMapper.findStationListByAreaId(taskAreaId1.intValue());
            if (stationList != null && stationList.size()!=0){
                //查询其下的 场站   只要有一个场站的状态 为4  则是 已提交待审核
                int i = this.examineMapper.findTableByStationId(stationList.get(0).getStationId(),year,"4");
                if (i>0){//只要有表 是6的状态  这个作业区就是待审核
                    RespUnaudited info = new RespUnaudited();
                    info.setTaskAreaId(taskAreaId1);
                    Date submitTime = this.examineMapper.findSubmitDateByArea(taskAreaId1,year,"作业区级A");
                    info.setSubmitTime(submitTime);

                    //查询场站其余信息
                    info.setYear(year);//年份
                    Map map = this.examineMapper.findStationById(stationList.get(0).getStationId());
                    info.setFactoryName((String) map.get("factory_name"));
                    info.setTaskAreaName((String) map.get("task_area_name"));
                    info.setCheckMsg("待审核");
                    //info.setStationName((String) map.get("station_name"));
                    //info.setFactoryTypeName((String) map.get("factory_type_name"));
                    list.add(info);
                }


                //根据场站id 查询这个场站有没有 23-气矿A已审核未通过被驳回  作业区B可继续驳回
                int j = this.examineMapper.findTableByStationIdByS(stationList.get(0).getStationId(),year,"23");
                if (j>0){//只要有表 是23的状态  作业区可继续驳回
                    RespUnaudited info = new RespUnaudited();
                    Date submitTime = this.examineMapper.findSubmitDateByArea(taskAreaId1,year,"作业区级A");
                    info.setSubmitTime(submitTime);

                    //查询场站其余信息
                    info.setYear(year);//年份
                    Map map = this.examineMapper.findStationById(stationList.get(0).getStationId());
                    info.setFactoryName((String) map.get("factory_name"));
                    info.setTaskAreaName((String) map.get("task_area_name"));
                    info.setCheckMsg("待审核");
                    list.add(info);

                }

            }

            if (list.size()==0){
                //根据作业区id  查询审批结果
                RespExamineResult info1 = this.examineMapper.findExamineResultByTaskAreaId(taskAreaId1,year,"作业区级A");
                if (info1 != null){
                    RespUnaudited info = new RespUnaudited();
                    //查询提交时间
                    Date submitDate = this.examineMapper.findSubmitDateByArea(taskAreaId1,year,"作业区级A");
                    info1.setSubmitTime(submitDate);
                    info.setFactoryName(info1.getFactoryName());
                    info.setTaskAreaId(info1.getTaskAreaId());
                    info.setTaskAreaName(info1.getTaskAreaName());
//                    info.setStationId(info1.getStationId());
//                    info.setStationName(info1.getStationName());
//                    info.setFactoryTypeName(info1.getFactoryTypeName());
                    info.setSubmitTime(info1.getSubmitTime());
                    info.setFullName(info1.getFullName());
                    info.setCheckMsg(info1.getCheckMsg());
                    info.setCheckOpinion(info1.getCheckOpinion());
                    list.add(info);
                }
            }



        }







        if ("气矿级A".equals(roleDescName)){
            //气矿级 查看未审核作业区的


            //根据adminId 查询所管理的所有作业区Id集合
            List<Long> taskAreaIdList = this.examineMapper.findTaskAreaIdListByAdminId(adminId);
            if (taskAreaIdList != null && taskAreaIdList.size()!=0){
                for (Long taskAreaId:taskAreaIdList){

                    //查询该作业区下 所有的 场站id
                    List<Station> stationList = this.examineMapper.findStationListByAreaId(taskAreaId.intValue());
                    if (stationList != null && stationList.size()!=0){
                        //查询其下的 场站   只要有一个场站的状态 为6  则是 已提交待审核
                        int i = this.examineMapper.findTableByStationId(stationList.get(0).getStationId(),year,"6");
                        if (i>0){//只要有表 是6的状态  这个作业区就是待审核
                            RespUnaudited info = new RespUnaudited();
                            info.setTaskAreaId(taskAreaId);
                            Date submitTime = this.examineMapper.findSubmitDateByArea(taskAreaId,year,"作业区级B");
                            info.setSubmitTime(submitTime);

                            //查询场站其余信息
                            info.setYear(year);//年份
                            Map map = this.examineMapper.findStationById(stationList.get(0).getStationId());
                            info.setFactoryName((String) map.get("factory_name"));
                            info.setTaskAreaName((String) map.get("task_area_name"));
                            info.setCheckMsg("待审核");
                            //info.setStationName((String) map.get("station_name"));
                            //info.setFactoryTypeName((String) map.get("factory_type_name"));
                            list.add(info);
                        }

                        //根据场站id 查询这个场站有没有 24-气矿B已审核未通过被驳回  气矿A可继续驳回
                        int j = this.examineMapper.findTableByStationIdByS(stationList.get(0).getStationId(),year,"24");
                        if (j>0){//只要有表 是24的状态  气矿A可继续驳回
                            RespUnaudited info = new RespUnaudited();
                            Date submitTime = this.examineMapper.findSubmitDateByArea(taskAreaId,year,"作业区级B");
                            info.setSubmitTime(submitTime);

                            //查询场站其余信息
                            info.setYear(year);//年份
                            Map map = this.examineMapper.findStationById(stationList.get(0).getStationId());
                            info.setFactoryName((String) map.get("factory_name"));
                            info.setTaskAreaName((String) map.get("task_area_name"));
                            info.setCheckMsg("待审核");
                            list.add(info);

                        }


                        if (i==0 && j==0){
                            //根据作业区id  查询审批结果
                            RespExamineResult info1 = this.examineMapper.findExamineResultByTaskAreaId(taskAreaId,year,"作业区级B");
                            if (info1 != null){
                                RespUnaudited info = new RespUnaudited();
                                //查询提交时间
                                Date submitDate = this.examineMapper.findSubmitDateByArea(taskAreaId,year,"作业区级B");
                                info1.setSubmitTime(submitDate);
                                info.setFactoryName(info1.getFactoryName());
                                info.setTaskAreaId(info1.getTaskAreaId());
                                info.setTaskAreaName(info1.getTaskAreaName());
//                    info.setStationId(info1.getStationId());
//                    info.setStationName(info1.getStationName());
//                    info.setFactoryTypeName(info1.getFactoryTypeName());
                                info.setSubmitTime(info1.getSubmitTime());
                                info.setFullName(info1.getFullName());
                                info.setCheckMsg(info1.getCheckMsg());
                                info.setCheckOpinion(info1.getCheckOpinion());
                                list.add(info);
                            }
                        }

                    }

                }


            }


        }






        if ("气矿级B".equals(roleDescName)){
            Long factoryId = this.examineMapper.findFactoryAdminId(adminId);
            //气矿级B 查看未审核气矿级A的
            //查询该气矿下 所有的 场站id
            List<Station> stationList = this.examineMapper.findStationListByFactoryId(factoryId);
            if (stationList != null && stationList.size()!=0){
                //查询其下的 场站   只要有一个场站的状态 为8  则是 已提交待审核
                int i = this.examineMapper.findTableByStationId(stationList.get(0).getStationId(),year,"8");
                if (i>0){//只要有表 是8的状态  这个气矿级A就是待审核
                    RespUnaudited info = new RespUnaudited();
                    Date submitTime = this.examineMapper.findSubmitDateByFactory(factoryId,year,"气矿级A");
                    info.setSubmitTime(submitTime);

                    //查询场站其余信息
                    info.setYear(year);//年份
                    Map map = this.examineMapper.findStationById(stationList.get(0).getStationId());
                    info.setFactoryName((String) map.get("factory_name"));
                    //info.setTaskAreaName((String) map.get("task_area_name"));
                    info.setCheckMsg("待审核");
                    //info.setStationName((String) map.get("station_name"));
                    //info.setFactoryTypeName((String) map.get("factory_type_name"));
                    list.add(info);
                }

                //根据场站id 查询这个场站有没有 25-气矿C已审核未通过被驳回  气矿B可继续驳回
                int j = this.examineMapper.findTableByStationIdByS(stationList.get(0).getStationId(),year,"25");
                if (j>0){//只要有表 是24的状态  气矿A可继续驳回
                    RespUnaudited info = new RespUnaudited();
                    Date submitTime = this.examineMapper.findSubmitDateByFactory(factoryId,year,"气矿级A");
                    info.setSubmitTime(submitTime);

                    //查询场站其余信息
                    info.setYear(year);//年份
                    Map map = this.examineMapper.findStationById(stationList.get(0).getStationId());
                    info.setFactoryName((String) map.get("factory_name"));
                    //info.setTaskAreaName((String) map.get("task_area_name"));
                    info.setCheckMsg("待审核");
                    list.add(info);

                }

            }

            if (list.size()==0){
                //根据作业区id  查询审批结果
                RespExamineResult info1 = this.examineMapper.findExamineResultByFactoryId(factoryId,year,"气矿级A");
                if (info1 != null){
                    RespUnaudited info = new RespUnaudited();
                    //查询提交时间
                    Date submitDate = this.examineMapper.findSubmitDateByFactory(factoryId,year,"气矿级A");
                    info1.setSubmitTime(submitDate);
                    info.setFactoryName(info1.getFactoryName());
                    //info.setTaskAreaId(info1.getTaskAreaId());
                    //info.setTaskAreaName(info1.getTaskAreaName());
//                    info.setStationId(info1.getStationId());
//                    info.setStationName(info1.getStationName());
//                    info.setFactoryTypeName(info1.getFactoryTypeName());
                    info.setSubmitTime(info1.getSubmitTime());
                    info.setFullName(info1.getFullName());
                    info.setCheckMsg(info1.getCheckMsg());
                    info.setCheckOpinion(info1.getCheckOpinion());
                    list.add(info);
                }
            }

        }





        if ("气矿级C".equals(roleDescName)){
            Long factoryId = this.examineMapper.findFactoryAdminId(adminId);
            //气矿级C 查看未审核气矿级B的
            //查询该气矿下 所有的 场站id
            List<Station> stationList = this.examineMapper.findStationListByFactoryId(factoryId);
            if (stationList != null && stationList.size()!=0){
                //查询其下的 场站   只要有一个场站的状态 为10  则是 已提交待审核
                int i = this.examineMapper.findTableByStationId(stationList.get(0).getStationId(),year,"10");
                if (i>0){//只要有表 是10的状态  这个气矿级A就是待审核
                    RespUnaudited info = new RespUnaudited();
                    Date submitTime = this.examineMapper.findSubmitDateByFactory(factoryId,year,"气矿级B");
                    info.setSubmitTime(submitTime);

                    //查询场站其余信息
                    info.setYear(year);//年份
                    Map map = this.examineMapper.findStationById(stationList.get(0).getStationId());
                    info.setFactoryName((String) map.get("factory_name"));
                    //info.setTaskAreaName((String) map.get("task_area_name"));
                    info.setCheckMsg("待审核");
                    //info.setStationName((String) map.get("station_name"));
                    //info.setFactoryTypeName((String) map.get("factory_type_name"));
                    list.add(info);
                }


            }

            if (list.size()==0){
                //根据作业区id  查询审批结果
                RespExamineResult info1 = this.examineMapper.findExamineResultByFactoryId(factoryId,year,"气矿级B");
                if (info1 != null){
                    RespUnaudited info = new RespUnaudited();
                    //查询提交时间
                    Date submitDate = this.examineMapper.findSubmitDateByFactory(factoryId,year,"气矿级B");
                    info1.setSubmitTime(submitDate);
                    info.setFactoryName(info1.getFactoryName());
                    info.setSubmitTime(info1.getSubmitTime());
                    info.setFullName(info1.getFullName());
                    info.setCheckMsg(info1.getCheckMsg());
                    info.setCheckOpinion(info1.getCheckOpinion());
                    list.add(info);
                }
            }

        }


        //手动分页

        //开始下标
        int minRow = (currentPage - 1) * 10;
        //结束下标
        int maxRow = minRow + 10;

        //返回结果
        List<RespUnaudited> newlist = new ArrayList<>();
        for (int i = minRow; i < maxRow; i++) {
            try {
                newlist.add(list.get(i));
            }catch (Exception e){//数组越界  跳出循环
                break;
            }
        }

        Pager pager = new Pager();
        pager.setContent(newlist);
        pager.setRecordTotal(list.size());//设置总条数


        apiResult = ApiResult.SUCCESS();
        apiResult.setData(pager);
        return apiResult;
    }

    /**
     * 根据场站id
     * 如果是作业区级或气矿级  则查询场站下所有的表
     * 如果是场站级 则查询场站填写的表
     * 查询提交信息详情
     * @param year
     * @param stationId
     * @param request
     * @return
     */
    @Override
    public ApiResult getInfo(String year, Long stationId, HttpServletRequest request) {
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        List<RespTableInfo> list = new ArrayList<>();//返回对象
        if ("作业区级A".equals(roleDescName) ||"作业区级B".equals(roleDescName) || "气矿级A".equals(roleDescName) || "气矿级B".equals(roleDescName)  || "气矿级C".equals(roleDescName)){
            //作业区级和气矿级  可以看这个场站下所有的表  包括 作业区填写的表
            list = this.examineMapper.findTableInfoByArea(stationId,year);
            //查询上年度表
            RespTableInfo respTableInfo = this.examineMapper.findLastYearTable(stationId,Integer.valueOf(year) - 1);
            if (respTableInfo != null){
                list.add(respTableInfo);
            }
        }
        if ("场站级".equals(roleDescName)){
            //场站级  只能看 由场站填写 表
            list = this.examineMapper.findTableInfoByStation(stationId,year);
        }
        for (RespTableInfo info:list){
            if (info != null){
                if ("2".equals(info.getType())){
                    info.setExcleTableName("风险防控本年度表");
                }
                if ("3".equals(info.getType())){
                    info.setExcleTableName("风险防控上年度表");
                }

                String name = info.getExcleTableName();
                //修改别名
                if ("C值(输气管线)".equals(name)||"C值(输水管线)".equals(name)||"C值(输油管线)".equals(name)||"E气".equals(name)||"E水".equals(name)||
                        "L气".equals(name)||"L水".equals(name)||"M气".equals(name)||"M水".equals(name)||
                        "P值".equals(name)||"Q气".equals(name)||"Q水".equals(name)||"违法记录表(场站)".equals(name)||
                        "违法记录表(管线)".equals(name)){//环境风险等级 模块

                    info.setByName("环境风险等级");
                }else if("风险防控本年度表".equals(name) || "风险防控上年度表".equals(name)){//环境风险防控与应急措施 模块

                    info.setByName("环境风险防控与应急措施");
                }else if ("环境风险台账(较大或一般)".equals(name)||"环境风险台账(重大)".equals(name)){//年度环境风险台账 模块

                    info.setByName("年度环境风险台账");
                }else {//其余模块不变
                    info.setByName(info.getExcleTableName());
                }

            }
        }



        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }


    /**
     * 测试分页
     * @param args
     */
    public static void main(String[] args) {
        List list = new ArrayList();
        for (int i = 1;i<=95; i++){
            list.add(i);
        }

        int currentPage = 10;

        //开始下标
        int minRow = (currentPage - 1) * 10;
        //结束下标
        int maxRow = minRow + 10;

        //返回结果
        List newlist = new ArrayList<>();
        for (int i = minRow; i < maxRow; i++) {
            try {
                newlist.add(list.get(i));
            }catch (Exception e){//数组越界  跳出循环
                break;
            }
        }

        Pager pager = new Pager();
        pager.setContent(newlist);
        pager.setRecordTotal(list.size());//设置总条数

        System.out.println(pager);
    }
}
