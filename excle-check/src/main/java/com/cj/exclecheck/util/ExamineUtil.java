package com.cj.exclecheck.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 审查工具类
 * Created by XD on 2018/10/23.
 */
public class ExamineUtil {

    /**
     * 筛选出 集合2中 未出现的名称
     *
     * @param list1 需要填写的表
     * @param list2 已经填写的表
     * @return
     */
    public static String getTableName(List<String> list1, List<String> list2) {
        StringBuffer sb = new StringBuffer();
        for (String s : list1) {
            if (!list2.contains(s)){//不包含这个元素
                sb.append(s);
                sb.append("、");
            }
        }
        String s= String.valueOf(sb);
        if (!"".equals(s)){//如果不等于空  则去掉最后一个逗号
             s = s.substring(0,s.length() - 1);
        }else {
            return null;
        }
        return s;
    }

    /**
     * 场站 所对应的工艺需要填写的表
     * @param factoryTypeName
     * @return
     */
    public static List<String> addTableByStation(String factoryTypeName){
        List<String> tabaleList = new ArrayList<>();
        switch (factoryTypeName){
            case "轻烃厂" :
            case "其他":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "净化厂":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("后果模拟(H2S)");
                tabaleList.add("作业场所应急物资调查");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "含硫化氢场站":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟");
                tabaleList.add("作业场所应急物资调查");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "含油水场站":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "输气场站":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "回注井站":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "含油水管线":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "含硫化氢管线":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟");
                tabaleList.add("环境风险受体(1)");
                tabaleList.add("环境风险受体(2)");
                tabaleList.add("外环境关系图上传");
                break;
            case "输气管线":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
            case "固体废弃物堆存站":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                tabaleList.add("环境风险受体");
                tabaleList.add("外环境关系图上传");
                break;
        }
        return tabaleList;
    }


    /**
     * 作业区 所需要填写的表
     *
     * @param factoryTypeName
     * @return
     */
    public static List<String> addTableByTaskArea(String factoryTypeName) {
        List<String> tabaleList = new ArrayList<>();
        switch (factoryTypeName){
            case "轻烃厂" :
            case "其他":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                //tabaleList.add("环境风险受体");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("Q水");
                tabaleList.add("E水");
                tabaleList.add("M水");
                tabaleList.add("Q气");
                tabaleList.add("E气");
                tabaleList.add("M气");
                tabaleList.add("违法记录表(场站)");
                break;
            case "净化厂":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                //tabaleList.add("环境风险受体");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("后果模拟(H2S)");
                tabaleList.add("作业场所应急物资调查");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("Q水");
                tabaleList.add("E水");
                tabaleList.add("M水");
                tabaleList.add("Q气");
                tabaleList.add("E气");
                tabaleList.add("M气");
                tabaleList.add("违法记录表(场站)");
                break;
            case "含硫化氢场站":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                //tabaleList.add("环境风险受体");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟");
                tabaleList.add("作业场所应急物资调查");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("Q水");
                tabaleList.add("E水");
                tabaleList.add("M水");
                tabaleList.add("Q气");
                tabaleList.add("E气");
                tabaleList.add("M气");
                tabaleList.add("违法记录表(场站)");
                break;
            case "含油水场站":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                //tabaleList.add("环境风险受体");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("Q水");
                tabaleList.add("E水");
                tabaleList.add("M水");
                tabaleList.add("Q气");
                tabaleList.add("E气");
                tabaleList.add("M气");
                tabaleList.add("违法记录表(场站)");
                break;
            case "输气场站":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                tabaleList.add("风险防范及应急措施");
                //tabaleList.add("环境风险受体");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("违法记录表(场站)");
                break;
            case "回注井站":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                //tabaleList.add("环境风险受体");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("L水");
                tabaleList.add("E水");
                tabaleList.add("M水");
                tabaleList.add("L气");
                tabaleList.add("E气");
                tabaleList.add("M气");
                tabaleList.add("违法记录表(场站)");
                break;
            case "含油水管线":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                //tabaleList.add("环境风险受体");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("P值");
                tabaleList.add("违法记录表(管线)");
                break;
            case "含硫化氢管线":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                tabaleList.add("风险防范及应急措施");
                //tabaleList.add("环境风险受体(1)");
                //tabaleList.add("环境风险受体(2)");
                tabaleList.add("后果模拟");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("P值");
                tabaleList.add("违法记录表(管线)");
                break;
            case "输气管线":
                tabaleList.add("基本信息");
                tabaleList.add("风险物质和数量");
                //tabaleList.add("环境风险受体");
                tabaleList.add("风险防范及应急措施");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("违法记录表(管线)");
                break;
            case "固体废弃物堆存站":
                tabaleList.add("基本信息");
                tabaleList.add("Q水");
                tabaleList.add("Q气");
                //tabaleList.add("环境风险受体");
                tabaleList.add("生产工艺");
                tabaleList.add("风险防范及应急措施");
                tabaleList.add("后果模拟(地表水)");
                tabaleList.add("后果模拟(地下水)");
                tabaleList.add("作业场所应急物资调查");
                //tabaleList.add("外环境关系图上传");
                tabaleList.add("Q水");
                tabaleList.add("E水");
                tabaleList.add("M水");
                tabaleList.add("Q气");
                tabaleList.add("E气");
                tabaleList.add("M气");
                tabaleList.add("违法记录表(管线)");
                break;
        }
        return tabaleList;
    }

    /**
     * 气矿 所需要填写的表
     *
     * @param factoryTypeName
     * @return
     */
    public static List<String> addTableByFactory(String factoryTypeName) {
        return null;
    }


    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");

        List<String> list2 = new ArrayList<>();
        list2.add("a");
        /*list2.add("b");
        list2.add("c");*/

        String s =getTableName(list1,list2);
    }


}
