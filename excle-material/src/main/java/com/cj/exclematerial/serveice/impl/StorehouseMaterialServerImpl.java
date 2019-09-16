package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Material;
import com.cj.common.entity.StorehouseMaterial;
import com.cj.common.pojo.StorhouseMaterialVo;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.mapper.MaterialMapper;
import com.cj.exclematerial.mapper.StorehouseMaterialMapper;
import com.cj.exclematerial.pojo.ListBo;
import com.cj.exclematerial.pojo.StorehouseMaterialBo;
import com.cj.exclematerial.serveice.StorehouseMaterialService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "storehouseMaterialServer")
@Transactional
public class StorehouseMaterialServerImpl implements StorehouseMaterialService {

    @Resource
    StorehouseMaterialMapper storehouseMaterialMapper;

    @Resource
    MaterialMapper materialMapper;

    @Override
    public ApiResult addStorehouseMaterial(StorehouseMaterial storehouseMaterial) {
        ApiResult apiResult=null;
        int i=storehouseMaterialMapper.insertSelective(storehouseMaterial);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 查询储备库物资
     * @param param
     * @return
     */
    @Override
    public ApiResult findStorehouseMaterialByQuery(Map param) {
        ApiResult apiResult=null;
        List<Material> storehouseMaterialList=materialMapper.selectStorehouseMaterial(param);
        /**
         * 计算物资总数
         */
        for (int i = 0; i <storehouseMaterialList.size() ; i++) {
            Material material=storehouseMaterialList.get(i);
            List<StorhouseMaterialVo> list=material.getStorhouseMaterialVoList();
            int num=0;
            for (int j = 0; j < list.size(); j++) {
                StorhouseMaterialVo storhouseMaterialVo=list.get(j);
                num+=storhouseMaterialVo.getMaterialNum();
            }
            material.setTotal(num);
        }
        apiResult=ApiResult.SUCCESS();
        apiResult.setData(storehouseMaterialList);
        return apiResult;
    }

    @Override
    public ApiResult updateStorehouseMaterial(StorehouseMaterial storehouseMaterial) {
        ApiResult apiResult=null;
        int i=storehouseMaterialMapper.updateByPrimaryKeySelective(storehouseMaterial);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 导出储备库物资Excel
     * @param param
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public HSSFWorkbook getStorehouseMaterialExcelByQuery(Map param) throws ClassNotFoundException {
        List<StorehouseMaterialBo> stationMaterialBos= materialMapper.selectExcelStorehouseMaterial(param);
        /**
         * 计算物资总数
         */
        for (StorehouseMaterialBo storehouseMaterialBo : stationMaterialBos){

            int num = 0;
            List<ListBo> listBos0 = storehouseMaterialBo.getListBos();
            for (ListBo listBo0 : listBos0){
                num += Integer.valueOf(listBo0.getObFieldValue());
            }
            ListBo listBo = new ListBo();
            listBo.setObExcelField("合计");
            listBo.setObFieldValue(num+"");

            List<ListBo> listBos = new ArrayList<>();
            listBos.add(listBo);
            listBos.addAll(listBos0);
            storehouseMaterialBo.setListBos(listBos);
        }



        return ExcelUtil.getHSSFWorkbook(stationMaterialBos,StorehouseMaterialBo.class);
    }
}
