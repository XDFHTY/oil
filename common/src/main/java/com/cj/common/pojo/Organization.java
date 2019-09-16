package com.cj.common.pojo;

import com.cj.common.entity.TaskArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 组织机构 关系实体类
 * Created by XD on 2018/10/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    /**
     * 厂、矿id
     */
    private Long factoryId;
    /**
     * 厂、矿名称
     */
    private String factoryName;
    /**
     * 作业区和场站集合
     */
    private List<TaskAreaAndStation> taskAreaAndStationList;
}
