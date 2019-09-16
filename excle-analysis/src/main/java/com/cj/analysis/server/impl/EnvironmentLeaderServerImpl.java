package com.cj.analysis.server.impl;

import com.cj.analysis.mapper.EnvironmentCheckMapper;
import com.cj.analysis.server.EnvironmentLeaderServer;
import com.cj.analysis.vo.VoLedger;
import com.cj.common.entity.ExcleTableStructure;
import com.cj.common.entity.TableCellRecord;
import com.cj.core.domain.ApiResult;
import com.cj.exclepublic.domain.FindExcleStruByStIdAndTabNameResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service("environmentLeaderServer")
public class EnvironmentLeaderServerImpl implements EnvironmentLeaderServer{


    @Autowired
    private EnvironmentCheckMapper environmentCheckMapper;

    @Autowired
    private com.cj.excleledger.service.LedgerService ledgerService;

    public List<ExcleTableStructure> getHB(ApiResult apiResult1 ,Integer grade) {
        List<ExcleTableStructure> structureList = new ArrayList<>();//拼接各个场站的集合
        //查询表结构及数据
        if (apiResult1 != null) {
            List<FindExcleStruByStIdAndTabNameResp> list = (List<FindExcleStruByStIdAndTabNameResp>) apiResult1.getData();
            if (list != null && list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    FindExcleStruByStIdAndTabNameResp info1 = list.get(i);
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
                        }
                        if (i == 0) {//第一个集合 全部set进新的集合
                            structureList = excleTableStructures;
                        } else {//后面的集合  取出表头 行号+i 添加至集合
                            if (grade == 2) {//排除前三行表头
                                for (ExcleTableStructure excleTableStructure : excleTableStructures) {
                                    if (excleTableStructure.getStartRow() != 0 && excleTableStructure.getStartRow() != 1
                                            && excleTableStructure.getStartRow() != 2) {//排除前三行表头
                                        excleTableStructure.setStartRow(excleTableStructure.getStartRow() + i);//行号+i
                                        excleTableStructure.setEndRow(excleTableStructure.getEndRow() + i);//行号+i
                                        structureList.add(excleTableStructure);//添加至集合
                                    }
                                }
                            }

                            if (grade == 1 || grade == 0) {//排除前四行表头
                                for (ExcleTableStructure excleTableStructure : excleTableStructures) {
                                    if (excleTableStructure.getStartRow() != 0 && excleTableStructure.getStartRow() != 1
                                            && excleTableStructure.getStartRow() != 2 && excleTableStructure.getStartRow() != 3) {//排除前四行表头
                                        excleTableStructure.setStartRow(excleTableStructure.getStartRow() + i);//行号+i
                                        excleTableStructure.setEndRow(excleTableStructure.getEndRow() + i);//行号+i
                                        structureList.add(excleTableStructure);//添加至集合
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }
        return structureList;
    }

    @Override
    public ApiResult getEnvironmentLeader(VoLedger query, HttpServletRequest request) {

        if (query.getTaskAreaIdList() != null && query.getTaskAreaIdList().size() != 0){

            String year=query.getDatetime();
            List list=environmentCheckMapper.getCheckStationId(year,query.getTaskAreaIdList());
            Map map=new HashMap();
            map.put("stationIdList",list);
            map.put("grade",query.getGrade());
            map.put("year",year);
            ApiResult apiResult=ledgerService.generateLedger(map,false,request);
            List<ExcleTableStructure> structureList=getHB(apiResult,query.getGrade());



            for (int i = 0; i < structureList.size(); i++) {
                ExcleTableStructure excleTableStructure=structureList.get(i);
                if (excleTableStructure.getCellType()!="5"){
                    excleTableStructure.setCellType("1");
                }
            }

            Map map1=new HashMap();
            List list1=new ArrayList();
            if (structureList.size()>0){
                map1.put("excleTableStructures",structureList);
                map1.put("tableCellRecords",new ArrayList<>());
                list1.add(map1);

            }
            else {
                apiResult.setMsg("暂无数据");
            }

            apiResult.setData(list1);
            return apiResult;
        }

        ApiResult apiResult = ApiResult.SUCCESS();
        return apiResult;

    }

    @Override
    public ApiResult getExcelEnvironmentLeader(VoLedger query,HttpServletResponse response, HttpServletRequest request) {
        List list=environmentCheckMapper.getCheckStationId(query.getDatetime().toString(),query.getTaskAreaIdList());
        String year=query.getDatetime();
        Map map=new HashMap();
        map.put("stationIdList",list);
        map.put("grade",query.getGrade());
        map.put("year",year);
        if (query.getGrade()==2){
            map.put("tableName","环境风险台账(重大)");
        }else if (query.getGrade()==1){
            map.put("tableName","环境风险台账(较大)");
        }else {
            map.put("tableName","环境风险台账(一般)");

        }

        return ledgerService.ledgerExport(map,response,request);
    }
}
