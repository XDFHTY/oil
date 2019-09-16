package com.cj.organization.mapper;

import com.cj.common.entity.TaskArea;
import com.cj.core.domain.Pager;
import com.cj.organization.entity.ExprotOrganInfo;
import com.cj.organization.entity.RespOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskAreaMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long taskAreaId);

    /**
     *
     * @mbggenerated
     */
    int insert(TaskArea record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(TaskArea record);

    /**
     *
     * @mbggenerated
     */
    TaskArea selectByPrimaryKey(Long taskAreaId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskArea record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskArea record);

    //根据气矿Id、作业区id 查询 气矿基本信息、作业区和管理人信息
    List<RespOrganization> findAreaById(Pager p);

    //计数
    Integer findAreaByIdCount(Pager p);

    //根据气矿id和作业区名称查重
    TaskArea findAreaByName(@Param("name") String name, @Param("id") Long aLong);

    //添加作业区信息 主键返回
    void addArea(TaskArea t);

    //根据分公司id查询 查询 气矿、作业区名称
    List<ExprotOrganInfo> findFactoryAndAreaName(Long branchCompanyId);

    //根据气矿id查询所有作业区
    List<TaskArea> findAreaByFactoryId(Integer factoryId);

    //修改作业区名称
    void updateAreaNameById(@Param("areaId") Integer areaId, @Param("name") String name);
}