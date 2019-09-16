package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Material;
import com.cj.common.entity.StationMaterial;
import com.cj.common.exception.UserException;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.adapter.ExcelServiceAdapter;
import com.cj.exclematerial.mapper.MaterialMapper;
import com.cj.exclematerial.mapper.StationMaterialMapper;
import com.cj.exclematerial.pojo.StationMaterialBo;
import com.cj.exclematerial.pojo.StationMaterialDto;
import com.cj.exclematerial.pojo.StationMaterialEditVo;
import com.cj.exclematerial.pojo.StationMaterialQo;
import com.cj.exclematerial.serveice.StationMaterialService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service(value = "stationMaterialServer")
public class StationMaterialServerImpl implements StationMaterialService {

    static final private String tableName = "作业场所应急物资调查";
    @Resource
    MaterialMapper materialMapper;

    @Resource
    StationMaterialMapper stationMaterialMapper;

    @Autowired
    ExcelServiceAdapter excelServiceAdapter;

    /**
     * 添加单个场站物资信息
     *
     * @param stationMaterial
     * @return
     */
    @Override
    public ApiResult addStationMaterial(StationMaterial stationMaterial) {
        ApiResult apiResult = null;
        int i = stationMaterialMapper.insertSelective(stationMaterial);
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 查询场站物资信息
     *
     * @param param
     * @return
     */
    @Override
    public ApiResult findStationMaterialByQuery(Map param) {
        ApiResult apiResult = null;
        try {
            List<StationMaterialDto> stationMaterials = materialMapper.selectStationMaterial(param);
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(stationMaterials);
        } catch (Exception e) {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 添加或修改场站物资信息
     *
     * @param stationMaterialEditVo
     * @return
     */
    @Override
    public ApiResult updateOrSaveStationMaterial(StationMaterialEditVo stationMaterialEditVo) {
        ApiResult apiResult = null;
        StationMaterial stationMaterial = new StationMaterial();
        Material material = new Material();
        BeanUtils.copyProperties(stationMaterialEditVo, stationMaterial);
        BeanUtils.copyProperties(stationMaterialEditVo, material);
        //先通过物资名字查物资，查找物资就执行修改物资的操作
        Long materialId = materialMapper.findMaterialByName(stationMaterialEditVo.getMaterialName());
        if (materialId == null) {
            materialMapper.insertSelective(material);
            stationMaterial.setMaterialId(material.getMaterialId());
        } else {
            material.setMaterialId(materialId);
            materialMapper.updateByPrimaryKeySelective(material);
            stationMaterial.setMaterialId(materialId);
        }
        //转换日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(stationMaterialEditVo.getDatetime(), pos);
        stationMaterial.setYear(strtodate);
        Long stationMaterialId = stationMaterialMapper.selectStationMaterialId(stationMaterial);
        int i;
        if (stationMaterialId == null) {
            i = stationMaterialMapper.insertSelective(stationMaterial);
        } else {
            stationMaterial.setStationMaterialId(stationMaterialId);
            i = stationMaterialMapper.updateByPrimaryKeySelective(stationMaterial);
        }
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 添加场站物资信息
     *
     * @param stationMaterials
     * @return
     */
    @Override
    public ApiResult addStationMaterials(List<StationMaterial> stationMaterials) {
        ApiResult apiResult = null;
        int i = stationMaterialMapper.addStationMaterialList(stationMaterials);
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 导出场站物资的Excel信息
     * @param stationId
     * @param datetime
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public HSSFWorkbook getStationMaterialExcelByQuery(long stationId,String datetime, HttpServletRequest request) throws ClassNotFoundException {
        //查询动态表物资信息
        List<List<Map>> list0 = excelServiceAdapter.getExcelDto(stationId, "作业场所应急物资调查", datetime,request);
        List<StationMaterialBo> stationMaterialBos0 = new ArrayList<>();
        for (List<Map> list : list0){
            StationMaterialBo stationMaterialBo0 = new StationMaterialBo();
                stationMaterialBo0.setNo(Integer.valueOf( (String) list.get(0).get("value")));
                stationMaterialBo0.setMaterialName((String) list.get(1).get("value"));
                stationMaterialBo0.setTechnicalRequirement((String) list.get(2).get("value"));
                stationMaterialBo0.setMaterialUtil((String) list.get(3).get("value"));
                stationMaterialBo0.setMaterNum((String) list.get(4).get("value"));

            stationMaterialBos0.add(stationMaterialBo0);
        }
        //查询场站物资信息
        List<StationMaterialBo> stationMaterialBos = materialMapper.selectExcelStationMaterial(stationId,datetime);
        for (int i = 0; i <stationMaterialBos.size() ; i++) {
            stationMaterialBos.get(i).setNo(stationMaterialBos0.size()+i+1);
        }
        stationMaterialBos0.addAll(stationMaterialBos);



        return ExcelUtil.getHSSFWorkbook(stationMaterialBos0, StationMaterialBo.class);
    }

    @Override
    public ApiResult deleteStationMaterial(Long stationMaterialId) {
        ApiResult apiResult = null;
        int i = stationMaterialMapper.deleteByPrimaryKey(stationMaterialId);
        if (i > 0) {
            apiResult = ApiResult.SUCCESS();
        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }

    @Override
    public ApiResult findDynamicStationMaterial(StationMaterialQo stationMaterialQo, HttpServletRequest request) {
        List list = excelServiceAdapter.getExcelDto(stationMaterialQo.getStationId(), "作业场所应急物资调查", stationMaterialQo.getDatetime(),request);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }

    @Override
    public ApiResult addMaterial(StationMaterialEditVo stationMaterialEditVo) {
        ApiResult apiResult = null;
//        根据物资名字查询物资ID
        Long materialId = materialMapper.findMaterialByName(stationMaterialEditVo.getMaterialName());


        Material material = new Material();
        StationMaterial stationMaterial = new StationMaterial();

        BeanUtils.copyProperties(stationMaterialEditVo, material);

        if (materialId != null) {
            //根据场站id和物资ID查询此场站此物资是否已存在
            stationMaterial = stationMaterialMapper.findStationIdAndMaterialId(stationMaterialEditVo.getStationId(),
                    materialId,
                    stationMaterialEditVo.getDatetime());
            if (stationMaterial != null) {
                apiResult = ApiResult.FAIL();
                apiResult.setMsg(stationMaterialEditVo.getDatetime() + "年 物资: " + stationMaterialEditVo.getMaterialName() + " 已存在");
                throw new UserException(apiResult);

            }

            stationMaterial = new StationMaterial();
            BeanUtils.copyProperties(stationMaterialEditVo, stationMaterial);
            stationMaterial.setMaterialId(materialId);

        } else {

            //物资ID不存在，新增物资
            materialMapper.insertSelective(material);
            stationMaterial.setMaterialId(material.getMaterialId());
        }


        //设置日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(stationMaterialEditVo.getDatetime(), pos);
        stationMaterial.setStationId(stationMaterialEditVo.getStationId());
        stationMaterial.setYear(strtodate);

        //添加场站物资
        int i = stationMaterialMapper.insertSelective(stationMaterial);

        if (i > 0) {
            apiResult = ApiResult.SUCCESS();

        } else {
            apiResult = ApiResult.FAIL();
        }
        return apiResult;
    }


}
