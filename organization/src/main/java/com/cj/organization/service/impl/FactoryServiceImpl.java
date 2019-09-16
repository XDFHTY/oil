package com.cj.organization.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.organization.entity.RespOrganization;
import com.cj.organization.mapper.FactoryBasicMsgMapper;
import com.cj.organization.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 组织结构信息 气矿管理
 * Created by XD on 2018/10/9.
 */
@Service
public class FactoryServiceImpl implements FactoryService {

    //分公司id
    private Long branchCompanyId = 1L;

    @Autowired
    private FactoryBasicMsgMapper factoryMapper;


    /**
     * 根据气矿id查询气矿基本信息和管理人信息
     *
     * @param p
     * @param request
     * @return
     */
    @Override
    public ApiResult findFactoryById(Pager p, HttpServletRequest request) {
        ApiResult apiResult;
        //设置分公司id
        p.getParameters().put("branchCompanyId",branchCompanyId);

        //根据气矿id查询气矿基本信息和管理人信息
        List<RespOrganization> list = this.factoryMapper.findFactoryById(p);
        //统计条数
        Integer i = this.factoryMapper.findFactoryByIdCount(p);

        p.setContent(list);
        p.setRecordTotal(i);

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(p);

        return apiResult;
    }
}
