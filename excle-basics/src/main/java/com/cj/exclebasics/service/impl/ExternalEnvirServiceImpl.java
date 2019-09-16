package com.cj.exclebasics.service.impl;

import com.cj.common.entity.ExcleTable;
import com.cj.common.entity.ExternalEnvirImg;
import com.cj.common.entity.LogCheck;
import com.cj.common.entity.TableRecord;
import com.cj.common.exception.UserException;
import com.cj.common.utils.excle.ImportExeclUtil;
import com.cj.core.domain.ApiResult;
import com.cj.exclebasics.domain.NexusImg;
import com.cj.exclebasics.mapper.ExternalEnvirImgMapper;
import com.cj.exclebasics.service.ExternalEnvirService;
import com.cj.exclepublic.domain.FindOrganizationResp;
import com.cj.exclepublic.mapper.ExcleTableMapper;
import com.cj.exclepublic.mapper.TableRecordMapper;
import com.cj.exclepublic.service.ExcleService;
import com.cj.exclepublic.service.PowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.cj.common.utils.file.FileUtil.getPrefix;

@Service
@Transactional
public class ExternalEnvirServiceImpl implements ExternalEnvirService {


    @Resource
    private ExternalEnvirImgMapper externalEnvirImgMapper;

    @Resource
    private TableRecordMapper tableRecordMapper;

    @Resource
    private ExcleTableMapper excleTableMapper;

    @Resource
    private PowerService powerService;

    @Resource
    private ExcleService excleService;


    @Override
    public List<ExternalEnvirImg> findNexusImg(Map map, HttpServletRequest request) {

        String prefix = getPrefix(request);

        List<ExternalEnvirImg> externalEnvirImgs = externalEnvirImgMapper.findNexusImg(map);

        for (ExternalEnvirImg externalEnvirImg : externalEnvirImgs) {
            externalEnvirImg.setImgUrl(prefix + externalEnvirImg.getImgUrl());
        }


        return externalEnvirImgs;
    }

    @Override
    public int addNexusImg(NexusImg nexusImg, HttpServletRequest request) {
        long adminId = (long) request.getSession().getAttribute("id");
        long stationId = nexusImg.getStationId();
        String tableName = nexusImg.getTableName();
        String year = nexusImg.getYear();
        List<ExternalEnvirImg> externalEnvirImgs = nexusImg.getNewExternalEnvirImgs();
        int i = 0;
        String prefix = getPrefix(request);

        //查询 tableRecord 信息是否存在
        Map map = new HashMap();
        map.put("stationId", nexusImg.getStationId());
        map.put("tableName", nexusImg.getTableName());
        map.put("year", nexusImg.getYear());
        map.put("tableType", "1");
        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);
        if (tableRecord != null) {
            return -1;
        }
        //新增表列表信息
        tableRecord = new TableRecord();

        //根据场站ID和表名称查询excle列表信息
        ExcleTable excleTable = excleTableMapper.selectByStationIdAndTableName(stationId, tableName);


        tableRecord.setStationId(stationId);
        tableRecord.setExcleTableId(excleTable.getExcleTableId());
        tableRecord.setYear(ImportExeclUtil.DateUtil.strToDate(nexusImg.getYear(), ImportExeclUtil.DateUtil.YYYY));
        tableRecord.setFounderId(adminId);
        tableRecord.setCreateTime(new Date());
        tableRecord.setCheckType("3");

        tableRecordMapper.insertSelective(tableRecord);
        for (ExternalEnvirImg externalEnvirImg : externalEnvirImgs) {
            externalEnvirImg.setTableRecordId(tableRecord.getTableRecordId());
            externalEnvirImg.setImgUrl(externalEnvirImg.getImgUrl().replaceAll(prefix, ""));

        }

        i = externalEnvirImgMapper.addNexusImg(externalEnvirImgs);

        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(year);
        logCheck.setCheckMsg("上传 " + tableName);

        int n = excleService.addLogCheck(logCheck, request);


        return i;
    }

    @Override
    public int updateNexusImg(NexusImg nexusImg, HttpServletRequest request) {
        long adminId = (long) request.getSession().getAttribute("id");
        long stationId = nexusImg.getStationId();
        String tableName = nexusImg.getTableName();
        String year = nexusImg.getYear();

        List<ExternalEnvirImg> oldExternalEnvirImgs = nexusImg.getOldExternalEnvirImgs();
        List<ExternalEnvirImg> newExternalEnvirImgs = nexusImg.getNewExternalEnvirImgs();

        String prefix = getPrefix(request);

        int i = 0;
        int j = 0;
        //查询 tableRecord 信息
        Map map = new HashMap();
        map.put("stationId", nexusImg.getStationId());
        map.put("tableName", nexusImg.getTableName());
        map.put("year", nexusImg.getYear());
        map.put("tableType", "1");
        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);
        if (tableRecord == null) {
            return i;
        }
        if (oldExternalEnvirImgs.size() > 0) {

            i = externalEnvirImgMapper.updateNexusImg(oldExternalEnvirImgs);
        }
        if (newExternalEnvirImgs.size() > 0) {
            for (ExternalEnvirImg externalEnvirImg : newExternalEnvirImgs) {
                externalEnvirImg.setTableRecordId(tableRecord.getTableRecordId());
                externalEnvirImg.setImgUrl(externalEnvirImg.getImgUrl().replaceAll(prefix, ""));
            }

            j = externalEnvirImgMapper.addNexusImg(newExternalEnvirImgs);
        }


        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(year);
        logCheck.setCheckMsg("修改 " + tableName);

        int n = excleService.addLogCheck(logCheck, request);


        return i + j;
    }


}
