package com.cj.exclegrade.server.impl;

import com.cj.common.entity.EnvironmentGrade;
import com.cj.common.entity.ExcleTable;
import com.cj.common.entity.TableCellRecord;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.exclegrade.mapper.EnvironmentGradeMapper;
import com.cj.exclegrade.pojo.EnvironmentGradeView;
import com.cj.exclegrade.server.EnvironmentGradeService;
import com.cj.exclegrade.vo.EnvironmentGradeVo;
import com.cj.exclegrade.vo.StationResultVo;
import com.cj.exclegrade.vo.VoEnvironment;
import com.cj.exclegrade.vo.VoEnvironmentStation;
import com.cj.exclepublic.domain.AddTableCellRecordsReq;
import com.cj.exclepublic.domain.FindExcleSortResp;
import com.cj.exclepublic.domain.UpdateTableCellRecordsReq;
import com.cj.exclepublic.service.ExcleService;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service("environmentGradeServer")
public class EnvironmentGradeServerImpl implements EnvironmentGradeService {

    @Resource
    ExcleService excleService;

    private ApiResult updateExclelData(List<AddTableCellRecordsReq> addTableCellRecordsReqs, HttpServletRequest request) {
        ApiResult apiResult;

        try {
            if(addTableCellRecordsReqs != null){
                for (AddTableCellRecordsReq info:addTableCellRecordsReqs){
                    Gson gson = new Gson();
                    ApiResult apiResult1 = excleService.findTableRecord(gson.toJson(info));

                    if (apiResult1.getData() != null && !"".equals(apiResult1.getData())) { //调修改
                        UpdateTableCellRecordsReq updateData = new UpdateTableCellRecordsReq();//修改对象
                        BeanUtils.copyProperties(info, updateData);

                        ApiResult apiResult2 = excleService.updateTableCellRecords(updateData, request);
                        if (apiResult2.getCode() == 1000) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult2;
                        }
                    }else {//调新增
                        ApiResult apiResult2 = excleService.addTableCellRecords(info, request);
                        if (apiResult2.getCode() == 1000){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                            return apiResult2;
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
            apiResult = ApiResult.FAIL();
            return apiResult;
        }

        apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 对EnvironmentGradeVo数据格式进行处理
     * @param environmentGradeVo
     * @return
     */
    private EnvironmentGrade formatterEnvironmentGradeVo(EnvironmentGradeVo environmentGradeVo){
        String target = environmentGradeVo.getTarget();
        if (target.indexOf("P")!=-1){
            environmentGradeVo.setTarget("P");
        }
        if (target.indexOf("L")!=-1){
            int i = environmentGradeVo.getTarget().indexOf("L");
            environmentGradeVo.setTarget("Q"+target.substring(i+1,i+2));
        }
        if (target.indexOf("C")!=-1){
            environmentGradeVo.setTarget("C");
        }
        if (target.indexOf("违法记录")!=-1){
            environmentGradeVo.setTarget("违法记录");
            if (environmentGradeVo.getResult().equals("是")){
                environmentGradeVo.setResult("true");
            }else {
                environmentGradeVo.setResult("false");
            }
        }
        EnvironmentGrade environmentGrade= new EnvironmentGrade();
        BeanUtils.copyProperties(environmentGradeVo,environmentGrade);
        Long tagerId=environmentGradeMapper.getTargetIdByTarget(environmentGradeVo.getTarget());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(environmentGradeVo.getDatetime(), pos);
        environmentGrade.setGradetime(strtodate);
        environmentGrade.setTarget(tagerId);
        return environmentGrade;
    }
    /**
     * 添加环境结果信息
     */
    @Resource
    EnvironmentGradeMapper environmentGradeMapper;

    @Override
    public ApiResult addEnvironmentGrade(EnvironmentGradeVo environmentGradeVo,HttpServletRequest request) {

        System.out.println(environmentGradeVo);
        ApiResult apiResult=updateExclelData(environmentGradeVo.getTableCellRecordsReqs(),request);
        if (1000==apiResult.getCode()){
            return apiResult;
        }
        EnvironmentGrade environmentGrade=formatterEnvironmentGradeVo(environmentGradeVo);
        int i=0;
        EnvironmentGrade temp=environmentGradeMapper.findByDate(environmentGrade);
        if (temp==null){
            i=environmentGradeMapper.insertSelective(environmentGrade);
        }
       else {
            environmentGrade.setEnvironmentalGradeId(temp.getEnvironmentalGradeId());
            i= environmentGradeMapper.updateByPrimaryKeySelective(environmentGrade);
        }
        if(i>0){
            apiResult=ApiResult.SUCCESS();
        }else{
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 修改环境结果
     * @param
     * @return
     */
    @Override
    public ApiResult updateEnvironmentGrade(EnvironmentGradeVo environmentGradeVo,HttpServletRequest request) {
        ApiResult apiResult=updateExclelData(environmentGradeVo.getTableCellRecordsReqs(),request);
        if (1000==apiResult.getCode()){
            return apiResult;
        }
        EnvironmentGrade environmentGrade= formatterEnvironmentGradeVo(environmentGradeVo);
        int i=environmentGradeMapper.updateByPrimaryKeySelective(environmentGrade);
            apiResult=ApiResult.SUCCESS();
        return apiResult;
    }

    /**
     * 转换结果
     * @param excleTablesGrades
     * @param environmentGradeViews
     * @return
     */
    private List<EnvironmentGradeView> encapsulation( List<ExcleTable> excleTablesGrades,List<EnvironmentGradeView> environmentGradeViews){
        List<EnvironmentGradeView> ans=new ArrayList<>();
        for (ExcleTable excleTable:excleTablesGrades) {
            int tab=0;//标记变量
            for (EnvironmentGradeView environmentGradeView:environmentGradeViews) {
                if (excleTable.getExcleTableName().indexOf(environmentGradeView.getTarget())!=-1){
                    EnvironmentGradeView temp=new EnvironmentGradeView();
                    BeanUtils.copyProperties(environmentGradeView,temp);
                    if ("false".equals(temp.getResult())){
                        temp.setResult("否");
                    }
                    if ("true".equals(temp.getResult())){
                        temp.setResult("是");
                    }
                    temp.setTarget(excleTable.getExcleTableName());
                    ans.add(temp);
                    tab=1;
                }else  if (
                        ("Q水".equals(environmentGradeView.getTarget()) && "L水".equals(excleTable.getExcleTableName()))
                        ||  ("Q气".equals(environmentGradeView.getTarget()) && "L气".equals(excleTable.getExcleTableName()))
                        ){
                    EnvironmentGradeView temp=new EnvironmentGradeView();
                    BeanUtils.copyProperties(environmentGradeView,temp);
                    if ("false".equals(temp.getResult())){
                        temp.setResult("否");
                    }
                    if ("true".equals(temp.getResult())){
                        temp.setResult("是");
                    }
                    temp.setTarget(excleTable.getExcleTableName());
                    ans.add(temp);
                    tab=1;

                }
            }

            if (tab==0){
                EnvironmentGradeView temp=new EnvironmentGradeView();
                temp.setTarget(excleTable.getExcleTableName());
                ans.add(temp);
            }
        }
        return ans;
    }
    /**
     * 查询环境结果(Q气，E气，M气，Q水，E水，M水)
     * @param query
     * @return
     */
    @Override
    public ApiResult findEnvironmentGrade(VoEnvironmentStation query,HttpSession httpSession) {
        ApiResult apiResult=null;
        try {
            Map map=new HashMap();
            map.put("stationId",query.getStationId().doubleValue());
            ApiResult excleSort = excleService.findExcleSort(map, httpSession);

            FindExcleSortResp findExcleSortResp = (FindExcleSortResp) excleSort.getData();
            List<ExcleTable> excleTablesGrades= findExcleSortResp.getExcleTablesGrade();
            List<EnvironmentGradeView> environmentGradeViews=environmentGradeMapper.findByQuery(query);
            List<EnvironmentGradeView> ans=encapsulation(excleTablesGrades,environmentGradeViews);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(ans);
        }catch (Exception e){
            System.out.println(e);
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    @Override
    public ApiResult findFactoryEnvironmentGrade(VoEnvironment query) {
        ApiResult apiResult=null;
        try {
            Pager pager=new Pager();
            List<List<?>> lists=environmentGradeMapper.findFactoryEnvironmentByQuery(query);

            //结果集处理
            List<Map> list0 = (List<Map>) lists.get(0);
            List<Map> list1 = (List<Map>) lists.get(1);

            Long total = (Long) (list1.get(0).get("total"));
            //总条数
            pager.setRecordTotal(total.intValue());
            //结果集
            pager.setContent(list0);
            pager.setCurrentPage(query.getCurrentPage()+1);
            apiResult=ApiResult.SUCCESS();
            apiResult.setData(pager);
        }catch (Exception e){
            System.out.println(e);
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }

    /**
     * 根据违法情况提升等级
     * @param result
     * @param illegal
     * @return
     */
    private String promoteResult(String result,boolean illegal){
        if(illegal){
            if ("一般".equals(result)){
                return "较大";
            }
            if ("较大".equals(result)){
                return "重大";
            }
            if ("重大".equals(result)){
                return "重大";
            }
        }
        return result;
    }

    /**
     * 比较环境风险结果
     * @param result1
     * @param result2
     * @return 最大的环境结果
     */
    private String compareResult(String result1,String result2){
        if (result1.equals("重大")||result2.equals("重大")){
            return "重大";
        }
        if (result1.equals("较大")||result2.equals("较大")){
            return "较大";
        }
        return "一般";
    }


    private static String getResultByPC(Integer P,Integer C){
        //最终评定准则
        String [][] result={{"较大","重大","重大"},{"一般","较大","重大"},{"一般","一般","较大"}};
        //c的评分准则
        Integer [] cResult={66,135,500};
//        P的评定准则
        Integer [] pResult={381,410,500};
        for (int i = 0; i < pResult.length; i++) {
            for (int j = 0; j < cResult.length; j++) {
                if (P<pResult[i]&&C<cResult[j]){
                    return result[i][j];
                }
            }
        }
        return null;
    }
    /**
     * 根据Q，E，M获取评价结果
     * @param Q
     * @param E
     * @param M
     * @return 评定结果
     */

    private String getResultByQEM(String Q,String E,String M){
//        转换Q
        Q="Q"+Q.substring(1,Q.length());
//      M的评定结果
        String[] resultJudgeM={"M1","M2","M3","M4"};
//        Q的评定结果
        String[] resultJudgeQ={"Q0","Q1","Q2","Q3"};
//        E的评定结果
        String[] resultJudgeE={"E1","E2","E3"};
//        最终评定结果
        String[][][] resultJudge={
                {{"一般","一般","一般","一般"},
                    {"较大","较大","重大","重大"},
                        {"较大","重大","重大","重大"},
                        {"重大","重大","重大","重大"}},
                {
                    {"一般","一般","一般","一般"},
                    {"一般","较大","较大","重大"},
                 {"较大","较大","重大","重大"},
                 {"较大","重大","重大","重大"}},
                {   {"一般","一般","一般","一般"},
                    {"一般","一般","较大","较大"},
                        {"一般","较大","较大","重大"},
                        {"较大","较大","重大","重大"}}
        };
        for (int i = 0; i <resultJudgeE.length ; i++) {
            for (int j = 0; j <resultJudgeQ.length ; j++) {
                for (int k = 0; k < resultJudgeM.length; k++) {
                    if (resultJudgeE[i].equals(E)&&resultJudgeM[k].equals(M)&&resultJudgeQ[j].equals(Q)){
                        return resultJudge[i][j][k];
                    }
                }
            }
        }
        return null;
    }

    //判断结果是否为空
    public List pdStationResult(StationResultVo stationResultVo){
        List list=new ArrayList();
        Class<? extends StationResultVo> aClass = stationResultVo.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field:declaredFields) {
            field.setAccessible(true);
            try {
                Object o = field.get(stationResultVo);
                if (o==null){
                    list.add(field.getName());
                }
            } catch (IllegalAccessException e) {

            }
        }
        return list;
    }

    @Override
    public ApiResult getStationResult(VoEnvironmentStation voEnvironmentStation,HttpSession session) {
        //在数据库中找数据
        StationResultVo stationResultVo=environmentGradeMapper.getResultByStationId(voEnvironmentStation);
        Map map=new HashMap();
        map.put("stationId",voEnvironmentStation.getStationId().doubleValue());
        ApiResult excleSort = excleService.findExcleSort(map, session);

        FindExcleSortResp findExcleSortResp = (FindExcleSortResp) excleSort.getData();
        List<ExcleTable> excleTablesGrades= findExcleSortResp.getExcleTablesGrade();
        int size = excleTablesGrades.size();

        //所有表都提交
        if (stationResultVo==null
                ){
            ApiResult apiResult=ApiResult.FAIL();
            apiResult.setMsg("风险等级评估信息未填写");
            return apiResult;
        }else if (
                (size==7)
                        &&
                        (
                stationResultVo.getQW()==null || stationResultVo.getEW()==null || stationResultVo.getMW()==null ||
                stationResultVo.getQG()==null || stationResultVo.getEG()==null || stationResultVo.getMG()==null ||
                stationResultVo.getQW().trim().length()==0 || stationResultVo.getEW().trim().length()==0 || stationResultVo.getMW().trim().length()==0 ||
                stationResultVo.getQG().trim().length()==0 || stationResultVo.getEG().trim().length()==0 || stationResultVo.getMG().trim().length()==0
                )
                ){
            ApiResult apiResult=ApiResult.FAIL();
            apiResult.setMsg("Q、E、M 值填写不完整");
            return apiResult;


        }else if ((size>1 && size<7) &&(stationResultVo.getP()==null || stationResultVo.getC()==null)){
            ApiResult apiResult=ApiResult.FAIL();
            apiResult.setMsg("P、C 值填写不完整");
            return apiResult;
        }

        if (stationResultVo.getDanger()==null){
            ApiResult apiResult=ApiResult.FAIL();
            apiResult.setMsg("违法情况表未提交");
            return apiResult;
        }
        //最终环境风险结果
        String result;
        //水环境风险结果
        String water;
        //大气环境风险结果
        String gas;
        //最最终结果描述
        String resultDescribe="";
        switch (stationResultVo.getStationType()){
            case "输水管线":
            case "净化厂":
            case "含硫化氢场站":
            case "含油水场站":
            case "回注井站":
            case "固体废弃物堆存站":
            case "轻烃厂":
            case "其他":{
                //计算水环境风险结果
                water=getResultByQEM(stationResultVo.getQW(),
                        stationResultVo.getEW(),
                        stationResultVo.getMW());
                //计算大气环境风险结果
                gas=getResultByQEM(stationResultVo.getQG(),
                        stationResultVo.getEG(),
                        stationResultVo.getMG());

                if(water!=null&&gas!=null){
                    //获取最大的环境风险等级
                    result=compareResult(water,gas);
                    //提升环境风险等级
                    result=promoteResult(result,stationResultVo.getDanger());
                    resultDescribe=result+"["+gas+"-大气("+
                            stationResultVo.getQG()+"-"+stationResultVo.getMG()+"-"+stationResultVo.getEG()+
                            ")+"+water+"-水("+
                            stationResultVo.getQW()+"-"+stationResultVo.getMW()+"-"+stationResultVo.getEW()+
                            ")]";
                    //保存大气环境
                    saveResult(voEnvironmentStation,gas,3L);
                    //保存水环境结果
                    saveResult(voEnvironmentStation,water,2L);
                }else{
                    if (stationResultVo.getQW()==null||"".equals(stationResultVo.getQW())){
                        resultDescribe+="Q水表,";
                    }
                    if (stationResultVo.getEW()==null||"".equals(stationResultVo.getEW())){
                        resultDescribe+="E水表,";
                    }
                    if (stationResultVo.getMW()==null||"".equals(stationResultVo.getMW())){
                        resultDescribe+="M水表";
                    }
                    if (stationResultVo.getQG()==null||"".equals(stationResultVo.getQG())){
                        resultDescribe+="Q气表,";
                    }
                    if (stationResultVo.getEG()==null||"".equals(stationResultVo.getEG())){
                        resultDescribe+="E气表,";
                    }
                    if (stationResultVo.getMG()==null||"".equals(stationResultVo.getMG())){
                        resultDescribe+="M气表,";
                    }
                    resultDescribe=resultDescribe.substring(0,resultDescribe.length()-1);
                    resultDescribe+="未提交";

                    ApiResult apiResult=ApiResult.FAIL();
                    apiResult.setMsg(resultDescribe);
                    return  apiResult;
                }
            } break;
            case "输气管线":;
            case "输气场站":{
                result="一般";
                //提升环境风险等级
                result=promoteResult(result,stationResultVo.getDanger());
                //最终描述
                resultDescribe=result+"[一般-大气（E0）+一般-水（E0)]";
            }break;
            case "含油水管线":;
            case "含硫化氢管线":{
                result=getResultByPC(stationResultVo.getP(),stationResultVo.getC());
                //提升环境风险等级
                if (result==null){
                    if (stationResultVo.getP()==null){
                        resultDescribe+="P表,";
                    }
                    if (stationResultVo.getC()==null){
                        resultDescribe+="C表,";
                    }
                    resultDescribe=resultDescribe.substring(0,resultDescribe.length()-1);
                    ApiResult apiResult=ApiResult.FAIL();
                    apiResult.setMsg(resultDescribe+"未提交");
                    return apiResult;
                }else {
                    result=promoteResult(result,stationResultVo.getDanger());
                    resultDescribe=result+"[P("+stationResultVo.getP()+")-C("+stationResultVo.getC()+")]";
                }

            }break;
            default:{
                result="";
                resultDescribe="";
            }
        }
        //执行到此处说明成功了,然后保存最终结果
        saveResult(voEnvironmentStation,result,1L);
        ApiResult apiResult=ApiResult.SUCCESS();
        apiResult.setData(resultDescribe);
        return apiResult;
    }

    /**
     * 先查结果，没有保存，有就修改
     */
    private void saveResult(VoEnvironmentStation voEnvironmentStation,String result,Long target){
        EnvironmentGrade environmentGrade=new EnvironmentGrade();
        //设置target
        environmentGrade.setTarget(target);
        //设置日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(voEnvironmentStation.getDatetime(), pos);
        environmentGrade.setGradetime(strtodate);
        //设置stationId
        String stationId=String.valueOf(voEnvironmentStation.getStationId());
        environmentGrade.setStationId(Integer.parseInt(stationId));
        environmentGrade.setResult(result);
        EnvironmentGrade temp=environmentGradeMapper.findByDate(environmentGrade);
        int i=0;
        if (temp==null){
            i=environmentGradeMapper.insertSelective(environmentGrade);
        }
        else {
            environmentGrade.setEnvironmentalGradeId(temp.getEnvironmentalGradeId());
            i= environmentGradeMapper.updateByPrimaryKeySelective(environmentGrade);
        }

    }
}
