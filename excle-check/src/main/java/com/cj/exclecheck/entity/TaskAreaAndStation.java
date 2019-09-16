package com.cj.exclecheck.entity;

import com.cj.common.entity.Station;
import com.cj.common.entity.TaskArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 作业区 和 场站 实体类
 * Created by XD on 2018/10/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskAreaAndStation implements Serializable {
    //作业区id
    private Long taskAreaId;
    //作业区名称
    private String taskAreaName;
    //场站集合
    private List<Station> stationList;
}
