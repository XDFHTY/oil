package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Material;
import com.cj.common.entity.StorehouseMaterial;
import com.cj.exclematerial.mapper.MaterialMapper;
import com.cj.exclematerial.mapper.StorehouseMapper;
import com.cj.exclematerial.mapper.StorehouseMaterialMapper;
import com.cj.exclematerial.pojo.MaterialModel;
import com.cj.exclematerial.serveice.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class MaterialServerImpl implements MaterialService {


    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private StorehouseMapper storehouseMapper;
    @Autowired
    private StorehouseMaterialMapper storehouseMaterialMapper;
    @Override
    public String addMaterial(MaterialModel material) {
        //根据名字查询物资信息是否存在
        Material m = materialMapper.findByName(material.getMaterialName());
        if(m!=null){
            //查看物资是否属于气矿
            if(!m.getMaterialType().equals("1")){
                material.setMaterialType("1");
                materialMapper.updateByPrimaryKeySelective(material);
            }
        }else{
            material.setMaterialType("1");
            materialMapper.insertSelective(material);
        }
        //根据所属矿，厂ID 查询仓库
        List<Long> list = storehouseMapper.findByFactoryId(material.getFactoryId());
        for(Long id :list){
            StorehouseMaterial sm = storehouseMaterialMapper.findByid(id, material.getMaterialId());
            if(sm!=null){
                return "物资已存在";
            }else{
                StorehouseMaterial storehouseMaterial = new StorehouseMaterial();
                storehouseMaterial.setMaterialId(material.getMaterialId());
                storehouseMaterial.setStorehouseId(id);
                storehouseMaterial.setMaterialNum(0);
                storehouseMaterial.setYear(new Date());
                storehouseMaterialMapper.insertSelective(storehouseMaterial);
            }
        }
        return "处理成功";
    }
}
