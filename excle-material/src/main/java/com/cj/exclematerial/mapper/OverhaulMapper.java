package com.cj.exclematerial.mapper;

import com.cj.common.entity.Overhaul;
import com.cj.common.pojo.Query;
import com.cj.exclematerial.pojo.OverhaulBo;

import java.util.List;
import java.util.Map;

public interface OverhaulMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long overhaulId);

    /**
     *
     * @mbggenerated
     */
    int insert(Overhaul record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Overhaul record);

    /**
     *
     * @mbggenerated
     */
    Overhaul selectByPrimaryKey(Long overhaulId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Overhaul record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Overhaul record);
    /**
     *条件查询检修队
     * @mbggenerated
     */
    List<Map> findByQuery(Map param);
    /**
     *按条件查询检修队的Excel信息
     * @mbggenerated
     */
    List<OverhaulBo> selectExcelOverhaul(Map param);
}