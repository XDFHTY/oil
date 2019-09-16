package com.cj.exclepublic.service.impl;

import com.cj.common.domain.ExclePoint;
import com.cj.common.domain.ExpotrExcle;
import com.cj.common.entity.*;
import com.cj.common.exception.UserException;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.common.utils.timeUtil.TimeToString;
import com.cj.common.utils.vo.ExcleData;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.MemoryData;
import com.cj.exclepublic.domain.*;
import com.cj.exclepublic.mapper.*;
import com.cj.exclepublic.service.ExcleService;
import com.cj.exclepublic.service.PowerService;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class ExcleServiceImpl implements ExcleService {

    @Resource
    private ExcleTableStructureMapper excleTableStructureMapper;

    @Resource
    private ExcleTableMapper excleTableMapper;

    @Resource
    private TableRecordMapper tableRecordMapper;

    @Resource
    private TableCellRecordMapper tableCellRecordMapper;



    @Resource
    private PowerService powerService;


    @Resource
    private ExcleTableFormulaMapper excleTableFormulaMapper;


    private static final String thisYearTableName = "本年环境风险防控与应急措施";
    private static final String lastYearTableName = "上年环境风险防控与应急措施";

    /**
     * 根据场站id和表名查询 表结构
     *
     * @param stationId
     * @param tableName
     * @return
     */
    @Override
    public ApiResult findExcleStruByStIdAndTabName(Long stationId, String tableName, String year, HttpServletRequest request) {
        ApiResult apiResult = null;

        HttpSession session = request.getSession();
        long adminId = (Long) session.getAttribute("id");
        List<Map> roles = (List<Map>) session.getAttribute("roles");


        try {
            List<ExcleTableStructure> excleTableStructures = excleTableStructureMapper.findExcleStruByStIdAndTableName(stationId, tableName);

            if (excleTableStructures.size() == 0) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("没有对应的表结构");
                return apiResult;
            }
            //根据场站ID查询组织结构信息替换到表结构
            FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);
            for (ExcleTableStructure excleTableStructure : excleTableStructures) {
                if ("调用作业区名称".equals(excleTableStructure.getCellMsg())) {
                    excleTableStructure.setCellMsg(findOrganizationResp.getTaskAreaName());
                } else if ("调用场站名称".equals(excleTableStructure.getCellMsg())) {
                    excleTableStructure.setCellMsg(findOrganizationResp.getStationName());

                }
            }

            List<TableCellRecord> tableCellRecords = new ArrayList<>();
            List<TableCellRecord> lastTableCellRecords = new ArrayList<>();
            if (year != null && year.length() > 0) {

                Map map = new HashMap();
                map.put("stationId", stationId);
                map.put("tableName", tableName);
                map.put("year", year);

                tableCellRecords = tableCellRecordMapper.findTableCellRecords(map);
                map.put("year", (Integer.valueOf(year)-1));
                lastTableCellRecords = tableCellRecordMapper.findTableCellRecords(map);

            }
            long excleTableId = excleTableStructures.get(0).getExcleTableId();
            List<ExcleTableFormula> excleTableFormulas = excleTableFormulaMapper.findExcleTableFormula(excleTableId);

            //查询表格审批状态
            Map map = new HashMap();
            map.put("stationId", stationId);
            map.put("tableName", tableName);
            map.put("year", year);
            map.put("tableType", "1");
            if (thisYearTableName.equals(tableName)) {
                map.put("tableType", "2");

            } else if (lastYearTableName.equals(tableName)) {
                map.put("tableType", "3");
            }
            //查询表内容列表信息
            TableRecord tableRecord = tableRecordMapper.findTableRecord(map);

            //查询表格是否允许编辑
            Boolean b = powerService.checkUpdatePower(tableRecord == null ? "" : tableRecord.getCheckType(), (String) roles.get(0).get("roleDescriptionName"));

            FindExcleStruByStIdAndTabNameResp findExcleStruByStIdAndTabNameResp = new FindExcleStruByStIdAndTabNameResp();
            findExcleStruByStIdAndTabNameResp.setExcleTableStructures(excleTableStructures);
            findExcleStruByStIdAndTabNameResp.setTableCellRecords(tableCellRecords);
            findExcleStruByStIdAndTabNameResp.setLastTableCellRecords(lastTableCellRecords);
            findExcleStruByStIdAndTabNameResp.setExcleTableFormulas(excleTableFormulas);
            findExcleStruByStIdAndTabNameResp.setUpdate(b);

            apiResult = ApiResult.SUCCESS();
            apiResult.setData(findExcleStruByStIdAndTabNameResp);
        } catch (Exception e) {
            apiResult = ApiResult.CODE_400();
            e.printStackTrace();
        }
        return apiResult;
    }

    @Override
    public ApiResult exportTableStru(Long stationId, String tableName, String year, HttpServletResponse response) throws Exception {

        //查询表结构
        List<ExcleTableStructure> excleTableStructures = excleTableStructureMapper.findExcleStruByStIdAndTableName(stationId, tableName);

        //根据场站ID查询组织结构信息替换到表结构
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(stationId);

        for (ExcleTableStructure excleTableStructure : excleTableStructures) {
            if ("调用作业区名称".equals(excleTableStructure.getCellMsg())) {
                excleTableStructure.setCellMsg(findOrganizationResp.getTaskAreaName());
            } else if ("调用场站名称".equals(excleTableStructure.getCellMsg())) {
                excleTableStructure.setCellMsg(findOrganizationResp.getStationName());

            }
        }


        Map map = new HashMap();
        map.put("stationId", stationId);
        map.put("tableName", tableName);
        map.put("year", year);
        //查询表数据
        List<TableCellRecord> tableCellRecords = new ArrayList<>();

        //查询表内容
        tableCellRecords = tableCellRecordMapper.findTableCellRecords(map);


        //查询表内容
        tableCellRecords = tableCellRecordMapper.findTableCellRecords(map);

        Set set = new HashSet();
        //数据整合
        for (ExcleTableStructure excleTableStructure : excleTableStructures) {
            set.add(excleTableStructure.getStartRow());
            for (TableCellRecord tableCellRecord : tableCellRecords) {
                if (tableCellRecord != null && excleTableStructure.getExcleTableStructureId() == tableCellRecord.getExcleTableStructureId()) {
                    excleTableStructure.setCellMsg(tableCellRecord.getCellMsg());
                }

            }
        }

        //set长度就是表头行数,将每一行放入一个集合并排序
        List<List<ExpotrExcle>> excles = new ArrayList<>();
        for (int i = 0; i < set.size(); i++) {
            List<ExpotrExcle> expotrExcles = new ArrayList<>();

            for (ExcleTableStructure excleTableStructure : excleTableStructures) {
                if (excleTableStructure.getStartRow() == i) {
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

        List<List<List<ExpotrExcle>>> excless = new ArrayList<>();
        excless.add(excles);


        List<List<List<ExpotrExcle>>> excleDatass = new ArrayList<>();
        List<List<ExpotrExcle>> excleDatas = new ArrayList<>();
        excleDatass.add(excleDatas);

        String[] sheetNames = {tableName};

        exportExcelUtil.exportExcel3(sheetNames, excless, excleDatass, response,
                findOrganizationResp.getFactoryName() + findOrganizationResp.getTaskAreaName() + findOrganizationResp.getStationName() + tableName + "-" + year);

        return null;
    }

    /**
     * 修改表结构
     *
     * @param updateTabStruReq
     * @return
     */
    @Override
    public ApiResult updateTabStru(UpdateTabStruReq updateTabStruReq) {
        ApiResult apiResult = null;

        List<ExcleTableStructure> oldExcleTableStructures = updateTabStruReq.getOldExcleTableStructures();
        List<ExcleTableStructure> newExcleTableStructures = updateTabStruReq.getNewExcleTableStructures();

        int i = 0;
        int j = 0;
        if (oldExcleTableStructures.size() > 0) {
            for (ExcleTableStructure excleTableStructure : oldExcleTableStructures) {
                excleTableStructure.setExcleTableId(updateTabStruReq.getExcleTableId());

            }
            //修改有ID的表结构
            i = excleTableStructureMapper.updateTabStru(oldExcleTableStructures);
        }

        if (newExcleTableStructures.size() > 0) {
            for (ExcleTableStructure excleTableStructure : newExcleTableStructures) {
                excleTableStructure.setExcleTableId(updateTabStruReq.getExcleTableId());

            }
            //新增没ID的表结构
            j = excleTableStructureMapper.updateTabStru(newExcleTableStructures);

        }
        if (i > 0 || j > 0) {
            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("更新" + i + "个单元格,新增" + j + "个单元格");
        } else {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("更新失败");
        }
        return apiResult;
    }


    /**
     * 根据场站ID和表名称查询出表对象excle_table
     *
     * @param stationId
     * @param tableName
     * @return
     */
    @Override
    public ExcleTable findByStationIdAndTabName(Long stationId, String tableName) {
        return excleTableMapper.selectByStationIdAndTableName(stationId, tableName);
    }

    /**
     * 根据excle_table_id来查询表结构
     *
     * @param excleTabId
     * @return
     */
    @Override
    public List<ExcleTableStructure> findExcleStruByExcleTabId(Long excleTabId) {
        return excleTableStructureMapper.findExcleStruByExcleTabId(excleTabId);
    }

    /**
     * 根据excleTabId和年份查询TableRecord
     *
     * @param excleTabId
     * @param year
     * @return
     */
    @Override
    public TableRecord findTableRecordByExcleTabIdAndYear(Long excleTabId, String year) {
        return tableRecordMapper.selectByExcleTabIdAndTime(excleTabId, year);
    }

    /**
     * 根据tableRecordId查询内容表
     *
     * @param tableRecordId
     * @return
     */
    @Override
    public List<TableCellRecord> findTabCellRecordListByTabRecordId(Long tableRecordId) {
        return tableCellRecordMapper.selectByTabRecordId(tableRecordId);
    }

    /**
     * 根据tableRecordId 和 excleTableStructureId查询 内容
     *
     * @param tableRecordId
     * @param excleTabStruId
     * @return
     */
    @Override
    public TableCellRecord findTabCellRecordByTabRecordIdAndStruId(Long tableRecordId, Long excleTabStruId) {
        return tableCellRecordMapper.selectByTabRecordIdAndStruId(tableRecordId, excleTabStruId);
    }

    /**
     * 新增/修改 动态表的数据
     * 传入 要出入的数据，对应的年份
     * 向表中插入数据
     *
     * @param excleDataList
     * @param year
     * @return
     */
    @Override
    public ApiResult addTableCellRocordList(Long stationId,
                                            String tableName,
                                            List<ExcleData> excleDataList,
                                            String year) {
        //1.查excle_table
        ExcleTable excleTable = findByStationIdAndTabName(stationId, tableName);
        //2.查excle_table_structure
        List<ExcleTableStructure> excleTableStructureList = findExcleStruByExcleTabId(excleTable.getExcleTableId());
        //2.1把2的结果转化成Map，方便高效查询:key:ExclePoint value:ExcleTableStructure对象
        HashMap<ExclePoint, ExcleTableStructure> excleTableStructureMap = new HashMap<>();
        for (ExcleTableStructure e : excleTableStructureList) {
            ExclePoint exclePoint = new ExclePoint(e.getStartRow(), e.getEndRow(), e.getStartCol(), e.getEndCol());
            excleTableStructureMap.put(exclePoint, e);
        }
        //3.查table_recdord
        TableRecord tableRecord = findTableRecordByExcleTabIdAndYear(excleTable.getExcleTableId(), year);
        if (tableRecord == null) {
            //新增一条tabRecord记录 (新增的时候，记得把主键返回到这个对象中，后面操作还需要使用)
        } else {
            //更新一条tabRecord记录
        }
        //4.查table_cell_record
        List<TableCellRecord> tableCellRecordList = findTabCellRecordListByTabRecordId(tableRecord.getTableRecordId());
        //4.1封装成Map key:excleTableStructureId value:TableCellRecord对象
        HashMap<Long, TableCellRecord> tableCellRecordMap = new HashMap<>();
        for (TableCellRecord t : tableCellRecordList) {
            tableCellRecordMap.put(t.getExcleTableStructureId(), t);
        }
        //5.遍历传入的数据，进行插入操作/更新操作
        for (ExcleData excleData : excleDataList) {
            ExcleTableStructure excleTableStructure = excleTableStructureMap.get(excleData.getExclePoint());
            if (excleTableStructure == null) {
                throw new RuntimeException("ExcleServiceImpl:用户传入错误的坐标信息，传入值为：" + excleData);
            }
            TableCellRecord tableCellRecord = tableCellRecordMap.get(excleTableStructure.getExcleTableStructureId());
            if (null == tableCellRecord) {
                //原本这个单元格没有内容，直接插入一条新内容
            } else {
                //原本这个单元格有内容，把旧记录的状态更新为不可用，插入一条新记录
            }
        }

        return null;
    }

    //===========================================================================================================
    @Override
    public ApiResult updateTabStru(Map map, HttpSession session) {
        return null;
    }

    /**
     * 查询该场站、该表、该年是否已存在数据
     *
     * @param json
     * @return
     */
    @Override
    public ApiResult findTableRecord(String json) {
        ApiResult apiResult = null;
        Map map = new Gson().fromJson(json, Map.class);

        String tableName = (String) map.get("tableName");
        if (thisYearTableName.equals(tableName)) {
            map.put("tableType", "2");

        } else if (lastYearTableName.equals(tableName)) {

            map.put("tableType", "3");
        } else {
            map.put("tableType", "1");

        }

        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);
        apiResult = ApiResult.SUCCESS();
        apiResult.setData(tableRecord);

        return apiResult;
    }


    /**
     * 新增表内容
     *
     * @param addTableCellRecordsReq
     * @param request
     * @return
     */
    @Override
    public ApiResult addTableCellRecords(AddTableCellRecordsReq addTableCellRecordsReq, HttpServletRequest request) throws UserException {
        ApiResult apiResult = null;
        HttpSession session = request.getSession();
        long adminId = (Long) session.getAttribute("id");
        List<Map> roles = (List<Map>) session.getAttribute("roles");

        Long stationId = addTableCellRecordsReq.getStationId();
        String tableName = addTableCellRecordsReq.getTableName();
        //查询场站级填写的表
        Boolean b = false;  //填表许可，默认为false
        Map map0 = new HashMap();
        map0.put("stationId", stationId.doubleValue());
        ApiResult apiResult1 = findExcleSort(map0, session);
        FindExcleSortResp findExcleSortResp = (FindExcleSortResp) apiResult1.getData();
        for (Map map : roles) {
            if ("场站级".equals((String) map.get("roleDescriptionName"))) {
                for (ExcleTable excleTable : findExcleSortResp.getExcleTablesBasic()) {
                    if (tableName.equals(excleTable.getExcleTableName().trim())) {
                        b = true;
                        break;
                    }

                }

            } else if ("作业区级A".equals((String) map.get("roleDescriptionName"))) {
                if (tableName.indexOf("环境风险台账") != -1 ||
                        tableName.indexOf("环境风险受体") != -1 ||
                        "外环境关系图上传".equals(tableName)) {
                    b = true;
                    break;
                }
                for (ExcleTable excleTable : findExcleSortResp.getExcleTablesGrade()) {
                    if (tableName.equals(excleTable.getExcleTableName().trim())) {
                        b = true;
                        break;
                    }

                }

            }
        }

        if (!b) { //b=true 允许填表
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("当前不允许填写此表格");
            return apiResult;
        }


        String year = addTableCellRecordsReq.getYear();
        List<TableCellRecord> tableCellRecords = addTableCellRecordsReq.getTableCellRecords();


        Date date = TimeToString.StrToDate3(year);

        //根据场站ID和表名称查询excle列表信息
        ExcleTable excleTable = excleTableMapper.selectByStationIdAndTableName(stationId, tableName);


        TableRecord tableRecord = new TableRecord();
        tableRecord.setStationId(stationId);
        tableRecord.setExcleTableId(excleTable.getExcleTableId());
        tableRecord.setYear(date);
        tableRecord.setFounderId(adminId);
        tableRecord.setCreateTime(new Date());
        tableRecord.setCheckType("1");
        for (Map map : roles) {
            if ("场站级".equals((String) map.get("roleDescriptionName"))) {
                tableRecord.setCheckType("1");
                break;

            } else if ("作业区级A".equals((String) map.get("roleDescriptionName"))) {
                tableRecord.setCheckType("3");
                break;

            }
        }

        tableRecord.setTableType("1");

        if (thisYearTableName.equals(tableName)) {
            tableRecord.setTableType("2");

        } else if (lastYearTableName.equals(tableName)) {
            tableRecord.setTableType("3");
        }

        //检查场站ID、表名、type、年份 数据是否唯一
        Map map = new HashMap();
        map.put("stationId", tableRecord.getStationId());
        map.put("tableName", tableName);
        map.put("year", year);
        map.put("tableType", tableRecord.getTableType());


        TableRecord tableRecord1 = tableRecordMapper.findTableRecord(map);
        if (tableRecord1 != null && tableRecord1.getStationId() > 0) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("数据重复，每年每个场站只能存在一张表");
            throw new UserException(apiResult);
        }

        //新增表内容列表信息
        int i = tableRecordMapper.insertSelective(tableRecord);


        for (TableCellRecord tableCellRecord : tableCellRecords) {

            tableCellRecord.setTableRecordId(tableRecord.getTableRecordId());
            tableCellRecord.setFounderId(adminId);
            tableCellRecord.setCreateTime(new Date());
        }


        //新增单元格内容
        int j = tableCellRecordMapper.addTableCellRecords(tableCellRecords);


        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(addTableCellRecordsReq.getStationId());

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(year);
        logCheck.setCheckMsg("填写 " + addTableCellRecordsReq.getTableName());


        int n = addLogCheck(logCheck, request);

        if (j > 0)
            apiResult = ApiResult.SUCCESS();
        else
            apiResult = ApiResult.FAIL();

        return apiResult;
    }


    /**
     * 审核状态，
     * 1-场站填写未提交到作业区（场站可编辑），
     * 2-场站已提交到作业区未审核（场站不可编辑，作业区可编辑），
     * 3-作业区审核未通过被驳回（场站可编辑），
     * 4-作业区填写未提交到气矿（作业区可编辑），
     * 5-作业区修改场站提交的数据未提交到气矿（作业区可编辑），
     * 6-作业区已提交（已审核通过）气矿未审核（作业区不可编辑，气矿可编辑），
     * 7-气矿已审核未通过被驳回（作业区可编辑），
     * 8-气矿修改作业区提交的数据未审核（气矿可编辑），
     * 9-气矿已提交（已审核通过）（气矿不可编辑），
     * 默认为0
     *
     * @param updateTableCellRecordsReq
     * @param request
     * @return
     */
    @Override
    public ApiResult updateTableCellRecords(UpdateTableCellRecordsReq updateTableCellRecordsReq, HttpServletRequest request) {
        ApiResult apiResult = null;
        HttpSession session = request.getSession();
        long adminId = (Long) session.getAttribute("id");
        List<Map> roles = (List<Map>) session.getAttribute("roles");

        long stationId = updateTableCellRecordsReq.getStationId();
        String tableName = updateTableCellRecordsReq.getTableName();
        String year = updateTableCellRecordsReq.getYear();
        List<TableCellRecord> tableCellRecords = updateTableCellRecordsReq.getTableCellRecords();

        Date date = TimeToString.StrToDate3(year);

        //校验角色
        Boolean powerWrite = true;

        Map map = new HashMap();
        map.put("stationId", stationId);
        map.put("tableName", tableName);
        map.put("year", year);
        map.put("tableType", "1");
        if (thisYearTableName.equals(tableName)) {
            map.put("tableType", "2");

        } else if (lastYearTableName.equals(tableName)) {
            map.put("tableType", "3");
        }
        //查询表内容列表信息
        TableRecord tableRecord = tableRecordMapper.findTableRecord(map);
        //检查表格状态
        powerWrite = powerService.checkUpdatePower(tableRecord.getCheckType(), (String) roles.get(0).get("roleDescriptionName"));

        if (!powerWrite) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("数据已锁定，不可修改内容");
            throw new UserException(apiResult);
        }

        //修改表内容列表
        tableRecord.setOperatorId(adminId);
        tableRecord.setUpdateTime(new Date());
        int i = tableRecordMapper.updateByPrimaryKeySelective(tableRecord);

        //修改表内容
        for (TableCellRecord tableCellRecord : tableCellRecords) {
            tableCellRecord.setFounderId(adminId);
        }
        int j = 0;


        if (tableCellRecords.size() > 0) {
            j = tableCellRecordMapper.updateTableCellRecords(tableCellRecords);

        }

        //添加操作日志
        LogCheck logCheck = new LogCheck();
        FindOrganizationResp findOrganizationResp = powerService.findOrganization(updateTableCellRecordsReq.getStationId());

        logCheck.setFactoryId(findOrganizationResp.getFactoryId());
        logCheck.setTaskAreaId(findOrganizationResp.getTaskAreaId());
        logCheck.setStationId(findOrganizationResp.getStationId());

        logCheck.setOperatorId(adminId);
        logCheck.setDate(year);
        logCheck.setCheckMsg("修改 " + updateTableCellRecordsReq.getTableName());


        int n = addLogCheck(logCheck, request);


        if (i > 0 && j > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }

        return apiResult;
    }

    @Override
    public ApiResult findExcleSort(Map map, HttpSession session) {
        ApiResult apiResult = null;
        long stationId = ((Double) map.get("stationId")).longValue();

        List<Map> roles = (List<Map>) session.getAttribute("roles");
        List<ExcleTable> excleTables = excleTableMapper.findExcleSortAndFactoryTypeNum(stationId);
        //基础模块表顺序
        List<ExcleTable> excleTablesBasic = new ArrayList<>();
        //风险等级评估模块表顺序
        List<ExcleTable> excleTablesGrade = new ArrayList<>();

        for (ExcleTable excleTable : excleTables) {
            if (excleTable.getFactoryTypeNum() == 1 && excleTable.getExcleTableName().length() > 2) {
                excleTablesBasic.add(excleTable);
            } else if (excleTable.getFactoryTypeNum() == 12) {
                excleTablesBasic.add(excleTable);
            } else if (excleTable.getExcleTableName().length() == 2 || excleTable.getFactoryTypeNum() > 1 && excleTable.getFactoryTypeNum() < 11) {
                excleTablesGrade.add(excleTable);
            }
        }

        //弃用 风险物质和数量(Q水)、风险物质和数量(Q气)表格，使用Q水，Q气表格ID/NAME
        ExcleTable excleTable1 = new ExcleTable();
        ExcleTable excleTable2 = new ExcleTable();
        for (ExcleTable excleTable : excleTablesGrade){
            if (excleTable.getExcleTableName().equals("Q水")){
                excleTable1 = excleTable;
            }else if (excleTable.getExcleTableName().equals("Q气")){
                excleTable2 = excleTable;
            }


        }

        for (ExcleTable excleTable : excleTables){
            if (excleTable.getExcleTableName().equals("风险物质和数量(Q水)")){
                excleTable.setExcleTableId(excleTable1.getExcleTableId());
                excleTable.setExcleTableName(excleTable1.getExcleTableName());
                excleTable.setFactoryTypeNum(excleTable1.getFactoryTypeNum());
            }else if (excleTable.getExcleTableName().equals("风险物质和数量(Q气)")){
                excleTable.setExcleTableId(excleTable2.getExcleTableId());
                excleTable.setExcleTableName(excleTable2.getExcleTableName());
                excleTable.setFactoryTypeNum(excleTable2.getFactoryTypeNum());
            }
        }



        if ("场站级".equals(roles.get(0).get("roleDescriptionName"))) {
//            Iterator<ExcleTable> iterator = excleTablesBasic.iterator();
//            while (iterator.hasNext()) {
//                ExcleTable excleTable = iterator.next();
//
//
//                if (excleTable.getExcleTableName().indexOf("环境风险受体") != -1 || "外环境关系图上传".equals(excleTable.getExcleTableName())) {
//                    iterator.remove();
//                }
//            }


            excleTablesGrade = new ArrayList<>();

        }


        FindExcleSortResp findExcleSortResp = new FindExcleSortResp();
        findExcleSortResp.setExcleTablesBasic(excleTablesBasic);
        findExcleSortResp.setExcleTablesGrade(excleTablesGrade);

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(findExcleSortResp);
        return apiResult;
    }

    @Resource
    private RestTemplate restTemplate;

    @Override
    public int addLogCheck(LogCheck logCheck, HttpServletRequest request) {

        String token = MemoryData.getTokenMap().get(logCheck.getOperatorId().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("token", token);

        String local = request.getLocalAddr(); //取得服务器IP
        int prot = request.getLocalPort(); //取得服务器端口
        String url = "http://" + local + ":" + prot + "/api/v1/log/checklog/logs";
        String json = new Gson().toJson(logCheck);


        ApiResult apiResult = restTemplate.postForObject(
                url,
                new HttpEntity<String>(json, headers)
                , ApiResult.class);
        if (apiResult.getCode() == 1000) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("日志保存失败,请重试");
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new UserException(apiResult);
        }
        return 1;
    }

    /**
     * 同步查询 该场站、改年 Q水-风险物质和数量(Q水)、Q气-风险物质和数量(Q气) 数据
     */
    public void synTableCellMsg(){

    }
}
