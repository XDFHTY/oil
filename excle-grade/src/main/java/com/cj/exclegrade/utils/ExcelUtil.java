package com.cj.exclegrade.utils;


import com.cj.exclegrade.annotation.ExcelField;
import com.cj.exclegrade.annotation.ExcelSheet;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class ExcelUtil {
    public static HSSFWorkbook getHSSFWorkbook(List list,Class c) throws ClassNotFoundException {
        /**
         * 记录需要动态增加列的列号和列标题
         */
        Map cglab=new HashMap();
        //创建一个Excel
        HSSFWorkbook wb = new HSSFWorkbook();

        //ExcelSheet里面存放了Sheet名
        ExcelSheet excelSheet= (ExcelSheet) c.getAnnotation(ExcelSheet.class);
        //excelSheet.value()获取sheet名然后设置sheet名
        HSSFSheet sheet = wb.createSheet(excelSheet.value());

        //设置列宽度大小
        sheet.setDefaultColumnWidth((short) 15*1);
        //设置行高
        sheet.setDefaultRowHeight((short)(15*20));
        //index标记表头列号
        int index=0;
        //标记表的行号
        int indexRow=0;
        HSSFRow hssfRow=null;
        HSSFCell headCell=null;
        List<Class> classList=new ArrayList();//定义一个存放类的栈
        List<Integer> classListHeadIndex=new ArrayList();//定义一个存放每个类开始位置
        classList.add(c);
        classListHeadIndex.add(0);
        //创建表头
        while (classList.size()!=0){
            int indexTemp=classList.size();//有多少向下扩展的属性

            hssfRow=sheet.createRow(indexRow++);
            //class c里面放有不需要动态增加的表头信息
            for (int k = 0; k<indexTemp ; k++) {
                Field[] fields = classList.remove(0).getDeclaredFields();
                int indexCell=classListHeadIndex.remove(0);
                for (int i = 0; i < fields.length; i++) {
                    ExcelField excelField=fields[i].getAnnotation(ExcelField.class);
                    if (excelField!=null){
                        headCell=hssfRow.createCell(indexCell++);
                        //设置表头的样式
                        setHeadStyle(headCell,wb);
                        //设置单元格内容
                        headCell.setCellValue(excelField.value());
                    }
                    if (!excelField.className().equals("")){
                        Class classTemp=Class.forName(excelField.className());
                        classList.add(classTemp);//向栈中添加一个类
                        classListHeadIndex.add(indexCell-1);
                        int count=countLeaf(classTemp);
                        //创建单元格合并
                        CellRangeAddress cellRangeAddress=new CellRangeAddress(indexRow-1,indexRow-1,indexCell-1,indexCell+count-2);
                        sheet.addMergedRegion(cellRangeAddress);
                        //设置合并后单元格的样式
                        setRangeStyle(cellRangeAddress,sheet);
                        indexCell=indexCell+count-1;
                    }
                }
                index=indexCell;
            }

        }
        //处理表头合并
        downRange(sheet,index);

        //表内容
        HSSFRow hssfRowData=null;
        for (int i = 0; i <list.size() ; i++) {
            hssfRowData=sheet.createRow(indexRow++);
            Object object=list.get(i);
            Field[] fieldDatas=object.getClass().getDeclaredFields();
            int temp=0;//标记数据项列号
            HSSFCell hssfCell=null;
            for (int j = 0; j <fieldDatas.length ; j++) {
                Type fieldType=fieldDatas[j].getType();//获取属性类型
                String fieldName=fieldDatas[j].getName();//获取属性名称
                //获取动态增加列的信息
                if (fieldType.getTypeName().equals("java.util.List")){
                    Map tempMap=getMap((List) getFieldValueByFieldName(fieldName,object));
                    List obExcelFieldList= (List) tempMap.get("obExcelField");
                    List obFieldValueList= (List) tempMap.get("obFieldValue");
                    for (int k = 0; k < obExcelFieldList.size(); k++) {
                        String obExcelField= (String) obExcelFieldList.get(k);
                        String obFieldValue=(String) obFieldValueList.get(k);
                        Integer clIndex= (Integer) cglab.get(obExcelField);
                        if (null==clIndex){
                            clIndex=index++;
                            cglab.put(obExcelField,clIndex);
                            headCell=hssfRow.createCell(clIndex);
                            /**
                             * 设置外边框和设置外边框的值
                             */
                            setHeadStyle(headCell,wb);
                            headCell.setCellValue(obExcelField);
                        }
                        hssfCell=hssfRowData.createCell(clIndex);
                        setDataStyle(hssfCell,wb);
                        hssfCell.setCellValue(obFieldValue);
                    }
                }
                /**
                 * 添加数据项
                 */
                ExcelField excelField=fieldDatas[j].getAnnotation(ExcelField.class);
                if (excelField!=null){
                    if (!excelField.className().equals("")){
                        List valueList=new ArrayList();
                        Object o=getFieldValueByFieldName(fieldDatas[j].getName(),object);
                        if(o==null){
                            for (int k = 0; k < countLeaf(fieldDatas[j].getType().getClass()); k++) {
                                valueList.add("");
                            }
                        }else {
                            valueLeaf(valueList,o);
                        }
                        for (int k = 0; k < valueList.size(); k++) {
                            hssfCell=hssfRowData.createCell(temp++);
                            Object value=valueList.get(k);
                            setDataStyle(hssfCell,wb);
                            setValue(hssfCell,value);//设置值
                        }
                    }else {
                        hssfCell=hssfRowData.createCell(temp++);
                        Object value= getFieldValueByFieldName(fieldDatas[j].getName(),object);
                        setDataStyle(hssfCell,wb);
                        setValue(hssfCell,value);//设置值
                    }
                }
            }

        }
        setAutoSizeColumn(sheet,index);
        return wb;
    }

    /**
     * 设置合并后的单元格样式
     */
    private static void setRangeStyle(CellRangeAddress cellRangeAddress,HSSFSheet sheet){
        RegionUtil.setBorderBottom(BorderStyle.THIN,cellRangeAddress,sheet); // 下边框
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet); // 左边框
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet); // 有边框
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet); // 上边框
    }

    /**
     * 单元格向下合并
     */
    private static void downRange(HSSFSheet sheet,int clounm){

        HSSFRow row=sheet.getRow(1);
        int rowNum=sheet.getLastRowNum();
        for (int i = 0; i <= clounm; i++) {
            if(row.getCell(i)==null){
                CellRangeAddress cellRangeAddress=new CellRangeAddress(0,rowNum,i,i);
                sheet.addMergedRegion(cellRangeAddress);
                setRangeStyle(cellRangeAddress,sheet);
            }
        }
    }
    /**
     * 为单元格样式添加外边框属性和居中属性
     * @param style 单元格样式对象
     */

    private static void setBord(HSSFCellStyle style){
        //设置下边框
        style.setBorderBottom(BorderStyle.THIN);
        //设置左边框
        style.setBorderLeft(BorderStyle.THIN);
        //设置右边框
        style.setBorderRight(BorderStyle.THIN);
        //设置下边框
        style.setBorderTop(BorderStyle.THIN);
        //设置居中样式
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    /**
     * 设置表头单元格样式
     */
    private static void setHeadStyle(HSSFCell hssfCell,HSSFWorkbook wb){
        HSSFCellStyle style=wb.createCellStyle();
        setBord(style);
        Font font=wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)12);
        font.setBold(true);//加粗
        style.setFont(font);//设置字体
        //设置背景色
/*        style.setFillForegroundColor((short) 13);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
        hssfCell.setCellStyle(style);

    }

    /**
     * 设置数据项单元格样式的样式
     * @param hssfCell 单元格对象
     * @param wb Excel对象
     */
    private static void setDataStyle(HSSFCell hssfCell,HSSFWorkbook wb){
        HSSFCellStyle style=wb.createCellStyle();
        setBord(style);
        Font font=wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)11);
        style.setFont(font);
        hssfCell.setCellStyle(style);
    }
    /**
     * 设置表格内容的值
     * @param hssfCell 单元格对象
     * @param value 单元格的值
     */
    private static void setValue(HSSFCell hssfCell,Object value){
        if (value instanceof String) {
            hssfCell.setCellValue(value.toString());
        }else if (value instanceof Integer) {
            hssfCell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            hssfCell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            hssfCell.setCellValue((Boolean) value);
        } else if (value instanceof Float) {
            hssfCell.setCellValue((Float) value);
        } else if (value instanceof Short) {
            hssfCell.setCellValue((Short) value);
        }else if (value instanceof Long) {
            hssfCell.setCellValue((Long) value);
        } else if (value instanceof Character) {
            hssfCell.setCellValue((Character) value);
        }
    }

    /**
     * 设置自动列宽
     * @param hssfSheet
     * @param size
     */
    private static void setAutoSizeColumn(HSSFSheet hssfSheet,int size){
        for (int i = 0; i <size ; i++) {
            hssfSheet.autoSizeColumn(i, true);
        }
    }

    /**
     * 深搜
     * 获取这个类下带ExcelField注解的子节点个数
     * @param c
     * @return
     * @throws ClassNotFoundException
     */
    private static int countLeaf(Class c) throws ClassNotFoundException {
        Field fields[]=c.getDeclaredFields();
        int ans=0;
        for (int i = 0; i <fields.length ; i++) {
            ExcelField excelField=fields[i].getAnnotation(ExcelField.class);
            if(excelField!=null){
                if (!excelField.className().equals("")){
                    ans+=countLeaf(Class.forName(excelField.className()));
                }else {
                    ans++;
                }
            }

        }
        return ans;
    }

    /**
     * 深搜
     * 获取叶子节点的值
     * @param list 传入的list
     * @param o 传入的object
     */
    private static void valueLeaf(List list,Object o){
        Class c=o.getClass();
        Field fields[]=c.getDeclaredFields();
        for (int i = 0; i <fields.length ; i++) {
            ExcelField excelField =fields[i].getAnnotation(ExcelField.class);
            if (excelField!=null){
                if (!excelField.className().equals("")){
                    valueLeaf(list,getFieldValueByFieldName(fields[i].getName(), o));
                }else {
                    list.add(getFieldValueByFieldName(fields[i].getName(), o));
                }

            }
        }
    }
    /**
     * 获取map
     * @param list
     * @return
     */
    public static Map getMap(List list){
        Map map =new LinkedHashMap();
        String obExcelField;
        String obFieldValue;
        List obExcelFieldList=new ArrayList();
        List obobFieldValueList=new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Object ob=list.get(i);
            Class c=ob.getClass();
            Field[] fields=c.getDeclaredFields();

            for (int j = 0; j < fields.length; j++) {
                String tempName=fields[j].getName();
                if (tempName.equals("obExcelField")){
                    obExcelFieldList.add((String) getFieldValueByFieldName(fields[j].getName(),ob));
                }
                if (tempName.equals("obFieldValue")){
                    obobFieldValueList.add((String) getFieldValueByFieldName(fields[j].getName(),ob));
                }
            }
        }
        map.put("obExcelField",obExcelFieldList);
        map.put("obFieldValue",obobFieldValueList);
        return map;
    }
    /**
     * 通过属性名字，调用相应的Get方法获取属性值
     * @param object
     * @param fieldName 属性名字
     * @return
     */
    private static Object getFieldValueByFieldName(String fieldName, Object object) {
        Class c=object.getClass();
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            String s=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            Method method=c.getMethod("get"+s);
            return method.invoke(object);
        } catch (Exception e) {
            return null;
        }
    }


    /*public static void main(String[] args) {
        FactoryEnvironmentView  testPojo=new FactoryEnvironmentView ();
        Object o=getFieldValueByFieldName("name",testPojo);
        List list=new ArrayList();

        EnvironmentTargetView environmentTarget1=new EnvironmentTargetView();
        EnvironmentTargetView environmentTarget2=new EnvironmentTargetView();
        environmentTarget1.setQ("Q1");
        environmentTarget1.setE("E1");
        environmentTarget1.setM("M1");
        environmentTarget2.setQ("Q2");
        environmentTarget2.setE("E2");
        environmentTarget2.setM("M");
        EnvironmentResultView environmentResultView=new EnvironmentResultView();
        environmentResultView.setGass(environmentTarget1);
        environmentResultView.setWater(environmentTarget2);
        FactoryEnvironmentView factoryEnvironmentView=new FactoryEnvironmentView();
        factoryEnvironmentView.setFactoryName("测试");
        factoryEnvironmentView.setFactoryTypeName("测试");
        factoryEnvironmentView.setResult("测试");
        factoryEnvironmentView.setTaskAreaName("测试");
        factoryEnvironmentView.setStationName("测试");
        factoryEnvironmentView.setEnvironmentResultView(environmentResultView);

        list.add(factoryEnvironmentView);
        HSSFWorkbook wb= null;
        try {
            wb = getHSSFWorkbook(list,FactoryEnvironmentView.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream("d:\\studens.xls");
            wb.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
    }
