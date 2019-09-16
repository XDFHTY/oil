package com.cj.exclepublic.mapper;

import com.cj.common.entity.ExcleTableFormula;

import java.util.List;

/**
* Created by Mybatis Generator 2018/11/16
*/
public interface ExcleTableFormulaMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long formulaId);

    /**
     *
     * @mbggenerated
     */
    int insert(ExcleTableFormula record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(ExcleTableFormula record);

    /**
     *
     * @mbggenerated
     */
    ExcleTableFormula selectByPrimaryKey(Long formulaId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ExcleTableFormula record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ExcleTableFormula record);

    //根据表格ID查询公式
    public List<ExcleTableFormula> findExcleTableFormula(long excleTableId);
}