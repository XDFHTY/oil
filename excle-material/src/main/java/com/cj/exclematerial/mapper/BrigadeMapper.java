package com.cj.exclematerial.mapper;

import com.cj.common.entity.Brigade;
import com.cj.common.pojo.Query;
import com.cj.exclematerial.pojo.BrigadeBo;

import java.util.List;
import java.util.Map;

public interface BrigadeMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long brigadeId);

    /**
     *
     * @mbggenerated
     */
    int insert(Brigade record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Brigade record);

    /**
     *
     * @mbggenerated
     */
    Brigade selectByPrimaryKey(Long brigadeId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Brigade record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Brigade record);

    /**
     *按照条件查询消防队信息
     * @mbggenerated
     */
    List<Map> findByQuery(Map param);

    /**
     *按照条件查询消防队分布的Excel表
     * @mbggenerated
     */
    List<BrigadeBo> selectExcelBrigade(Map param);
}