package com.cj.analysis.server;

import com.cj.analysis.vo.VoChart;
import com.cj.core.domain.ApiResult;


public interface EnvironmentGradeChartServer {
    ApiResult findTaskAreaChart(VoChart param);
}
