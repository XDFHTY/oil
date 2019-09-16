package com.cj.exclematerial.mapper;

import com.cj.common.entity.StationMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationMaterialMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long stationMaterialId);

    /**
     *
     * @mbggenerated
     */
    int insert(StationMaterial record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(StationMaterial record);

    /**
     *
     * @mbggenerated
     */
    StationMaterial selectByPrimaryKey(Long stationMaterialId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StationMaterial record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StationMaterial record);

    /**
     * 批量添加场站物资调查信息
     * @param stationMaterials
     * @return
     */
    int addStationMaterialList(List<StationMaterial> stationMaterials);

    Long selectStationMaterialId(StationMaterial stationMaterial);

    //根据场站ID和物资id和年份检查此场站、此物资、次年是否已存在
    public StationMaterial findStationIdAndMaterialId(@Param("stationId") long stationId,
                                                      @Param("materialId") long materialId,
                                                      @Param("year") String datetime);
}