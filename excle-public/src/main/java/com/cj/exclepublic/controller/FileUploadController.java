package com.cj.exclepublic.controller;

import com.cj.core.domain.ApiResult;
import com.cj.common.utils.file.FileUtil;
import com.cj.core.aop.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XD on 2018/10/16.
 */
@RestController
@RequestMapping("api/v1/common/file")
@Api(tags = "公共模块：文件上传")
public class FileUploadController {
    @Value("${web.upload-path}")
    String path;


    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @Log(name = "文件管理 ==> 文件上传")
    public ApiResult upload(HttpServletRequest request,
                      @ApiParam(name = "multipartFile",value = "文件字节流",required = true) MultipartFile multipartFile) throws Exception{
        String fileUrl = "";  //地址

        //判断文件是否为空
        if(multipartFile != null && !multipartFile.isEmpty()){
            fileUrl += FileUtil.uploadFile(path+"",multipartFile);

        }


        //图片的访问路径
        fileUrl = fileUrl.replace(this.path,FileUtil.getPrefix(request));
        System.out.println("文件在系统中的完整访问路径===>>"+fileUrl);
        Map map = new HashMap();
        map.put("fileUrl",fileUrl);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(map);

        return apiResult;
    }
}
