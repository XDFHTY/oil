package com.cj.organization.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.organization.entity.RespOrganization;
import com.cj.organization.mapper.TaskAreaMapper;
import com.cj.organization.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 组织结构信息 作业区管理
 * Created by XD on 2018/10/9.
 */
@Service
public class AreaServiceImpl implements AreaService {

    //分公司id
    private Long branchCompanyId = 1L;

    @Autowired
    private TaskAreaMapper areaMapper;

    /**
     * 根据气矿Id、作业区id 查询 气矿基本信息、作业区和管理人信息
     * @param p
     * @param request
     * @return
     */
    @Override
    public ApiResult findAreaById(Pager p, HttpServletRequest request) {
        ApiResult apiResult;
        //设置分公司id
        p.getParameters().put("branchCompanyId",branchCompanyId);

        //根据气矿Id、作业区id 查询 气矿基本信息、作业区和管理人信息
        List<RespOrganization> list = this.areaMapper.findAreaById(p);
        //计数
        Integer i = this.areaMapper.findAreaByIdCount(p);

        p.setContent(list);
        p.setRecordTotal(i);

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(p);

        return apiResult;
    }
}
