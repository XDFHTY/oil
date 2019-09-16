package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Storehouse;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.mapper.StorehouseMapper;
import com.cj.exclematerial.pojo.StationMaterialBo;
import com.cj.exclematerial.pojo.StorehouseBo;
import com.cj.exclematerial.serveice.StorehouseService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("storehouseServer")
public class StorehouseServerImpl implements StorehouseService {

    @Resource
    StorehouseMapper storehouseMapper;

    /**
     * 添加储物库信息
     * @param storehouse
     * @return
     */
    @Override
    public ApiResult addStorehouse(Storehouse storehouse) {
        ApiResult apiResult=null;
        int i=storehouseMapper.insertSelective(storehouse);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 查询储备库信息
     * @param param
     * @return
     */

    @Override
    public ApiResult findStorehouses(Map param) {
        ApiResult apiResult=null;
        try {
            List<Map> storehouseList=storehouseMapper.findByQuery(param);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(storehouseList);
        }catch (Exception e){
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 修改储备库信息
     * @param storehouse
     * @return
     */
    @Override
    public ApiResult updateStorehous(Storehouse storehouse) {
        ApiResult apiResult=null;
        int i=storehouseMapper.updateByPrimaryKeySelective(storehouse);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 导出应急物资储备库Excle
     * @param param
     * @return
     */
    @Override
    public HSSFWorkbook getStorehouseExcelByQuery(Map param) throws ClassNotFoundException {
        List<StorehouseBo> stationMaterialBos= storehouseMapper.selectExcelStorehouse(param);

        int num = 1;
        for (StorehouseBo storehouseBo : stationMaterialBos) {
            storehouseBo.setNum(num++);
        }
        return ExcelUtil.getHSSFWorkbook(stationMaterialBos,StorehouseBo.class);
    }

    @Override
    public ApiResult deleteStorehouse(Long id) {
        ApiResult apiResult=null;
        int i=storehouseMapper.deleteByPrimaryKey(id);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

}
