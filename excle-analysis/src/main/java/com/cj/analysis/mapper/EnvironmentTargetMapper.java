package com.cj.analysis.mapper;


import com.cj.analysis.pojo.EnvironmentTarget;

public interface EnvironmentTargetMapper {
    int deleteByPrimaryKey(Long environmentTargetId);

    int insert(EnvironmentTarget record);

    int insertSelective(EnvironmentTarget record);

    EnvironmentTarget selectByPrimaryKey(Long environmentTargetId);

    int updateByPrimaryKeySelective(EnvironmentTarget record);

    int updateByPrimaryKey(EnvironmentTarget record);
}