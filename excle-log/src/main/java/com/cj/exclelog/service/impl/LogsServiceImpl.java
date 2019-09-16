package com.cj.exclelog.service.impl;

import com.cj.common.entity.LogCheck;
import com.cj.common.entity.Measures;
import com.cj.core.domain.Pager;
import com.cj.exclelog.mapper.LogCheckMapper;
import com.cj.exclelog.service.LogsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogsServiceImpl implements LogsService {

    @Resource
    private LogCheckMapper logCheckMapper;


    @Override
    public Pager findLog(Pager pager) {

        List<List<?>> lists = logCheckMapper.findLog(pager);
        //结果集处理
        List<Measures> list0 = (List<Measures>) lists.get(0);
        List<Map> list1 = (List<Map>) lists.get(1);

        Long total = (Long) (list1.get(0).get("total"));
        //总条数
        pager.setRecordTotal(total.intValue());
        //结果集
        pager.setContent(list0);

        return pager;
    }

    @Override
    public int addLog(LogCheck logCheck) {
        //根据操作员ID查询其姓名和用户名
        Map map = logCheckMapper.findAdminAndInfo(logCheck.getOperatorId());

        logCheck.setAdminName((String) map.get("adminName"));
        logCheck.setFullName((String) map.get("fullName"));
        logCheck.setOperatorTime(new Date());

        int i = logCheckMapper.insertSelective(logCheck);

        return i;
    }
}
