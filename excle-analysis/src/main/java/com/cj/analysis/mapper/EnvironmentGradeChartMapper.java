package com.cj.analysis.mapper;

import com.cj.analysis.vo.VoChart;

import java.util.Map;

public interface EnvironmentGradeChartMapper {
    Map findTaskAreaChart(VoChart param);
}
