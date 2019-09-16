package com.cj.exclematerial.serveice.impl;

import com.cj.common.entity.Brigade;
import com.cj.core.domain.ApiResult;
import com.cj.exclematerial.mapper.BrigadeMapper;
import com.cj.exclematerial.pojo.BrigadeBo;
import com.cj.exclematerial.serveice.BrigadeService;
import com.cj.exclematerial.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("brigadeServer")
public class BrigadeServerImpl implements BrigadeService {

    /**
     *
     */
    @Resource
    BrigadeMapper brigadeMapper;
    @Override
    /**
     * 通过query查询消防队信息
     */
    public ApiResult findBrigade(Map param) {
        ApiResult apiResult=null;
        try {
            List<Map> brigadeList=brigadeMapper.findByQuery(param);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(brigadeList);
        }catch (Exception e){
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 添加消防队信息
     * @param brigade
     * @return
     */
    @Override
    public ApiResult addBrigade(Brigade brigade) {
        ApiResult apiResult=null;
        int i=brigadeMapper.insertSelective(brigade);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 修改消防队信息
     * @param brigade
     * @return
     */
    @Override
    public ApiResult updateBrigade(Brigade brigade) {
        ApiResult apiResult=null;
        int i=brigadeMapper.updateByPrimaryKeySelective(brigade);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 获取消防队的Excel信息
     * @param param
     * @return
     */
    @Override
    public HSSFWorkbook getBrigadeExcelByQuery(Map param) throws ClassNotFoundException {
        List<BrigadeBo> list= brigadeMapper.selectExcelBrigade(param);
        return ExcelUtil.getHSSFWorkbook(list,BrigadeBo.class);
    }

    @Override
    public ApiResult deleteBrigadeById(Long id) {
        ApiResult apiResult=null;
        int i=brigadeMapper.deleteByPrimaryKey(id);
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }
}
