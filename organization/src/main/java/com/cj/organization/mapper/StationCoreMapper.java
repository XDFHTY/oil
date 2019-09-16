package com.cj.organization.mapper;

import com.cj.common.entity.StationCore;
import com.cj.organization.entity.RespStation;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

public interface StationCoreMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long stationCoreId);

    /**
     *
     * @mbggenerated
     */
    int insert(StationCore record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(StationCore record);

    /**
     *
     * @mbggenerated
     */
    StationCore selectByPrimaryKey(Long stationCoreId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StationCore record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StationCore record);

    //根据作业区id和中心站名称查重
    StationCore findStationCoreByName(@Param("name") String name,
                                     @Param("id") Long aLong);

    //添加中心站信息 主键返回
    void addStationCore(StationCore s);

    //修改中心站关联的 工艺分类id
    void updateFactoryTypeById(@Param("popId") Long popId, @Param("factoryTypeId")Long factoryTypeId);

    //根据中心站id  查询中心站和联系人信息
    RespStation findStationCoreAndAdmin(Long stationCoreId);
}