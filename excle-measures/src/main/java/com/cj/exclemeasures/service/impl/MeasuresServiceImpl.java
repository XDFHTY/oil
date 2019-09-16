package com.cj.exclemeasures.service.impl;

import com.cj.common.domain.ExpotrExcle;
import com.cj.common.entity.*;
import com.cj.common.exception.UserException;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.common.utils.excle.ImportExeclUtil;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.exclemeasures.domain.FindMeasuresByLastYearResp;
import com.cj.exclemeasures.domain.FindMeasuresByThisYearResp;
import com.cj.exclemeasures.domain.UpdateMeasuresByLastYearReq;
import com.cj.exclemeasures.domain.UpdateMeasuresByThisYearReq;
import com.cj.exclemeasures.mapper.MeasuresLastMapper;
import com.cj.exclemeasures.mapper.MeasuresMapper;
import com.cj.exclemeasures.service.MeasuresService;
import com.cj.exclepublic.domain.FindOrganizationResp;
import com.cj.exclepublic.mapper.TableRecordMapper;
import com.cj.exclepublic.service.ExcleService;
import com.cj.exclepublic.service.PowerService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class MeasuresServiceImpl implements MeasuresService {

    @Resource
    private MeasuresMapper measuresMapper;

    @Resource
    private MeasuresLastMapper measuresLastMapper;

    @Resource
    private TableRecordMapper tableRecordMapper;

    @Resource
    private PowerService powerService;

    @Resource
    private ExcleService excleService;

    @Override
    public ApiResult findMeasuresByThisYear(Pager pager,HttpSession session) {
        List<Map> roles = (List<Map>) session.getAttribute("roles");
        Map map = pager.getParameters();
        map.put("tableType","2");

        String year = (String) map.get("year");

        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);
        FindMeasuresByThisYearResp findMeasuresByThisYearResp = new FindMeasuresByThisYearResp();
        //校验是否可编辑
        Boolean b = powerService.checkUpdatePower(tableRecord==null?"0":tableRecord.getCheckType(), (String) roles.get(0).get("roleDescriptionName"));

        if (tableRecord != null){
//            //年份匹配不上
//            if (!year.equals((new Date().getYear()+1900)+"")){
//                b = false;
//            }
            List<List<?>> lists = measuresMapper.findMeasuresByThisYear(pager);

            //结果集处理
            List<Measures> list0 = (List<Measures>) lists.get(0);
            List<Map> list1 = (List<Map>) lists.get(1);

            Long total = (Long) (list1.get(0).get("total"));
            //总条数
            pager.setRecordTotal(total.intValue());
            //结果集
            findMeasuresByThisYearResp.setMeasures(list0);

        }else {
            findMeasuresByThisYearResp.setMeasures(new ArrayList<>());
        }
        findMeasuresByThisYearResp.setUpdate(b);
        pager.setContent(findMeasuresByThisYearResp);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(pager);

        return apiResult;
    }

    @Override
    public ApiResult findMeasuresByLastYear(Pager pager,HttpSession session) {

        List<Map> roles = (List<Map>) session.getAttribute("roles");
        Map map = pager.getParameters();
        map.put("tableType","3");

        String year = (String) map.get("year");
        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);

        FindMeasuresByLastYearResp findMeasuresByLastYearResp = new FindMeasuresByLastYearResp();
        //校验是否可编辑
        Boolean b = powerService.checkUpdatePower(tableRecord==null?"0":tableRecord.getCheckType(), (String) roles.get(0).get("roleDescriptionName"));
        if (tableRecord != null){


//            //年份匹配不上
//            if (!year.equals((new Date().getYear()+1900-1)+"")){
//                b = false;
//            }


            List<List<?>> lists = measuresLastMapper.findMeasuresByLastYear(pager);

            //结果集处理
            List<MeasuresLast> list0 = (List<MeasuresLast>) lists.get(0);
            List<Map> list1 = (List<Map>) lists.get(1);

            Long total = (Long) (list1.get(0).get("total"));
            //总条数
            pager.setRecordTotal(total.intValue());
            //结果集
            findMeasuresByLastYearResp.setMeasuresLasts(list0);
        }else {
            findMeasuresByLastYearResp.setMeasuresLasts(new ArrayList<>());
        }
        findMeasuresByLastYearResp.setUpdate(b);


        pager.setContent(findMeasuresByLastYearResp);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(pager);

        return apiResult;

    }

    @Override
    public ApiResult addMeasuresByThisYear(List<Measures> measures, HttpServletRequest request) {

        ApiResult apiResult = null;
        if (measures.size()==0){
            ApiResult apiResult1 = ApiResult.FAIL();
            apiResult1.setMsg("请先填写 环境风险防控与应急措施 内容再保存");
            return apiResult1;
        }
        HttpSession session = request.getSession();


        long adminId = (long) session.getAttribute("id");

        Date date = new Date();
        for (Measures measures1 : measures){
            measures1.setFounderId(adminId);
            measures1.setCreateTime(date);
        }
        //检查表列表信息
        Date year = measures.get(0).getYear();
        long stationId = measures.get(0).getStationId();
        String tableType = "2";
        Map map = new HashMap();
        map.put("year",year.getYear()+1900+"");
        map.put("stationId",stationId);
        map.put("tableType",tableType);

        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);
        if (tableRecord != null){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("每个场站 每年 只能新增一张 本年度环境风险防控与应急措施记录 表");
            return apiResult;
        }else {
            //新增表列表信息
            tableRecord = new TableRecord();
            tableRecord.setStationId(stationId);
            tableRecord.setTableType("2");
            tableRecord.setYear(year);
            tableRecord.setFounderId(adminId);
            tableRecord.setCreateTime(new Date());
            tableRecord.setCheckType("3");

            tableRecordMapper.insertSelective(tableRecord);

        }



        int i = measuresMapper.addMeasuresByThisYear(measures);

        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(ImportExeclUtil.DateUtil.dateToStr(year, ImportExeclUtil.DateUtil.YYYY));
        logCheck.setCheckMsg("填写 本年度环境风险防控与应急措施记录");

        int n = excleService.addLogCheck(logCheck,request);


        if (i > 0){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }



        return apiResult;
    }

    @Override
    public ApiResult addMeasuresByLastYear( List<MeasuresLast> measuresLasts, HttpServletRequest request) {
        ApiResult apiResult = null;
        if (measuresLasts.size()==0){
            ApiResult apiResult1 = ApiResult.FAIL();
            apiResult1.setMsg("请先填写 环境风险防控与应急措施 内容再保存");
            return apiResult1;
        }
        HttpSession session = request.getSession();
        Calendar c = Calendar.getInstance();

        long adminId = (long) session.getAttribute("id");

        Date date = new Date();
        for (MeasuresLast measuresLast : measuresLasts){
            measuresLast.setFounderId(adminId);
            measuresLast.setCreateTime(date);
        }
        //检查表列表信息
        Date year = measuresLasts.get(0).getYear();
        long stationId = measuresLasts.get(0).getStationId();
        String tableType = "3";
        Map map = new HashMap();
        map.put("year",year.getYear()+1900+"");
        map.put("stationId",stationId);
        map.put("tableType",tableType);

        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);
        if (tableRecord != null){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("每个场站 每年 只能新增一张 上年环境风险防控与应急措施记录 表");
            return apiResult;
        }else {
            //处理年份
            c.setTime(year);
            c.add(Calendar.YEAR,-1);
            //新增表列表信息
            tableRecord = new TableRecord();
            tableRecord.setStationId(stationId);
            tableRecord.setTableType("3");
            tableRecord.setYear(year);
            tableRecord.setFounderId(adminId);
            tableRecord.setCreateTime(new Date());
            tableRecord.setCheckType("3");

            tableRecordMapper.insertSelective(tableRecord);

        }

        int i = measuresLastMapper.addMeasuresByLastYear(measuresLasts);

        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(ImportExeclUtil.DateUtil.dateToStr(year, ImportExeclUtil.DateUtil.YYYY));
        logCheck.setCheckMsg("填写 上年度环境风险防控与应急措施记录");

        int n = excleService.addLogCheck(logCheck,request);


        if (i > 0){
            apiResult = ApiResult.SUCCESS();
        }else {
            apiResult = ApiResult.FAIL();
        }



        return apiResult;
    }

    @Override
    public ApiResult updateMeasuresByThisYear(UpdateMeasuresByThisYearReq updateMeasuresByThisYearReq, HttpServletRequest request) {
        ApiResult apiResult = null;
        HttpSession session = request.getSession();

        long adminId = (long) session.getAttribute("id");
        List<Map> roles = (List<Map>) session.getAttribute("roles");

        List<Measures> oldMeasures = updateMeasuresByThisYearReq.getOldMeasures();
        List<Measures> newMeasures = updateMeasuresByThisYearReq.getNewMeasures();
        //检查表列表信息
        String year = updateMeasuresByThisYearReq.getYear();
        long stationId = updateMeasuresByThisYearReq.getStationId();
        String tableType = "2";
        Map map1 = new HashMap();
        map1.put("year",year);
        map1.put("stationId",stationId);
        map1.put("tableType",tableType);

        TableRecord tableRecord = tableRecordMapper.findTableRecord(map1);
        //校验是否可编辑
        Boolean b = powerService.checkUpdatePower(tableRecord.getCheckType(), (String) roles.get(0).get("roleDescriptionName"));
        if (!b){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("此表格已提交审核，当前不可修改内容");
        }



        int i = 0;
        if (oldMeasures.size()>0){
            for (Measures measures : oldMeasures){
                measures.setOperatorId(adminId);
            }

            i = measuresMapper.updateMeasuresByThisYear(oldMeasures);
        }


        int j = 0;
        if (newMeasures.size()>0){
            Date date = new Date();

            for (Measures measures : newMeasures){
                measures.setFounderId(adminId);
                measures.setCreateTime(date);
            }
            j = measuresMapper.addMeasuresByThisYear(newMeasures);
        }

        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(year);
        logCheck.setCheckMsg("修改 本年度环境风险防控与应急措施记录");

        int n = excleService.addLogCheck(logCheck,request);


        if (i>0 | j>0){
            apiResult = ApiResult.SUCCESS();;
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }

    @Override
    public ApiResult updateMeasuresByLastYear(UpdateMeasuresByLastYearReq updateMeasuresByLastYearReq, HttpServletRequest request) {
        ApiResult apiResult = null;
        HttpSession session = request.getSession();

        long adminId = (long) session.getAttribute("id");
        List<Map> roles = (List<Map>) session.getAttribute("roles");

        List<MeasuresLast> oldMeasuresLast = updateMeasuresByLastYearReq.getOldMeasuresLasts();
        List<MeasuresLast> newMeasuresLast = updateMeasuresByLastYearReq.getNewMeasuresLasts();
        //检查表列表信息
        String year = updateMeasuresByLastYearReq.getYear();
        long stationId = updateMeasuresByLastYearReq.getStationId();
        String tableType = "3";
        Map map1 = new HashMap();
        map1.put("year",year);
        map1.put("stationId",stationId);
        map1.put("tableType",tableType);

        TableRecord tableRecord = tableRecordMapper.findTableRecord(map1);
        //校验是否可编辑
        //校验是否可编辑
        Boolean b = powerService.checkUpdatePower(tableRecord.getCheckType(), (String) roles.get(0).get("roleDescriptionName"));
        if (!b){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("此表格已提交审核，当前不可修改内容");
        }

        int i = 0;
        if (oldMeasuresLast.size()>0){
            for (MeasuresLast measuresLast : oldMeasuresLast){
                measuresLast.setOperatorId(adminId);
            }

            i = measuresLastMapper.updateMeasuresByLastYear(oldMeasuresLast);
        }


        int j = 0;
        if (newMeasuresLast.size()>0){
            Date date = new Date();

            for (MeasuresLast measuresLast : newMeasuresLast){
                measuresLast.setFounderId(adminId);
                measuresLast.setCreateTime(date);
            }
            j = measuresLastMapper.addMeasuresByLastYear(newMeasuresLast);
        }

        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(year);
        logCheck.setCheckMsg("修改 上年度环境风险防控与应急措施记录");

        int n = excleService.addLogCheck(logCheck,request);


        if (i>0 | j>0){
            apiResult = ApiResult.SUCCESS();;
        }else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }

    @Override
    public void exportMeasures(Pager pager, HttpServletResponse response) throws Exception {
        String year = (String) pager.getParameters().get("year");
        String oleYear = ImportExeclUtil.DateUtil.strToDate(year, ImportExeclUtil.DateUtil.YYYY).getYear()+1900-1+"";

        //查询本年数据
        List<List<?>> lists1 = measuresMapper.findMeasuresByThisYear(pager);
        List<Measures> measures = (List<Measures>) lists1.get(0);

        pager.getParameters().put("year",oleYear);

        //查询上年度数据
        List<List<?>> lists2 = measuresLastMapper.findMeasuresByLastYear(pager);
        List<MeasuresLast> measuresLasts = (List<MeasuresLast>) lists2.get(0);

        //sheet1数据处理
        //sheet1表头
        List<List<ExpotrExcle>> excles1 = new ArrayList<>();
        List<ExpotrExcle> expotrExcles0 = new ArrayList<>();

        ExpotrExcle expotrExcle01 = new ExpotrExcle();
        expotrExcle01.setFirstRow(0);
        expotrExcle01.setLastRow(0);
        expotrExcle01.setFirstCol(0);
        expotrExcle01.setLastCol(7);
        expotrExcle01.setMsg("本年度环境风险防控措施完善实施计划");
        ExpotrExcle expotrExcle02 = new ExpotrExcle();
        expotrExcle02.setFirstRow(0);
        expotrExcle02.setLastRow(0);
        expotrExcle02.setFirstCol(1);
        expotrExcle02.setLastCol(1);
        expotrExcle02.setMsg("");
        ExpotrExcle expotrExcle03 = new ExpotrExcle();
        expotrExcle03.setFirstRow(0);
        expotrExcle03.setLastRow(0);
        expotrExcle03.setFirstCol(2);
        expotrExcle03.setLastCol(2);
        expotrExcle03.setMsg("");
        ExpotrExcle expotrExcle04 = new ExpotrExcle();
        expotrExcle04.setFirstRow(0);
        expotrExcle04.setLastRow(0);
        expotrExcle04.setFirstCol(3);
        expotrExcle04.setLastCol(3);
        expotrExcle04.setMsg("");
        ExpotrExcle expotrExcle05 = new ExpotrExcle();
        expotrExcle05.setFirstRow(0);
        expotrExcle05.setLastRow(0);
        expotrExcle05.setFirstCol(4);
        expotrExcle05.setLastCol(4);
        expotrExcle05.setMsg("");
        ExpotrExcle expotrExcle06 = new ExpotrExcle();
        expotrExcle06.setFirstRow(0);
        expotrExcle06.setLastRow(0);
        expotrExcle06.setFirstCol(5);
        expotrExcle06.setLastCol(5);
        expotrExcle06.setMsg("");
        ExpotrExcle expotrExcle07 = new ExpotrExcle();
        expotrExcle07.setFirstRow(0);
        expotrExcle07.setLastRow(0);
        expotrExcle07.setFirstCol(6);
        expotrExcle07.setLastCol(6);
        expotrExcle07.setMsg("");
        ExpotrExcle expotrExcle08 = new ExpotrExcle();
        expotrExcle08.setFirstRow(0);
        expotrExcle08.setLastRow(0);
        expotrExcle08.setFirstCol(7);
        expotrExcle08.setLastCol(7);
        expotrExcle08.setMsg("");

        expotrExcles0.add(expotrExcle01);
        expotrExcles0.add(expotrExcle02);
        expotrExcles0.add(expotrExcle03);
        expotrExcles0.add(expotrExcle04);
        expotrExcles0.add(expotrExcle05);
        expotrExcles0.add(expotrExcle06);
        expotrExcles0.add(expotrExcle07);
        expotrExcles0.add(expotrExcle08);
        excles1.add(expotrExcles0);

        List<ExpotrExcle> expotrExcles1 = new ArrayList<>();

        ExpotrExcle expotrExcle11 = new ExpotrExcle();
        expotrExcle11.setFirstRow(1);
        expotrExcle11.setLastRow(2);
        expotrExcle11.setFirstCol(0);
        expotrExcle11.setLastCol(0);
        expotrExcle11.setMsg("单位");
        ExpotrExcle expotrExcle12 = new ExpotrExcle();
        expotrExcle12.setFirstRow(1);
        expotrExcle12.setLastRow(2);
        expotrExcle12.setFirstCol(1);
        expotrExcle12.setLastCol(1);
        expotrExcle12.setMsg("作业区");
        ExpotrExcle expotrExcle13 = new ExpotrExcle();
        expotrExcle13.setFirstRow(1);
        expotrExcle13.setLastRow(2);
        expotrExcle13.setFirstCol(2);
        expotrExcle13.setLastCol(2);
        expotrExcle13.setMsg("环境风险单元");
        ExpotrExcle expotrExcle14 = new ExpotrExcle();
        expotrExcle14.setFirstRow(1);
        expotrExcle14.setLastRow(2);
        expotrExcle14.setFirstCol(3);
        expotrExcle14.setLastCol(3);
        expotrExcle14.setMsg("现有环境风险防控措施");
        ExpotrExcle expotrExcle15 = new ExpotrExcle();
        expotrExcle15.setFirstRow(1);
        expotrExcle15.setLastRow(1);
        expotrExcle15.setFirstCol(4);
        expotrExcle15.setLastCol(7);
        expotrExcle15.setMsg("本年度提出的拟整改环境风险防控措施");
        ExpotrExcle expotrExcle16 = new ExpotrExcle();
        expotrExcle16.setFirstRow(1);
        expotrExcle16.setLastRow(1);
        expotrExcle16.setFirstCol(5);
        expotrExcle16.setLastCol(5);
        expotrExcle16.setMsg("");
        ExpotrExcle expotrExcle17 = new ExpotrExcle();
        expotrExcle17.setFirstRow(1);
        expotrExcle17.setLastRow(1);
        expotrExcle17.setFirstCol(6);
        expotrExcle17.setLastCol(6);
        expotrExcle17.setMsg("");
        ExpotrExcle expotrExcle18 = new ExpotrExcle();
        expotrExcle18.setFirstRow(1);
        expotrExcle18.setLastRow(1);
        expotrExcle18.setFirstCol(7);
        expotrExcle18.setLastCol(7);
        expotrExcle18.setMsg("");

        expotrExcles1.add(expotrExcle11);
        expotrExcles1.add(expotrExcle12);
        expotrExcles1.add(expotrExcle13);
        expotrExcles1.add(expotrExcle14);
        expotrExcles1.add(expotrExcle15);
        expotrExcles1.add(expotrExcle16);
        expotrExcles1.add(expotrExcle17);
        expotrExcles1.add(expotrExcle18);
        excles1.add(expotrExcles1);


        List<ExpotrExcle> expotrExcles2 = new ArrayList<>();

        ExpotrExcle expotrExcle21 = new ExpotrExcle();
        expotrExcle21.setFirstRow(2);
        expotrExcle21.setLastRow(2);
        expotrExcle21.setFirstCol(0);
        expotrExcle21.setLastCol(0);
        expotrExcle21.setMsg("");
        ExpotrExcle expotrExcle22 = new ExpotrExcle();
        expotrExcle22.setFirstRow(2);
        expotrExcle22.setLastRow(2);
        expotrExcle22.setFirstCol(1);
        expotrExcle22.setLastCol(1);
        expotrExcle22.setMsg("");
        ExpotrExcle expotrExcle23 = new ExpotrExcle();
        expotrExcle23.setFirstRow(2);
        expotrExcle23.setLastRow(2);
        expotrExcle23.setFirstCol(2);
        expotrExcle23.setLastCol(2);
        expotrExcle23.setMsg("");
        ExpotrExcle expotrExcle24 = new ExpotrExcle();
        expotrExcle24.setFirstRow(2);
        expotrExcle24.setLastRow(2);
        expotrExcle24.setFirstCol(3);
        expotrExcle24.setLastCol(3);
        expotrExcle24.setMsg("");
        ExpotrExcle expotrExcle25 = new ExpotrExcle();
        expotrExcle25.setFirstRow(2);
        expotrExcle25.setLastRow(2);
        expotrExcle25.setFirstCol(4);
        expotrExcle25.setLastCol(4);
        expotrExcle25.setMsg("拟整改的措施");
        ExpotrExcle expotrExcle26 = new ExpotrExcle();
        expotrExcle26.setFirstRow(2);
        expotrExcle26.setLastRow(2);
        expotrExcle26.setFirstCol(5);
        expotrExcle26.setLastCol(5);
        expotrExcle26.setMsg("整改措施计划完成时间");
        ExpotrExcle expotrExcle27 = new ExpotrExcle();
        expotrExcle27.setFirstRow(2);
        expotrExcle27.setLastRow(2);
        expotrExcle27.setFirstCol(6);
        expotrExcle27.setLastCol(6);
        expotrExcle27.setMsg("措施整改责任部门");
        ExpotrExcle expotrExcle28 = new ExpotrExcle();
        expotrExcle28.setFirstRow(2);
        expotrExcle28.setLastRow(2);
        expotrExcle28.setFirstCol(7);
        expotrExcle28.setLastCol(7);
        expotrExcle28.setMsg("整改责任人");

        expotrExcles2.add(expotrExcle21);
        expotrExcles2.add(expotrExcle22);
        expotrExcles2.add(expotrExcle23);
        expotrExcles2.add(expotrExcle24);
        expotrExcles2.add(expotrExcle25);
        expotrExcles2.add(expotrExcle26);
        expotrExcles2.add(expotrExcle27);
        expotrExcles2.add(expotrExcle28);
        excles1.add(expotrExcles2);

        //sheet1数据
        List<List<ExpotrExcle>> exportDatas1 = new ArrayList<>();
        for (Measures measures1 : measures){

            List<ExpotrExcle> expotrExcles = new ArrayList<>();

            ExpotrExcle expotrExcle0 = new ExpotrExcle();
            expotrExcle0.setMsg(measures1.getFactoryName());
            ExpotrExcle expotrExcle1 = new ExpotrExcle();
            expotrExcle1.setMsg(measures1.getTaskAreaName());
            ExpotrExcle expotrExcle2 = new ExpotrExcle();
            expotrExcle2.setMsg(measures1.getStationName());
            ExpotrExcle expotrExcle3 = new ExpotrExcle();
            expotrExcle3.setMsg(measures1.getExistingMeasures());
            ExpotrExcle expotrExcle4 = new ExpotrExcle();
            expotrExcle4.setMsg(measures1.getProposedRectification());
            ExpotrExcle expotrExcle5 = new ExpotrExcle();
            expotrExcle5.setMsg(ImportExeclUtil.DateUtil.dateToStr(measures1.getPlannedTime(), ImportExeclUtil.DateUtil.YYYY_MM_DD));
            ExpotrExcle expotrExcle6 = new ExpotrExcle();
            expotrExcle6.setMsg(measures1.getDepartment());
            ExpotrExcle expotrExcle7 = new ExpotrExcle();
            expotrExcle7.setMsg(measures1.getPersonLiable());

            expotrExcles.add(expotrExcle0);
            expotrExcles.add(expotrExcle1);
            expotrExcles.add(expotrExcle2);
            expotrExcles.add(expotrExcle3);
            expotrExcles.add(expotrExcle4);
            expotrExcles.add(expotrExcle5);
            expotrExcles.add(expotrExcle6);
            expotrExcles.add(expotrExcle7);

            exportDatas1.add(expotrExcles);
        }
        //sheet2数据处理
        //sheet2表头
        List<List<ExpotrExcle>> excles2 = new ArrayList<>();
        List<ExpotrExcle> expotrExcles20 = new ArrayList<>();

        ExpotrExcle expotrExcle201 = new ExpotrExcle();
        expotrExcle201.setFirstRow(0);
        expotrExcle201.setLastRow(0);
        expotrExcle201.setFirstCol(0);
        expotrExcle201.setLastCol(8);
        expotrExcle201.setMsg("上年度环境风险防控措施落实情况分析");
        ExpotrExcle expotrExcle202 = new ExpotrExcle();
        expotrExcle202.setFirstRow(0);
        expotrExcle202.setLastRow(0);
        expotrExcle202.setFirstCol(1);
        expotrExcle202.setLastCol(1);
        expotrExcle202.setMsg("");
        ExpotrExcle expotrExcle203 = new ExpotrExcle();
        expotrExcle203.setFirstRow(0);
        expotrExcle203.setLastRow(0);
        expotrExcle203.setFirstCol(2);
        expotrExcle203.setLastCol(2);
        expotrExcle203.setMsg("");
        ExpotrExcle expotrExcle204 = new ExpotrExcle();
        expotrExcle204.setFirstRow(0);
        expotrExcle204.setLastRow(0);
        expotrExcle204.setFirstCol(3);
        expotrExcle204.setLastCol(3);
        expotrExcle204.setMsg("");
        ExpotrExcle expotrExcle205 = new ExpotrExcle();
        expotrExcle205.setFirstRow(0);
        expotrExcle205.setLastRow(0);
        expotrExcle205.setFirstCol(4);
        expotrExcle205.setLastCol(4);
        expotrExcle205.setMsg("");
        ExpotrExcle expotrExcle206 = new ExpotrExcle();
        expotrExcle206.setFirstRow(0);
        expotrExcle206.setLastRow(0);
        expotrExcle206.setFirstCol(5);
        expotrExcle206.setLastCol(5);
        expotrExcle206.setMsg("");
        ExpotrExcle expotrExcle207 = new ExpotrExcle();
        expotrExcle207.setFirstRow(0);
        expotrExcle207.setLastRow(0);
        expotrExcle207.setFirstCol(6);
        expotrExcle207.setLastCol(6);
        expotrExcle207.setMsg("");
        ExpotrExcle expotrExcle208 = new ExpotrExcle();
        expotrExcle208.setFirstRow(0);
        expotrExcle208.setLastRow(0);
        expotrExcle208.setFirstCol(7);
        expotrExcle208.setLastCol(7);
        expotrExcle208.setMsg("");
        ExpotrExcle expotrExcle209 = new ExpotrExcle();
        expotrExcle209.setFirstRow(0);
        expotrExcle209.setLastRow(0);
        expotrExcle209.setFirstCol(8);
        expotrExcle209.setLastCol(8);
        expotrExcle209.setMsg("");

        expotrExcles20.add(expotrExcle201);
        expotrExcles20.add(expotrExcle202);
        expotrExcles20.add(expotrExcle203);
        expotrExcles20.add(expotrExcle204);
        expotrExcles20.add(expotrExcle205);
        expotrExcles20.add(expotrExcle206);
        expotrExcles20.add(expotrExcle207);
        expotrExcles20.add(expotrExcle208);
        expotrExcles20.add(expotrExcle209);
        excles2.add(expotrExcles20);

        List<ExpotrExcle> expotrExcles21 = new ArrayList<>();

        ExpotrExcle expotrExcle211 = new ExpotrExcle();
        expotrExcle211.setFirstRow(1);
        expotrExcle211.setLastRow(2);
        expotrExcle211.setFirstCol(0);
        expotrExcle211.setLastCol(0);
        expotrExcle211.setMsg("单位");
        ExpotrExcle expotrExcle212 = new ExpotrExcle();
        expotrExcle212.setFirstRow(1);
        expotrExcle212.setLastRow(2);
        expotrExcle212.setFirstCol(1);
        expotrExcle212.setLastCol(1);
        expotrExcle212.setMsg("作业区");
        ExpotrExcle expotrExcle213 = new ExpotrExcle();
        expotrExcle213.setFirstRow(1);
        expotrExcle213.setLastRow(2);
        expotrExcle213.setFirstCol(2);
        expotrExcle213.setLastCol(2);
        expotrExcle213.setMsg("风险单元");
        ExpotrExcle expotrExcle214 = new ExpotrExcle();
        expotrExcle214.setFirstRow(1);
        expotrExcle214.setLastRow(2);
        expotrExcle214.setFirstCol(3);
        expotrExcle214.setLastCol(3);
        expotrExcle214.setMsg("上年度制定的环境风险防控措施计划");
        ExpotrExcle expotrExcle215 = new ExpotrExcle();
        expotrExcle215.setFirstRow(1);
        expotrExcle215.setLastRow(2);
        expotrExcle215.setFirstCol(4);
        expotrExcle215.setLastCol(4);
        expotrExcle215.setMsg("已经完成整改的环境风险防控措施");
        ExpotrExcle expotrExcle216 = new ExpotrExcle();
        expotrExcle216.setFirstRow(1);
        expotrExcle216.setLastRow(1);
        expotrExcle216.setFirstCol(5);
        expotrExcle216.setLastCol(7);
        expotrExcle216.setMsg("正在落实（尚未完成）的环境风险防控措施");
        ExpotrExcle expotrExcle217 = new ExpotrExcle();
        expotrExcle217.setFirstRow(1);
        expotrExcle217.setLastRow(1);
        expotrExcle217.setFirstCol(6);
        expotrExcle217.setLastCol(6);
        expotrExcle217.setMsg("");
        ExpotrExcle expotrExcle218 = new ExpotrExcle();
        expotrExcle218.setFirstRow(1);
        expotrExcle218.setLastRow(1);
        expotrExcle218.setFirstCol(7);
        expotrExcle218.setLastCol(7);
        expotrExcle218.setMsg("");
        ExpotrExcle expotrExcle219 = new ExpotrExcle();
        expotrExcle219.setFirstRow(1);
        expotrExcle219.setLastRow(2);
        expotrExcle219.setFirstCol(8);
        expotrExcle219.setLastCol(8);
        expotrExcle219.setMsg("未开始，已纳入后续环境风险防控措施计划");

        expotrExcles21.add(expotrExcle211);
        expotrExcles21.add(expotrExcle212);
        expotrExcles21.add(expotrExcle213);
        expotrExcles21.add(expotrExcle214);
        expotrExcles21.add(expotrExcle215);
        expotrExcles21.add(expotrExcle216);
        expotrExcles21.add(expotrExcle217);
        expotrExcles21.add(expotrExcle218);
        expotrExcles21.add(expotrExcle219);
        excles2.add(expotrExcles21);


        List<ExpotrExcle> expotrExcles22 = new ArrayList<>();

        ExpotrExcle expotrExcle221 = new ExpotrExcle();
        expotrExcle221.setFirstRow(2);
        expotrExcle221.setLastRow(2);
        expotrExcle221.setFirstCol(0);
        expotrExcle221.setLastCol(0);
        expotrExcle221.setMsg("");
        ExpotrExcle expotrExcle222 = new ExpotrExcle();
        expotrExcle222.setFirstRow(2);
        expotrExcle222.setLastRow(2);
        expotrExcle222.setFirstCol(1);
        expotrExcle222.setLastCol(1);
        expotrExcle222.setMsg("");
        ExpotrExcle expotrExcle223 = new ExpotrExcle();
        expotrExcle223.setFirstRow(2);
        expotrExcle223.setLastRow(2);
        expotrExcle223.setFirstCol(2);
        expotrExcle223.setLastCol(2);
        expotrExcle223.setMsg("");
        ExpotrExcle expotrExcle224 = new ExpotrExcle();
        expotrExcle224.setFirstRow(2);
        expotrExcle224.setLastRow(2);
        expotrExcle224.setFirstCol(3);
        expotrExcle224.setLastCol(3);
        expotrExcle224.setMsg("");
        ExpotrExcle expotrExcle225 = new ExpotrExcle();
        expotrExcle225.setFirstRow(2);
        expotrExcle225.setLastRow(2);
        expotrExcle225.setFirstCol(4);
        expotrExcle225.setLastCol(4);
        expotrExcle225.setMsg("");
        ExpotrExcle expotrExcle226 = new ExpotrExcle();
        expotrExcle226.setFirstRow(2);
        expotrExcle226.setLastRow(2);
        expotrExcle226.setFirstCol(5);
        expotrExcle226.setLastCol(5);
        expotrExcle226.setMsg("措施内容");
        ExpotrExcle expotrExcle227 = new ExpotrExcle();
        expotrExcle227.setFirstRow(2);
        expotrExcle227.setLastRow(2);
        expotrExcle227.setFirstCol(6);
        expotrExcle227.setLastCol(6);
        expotrExcle227.setMsg("责任人");
        ExpotrExcle expotrExcle228 = new ExpotrExcle();
        expotrExcle228.setFirstRow(2);
        expotrExcle228.setLastRow(2);
        expotrExcle228.setFirstCol(7);
        expotrExcle228.setLastCol(7);
        expotrExcle228.setMsg("预计完成时间");
        ExpotrExcle expotrExcle229 = new ExpotrExcle();
        expotrExcle229.setFirstRow(2);
        expotrExcle229.setLastRow(2);
        expotrExcle229.setFirstCol(8);
        expotrExcle229.setLastCol(8);
        expotrExcle229.setMsg("");

        expotrExcles22.add(expotrExcle221);
        expotrExcles22.add(expotrExcle222);
        expotrExcles22.add(expotrExcle223);
        expotrExcles22.add(expotrExcle224);
        expotrExcles22.add(expotrExcle225);
        expotrExcles22.add(expotrExcle226);
        expotrExcles22.add(expotrExcle227);
        expotrExcles22.add(expotrExcle228);
        expotrExcles22.add(expotrExcle229);
        excles2.add(expotrExcles22);

        //sheet2数据
        List<List<ExpotrExcle>> exportDatas2 = new ArrayList<>();
        for (MeasuresLast measuresLast : measuresLasts){

            List<ExpotrExcle> expotrExcles = new ArrayList<>();

            ExpotrExcle expotrExcle0 = new ExpotrExcle();
            expotrExcle0.setMsg(measuresLast.getFactoryName());
            ExpotrExcle expotrExcle1 = new ExpotrExcle();
            expotrExcle1.setMsg(measuresLast.getTaskAreaName());
            ExpotrExcle expotrExcle2 = new ExpotrExcle();
            expotrExcle2.setMsg(measuresLast.getStationName());

            ExpotrExcle expotrExcle3 = new ExpotrExcle();
            expotrExcle3.setMsg(measuresLast.getLastMeasures());
            ExpotrExcle expotrExcle4 = new ExpotrExcle();
            expotrExcle4.setMsg(measuresLast.getMeasuresCompleted());

            ExpotrExcle expotrExcle5 = new ExpotrExcle();
            expotrExcle5.setMsg(measuresLast.getMeasuresContent());
            ExpotrExcle expotrExcle6 = new ExpotrExcle();
            expotrExcle6.setMsg(measuresLast.getLastPersonLiable());
            ExpotrExcle expotrExcle7 = new ExpotrExcle();
            expotrExcle7.setMsg(ImportExeclUtil.DateUtil.dateToStr(measuresLast.getCompletionTime(), ImportExeclUtil.DateUtil.YYYY_MM_DD));
            ExpotrExcle expotrExcle8 = new ExpotrExcle();
            expotrExcle8.setMsg(measuresLast.getNoStartPlan());

            expotrExcles.add(expotrExcle0);
            expotrExcles.add(expotrExcle1);
            expotrExcles.add(expotrExcle2);
            expotrExcles.add(expotrExcle3);
            expotrExcles.add(expotrExcle4);
            expotrExcles.add(expotrExcle5);
            expotrExcles.add(expotrExcle6);
            expotrExcles.add(expotrExcle7);
            expotrExcles.add(expotrExcle8);

            exportDatas2.add(expotrExcles);
        }
        List<List<List<ExpotrExcle>>> excless = new ArrayList<>();
        excless.add(excles1);
        excless.add(excles2);


        List<List<List<ExpotrExcle>>> excleDatass = new ArrayList<>();
        excleDatass.add(exportDatas1);
        excleDatass.add(exportDatas2);

        String[] sheetNames = {"本年度","上年度"};
        exportExcelUtil.exportExcel3(sheetNames,excless,excleDatass,response,"环境风险防控与应急措施");



    }


}
