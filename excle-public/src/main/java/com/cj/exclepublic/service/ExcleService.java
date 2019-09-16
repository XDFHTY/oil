package com.cj.exclepublic.service;

import com.cj.common.entity.*;
import com.cj.common.exception.UserException;
import com.cj.common.utils.vo.ExcleData;
import com.cj.core.domain.ApiResult;
import com.cj.exclepublic.domain.AddTableCellRecordsReq;
import com.cj.exclepublic.domain.UpdateTabStruReq;
import com.cj.exclepublic.domain.UpdateTableCellRecordsReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ExcleService {

    /**
     * 根据场站id和表名、年份查询 表结构及表内容
     * @param stationId
     * @param tableName
     * @return
     */
    public ApiResult findExcleStruByStIdAndTabName(Long stationId, String tableName,String year,HttpServletRequest request);

    /**
     * 导出表结构及数据
     * @param stationId
     * @param tableName
     * @param year
     * @return
     */
    public ApiResult exportTableStru(Long stationId, String tableName,String year, HttpServletResponse response) throws Exception;

    /**
     * 修改表结构
     * @param updateTabStruReq
     * @return
     */
    public ApiResult updateTabStru(UpdateTabStruReq updateTabStruReq);

    /**
     * 根据场站ID和表名称查询出表对象
     * @param stationId
     * @param tableName
     * @return
     */
    public ExcleTable findByStationIdAndTabName(Long stationId, String tableName);

    /**
     * 根据excle_table_id来查询表结构
     * @param excleTabId
     * @return
     */
    public List<ExcleTableStructure> findExcleStruByExcleTabId(Long excleTabId);

    /**
     * 根据excleTabId和年份查询TableRecord
     * @param excleTabId
     * @param year
     * @return
     */
    public TableRecord findTableRecordByExcleTabIdAndYear(Long excleTabId, String year);

    /**
     * 根据tableRecordId查询内容表
     * @param tableRecordId
     * @return
     */
    public List<TableCellRecord> findTabCellRecordListByTabRecordId(Long tableRecordId);

    /**
     * 根据tableRecordId 和 excleTableStructureId查询 内容
     * @param tableRecordId
     * @param excleTabStruId
     * @return
     */
    public TableCellRecord findTabCellRecordByTabRecordIdAndStruId(Long tableRecordId, Long excleTabStruId);

    /**
     * 传入 要插入的数据，对应的年份
     * 向表中出入数据
     * @param excleDataList
     * @param year
     * @return
     */
    public ApiResult addTableCellRocordList(Long stationId,
                                            String tableName,
                                            List<ExcleData> excleDataList,
                                            String year);

    /**
     * 修改表结构信息
     * @return
     */
    public ApiResult updateTabStru(Map map, HttpSession session);


    /**
     * 查询该场站、该表、该年是否已存在数据
     * @param json
     * @return
     */
    public ApiResult findTableRecord(String json);
    /**
     * 新增表内容
     * @param addTableCellRecordsReq
     * @param request
     * @return
     */
    public ApiResult addTableCellRecords(AddTableCellRecordsReq addTableCellRecordsReq, HttpServletRequest request) throws UserException;

    /**
     * 批量修改表内容
     * @param updateTableCellRecordsReq
     * @param request
     * @return
     */
    public ApiResult updateTableCellRecords(UpdateTableCellRecordsReq updateTableCellRecordsReq, HttpServletRequest request);

    /**
     * 根据场站ID查询填表顺序
     * @param map
     * @return
     */
    public ApiResult findExcleSort(Map map,HttpSession session);

    /**
     * 添加操作日志
     * @param logCheck
     * @return
     */
    public int addLogCheck(LogCheck logCheck,HttpServletRequest request);
}
