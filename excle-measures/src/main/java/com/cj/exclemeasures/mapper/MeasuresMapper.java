package com.cj.exclemeasures.mapper;

import com.cj.common.entity.Measures;
import com.cj.core.domain.Pager;

import java.util.List;

public interface MeasuresMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long measuresId);

    /**
     *
     * @mbggenerated
     */
    int insert(Measures record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Measures record);

    /**
     *
     * @mbggenerated
     */
    Measures selectByPrimaryKey(Long measuresId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Measures record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Measures record);

    /**
     * 查询 场站 本年度 数据
     * @param pager
     * @return
     */
    public List<List<?>> findMeasuresByThisYear(Pager pager);

    /**
     * 新增 场站 本年度 数据
     * @param measures
     * @return
     */
    public int addMeasuresByThisYear(List<Measures> measures);

    /**
     * 根据主键编辑 场站 本年度 数据
     * @param measures
     * @return
     */
    public int updateMeasuresByThisYear(List<Measures> measures);

}