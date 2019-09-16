package com.cj.exclematerial.adapter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 黄维
 * @date 2018/12/10 13:22
 **/
public interface ExcelDto {
    List getExcelDto(Long stationId, String tableName, String year, HttpServletRequest request);

}
