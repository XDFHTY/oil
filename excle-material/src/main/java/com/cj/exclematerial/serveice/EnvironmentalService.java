package com.cj.exclematerial.serveice;

import com.cj.common.entity.Environmental;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

public interface EnvironmentalService {
    ApiResult findEnvironmental(Map param);

    ApiResult addEnvironmental(Environmental environmental);

    ApiResult updateEnvironmental(Environmental environmental);

    HSSFWorkbook getEnvironmentalExcelByQuery(Map param) throws ClassNotFoundException;

    ApiResult deleteEnvironmental(Long id);
}
