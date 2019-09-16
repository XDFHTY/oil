package com.cj.exclematerial.mapper;

import com.cj.common.entity.StorehouseMaterial;
import com.cj.common.pojo.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorehouseMaterialMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long storehouseMaterialId);

    /**
     *
     * @mbggenerated
     */
    int insert(StorehouseMaterial record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(StorehouseMaterial record);

    /**
     *
     * @mbggenerated
     */
    StorehouseMaterial selectByPrimaryKey(Long storehouseMaterialId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StorehouseMaterial record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StorehouseMaterial record);

    /**
     * 根据储备库id 物资id 查询是否已有
     */
    StorehouseMaterial findByid(@Param("storehouseId") Long storehouseId,
                                @Param("materialId") Long materialId);
}