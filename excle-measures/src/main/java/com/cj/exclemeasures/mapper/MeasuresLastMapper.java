package com.cj.exclemeasures.mapper;

import com.cj.common.entity.MeasuresLast;
import com.cj.core.domain.Pager;

import java.util.List;

public interface MeasuresLastMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long lastMeasuresId);

    /**
     *
     * @mbggenerated
     */
    int insert(MeasuresLast record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(MeasuresLast record);

    /**
     *
     * @mbggenerated
     */
    MeasuresLast selectByPrimaryKey(Long lastMeasuresId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MeasuresLast record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MeasuresLast record);


    /**
     * 查询 场站 上年度 数据
     * @param pager
     * @return
     */
    public List<List<?>> findMeasuresByLastYear(Pager pager);


    /**
     * 新增 场站 上年度 数据
     * @param measuresLasts
     * @return
     */
    public int addMeasuresByLastYear(List<MeasuresLast> measuresLasts);


    /**
     * 编辑 场站 上年度 数据
     * @param measuresLasts
     * @return
     */
    public int updateMeasuresByLastYear(List<MeasuresLast> measuresLasts);
}