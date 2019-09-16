package com.cj.exclematerial.mapper;

import com.cj.common.entity.Environmental;
import com.cj.common.pojo.Query;
import com.cj.exclematerial.pojo.EnvironmentalBo;

import java.util.List;
import java.util.Map;

public interface EnvironmentalMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long environmentalId);

    /**
     *
     * @mbggenerated
     */
    int insert(Environmental record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Environmental record);

    /**
     *
     * @mbggenerated
     */
    Environmental selectByPrimaryKey(Long environmentalId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Environmental record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Environmental record);

    /**
     * 按照Query条件查询环境机构的分布情况
     * @param param
     * @return
     */
    List<Map> findByQuery(Map param);

    /**
     * 按照Query条件查询Excel环境机构分布
     * @return
     */
    List<EnvironmentalBo> selectExcelEnvironmental(Map param);
}