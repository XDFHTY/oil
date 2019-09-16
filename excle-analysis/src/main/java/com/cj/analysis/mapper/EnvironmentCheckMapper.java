package com.cj.analysis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnvironmentCheckMapper {

    List getCheckStationId(@Param("datetime") String datetime, @Param("taskAreaIds")List<Long> taskAreaIds);
}
