package com.cj.exclematerial.mapper;

import com.cj.common.entity.Storehouse;
import com.cj.common.pojo.Query;
import com.cj.exclematerial.pojo.StorehouseBo;

import java.util.List;
import java.util.Map;

public interface StorehouseMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long storehouseId);

    /**
     *
     * @mbggenerated
     */
    int insert(Storehouse record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Storehouse record);

    /**
     *
     * @mbggenerated
     */
    Storehouse selectByPrimaryKey(Long storehouseId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Storehouse record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Storehouse record);

    /**
     *按条件查询储备库
     * @param param 查询条件
     * @return List<Storehouse>
     */
    List<Map> findByQuery(Map param);

    /**
     *按条件查询储备库的Excel信息
     * @return
     */
    List<StorehouseBo> selectExcelStorehouse(Map param);

    /**
     * 根据所属矿，厂ID 查询仓库id
     * @param id
     * @return
     */
    List<Long> findByFactoryId(Long id);
}