package com.cj.exclelog.service;

import com.cj.common.entity.LogCheck;
import com.cj.core.domain.Pager;

public interface LogsService {


    /**
     * 查询审核日志
     * @param pager
     * @return
     */
    public Pager findLog(Pager pager);

    /**
     * 新增审核日志
     * 返回数据 > 0,添加成功
     * @param logCheck
     * @return
     */
    public int addLog(LogCheck logCheck);
}
