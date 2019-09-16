package com.cj.exclepublic.read;

import com.cj.common.entity.ExcleTable;
import com.cj.common.entity.ExcleTableStructure;
import com.cj.common.entity.FactoryType;
import com.cj.core.domain.ApiResult;
import com.cj.exclepublic.mapper.AuthExcleFactoryMapper;
import com.cj.exclepublic.mapper.ExcleTableMapper;
import com.cj.exclepublic.mapper.ExcleTableStructureMapper;
import com.cj.exclepublic.mapper.FactoryTypeMapper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadServiceImpl implements ReadService {

    @Resource
    private FactoryTypeMapper factoryTypeMapper;

    @Resource
    private AuthExcleFactoryMapper authExcleFactoryMapper;


    @Resource
    private ExcleTableStructureMapper excleTableStructureMapper;

    @Resource
    private ExcleTableMapper excleTableMapper;

    //读取excle表结构
    @Override
    public ApiResult readExcle(MultipartFile multipartFile, Boolean b) throws Exception {
        ApiResult apiResult = null;
        String fileName = multipartFile.getOriginalFilename();

        //工艺ID集合
        List<Long> factoryTypeIds = new ArrayList<>();
        if (fileName.indexOf("环境风险台账") != -1) {  //环境风险台账没有工艺

            factoryTypeIds.add(1l);
            factoryTypeIds.add(2l);
            factoryTypeIds.add(3l);
            factoryTypeIds.add(4l);
            factoryTypeIds.add(5l);
            factoryTypeIds.add(6l);
            factoryTypeIds.add(7l);
            factoryTypeIds.add(8l);
            factoryTypeIds.add(9l);
            factoryTypeIds.add(10l);
            factoryTypeIds.add(11l);

        } else if (fileName.indexOf("含硫场站、含油水场站、净化厂、轻烃站等级划分方法") != -1) {

            factoryTypeIds.add(1l);
            factoryTypeIds.add(2l);
            factoryTypeIds.add(3l);
            factoryTypeIds.add(4l);

        } else if (fileName.indexOf("固体废弃物堆存站等级划分方法") != -1) {

            factoryTypeIds.add(10l);

        } else if (fileName.indexOf("回注井站等级划分方法等级划分方法") != -1) {

            factoryTypeIds.add(6l);

        } else if (fileName.indexOf("含硫、输水、输油管线等级划分方法") != -1) {

            factoryTypeIds.add(7l);
            factoryTypeIds.add(8l);
            factoryTypeIds.add(-1l);

        } else if (fileName.indexOf("场站 违反环保管理要求识别表") != -1) {

            factoryTypeIds.add(1l);
            factoryTypeIds.add(2l);
            factoryTypeIds.add(3l);
            factoryTypeIds.add(4l);
            factoryTypeIds.add(5l);
            factoryTypeIds.add(6l);

        } else if (fileName.indexOf("管线 违反环保管理要求识别表") != -1) {

            factoryTypeIds.add(7l);
            factoryTypeIds.add(8l);
            factoryTypeIds.add(9l);
            factoryTypeIds.add(10l);
        } else {

            fileName = fileName.substring(1, fileName.indexOf("-"));
            String[] fileNames = fileName.split("、");

            //根据excle名字数组查询工艺ID集合
            List<FactoryType> factoryTypes = factoryTypeMapper.findFactoryTypeByName(fileNames);

            for (FactoryType factoryType : factoryTypes) {
                factoryTypeIds.add(factoryType.getFactoryTypeId());
            }
        }

        InputStream is = multipartFile.getInputStream();

        //1、获取Excel工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        int sheetNum = workbook.getNumberOfSheets();

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            //2、得到Excel工作表对象
            XSSFSheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName().trim();
            Map map = new HashMap();
            map.put("factoryTypeIds", factoryTypeIds);
            map.put("excleTableName", sheetName);

            ExcleTable excleTable = excleTableMapper.findExcleTable(map);

            int j = 0;

            if (excleTable == null) {  //不存在这张excle表
                excleTable = new ExcleTable();
                excleTable.setExcleTableName(sheetName);
                excleTable.setExcleSort(i + 1);
                //新增表结构列表信息
                excleTableMapper.insertSelective(excleTable);
                //新增表结构列表-工艺关联信息
                map.put("excleTableId", excleTable.getExcleTableId());
                authExcleFactoryMapper.addList(map);

                //读取表结构内容
                List<ExcleTableStructure> excleTableStructures = ReadExcleUtil.readeExcle(sheet, excleTable.getExcleTableId());
                //新增表结构信息
                j = excleTableStructureMapper.updateTabStru(excleTableStructures);


            } else {  //存在这张表
                //excle读取到的表结构信息
                List<ExcleTableStructure> excleTableStructures = ReadExcleUtil.readeExcle(sheet, excleTable.getExcleTableId());

                if (!b) {
                    //从数据库读取表结构信息
                    List<ExcleTableStructure> oldExcleTableStructures = excleTableStructureMapper.findExcleStruByFactoryTypeIdAndTableName(map);

                    //处理数据
                    for (ExcleTableStructure oldExcleTableStructure : oldExcleTableStructures) {
                        for (ExcleTableStructure newExcleTableStructure : excleTableStructures) {
                            if (newExcleTableStructure.getStartRow() == oldExcleTableStructure.getStartRow()
                                    && newExcleTableStructure.getStartCol() == oldExcleTableStructure.getStartCol()) {
                                oldExcleTableStructure.setCellMsg(newExcleTableStructure.getCellMsg());
                                oldExcleTableStructure.setOnlyRead(newExcleTableStructure.getOnlyRead());
                                oldExcleTableStructure.setTableHead(newExcleTableStructure.getTableHead());
                                oldExcleTableStructure.setCellType(newExcleTableStructure.getCellType());
                                oldExcleTableStructure.setHidden(newExcleTableStructure.getHidden());
                            }
                        }
                    }

                    //将表结构单元格内容更新
                    excleTableStructureMapper.updateTabStruById(oldExcleTableStructures);

                    j = oldExcleTableStructures.size();


                } else {
                    //删除旧的表结构信息，在添加新的表结构信息
                    excleTableStructureMapper.deleteTabStruByExcleTableId(excleTable.getExcleTableId());
                    j = excleTableStructureMapper.updateTabStru(excleTableStructures);

                }

            }

            strings.add(sheetName + " 表变更了" + j + "个单元格");
        }
        apiResult = ApiResult.SUCCESS();
        apiResult.setData(strings);
        return apiResult;
    }

}
