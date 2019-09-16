package com.cj.exclematerial.serveice;

import com.cj.common.entity.Overhaul;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

public interface OverhaulService {

    ApiResult findOverhaulByQuery(Map param);

    ApiResult updateOverhaul(Overhaul overhaul);

    ApiResult addOverhaul(Overhaul overhaul);

    HSSFWorkbook getOverhaulExcelByQuery(Map param) throws ClassNotFoundException;

    ApiResult deleteOverhaul(Long id);
}
