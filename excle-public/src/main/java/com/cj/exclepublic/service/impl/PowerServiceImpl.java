package com.cj.exclepublic.service.impl;

import com.cj.common.exception.UserException;
import com.cj.common.mapper.AuthCustomerRoleMapper;
import com.cj.common.pojo.Organization;
import com.cj.core.domain.ApiResult;
import com.cj.exclepublic.domain.FindOrganizationResp;
import com.cj.exclepublic.mapper.FactoryTypeMapper;
import com.cj.exclepublic.service.PowerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XD on 2018/10/15.
 */
@Service
public class PowerServiceImpl implements PowerService {

    @Resource
    private AuthCustomerRoleMapper roleMapper;

    @Resource
    private FactoryTypeMapper factoryTypeMapper;


    /**
     * 带权限查询各个机构名称
     * @param request
     * @return
     */
    @Override
    public ApiResult getOrganizationList(HttpServletRequest request) {
        ApiResult apiResult;
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map = maps.get(0);
        String roleDescName = (String) map.get("roleDescriptionName");//获取账号等级
        List<Organization> list = new ArrayList<>();//返回对象
        if ("分公司级".equals(roleDescName)){
            //查询全部
            list = this.roleMapper.findOrganizationByBC();
        }
        if ("气矿级A".equals(roleDescName) || "气矿级B".equals(roleDescName) || "气矿级C".equals(roleDescName)){
            list = this.roleMapper.findOrganizationByFactory(adminId);
        }
        if ("作业区级A".equals(roleDescName) || "作业区级B".equals(roleDescName)){
            list = this.roleMapper.findOrganizationByTaskArea(adminId);
        }
        if ("场站级".equals(roleDescName)){
            list = this.roleMapper.findOrganizationByStation(adminId);
        }

        apiResult = ApiResult.SUCCESS();
        apiResult.setData(list);
        return apiResult;
    }


    /**
     * 返回true为可以编辑
     * 检查当前表状态 此角色等级是否可编辑
     * @param checkType 当前表状态
     * @param roleGradeName 用户角色等级
     * @return
     */
    @Override
    public Boolean checkUpdatePower(String checkType,String roleGradeName){
        Boolean b = false;
        ApiResult apiResult = new ApiResult();
        switch (checkType){

            case "":
                if ("场站级".equals(roleGradeName)||"作业区级A".equals(roleGradeName)){
                    b = true;
                }
                break;
            case "0":
                if ("作业区级A".equals(roleGradeName)){
                    b = true;
                }
                break;
            case "1":
            case "21":
                if ("场站级".equals(roleGradeName)){
                        b = true;
                }
                break;

            case "2":
            case "3":
            case "22":
                if ("作业区级A".equals(roleGradeName)){
                        b = true;
                }
                break;

            case "6":
            case "7":
            case "24":
                if ("气矿级A".equals(roleGradeName)){
                        b = true;
                }
                break;
//                apiResult = ApiResult.FAIL();
//                apiResult.setMsg("作业区技术管理人员 审核中,不可编辑");
//                throw new UserException(apiResult);
            case "4":
            case "5":
            case "23":
//                apiResult = ApiResult.FAIL();
//                apiResult.setMsg("作业区分管领导 审核中,不可编辑");
//                throw new UserException(apiResult);
//                apiResult = ApiResult.FAIL();
//                apiResult.setMsg("气矿技术管理人员 审核中,不可编辑");
//                throw new UserException(apiResult);
            case "8":
            case "9":
            case "25":
//                apiResult = ApiResult.FAIL();
//                apiResult.setMsg("气矿部门(环保)主管 审核中,不可编辑");
//                throw new UserException(apiResult);
            case "10":
//                apiResult = ApiResult.FAIL();
//                apiResult.setMsg("气矿分管领导 审核中,不可编辑");
//                throw new UserException(apiResult);
            case "11":
//                apiResult = ApiResult.FAIL();
//                apiResult.setMsg("审批已完成,所有人都不可编辑");
//                throw new UserException(apiResult);
        }


        return b;


    }

    @Override
    public FindOrganizationResp findOrganization(Long stationId) {
        return factoryTypeMapper.findOrganization(stationId);
    }


}
