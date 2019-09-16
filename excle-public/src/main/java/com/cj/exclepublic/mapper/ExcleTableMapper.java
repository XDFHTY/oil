package com.cj.exclepublic.mapper;

import com.cj.common.entity.ExcleTable;
import com.cj.common.entity.ExcleTableStructure;
import com.cj.common.entity.TableRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExcleTableMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long excleTableId);

    /**
     *
     * @mbggenerated
     */
    int insert(ExcleTable record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(ExcleTable record);

    /**
     *
     * @mbggenerated
     */
    ExcleTable selectByPrimaryKey(Long excleTableId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ExcleTable record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ExcleTable record);




    /**
     * 根据station和TableName查询出ExcleTable记录
     * @param stationId
     * @param tableName
     * @return
     */
    ExcleTable selectByStationIdAndTableName(@Param("stationId") Long stationId, @Param("tableName") String tableName);

    /**
     * 根据工艺ID和表名查询表列表信息
     */
    public ExcleTable findExcleTable(Map map);

    /**
     * 根据场站ID查询 模块填表顺序及表格对应工艺数量
     */
    public List<ExcleTable> findExcleSortAndFactoryTypeNum(long stationId);




}