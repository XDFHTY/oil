package com.cj.common.mapper;

import com.cj.common.entity.AuthCustomerRole;
import com.cj.common.pojo.Organization;

import java.util.List;

public interface AuthCustomerRoleMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int insert(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    AuthCustomerRole selectByPrimaryKey(Long id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthCustomerRole record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthCustomerRole record);

    /**
     * 统计使用此角色的用户ID
     * @return
     */
    public int findCustomerNumByRoleId(Long roleId);

    //分公司级  查询全部组织机构
    List<Organization> findOrganizationByBC();

    //气矿级  根据adminId查询所管理的机构
    List<Organization> findOrganizationByFactory(Long adminId);

    //作业区级  根据adminId查询所管理的机构
    List<Organization> findOrganizationByTaskArea(Long adminId);

    //场站级  根据adminId查询所管理的机构
    List<Organization> findOrganizationByStation(Long adminId);
}