package com.cj.exclelog.mapper;

import com.cj.common.entity.LogCheck;
import com.cj.core.domain.Pager;

import java.util.List;
import java.util.Map;

public interface LogCheckMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long logCheckId);

    /**
     *
     * @mbggenerated
     */
    int insert(LogCheck record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(LogCheck record);

    /**
     *
     * @mbggenerated
     */
    LogCheck selectByPrimaryKey(Long logCheckId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LogCheck record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(LogCheck record);

    /**
     * 根据adminId查询用户名和姓名
     * @return
     */
    public Map findAdminAndInfo(long adminId);


    /**
     * 查询审核日志
     */
    public List<List<?>> findLog(Pager pager);
}