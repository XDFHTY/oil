package com.cj.exclepublic.read;

import com.cj.common.entity.ExcleTableStructure;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by brss on 2018/10/6.
 */
public class ReadExcleUtil {


    public static List<ExcleTableStructure> readeExcle(XSSFSheet sheet, long excleTableId) throws Exception {


        //3、获取所有合并后的单元格
        List<CellRangeAddress> combineCell = getCombineCell(sheet);


        List<ExcleTableStructure> excleTableStructures1 = new ArrayList<>();
        Cell cell = null;
        //4、循环读取所有表格
        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                CellStyle cellStyle = cell.getCellStyle();
                BorderStyle left = cellStyle.getBorderLeftEnum();
                BorderStyle right = cellStyle.getBorderRightEnum();
                BorderStyle top = cellStyle.getBorderTopEnum();
                BorderStyle bottom = cellStyle.getBorderBottomEnum();
                String style = "" + left + right + top + bottom;
                if (style.length() > 0 && style.equals("THINTHINTHINTHIN")) {

                    ExcleTableStructure excleTableStructure = new ExcleTableStructure();
                    excleTableStructure.setStartRow(cell.getRowIndex());
                    excleTableStructure.setEndRow(cell.getRowIndex());
                    excleTableStructure.setStartCol(cell.getColumnIndex());
                    excleTableStructure.setEndCol(cell.getColumnIndex());
                    excleTableStructure.setCellMsg(getCellValue(cell));
                    excleTableStructures1.add(excleTableStructure);
                }


            }
        }

        //整合合并单元格
        for (ExcleTableStructure ets : excleTableStructures1) {
            for (CellRangeAddress ca : combineCell) {
                if (ets.getStartRow() == ca.getFirstRow() && ets.getStartCol() == ca.getFirstColumn()) {
                    ets.setEndRow(ca.getLastRow());
                    ets.setEndCol(ca.getLastColumn());
                }

            }
        }


        //处理单元格信息
        for (ExcleTableStructure excleTableStructure : excleTableStructures1) {
            excleTableStructure.setExcleTableId(excleTableId);
            String msg = excleTableStructure.getCellMsg();

            //设置单元格类型

            if (msg.indexOf("yyyy") != -1) {
                excleTableStructure.setCellType("5");  //时间格式
                excleTableStructure.setCellMsg("0000-00-00");

            } else if (
                    msg.indexOf("收集批复文件") != -1
                            || msg.indexOf("收集资料") != -1
                            || msg.indexOf("收集政府划分文件") != -1
                    ) {

                excleTableStructure.setCellType("4");  //文件上传格式

            }  else if (msg.indexOf("||") != -1){ // 存在||

                excleTableStructure.setCellType("2");

            }else if (msg.indexOf("||") == -1 && msg.indexOf("|") != -1) { // 不存在||而存在|

                excleTableStructure.setCellType("2");  //单选框格式
                //将替换成,
                msg = msg.replaceAll("\\|", ",");
                String[] msgs = msg.split(",");
                excleTableStructure.setCellMsg(Arrays.toString(msgs));
            } else {
                excleTableStructure.setCellType("1");  //普通字符串
            }

            if (msg != null) {
                //msg 为  "" 、/： 、() 、 xx  设为可编辑
                if (msg.length() == 0 ||
                        (msg.length()>1 && msg.indexOf("/") != -1 && msg.indexOf("：") != -1) ||
                        msg.indexOf("：") != -1 ||
                        msg.indexOf("（）") != -1 ||
                        msg.indexOf("××") != -1 ||
                        (msg.length() == 4 && msg.indexOf("0000") != -1)) {
                    excleTableStructure.setOnlyRead("false");  //可写
                    excleTableStructure.setTableHead("false");  //非表头
                    excleTableStructure.setHidden("false");     //不用于计算
                } else if (msg.length() == 1 && msg.indexOf("/") != -1) {  // msg 为 / 设为不可编辑
                    excleTableStructure.setOnlyRead("true");
                    excleTableStructure.setTableHead("true");
                    excleTableStructure.setHidden("false");
                } else if (msg.indexOf("**") != -1 && (!"0**".equals(msg))) {
                    excleTableStructure.setCellType("6");
                    excleTableStructure.setOnlyRead("true");
                    excleTableStructure.setTableHead("true");
                    excleTableStructure.setHidden("true");

                } else if ("0**".equals(msg)) {
                    excleTableStructure.setCellType("6");
                    excleTableStructure.setOnlyRead("false");
                    excleTableStructure.setTableHead("false");
                    excleTableStructure.setHidden("true");

                } else if ("0*".equals(msg)) {
                    excleTableStructure.setCellType("6");
                    excleTableStructure.setOnlyRead("true");
                    excleTableStructure.setTableHead("true");
                    excleTableStructure.setHidden("true");

                }else if (msg.length() == 1 && "0".equals(msg)) {
                    excleTableStructure.setCellType("6");
                    excleTableStructure.setOnlyRead("false");
                    excleTableStructure.setTableHead("false");
                    excleTableStructure.setHidden("false");
                } else if ("0/0".equals(msg)) {
                    excleTableStructure.setCellMsg("0");
                    excleTableStructure.setCellType("6");
                    excleTableStructure.setOnlyRead("true");
                    excleTableStructure.setTableHead("true");
                    excleTableStructure.setHidden("true");

                } else if ("sum".equals(msg)) {
                    excleTableStructure.setCellType("7");
                    excleTableStructure.setOnlyRead("true");
                    excleTableStructure.setTableHead("true");
                    excleTableStructure.setHidden("false");

                } else {

                    excleTableStructure.setOnlyRead("true");  //只读
                    excleTableStructure.setTableHead("true"); //表头
                    excleTableStructure.setHidden("false");   //不用于计算
                }


            }

        }

        //4、读取所有下拉框中的数据
        List<ExcleTableStructure> excleTableStructures = readExcel2007(sheet);

        if (excleTableStructures.size() > 0) {
            //整合下拉框单元格信息
            for (ExcleTableStructure excleTableStructure1 : excleTableStructures1) {
                for (ExcleTableStructure excleTableStructure : excleTableStructures) {
                    if (excleTableStructure.getStartRow() == excleTableStructure1.getStartRow()
                            && excleTableStructure.getStartCol() == excleTableStructure1.getStartCol()) {

                        String msg = excleTableStructure.getCellMsg();
                        if (msg.indexOf("&") != -1) {
                            msg = msg.replaceAll("&", "");
                            excleTableStructure1.setCellType("3");
                        }else {
                            excleTableStructure1.setCellType("2");
                        }

                        excleTableStructure1.setCellMsg(msg);
                        excleTableStructure1.setOnlyRead("true");
                        excleTableStructure1.setTableHead("true");


                    }
                }
            }

        }

        //清理 **
        for (ExcleTableStructure excleTableStructure : excleTableStructures1) {
            if (excleTableStructure.getCellMsg().indexOf("**") != -1 || excleTableStructure.getCellMsg().indexOf("*") != -1) {
                excleTableStructure.setCellMsg(excleTableStructure.getCellMsg().replaceAll("\\*", ""));
            }
            if (excleTableStructure.getCellMsg().indexOf("||") != -1){
                excleTableStructure.setCellMsg(excleTableStructure.getCellMsg().replace("||", ""));

            }
        }

        return excleTableStructures1;
    }


    /**
     * 合并单元格处理,获取合并单元格数据
     *
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public static List<CellRangeAddress> getCombineCell(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            //获得合并单元格加入list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }

        return list;
    }

    //读取单元格内容
    private static String getCellValue(Cell fCell) {
        fCell.setCellType(Cell.CELL_TYPE_STRING);
        return fCell.getStringCellValue();
    }

    /**
     * 读取excel
     *
     * @param filepath
     */
    public static void readexcel2003(String filepath) {
        try {
            //2003读取方式 , 2007请用SSFWorkbook
            //读取默认模板Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filepath));
            //获取Sheet页
            HSSFSheet sheet = workbook.getSheetAt(1);
            List<HSSFDataValidation> validations = sheet.getDataValidations();
            for (HSSFDataValidation validation : validations) {
                CellRangeAddressList addressList = validation.getRegions();

                if (null == addressList || addressList.getSize() == 0) {
                    continue;
                }
                //获取单元格行位置
                int row = addressList.getCellRangeAddress(0).getFirstRow();
                //获取单元格列位置
                int column = addressList.getCellRangeAddress(0).getFirstColumn();
                //根据位置信息判断是不是自己想要获取的单元格位置
                if (row == 1 && column == 1) {
                    DataValidationConstraint constraint = validation.getValidationConstraint();
                    //获取单元格数组
                    String[] strs = constraint.getExplicitListValues();
                    //输出数组
                    System.out.println(StringUtils.join(strs, "-"));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("E:\\西南油气田/test.xlsx");
        int sheetIndex = 0;
        //1、获取Excel工作簿对象 xlsx
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int sheetNum = workbook.getNumberOfSheets();

        long factoryTypeId = 0;

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            //2、得到Excel工作表对象
            XSSFSheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();

            //读取单元格信息
            List<ExcleTableStructure> excleTableStructures = readeExcle(sheet, sheetIndex);

            for (ExcleTableStructure excleTableStructure : excleTableStructures) {
                System.out.println(excleTableStructure);

            }
        }


//        FileInputStream fis = new FileInputStream("D:/file/oil/test.xlsx");
//        XSSFWorkbook wb = new XSSFWorkbook(fis);
//        XSSFSheet sheet = wb.getSheetAt(0);
//
//        List<ExcleTableStructure> excleTableStructures = readExcel2007(sheet);
//        System.out.println(excleTableStructures);

    }

    public static List<ExcleTableStructure> readExcel2007(XSSFSheet sheet) throws IOException {


        List<XSSFDataValidation> validations = sheet.getDataValidations();
        //返回集合
        List<ExcleTableStructure> excleTableStructures = new ArrayList<>();


        for (XSSFDataValidation validation : validations) {
            ExcleTableStructure excleTableStructure = new ExcleTableStructure();

            CellRangeAddressList addressList = validation.getRegions();

            if (null == addressList || addressList.getSize() == 0) {
                continue;
            }
            //获取单元格行位置
            int row = addressList.getCellRangeAddress(0).getFirstRow();
            //获取单元格列位置
            int column = addressList.getCellRangeAddress(0).getFirstColumn();
            //根据位置信息判断是不是自己想要获取的单元格位置
            DataValidationConstraint constraint = validation.getValidationConstraint();
            //获取单元格数组
            String[] strs = constraint.getExplicitListValues();
            if (strs != null) {
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i].indexOf("|") != -1) {
                        String[] s = strs[i].split("\\|");

                        strs[i] = Arrays.toString(s);
                    }
                }

                excleTableStructure.setStartRow(row);
                excleTableStructure.setEndRow(row);
                excleTableStructure.setStartCol(column);
                excleTableStructure.setEndCol(column);
                excleTableStructure.setCellMsg(Arrays.toString(strs));

                excleTableStructures.add(excleTableStructure);
            }


        }


        return excleTableStructures;

    }


}
