package com.cj.exclematerial.controller;

import com.cj.common.entity.Hospital;
import com.cj.core.domain.ApiResult;
import com.cj.core.aop.Log;
import com.cj.exclematerial.annotation.FileName;
import com.cj.exclematerial.serveice.HospitalService;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Api(tags = "应急物资配备及预案备案情况：应急医疗机构")
@RequestMapping("/api/v1/emergency")
@RestController
public class HospitalController {

    @Autowired
    HospitalService hospitalServer;
    /**
     * 根据条件查询医疗机构信息
     * @param param 查询条件
     */
    @ApiOperation(value = "查询医疗机构信息",response = Hospital.class)
    @GetMapping("/hospital")
    @Log(name = "医疗机构",value = "查询医疗机构信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "厂、矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public ApiResult getHospitalByQuery(@RequestParam(required = false) Map<String, Object> param){
        ApiResult apiResult=hospitalServer.findHospital(param);
        return apiResult;
    }

    /**
     * 添加医疗机构对信息
     * @param hospital 医疗机构信息
     */
    @ApiOperation(value = "添加医疗机构信息",response = ApiResult.class)
    @PostMapping("/hospital")
    @Log(name = "医疗机构",value = "添加医疗机构信息")
    public ApiResult addHospital(
            @ApiParam(value = "factoryId=厂、矿ID、hospitalName=医疗机构名称、hospitalAddress=医疗机构地址、hospitalLevel=医疗机构等级" +
                    "\n{\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"hospitalName\": \"1号医疗机构\",\n" +
                    "  \"hospitalAddress\": \"1号地址\",\n" +
                    "  \"hospitalLevel\": \"三甲\",\n" +
                    "  \"oxygenChamber\": \"1\"\n" +
                    "}" +
                    "")
            @RequestBody Hospital hospital){
        ApiResult apiResult=hospitalServer.addHospital(hospital);
        return apiResult;
    }

    /**
     * 修改医疗机构信息
     * @param hospital 医疗机构信息
     */
    @ApiOperation(value = "修改医疗机构信息",response = ApiResult.class)
    @PutMapping("/hospital")
    @Log(name = "医疗机构",value = "修改医疗机构信息")
    public ApiResult updateHospital(
            @ApiParam(value = "factoryId=修改后的厂、矿ID、hospitalName=修改后的医疗机构名称、hospitalAddress=修改后的医疗机构地址、hospitalLevel=医疗机构等级" +
                    "\n{\n" +
                    "  \"factoryId\": 1,\n" +
                    "  \"hospitalName\": \"1号医疗机构修改\",\n" +
                    "  \"hospitalAddress\": \"1号地址修改\",\n" +
                    "  \"hospitalAddress\": \"二甲\",\n" +
                    "  \"oxygenChamber\": \"1\"\n" +
                    "}" +
                    "")
            @RequestBody Hospital hospital){
        ApiResult apiResult=hospitalServer.updateHospital(hospital);
        return apiResult;
    }

    /**
     * 获取Excel
     */
    @GetMapping("/hospital/excel")
    @ApiOperation(value = "按条件获取应急医疗机构分布信息的Excel",response = Hospital.class)
    @Log(name = "应急物资配备及预案备案情况:应急医疗机构分布",value = "按条件获取应急医疗机构分布信息的Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "factoryId",value = "厂、矿ID",defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",name = "datetime",value = "时间(时间格式yyyy-MM-dd)",defaultValue = "2018-9-10")
    })
    public void getHospitalExcelByQuery(@RequestParam(required = false) Map<String, Object> param, HttpServletResponse response) throws IOException, ClassNotFoundException {
        HSSFWorkbook wb=hospitalServer.getHospitalExcelByQuery(param);
        //输出Excel文件
        String fileName = URLEncoder.encode("应急医疗机构分布情况", StandardCharsets.UTF_8.toString());
        OutputStream out = response.getOutputStream();
        response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
        response.setCharacterEncoding("UTF-8");
        wb.write(out);
        out.flush();
        out.close();
    }

    @DeleteMapping("/hospital")
    public ApiResult deleteHospital(@RequestParam Long id){
        ApiResult apiResult=hospitalServer.deleteHospital(id);
        return apiResult;
    }
}
