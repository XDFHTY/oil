package com.cj.analysis.mapper;

import com.cj.analysis.pojo.FactoryEnvironmentPipelineView;
import com.cj.analysis.pojo.FactoryEnvironmentView;
import com.cj.analysis.vo.VoEnvironment;
import com.cj.common.pojo.Query;

import java.util.List;
import java.util.Map;

public interface EnvironmentResultMapper {

    //分页查询点型 环境风险评估汇总
    List<List<?>> findFactoryEnvironment(VoEnvironment query);


    //分页查询线型 环境风险评估 汇总信息
    List<FactoryEnvironmentPipelineView> findPipelineEnvironment(VoEnvironment query);


    List<FactoryEnvironmentView> findFactoryEnvironmentByQuery(VoEnvironment query);

    List<FactoryEnvironmentPipelineView> findPipelineEnvironmentByQuery(VoEnvironment query);


    int countFactoryEnvironment(VoEnvironment query);

    //条件查询线型总条数
    int countPipelineEnvironment(VoEnvironment query);
}
