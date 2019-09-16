package com.cj.excleledger.service;

import com.cj.core.domain.ApiResult;
import com.cj.exclepublic.domain.AddTableCellRecordsReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 年度台账模块
 * Created by XD on 2018/10/17.
 */
public interface LedgerService {
    //生成台账
    ApiResult generateLedger(Map map,Boolean isExcel, HttpServletRequest request);

    //导出台账
    ApiResult ledgerExport(Map map, HttpServletResponse response, HttpServletRequest request);

    //新增或修改 台账数据
    ApiResult updateLedgerData(List<AddTableCellRecordsReq> addTableCellRecordsReqs, HttpServletRequest request);
}
