package com.cj.exclepublic.mapper;

import com.cj.common.entity.ExcleTableStructure;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExcleTableStructureMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long excleTableStructureId);

    /**
     *
     * @mbggenerated
     */
    int insert(ExcleTableStructure record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(ExcleTableStructure record);

    /**
     *
     * @mbggenerated
     */
    ExcleTableStructure selectByPrimaryKey(Long excleTableStructureId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ExcleTableStructure record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ExcleTableStructure record);


    /**
     * 根据站点id和表名查询表结构
     * @param stationId
     * @param tableName
     * @return
     */
    List<ExcleTableStructure> findExcleStruByStIdAndTableName(@Param("stationId") Long stationId, @Param("tableName") String tableName);

    /**
     * 根据工艺ID和excle表名称查询表结构信息
     * @param map
     * @return
     */
    List<ExcleTableStructure> findExcleStruByFactoryTypeIdAndTableName(Map map);

//    根据excle表列表ID查询excle表结构信息
    List<ExcleTableStructure> findExcleStruByExcleTabId(Long excleTableId);


    /**
     * 修改表结构，新增表结构
     * @param excleTableStructures
     * @return
     */
    public int updateTabStru(List<ExcleTableStructure> excleTableStructures);

    /**
     * 根据主键修改单元格内容
     * @param excleTableStructures
     * @return
     */
    public int updateTabStruById(List<ExcleTableStructure> excleTableStructures);

    /**
     * 根据excleTableId删除表结构信息
     * @param excleTableId
     * @return
     */
    public int deleteTabStruByExcleTableId(long excleTableId);



}