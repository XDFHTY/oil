package com.cj.exclepublic.mapper;

import com.cj.common.entity.TableCellRecord;
import com.cj.exclepublic.domain.TableCellRecordsByCol;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TableCellRecordMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long tableCellRecordId);

    /**
     *
     * @mbggenerated
     */
    int insert(TableCellRecord record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(TableCellRecord record);

    /**
     *
     * @mbggenerated
     */
    TableCellRecord selectByPrimaryKey(Long tableCellRecordId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableCellRecord record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableCellRecord record);

    /**
     * 根据tableRecordId查询表内容
     * @param tabRecordId
     * @return
     */
    List<TableCellRecord> selectByTabRecordId(Long tabRecordId);

    /**
     * 根据tableRecordId 和 excleTableStructureId查询 内容
     * @param tableRecordId
     * @param excleTabStruId
     * @return
     */
    TableCellRecord selectByTabRecordIdAndStruId(@Param("tableRecordId") Long tableRecordId, @Param("excleTabStruId") Long excleTabStruId);

    /**
     * 批量新增单元格内容
     * @param tableCellRecords
     * @return
     */
    public int addTableCellRecords(List<TableCellRecord> tableCellRecords);

    /**
     * 根据场站ID,年份，表名查询表内容
     * @param map
     * @return
     */
    public List<TableCellRecord> findTableCellRecords(Map map);


    public List<TableCellRecordsByCol> findTableCellRecordsByCol(Map map);

    /**
     * 批量修改表内容
     * @param tableCellRecords
     * @return
     */
    public int updateTableCellRecords(List<TableCellRecord> tableCellRecords);

    /**
     * 根据场站ID,表名称,年份,审核状态查询表内容
     * @param map
     * @return
     */
    public List<TableCellRecord> findTableCellRecordsByState(Map map);
}