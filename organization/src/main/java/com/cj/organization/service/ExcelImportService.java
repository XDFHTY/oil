package com.cj.organization.service;

import com.cj.core.domain.ApiResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Excel导入
 * Created by XD on 2018/10/10.
 */
public interface ExcelImportService {

    //导入组织机构基本信息
    ApiResult importInfo(MultipartFile file, InputStream in);

    //导出组织机构名称，生成需要填写账号格式表
    ApiResult organizationExport(HttpServletResponse response, HttpServletRequest request);

    //导入气矿和账号的关系表格
    ApiResult importFactoryAdmin(MultipartFile file, InputStream in);

    //导出 作业区，生成需要填写账号格式表
    ApiResult taskAreaExport(HttpServletResponse response, HttpServletRequest request);

    //导出 厂、矿，生成需要填写账号格式表
    ApiResult factoryExport(HttpServletResponse response, HttpServletRequest request);

    //导入作业区和账号的关系表格
    ApiResult importAreaAdmin(MultipartFile file, InputStream in);

    //导入中心站/作业区 和账号的关系表格
    ApiResult importStationAdmin(MultipartFile file, InputStream in);
}
