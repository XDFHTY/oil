package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Overhaul;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.mapper.OverhaulMapper;
import com.cj.exclematerial.pojo.OverhaulBo;
import com.cj.exclematerial.serveice.OverhaulService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("overhaulSever")
public class OverhaulServerImpl implements OverhaulService {

    @Resource
    OverhaulMapper overhaulMapper;

    /**
     * 查询检修队信息
     * @param param
     * @return
     */
    @Override
    public ApiResult findOverhaulByQuery(Map param) {
        ApiResult apiResult=null;
        try {
            List<Map> overhaulList=overhaulMapper.findByQuery(param);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(overhaulList);
        }catch (Exception e){
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 修改检修队信息
     * @param overhaul
     * @return
     */
    @Override
    public ApiResult updateOverhaul(Overhaul overhaul) {
        ApiResult apiResult=null;
        int i=overhaulMapper.updateByPrimaryKeySelective(overhaul);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 添加检修队信息
     * @param overhaul
     * @return
     */
    @Override
    public ApiResult addOverhaul(Overhaul overhaul) {
        ApiResult apiResult=null;
        int i=overhaulMapper.insertSelective(overhaul);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 获取检修队Excel信息
     * @param param
     * @return
     */
    @Override
    public HSSFWorkbook getOverhaulExcelByQuery(Map param) throws ClassNotFoundException {
        List<OverhaulBo> stationMaterialBos= overhaulMapper.selectExcelOverhaul(param);
        return ExcelUtil.getHSSFWorkbook(stationMaterialBos,OverhaulBo.class);
    }

    @Override
    public ApiResult deleteOverhaul(Long id) {
        ApiResult apiResult=null;
        int i=overhaulMapper.deleteByPrimaryKey(id);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }
}
