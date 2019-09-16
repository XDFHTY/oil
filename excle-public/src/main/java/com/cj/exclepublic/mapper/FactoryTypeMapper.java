package com.cj.exclepublic.mapper;

import com.cj.common.entity.FactoryType;
import com.cj.exclepublic.domain.FindOrganizationResp;

import java.util.List;

public interface FactoryTypeMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long factoryTypeId);

    /**
     *
     * @mbggenerated
     */
    int insert(FactoryType record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(FactoryType record);

    /**
     *
     * @mbggenerated
     */
    FactoryType selectByPrimaryKey(Long factoryTypeId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FactoryType record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FactoryType record);

    /**
     * 根据名称查询实体
     */
    public List<FactoryType> findFactoryTypeByName(String[] fileNames);

    //根据场站ID查询组织结构信息
    public FindOrganizationResp findOrganization(Long stationId);
}