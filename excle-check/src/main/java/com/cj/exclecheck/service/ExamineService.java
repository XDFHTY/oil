package com.cj.exclecheck.service;

import com.cj.core.domain.ApiResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by XD on 2018/10/23.
 */
public interface ExamineService {
    //提交审核
    ApiResult submit(Map map, HttpServletRequest request);

    //审核 通过或驳回
    ApiResult examine(Map map, HttpServletRequest request);


    //查询审核结果
    ApiResult examineResult(String year, HttpServletRequest request);

    /**
     * 查询待审核

     */
    ApiResult unaudited(String year, HttpServletRequest request,int currentPage);

    //查询提交信息详情
    ApiResult getInfo(String year, Long stationId, HttpServletRequest request);
}
