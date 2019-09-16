package com.cj.excleassessment.controller;

import com.cj.core.domain.ApiResult;
import com.cj.common.utils.file.FileUtil;
import com.cj.core.aop.Log;
import com.cj.excleassessment.service.AnnualReportService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.cj.common.utils.file.FileUtil.getPrefix;

/**
 * 年度环境风险报告上传 接口
 * Created by XD on 2018/10/16.
 */
@RestController
@RequestMapping("api/v1/annual/report")
@Api(tags = "年度环境风险评估报告模块 ==> 报告管理")
public class AnnualReportController {

    @Autowired
    private AnnualReportService annualReportService;


    @Value("${web.upload-path}")
    String path;


    @ApiOperation("pdf文件上传")
    @PostMapping("/upload")
    @Log(name = "报告管理 ==> pdf文件上传")
    public ApiResult upload(HttpServletRequest request,
                      @ApiParam(name = "multipartFile",value = "文件字节流",required = true) MultipartFile multipartFile) throws Exception{
        String fileUrl = "";  //地址

        String fileUrlRsp = "";//返回给前端的地址
        //判断文件是否为空
        if(multipartFile != null && !multipartFile.isEmpty()){
            fileUrl += FileUtil.uploadFile(path+"report/",multipartFile);
        }
        System.out.println("文件在系统中的完整路径===>>"+fileUrl);
        //判断是否是pdf文件
        String s = FileUtil.getExtensions(fileUrl);
        if (!"pdf".equals(s)){
            ApiResult apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("请上传pdf文件");
            return apiResult;
        }

        String prefix = getPrefix(request);
        //图片的访问路径
        fileUrl = fileUrl.replace(this.path,prefix);

        Map map = new HashMap();
        map.put("fileUrl",fileUrl);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(map);

        return apiResult;
    }

    @ApiOperation("保存 报告文件")
    @PostMapping("/save")
    @Log(name = "报告管理 ==> 保存报告")
    public ApiResult save(
            @ApiParam(name = "map", value = "参数=" +"url=pdf文件名称(调用通用上传文件接口后获得，必传)," +
                    "name=报告文件名   year=年份（必传）"
                    ,required = true)
            @RequestBody Map map, HttpServletRequest request){

        ApiResult apiResult = this.annualReportService.save(map,request);
        return apiResult;
    }


    @ApiOperation("根据年份查询全部报告文件")
    @GetMapping("/getAllByYear")
    @Log(name = "报告管理 ==> 根据年份查询全部报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份")
    })
    public ApiResult getAllByYear(String year,HttpServletRequest request){

        ApiResult apiResult = this.annualReportService.getAllByYear(year,request);
        return apiResult;
    }


    /**
     * 逻辑删除  只可以删除自己管辖区域的报告
     * @param annualReportId
     * @param request
     * @return
     */
    @ApiOperation("删除报告")
    @DeleteMapping("/delete")
    @Log(name = "报告管理 ==> 删除报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "annualReportId", value = "报告id")
    })
    public ApiResult delete(Long annualReportId,HttpServletRequest request){

        ApiResult apiResult = this.annualReportService.delete(annualReportId,request);
        return apiResult;
    }


    /**
     * pdf文件下载  各个气矿和分公司之间可以相互下载
     * @param annualReportId
     * @param request
     * @return
     */
    @ApiOperation("pdf文件下载")
    @GetMapping("/getReportPdf")
    @Log(name = "报告管理 ==> pdf文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "annualReportId", value = "报告id")
    })
    public void getReportPdf(Long annualReportId, HttpServletRequest request, HttpServletResponse response){

        this.annualReportService.getReportPdf(annualReportId,request,response);
    }

}
