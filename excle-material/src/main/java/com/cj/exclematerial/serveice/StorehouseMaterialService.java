package com.cj.exclematerial.serveice;

import com.cj.common.entity.StorehouseMaterial;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

public interface StorehouseMaterialService {
    ApiResult addStorehouseMaterial(StorehouseMaterial storehouseMaterial);
    ApiResult findStorehouseMaterialByQuery(Map param);
    ApiResult updateStorehouseMaterial(StorehouseMaterial storehouseMaterial);

    HSSFWorkbook getStorehouseMaterialExcelByQuery(Map param) throws ClassNotFoundException;
}
