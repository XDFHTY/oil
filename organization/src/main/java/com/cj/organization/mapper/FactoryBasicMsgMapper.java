package com.cj.organization.mapper;

import com.cj.common.entity.AdminInfo;
import com.cj.common.entity.FactoryBasicMsg;
import com.cj.core.domain.Pager;
import com.cj.organization.entity.AdminAndRole;
import com.cj.organization.entity.ExprotOrganInfo;
import com.cj.organization.entity.RespOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FactoryBasicMsgMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long factoryId);

    /**
     *
     * @mbggenerated
     */
    int insert(FactoryBasicMsg record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(FactoryBasicMsg record);

    /**
     *
     * @mbggenerated
     */
    FactoryBasicMsg selectByPrimaryKey(Long factoryId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FactoryBasicMsg record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FactoryBasicMsg record);

    //根据气矿id查询气矿基本信息和管理人信息
    List<RespOrganization> findFactoryById(Pager p);

    //统计条数
    Integer findFactoryByIdCount(Pager p);

    //根据气矿名称查重
    FactoryBasicMsg findFactoryByName(@Param("name") String name, @Param("id") Long bcId);

    //添加气矿信息 主键返回
    void addFactory(FactoryBasicMsg f);

    // 根据分公司id查询 全部组织机构名称
    List<ExprotOrganInfo> findAllName(Long branchCompanyId);

    //根据分公司id查询 查询 气矿名称
    List<ExprotOrganInfo> findFactoryName(Long branchCompanyId);

    //给管理员添加姓名
    void addFullName(@Param("adminId") Long adminId, @Param("fullName") String fullName,@Param("phone") String phone);

    //根据工艺分类名称 查询id
    Long findFactoryTypeByName(String factoryTypeName);

    //AdminInfo 根据管理员id查询是否添加了姓名
    AdminInfo findAdminInfoById(Long adminId);

    //AdminInfo  根据管理员id 修改姓名
    void updateNameById(@Param("adminId") Long adminId, @Param("fullName")String fullName,@Param("phone") String phone);

    //Admin 查询用户名是否已存在 和 角色等级
    AdminAndRole findAdminByName(String name);

    //修改气矿名称
    void updateFactoryNameById(@Param("factoryId") Integer factoryId, @Param("name")String name);
}