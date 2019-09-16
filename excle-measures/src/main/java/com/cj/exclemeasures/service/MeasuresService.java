package com.cj.exclemeasures.service;

import com.cj.common.entity.Measures;
import com.cj.common.entity.MeasuresLast;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.exclemeasures.domain.UpdateMeasuresByLastYearReq;
import com.cj.exclemeasures.domain.UpdateMeasuresByThisYearReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface MeasuresService {

    /**
     * 查询 场站 本年度 数据
     * @param pager
     * @return
     */
    public ApiResult findMeasuresByThisYear(Pager pager,HttpSession session);


    /**
     * 新增 场站 本年度 数据
     * @param measures
     * @return
     */
    public ApiResult addMeasuresByThisYear(List<Measures> measures, HttpServletRequest request);



    /**
     * 修改 场站 本年度 数据
     * @param updateMeasuresByThisYearReq
     * @return
     */
    public ApiResult updateMeasuresByThisYear(UpdateMeasuresByThisYearReq updateMeasuresByThisYearReq, HttpServletRequest request);


    /**
     * 查询 场站 上年度 数据
     * @param pager
     * @return
     */
    public ApiResult findMeasuresByLastYear(Pager pager,HttpSession session);

    /**
     * 新增 场站 上年度 数据
     * @param measuresLasts
     * @return
     */
    public ApiResult addMeasuresByLastYear(List<MeasuresLast> measuresLasts, HttpServletRequest request);


    /**
     * 修改 场站 上年度 数据
     * @param updateMeasuresByLastYearReq
     * @return
     */
    public ApiResult updateMeasuresByLastYear(UpdateMeasuresByLastYearReq updateMeasuresByLastYearReq, HttpServletRequest request);


    /**
     * 导出数据
     * @param pager
     * @param response
     */
    public void exportMeasures(Pager pager, HttpServletResponse response) throws Exception;


}
