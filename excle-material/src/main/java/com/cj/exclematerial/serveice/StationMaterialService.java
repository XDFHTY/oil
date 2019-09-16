package com.cj.exclematerial.serveice;

import com.cj.common.entity.StationMaterial;
import com.cj.common.pojo.StationMaterialVo;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.pojo.StationMaterialEditVo;
import com.cj.exclematerial.pojo.StationMaterialQo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface StationMaterialService {

    ApiResult addStationMaterial(StationMaterial stationMaterial);

    //查询场站附加物资数量
    ApiResult findStationMaterialByQuery(Map param);

    ApiResult updateOrSaveStationMaterial(StationMaterialEditVo stationMaterialEditVo);
    ApiResult addStationMaterials(List<StationMaterial> stationMaterials);


    //导出
    HSSFWorkbook getStationMaterialExcelByQuery(long stationId,String datetime, HttpServletRequest request) throws ClassNotFoundException;

    ApiResult deleteStationMaterial(Long stationMaterialId);

    ApiResult findDynamicStationMaterial(StationMaterialQo stationMaterialQo, HttpServletRequest request);

    ApiResult addMaterial(StationMaterialEditVo stationMaterialEditVo);
}
