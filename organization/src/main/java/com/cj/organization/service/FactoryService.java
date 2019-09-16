package com.cj.organization.service;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;

import javax.servlet.http.HttpServletRequest;

/**
 * 组织结构信息 气矿管理
 * Created by XD on 2018/10/9.
 */
public interface FactoryService {
    //根据气矿id查询气矿基本信息和管理人信息
    ApiResult findFactoryById(Pager p, HttpServletRequest request);
}
