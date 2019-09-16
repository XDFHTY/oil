package com.cj.organization.mapper;

import com.cj.common.entity.BranchCompany;

public interface BranchCompanyMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long branchCompanyId);

    /**
     *
     * @mbggenerated
     */
    int insert(BranchCompany record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(BranchCompany record);

    /**
     *
     * @mbggenerated
     */
    BranchCompany selectByPrimaryKey(Long branchCompanyId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BranchCompany record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BranchCompany record);

    //根据名称查重
    BranchCompany findBCompanyByName(String str);

    //添加  主键返回
    void addBCompany(BranchCompany bc);
}