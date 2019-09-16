package com.cj.exclematerial.serveice;

import com.cj.common.entity.Hospital;
import com.cj.core.domain.ApiResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

public interface HospitalService {
    ApiResult findHospital(Map param);

    ApiResult addHospital(Hospital hospital);

    ApiResult updateHospital(Hospital hospital);

    HSSFWorkbook getHospitalExcelByQuery(Map param) throws ClassNotFoundException;

    ApiResult deleteHospital(Long id);
}
