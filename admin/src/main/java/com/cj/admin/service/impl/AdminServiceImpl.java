package com.cj.admin.service.impl;


import com.cj.admin.domain.AddAdminResp;
import com.cj.admin.domain.IfLoginResp;
import com.cj.admin.domain.UpdateAdminByAdminPassReq;
import com.cj.admin.mapper.AdminMapper;
import com.cj.admin.service.AdminService;
import com.cj.common.entity.Admin;
import com.cj.common.entity.AuthCustomerRole;
import com.cj.common.entity.AuthRole;
import com.cj.common.entity.Key64;
import com.cj.common.exception.UserException;
import com.cj.common.mapper.Key64Mapper;
import com.cj.common.service.AuthCustomerRoleService;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.MemoryData;
import com.cj.common.utils.jwt.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.cj.common.utils.md5.Md5Utils.MD5Encode;


@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private Key64Mapper key64Mapper;

    @Resource
    private AuthCustomerRoleService authCustomerRoleService;


    @Resource
    private RestTemplate restTemplate;

    Gson gson = new Gson();


//    @Value("${ip.white.list}")
//    private String[] ips;


    //添加账号
    @Override
    public ApiResult addAdmin(Admin admin) {
        ApiResult apiResult;
        //检查账号是否已存在
        Admin oldAdmin = adminMapper.findAdminByName(admin);

        long time = System.currentTimeMillis();

        //账号已存在
        if (oldAdmin != null) {
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("账号已存在");
            return apiResult;
        }
        //获取唯一主键
        Key64 key64 = new Key64();
        key64.setStub("a");
        //获取key-adminId
        key64Mapper.addKey64(key64);

        admin.setAdminId(key64.getId());
        //生成盐值
        String uuid = UUID.randomUUID().toString();
        admin.setSaltVal(uuid);
        admin.setAdminType("1");  //系统管理员
        admin.setCreateTime(new Date(time));

        if (admin.getAdminPass() == null || "".equals(admin.getAdminPass())) {
            admin.setAdminPass("123456"); //设置初始密码
        }

        //加密密码，MD5（主键+盐值+密码）
        String adminPass = MD5Encode(admin.getAdminId() + admin.getSaltVal() + admin.getAdminPass(), "UTF-8", true);

        admin.setAdminPass(adminPass);
        admin.setAdminType("1");  //设置为管理员

        int i = adminMapper.insertSelective(admin);

        //添加角色
        AuthCustomerRole authCustomerRole = new AuthCustomerRole();
        authCustomerRole.setCustomerId(key64.getId());
        authCustomerRole.setRoleId(admin.getRoleId());
        int k = authCustomerRoleService.addCustomerRole(authCustomerRole);

        Long adminId = admin.getAdminId();
        AddAdminResp addAdminResp = new AddAdminResp();
        addAdminResp.setAdminId(adminId);
        if (i == 1 && k == 1) {
            apiResult = ApiResult.SUCCESS();
            apiResult.setData(addAdminResp);
            return apiResult;
        } else {
            apiResult = ApiResult.FAIL();
            return apiResult;
        }

    }

    @Override
    public int updateAdmin(Admin admin) {
        ApiResult apiResult;
        Admin oldAdmin = adminMapper.findAdminByName(admin);
        String newAdminPass = MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + admin.getAdminPass(), "UTF-8", true);
        admin.setAdminPass(newAdminPass);
        int i = adminMapper.updateAdminPass(admin);

        return i;
    }

    @Override
    public int delete(Long adminId) {
        return adminMapper.deleteAdmin(adminId);
    }

    @Override
    public List<Admin> findAllAdmin() {
        return adminMapper.findAllAdmin();
    }


    //登录
    @Override
    public ApiResult ifLogin(Admin admin, HttpServletRequest request) {
        ApiResult apiResult = null;

        Admin oldAdmin = adminMapper.findAdminByUserName(admin);

        long time = System.currentTimeMillis();

        if (oldAdmin == null) {

            apiResult = ApiResult.FAIL();
            apiResult.setMsg("账号不存在");
            apiResult.setParams(request.getRequestURL());
            throw new UserException(apiResult);


        } else if (oldAdmin.getAdminPass().equals(MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + admin.getAdminPass(), "UTF-8", true))) {  //密码正确
            String token = "";

            Long adminId = oldAdmin.getAdminId();
            String adminName = oldAdmin.getAdminName();

            String tokenKey = adminId.toString();

            //查询账号角色信息
            List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(adminId);


            //设置token，有效期
            token = JwtUtil.getToken(adminId, adminName, oldAdmin.getAdminType(), roles);

            IfLoginResp ifLoginResp = new IfLoginResp();
            ifLoginResp.setToken(token);
            ifLoginResp.setIssuedAt(new Date(time));
            ifLoginResp.setRoles(roles);

            apiResult = ApiResult.SUCCESS();
            apiResult.setData(ifLoginResp);
            System.out.println(apiResult);
            return apiResult;
        } else {

            apiResult = ApiResult.FAIL();
            apiResult.setMsg("账号不存在或密码错误");
            return apiResult;

        }


    }

    @Override
    public IfLoginResp ADLogin(String userid, String token, String key) {

        String url = "http://10.89.1.101:10039/xysso/check?userid=" + userid + "&token=" + token + "&key=" + key;
        //验证token
        String b = restTemplate.getForObject(url, String.class);
        if (b.equals("F\n")) {
            return null;
        }
        //TODO:查询用户在系统内的信息，签发token，
        // 如果没有，查询用户相关信息，将用户注册到系统内，使用对应角色
        //需要id，用户名，姓名，电话，角色信息

        //获取用户名
        String type = "4";
        String dataIDs = userid;

        Map map = gson.fromJson(callInterface(type,userid),Map.class);
        Admin admin = new Admin();
        admin.setAdminName((String)map.get("EMAIL"));

        Admin oldAdmin = adminMapper.findAdminByUserName(admin);

        long time = System.currentTimeMillis();

        if (oldAdmin==null){
            //走注册流程
            //获取唯一主键
            Key64 key64 = new Key64();
            key64.setStub("a");
            //获取key-adminId
            key64Mapper.addKey64(key64);
            //生成盐值
            String uuid = UUID.randomUUID().toString();
            admin.setSaltVal(uuid);
            admin.setAdminId(key64.getId());
            admin.setAdminType("1");  //系统管理员
            admin.setCreateTime(new Date(time));
            admin.setAdminPass("123456"); //设置初始密码

            //加密密码，MD5（主键+盐值+密码）
            String adminPass = MD5Encode(admin.getAdminId() + admin.getSaltVal() + admin.getAdminPass(), "UTF-8", true);

            admin.setAdminPass(adminPass);
            admin.setAdminType("1");  //设置为管理员

            int i = adminMapper.insertSelective(admin);

            //添加角色
            AuthCustomerRole authCustomerRole = new AuthCustomerRole();
            authCustomerRole.setCustomerId(key64.getId());
//            //TODO:根据原有角色对应设置角色id
//            Map<String ,Integer> roleMap = new HashMap();
//            roleMap.put("",1);
//            roleMap.put("",2);
//            roleMap.put("",3);
//            roleMap.put("",4);
//            roleMap.put("",5);
//            roleMap.put("",6);
//            roleMap.put("",7);
//            roleMap.put("",8);
//            roleMap.put("",9);
//            roleMap.put("",10);
//            roleMap.put("",11);
//            //查询权限角色中间表
//            List<Map> list = gson.fromJson(callInterface("10",""),new TypeToken<List<Map>>(){}.getType());
//            //循环匹配角色
//            for(Map map1:list){
//                if(map1.get("USER_ID")==userid){
//                    authCustomerRole.setRoleId(roleMap.get((String)map1.get("ROLE_ID")).longValue());
//                    break;
//                }
//            }
//            int k = authCustomerRoleService.addCustomerRole(authCustomerRole);

            Long adminId = admin.getAdminId();
            oldAdmin.setAdminId(adminId);
            oldAdmin.setAdminName(admin.getAdminName());
        }

        String myToken = "";

        Long adminId = oldAdmin.getAdminId();
        String adminName = oldAdmin.getAdminName();

        String tokenKey = adminId.toString();

        //查询账号角色信息
        List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(adminId);


        //设置token，有效期
        myToken = JwtUtil.getToken(adminId, adminName, oldAdmin.getAdminType(), roles);


        IfLoginResp ifLoginResp = new IfLoginResp();
        ifLoginResp.setToken(myToken);
        ifLoginResp.setIssuedAt(new Date(time));
        ifLoginResp.setRoles(roles);

        return ifLoginResp;

    }

//    @Override
//    public IfLoginResp ipLogin(Admin admin, HttpServletRequest request) {
//
//        ApiResult apiResult = new ApiResult();
//        if (!Arrays.asList(ips).contains(request.getRemoteAddr())) {
//            apiResult = ApiResult.CODE_403();
//            apiResult.setMsg("此站点未加入受信任系统白名单");
//            throw new UserException(apiResult);
//        }
//
//
//        Admin oldAdmin = adminMapper.findAdminByUserName(admin);
//
//        long time = System.currentTimeMillis();
//
//        if (oldAdmin == null) {
//            apiResult = ApiResult.FAIL();
//            apiResult.setMsg("用户:"+admin.getAdminName()+" 未注册到环境风险管理平台，请联系管理人员");
//            throw new UserException(apiResult);
//
//        } else {  //不验证密码，
//            String token = "";
//
//            Long adminId = oldAdmin.getAdminId();
//            String adminName = oldAdmin.getAdminName();
//
//            String tokenKey = adminId.toString();
//
//            //查询账号角色信息
//            List<AuthRole> roles = authCustomerRoleService.findCustomerRoleById(adminId);
//
//
//            //设置token，有效期
//            token = JwtUtil.getToken(adminId, adminName, oldAdmin.getAdminType(), roles);
//
//
//
//
//            IfLoginResp ifLoginResp = new IfLoginResp();
//            ifLoginResp.setToken(token);
//            ifLoginResp.setIssuedAt(new Date(time));
//            ifLoginResp.setRoles(roles);
//
//            return ifLoginResp;
//        }
//    }

    //注销
    @Override
    public int ifLogout(HttpSession session) {
        Integer adminId = (Integer) session.getAttribute("id");
        String tokenKey = adminId.toString();
        if (MemoryData.getTokenMap().containsKey(tokenKey)) {
            MemoryData.getTokenMap().remove(tokenKey);  //删除adminId-token
        }
        return 1;
    }

    //修改密码，校验原密码
    @Override
    public ApiResult updateAdminByAdminPass(HttpSession session, UpdateAdminByAdminPassReq updateAdminByAdminPassReq) {


        String oldAdminPass = updateAdminByAdminPassReq.getOldAdminPass();
        String newAdminPass = updateAdminByAdminPassReq.getNewAdminPass();


        ApiResult apiResult;
        Admin admin = new Admin();
        admin.setAdminName((String) session.getAttribute("name"));
        Admin oldAdmin = adminMapper.findAdminByName(admin);

        int i = 0;
        if (MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + oldAdminPass, "UTF-8", true).equals(oldAdmin.getAdminPass())) {

            String md5Pass = MD5Encode(oldAdmin.getAdminId() + oldAdmin.getSaltVal() + newAdminPass, "UTF-8", true);
            oldAdmin.setAdminPass(md5Pass);

            admin.setAdminId(oldAdmin.getAdminId());
            admin.setAdminPass(md5Pass);
            i = adminMapper.updateAdminPass(admin);

            if (i > 0) {
                apiResult = ApiResult.SUCCESS();
            } else {
                apiResult = ApiResult.FAIL();
            }
        } else {

            apiResult = ApiResult.FAIL();
            apiResult.setMsg("密码错误");
        }


        return apiResult;
    }


    /**
     * 调用石化权限系统接口
     * <p>
     * 系统编码	sysID	字符串	必填	系统注册时的系统编码
     * 数据类型	type	字符串	必填	用于区分数据变化类型，比如：“1”代表组织机构数据发生变化。
     * 变更数据主键	dataIDs	字符串		为空时查询对应表中对应系统的全部数据
     * 返回数据格式	dataType	字符串		默认为1
     * 1返回xml
     * 2返回json
     */
    String sysID = "08003";

    /**
     * 根据参数不同调用接口，返回JSON
     * @param type
     * @param dataIDs
     * @return
     */
    public String callInterface(String type, String dataIDs) {

        String str = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:impl=\"http://impl.webservcice.eis.com/\">\n" +
                "\t<soapenv:Header/> \n" +
                "\t<soapenv:Body>\n" +
                "\t\t<impl:esbServiceOperation>\n" +
                "\t\t\t<arg0>\n" +
                "\t\t\t\t<Service>\n" +
                "\t\t\t\t\t<Route>\n" +
                "\t\t\t\t\t\t<SerialNO>1</SerialNO>\n" +
                "\t\t\t\t\t\t<ServiceID>70201000000001</ServiceID>\n" +
                "\t\t\t\t\t\t<ServiceTime>1</ServiceTime>\n" +
                "\t\t\t\t\t\t<SourceSysID>60501</SourceSysID>\n" +
                "\t\t\t\t\t</Route>\n" +
                "\t\t\t\t\t<Data>\n" +
                "\t\t\t\t\t\t<Control></Control>\n" +
                "\t\t\t\t\t\t<Request>\n" +
                "\t\t\t\t\t\t\t<sysID>" + sysID + "</sysID>\n" +
                "\t\t\t\t\t\t\t<type>" + type + "</type>\n" +
                "\t\t\t\t\t\t\t<dataIDs>" + dataIDs + "</dataIDs>\n" +
                "\t\t\t\t\t\t\t<dataType>2</dataType>\n" +
                "\t\t\t\t\t\t</Request>\n" +
                "\t\t\t\t\t</Data>\n" +
                "\t\t\t\t</Service>\n" +
                "\t\t\t</arg0>\n" +
                "\t\t</impl:esbServiceOperation>\t\t\n" +
                "\t</soapenv:Body>\n" +
                "</soapenv:Envelope> \n";
        return restTemplate.postForObject("http://10.89.1.103:7080/httpservice", str, String.class);
    }

}