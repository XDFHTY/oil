package com.cj.analysis.server.impl;

import com.cj.analysis.mapper.EnvironmentGradeChartMapper;
import com.cj.analysis.server.EnvironmentGradeChartServer;
import com.cj.analysis.vo.VoChart;
import com.cj.core.domain.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("environmentGradeChart")
public class EnvironmentGradeChartServerImpl implements EnvironmentGradeChartServer{
    /**
     * 添加环境结果信息
     */
    @Autowired
    EnvironmentGradeChartMapper environmentGradeChartMapper;

    @Override
    public ApiResult findTaskAreaChart(VoChart param) {
        ApiResult apiResult=null;
        try {
            Map map= environmentGradeChartMapper.findTaskAreaChart(param);
            List list=new ArrayList();
            Map map1=new HashMap();
            int larger =Integer.parseInt(map.get("larger").toString());
            int general=Integer.parseInt(map.get("general").toString());
            int major=Integer.parseInt(map.get("major").toString());
            int total=larger+general+major;
            DecimalFormat df  = new DecimalFormat("###.00");
            if (total!=0){
                map1.put("name","较大("+df.format((larger*1.0)/total*100)+"%)");
                map1.put("value",larger);
                list.add(map1);
                map1=new HashMap();
                map1.put("name","一般("+df.format((general*1.0)/total*100)+"%)");
                map1.put("value",general);
                list.add(map1);
                map1=new HashMap();
                map1.put("name","重大("+df.format((major*1.0)/total*100)+"%)");
                map1.put("value",major);
                list.add(map1);
                apiResult=ApiResult.SUCCESS();
                apiResult.setData(list);
            }else {
                apiResult=ApiResult.SUCCESS();
                apiResult.setMsg("暂无数据");
            }
        }catch (Exception e){
            System.out.println(e);
            apiResult=ApiResult.FAIL();
        }
        return apiResult;
    }
}
