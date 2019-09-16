package com.cj.excleassessment.service.impl;

import com.cj.common.entity.AnnualReport;
import com.cj.common.exception.UserException;
import com.cj.core.domain.ApiResult;
import com.cj.common.utils.file.FileUtil;
import com.cj.excleassessment.mapper.AnnualReportMapper;
import com.cj.excleassessment.service.AnnualReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.cj.common.utils.file.FileUtil.getPrefix;

/**
 * 年度环境风险报告上传 接口
 * Created by XD on 2018/10/16.
 */
@Service
public class AnnualReportServiceImpl implements AnnualReportService {


    @Value("${web.upload-path}")
    String path;


    @Autowired(required = false)
    private AnnualReportMapper annualReportMapper;

    /**
     * 保存 报告文件
     * 气矿级  每个气矿每年只能有一份报告
     * 分公司级 每年只能有两份报告  且监督中心工作人员只能查看不能上传
     * 需要删除原来的文件 才可以重新上传
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult save(Map map, HttpServletRequest request) {
        String prefix = getPrefix(request);
        String url = (String) map.get("url");

        map.put("url",url.replaceAll(prefix,""));//替换 为数据库保存的url
        ApiResult apiResult;
        Date nowDate = new Date();
        map.put("nowDate",nowDate);//当前时间
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        map.put("adminId",adminId);//操作员
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);

        //判断角色
        String roleDescriptionName = (String) map1.get("roleDescriptionName");
        if ("分公司级".equals(roleDescriptionName)){
            //分公司级
            //每年只能有两份报告  且监督中心工作人员只能查看不能上传
            //获取角色名称
            String roleName = (String) map1.get("roleName");
            if ("监督中心".equals(roleName)){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("监督中心工作人员只可查看，不可上传");
                return apiResult;
            }
            //根据admin查询分公司id
            Long bcId = this.annualReportMapper.findBCIdByAdminId(adminId);
            if (bcId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定分公司，请先绑定");
                return apiResult;
            }

            //根据年份和分公司id查询 当前分公司报告份数
           /* Integer count = this.annualReportMapper.findBCReportNumberByYear((String)map.get("year"),bcId);
            if (count >= 2){//每年的分数不能超过两份
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("总报告只能上传两份，请先删除已经添加的文件");
                return apiResult;
            }else {//保存文件
                map.put("bcId",bcId);//设置分公司id
                map.put("type","1");//报告分类  1-分公司 2-气矿
                map.put("factoryId",null);//没有气矿
                this.annualReportMapper.addRecord(map);
            }*/

            //不限制报告份数
            map.put("bcId",bcId);//设置分公司id
            map.put("type","1");//报告分类  1-分公司 2-气矿
            map.put("factoryId",null);//没有气矿
            this.annualReportMapper.addRecord(map);

        }
        if ("气矿级A".equals(roleDescriptionName)||"气矿级B".equals(roleDescriptionName)||"气矿级C".equals(roleDescriptionName)){
            //气矿级
            //气矿级  每个气矿每年只能有一份报告
            //根据adminId查询气矿id
            Long factoryId = this.annualReportMapper.findFactoryIdByAdminId(adminId);
            if (factoryId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定气矿，请先绑定");
                return apiResult;
            }
            //根据气矿id和年份查询报告份数
            /*Integer count = this.annualReportMapper.findFactoryNumberById((String)map.get("year"),factoryId);
            if (count >= 1){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("每个矿区只能上传一份，请先删除已经添加的文件");
                return apiResult;
            }else {//保存文件
                map.put("bcId",null);//不填分公司id
                map.put("type","2");//报告分类  1-分公司 2-气矿
                map.put("factoryId",factoryId);//气矿id
                this.annualReportMapper.addRecord(map);
            }*/
            //不限制报告份数
            map.put("bcId",null);//不填分公司id
            map.put("type","2");//报告分类  1-分公司 2-气矿
            map.put("factoryId",factoryId);//气矿id
            this.annualReportMapper.addRecord(map);

        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 根据年份查询全部报告
     * @param request
     * @return
     */
    @Override
    public ApiResult getAllByYear(String year,HttpServletRequest request) {
        String prefix = getPrefix(request);
        //根据年份查询全部报告
        List<AnnualReport> list = this.annualReportMapper.getAllByYear(year);
        if (list!=null && list.size()!=0){
            for (AnnualReport info:list){
                info.setReportUrl(prefix+info.getReportUrl());
            }
        }
        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }


    /**
     * 逻辑删除  只可以删除自己管辖区域的报告
     * @param annualReportId
     * @param annualReportId
     * @param request
     * @return
     */
    @Override
    public ApiResult delete( Long annualReportId, HttpServletRequest request) {
        ApiResult apiResult;
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        //根据报告id 查询报告
        AnnualReport annualReport = this.annualReportMapper.selectByPrimaryKey(annualReportId);
        if (annualReport == null){
            apiResult = ApiResult.FAIL();
            return apiResult;
        }

        //判断角色
        String roleDescriptionName = (String) map1.get("roleDescriptionName");
        if ("分公司级".equals(roleDescriptionName)){
            //获取角色名称
            String roleName = (String) map1.get("roleName");
            if ("监督中心".equals(roleName)){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("监督中心工作人员只可查看，不可删除");
                return apiResult;
            }
            //根据admin查询分公司id
            Long bcId = this.annualReportMapper.findBCIdByAdminId(adminId);
            if (bcId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定分公司，请先绑定");
                return apiResult;
            }
            if (annualReport.getBranchCompanyId() != bcId){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("删除失败，只能删除自己管辖区域的报告");
                return apiResult;
            }

        }
        if ("气矿级A".equals(roleDescriptionName) || "气矿级B".equals(roleDescriptionName) || "气矿级C".equals(roleDescriptionName)){
            //根据adminId查询气矿id
            Long factoryId = this.annualReportMapper.findFactoryIdByAdminId(adminId);
            if (factoryId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("账号未绑定气矿，请先绑定");
                return apiResult;
            }
            if (annualReport.getFactoryId() != factoryId){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("删除失败，只能删除自己管辖区域的报告");
                return apiResult;
            }
        }
        annualReport.setOperatorId(adminId);
        annualReport.setReportState("0");//0 已删除
        this.annualReportMapper.updateByPrimaryKeySelective(annualReport);

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * pdf文件下载  各个气矿和分公司之间可以相互下载
     * @param annualReportId
     * @param request
     * @return
     */
    @Override
    public ApiResult getReportPdf(Long annualReportId, HttpServletRequest request,HttpServletResponse response) {
        ApiResult apiResult;
        //根据报告id查询报告
        AnnualReport annualReport = this.annualReportMapper.findReportById(annualReportId);
        if (annualReport == null){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("下载失败，文件不存在或已删除");
            throw new UserException(apiResult);
        }
        /*String prefix = getPrefix(request);
        annualReport.setReportUrl(prefix + annualReport.getReportUrl());*/
        //http://pr.appshowings.com:8185/img/report/c4e982ab-ceb6-4cfd-8d1e-7502968a6e5e.pdf

        /*String url = annualReport.getReportUrl();
        int start = url.indexOf("report/");
        int end = url.length();
        String fileName = url.substring(start,end);*/

        //下载
        try {
            FileUtil.download(request,response,path+annualReport.getReportUrl(),annualReport.getReportName());
        }catch (Exception e){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("下载失败");
            throw new UserException(apiResult);
        }
        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


}
