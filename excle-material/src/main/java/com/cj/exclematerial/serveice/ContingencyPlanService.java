package com.cj.exclematerial.serveice;

import com.cj.common.entity.ReservePlan;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

public interface ContingencyPlanService {

    HSSFWorkbook getContingencyPlanExcelByQuery(Map param) throws ClassNotFoundException;

    ApiResult addContingencyPlan(ReservePlan reservePlan);

    ApiResult findContingencyPlan(Map param);

    ApiResult updateContingencyPlan(ReservePlan reservePlan);

    ApiResult deleteContingencyPlan(Long id);
}
