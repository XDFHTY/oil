package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.ReservePlan;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.mapper.ReservePlanMapper;
import com.cj.exclematerial.pojo.ReservePlanBo;
import com.cj.exclematerial.serveice.ContingencyPlanService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("contingencyPlanServer")
public class ContingencyPlanServerImpl implements ContingencyPlanService {


    @Resource
    ReservePlanMapper reservePlanMapper;

    /**
     * 获取预案信息的Excel信息
     * @param param
     * @return
     */
    @Override
    public HSSFWorkbook getContingencyPlanExcelByQuery(Map param) throws ClassNotFoundException {
        List<ReservePlanBo> reservePlanBos= reservePlanMapper.selectExcelReservePlan(param);
        return ExcelUtil.getHSSFWorkbook(reservePlanBos, ReservePlanBo.class);
    }

    /**
     * 添加预案信息
     * @param reservePlan
     * @return
     */
    @Override
    public ApiResult addContingencyPlan(ReservePlan reservePlan) {
        ApiResult apiResult=null;
        int i=reservePlanMapper.insertSelective(reservePlan);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 通过条件查看预案情况信息
     * @param param
     * @return
     */
    @Override
    public ApiResult findContingencyPlan(Map param) {
        ApiResult apiResult=null;
        try {
            List<ReservePlan> reservePlans=reservePlanMapper.findByQuery(param);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(reservePlans);
        }catch (Exception e){
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 修改预案情况信息
     * @param reservePlan
     * @return
     */
    @Override
    public ApiResult updateContingencyPlan(ReservePlan reservePlan) {
        ApiResult apiResult=null;
        int i=reservePlanMapper.updateByPrimaryKeySelective(reservePlan);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    @Override
    public ApiResult deleteContingencyPlan(Long id) {
        ApiResult apiResult=null;
        int i=reservePlanMapper.deleteByPrimaryKey(id);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }
}
