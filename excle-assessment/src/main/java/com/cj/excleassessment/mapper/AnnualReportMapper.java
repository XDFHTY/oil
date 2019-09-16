package com.cj.excleassessment.mapper;

import com.cj.common.entity.AnnualReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AnnualReportMapper {
    int deleteByPrimaryKey(Long annualReportId);

    int insert(AnnualReport record);

    int insertSelective(AnnualReport record);

    AnnualReport selectByPrimaryKey(Long annualReportId);

    int updateByPrimaryKeySelective(AnnualReport record);

    int updateByPrimaryKey(AnnualReport record);
	
	//根据admin查询分公司id
    Long findBCIdByAdminId(Long adminId);

    //根据年份和分公司id查询 当前分公司报告份数
    Integer findBCReportNumberByYear(@Param("year") String year, @Param("bcId") Long bcId);

    //添加一条数据
    Integer addRecord(Map map);

    //根据adminId查询气矿id
    Long findFactoryIdByAdminId(Long adminId);

    //根据气矿id和年份查询报告份数
    Integer findFactoryNumberById(@Param("year")String year, @Param("factoryId")Long factoryId);

    //根据年份查询全部报告
    List<AnnualReport> getAllByYear(String year);

    //根据报告id查询报告
    AnnualReport findReportById(Long annualReportId);
}