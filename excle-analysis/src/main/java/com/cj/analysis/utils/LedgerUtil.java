package com.cj.analysis.utils;

import com.cj.common.entity.ExcleTableStructure;
import com.cj.common.entity.TableCellRecord;
import com.cj.core.domain.ApiResult;

import java.util.*;

/**
 * 处理 风险物质和数量 物质名称 总量
 * Created by XD on 2018/10/19.
 */
public class LedgerUtil {
    /**
     * 含硫化氢场站 含油水场站 轻烃厂 净化厂 固体废弃物堆存站 其他
     * @param apiResult
     * @return
     */
    public static List getNameAndNum1(ApiResult apiResult){
        List<ExcleTableStructure> structureList1 = new ArrayList<>();
        List<ExcleTableStructure> structureList2 = new ArrayList<>();
        List<ExcleTableStructure> structureList3 = new ArrayList<>();
        StringBuffer sb1 = new StringBuffer();//风险物质
        StringBuffer sb2 = new StringBuffer();//最大储存量/t

        HashMap map3 = (HashMap) apiResult.getData();
        if (map3 != null){
            List<ExcleTableStructure> list2 = (List<ExcleTableStructure>) map3.get("excleTableStructures");//获取表结构
            List<TableCellRecord> list3 = (List<TableCellRecord>) map3.get("tableCellRecords");//获取表数据
            if (list2!=null){
                Map<Long,String> nameMap = new LinkedHashMap<>();
                ExcleTableStructure structure1 = list2.get(list2.size() - 1);//获取list最后一个元素
                Integer lastRow = structure1.getStartRow(); //表格最后一行行号
                for (ExcleTableStructure info:list2){
                    if(info.getStartRow()==0 || info.getEndRow()==1 || info.getEndRow()==lastRow
                            || info.getEndRow()==lastRow-1 ||info.getEndRow()==lastRow-2
                            || info.getStartCol() == 0){
                        info.setExcleTableStructureId(null);//把前两行 及倒数三行 第一列 id设为null 用于筛选
                    }
                }
                for (ExcleTableStructure info:list2){
                    if (info.getExcleTableStructureId() != null){
                        if (info.getStartCol()==1){//把开始列第一列放入集合
                            structureList1.add(info);
                        }
                        if (info.getStartCol()==2){//把开始列第二列放入集合
                            structureList2.add(info);
                        }
                        if (info.getStartCol()==4){//把开始列第四列放入集合
                            structureList3.add(info);
                        }
                    }
                }
                for (int i=0;i<structureList1.size();i++){
                    String s1 = structureList1.get(i).getCellMsg();//获取第一列的名称
                    String s2 = structureList2.get(i).getCellMsg();//获取第二列的名称
                    String name = s1 + s2;
                    Long id = structureList3.get(i).getExcleTableStructureId();//获取第四列的id
                    nameMap.put(id,name);
                }


                Iterator<Map.Entry<Long, String>> it = nameMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Long, String> entry = it.next();
                    Long id = entry.getKey();
                    if (list3!=null){
                        for (TableCellRecord record:list3){
                            if (record.getExcleTableStructureId() == id &&
                                    !"0".equals(record.getCellMsg().trim()) &&
                                    !"".equals(record.getCellMsg().trim())){
                                sb1.append(entry.getValue());//设置风险物质
                                sb1.append("\n");
                                sb2.append(record.getCellMsg());//设置数量
                                sb2.append("\n");
                                break;
                            }
                        }
                    }
                }
            }
        }
        List<StringBuffer> list = new ArrayList<>();
        list.add(sb1);
        list.add(sb2);
        return list;
    }




    public static List getNameAndNum2(ApiResult apiResult){
        List<ExcleTableStructure> structureList1 = new ArrayList<>();
        List<ExcleTableStructure> structureList2 = new ArrayList<>();
        StringBuffer sb1 = new StringBuffer();//风险物质
        StringBuffer sb2 = new StringBuffer();//最大储存量/t

        HashMap map3 = (HashMap) apiResult.getData();
        if (map3 != null){
            List<ExcleTableStructure> list2 = (List<ExcleTableStructure>) map3.get("excleTableStructures");//获取表结构
            List<TableCellRecord> list3 = (List<TableCellRecord>) map3.get("tableCellRecords");//获取表数据
            if (list2!=null){
                Map<Long,String> nameMap = new LinkedHashMap<>();
                ExcleTableStructure structure1 = list2.get(list2.size() - 1);//获取list最后一个元素
                Integer lastRow = structure1.getStartRow(); //表格最后一行行号
                for (ExcleTableStructure info:list2){
                    if(info.getStartRow()==0 || info.getEndRow()==1 || info.getEndRow()==lastRow
                            || info.getEndRow()==lastRow-1
                            || info.getStartCol() == 0){
                        info.setExcleTableStructureId(null);//把前两行 及倒数二行 第一列 id设为null 用于筛选
                    }
                }
                for (ExcleTableStructure info:list2){
                    if (info.getExcleTableStructureId() != null){
                        if (info.getStartCol()==1){//把开始列第一列放入集合
                            structureList1.add(info);
                        }
                        if (info.getStartCol()==3){//把开始列第三列放入集合
                            structureList2.add(info);
                        }
                    }
                }
                for (int i=0;i<structureList1.size();i++){
                    String s1 = structureList1.get(i).getCellMsg();//获取第一列的名称
                    Long id = structureList2.get(i).getExcleTableStructureId();//获取第四列的id
                    nameMap.put(id,s1);
                }


                Iterator<Map.Entry<Long, String>> it = nameMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Long, String> entry = it.next();
                    Long id = entry.getKey();
                    if (list3!=null){
                        for (TableCellRecord record:list3){
                            if (record.getExcleTableStructureId() == id &&
                                    !"0".equals(record.getCellMsg().trim()) &&
                                    !"".equals(record.getCellMsg().trim())){
                                sb1.append(entry.getValue());//设置风险物质
                                sb1.append("\n");
                                sb2.append(record.getCellMsg());//设置数量
                                sb2.append("\n");
                                break;
                            }
                        }
                    }
                }
            }
        }
        List<StringBuffer> list = new ArrayList<>();
        list.add(sb1);
        list.add(sb2);
        return list;
    }
}
