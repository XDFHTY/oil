package com.cj.exclematerial.mapper;

import com.cj.common.entity.Material;
import com.cj.common.pojo.StationMaterialVo;
import com.cj.exclematerial.pojo.StationMaterialBo;
import com.cj.exclematerial.pojo.StationMaterialDto;
import com.cj.exclematerial.pojo.StorehouseMaterialBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MaterialMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long materialId);

    /**
     *
     * @mbggenerated
     */
    int insert(Material record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Material record);

    /**
     *
     * @mbggenerated
     */
    Material selectByPrimaryKey(Long materialId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Material record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Material record);

    /**
     *查询储物库的物资分布表
     * @mbggenerated
     */
    List<Material> selectStorehouseMaterial(Map param);

    /**
     * 查询场站物资调查表
     * @mbggenerated
     */
    List<StationMaterialDto> selectStationMaterial(Map param);

    /**
     * 查找场站物资信息调查Excel表
     * @mbggenerated
     */
    List<StationMaterialBo> selectExcelStationMaterial(@Param("stationId")long stationId,@Param("datetime")String datetime);

    /**
     * 导出储备库物资Excel
     * @mbggenerated
     */
    List<StorehouseMaterialBo> selectExcelStorehouseMaterial(Map param);

    /**
     * 根据物资名字获取物资ID
     * @param materialName
     * @return
     */
    Long findMaterialByName(String materialName);

    /**
     * 根据物资名字获取物资信息
     * @param materialName
     * @return
     */
    Material findByName(String materialName);
}