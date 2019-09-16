package com.cj.exclematerial.serveice;

import com.cj.common.entity.Storehouse;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

public interface StorehouseService {
    ApiResult addStorehouse(Storehouse storehouse);
    ApiResult findStorehouses(Map param);
    ApiResult updateStorehous(Storehouse storehouse);

    HSSFWorkbook getStorehouseExcelByQuery(Map param) throws ClassNotFoundException;

    ApiResult deleteStorehouse(Long id);
}
