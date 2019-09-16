package com.cj.common.utils.vo;

import com.cj.common.domain.ExclePoint;
import lombok.Data;

@Data
public class ExcleData {
    //坐标
    private ExclePoint exclePoint;
    //内容
    private String cellMsg;

}
