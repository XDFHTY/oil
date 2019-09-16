package com.cj.analysis.server;

import com.cj.analysis.vo.VoEnvironment;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface EnvironmentResultServer {
    ApiResult findFactoryEnvironmentGrade(VoEnvironment query);

    HSSFWorkbook getFactoryEnvironmentExcelByQuery(VoEnvironment query);

    //分页查询线型 环境风险评估 汇总信息
    ApiResult getPipelineEnvironmentGradeByQuery(VoEnvironment query);
}
