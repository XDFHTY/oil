package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Environmental;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.mapper.EnvironmentalMapper;
import com.cj.exclematerial.pojo.EnvironmentalBo;
import com.cj.exclematerial.serveice.EnvironmentalService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("environmentalServer")
public class EnvironmentalServerImpl implements EnvironmentalService {

    @Resource
    EnvironmentalMapper environmentalMapper;

    /**
     * 查看环境机构信息
     * @param param
     * @return
     */
    @Override
    public ApiResult findEnvironmental(Map param) {
        ApiResult apiResult=null;
        try {
            List<Map> storehouseList=environmentalMapper.findByQuery(param);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(storehouseList);
        }catch (Exception e){
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 添加环境机构信息
     * @param environmental
     * @return
     */
    @Override
    public ApiResult addEnvironmental(Environmental environmental) {
        ApiResult apiResult=null;
        int i=environmentalMapper.insertSelective(environmental);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 修改环境机构信息
     * @param environmental
     * @return
     */
    @Override
    public ApiResult updateEnvironmental(Environmental environmental) {
        ApiResult apiResult=null;
        int i=environmentalMapper.updateByPrimaryKeySelective(environmental);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 获取环境机构的Excel信息
     * @param param
     * @return
     */
    @Override
    public HSSFWorkbook getEnvironmentalExcelByQuery(Map param) throws ClassNotFoundException {
        List<EnvironmentalBo> stationMaterialBos= environmentalMapper.selectExcelEnvironmental(param);
        return ExcelUtil.getHSSFWorkbook(stationMaterialBos,EnvironmentalBo.class);
    }

    @Override
    public ApiResult deleteEnvironmental(Long id) {
        ApiResult apiResult=null;
        int i=environmentalMapper.deleteByPrimaryKey(id);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }
}
