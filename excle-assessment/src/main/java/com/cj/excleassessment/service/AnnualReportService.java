package com.cj.excleassessment.service;

import com.cj.core.domain.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 年度环境风险报告上传 接口
 * Created by XD on 2018/10/16.
 */
public interface AnnualReportService {

    //保存 报告文件
    ApiResult save(Map map, HttpServletRequest request);

    //根据年份查询全部报告
    ApiResult getAllByYear(String year,HttpServletRequest request);

    //逻辑删除  只可以删除自己管辖区域的报告
    ApiResult delete( Long annualReportId, HttpServletRequest request);

    //pdf文件下载  各个气矿和分公司之间可以相互下载
    ApiResult getReportPdf(Long annualReportId, HttpServletRequest request,HttpServletResponse response);
}
