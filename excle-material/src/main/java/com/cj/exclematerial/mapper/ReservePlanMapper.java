package com.cj.exclematerial.mapper;

import com.cj.common.entity.ReservePlan;
import com.cj.common.pojo.Query;
import com.cj.exclematerial.pojo.ReservePlanBo;

import java.util.List;
import java.util.Map;

public interface ReservePlanMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long reservePlanId);

    /**
     *
     * @mbggenerated
     */
    int insert(ReservePlan record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(ReservePlan record);

    /**
     *
     * @mbggenerated
     */
    ReservePlan selectByPrimaryKey(Long reservePlanId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ReservePlan record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ReservePlan record);

    /**
     *按条件查询应急预案情况Excel
     * @mbggenerated
     */
    List<ReservePlanBo> selectExcelReservePlan(Map param);

    /**
     * 按条件查询应急预案情况
     * @param param
     * @return
     */
    List<ReservePlan> findByQuery(Map param);
}