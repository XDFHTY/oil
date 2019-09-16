package com.cj.exclematerial.serveice;

import com.cj.common.entity.Brigade;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

public interface BrigadeService {

    ApiResult findBrigade(Map param);

    ApiResult addBrigade(Brigade brigade);

    ApiResult updateBrigade(Brigade brigade);

    HSSFWorkbook getBrigadeExcelByQuery(Map param) throws ClassNotFoundException;

    ApiResult deleteBrigadeById(Long id);
}
