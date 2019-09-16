package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Hospital;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.mapper.HospitalMapper;
import com.cj.exclematerial.pojo.HospitalBo;
import com.cj.exclematerial.serveice.HospitalService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("hospitalServer")
public class HospitalServerImpl implements HospitalService {
    @Resource
    HospitalMapper hospitalMapper;

    /**
     * 获取医疗机构信息的数据
     * @param param
     * @return
     */
    @Override
    public ApiResult findHospital(Map param) {
        ApiResult apiResult=null;
        try {
            List<Hospital> storehouseList=hospitalMapper.findByQuery(param);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(storehouseList);
        }catch (Exception e){
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 添加医疗结构信息
     * @param hospital
     * @return
     */
    @Override
    public ApiResult addHospital(Hospital hospital) {
        ApiResult apiResult=null;
        int i=hospitalMapper.insertSelective(hospital);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 修改医疗机构信息
     * @param hospital
     * @return
     */
    @Override
    public ApiResult updateHospital(Hospital hospital) {
        ApiResult apiResult=null;
        int i=hospitalMapper.updateByPrimaryKeySelective(hospital);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 获取医疗机构Excel信息
     * @param param
     * @return
     */
    @Override
    public HSSFWorkbook getHospitalExcelByQuery(Map param) throws ClassNotFoundException {
        List<HospitalBo> hospitalBos= hospitalMapper.selectExcelHospital(param);
        return ExcelUtil.getHSSFWorkbook(hospitalBos,HospitalBo.class);
    }

    @Override
    public ApiResult deleteHospital(Long id) {
        ApiResult apiResult=null;
        int i=hospitalMapper.deleteByPrimaryKey(id);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }
}
