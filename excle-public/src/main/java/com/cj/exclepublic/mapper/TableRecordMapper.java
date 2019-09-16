package com.cj.exclepublic.mapper;

import com.cj.common.entity.TableRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.Map;

public interface TableRecordMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long tableRecordId);

    /**
     *
     * @mbggenerated
     */
    int insert(TableRecord record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(TableRecord record);

    /**
     *
     * @mbggenerated
     */
    TableRecord selectByPrimaryKey(Long tableRecordId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableRecord record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableRecord record);

    /**
     * 根据excle_tablle_id和年份查询TableRecord
     * @param excleTabId
     * @param time
     * @return
     */
    TableRecord selectByExcleTabIdAndTime(@Param("excleTabId") Long excleTabId, @Param("time") String time);

    /**
     * 查询该场站、该表、该年、该分类的数据
     * @param map
     * @return
     */
    public TableRecord findTableRecord(Map map);

}