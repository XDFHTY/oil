package com.cj.organization.service.impl;

import com.cj.admin.service.AdminService;
import com.cj.common.entity.Admin;
import com.cj.common.entity.AuthAdminPopedom;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.organization.entity.AdminAndOrgan;
import com.cj.organization.entity.AdminAndOrganInfo;
import com.cj.organization.mapper.AuthAdminPopedomMapper;
import com.cj.organization.service.AdminPopedomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织结构 账号 关系操作
 * Created by XD on 2018/10/12.
 */
@Service
@Transactional
public class AdminPopedomServiceImpl implements AdminPopedomService {


    @Autowired(required = false)
    private AuthAdminPopedomMapper authAdminPopedomMapper;

    @Autowired
    private AdminService adminService;


    /**
     * 角色id 常量
     */
    private final static Integer valBranchId = 1;//分公司工作人员
    private final static Integer valInstitute = 2;//安研院工作人员
    private final static Integer valSupervisionId = 3;//监督中心工作人员
    private final static Integer valFactoryCId = 4;//气矿C工作人员
    private final static Integer valFactoryBId = 5;//气矿B工作人员
    private final static Integer valFactoryAId = 6;//气矿A工作人员
    private final static Integer valTaskAreaBId = 8;//作业区B工作人员
    private final static Integer valTaskAreaAId = 9;//作业区A工作人员
    private final static Integer valStationId = 10;//场站工作人员
    private final static Integer valStationPipelineId = 11;//管线工作人员

/*
    private final static String valBranch = "分公司级";//分公司工作人员
    private final static String valFactoryC = "气矿级C";//气矿C工作人员
    private final static String valFactoryB = "气矿级B";//气矿B工作人员
    private final static String valFactoryA = "气矿级A";//气矿A工作人员
    private final static String valTaskAreaB = "作业区级B";//作业区B工作人员
    private final static String valTaskAreaA = "作业区级A";//作业区A工作人员
    private final static String valStation = "场站级";//场站工作人员*/



    /**
     * 条件查询 账号列表
     * @param pager
     * @param request
     * @return
     */
    @Override
    public ApiResult findAdminByName(Pager pager, HttpServletRequest request) {
        ApiResult apiResult;

        Map map = pager.getParameters();
        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");

        Map map1 = maps.get(0);
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级
        List<List<?>> lists = new ArrayList<>();//返回结果

        Map map2 = new HashMap();//查询条件
        map2.put("minRow",pager.getMinRow());
        map2.put("maxRow",pager.getMaxRow());
        String name = (String) map.get("name");//获取姓名
        if (name != null && name.trim()!=""){
            map2.put("name",name);
        }

        if (roleId==valStationId || roleId==valStationPipelineId){//场站级
            apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ){//作业区级
            if (areaId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是作业区级，只能查看场站级账号");
                return apiResult;
            }
            if (stationId == null){
                //场站id为空  根据作业区id 查询下属所有账号
                map2.put("taskAreaId",areaId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }else {
                //场站id 不为空  根据场站id查询账号
                map2.put("stationId",stationId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }


        }
        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ){//气矿级
            if (factoryId==null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您是气矿级，只能查看作业区级或场站级账号");
                return apiResult;
            }
            if (factoryId != null && areaId == null && stationId == null){
                //查询该气矿下所有账号
                map2.put("factoryId",factoryId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }
            if (factoryId != null && areaId!=null && stationId==null){
                //根据作业区id  查询所属所有账号
                map2.put("taskAreaId",areaId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }
            if (factoryId != null && areaId!=null && stationId!=null){
                //查询这个场站的账号列表
                map2.put("stationId",stationId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }

        }
        if (roleId==valBranchId || roleId==valInstitute || roleId==valSupervisionId){//分公司级
            if (factoryId==null){
                //查询所有气矿下的所有账号
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }
            if (factoryId!=null && areaId==null && stationId==null){
                //根据气矿id查询所有账号
                map2.put("factoryId",factoryId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }
            if (factoryId!=null && areaId!=null && stationId==null){
                //根据作业区id 查询所有账号
                map2.put("taskAreaId",areaId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }
            if (factoryId!=null && areaId!=null && stationId!=null){
                //根据场站id 查询所有账号
                map2.put("stationId",stationId);
                lists = this.authAdminPopedomMapper.findAdminByOrgan(map2);
            }
        }

        //结果集处理
        List<AdminAndOrganInfo> list0 = (List<AdminAndOrganInfo>) lists.get(0);
        List<Map> list1 = (List<Map>) lists.get(1);

        Long total = (Long) (list1.get(0).get("total"));
        pager.setRecordTotal(total.intValue());

        //返回对象
        List<AdminAndOrgan> list = new ArrayList<>();

        //处理数据
        if (list0 != null){
            for (AdminAndOrganInfo info:list0){
                AdminAndOrgan organ = new AdminAndOrgan();
                organ.setAdminId(info.getAdminId());
                organ.setAdminName(info.getAdminName());
                organ.setPopedomGrade(info.getPopedomGrade());
                organ.setFullName(info.getFullName());
                organ.setPhone(info.getPhone());

                if (info.getRoleId()!=null){//设置角色
                    organ.setRoleId(info.getRoleId());
                    organ.setRoleName(info.getRoleName());
                }

                if (info.getPopedomGrade()!=null && !"".equals(info.getPopedomGrade().trim())){
                    //grade等于null  则是没有绑定机构的账号
                    //设置分公司id 和 分公司名称
                    if (info.getB1Id()!=null){
                        organ.setBranchCompanyId(info.getB1Id());
                        organ.setBranchCompanyName(info.getB1Name());
                    }else if (info.getB2Id()!=null){
                        organ.setBranchCompanyId(info.getB2Id());
                        organ.setBranchCompanyName(info.getB2Name());
                    }else if (info.getB3Id()!=null){
                        organ.setBranchCompanyId(info.getB3Id());
                        organ.setBranchCompanyName(info.getB3Name());
                    }else if (info.getB4Id()!=null){
                        organ.setBranchCompanyId(info.getB4Id());
                        organ.setBranchCompanyName(info.getB4Name());
                    }

                    //设置 气矿id 和 气矿名称
                    if (info.getF1Id()!=null){
                        organ.setFactoryId(info.getF1Id());
                        organ.setFactoryName(info.getF1Name());
                    }else if (info.getF2Id()!=null){
                        organ.setFactoryId(info.getF2Id());
                        organ.setFactoryName(info.getF2Name());
                    }else if (info.getF3Id()!=null){
                        organ.setFactoryId(info.getF3Id());
                        organ.setFactoryName(info.getF3Name());
                    }

                    //设置作业区id 和 作业区名称
                    if (info.getT1Id()!=null){
                        organ.setTaskAreaId(info.getT1Id());
                        organ.setTaskAreaName(info.getT1Name());
                    } else if (info.getT2Id()!=null){
                        organ.setTaskAreaId(info.getT2Id());
                        organ.setTaskAreaName(info.getT2Name());
                    }

                    //设置场站
                    if (info.getS1Id()!=null){
                        organ.setStationId(info.getS1Id());
                        organ.setStationName(info.getS1Name());
                    }
                    //设置工艺分类
                    if (info.getFactoryTypeId()!=null){
                        organ.setFactoryTypeId(info.getFactoryTypeId());
                        organ.setFactoryTypeName(info.getFactoryTypeName());
                    }
                }

                list.add(organ);
            }
        }
        pager.setContent(list);
        apiResult = ApiResult.SUCCESS();
        apiResult.setData(pager);
        return apiResult;
    }

    /**
     * 修改 机构对应的账号
     * 一个气矿 和 作业区 可以对应多个账号
     * 一个账号 只能对应 一个气矿和作业区
     * 一个场站只能对应一个账号
     * 一个账号 可以对应 多个场站
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult updateAdmin(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        /**
         * 判断权限
         * 分公司级 可以修改为  气矿、作业区、场站
         * 气矿级  可以修改为  作业区、场站
         * 作业区级  可以修改为 场站
         * 场站级 不可修改
         */
        Integer branchCompanyId = (Integer) map.get("branchCompanyId");
        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        //Integer roleId = (Integer) map.get("roleId");

        Integer adminId1 = (Integer) map.get("adminId");
        //根据adminId查询角色id
        Integer roleId = this.authAdminPopedomMapper.findRoleIdByAdminId(adminId1);


        if (branchCompanyId==null && factoryId==null && areaId==null && stationId==null){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("保存失败");
            return apiResult;
        }

        //根据管理员id查询是否分配了机构
        AuthAdminPopedom info = authAdminPopedomMapper.findDataByAdminId(map);

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId2 = (Integer) map1.get("roleId");//获取账号等级名称
        //根据adminId 查询账号等级
        String roleDescName2 = this.authAdminPopedomMapper.findRoleByAdminId((Integer)map.get("adminId"));
        if (roleId2==valStationId || roleId2==valStationPipelineId){//场站级
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ){//作业区级
            if (stationId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("不可修改为作业区级账号");
                return apiResult;
            }
            if (!"场站级".equals(roleDescName2) && roleDescName2!=null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能修改场站级账号");
                return apiResult;
            }
            //修改场站级
            return this.updateStation(roleId,stationId,adminId1);
        }



        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ){//气矿级
            if (factoryId!=null && areaId==null && stationId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("不可修改为气矿级账号");
                return apiResult;
            }
            if (!"作业区级".equals(roleDescName2) && !"场站级".equals(roleDescName2) && roleDescName2!=null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能修改作业区级或场站级账号");
                return apiResult;
            }
            if (factoryId!=null && areaId != null && stationId != null){
                //修改场站级
                return this.updateStation(roleId,stationId,adminId1);
            }
            if (factoryId!=null && areaId != null && stationId == null){
                //修改作业区级账号
                return this.updateTaskArea(info,roleId,areaId,adminId1);
            }

        }
        if (roleId2==valBranchId || roleId2==valInstitute || roleId2==valSupervisionId){//分公司级
            if (factoryId!=null && areaId != null && stationId != null){
                //修改场站级
                return this.updateStation(roleId,stationId,adminId1);
            }
            if (factoryId!=null && areaId != null && stationId == null){
                //修改作业区级账号
                return this.updateTaskArea(info,roleId,areaId,adminId1);
            }
            if (factoryId!=null && areaId == null && stationId == null){
                //修改气矿级账号
                return this.updateFactory(info,roleId,factoryId,adminId1);
            }
            //修改分公司级账号
            if (branchCompanyId!=null && factoryId==null && areaId==null && stationId==null){
                //如果是分公司级  那么需要修改的账号又是分公司级，那么你必须要是安研院工作人员才能修改
                if (roleId2!=valInstitute){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("权限不足");
                    return apiResult;
                }
                return this.updateBc(info,roleId,branchCompanyId,adminId1);
            }
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    //修改场站账号 方法
    public ApiResult updateStation(Integer roleId,Integer stationId,Integer adminId){
        ApiResult apiResult;
        if (roleId!=10 && roleId!=11){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("角色不符合场站级");
            return apiResult;
        }
        //查询该场站是否绑定了该账号
        AuthAdminPopedom authAdminPopedom = authAdminPopedomMapper.findAdminByStationId(Long.valueOf(stationId),Long.valueOf(adminId),4);
        if (authAdminPopedom != null){
            //已绑定了
            /*apiResult = ApiResult.SUCCESS();
            apiResult.setMsg("修改成功");
            return apiResult;*/
        }else {
            //绑定关系
            authAdminPopedomMapper.addRecord(Long.valueOf(adminId),Long.valueOf(stationId),4);
            //修改角色
            authAdminPopedomMapper.updateAdminAndRole(adminId,roleId);
        }


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    //修改作业区账号 方法
    public ApiResult updateTaskArea(AuthAdminPopedom info,Integer roleId, Integer areaId, Integer adminId) {
        ApiResult apiResult;
        if (info != null){ //一个账号只能对应作业区和气矿
            //该账号已分配了机构
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("该账号已存在对应机构，请先删除");
            return apiResult;
        }
        if (roleId!=8 && roleId!=9){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("角色不符合作业区级");
            return apiResult;
        }
        //绑定关系
        authAdminPopedomMapper.addRecord(Long.valueOf(adminId),Long.valueOf(areaId),3);
        //修改角色
        authAdminPopedomMapper.updateAdminAndRole(adminId,roleId);


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    //修改气矿账号 方法
    public ApiResult updateFactory(AuthAdminPopedom info,Integer roleId, Integer factory, Integer adminId) {
        ApiResult apiResult;
        if (info != null){ //一个账号只能对应作业区和气矿
            //该账号已分配了机构
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("该账号已存在对应机构，请先删除");
            return apiResult;
        }
        if (roleId!=4 && roleId!=5 && roleId!=6){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("角色不符合气矿级");
            return apiResult;
        }
        //绑定关系
        authAdminPopedomMapper.addRecord(Long.valueOf(adminId),Long.valueOf(factory),2);
        //修改角色
        authAdminPopedomMapper.updateAdminAndRole(adminId,roleId);


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    //修改分公司级账号 方法
    public ApiResult updateBc(AuthAdminPopedom info,Integer roleId, Integer bcId, Integer adminId) {
        ApiResult apiResult;
        if (info != null){ //一个账号只能对应作业区和气矿
            //该账号已分配了机构
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("该账号已存在对应机构，请先删除");
            return apiResult;
        }
        if (roleId!=3 && roleId!=2 && roleId!=1){
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("角色不符合分公司级");
            return apiResult;
        }
        //绑定关系
        authAdminPopedomMapper.addRecord(Long.valueOf(adminId),Long.valueOf(bcId),1);
        //修改角色
        authAdminPopedomMapper.updateAdminAndRole(adminId,roleId);


        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


    /**
     * 删除 机构对应的账号
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult deleteAdmin(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        /**
         * 权限判断
         * 安研院工作人员 也可删除分公司级账号
         * 分公司级 可删除气矿 作业区 场站 账号
         * 气矿级 可删除 作业区 场站 账号
         * 作业区 可删除 场站账号
         */
        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        Integer branchCompanyId = (Integer) map.get("branchCompanyId");
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级
        //根据adminId 查询账号等级
        String roleDescName2 = this.authAdminPopedomMapper.findRoleByAdminId((Integer)map.get("adminId"));
        if (roleId==valStationId || roleId==valStationPipelineId){//场站级
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId){//作业区级
            if (!"场站级".equals(roleDescName2) && roleDescName2!=null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能删除场站级账号");
                return apiResult;
            }
            //删除场站级账号
            authAdminPopedomMapper.deleteRecord((Integer)map.get("adminId"),(Integer)map.get("stationId"),4);
        }
        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ){//气矿级
            if (!"作业区级A".equals(roleDescName2) && !"场站级".equals(roleDescName2) && roleDescName2!=null && !"作业区级B".equals(roleDescName2)){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能删除作业区级或场站级账号");
                return apiResult;
            }

            if (areaId != null && stationId != null){//删除场站级
                //删除场站级账号
                authAdminPopedomMapper.deleteRecord((Integer)map.get("adminId"),(Integer)map.get("stationId"),4);
            }
            if (areaId != null && stationId == null) {//删除作业区级
                //删除作业区级账号
                authAdminPopedomMapper.deleteRecord((Integer)map.get("adminId"),(Integer)map.get("areaId"),3);
            }

        }
        if (roleId==valBranchId || roleId==valInstitute || roleId==valSupervisionId){//分公司级
            if (roleId != valInstitute){//不是安研院只能删除气矿 作业区 场站 账号
                if (!"气矿级A".equals(roleDescName2) && !"气矿级B".equals(roleDescName2) && !"气矿级C".equals(roleDescName2) &&
                        !"作业区级A".equals(roleDescName2) &&
                        !"场站级".equals(roleDescName2) && roleDescName2!=null && !"作业区级B".equals(roleDescName2)){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("您只能删除气矿级、作业区级或场站级账号");
                    return apiResult;
                }
            }
            if (areaId != null && stationId != null){//删除场站级
                //删除场站级账号
                authAdminPopedomMapper.deleteRecord((Integer)map.get("adminId"),(Integer)map.get("stationId"),4);
            }
            if (areaId != null && stationId == null) {//删除作业区级
                //删除作业区级账号
                authAdminPopedomMapper.deleteRecord((Integer)map.get("adminId"),(Integer)map.get("areaId"),3);
            }
            if (areaId == null && stationId == null && factoryId != null){//删除气矿级
                //删除气矿级账号
                authAdminPopedomMapper.deleteRecord((Integer)map.get("adminId"),(Integer)map.get("factoryId"),2);
            }
            if (branchCompanyId!=null && areaId == null && stationId == null && factoryId == null){
                //删除分公司级账号
                authAdminPopedomMapper.deleteRecord((Integer)map.get("adminId"),branchCompanyId,1);
            }

        }

        //删除账号本身
        Integer adminId1 = (Integer)map.get("adminId");
        adminService.delete(Long.valueOf(adminId1));

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 新增 机构对应的账号和关系
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult addAdminInfo(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        /**
         * 判断权限
         * 分公司级 可以增加  气矿、作业区、场站
         * 分公司级 安研院工作人员 可以增加分公司级账号
         * 气矿级  可以增加  作业区、场站
         * 作业区级  可以增加 场站
         * 场站级 不可增加
         */
        Integer branchCompanyId = (Integer) map.get("branchCompanyId");
        Integer factoryId = (Integer) map.get("factoryId");
        Integer areaId = (Integer) map.get("areaId");
        Integer stationId = (Integer) map.get("stationId");
        Integer roleId = (Integer) map.get("roleId");
        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        Integer roleId2 = (Integer) map1.get("roleId");//获取账号等级名称
        if (roleId2==valStationId || roleId2==valStationPipelineId){//场站级
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId2==valTaskAreaAId || roleId2==valTaskAreaBId ){//作业区级
            if (areaId!=null && stationId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("不可新增作业区级账号");
                return apiResult;
            }
            if (roleId!=10 && roleId!=11){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("所选角色不符合");
                return apiResult;
            }
        }
        if (roleId2==valFactoryAId || roleId2==valFactoryBId || roleId2==valFactoryCId ){//气矿级
            if (factoryId!=null && areaId==null && stationId == null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("不可新增气矿级账号");
                return apiResult;
            }
            if (roleId!=8 && roleId!=9 && roleId!=10 && roleId!=11){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("所选角色不符合");
                return apiResult;
            }
        }
        if (roleId2==valBranchId || roleId2==valInstitute || roleId2==valSupervisionId){//分公司级
            if (branchCompanyId!=null && factoryId==null && areaId==null && stationId==null){
                //增加分公司级工作人员
                if (roleId2!=valInstitute){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("权限不足");
                    return apiResult;
                }
            }
        }


        Admin admin = new Admin();
        admin.setAdminName((String) map.get("adminName"));//设置用户名
        admin.setRoleId(Long.valueOf(roleId));//设置角色id
        admin.setAdminPass((String) map.get("adminPass"));//设置密码

        //先新增账号
        ApiResult result = adminService.addAdmin(admin);
        if (result.getCode() == 1000){//账号重复
            return result;
        }
        if (result.getCode() == 1001){//添加成功
            //添加账号姓名 手机号
            map.put("adminId",admin.getAdminId());
            int j = authAdminPopedomMapper.insertAdminInfo(map);
            if (j==0){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("姓名，手机号添加失败");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                return apiResult;
            }
            //添加机构管理信息
            if (stationId!=null){//添加场站的关联
                //场站级
                if (roleId!=10 && roleId!=11){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("所选角色不符合");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }
                Integer popId = (Integer) map.get("stationId");
                Integer popGrade = 4;
                //查询该场站是否绑定了该账号
                AuthAdminPopedom authAdminPopedom = authAdminPopedomMapper.findAdminByStationId(Long.valueOf(popId),admin.getAdminId(),popGrade);
                if (authAdminPopedom != null){
                    //已绑定了  修改为 一个场站可以绑定多个管理员
                   /* apiResult = ApiResult.FAIL();
                    apiResult.setMsg("该场站已绑定了管理员，请先删除");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;*/
                }else {
                    int i = authAdminPopedomMapper.addRecord(admin.getAdminId(), Long.valueOf(popId),popGrade);//添加记录
                    if (i==0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("绑定场站失败");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }else{
                        apiResult = ApiResult.SUCCESS();
                        return apiResult;
                    }
                }

            }
            if (factoryId!=null && areaId!=null && stationId==null){//添加作业区的关联
                if (roleId!=8 && roleId!=9){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("所选角色不符合");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }
                Integer popId = (Integer) map.get("areaId");
                int i = authAdminPopedomMapper.addRecord(admin.getAdminId(), Long.valueOf(popId),3);//添加记录
                if (i==0){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("绑定作业区失败");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }else{
                    apiResult = ApiResult.SUCCESS();
                    return apiResult;
                }
            }
            if (factoryId!=null && areaId==null && stationId==null){//添加气矿的关联
                if (roleId!=4 && roleId!=5 && roleId!=6){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("所选角色不符合");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }
                Integer popId = (Integer) map.get("factoryId");
                int i = authAdminPopedomMapper.addRecord(admin.getAdminId(), Long.valueOf(popId),2);//添加记录
                if (i==0){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("绑定气矿失败");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }else{
                    apiResult = ApiResult.SUCCESS();
                    return apiResult;
                }
            }
            if (branchCompanyId!=null && factoryId==null && areaId==null && stationId==null){//添加分公司的关联
                if (roleId!=1 && roleId!=2 && roleId!=3){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("所选角色不符合");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }
                Integer popId = (Integer) map.get("branchCompanyId");
                int i = authAdminPopedomMapper.addRecord(admin.getAdminId(), Long.valueOf(popId),1);//添加记录
                if (i==0){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("绑定分公司失败");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }else{
                    apiResult = ApiResult.SUCCESS();
                    return apiResult;
                }
            }

        }else {
            apiResult = ApiResult.FAIL();
            return apiResult;
        }
        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 修改 账号附属信息
     * @param map
     * @param request
     * @return
     */
    @Override
    public ApiResult updateAdminSubsidiaryInfo(Map map, HttpServletRequest request) {
        ApiResult apiResult;

        //获取adminId
        Long adminId = (Long) request.getSession().getAttribute("id");
        List<Map> maps = (List<Map>) request.getSession().getAttribute("roles");
        Map map1 = maps.get(0);
        String roleDescName = (String) map1.get("roleDescriptionName");//获取账号等级
        //根据adminId 查询账号等级
        String roleDescName2 = this.authAdminPopedomMapper.findRoleByAdminId((Integer)map.get("adminId"));
        Integer roleId = (Integer) map1.get("roleId");//获取账号等级
        if (roleId==valStationId || roleId==valStationPipelineId){//场站级
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("权限不足");
            return apiResult;
        }
        if (roleId==valTaskAreaAId || roleId==valTaskAreaBId ){//作业区级
            if (!"场站级".equals(roleDescName2) && roleDescName2!=null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能修改场站级账号");
                return apiResult;
            }
        }
        if (roleId==valFactoryAId || roleId==valFactoryBId || roleId==valFactoryCId ){//气矿级
            if (!"作业区级".equals(roleDescName2) && !"场站级".equals(roleDescName2) && roleDescName2!=null){
                apiResult = ApiResult.FAIL();
                apiResult.setMsg("您只能修改作业区级或场站级账号");
                return apiResult;
            }
        }
        if (roleId==valBranchId || roleId==valInstitute || roleId==valSupervisionId){//分公司级
            if ("分公司级".equals(roleDescName2)){
                if (roleId!=valInstitute){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("权限不足");
                    return apiResult;
                }
            }
        }
        //查询adminInfo表中是否存在信息
        int i = this.authAdminPopedomMapper.findAdminInfoById(map);
        if (i==0){
            //没有  则添加
            this.authAdminPopedomMapper.insertAdminInfo(map);
        }else {
            //修改adminInfo的信息
            this.authAdminPopedomMapper.updateAdminInfoById(map);
        }


        //修改密码
        String adminPass = (String) map.get("adminPass");
        if (adminPass!=null && !"".equals(adminPass.trim())){
            Admin admin = new Admin();
            Integer adminId1 = (Integer) map.get("adminId");
            admin.setAdminId(Long.valueOf(adminId1));
            admin.setAdminPass(adminPass);
            admin.setAdminName((String) map.get("adminName"));
            this.adminService.updateAdmin(admin);
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }
}
