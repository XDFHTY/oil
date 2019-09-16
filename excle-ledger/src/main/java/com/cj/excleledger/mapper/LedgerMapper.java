package com.cj.excleledger.mapper;


import com.cj.common.entity.EnvironmentGrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LedgerMapper {

    //根据需查询的结果类型 场站id 年份 查询
    EnvironmentGrade findRiskGrade(@Param("targent")String targent,
                                   @Param("stationId")Integer stationId,
                                   @Param("year")String year);

    //根据场站id 查询作业区名称 场站名称 气矿名称
    Map findStationName(Integer stationId);

    //根据场站id  查询工艺分类名称
    String findFactoryTypeNameById(Integer stationId);

    //根据场站id和年份查询本年度 环境风险防控措施表
    List<Map> findMeasuresByIdAndYear(@Param("stationId")Integer stationId, @Param("year")String year);



}