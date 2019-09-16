package com.cj.exclecheck.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 提交信息详情 返回实体类
 * Created by XD on 2018/10/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespTableInfo implements Serializable {
    /**
     * excle表格id
     */
    private Long excleTableId;

    /**
     * exlce表格名称
     */
    private String excleTableName;

    /**
     * 别名 前端跳转用
     */
    private String byName;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后修改时间
     */
    private String updateTime;

    /**
     * 表格种类
     */
    private String type;

    private String factoryName;

    private String taskAreaName;

    private String stationName;

    private String factoryTypeName;

    private String year;
}
