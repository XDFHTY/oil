package com.cj.organization.service.impl;

import com.cj.admin.domain.AddAdminResp;
import com.cj.admin.mapper.AdminMapper;
import com.cj.admin.service.AdminService;
import com.cj.common.entity.*;
import com.cj.common.mapper.AuthRoleMapper;
import com.cj.core.domain.ApiResult;
import com.cj.common.utils.excle.ImportExeclUtil;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.organization.entity.*;
import com.cj.organization.mapper.*;
import com.cj.organization.service.ExcelImportService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Excel导入
 * Created by XD on 2018/10/10.
 */
@Service
@Transactional
public class ExcelImportServiceImpl implements ExcelImportService {

    //分公司id
    private Long branchCompanyId = 1L;

    @Autowired
    private BranchCompanyMapper branchCompanyMapper;

    @Autowired
    private FactoryBasicMsgMapper factoryMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private TaskAreaMapper areaMapper;

    @Autowired
    private StationCoreMapper stationCoreMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthAdminPopedomMapper authAdminPopedomMapper;

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AdminMapper adminMapper;




    /**
     * 导入组织机构基本信息
     * @param file
     * @param in
     * @return
     */
    @Override
    public ApiResult importInfo(MultipartFile file, InputStream in) {
        ApiResult apiResult;
        //返回提示信息
        StringBuffer msg = new StringBuffer();






        try {
            String fileName = file.getOriginalFilename(); //获取文件名
            Workbook workbook = ImportExeclUtil.chooseWorkbook(fileName, in);
            int sheets = workbook.getNumberOfSheets(); //获取sheet数量


            //遍历sheet  检查 父名称+子名称 是否有重复的值
            for (int i=0;i<sheets;i++){
                //读取sheet的第2行开始读取
                ImportOrganInfo info = new ImportOrganInfo();
                List<ImportOrganInfo> readBaseInfo = ImportExeclUtil.readDateListT(workbook, info, 2, 0, i);

                HashMap<String,Integer> hashMap=new HashMap<String, Integer>();
                for(ImportOrganInfo organInfo:readBaseInfo){
                    String string1 = organInfo.getSuperiorName();//父名称
                    String string2 = organInfo.getName();//子名称
                    String string = string1 + string2;
                    if(hashMap.get(string)!=null){  //hashMap包含遍历list中的当前元素
                        Integer integer=hashMap.get(string);
                        hashMap.put(string,integer+1);

                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("有重复的值 ==>   组织机构上级名称：" + string1 + "   组织机构名称：" + string2);
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }else{
                        hashMap.put(string,1);
                    }
                }

                //检查空值
                for (ImportOrganInfo organInfo:readBaseInfo){
                    //校验父名称是否为空
                    if (organInfo.getSuperiorName() == null || organInfo.getName() == null ||
                           organInfo.getSuperiorName().trim().length()==0 || organInfo.getName().trim().length() == 0){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("Sheet" + ++i + "中有空值，请检查");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }
                }

            }





            //存放 父名称 和 id
            Map map = new HashMap();
            //存放 父名称 不重复的集合
            Set<String> set = new HashSet<String>();

            //读取第一个sheet 父名称添加至不重复的集合中  添加至数据库 保存id到map
            ImportOrganInfo info1 = new ImportOrganInfo();
            List<ImportOrganInfo> readBaseInfo1 = ImportExeclUtil.readDateListT(workbook, info1, 2, 0, 0);
            for (ImportOrganInfo org:readBaseInfo1){
                set.add(org.getSuperiorName());
            }
            //迭代遍历 set  保存数据库 主键返回
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String str = it.next();
                System.out.println(str);
                //添加对象
                BranchCompany bc = new BranchCompany();
                bc.setBranchCompanyName(str);
                //根据名称查重
                BranchCompany branchCompany = this.branchCompanyMapper.findBCompanyByName(str);
                if (branchCompany != null){//重复  不添加
                    //把 名称 和 id 添加至map
                    map.put(branchCompany.getBranchCompanyName(),branchCompany.getBranchCompanyId());
                }else {
                    //添加  主键返回
                    this.branchCompanyMapper.addBCompany(bc);
                    //把名称 和 id 添加至map
                    map.put(bc.getBranchCompanyName(),bc.getBranchCompanyId());
                }
            }



            //读取第一个sheet 完成气矿信息添加
            ImportOrganInfo sheet1 = new ImportOrganInfo();
            List<ImportOrganInfo> orgInfo1 = ImportExeclUtil.readDateListT(workbook, sheet1, 2, 0, 0);

                //循环添加
                for (ImportOrganInfo organInfo : orgInfo1) {
                    //判断父名称是否有对应的id
                    if (map.get(organInfo.getSuperiorName()) == null){
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("在Sheet1中，组织机构上级名称：" + organInfo.getSuperiorName() + " 未录入，请先添加");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚v
                        return apiResult;
                    }

                    FactoryBasicMsg f = new FactoryBasicMsg();//创建添加对象
                    f.setFactoryName(organInfo.getName());//设置气矿名称
                    f.setBranchCompanyId((Long) map.get(organInfo.getSuperiorName()));//设置分公司id

                    //根据气矿名称和分公司id查重
                    FactoryBasicMsg factory = this.factoryMapper.findFactoryByName(organInfo.getName(),(Long) map.get(organInfo.getSuperiorName()) );
                    if (factory != null) {
                        //存在  把名称和id 加入map中
                        map.put(factory.getFactoryName(), factory.getFactoryId());
                    } else {
                        //不存在  添加 主键返回
                        this.factoryMapper.addFactory(f);
                        //把名称和id 加入map
                        map.put(f.getFactoryName(),f.getFactoryId());
                    }
                }

            //读取第二个sheet 完成作业区信息添加
            ImportOrganInfo sheet2 = new ImportOrganInfo();
            List<ImportOrganInfo> orgInfo2 = ImportExeclUtil.readDateListT(workbook, sheet2, 2, 0, 1);
            //循环添加
            for (ImportOrganInfo organInfo : orgInfo2) {
                //判断父名称是否有对应的id
                if (map.get(organInfo.getSuperiorName()) == null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("在Sheet2中，组织机构上级名称：" + organInfo.getSuperiorName() + " 未录入，请先添加");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }


                TaskArea t = new TaskArea();//创建添加对象
                t.setTaskAreaName(organInfo.getName());//设置作业区名称
                t.setFactoryId((Long) map.get(organInfo.getSuperiorName()));//设置气矿id

                //根据气矿id和作业区名称查重
                TaskArea taskArea = this.areaMapper.findAreaByName(organInfo.getName(),(Long) map.get(organInfo.getSuperiorName()) );
                if (taskArea != null) {
                    //存在  把名称和id 加入map中
                    map.put(taskArea.getTaskAreaName(), taskArea.getTaskAreaId());
                } else {
                    //不存在  添加 主键返回
                    this.areaMapper.addArea(t);
                    //把名称和id 加入map
                    map.put(t.getTaskAreaName(),t.getTaskAreaId());
                }
            }


            //读取第三个sheet 完成中心站信息添加
          /*  ImportOrganInfo sheet3 = new ImportOrganInfo();
            List<ImportOrganInfo> orgInfo3 = ImportExeclUtil.readDateListT(workbook, sheet3, 2, 0, 2);
            //循环添加
            for (ImportOrganInfo organInfo : orgInfo3) {
                //判断父名称是否有对应的id
                if (map.get(organInfo.getSuperiorName()) == null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("在Sheet3中，组织机构上级名称：" + organInfo.getSuperiorName() + " 未录入，请先添加");
                    return apiResult;
                }

                StationCore s = new StationCore();//创建添加对象
                s.setStationCoreName(organInfo.getName());//设置中心站名称
                s.setTaskAreaId((Long) map.get(organInfo.getSuperiorName()));//设置作业区id

                //根据作业区id和中心站名称查重
                StationCore stationCore = this.stationCoreMapper.findStationCoreByName(organInfo.getName(),(Long) map.get(organInfo.getSuperiorName()) );
                if (stationCore != null) {
                    //存在  把名称和id 加入map中
                    map.put(stationCore.getStationCoreName(), stationCore.getStationCoreId());
                } else {
                    //不存在  添加 主键返回
                    this.stationCoreMapper.addStationCore(s);
                    //把名称和id 加入map
                    map.put(s.getStationCoreName(),s.getStationCoreId());
                }
            }
*/

            //读取第三个sheet 完成场站信息添加
            ImportOrganInfo sheet3 = new ImportOrganInfo();
            List<ImportOrganInfo> orgInfo3= ImportExeclUtil.readDateListT(workbook, sheet3, 2, 0, 2);
            //循环添加
            for (ImportOrganInfo organInfo : orgInfo3) {
                //判断父名称是否有对应的id
                if (map.get(organInfo.getSuperiorName()) == null){
                    apiResult = ApiResult.FAIL();
                    apiResult.setMsg("在Sheet3中，组织机构上级名称：" + organInfo.getSuperiorName() + " 未录入，请先添加");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                    return apiResult;
                }

                Station s = new Station();//创建添加对象
                s.setStationName(organInfo.getName());//设置场站名称
                s.setTaskAreaId((Long) map.get(organInfo.getSuperiorName()));//设置作业区名称

                //根据作业区id和场站名称查重
                Station station = this.stationMapper.findStationByName(organInfo.getName(),(Long) map.get(organInfo.getSuperiorName()) );
                if (station != null) {//最后一级  无需加入map
                    //存在  把名称和id 加入map中
                    //map.put(stationCore.getStationCoreName(), stationCore.getStationCoreId());
                } else {
                    //不存在  添加
                    this.stationMapper.addStation(s);
                    //把名称和id 加入map
                    //map.put(s.getStationCoreName(),s.getStationCoreId());
                }
            }



            apiResult = ApiResult.SUCCESS();
        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
        }


        return apiResult;

    }

    /**
     * 导出组织机构名称，生成需要填写账号格式表
     * @param response
     * @param request
     * @return
     */
    @Override
    public ApiResult organizationExport(HttpServletResponse response, HttpServletRequest request) {
        ApiResult apiResult;

        //查询数据 根据分公司id查询 全部组织机构名称
        List<ExprotOrganInfo> list = this.factoryMapper.findAllName(branchCompanyId);

        //导出
        OutputStream out = null;
        try {
            //获取流
            try {
                out = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Excel结果集
            List<List<String>> dataHandler = new ArrayList<List<String>>();
            if (list != null && list.size() != 0) {
                for (ExprotOrganInfo info : list) {
                    if (info != null) {
                        List<String> stringList = new ArrayList<>();
                        //设置气矿名称
                        if (info.getFactoryName()==null){
                            stringList.add("");
                        }else {
                            stringList.add(info.getFactoryName());
                        }
                        //设置作业区名称
                        if (info.getTaskAreaName()==null){
                            stringList.add("");
                        }else {
                            stringList.add(info.getTaskAreaName());
                        }
                        //设置场站名称
                        if (info.getStationName()==null){
                            stringList.add("");
                        }else {
                            stringList.add(info.getStationName());
                        }

                        dataHandler.add(stringList);
                    }
                }
            }

            //设置表头
            List<String> excle1 = new ArrayList<>();
            excle1.add("        气、矿        ");
            excle1.add("        作业区        ");
            excle1.add("      场站、管线        ");
            excle1.add("       工艺分类        ");
            excle1.add("         账号        ");
            excle1.add("         姓名        ");
            excle1.add("         角色        ");
            excle1.add("        手机号        ");

            List<String> num1 = new ArrayList<>();
            num1.add("0,0,0,0");
            num1.add("0,0,1,1");
            num1.add("0,0,2,2");
            num1.add("0,0,3,3");
            num1.add("0,0,4,4");
            num1.add("0,0,5,5");
            num1.add("0,0,6,6");
            num1.add("0,0,7,7");



            String[] excelHeader1 = excle1.toArray(new String[0]);
            String[] headnum1 = num1.toArray(new String[0]);

            // 声明一个工作簿
            HSSFWorkbook wb = new HSSFWorkbook();
            // 生成一种样式
            HSSFCellStyle style = wb.createCellStyle();
            // 设置单元格上、下、左、右的边框线
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);

            // 水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            // 生成一种字体
            HSSFFont font = wb.createFont();
            // 设置字体
            font.setFontName("微软雅黑");
            // 设置字体大小
            font.setFontHeightInPoints((short) 12);
            // 字体加粗
            font.setBold(true);
            // 把字体应用到当前的样式
            style.setFont(font);
            // 生成并设置另一个样式
            HSSFCellStyle style2 = wb.createCellStyle();
            // 设置单元格上、下、左、右的边框线
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setBorderRight(BorderStyle.THIN);
            style2.setBorderTop(BorderStyle.THIN);
            // 水平居中
            style2.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);

            // 生成一种字体
            HSSFFont font2 = wb.createFont();
            // 设置字体
            font2.setFontName("宋体");
            // 设置字体大小
            font2.setFontHeightInPoints((short) 12);

            // 把字体应用到当前的样式
            style2.setFont(font2);

            List<Map<String,Object>> headers = new ArrayList<>();
            Map<String,Object> header1 = new HashMap<>();
            header1.put("excelHeader",excelHeader1);
            header1.put("headnum",headnum1);
            header1.put("style",style);

            headers.add(header1);


            try {
                //导出信息
                //导出信息

                String fileName = "账号格式";
                exportExcelUtil.exportExcel2(wb,0,"账号格式",style2,headers,dataHandler,response,fileName);
                apiResult = ApiResult.SUCCESS();
            } catch (Exception e) {
                System.out.println("导出失败");
                e.printStackTrace();
                apiResult = ApiResult.FAIL();
            }
        }catch (Exception e) {
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return apiResult;
    }


    /**
     * 导出 作业区，生成需要填写账号格式表
     * @param response
     * @param request
     * @return
     */
    @Override
    public ApiResult taskAreaExport(HttpServletResponse response, HttpServletRequest request) {
        ApiResult apiResult;

        //查询数据 根据分公司id查询 查询 气矿、作业区名称
        List<ExprotOrganInfo> list = this.areaMapper.findFactoryAndAreaName(branchCompanyId);

        //导出
        OutputStream out = null;
        try {
            //获取流
            try {
                out = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Excel结果集
            List<List<String>> dataHandler = new ArrayList<List<String>>();
            if (list != null && list.size() != 0) {
                for (ExprotOrganInfo info : list) {
                    if (info != null) {
                        List<String> stringList = new ArrayList<>();
                        //设置气矿名称
                        if (info.getFactoryName()==null){
                            stringList.add("");
                        }else {
                            stringList.add(info.getFactoryName());
                        }
                        //设置作业区名称
                        if (info.getTaskAreaName()==null){
                            stringList.add("");
                        }else {
                            stringList.add(info.getTaskAreaName());
                        }


                        dataHandler.add(stringList);
                    }
                }
            }

            //设置表头
            List<String> excle1 = new ArrayList<>();
            excle1.add("        气、矿        ");
            excle1.add("        作业区        ");
            excle1.add("         账号        ");
            excle1.add("         姓名        ");
            excle1.add("         角色        ");
            excle1.add("        手机号        ");

            List<String> num1 = new ArrayList<>();
            num1.add("0,0,0,0");
            num1.add("0,0,1,1");
            num1.add("0,0,2,2");
            num1.add("0,0,3,3");
            num1.add("0,0,4,4");
            num1.add("0,0,5,5");


            String[] excelHeader1 = excle1.toArray(new String[0]);
            String[] headnum1 = num1.toArray(new String[0]);

            // 声明一个工作簿
            HSSFWorkbook wb = new HSSFWorkbook();
            // 生成一种样式
            HSSFCellStyle style = wb.createCellStyle();
            // 设置单元格上、下、左、右的边框线
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);

            // 水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            // 生成一种字体
            HSSFFont font = wb.createFont();
            // 设置字体
            font.setFontName("微软雅黑");
            // 设置字体大小
            font.setFontHeightInPoints((short) 12);
            // 字体加粗
            font.setBold(true);
            // 把字体应用到当前的样式
            style.setFont(font);
            // 生成并设置另一个样式
            HSSFCellStyle style2 = wb.createCellStyle();
            // 设置单元格上、下、左、右的边框线
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setBorderRight(BorderStyle.THIN);
            style2.setBorderTop(BorderStyle.THIN);
            // 水平居中
            style2.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);

            // 生成一种字体
            HSSFFont font2 = wb.createFont();
            // 设置字体
            font2.setFontName("宋体");
            // 设置字体大小
            font2.setFontHeightInPoints((short) 12);

            // 把字体应用到当前的样式
            style2.setFont(font2);

            List<Map<String,Object>> headers = new ArrayList<>();
            Map<String,Object> header1 = new HashMap<>();
            header1.put("excelHeader",excelHeader1);
            header1.put("headnum",headnum1);
            header1.put("style",style);

            headers.add(header1);


            try {
                //导出信息
                //导出信息

                String fileName = "账号格式";
                exportExcelUtil.exportExcel2(wb,0,"账号格式",style2,headers,dataHandler,response,fileName);
                apiResult = ApiResult.SUCCESS();
            } catch (Exception e) {
                System.out.println("导出失败");
                e.printStackTrace();
                apiResult = ApiResult.FAIL();
            }
        }catch (Exception e) {
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return apiResult;
    }

    /**
     * 导出 厂、矿，生成需要填写账号格式表
     * @param response
     * @param request
     * @return
     */
    @Override
    public ApiResult factoryExport(HttpServletResponse response, HttpServletRequest request) {
        ApiResult apiResult;

        //查询数据 根据分公司id查询 查询 气矿名称
        List<ExprotOrganInfo> list = this.factoryMapper.findFactoryName(branchCompanyId);

        //导出
        OutputStream out = null;
        try {
            //获取流
            try {
                out = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Excel结果集
            List<List<String>> dataHandler = new ArrayList<List<String>>();
            if (list != null && list.size() != 0) {
                for (ExprotOrganInfo info : list) {
                    if (info != null) {
                        List<String> stringList = new ArrayList<>();
                        //设置气矿名称
                        if (info.getFactoryName()==null){
                            stringList.add("");
                        }else {
                            stringList.add(info.getFactoryName());
                        }


                        dataHandler.add(stringList);
                    }
                }
            }

            //设置表头
            List<String> excle1 = new ArrayList<>();
            excle1.add("        气、矿        ");
            excle1.add("         账号        ");
            excle1.add("         姓名        ");
            excle1.add("         角色        ");
            excle1.add("        手机号        ");


            List<String> num1 = new ArrayList<>();
            num1.add("0,0,0,0");
            num1.add("0,0,1,1");
            num1.add("0,0,2,2");
            num1.add("0,0,3,3");
            num1.add("0,0,4,4");

            String[] excelHeader1 = excle1.toArray(new String[0]);
            String[] headnum1 = num1.toArray(new String[0]);

            // 声明一个工作簿
            HSSFWorkbook wb = new HSSFWorkbook();
            // 生成一种样式
            HSSFCellStyle style = wb.createCellStyle();
            // 设置单元格上、下、左、右的边框线
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);

            // 水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            // 生成一种字体
            HSSFFont font = wb.createFont();
            // 设置字体
            font.setFontName("微软雅黑");
            // 设置字体大小
            font.setFontHeightInPoints((short) 12);
            // 字体加粗
            font.setBold(true);
            // 把字体应用到当前的样式
            style.setFont(font);
            // 生成并设置另一个样式
            HSSFCellStyle style2 = wb.createCellStyle();
            // 设置单元格上、下、左、右的边框线
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setBorderRight(BorderStyle.THIN);
            style2.setBorderTop(BorderStyle.THIN);
            // 水平居中
            style2.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            style2.setVerticalAlignment(VerticalAlignment.CENTER);

            // 生成一种字体
            HSSFFont font2 = wb.createFont();
            // 设置字体
            font2.setFontName("宋体");
            // 设置字体大小
            font2.setFontHeightInPoints((short) 12);

            // 把字体应用到当前的样式
            style2.setFont(font2);

            List<Map<String,Object>> headers = new ArrayList<>();
            Map<String,Object> header1 = new HashMap<>();
            header1.put("excelHeader",excelHeader1);
            header1.put("headnum",headnum1);
            header1.put("style",style);

            headers.add(header1);


            try {
                //导出信息
                //导出信息

                String fileName = "账号格式";
                exportExcelUtil.exportExcel2(wb,0,"账号格式",style2,headers,dataHandler,response,fileName);
                apiResult = ApiResult.SUCCESS();
            } catch (Exception e) {
                System.out.println("导出失败");
                e.printStackTrace();
                apiResult = ApiResult.FAIL();
            }
        }catch (Exception e) {
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return apiResult;
    }



    /**
     * 导入气矿和账号的关系表格
     * @param file
     * @param in
     * @return
     */
    @Override
    public ApiResult importFactoryAdmin(MultipartFile file, InputStream in) {
        ApiResult apiResult;
        //返回提示信息
        StringBuffer msg = new StringBuffer();

        try {
            String fileName = file.getOriginalFilename(); //获取文件名
            Workbook workbook = ImportExeclUtil.chooseWorkbook(fileName, in);
            int sheets = workbook.getNumberOfSheets(); //获取sheet数量

            //读取第一个sheet的第2行开始读取
            ImprotFactoryAdminInfo info = new ImprotFactoryAdminInfo();
            List<ImprotFactoryAdminInfo> readBaseInfo = ImportExeclUtil.readDateListT(workbook, info, 2, 0, 0);
            if (readBaseInfo != null){
                for (ImprotFactoryAdminInfo adminInfo:readBaseInfo){
                    //根据气矿名称和分公司id 查询
                    FactoryBasicMsg factory = this.factoryMapper.findFactoryByName(adminInfo.getFactoryName(), branchCompanyId);
                    if (factory == null) {//输入的气矿名称不存在
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("未找到：" + adminInfo.getFactoryName() + ",请先添加");

                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;

                    }
                    //根据角色名称 查询角色id
                    Long roleId = this.authRoleMapper.findRoleIdByName(adminInfo.getRoleName());
                    if (roleId == null){//未找到等级和角色
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("未找到角色："+ adminInfo.getRoleName() + ",请先添加");

                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;

                    }
                    Long adminId = null;
                    //根据用户名查询账号
                    AdminAndRole a = this.factoryMapper.findAdminByName(adminInfo.getAdminName());
                    if (a == null){
                        //查询不到账号  注册
                        Admin admin = new Admin();
                        admin.setAdminName(adminInfo.getAdminName());
                        admin.setAdminPass("123456");
                        admin.setRoleId(roleId);

                        ApiResult apiResult1 = adminService.addAdmin(admin);
                        if ( apiResult1.getCode()==1000){
                            //账号已存在
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("账号：" + adminInfo.getAdminName() + " 新增失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;

                        }

                        if (apiResult1.getCode()==1001){
                            //新增成功 获取adminId
                            AddAdminResp addAdminResp = (AddAdminResp) apiResult1.getData();
                            adminId = addAdminResp.getAdminId();
                        }
                    }else {
                        adminId = a.getAdminId();
                        //判断账号角色等级
                        if (!"气矿级A".equals(a.getRoleDescriptionName()) && !"气矿级B".equals(a.getRoleDescriptionName()) && !"气矿级C".equals(a.getRoleDescriptionName())  ){
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("账号:" + a.getAdminName() + "，角色等级不是气矿级");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }
                        //根据管理员id 查询该账号是否绑定了 气矿
                        Map map = new HashMap();
                        map.put("adminId",adminId);
                        AuthAdminPopedom aap = this.authAdminPopedomMapper.findDataByAdminId(map);
                        if (aap != null){
                            //一个账号只能绑定一个气矿
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("账号:" + a.getAdminName() + "，一个账号只能绑定一个气矿或作业区");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }
                    }
                    //给管理员添加姓名
                    //根据管理员id查询是否添加了姓名
                    AdminInfo adminInfo1 = this.factoryMapper.findAdminInfoById(adminId);
                    if (adminInfo1 == null){//无 添加
                        this.factoryMapper.addFullName(adminId,adminInfo.getFullName(),adminInfo.getPhone());
                    }else {//有 更新
                        this.factoryMapper.updateNameById(adminId,adminInfo.getFullName(),adminInfo.getPhone());
                    }

                    //绑定关系  添加auth_admin_popedom表记录
                    //1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线
                    authAdminPopedomMapper.addRecord(adminId,factory.getFactoryId(),2);

                    
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }


    /**
     * 导入作业区和账号的关系表格
     * @param file
     * @param in
     * @return
     */
    @Override
    public ApiResult importAreaAdmin(MultipartFile file, InputStream in) {
        ApiResult apiResult;
        //返回提示信息
        StringBuffer msg = new StringBuffer();

        try {
            String fileName = file.getOriginalFilename(); //获取文件名
            Workbook workbook = ImportExeclUtil.chooseWorkbook(fileName, in);
            int sheets = workbook.getNumberOfSheets(); //获取sheet数量

            //读取第一个sheet的第2行开始读取
            ImprotAreaAdminInfo info = new ImprotAreaAdminInfo();
            List<ImprotAreaAdminInfo> readBaseInfo = ImportExeclUtil.readDateListT(workbook, info, 2, 0, 0);
            if (readBaseInfo != null){
                for (ImprotAreaAdminInfo adminInfo:readBaseInfo){
                    //根据气矿名称和分公司id 查询
                    FactoryBasicMsg factory = this.factoryMapper.findFactoryByName(adminInfo.getFactoryName(), branchCompanyId);
                    if (factory == null) {//输入的气矿名称不存在
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("未找到：" + adminInfo.getFactoryName() + ",请先添加");

                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;

                    }
                    //根据气矿id和作业区名称 查询
                    TaskArea taskArea = this.areaMapper.findAreaByName(adminInfo.getTaskAreaName(),factory.getFactoryId());
                    if (taskArea == null){//作业区名称不存在
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("未找到：" + adminInfo.getTaskAreaName() + ",请先添加");

                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;
                    }
                    //根据账号等级和角色名称 查询角色id
                    Long roleId = this.authRoleMapper.findRoleIdByName(adminInfo.getRoleName());
                    if (roleId == null){//未找到等级和角色
                        apiResult = ApiResult.FAIL();
                        apiResult.setMsg("未找到等级或角色：" + adminInfo.getRoleName() +  ",请先添加");

                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                        return apiResult;

                    }
                    Long adminId = null;
                    //根据用户名查询账号
                    AdminAndRole a = this.factoryMapper.findAdminByName(adminInfo.getAdminName());
                    if (a == null){
                        Admin admin = new Admin();
                        admin.setAdminName(adminInfo.getAdminName());
                        admin.setAdminPass("123456");
                        admin.setRoleId(roleId);

                        ApiResult apiResult1 = adminService.addAdmin(admin);
                        if ( apiResult1.getCode()==1000){
                            //账号已存在
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("账号：" + adminInfo.getAdminName() + " 新增失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;

                        }
                        if (apiResult1.getCode()==1001){
                            //新增成功 获取adminId
                            AddAdminResp addAdminResp = (AddAdminResp) apiResult1.getData();
                            adminId = addAdminResp.getAdminId();
                        }
                    }else {
                        adminId = a.getAdminId();
                        //判断账号角色等级
                        if (!"作业区级A".equals(a.getRoleDescriptionName()) && !"作业区级B".equals(a.getRoleDescriptionName())){
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("账号:" + a.getAdminName() + "，角色等级不是作业区级");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }
                        //根据管理员id 查询该账号是否绑定了 作业区
                        Map map = new HashMap();
                        map.put("adminId",adminId);
                        AuthAdminPopedom aap = this.authAdminPopedomMapper.findDataByAdminId(map);
                        if (aap != null){
                            //一个账号只能绑定一个气矿
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("账号:" + a.getAdminName() + "，一个账号只能绑定一个气矿或作业区");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }
                    }
                    //给管理员添加姓名
                    //根据管理员id查询是否添加了姓名
                    AdminInfo adminInfo1 = this.factoryMapper.findAdminInfoById(adminId);
                    if (adminInfo1 == null){//无 添加
                        this.factoryMapper.addFullName(adminId,adminInfo.getFullName(),adminInfo.getPhone());
                    }else {//有 更新
                        this.factoryMapper.updateNameById(adminId,adminInfo.getFullName(),adminInfo.getPhone());
                    }
                    //绑定关系  添加auth_admin_popedom表记录
                    //1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线
                    authAdminPopedomMapper.addRecord(adminId,taskArea.getTaskAreaId(),3);


                }
            }

        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 导入导入场站 和账号的关系表格
     * @param file
     * @param in
     * @return
     */
    @Override
    public ApiResult importStationAdmin(MultipartFile file, InputStream in) {
        ApiResult apiResult;
        //返回提示信息
        StringBuffer msg = new StringBuffer();

        try {
            String fileName = file.getOriginalFilename(); //获取文件名
            Workbook workbook = ImportExeclUtil.chooseWorkbook(fileName, in);
            int sheets = workbook.getNumberOfSheets(); //获取sheet数量

            //读取第一个sheet的第2行开始读取
            ExprotOrganInfo info = new ExprotOrganInfo();
            List<ExprotOrganInfo> readBaseInfo = ImportExeclUtil.readDateListT(workbook, info, 2, 0, 0);
            if (readBaseInfo != null){
                for (ExprotOrganInfo adminInfo:readBaseInfo){
                    if (adminInfo.getFactoryName() != null && adminInfo.getFactoryName().trim().length() != 0){
                        //根据气矿名称和分公司id 查询
                        FactoryBasicMsg factory = this.factoryMapper.findFactoryByName(adminInfo.getFactoryName(), branchCompanyId);
                        if (factory == null) {//输入的气矿名称不存在
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("未找到：" + adminInfo.getFactoryName() + ",请先添加");

                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;

                        }
                        //根据气矿id和作业区名称 查询
                        TaskArea taskArea = this.areaMapper.findAreaByName(adminInfo.getTaskAreaName(),factory.getFactoryId());
                        if (taskArea == null){//作业区名称不存在
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("未找到：" + adminInfo.getTaskAreaName() + ",请先添加");

                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }

                        //根据作业区id和场站名称 查询
                        Station station = this.stationMapper.findStationByName(adminInfo.getStationName(),taskArea.getTaskAreaId());
                        if (station == null){//场站名称不存在
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("未找到：" + adminInfo.getStationName() + ",请先添加");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }

                        //一个场站只能对应一个管理员  体现为 有则修改 无则添加
                        //根据场站id 和辖区分类id 4  查询这个区域是否已经添加了管理员
                        /*AuthAdminPopedom authAdminPopedom = this.authAdminPopedomMapper.findDataByPopedomId(station.getStationId(),4);
                        if (authAdminPopedom != null){
                            //场站名称错误
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("机构名称：" + station.getStationName() + ",已经添加了管理员");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }*/

                        //根据账号等级和角色名称 查询角色id
                        Long roleId = this.authRoleMapper.findRoleIdByName(adminInfo.getRoleName());
                        if (roleId == null){//未找到等级和角色
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("未找到等级或角色：" + adminInfo.getRoleName() + " ,请先添加");

                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;

                        }

                        Long adminId = null;
                        //根据用户名查询账号
                        AdminAndRole a = this.factoryMapper.findAdminByName(adminInfo.getAdminName());
                        if (a == null){
                            Admin admin = new Admin();
                            admin.setAdminName(adminInfo.getAdminName());
                            admin.setAdminPass("123456");
                            admin.setRoleId(roleId);

                            ApiResult apiResult1 = adminService.addAdmin(admin);
                            if ( apiResult1.getCode()==1000){
                                //账号已存在
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("账号：" + adminInfo.getAdminName() + " 新增失败");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                                return apiResult;

                            }

                            if (apiResult1.getCode()==1001){
                                //新增成功 获取adminId
                                AddAdminResp addAdminResp = (AddAdminResp) apiResult1.getData();
                                adminId = addAdminResp.getAdminId();

                            }
                        }else {
                            adminId = a.getAdminId();
                            //判断账号角色等级
                            if (!"场站级".equals(a.getRoleDescriptionName())){
                                apiResult = ApiResult.FAIL();
                                apiResult.setMsg("账号:" + a.getAdminName() + "，角色等级不是场站级");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                                return apiResult;
                            }
                            //一个账号可以绑定多个场站
                        }
                        //给管理员添加姓名
                        //根据管理员id查询是否添加了姓名
                        AdminInfo adminInfo1 = this.factoryMapper.findAdminInfoById(adminId);
                        if (adminInfo1 == null){//无 添加
                            this.factoryMapper.addFullName(adminId,adminInfo.getFullName(),adminInfo.getPhone());
                        }else {//有 更新
                            this.factoryMapper.updateNameById(adminId,adminInfo.getFullName(),adminInfo.getPhone());
                        }
                        //绑定关系  添加auth_admin_popedom表记录
                        //1-分公司，2-厂、矿，3-作业区，4-场站/管线
                        //根据场站id 和 adminId 查询是否已经绑定了关系
                        AuthAdminPopedom aap = this.authAdminPopedomMapper.findAdminByStationId(station.getStationId(),adminId,4);
                        if (aap == null){//添加
                            authAdminPopedomMapper.addRecord(adminId, station.getStationId(), 4);
                        }else {
                            //修改  根据AuthAdminPopedomId 修改管理员id
                            authAdminPopedomMapper.updateAdminIdByStationId(adminId, aap.getAuthAdminPopedom(), 4);
                        }



                        //根据工艺分类名称 查询id
                        Long factoryTypeId = this.factoryMapper.findFactoryTypeByName(adminInfo.getFactoryTypeName());
                        if (factoryTypeId == null){
                            apiResult = ApiResult.FAIL();
                            apiResult.setMsg("未找到工艺分类：" + adminInfo.getFactoryTypeName());
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult;
                        }
                        //设置工艺分类
                        this.stationMapper.updateFactoryTypeById(station.getStationId(),factoryTypeId);

                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            apiResult = ApiResult.FAIL();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

}
