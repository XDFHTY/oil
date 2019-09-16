package com.cj.organization.mapper;

import com.cj.common.entity.Station;
import com.cj.core.domain.Pager;
import com.cj.organization.entity.RespStation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long stationId);

    /**
     *
     * @mbggenerated
     */
    int insert(Station record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(Station record);

    /**
     *
     * @mbggenerated
     */
    Station selectByPrimaryKey(Long stationId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Station record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Station record);

    //根据作业区id和场站名称查重
    Station findStationByName(@Param("name") String name, @Param("id") Long aLong);

    //添加场站信息
    void addStation(Station s);

    //修改场站关联的 工艺分类id
    void updateFactoryTypeById(@Param("popId") Long popId, @Param("factoryTypeId")Long factoryTypeId);

    //条件查询场站级联系人
    List<RespStation> findStationAndAdmin(Pager p);

    //计数
    int findStationAndAdminCount(Pager p);

    //根据场站id 查询场站和联系人信息
    RespStation findStationAndAdmin1(Long stationId);

    //查询该作业区下所有场站
    List<Station> findStationByAreaId(Integer areaId);

    //修改场站名称
    void updateStationNameById(@Param("stationId") Integer stationId, @Param("name") String name);
}