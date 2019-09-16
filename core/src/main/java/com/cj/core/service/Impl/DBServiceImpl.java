package com.cj.core.service.Impl;

import com.alibaba.druid.sql.SQLUtils;
import com.cj.core.domain.Datapram;
import com.cj.core.service.DBService;
import com.cj.core.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DBServiceImpl implements DBService {

    @Autowired
    private Datapram datapram;
    @Override
    public void backup() {
        String string = datapram.getTooldir()+" -h "+datapram.getIp()+" -u"+datapram.getUsername()+" -p"+datapram.getPassword()+" "+datapram.getDbname();

        SqlUtil.backup(string,datapram.getDir());
    }
}
