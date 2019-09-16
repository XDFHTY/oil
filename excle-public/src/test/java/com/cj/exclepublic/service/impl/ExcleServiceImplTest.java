package com.cj.exclepublic.service.impl;

import com.cj.exclepublic.service.ExcleService;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@MapperScan({"com.cj.*.mapper"})
@ComponentScan(basePackages = {"com.cj"})
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExcleServiceImplTest {

    @Autowired
    private ExcleService excleService;

    /*@Test
    public void findExcleStruByStIdAndTabName() {

        ApiResult e = excleService.findExcleStruByStIdAndTabName((long) 1, "基础信息","2014");

        System.out.println(e);
        ExcleTable excleTable = excleService.findByStationIdAndTabName((long) 1, "基础信息");
        System.out.println(excleTable);
        List<ExcleTableStructure> v1 = excleService.findExcleStruByExcleTabId((long) 1);
        System.out.println(v1);
        TableRecord v2 = excleService.findTableRecordByExcleTabIdAndYear((long) 1, "2018");
        System.out.println(v2);
        List<TableCellRecord> v3List = excleService.findTabCellRecordListByTabRecordId((long) 1);
        System.out.println(v3List);
        TableCellRecord v4 = excleService.findTabCellRecordByTabRecordIdAndStruId((long) 1, (long) 65);
        System.out.println(v4);
    }*/
}