package com.cj.analysis.server.impl;

import com.cj.analysis.mapper.EnvironmentResultMapper;
import com.cj.analysis.pojo.FactoryEnvironmentPipelineView;
import com.cj.analysis.pojo.FactoryEnvironmentView;
import com.cj.analysis.server.EnvironmentResultServer;
import com.cj.analysis.utils.ExcelUtil;
import com.cj.analysis.vo.VoEnvironment;
import com.cj.common.entity.Measures;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("environmentResultServer")
public class EnvironmentResultServerImpl implements EnvironmentResultServer{
    @Resource
    EnvironmentResultMapper environmentResultMapper;
    /**
     * 查询气矿评定结果汇总结果
     * @param query
     * @return
     */
    @Override
    public ApiResult findFactoryEnvironmentGrade(VoEnvironment query) {
        ApiResult apiResult=null;
        List<List<?>> lists;
        List environmentGradeViews=null;
        try {
            lists= environmentResultMapper.findFactoryEnvironment(query);
            apiResult=ApiResult.SUCCESS();
            //int recordTotal=environmentResultMapper.countFactoryEnvironment(query);

            //结果集处理
            List<Map> list0 = (List<Map>) lists.get(0);
            List<Map> list1 = (List<Map>) lists.get(1);

            Long total = (Long) (list1.get(0).get("total"));



            Pager pager=new Pager();
            pager.setCurrentPage(query.getCurrentPage()+1);
            pager.setPageSize(query.getPageSize());
            pager.setRecordTotal(total.intValue());
            pager.setContent(list0);
            apiResult.setData(pager);
        }catch (Exception e){
            System.out.println(e);
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }


    /**
     * 获取气矿评定的Excel信息
     * @param query
     * @return
     */
    @Override
    public HSSFWorkbook getFactoryEnvironmentExcelByQuery(VoEnvironment query) {
        List list=null;
        try {
            if (query.getShape()==1){
                list=environmentResultMapper.findFactoryEnvironmentByQuery(query);
                return ExcelUtil.getHSSFWorkbook(list,FactoryEnvironmentView.class);
            }
            else{
                list=environmentResultMapper.findPipelineEnvironmentByQuery(query);
                return ExcelUtil.getHSSFWorkbook(list,FactoryEnvironmentPipelineView.class);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApiResult getPipelineEnvironmentGradeByQuery(VoEnvironment query) {
        ApiResult apiResult=null;
        List environmentGradeViews=null;
        try {
            environmentGradeViews=environmentResultMapper.findPipelineEnvironment(query);
            apiResult=ApiResult.SUCCESS();
            int recordTotal=environmentResultMapper.countPipelineEnvironment(query);
            Pager pager=new Pager();
            pager.setCurrentPage(query.getCurrentPage()+1);
            pager.setPageSize(query.getPageSize());
            pager.setRecordTotal(recordTotal);
            pager.setContent(environmentGradeViews);
            apiResult.setData(pager);
        }catch (Exception e){
            System.out.println(e);
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }


}
