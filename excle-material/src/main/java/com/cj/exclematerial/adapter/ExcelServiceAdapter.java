package com.cj.exclematerial.adapter;

import com.cj.common.entity.ExcleTableStructure;
import com.cj.common.entity.TableCellRecord;
import com.cj.core.domain.ApiResult;
import com.cj.exclepublic.domain.FindExcleStruByStIdAndTabNameResp;
import com.cj.exclepublic.service.ExcleService;
import com.cj.exclepublic.service.impl.ExcleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 黄维
 * @date 2018/12/10 13:10
 **/
@Component("excelServiceAdapter")
public class ExcelServiceAdapter implements ExcelDto {
    @Autowired
    ExcleService excleService;

    @Override
    public List<List<Map>> getExcelDto(Long stationId, String tableName, String year, HttpServletRequest request) {
        ApiResult apiResult=excleService.findExcleStruByStIdAndTabName(stationId,tableName,year,request);

        /**
         * 获取合并数据
         */
        List<ExcleTableStructure> excleTableStructures = getHB(apiResult);
        //列号-表头
        Map<Integer,String> columnMap=new LinkedHashMap<>();
        //表头-表数据
        Map<String,List> map=new LinkedHashMap<>();
        //重复表头标记量
        int index=0;
        for (ExcleTableStructure excleTableStructure:excleTableStructures) {
            int col=excleTableStructure.getStartCol();
            int row=excleTableStructure.getStartRow();
            String msg=excleTableStructure.getCellMsg();
            //获取表头
            String header=columnMap.get(col);
            if (row<1){
                continue;
            }
            if (row==1){
                if (header==null){
                    columnMap.put(col,msg);
                }
                else if (header.equals(msg)){
                    columnMap.put(col,msg+index++);
                }
                else {
                    columnMap.put(col,msg);
                }
            }else {
                if (header==null){
                    columnMap.put(col,"未知表头"+index++);
                    putData(map,"未知表头"+(index-1),msg);
                }
                else {
                    putData(map,header,msg);
                }
            }
        }
        List mapList = mapList(map);
        return mapList;
    }

    /**
     * 向Map的list添加数据
     * @param map
     * @param key
     * @param value
     * @return
     */
    private void putData(Map<String,List> map,String key,Object value){
        List data=map.get(key);
        if(data!=null){
            data.add(value);
        }else {
            data=new ArrayList();
            data.add(value);
            map.put(key,data);
        }
    }

    private List mapList(Map<String,List> map){
        //字段序号
        int index=0;
        List list=new ArrayList();
        //序号对应的Key
        Map<Integer,String> numKey=new HashMap();
        //序号对应的value
        Map numValue=new HashMap();
        //转换map
        for (Map.Entry<String, List> entry : map.entrySet()) {
            String key=entry.getKey();
            List valueList=entry.getValue();
            numKey.put(index,key);
            numValue.put(index,valueList);
            index++;
        }

        List valueTempList = (List) numValue.get(0);
        if (valueTempList != null && valueTempList.size()>0){
            for (int i = 0; i < valueTempList.size(); i++) {
                List list1=new ArrayList();
                for (int j = 0; j <index ; j++) {
                    Map ansMap=new HashMap(16);
                    String key=numKey.get(j);
                    List valueList=(List) numValue.get(j);
                    Object value=valueList.get(i);

                    ansMap.put("key",key);
                    ansMap.put( "value",value);
                    list1.add(ansMap);
                }
                list.add(list1);
            }
        }

       return list;
    }

    private  List<ExcleTableStructure> getHB(ApiResult apiResult1){
        List<ExcleTableStructure> structureList = new ArrayList<>();//拼接各个场站的集合
        //查询表结构及数据
        if (apiResult1 != null) {
            FindExcleStruByStIdAndTabNameResp info1 = (FindExcleStruByStIdAndTabNameResp) apiResult1.getData();
                    if (info1 != null) {
                        //表数据
                        List<TableCellRecord> tableCellRecords = info1.getTableCellRecords();
                        //表结构
                        List<ExcleTableStructure> excleTableStructures = info1.getExcleTableStructures();
                        //数据整合
                        Set set = new HashSet();
                        for (ExcleTableStructure excleTableStructure : excleTableStructures) {
                            set.add(excleTableStructure.getStartRow());
                            for (TableCellRecord tableCellRecord : tableCellRecords) {
                                if (tableCellRecord != null) {
                                    if (excleTableStructure.getExcleTableStructureId() == tableCellRecord.getExcleTableStructureId()) {
                                        excleTableStructure.setCellMsg(tableCellRecord.getCellMsg());
                                    }
                                }
                            }
                            structureList = excleTableStructures;
                }
            }
        }
        return structureList;
    }
}
