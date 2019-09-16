package com.cj.exclepublic.mapper;

import com.cj.common.entity.AuthExcleFactory;

import java.util.Map;

public interface AuthExcleFactoryMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long authExcleFactoryId);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthExcleFactory record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthExcleFactory record);

    /**
     *
     * @mbggenerated
     */
    AuthExcleFactory selectByPrimaryKey(Long authExcleFactoryId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthExcleFactory record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthExcleFactory record);

    /**
     * 批量新增excle列表-工艺关联信息
     * @param map
     * @return
     */
    public int addList(Map map);
}