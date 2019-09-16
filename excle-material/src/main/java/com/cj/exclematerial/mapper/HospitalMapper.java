package com.cj.exclematerial.mapper;

import com.cj.common.entity.Hospital;
import com.cj.common.pojo.Query;
import com.cj.exclematerial.pojo.HospitalBo;

import java.util.List;
import java.util.Map;

public interface HospitalMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long hospitalId);

    /**
     *
     * @mbggenerated
     */
    int insert(Hospital record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Hospital record);

    /**
     *
     * @mbggenerated
     */
    Hospital selectByPrimaryKey(Long hospitalId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Hospital record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Hospital record);

    /**
     *按条件查询医疗机构分布情况
     * @mbggenerated
     */
    List<Hospital> findByQuery(Map param);

    /**
     * 按条件查询医疗机构EXcel分布情况
     * @param param
     * @return
     */
    List<HospitalBo> selectExcelHospital(Map param);
}