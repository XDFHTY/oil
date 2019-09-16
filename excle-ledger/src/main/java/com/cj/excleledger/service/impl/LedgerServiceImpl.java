package com.cj.excleledger.service.impl;

import com.cj.common.domain.ExpotrExcle;
import com.cj.common.entity.EnvironmentGrade;
import com.cj.common.entity.ExcleTableStructure;
import com.cj.common.entity.TableCellRecord;
import com.cj.core.domain.ApiResult;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.excleledger.mapper.LedgerMapper;
import com.cj.excleledger.service.LedgerService;
import com.cj.excleledger.util.LedgerUtil;
import com.cj.exclepublic.domain.AddTableCellRecordsReq;
import com.cj.exclepublic.domain.FindExcleStruByStIdAndTabNameResp;
import com.cj.exclepublic.domain.UpdateTableCellRecordsReq;
import com.cj.exclepublic.service.ExcleService;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 台账模块
 * Created by XD on 2018/10/17.
 */
@Service
@Transactional
public class LedgerServiceImpl implements LedgerService {




    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private ExcleService excleService;


    /**
     * 生成台账
     * 根据场站、年份和等级  拉取表结构及表数据 部分数据需调用
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult generateLedger(Map map,Boolean isExcel, HttpServletRequest request) {
        ApiResult apiResult;
        List<Integer> list = (List<Integer>) map.get("stationIdList");//获取场站id
        if (list == null || list.size() == 0){
            apiResult = ApiResult.SUCCESS();
            return apiResult;
        }
        Integer grade = (Integer) map.get("grade");//获取风险等级（1-较大 2-重大）
        String br;//换行符
        if (isExcel){
            br = "\n";
        }else {
            br = "</br>";
        }


        String year = (String)map.get("year");
        List<FindExcleStruByStIdAndTabNameResp> list1 = new ArrayList<>();//返回对象
        for (Integer stationId:list){
            int i = 1;//风险物质 序号
            //根据场站id 年份 查询台账表结构以及内容 以及环境等级、作业区、气矿名称
            //根据场站id 查询作业区名称 场站名称 气矿名称
            Map map2 = this.ledgerMapper.findStationName(stationId);

            //查询工艺分类 根据工艺分类决定表名  表结构不一致
            String factoryTypeName = this.ledgerMapper.findFactoryTypeNameById(stationId);
            //根据场站id 和年份 调用 风险物质和数量(Q水) 风险物质和数量(Q气)表结构及内容
            StringBuffer sb1 = new StringBuffer();//风险物质
            StringBuffer sb2 = new StringBuffer();//最大储存量/t
            if ("含硫化氢场站".equals(factoryTypeName) || "含油水场站".equals(factoryTypeName)
                    || "轻烃厂".equals(factoryTypeName) || "净化厂".equals(factoryTypeName)
                    || "固体废弃物堆存站".equals(factoryTypeName) || "其他".equals(factoryTypeName))
            {//这些分类的表 物质名称在1,2列 最大储存量在第四列 需要调用 风险物质和数量(Q水) 风险物质和数量(Q气)
                ApiResult apiResult2 = excleService.findExcleStruByStIdAndTabName(
                        Long.valueOf(stationId), "Q水",year,request);

                if (apiResult2 != null){
                    Map map1 = LedgerUtil.getNameAndNum1(apiResult2,br,i);
                    List<StringBuffer> list2 = (List<StringBuffer>) map1.get("list");
                    i = (int) map1.get("int");//续上 风险物质 序号
                    sb1.append(list2.get(0));
                    sb2.append(list2.get(1));
                }
                ApiResult apiResult3 = excleService.findExcleStruByStIdAndTabName(
                        Long.valueOf(stationId), "Q气",year,request);
                if (apiResult3 != null){
                    Map map1 = LedgerUtil.getNameAndNum1(apiResult3,br,i);
                    List<StringBuffer> list2 = (List<StringBuffer>) map1.get("list");
                    i = (int) map1.get("int");//续上 风险物质 序号
                    sb1.append(list2.get(0));
                    sb2.append(list2.get(1));
                }
            }else {//其余表 物质名称在1列，最大储存量在第三列 风险物质和数量
                ApiResult apiResult2 = excleService.findExcleStruByStIdAndTabName(
                        Long.valueOf(stationId), "风险物质和数量",year,request);
                if (apiResult2 != null){
                    Map map1 = LedgerUtil.getNameAndNum2(apiResult2,br,i);
                    List<StringBuffer> list2 = (List<StringBuffer>) map1.get("list");
                    i = (int) map1.get("int");//续上 风险物质 序号
                    sb1.append(list2.get(0));
                    sb2.append(list2.get(1));
                }
            }

            StringBuffer sb3 = new StringBuffer();//现有环境风险防控措施
            StringBuffer sb4 = new StringBuffer();//风险防控措施计划 (拟整改)
            StringBuffer sb5 = new StringBuffer();//责任单位
            StringBuffer sb6 = new StringBuffer();//责任人
            StringBuffer sb7 = new StringBuffer();//整改措施计划完成时间


            //根据场站id和年份查询本年度 环境风险防控措施表
            List<Map> maps = this.ledgerMapper.findMeasuresByIdAndYear(stationId,year);
            if (maps!=null){
                for (Map m:maps){
                    sb3.append((String) m.get("existing_measures"));//现有环境风险防控措施
                    sb3.append(br);
                    sb4.append((String) m.get("proposed_rectification"));//风险防控措施计划 (拟整改)
                    sb4.append(br);
                    sb5.append((String) m.get("department"));//责任单位
                    sb5.append(br);
                    sb6.append((String) m.get("person_liable"));//责任人
                    sb6.append(br);
                    if ((Date) m.get("planned_time")==null){
                        sb7.append("");
                    }else {
                        sb7.append((Date) m.get("planned_time"));//整改措施计划完成时间
                    }
                    sb7.append(br);
                }
            }


            //根据场站id 年份 查询 最终风险等级
            EnvironmentGrade environmentGrade = this.ledgerMapper.findRiskGrade("环境风险",stationId,year);//获取最终风险等级
            if (environmentGrade != null){
                //调用重大的表格
                if ("重大".equals(environmentGrade.getResult())){
                    if (grade == 2){//前端提交的是重大才查询
                        ApiResult apiResult1 = excleService.findExcleStruByStIdAndTabName(Long.valueOf(stationId), "环境风险台账(重大)",year,request);
                        if (apiResult1!=null){
                            FindExcleStruByStIdAndTabNameResp info1 = (FindExcleStruByStIdAndTabNameResp) apiResult1.getData();
                            if (info1!=null){
                                info1.setStationId(Long.valueOf(stationId));
                                List<ExcleTableStructure> structureList = info1.getExcleTableStructures();//获取表结构
                                if (structureList != null && structureList.size()!=0){
                                    for (ExcleTableStructure structure:structureList){
                                        String cellMsg = structure.getCellMsg();//获取表结构内容
                                        if ("调用气矿名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("factory_name"));
                                            continue;//处理完后 进行下一个单元格
                                        }
                                        if ("调用作业区名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("task_area_name"));
                                            continue;
                                        }
                                        if ("调用场站名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("station_name"));
                                            continue;
                                        }

                                        if ("调用风险物质名称".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb1));
                                            System.out.println(sb1);
                                            continue;
                                        }
                                        if ("调用风险物质最大储存量".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb2));
                                            System.out.println(sb2);
                                            continue;
                                        }
                                        if ("调用现有环境风险防控措施".equals(cellMsg)){
                                            structure.setCellMsg( String.valueOf(sb3));
                                            System.out.println(sb3);
                                            continue;
                                        }
                                        if ("调用大气环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("大气风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用水环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("水环境风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用最终环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("环境风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用风险防控措施计划".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb4));
                                            continue;
                                        }
                                        if ("调用责任单位".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb5));
                                            continue;
                                        }
                                        if ("调用责任人".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb6));
                                            continue;
                                        }
                                    }
                                }
                                list1.add(info1);//map中包含 表内容和处理好的表结构  添加到list中 进入下一个场站的处理
                            }
                        }
                    }
                }

                //调用较大的表格
                if ("较大".equals(environmentGrade.getResult())){
                    if (grade == 1){//前端提交的是较大或一般才查询
                        ApiResult apiResult1 = excleService.findExcleStruByStIdAndTabName(
                                Long.valueOf(stationId), "环境风险台账(较大或一般)",(String)map.get("year"),request);
                        if (apiResult1!=null){
                           FindExcleStruByStIdAndTabNameResp info1 = (FindExcleStruByStIdAndTabNameResp) apiResult1.getData();
                            if (info1!=null){
                                info1.setStationId(Long.valueOf(stationId));
                                List<ExcleTableStructure> structureList = info1.getExcleTableStructures();//获取表结构
                                if (structureList != null && structureList.size()!=0){
                                    for (ExcleTableStructure structure:structureList){
                                        String cellMsg = structure.getCellMsg();//获取表结构内容
                                        if ("调用气矿名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("factory_name"));
                                            continue;//处理完后 进行下一个单元格
                                        }
                                        if ("调用作业区名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("task_area_name"));
                                            continue;
                                        }
                                        if ("调用场站名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("station_name"));
                                            continue;
                                        }
                                        if ("调用大气环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("大气风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用水环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("水环境风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用最终环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("环境风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用水环境风险受体敏感程度".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E水",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用水环境风险受体敏感性描述".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E水",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResultDescribe());//获取描述
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用大气环境风险受体敏感程度".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E气",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用大气环境风险受体敏感性描述".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E气",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResultDescribe());//获取描述
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }

                                        if ("调用现有风险防控措施情况".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb3));
                                            System.out.println(sb3);
                                            continue;
                                        }
                                        if ("调用拟整改的风险防控措施".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb4));
                                            System.out.println(sb4);
                                            continue;
                                        }
                                        if ("调用整改措施计划完成时间".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb7));
                                            System.out.println(sb7);
                                            continue;
                                        }
                                    }
                                }
                                list1.add(info1);//map中包含 表内容和处理好的表结构  添加到list中 进入下一个场站的处理
                            }
                        }
                    }
                }


                //调用较大的表格
                if ("一般".equals(environmentGrade.getResult()) ){
                    if (grade == 0){//前端提交的是较大或一般才查询
                        ApiResult apiResult1 = excleService.findExcleStruByStIdAndTabName(Long.valueOf(stationId), "环境风险台账(较大或一般)",(String)map.get("year"),request);
                        if (apiResult1!=null){
                            FindExcleStruByStIdAndTabNameResp info1 = (FindExcleStruByStIdAndTabNameResp) apiResult1.getData();
                            if (info1!=null){
                                info1.setStationId(Long.valueOf(stationId));
                                List<ExcleTableStructure> structureList = info1.getExcleTableStructures();//获取表结构
                                if (structureList != null && structureList.size()!=0){
                                    for (ExcleTableStructure structure:structureList){
                                        String cellMsg = structure.getCellMsg();//获取表结构内容
                                        if ("调用气矿名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("factory_name"));
                                            continue;//处理完后 进行下一个单元格
                                        }
                                        if ("调用作业区名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("task_area_name"));
                                            continue;
                                        }
                                        if ("调用场站名称".equals(cellMsg)){
                                            structure.setCellMsg((String) map2.get("station_name"));
                                            continue;
                                        }
                                        if ("调用大气环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("大气风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用水环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("水环境风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用最终环境风险等级".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("环境风险",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用水环境风险受体敏感程度".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E水",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用水环境风险受体敏感性描述".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E水",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResultDescribe());//获取描述
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用大气环境风险受体敏感程度".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E气",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResult());
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }
                                        if ("调用大气环境风险受体敏感性描述".equals(cellMsg)){
                                            EnvironmentGrade grade1 = this.ledgerMapper.findRiskGrade("E气",stationId,year);
                                            if (grade1 != null) {
                                                structure.setCellMsg(grade1.getResultDescribe());//获取描述
                                            }else {
                                                structure.setCellMsg("");
                                            }
                                            continue;
                                        }

                                        if ("调用现有风险防控措施情况".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb3));
                                            System.out.println(sb3);
                                            continue;
                                        }
                                        if ("调用拟整改的风险防控措施".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb4));
                                            System.out.println(sb4);
                                            continue;
                                        }
                                        if ("调用整改措施计划完成时间".equals(cellMsg)){
                                            structure.setCellMsg(String.valueOf(sb7));
                                            System.out.println(sb7);
                                            continue;
                                        }
                                    }
                                }
                                list1.add(info1);//map中包含 表内容和处理好的表结构  添加到list中 进入下一个场站的处理
                            }
                        }
                    }
                }




            }

        }
        List<FindExcleStruByStIdAndTabNameResp> list2 = new ArrayList<>();//排序后的返回对象

        if (list1!=null && list1.size()!=0){

            //首先把 update = false的 放在集合前面
            for (FindExcleStruByStIdAndTabNameResp info:list1){
                if (info != null){
                    if (!info.getUpdate()){
                        list2.add(info);;
                    }
                }
            }

            //再把update = true的 放在集合后面
            for (FindExcleStruByStIdAndTabNameResp info:list1){
                if (info != null){
                    if (info.getUpdate()){
                        list2.add(info);;
                    }
                }
            }

        }



        apiResult = ApiResult.SUCCESS();
        apiResult.setData(list2);
        return apiResult;
    }

    /**
     * 导出台账
     *
     * @param map
     * @param response
     * @param request
     * @return
     */
    @Override
    public ApiResult ledgerExport(Map map, HttpServletResponse response, HttpServletRequest request) {
        ApiResult apiResult;
        List<ExcleTableStructure> structureList = new ArrayList<>();//拼接各个场站的集合
        //查询表结构及数据
        ApiResult apiResult1 = generateLedger(map,true, request);
        if (apiResult1!=null){
            List<FindExcleStruByStIdAndTabNameResp> list = (List<FindExcleStruByStIdAndTabNameResp>) apiResult1.getData();
            if (list != null && list.size()!=0){
                for (int i=0;i<list.size();i++){
                    FindExcleStruByStIdAndTabNameResp info1 = list.get(i);
                    if (info1!=null){
                        //表数据
                        List<TableCellRecord> tableCellRecords =info1.getTableCellRecords();
                        //表结构
                        List<ExcleTableStructure> excleTableStructures = info1.getExcleTableStructures();
                        //数据整合
                        Set set = new HashSet();
                        for (ExcleTableStructure excleTableStructure : excleTableStructures){
                            set.add(excleTableStructure.getStartRow());
                            for (TableCellRecord tableCellRecord : tableCellRecords){
                                if (tableCellRecord != null){
                                    if (excleTableStructure.getExcleTableStructureId() == tableCellRecord.getExcleTableStructureId()){
                                        excleTableStructure.setCellMsg(tableCellRecord.getCellMsg());
                                    }
                                }
                            }
                        }
                        if (i==0){//第一个集合 全部set进新的集合
                            structureList = excleTableStructures;
                        }else {//后面的集合  取出表头 行号+i 添加至集合
                            Integer grade = (Integer) map.get("grade");//风险等级（1-一般及较大 2-重大）
                            if (grade == 2){//排除前三行表头
                                for (ExcleTableStructure excleTableStructure : excleTableStructures){
                                    if (excleTableStructure.getStartRow()!=0 && excleTableStructure.getStartRow()!=1
                                            && excleTableStructure.getStartRow()!=2){//排除前三行表头
                                        excleTableStructure.setStartRow(excleTableStructure.getStartRow()+i);//行号+i
                                        excleTableStructure.setEndRow(excleTableStructure.getEndRow()+i);//行号+i
                                        structureList.add(excleTableStructure);//添加至集合
                                    }
                                }
                            }

                            if (grade == 1 || grade == 0){//排除前四行表头
                                for (ExcleTableStructure excleTableStructure : excleTableStructures){
                                    if (excleTableStructure.getStartRow()!=0 && excleTableStructure.getStartRow()!=1
                                            && excleTableStructure.getStartRow()!=2 && excleTableStructure.getStartRow()!=3){//排除前四行表头
                                        excleTableStructure.setStartRow(excleTableStructure.getStartRow()+i);//行号+i
                                        excleTableStructure.setEndRow(excleTableStructure.getEndRow()+i);//行号+i
                                        structureList.add(excleTableStructure);//添加至集合
                                    }
                                }
                            }
                        }
                    }
                }

                List<List<ExpotrExcle>> excles = new ArrayList<>();
                for (int i = 0; i <structureList.size() ; i++) {
                    List<ExpotrExcle> expotrExcles = new ArrayList<>();

                    for (ExcleTableStructure excleTableStructure : structureList){
                        if (excleTableStructure.getStartRow()==i){
                            ExpotrExcle expotrExcle = new ExpotrExcle();
                            expotrExcle.setFirstRow(excleTableStructure.getStartRow());
                            expotrExcle.setLastRow(excleTableStructure.getEndRow());
                            expotrExcle.setFirstCol(excleTableStructure.getStartCol());
                            expotrExcle.setLastCol(excleTableStructure.getEndCol());
                            expotrExcle.setMsg(excleTableStructure.getCellMsg());
                            expotrExcles.add(expotrExcle);
                        }
                    }
                    excles.add(expotrExcles);
                }
                System.out.println(excles);
                try {
                    List<List<List<ExpotrExcle>>> excless = new ArrayList<>();
                    excless.add(excles);


                    List<List<List<ExpotrExcle>>> excleDatass = new ArrayList<>();
                    List<List<ExpotrExcle>> excleDatas = new ArrayList<>();
                    excleDatass.add(excleDatas);

                    String[] sheetNames = {(String) map.get("tableName")};
                    exportExcelUtil.exportExcel3(sheetNames,excless,excleDatass,response,"台账导出");
                    apiResult = ApiResult.SUCCESS();
                    return apiResult;
                }catch (Exception e){
                    e.printStackTrace();
                    apiResult = ApiResult.FAIL();
                    return apiResult;
                }

            }
        }
        apiResult = ApiResult.SUCCESS();
        apiResult.setMsg("数据为空");
        return apiResult;
    }


    @Override
    public ApiResult updateLedgerData(List<AddTableCellRecordsReq> addTableCellRecordsReqs, HttpServletRequest request) {
        ApiResult apiResult;

        if (addTableCellRecordsReqs!=null){
            for (AddTableCellRecordsReq info:addTableCellRecordsReqs) {
                if ("环境风险台账(一般)".equals(info.getTableName()) || "环境风险台账(较大)".equals(info.getTableName()) ){
                    info.setTableName("环境风险台账(较大或一般)");
                }
            }
        }

        try {
            if(addTableCellRecordsReqs != null && addTableCellRecordsReqs.size()!=0){
                for (AddTableCellRecordsReq info:addTableCellRecordsReqs){
                    Gson gson = new Gson();
                    ApiResult apiResult1 = excleService.findTableRecord(gson.toJson(info));

                    if (apiResult1.getData() != null && !"".equals(apiResult1.getData())) { //调修改
                        UpdateTableCellRecordsReq updateData = new UpdateTableCellRecordsReq();//修改对象
                        BeanUtils.copyProperties(info, updateData);

                        ApiResult apiResult2 = excleService.updateTableCellRecords(updateData, request);
                        if (apiResult2.getCode() == 1000) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult2;
                        }
                    }else {//调新增
                        ApiResult apiResult2 = excleService.addTableCellRecords(info, request);
                        if (apiResult2.getCode() == 1000){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult2;
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            apiResult = ApiResult.FAIL();
            apiResult.setMsg(e.getMessage());
            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }
}
