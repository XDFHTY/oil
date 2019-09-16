package com.cj.core.util;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Exrickx
 */
@Slf4j
public class IpUtil {

    /**
     * 你的APPKEY mob官网注册申请即可
     */
    private final static String APPKEY = "你的APPKEY";

    /**
     * Mob IP查询接口
     */
    private final static String GET_IP_LOCATE = "http://apicloud.mob.com/ip/query?key="+ APPKEY +"&ip=";

    /**
     * Mob IP天气查询接口
     */
    private final static String GET_IP_WEATHER = "http://apicloud.mob.com/v1/weather/ip?key="+ APPKEY +"&ip=";

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
//        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
//            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
//            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
//            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
//            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
//            System.out.println("getRemoteAddr ip: " + ip);
        }
//        log.info("==========获取客户端ip: " + ip);
        return ip;
    }

    /**
     * 获取IP返回地理天气信息
     * @param ip ip地址
     * @return
     */
    public static String getIpWeatherInfo(String ip){

        if(StrUtil.isNotBlank(ip)){
            String url = GET_IP_WEATHER + ip;
            String result= HttpUtil.get(url);
            return result;
        }
        return null;
    }

//    /**
//     * 获取IP返回地理信息
//     * @param ip ip地址
//     * @return
//     */
//    public static String getIpCity(String ip){
//        if(null != ip){
//            String url = GET_IP_LOCATE + ip;
//            String result="未知";
//            try{
//                String json= HttpUtil.get(url, 3000);
//                IpLocate locate=JsonUtil.gson.fromJson(json, IpLocate.class);
//                if(("200").equals(locate.getRetCode())){
//                    if(StrUtil.isNotBlank(locate.getResult().getProvince())){
//                        result=locate.getResult().getProvince()+" "+locate.getResult().getCity();
//                    }else{
//                        result=locate.getResult().getCountry();
//                    }
//                }
//            }catch (Exception e){
//                log.info("获取IP信息失败");
//            }
//            return result;
//        }
//        return null;
//    }
}
