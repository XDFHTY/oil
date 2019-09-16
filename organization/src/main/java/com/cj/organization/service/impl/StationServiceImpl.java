package com.cj.organization.service.impl;

import com.cj.common.entity.*;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.organization.entity.*;
import com.cj.organization.mapper.*;
import com.cj.organization.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 组织结构信息 中心站 场站管理
 * Created by XD on 2018/10/9.
 */
@Service
@Transactional
public class StationServiceImpl implements StationService {

    //分公司id
    private Long branchCompanyId = 1L;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private AuthAdminPopedomMapper authAdminPopedomMapper;

    @Autowired
    private StationCoreMapper stationCoreMapper;

    @Autowired
    private TaskAreaMapper taskAreaMapper;

    @Autowired
    private FactoryBasicMsgMapper factoryMapper;

    /**
     * 角色id 常量
     */
    private final static Integer valBranchId = 1;//分公司工作人员
    private final static Integer valInstitute = 2;//安研院工作人员
    private final static Integer valSupervisionId = 3;//监督中心工作人员
    private final static Integer valFactoryCId = 4;//气矿C工作人员
    private final static Integer valFactoryBId = 5;//气矿B工作人员
    private final static Integer valFactoryAId = 6;//气矿A工作人员
    private final static Integer valTaskAreaBId = 8;//作业区B工作人员
    private final static Integer valTaskAreaAId = 9;//作业区A工作人员
    private final static Integer valStationId = 10;//场站工作人员
    private final static Integer valStationPipelineId = 11;//管线工作人员

    /**
     * 条件查询 气矿基本信息、作业区、场站和管理人信息
     *
     * @param p
     * @param request
     * @return
     */
    @Override
    public ApiResult findStationById(Pager p, HttpServletRequest request) {
        ApiResult apiResult;
        //设置分公司id
        p.getParameters().put("branchCompanyId", branchCompanyId);


        //条件查询场站级联系人
        List<RespStation> list = this.stationMapper.findStationAndAdmin(p);
        //计数
        int i = this.stationMapper.findStationAndAdminCount(p);


        p.setContent(list);
        p.setRecordTotal(i);
        apiResult = ApiResult.SUCCESS();
        apiResult.setData(p);

        return apiResult;
    }

    /**
     * 移除账号关联
     *
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult removeAdmin(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        /**
         * 移除关联  前端查询的账号列表  只有同级 和 所属下级 机构的账号
         * 所以只需要判断  需要移除的账号 角色等级是否比 操作人等级小 就可以移除
         */

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级
        //根据adminId 查询账号等级
        String roleDescName2 = this.authAdminPopedomMapper.findRoleByAdminId((Integer) map.get("adminId"));
        if (roleId == valStationId || roleId == valStationPipelineId) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ) {//作业区级
            if (!"场站级".equals(roleDescName2) && roleDescName2 != null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能移除场站级账号");
                return apiResult;
            }
        }
        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ) {//气矿级
            if (!"作业区级".equals(roleDescName2) && !"场站级".equals(roleDescName2) && roleDescName2 != null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能移除作业区级或场站级账号");
                return apiResult;
            }
        }
        if (roleId == valBranchId || roleId == valInstitute || roleId == valSupervisionId) {//分公司级
            if ("分公司级".equals(roleDescName2)) {
                if (roleId!=valInstitute){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("您只能移除气矿级、作业区级或场站级账号");
                    return apiResult;
                }
            }
        }
        Integer stationId = (Integer) map.get("stationId");//场站id
        int i;
        if (stationId == null) {
            //需要移除的账号不是场站级  那么直接去数据库删除就好 因为一个账号只对应一个作业区或气矿
            i = this.authAdminPopedomMapper.deleteRecordByAdminIdAndPopId((Integer) map.get("adminId"), null);
        } else {
            //场站级  因为一个账号可能关联多个场站  所以需要确定场站id删除
            i = this.authAdminPopedomMapper.deleteRecordByAdminIdAndPopId((Integer) map.get("adminId"), stationId);
        }
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }


        return apiResult;
    }

    /**
     * 关联账号
     *
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult relationAdmin(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        /**
         * 分公司级 可操作气矿级
         * 气矿级  可操作作业区级
         * 作业区级 可操作场站级
         * 需要操作的账号等级  需要比操作人等级小  才可关联
         * 需要操作的账号等级  需要和准备关联的机构等级 一致
         *
         **/

        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        Integer branchCompanyId = (Integer) map.get("branchCompanyId");

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        //根据adminId 查询账号等级
        String roleDescName2 = this.authAdminPopedomMapper.findRoleByAdminId((Integer) map.get("adminId"));
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级
        Integer adminId2 = (Integer) map.get("adminId");
        int i = 0;
        if (roleId == valStationId || roleId == valStationPipelineId) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ) {//作业区级
            if (!"场站级".equals(roleDescName2) && roleDescName2 != null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能关联场站级账号");
                return apiResult;
            }
            if (stationId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是作业区级，只能把账号关联到场站");
                return apiResult;
            }
            //查询该场站是否有账号关联
            AuthAdminPopedom adminByStationId = this.authAdminPopedomMapper.findAdminByStationId(Long.valueOf(stationId),Long.valueOf(adminId2), 4);
            if (adminByStationId != null) {
                /*apiResult = ApiResult.FAIL();
                apiResult.setMsg("该场站已经绑定过管理员，请先移除");
                return apiResult;*/
            }else {
                //把该账号关联到场站
                i = this.authAdminPopedomMapper.addRecord(Long.valueOf(adminId2), Long.valueOf(stationId), 4);
            }
        }
        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ) {//气矿级
            if (!"作业区级A".equals(roleDescName2) && !"作业区级B".equals(roleDescName2) && !"场站级".equals(roleDescName2) && roleDescName2 != null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能关联作业区级或场站级账号");
                return apiResult;
            }
            if (areaId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是气矿级，只能把账号关联到作业区或场站");
                return apiResult;
            }
            if (areaId != null && stationId != null) {
                //把该账号关联到场站
                //查询该场站是否有账号关联
                AuthAdminPopedom adminByStationId = this.authAdminPopedomMapper.findAdminByStationId(Long.valueOf(stationId),Long.valueOf(adminId2), 4);
                if (adminByStationId != null) {
                   /* apiResult = ApiResult.FAIL();
                    apiResult.setMsg("该场站已经绑定过管理员，请先移除");
                    return apiResult;*/
                }else {
                    i = this.authAdminPopedomMapper.addRecord(Long.valueOf(adminId2), Long.valueOf(stationId), 4);
                }
            }
            if (areaId != null && stationId == null) {
                //把该账号关联到作业区
                i = this.authAdminPopedomMapper.addRecord(Long.valueOf(adminId2), Long.valueOf(areaId), 3);
            }
        }
        if (roleId == valBranchId || roleId == valInstitute || roleId == valSupervisionId) {//分公司级
            if ("分公司级".equals(roleDescName2)) {
                if (roleId!=valInstitute){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("您只能关联气矿级、作业区级或场站级账号");
                    return apiResult;
                }
            }
            if (roleId!=valInstitute){
                if (factoryId == null) {
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("您只能把账号关联到气矿、作业区或场站");
                    return apiResult;
                }
            }
            if (areaId != null && stationId != null) {
                //把该账号关联到场站
                //查询该场站是否有账号关联
                AuthAdminPopedom adminByStationId = this.authAdminPopedomMapper.findAdminByStationId(Long.valueOf(stationId),Long.valueOf(adminId2), 4);
                if (adminByStationId != null) {
                    /*apiResult = ApiResult.FAIL();
                    apiResult.setMsg("该场站已经绑定过管理员，请先移除");
                    return apiResult;*/
                }else {
                    i = this.authAdminPopedomMapper.addRecord(Long.valueOf(adminId2), Long.valueOf(stationId), 4);
                }
            }
            if (areaId != null && stationId == null) {
                //把该账号关联到作业区
                i = this.authAdminPopedomMapper.addRecord(Long.valueOf(adminId2), Long.valueOf(areaId), 3);
            }
            if (areaId == null && stationId == null && factoryId != null) {
                //把该账号关联到气矿
                i = this.authAdminPopedomMapper.addRecord(Long.valueOf(adminId2), Long.valueOf(factoryId), 2);
            }
            if (branchCompanyId!=null && factoryId==null && areaId == null && stationId == null){
                i = this.authAdminPopedomMapper.addRecord(Long.valueOf(adminId2), Long.valueOf(branchCompanyId), 1);
            }
        }
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 按姓名模糊筛选账号列表
     *
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult selectAdmin(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        /**
         * 按姓名模糊查询账号列表
         * 需要带上 机构的id  明确  查询哪一级的账号
         * 只能查询比操作人等级小的账号
         * 查询的账号是未绑定过关系的账号
         */

        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        Integer branchCompanyId = (Integer) map.get("branchCompanyId");

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级


        List<RespAdmin> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();//存放账号等级id
        String name = (String) map.get("name");
        if (name != null && "".equals(name.trim())) {//如果是空字符串 则查询全部
            name = null;
        }

        if (roleId == valStationId || roleId == valStationPipelineId) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ) {//作业区级
            if (stationId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是作业区级，只能查询场站级账号");
                return apiResult;
            }
            //查询所有场站级账号 并没有绑定机构  场站级的账号角色等级id为 10,11
            list2.add(10);
            list2.add(11);
            list = this.authAdminPopedomMapper.findAdminByRoleId(name, list2);
            //查询此作业区下 所有场站级账号
            List<RespAdmin> list3 = this.authAdminPopedomMapper.findAdminByTaskAreaId(areaId);
            list.addAll(list3);

        }
        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ) {//气矿级
            if (areaId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是气矿级，只能查询作业区级或场站级账号");
                return apiResult;
            }
            if (areaId != null && stationId != null) {//查询场站级
                list2.add(10);
                list2.add(11);
                list = this.authAdminPopedomMapper.findAdminByRoleId(name, list2);

                //查询此作业区下 所有场站级账号
                List<RespAdmin> list3 = this.authAdminPopedomMapper.findAdminByTaskAreaId(areaId);
                list.addAll(list3);
            }
            if (areaId != null && stationId == null) {//查询作业区级
                list2.add(8);
                list2.add(9);
                list = this.authAdminPopedomMapper.findAdminByRoleId(name, list2);
            }

        }
        if (roleId == valBranchId || roleId == valInstitute || roleId == valSupervisionId) {//分公司级
            if (roleId!=valInstitute){
                if (factoryId == null) {
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("您只能查询气矿级、作业区级或场站级账号");
                    return apiResult;
                }
            }
            if (areaId != null && stationId != null) {//查询场站级
                list2.add(10);
                list2.add(11);
                list = this.authAdminPopedomMapper.findAdminByRoleId(name, list2);

                //查询此作业区下 所有场站级账号
                List<RespAdmin> list3 = this.authAdminPopedomMapper.findAdminByTaskAreaId(areaId);
                list.addAll(list3);
            }
            if (areaId != null && stationId == null) {//查询作业区级
                list2.add(8);
                list2.add(9);
                list = this.authAdminPopedomMapper.findAdminByRoleId(name, list2);
            }
            if (areaId == null && stationId == null && factoryId != null) {//查询气矿级
                list2.add(4);
                list2.add(5);
                list2.add(6);
                list = this.authAdminPopedomMapper.findAdminByRoleId(name, list2);
            }
            if (branchCompanyId!=null && factoryId==null && areaId == null && stationId == null){//查询分公司级
                list2.add(1);
                list2.add(2);
                list2.add(3);
                list = this.authAdminPopedomMapper.findAdminByRoleId(name, list2);
            }
        }
        apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }


    /**
     * 删除的组织
     *
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult deleteOrganization(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        //要删除的组织  必须管理人 及下属机构的管理人 都要被移除后才能删除

        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级

        if (roleId == valStationId || roleId == valStationPipelineId) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }


        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ) {//作业区级
            if (stationId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是作业区级，只能删除场站");
                return apiResult;
            }
            //删除场站
            return this.deleteStationById(stationId);
        }


        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ) {//气矿级
            if (areaId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是气矿级，只能删除作业区或场站");
                return apiResult;
            }
            if (areaId != null && stationId != null) {//删除场站级
                //删除场站
                return this.deleteStationById(stationId);
            }
            if (areaId != null && stationId == null) {//删除作业区级
                //删除作业区
                return this.deleleAreaById(areaId);
            }

        }


        if (roleId == valBranchId || roleId == valInstitute || roleId == valSupervisionId) {//分公司级
            if (factoryId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能删除气矿、作业区或场站");
                return apiResult;
            }
            if (areaId != null && stationId != null) {//删除场站级
                //删除场站
                return this.deleteStationById(stationId);
            }
            if (areaId != null && stationId == null) {//删除作业区级
                //删除作业区
                return this.deleleAreaById(areaId);
            }
            if (areaId == null && stationId == null && factoryId != null) {//删除气矿级
                //删除气矿
                return this.deleteFactoryById(factoryId);
            }
        }
        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


    /**
     * 根据id 删除场站
     */
    public ApiResult deleteStationById(Integer stationId) {
        ApiResult apiResult;
        //查询该场站是否还存在管理人，如果有管理员 则不能删除
        int i = this.authAdminPopedomMapper.findPopCountById(stationId, 4);
        if (i > 0) {
            //该场站还有关联的账号 请先移除
            String stationName = this.authAdminPopedomMapper.findStationNameById(stationId);
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("场站：" + stationName + " 还关联有账号，请先移除账号");
            return apiResult;
        } else {
            //根据场站id删除场站
            int j = this.stationMapper.deleteByPrimaryKey(Long.valueOf(stationId));
            apiResult = ApiResult.SUCCESS();
            return apiResult;
        }
    }

    /**
     * 根据id 删除作业区
     */
    public ApiResult deleleAreaById(Integer areaId) {
        ApiResult apiResult;
        //查询该作业区下所有场站
        List<Station> stationList = this.stationMapper.findStationByAreaId(areaId);
        if (stationList != null && stationList.size() != 0) {
            //检查各个场站是否关联有账号
            for (Station station : stationList) {
                Long stationId = station.getStationId();
                //查询该场站是否还存在管理人，如果有管理员 则不能删除
                int i = this.authAdminPopedomMapper.findPopCountById(stationId.intValue(), 4);
                if (i > 0) {
                    //该场站还有关联的账号 请先移除
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("场站：" + station.getStationName() + " 还关联有账号，请先移除账号");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                } else {
                    //根据场站id删除场站
                    int j = this.stationMapper.deleteByPrimaryKey(Long.valueOf(stationId));
                }
            }
        }
        //检查作业区下是否还有账号关联
        int i = this.authAdminPopedomMapper.findPopCountById(areaId, 3);
        if (i > 0) {
            //该作业区还有关联的账号 请先移除
            String areaName = this.authAdminPopedomMapper.findAreaNameById(areaId);
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("作业区：" + areaName + " 还关联有账号，请先移除账号");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        } else {
            //根据场站id删除作业区
            int j = this.taskAreaMapper.deleteByPrimaryKey(Long.valueOf(areaId));
            apiResult = ApiResult.SUCCESS();
            return apiResult;
        }
    }

    /**
     * 根据id删除气矿
     */
    public ApiResult deleteFactoryById(Integer factoryId) {
        ApiResult apiResult;
        //根据气矿id查询所有作业区
        List<TaskArea> taskAreaList = this.taskAreaMapper.findAreaByFactoryId(factoryId);
        if (taskAreaList != null && taskAreaList.size() != 0) {
            for (TaskArea taskArea : taskAreaList) {//遍历作业区
                Long areaId = taskArea.getTaskAreaId();
                //查询该作业区下所有场站
                List<Station> stationList = this.stationMapper.findStationByAreaId(areaId.intValue());
                if (stationList != null && stationList.size() != 0) {
                    //检查各个场站是否关联有账号
                    for (Station station : stationList) {
                        Long stationId = station.getStationId();
                        //查询该场站是否还存在管理人，如果有管理员 则不能删除
                        int i = this.authAdminPopedomMapper.findPopCountById(stationId.intValue(), 4);
                        if (i > 0) {
                            //该场站还有关联的账号 请先移除
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("作业区：" + taskArea.getTaskAreaName() + " 场站：" + station.getStationName() + " 还关联有账号，请先移除账号");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        } else {
                            //根据场站id删除场站
                            int j = this.stationMapper.deleteByPrimaryKey(Long.valueOf(stationId));
                        }
                    }
                }
                //检查作业区下是否还有账号关联
                int i = this.authAdminPopedomMapper.findPopCountById(areaId.intValue(), 3);
                if (i > 0) {
                    //该作业区还有关联的账号 请先移除
                    String areaName = this.authAdminPopedomMapper.findAreaNameById(areaId.intValue());
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("作业区：" + areaName + " 还关联有账号，请先移除账号");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                } else {
                    //根据场站id删除作业区
                    int j = this.taskAreaMapper.deleteByPrimaryKey(Long.valueOf(areaId));
                }
            }
        }
        //检查气矿是否还有账号关联
        int i = this.authAdminPopedomMapper.findPopCountById(factoryId, 2);
        if (i > 0) {
            String factoryName = this.authAdminPopedomMapper.findFactoryNameById(factoryId);
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("气矿：" + factoryName + " 还关联有账号，请先移除账号");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        } else {
            //删除气矿
            this.factoryMapper.deleteByPrimaryKey(Long.valueOf(factoryId));
            apiResult = ApiResult.SUCCESS();
            return apiResult;
        }
    }


    /**
     * 新增组织机构
     *
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult addOrganization(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        /**
         * 只新增机构  不新增账号关系
         * 只能增加操作人所管理下的机构
         * 分公司级 只能添加 气矿 作业区 场站
         * 气矿级  只能添加作业区 场站
         * 作业区级 只能添加场站
         */
        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        String name = (String) map.get("name");//获取机构名称
        //type=机构类型（必传 1-气矿  2-作业区  3-场站）
        Integer type = (Integer) map.get("type");
        //工艺分类id 获取
        Integer factoryTypeId = (Integer) map.get("factoryTypeId");

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级


        if (roleId == valStationId || roleId == valStationPipelineId) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }

        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ) {//作业区级
            //只能添加场站
            if (type != 3) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是作业区级，只能添加场站");
                return apiResult;
            }
            //查询 此账号所管理的作业区id
            Long id = this.authAdminPopedomMapper.findPopIdByAdminId(adminId, 3);
            if (id == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您的账号未绑定作业区");
                return apiResult;
            }
            return this.addStation(id, factoryTypeId, name);
        }


        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ) {//气矿级
            if (type != 3 && type != 2) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是气矿级，只能添加作业区或场站");
                return apiResult;
            }
            if (type == 3) {//新增场站
                //获取前端传入作业区id
                if (areaId == null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("请在页面选择框中确定作业区");
                    return apiResult;
                }
                return this.addStation(Long.valueOf(areaId), factoryTypeId, name);
            }
            if (type == 2) {//新增作业区
                //查询 此账号所管理的气矿id
                Long id = this.authAdminPopedomMapper.findPopIdByAdminId(adminId, 2);
                if (id == null) {
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("您的账号未绑定气矿");
                    return apiResult;
                }
                return this.addTaskArea(id, name);
            }
        }


        if (roleId == valBranchId || roleId == valInstitute || roleId == valSupervisionId) {//分公司级
            //分公司 都可以添加
            if (type == 3) {//新增场站
                if (areaId == null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("请在页面选择框中确定作业区");
                    return apiResult;
                }
                return this.addStation(Long.valueOf(areaId), factoryTypeId, name);
            }
            if (type == 2) {//新增作业区
                if (factoryId == null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("请在页面选择框中确定气矿");
                    return apiResult;
                }
                return this.addTaskArea(Long.valueOf(factoryId), name);
            }
            if (type == 1) {//新增气矿
                FactoryBasicMsg factoryBasicMsg = new FactoryBasicMsg();
                factoryBasicMsg.setBranchCompanyId(1L);
                factoryBasicMsg.setFactoryName(name);
                int i = this.factoryMapper.insertSelective(factoryBasicMsg);
                if (i > 0) {
                    apiResult = ApiResult.SUCCESS();
                    return apiResult;
                } else {
                    apiResult = ApiResult.FAIL();
                    return apiResult;
                }
            }

        }
        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


    /**
     * 新增场站的方法
     */
    public ApiResult addStation(Long areaId, Integer factoryTypeId, String name) {
        ApiResult apiResult;
        //添加场站
        Station station = new Station();
        station.setTaskAreaId(areaId);//作业区id
        station.setFactoryTypeId(Long.valueOf(factoryTypeId));//工艺分类id
        station.setStationName(name);//场站名称
        int i = this.stationMapper.insertSelective(station);
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
            return apiResult;
        } else {
            apiResult = ApiResult.FAIL();
            return apiResult;
        }
    }


    /**
     * 新增作业区的方法
     */
    public ApiResult addTaskArea(Long factoryId, String name) {
        ApiResult apiResult;
        //添加作业区
        TaskArea taskArea = new TaskArea();
        taskArea.setFactoryId(factoryId);
        taskArea.setTaskAreaName(name);
        int i = this.taskAreaMapper.insertSelective(taskArea);
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
            return apiResult;
        } else {
            apiResult = ApiResult.FAIL();
            return apiResult;
        }
    }


    /**
     * 查询所有工艺分类
     *
     * @param request
     * @return
     */
    @Override
    public ApiResult getFactoryType(HttpServletRequest request) {
        List<FactoryType> list = this.authAdminPopedomMapper.findFactoryTypeList();
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }

    /**
     * 修改组织机构名称
     *
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult updateOrganization(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        String name = (String) map.get("name");//获取机构名称

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级

        if (roleId == valStationId || roleId == valStationPipelineId) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }

        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ) {//作业区级
            if (stationId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是作业区级，只能修改场站");
                return apiResult;
            }
            this.stationMapper.updateStationNameById(stationId, name);
        }


        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ) {//气矿级
            if (areaId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是气矿级，只能修改作业区或场站");
                return apiResult;
            }
            if (areaId != null && stationId != null) {//修改场站
                this.stationMapper.updateStationNameById(stationId, name);
            }
            if (areaId != null && stationId == null) {//修改作业区
                this.taskAreaMapper.updateAreaNameById(areaId, name);
            }
        }


        if (roleId == valBranchId || roleId == valInstitute || roleId == valSupervisionId) {//分公司级
            if (factoryId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能修改气矿、作业区或场站");
                return apiResult;
            }

            if (areaId != null && stationId != null) {//修改场站级
                this.stationMapper.updateStationNameById(stationId, name);
            }
            if (areaId != null && stationId == null) {//修改作业区级
                this.taskAreaMapper.updateAreaNameById(areaId, name);
            }
            if (areaId == null && stationId == null && factoryId != null) {//修改气矿级
                this.factoryMapper.updateFactoryNameById(factoryId, name);
            }
        }
        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 分页条件查询 气矿基本信息、作业区、场站和管理人信息
     *
     * @param p
     * @param request
     * @return
     */
    @Override
    public ApiResult findOrganizationById(Pager p, HttpServletRequest request) {
        ApiResult apiResult;

        Map map = p.getParameters();
        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");

        Map map1 = maps.get(0);
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级
        List<OrganInfo> info = new ArrayList<>();//返回结果
        Map map2 = new HashMap();//查询条件

        if (roleId == valStationId || roleId == valStationPipelineId) {//场站级
            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("权限不足");
            return apiResult;
        }

        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ) {//作业区级
            if (areaId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是作业区级，只能查看场站级账号");
                return apiResult;
            }
            if (stationId == null) {
                //场站id为空  根据作业区id 查询下属所有账号
                map2.put("factoryId", factoryId);
                map2.put("taskAreaId", areaId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            } else {
                //场站id 不为空  根据场站id查询账号
                map2.put("factoryId", factoryId);
                map2.put("taskAreaId", areaId);
                map2.put("stationId", stationId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
        }
        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ) {//气矿级
            if (factoryId == null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是气矿级，只能查看作业区级或场站级账号");
                return apiResult;
            }
            if (factoryId != null && areaId == null && stationId == null) {
                //查询该气矿下所有账号
                map2.put("factoryId", factoryId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
            if (factoryId != null && areaId != null && stationId == null) {
                //根据作业区id  查询所属所有账号
                map2.put("factoryId", factoryId);
                map2.put("taskAreaId", areaId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
            if (factoryId != null && areaId != null && stationId != null) {
                //查询这个场站的账号列表
                map2.put("factoryId", factoryId);
                map2.put("taskAreaId", areaId);
                map2.put("stationId", stationId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
        }
        if (roleId == valBranchId || roleId == valInstitute || roleId == valSupervisionId) {//分公司级
            if (factoryId == null) {
                //查询所有气矿下的所有账号
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
            if (factoryId != null && areaId == null && stationId == null) {
                //根据气矿id查询所有账号
                map2.put("factoryId", factoryId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
            if (factoryId != null && areaId != null && stationId == null) {
                //根据作业区id 查询所有账号
                map2.put("factoryId", factoryId);
                map2.put("taskAreaId", areaId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
            if (factoryId != null && areaId != null && stationId != null) {
                //根据场站id 查询所有账号
                map2.put("factoryId", factoryId);
                map2.put("taskAreaId", areaId);
                map2.put("stationId", stationId);
                info = this.authAdminPopedomMapper.findOrganInfo(map2);
            }
        }


        List<RespOrganInfo> list = new ArrayList<>();


        //查询的是分公司级
        if (factoryId == null && areaId == null && stationId == null) {
         list = this.pottingBc(info);
        }
        //气矿
        if (factoryId!=null && areaId==null && stationId==null){
            list = this.pottingFactory(info);
        }
        //作业区
        if (factoryId!=null && areaId!=null && stationId==null){
            list = this.pottingTaskArea(info);
        }
        //场站
        if (factoryId!=null && areaId!=null && stationId!=null){
            list = this.pottingStation(info);
        }

        Collections.sort(list);//排序

        p.setRecordTotal(list.size());

        List<RespOrganInfo> respList = new ArrayList<>();
        for (int i = p.getMinRow(); i < p.getMinRow()+p.getPageSize(); i++) {
            if (i<list.size()){
                respList.add(list.get(i));
            }
        }

        for (RespOrganInfo info1:respList){
            //查询管理员姓名
            if (info1!=null && info1.getAdminId()!=null){
                info1.setFullName(this.authAdminPopedomMapper.findFullNameById(info1.getAdminId()));
            }
            //查询工艺分类
            if (info1!=null && info1.getStationId()!=null){
                info1.setFactoryTypeName(this.authAdminPopedomMapper.findFactoryNameByStationId(info1.getStationId()));
            }
        }




        p.setContent(respList);
        apiResult = ApiResult.SUCCESS();
        apiResult.setData(p);
        return apiResult;
    }



    //封装分公司级数据
    public List<RespOrganInfo> pottingBc(List<OrganInfo> info){
        List<RespOrganInfo> list = new ArrayList<>();
        if (info != null && info.size() != 0 && info.get(0) != null) {
            //封装分公司级
            for (OrganInfo info1 : info) {
                if (info1 != null) {
                    List<AdminAndInfo> adminList1 = info1.getAdminAndInfos();//管理员集合
                    if (adminList1 != null && adminList1.size() != 0) {
                        for (AdminAndInfo admin1 : adminList1) {
                            RespOrganInfo organ1 = new RespOrganInfo();
                            organ1.setBranchCompanyId(info1.getOId());//分公司id
                            organ1.setBranchCompanyName(info1.getOName());//分公司名称
                            organ1.setAdminId(admin1.getAdminId());//分公司管理员
                            organ1.setAdminName(admin1.getAdminName());
                            list.add(organ1);
                        }
                    } else {//没有管理员
                        RespOrganInfo organ1 = new RespOrganInfo();
                        organ1.setBranchCompanyId(info1.getOId());//分公司id
                        organ1.setBranchCompanyName(info1.getOName());//分公司名称
                        list.add(organ1);
                    }

                    //气矿级
                    List<OrganInfo> info2List = info1.getOrganInfos();//获取气矿集合
                    if (info2List != null && info2List.size() != 0) {
                        for (OrganInfo info2 : info2List) {
                            if (info2 != null) {
                                List<AdminAndInfo> admin2List = info2.getAdminAndInfos();//账号集合
                                if (admin2List != null && admin2List.size() != 0) {
                                    for (AdminAndInfo admin2 : admin2List) {
                                        RespOrganInfo organ1 = new RespOrganInfo();
                                        organ1.setBranchCompanyId(info1.getOId());//分公司id
                                        organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                        organ1.setFactoryId(info2.getOId());//气矿id
                                        organ1.setFactoryName(info2.getOName());//气矿名称
                                        organ1.setAdminId(admin2.getAdminId());
                                        organ1.setAdminName(admin2.getAdminName());
                                        list.add(organ1);
                                    }
                                } else {
                                    RespOrganInfo organ1 = new RespOrganInfo();
                                    organ1.setBranchCompanyId(info1.getOId());//分公司id
                                    organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                    organ1.setFactoryId(info2.getOId());//气矿id
                                    organ1.setFactoryName(info2.getOName());//气矿名称
                                    list.add(organ1);
                                }

                                //作业区
                                List<OrganInfo> info3List = info2.getOrganInfos();//获取作业区集合
                                if (info3List != null && info3List.size() != 0) {
                                    for (OrganInfo info3 : info3List) {
                                        if (info3 != null) {
                                            List<AdminAndInfo> admin3List = info3.getAdminAndInfos();//账号集合
                                            if (admin3List != null && admin3List.size() != 0) {
                                                for (AdminAndInfo admin3 : admin3List) {
                                                    RespOrganInfo organ1 = new RespOrganInfo();
                                                    organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                    organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                    organ1.setFactoryId(info2.getOId());//气矿id
                                                    organ1.setFactoryName(info2.getOName());//气矿名称
                                                    organ1.setTaskAreaId(info3.getOId());//作业区id
                                                    organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                    organ1.setAdminId(admin3.getAdminId());
                                                    organ1.setAdminName(admin3.getAdminName());
                                                    list.add(organ1);
                                                }
                                            } else {
                                                RespOrganInfo organ1 = new RespOrganInfo();
                                                organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                organ1.setFactoryId(info2.getOId());//气矿id
                                                organ1.setFactoryName(info2.getOName());//气矿名称
                                                organ1.setTaskAreaId(info3.getOId());//作业区id
                                                organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                list.add(organ1);
                                            }

                                            //场站
                                            List<OrganInfo> info4List = info3.getOrganInfos();//获取场站集合
                                            if (info4List != null && info4List.size() != 0) {
                                                for (OrganInfo info4 : info4List) {
                                                    if (info4 != null) {
                                                        List<AdminAndInfo> admin4List = info4.getAdminAndInfos();//账号集合
                                                        if (admin4List != null && admin4List.size() != 0) {
                                                            for (AdminAndInfo admin4 : admin4List) {
                                                                RespOrganInfo organ1 = new RespOrganInfo();
                                                                organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                                organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                                organ1.setFactoryId(info2.getOId());//气矿id
                                                                organ1.setFactoryName(info2.getOName());//气矿名称
                                                                organ1.setTaskAreaId(info3.getOId());//作业区id
                                                                organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                                organ1.setStationId(info4.getOId());//场站id
                                                                organ1.setStationName(info4.getOName());//场站名称
                                                                organ1.setAdminId(admin4.getAdminId());
                                                                organ1.setAdminName(admin4.getAdminName());
                                                                list.add(organ1);
                                                            }
                                                        } else {
                                                            RespOrganInfo organ1 = new RespOrganInfo();
                                                            organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                            organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                            organ1.setFactoryId(info2.getOId());//气矿id
                                                            organ1.setFactoryName(info2.getOName());//气矿名称
                                                            organ1.setTaskAreaId(info3.getOId());//作业区id
                                                            organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                            organ1.setStationId(info4.getOId());//场站id
                                                            organ1.setStationName(info4.getOName());//场站名称
                                                            list.add(organ1);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    //封装气矿级数据
    public List<RespOrganInfo> pottingFactory(List<OrganInfo> info){
        List<RespOrganInfo> list = new ArrayList<>();
        if (info != null && info.size() != 0 && info.get(0) != null) {
            //封装分公司级
            for (OrganInfo info1 : info) {
                if (info1 != null) {

                    //气矿级
                    List<OrganInfo> info2List = info1.getOrganInfos();//获取气矿集合
                    if (info2List != null && info2List.size() != 0) {
                        for (OrganInfo info2 : info2List) {
                            if (info2 != null) {
                                List<AdminAndInfo> admin2List = info2.getAdminAndInfos();//账号集合
                                if (admin2List != null && admin2List.size() != 0) {
                                    for (AdminAndInfo admin2 : admin2List) {
                                        RespOrganInfo organ1 = new RespOrganInfo();
                                        organ1.setBranchCompanyId(info1.getOId());//分公司id
                                        organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                        organ1.setFactoryId(info2.getOId());//气矿id
                                        organ1.setFactoryName(info2.getOName());//气矿名称
                                        organ1.setAdminId(admin2.getAdminId());
                                        organ1.setAdminName(admin2.getAdminName());
                                        list.add(organ1);
                                    }
                                } else {
                                    RespOrganInfo organ1 = new RespOrganInfo();
                                    organ1.setBranchCompanyId(info1.getOId());//分公司id
                                    organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                    organ1.setFactoryId(info2.getOId());//气矿id
                                    organ1.setFactoryName(info2.getOName());//气矿名称
                                    list.add(organ1);
                                }

                                //作业区
                                List<OrganInfo> info3List = info2.getOrganInfos();//获取作业区集合
                                if (info3List != null && info3List.size() != 0) {
                                    for (OrganInfo info3 : info3List) {
                                        if (info3 != null) {
                                            List<AdminAndInfo> admin3List = info3.getAdminAndInfos();//账号集合
                                            if (admin3List != null && admin3List.size() != 0) {
                                                for (AdminAndInfo admin3 : admin3List) {
                                                    RespOrganInfo organ1 = new RespOrganInfo();
                                                    organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                    organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                    organ1.setFactoryId(info2.getOId());//气矿id
                                                    organ1.setFactoryName(info2.getOName());//气矿名称
                                                    organ1.setTaskAreaId(info3.getOId());//作业区id
                                                    organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                    organ1.setAdminId(admin3.getAdminId());
                                                    organ1.setAdminName(admin3.getAdminName());
                                                    list.add(organ1);
                                                }
                                            } else {
                                                RespOrganInfo organ1 = new RespOrganInfo();
                                                organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                organ1.setFactoryId(info2.getOId());//气矿id
                                                organ1.setFactoryName(info2.getOName());//气矿名称
                                                organ1.setTaskAreaId(info3.getOId());//作业区id
                                                organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                list.add(organ1);
                                            }

                                            //场站
                                            List<OrganInfo> info4List = info3.getOrganInfos();//获取场站集合
                                            if (info4List != null && info4List.size() != 0) {
                                                for (OrganInfo info4 : info4List) {
                                                    if (info4 != null) {
                                                        List<AdminAndInfo> admin4List = info4.getAdminAndInfos();//账号集合
                                                        if (admin4List != null && admin4List.size() != 0) {
                                                            for (AdminAndInfo admin4 : admin4List) {
                                                                RespOrganInfo organ1 = new RespOrganInfo();
                                                                organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                                organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                                organ1.setFactoryId(info2.getOId());//气矿id
                                                                organ1.setFactoryName(info2.getOName());//气矿名称
                                                                organ1.setTaskAreaId(info3.getOId());//作业区id
                                                                organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                                organ1.setAdminId(admin4.getAdminId());
                                                                organ1.setAdminName(admin4.getAdminName());
                                                                list.add(organ1);
                                                            }
                                                        } else {
                                                            RespOrganInfo organ1 = new RespOrganInfo();
                                                            organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                            organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                            organ1.setFactoryId(info2.getOId());//气矿id
                                                            organ1.setFactoryName(info2.getOName());//气矿名称
                                                            organ1.setTaskAreaId(info3.getOId());//作业区id
                                                            organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                            organ1.setStationId(info4.getOId());//场站id
                                                            organ1.setStationName(info4.getOName());//场站名称
                                                            list.add(organ1);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    //封装作业区级数据
    public List<RespOrganInfo> pottingTaskArea(List<OrganInfo> info){
        List<RespOrganInfo> list = new ArrayList<>();
        if (info != null && info.size() != 0 && info.get(0) != null) {
            //封装分公司级
            for (OrganInfo info1 : info) {
                if (info1 != null) {

                    //气矿级
                    List<OrganInfo> info2List = info1.getOrganInfos();//获取气矿集合
                    if (info2List != null && info2List.size() != 0) {
                        for (OrganInfo info2 : info2List) {
                            if (info2 != null) {

                                //作业区
                                List<OrganInfo> info3List = info2.getOrganInfos();//获取作业区集合
                                if (info3List != null && info3List.size() != 0) {
                                    for (OrganInfo info3 : info3List) {
                                        if (info3 != null) {
                                            List<AdminAndInfo> admin3List = info3.getAdminAndInfos();//账号集合
                                            if (admin3List != null && admin3List.size() != 0) {
                                                for (AdminAndInfo admin3 : admin3List) {
                                                    RespOrganInfo organ1 = new RespOrganInfo();
                                                    organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                    organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                    organ1.setFactoryId(info2.getOId());//气矿id
                                                    organ1.setFactoryName(info2.getOName());//气矿名称
                                                    organ1.setTaskAreaId(info3.getOId());//作业区id
                                                    organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                    organ1.setAdminId(admin3.getAdminId());
                                                    organ1.setAdminName(admin3.getAdminName());
                                                    list.add(organ1);
                                                }
                                            } else {
                                                RespOrganInfo organ1 = new RespOrganInfo();
                                                organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                organ1.setFactoryId(info2.getOId());//气矿id
                                                organ1.setFactoryName(info2.getOName());//气矿名称
                                                organ1.setTaskAreaId(info3.getOId());//作业区id
                                                organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                list.add(organ1);
                                            }

                                            //场站
                                            List<OrganInfo> info4List = info3.getOrganInfos();//获取场站集合
                                            if (info4List != null && info4List.size() != 0) {
                                                for (OrganInfo info4 : info4List) {
                                                    if (info4 != null) {
                                                        List<AdminAndInfo> admin4List = info4.getAdminAndInfos();//账号集合
                                                        if (admin4List != null && admin4List.size() != 0) {
                                                            for (AdminAndInfo admin4 : admin4List) {
                                                                RespOrganInfo organ1 = new RespOrganInfo();
                                                                organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                                organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                                organ1.setFactoryId(info2.getOId());//气矿id
                                                                organ1.setFactoryName(info2.getOName());//气矿名称
                                                                organ1.setTaskAreaId(info3.getOId());//作业区id
                                                                organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                                organ1.setStationId(info4.getOId());//场站id
                                                                organ1.setStationName(info4.getOName());//场站名称
                                                                organ1.setAdminId(admin4.getAdminId());
                                                                organ1.setAdminName(admin4.getAdminName());
                                                                list.add(organ1);
                                                            }
                                                        } else {
                                                            RespOrganInfo organ1 = new RespOrganInfo();
                                                            organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                            organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                            organ1.setFactoryId(info2.getOId());//气矿id
                                                            organ1.setFactoryName(info2.getOName());//气矿名称
                                                            organ1.setTaskAreaId(info3.getOId());//作业区id
                                                            organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                            organ1.setStationId(info4.getOId());//场站id
                                                            organ1.setStationName(info4.getOName());//场站名称
                                                            list.add(organ1);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    //封装场站级数据
    public List<RespOrganInfo> pottingStation(List<OrganInfo> info){
        List<RespOrganInfo> list = new ArrayList<>();
        if (info != null && info.size() != 0 && info.get(0) != null) {
            //封装分公司级
            for (OrganInfo info1 : info) {
                if (info1 != null) {

                    //气矿级
                    List<OrganInfo> info2List = info1.getOrganInfos();//获取气矿集合
                    if (info2List != null && info2List.size() != 0) {
                        for (OrganInfo info2 : info2List) {
                            if (info2 != null) {

                                //作业区
                                List<OrganInfo> info3List = info2.getOrganInfos();//获取作业区集合
                                if (info3List != null && info3List.size() != 0) {
                                    for (OrganInfo info3 : info3List) {
                                        if (info3 != null) {

                                            //场站
                                            List<OrganInfo> info4List = info3.getOrganInfos();//获取场站集合
                                            if (info4List != null && info4List.size() != 0) {
                                                for (OrganInfo info4 : info4List) {
                                                    if (info4 != null) {
                                                        List<AdminAndInfo> admin4List = info4.getAdminAndInfos();//账号集合
                                                        if (admin4List != null && admin4List.size() != 0) {
                                                            for (AdminAndInfo admin4 : admin4List) {
                                                                RespOrganInfo organ1 = new RespOrganInfo();
                                                                organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                                organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                                organ1.setFactoryId(info2.getOId());//气矿id
                                                                organ1.setFactoryName(info2.getOName());//气矿名称
                                                                organ1.setTaskAreaId(info3.getOId());//作业区id
                                                                organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                                organ1.setStationId(info4.getOId());//场站id
                                                                organ1.setStationName(info4.getOName());//场站名称
                                                                organ1.setAdminId(admin4.getAdminId());
                                                                organ1.setAdminName(admin4.getAdminName());
                                                                list.add(organ1);
                                                            }
                                                        } else {
                                                            RespOrganInfo organ1 = new RespOrganInfo();
                                                            organ1.setBranchCompanyId(info1.getOId());//分公司id
                                                            organ1.setBranchCompanyName(info1.getOName());//分公司名称
                                                            organ1.setFactoryId(info2.getOId());//气矿id
                                                            organ1.setFactoryName(info2.getOName());//气矿名称
                                                            organ1.setTaskAreaId(info3.getOId());//作业区id
                                                            organ1.setTaskAreaName(info3.getOName());//作业区名称
                                                            organ1.setStationId(info4.getOId());//场站id
                                                            organ1.setStationName(info4.getOName());//场站名称
                                                            list.add(organ1);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
}