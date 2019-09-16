package com.cj.organization.service;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;

import javax.servlet.http.HttpServletRequest;

/**
 * 组织结构信息 作业区管理
 * Created by XD on 2018/10/9.
 */
public interface AreaService {

    //根据气矿Id、作业区id查询 气矿基本信息、作业区和管理人信息
    ApiResult findAreaById(Pager p, HttpServletRequest request);
}
