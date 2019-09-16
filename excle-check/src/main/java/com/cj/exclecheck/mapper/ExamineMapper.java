package com.cj.exclecheck.mapper;

import com.cj.common.entity.LogCheck;
import com.cj.common.entity.Station;
import com.cj.common.entity.TableRecord;
import com.cj.exclecheck.entity.RespExamineResult;
import com.cj.exclecheck.entity.RespTableInfo;
import com.cj.exclecheck.entity.RespUnaudited;
import com.cj.exclecheck.entity.TaskAreaAndStation;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by XD on 2018/10/23.
 */
public interface ExamineMapper {

    //根据场站id 查询工艺分类名称
    String findFactoryTypeName(Integer stationId);

    //根据场站id和年份 查询基础信息表
    TableRecord findTableRecord(@Param("year") String year, @Param("stationId") Integer stationId);

    //根据场站id和年份查询已经填过的表
    List<String> findTableNameList( @Param("stationId")Integer stationId, @Param("year")String year);

    //根据年份和场站id  修改审核状态 为 2
    int updateTableRecordCheckType(@Param("stationId")Integer stationId, @Param("year")String year);

    //查询所管辖的作业区下所有的场站id和场站名称
    List<Station> findStationList(Long adminId);

    //根据场站id和年份查询已经填过的表 不筛选type
    List<String> findTableNameListNotType(@Param("stationId")Integer stationId, @Param("year")String year);

    //根据年份和场站id  修改审核状态
    void updateTableRecordCheckByNum(@Param("stationId")Integer stationId,
                                     @Param("year")String year,
                                     @Param("num") String num);

    //查询所管辖下所有的作业区id和名称 及 下属场站id和场站名称
    List<TaskAreaAndStation> findTaskAreaAndStation(Long adminId);

    //模糊查询 是否提交台账或C值
    List<String> findTableName(@Param("stationId")Integer stationId, @Param("year")String year, @Param("name")String s);

    //环境风险是否填写 今年和上年
    Integer findEnvironmental(@Param("stationId")Integer stationId, @Param("year")String year, @Param("type")String s);

    //根据年份和场站id  修改审核状态 为 4
    int updateTableRecordCheckTypeSex(@Param("stationId")Integer id,  @Param("year")String year);

    //修改审核状态
    int updateExamineCheckType(@Param("stationId") Integer stationId,
                               @Param("year")String year,
                               @Param("s1")String s1,
                               @Param("s2")String s2,
                               @Param("s3")String s3);

    //风险防控上年度表 修改审核状态 为 6
    int updateLasetYearTable(@Param("stationId") Integer id, @Param("year")String year);

    //查询作业区下所有场站
    List<Station> findStationListByAreaId(Integer taskAreaId);

    //风险防控上年度表 修改审核状态 为 8
    int updateExamineLasetYearTable(@Param("stationId") int i,
                                    @Param("lastYear") int lastYear,
                                    @Param("s1")String s1,@Param("s2")String s2,@Param("s3")String s3);

    //驳回 这个场站下  场站级填的表 修改状态 从 2或者7变成3
    int updateCheckTypeByStation(@Param("stationId")Integer stationId, @Param("year")String year, @Param("s1")String s1,@Param("s2")String s2,@Param("s3")String s3);

    //根据场站id 查询作业区id 和 气矿id
    Map findTaskAreaAndFactoryId(Integer stationId);

    //根据作业区id 查询 气矿id
    Map findFactoryId(Integer taskAreaId);

    //根据adminId查询管理的作业区id
    Integer findTaskAreaIdByAdminId(Long adminId);

    //查询该账号所管理的场站id
    List<Long> findStationIdList(Long adminId);

    //根据场站id 查询 审批结果
    RespExamineResult findExamineResultById(@Param("stationId") Long stationId, @Param("year") String year);

    //查询提交时间
    Date findSubmitDate(@Param("stationId") Long stationId, @Param("year") String year);

    //查询该账号所管理的作业区id  只有一个
    Long findTaskAreaId(Long adminId);

    //根据作业区id  查询审批结果
    RespExamineResult findExamineResultByTaskAreaId(@Param("taskAreaId") Long taskAreaId, @Param("year") String year,@Param("s1")String s);

    //查询提交时间
    Date findSubmitDateByArea(@Param("taskAreaId") Long taskAreaId, @Param("year") String year,@Param("s1")String s1);

    //根据场站id 查询这个场站有没有 已提交待审核的表(状态为2)
    int findTableByStationId(@Param("stationId") Long stationId,
                             @Param("year")String year,
                             @Param("type")String s);


    //作业区级和气矿级  可以看这个场站下所有的表  包括 作业区填写的表
    List<RespTableInfo> findTableInfoByArea(@Param("stationId") Long stationId, @Param("year") String year);

    //场站级  只能看 由场站填写 表
    List<RespTableInfo> findTableInfoByStation(@Param("stationId") Long stationId, @Param("year") String year);

    //查询上年度表
    RespTableInfo findLastYearTable(@Param("stationId") Long stationId,  @Param("year") int i);

    //查询场站其余信息
    Map findStationById(Long stationId);

    //根据场站id 查询这个场站有没有 7-厂矿已审核未通过被驳回
    int findTableByStationIdByS(@Param("stationId")Long stationId, @Param("year")String year, @Param("type")String s);

    //把上年度表  7 变 6
    int updateLasetYearTableByS(@Param("stationId")Integer stationId, @Param("year")int i, @Param("s1") String s, @Param("s2") String s1, @Param("s3") String s2);

    //根据年份和场站id  修改审核状态 为 5 -> 6
    int updateTableRecordCheckTypeSexB(@Param("stationId")Integer id,  @Param("year")String year);

    //风险防控上年度表 修改审核状态 为 5 -> 6
    int updateLasetYearTableB(@Param("stationId") Integer id, @Param("year")String year);

    //修改审核状态
    int updateTableRecordCheckTypeSexQA(@Param("stationId") Long id, @Param("year") String year, @Param("s1") String s, @Param("s2") String s1, @Param("s3") String s2);


    //风险防控上年度表 修改审核状态
    int updateLasetYearTableQA(@Param("stationId") Long id, @Param("year") String year, @Param("s1") String s, @Param("s2") String s1, @Param("s3") String s2);

    //查询带有场站id 且 是 作业区级A的审核日志
    RespExamineResult findLogByCA(@Param("stationId") Long id, @Param("year") String year, @Param("s1") String s);

    //查询是否有自己的日志 并且 日志内容是 已提交
    RespExamineResult findLogByC(Long stationId, String year, String 场站级);


    RespExamineResult findLogByZA(@Param("taskAreaId") Long taskAreaId,@Param("year") String year, @Param("s1") String s);

    //查询该账号所管理的气矿id  只有一个
    Long findFactoryAdminId(Long adminId);

    RespExamineResult findLogByQA(@Param("factoryId") Long factoryId, @Param("year") String year, @Param("s1") String s);

    Date findSubmitDateByFactory(@Param("factoryId") Long factoryId, @Param("year") String year, @Param("s1") String s);

    //根据气矿id查询审核结果
    RespExamineResult findExamineResultByFactoryId(@Param("factoryId") Long factoryId, @Param("year") String year,@Param("s1")String s);

    //查询该气矿下 所有的 场站id
    List<Station> findStationListByFactoryId(Long factoryId);

    //根据adminId 查询所管理的所有作业区Id集合
    List<Long> findTaskAreaIdListByAdminId(Long adminId);

    //查询作业区下所管理的场站id集合
    List<Long> findStationIdListByAdminId(Long adminId);
}
