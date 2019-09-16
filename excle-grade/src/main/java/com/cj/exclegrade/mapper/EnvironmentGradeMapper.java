package com.cj.exclegrade.mapper;


import com.cj.common.entity.EnvironmentGrade;
import com.cj.common.pojo.Query;
import com.cj.exclegrade.pojo.EnvironmentGradeView;
import com.cj.exclegrade.pojo.FactoryEnvironmentPipelineView;
import com.cj.exclegrade.vo.StationResultVo;
import com.cj.exclegrade.vo.VoEnvironment;
import com.cj.exclegrade.vo.VoEnvironmentStation;

import java.util.List;

public interface EnvironmentGradeMapper {
    int deleteByPrimaryKey(Long environmentalGradeId);

    int insert(EnvironmentGrade record);

    int insertSelective(EnvironmentGrade record);

    EnvironmentGrade selectByPrimaryKey(Long environmentalGradeId);

    int updateByPrimaryKeySelective(EnvironmentGrade record);

    int updateByPrimaryKey(EnvironmentGrade record);

    List<EnvironmentGradeView> findByQuery(VoEnvironmentStation query);

    List<List<?>> findFactoryEnvironmentByQuery(VoEnvironment query);

    EnvironmentGrade findByDate(EnvironmentGrade environmentGrade);

    StationResultVo getResultByStationId(VoEnvironmentStation voEnvironmentStation);

    Long getTargetIdByTarget(String target);
}