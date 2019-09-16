package com.cj.common.pojo;

import com.cj.common.entity.TaskArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 作业区和气矿实体类
 * Created by XD on 2018/10/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskAreaAndStation extends TaskArea {
    /**
     * 场站集合
     */
    private List<RespStation> respStationList;
}
