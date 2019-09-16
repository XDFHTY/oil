package com.cj.exclepublic.domain;


import com.cj.common.entity.ExcleTableStructure;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UpdateTabStru 接口请求参数")
public class UpdateTabStruReq {

    @ApiModelProperty(name = "excleTableId",value = "Excle表列表ID",dataType = "long")
    private long excleTableId;




    @ApiModelProperty(name = "excleTableStructures",value = "excle表结构修改后信息集合(List<ExcleTableStructure>,包含有ID的被修改内容)",dataType = "List")
    private List<ExcleTableStructure> oldExcleTableStructures;

    @ApiModelProperty(name = "excleTableStructures",value = "excle表结构修改后信息集合(List<ExcleTableStructure>,包含没ID的新增内容)",dataType = "List")
    private List<ExcleTableStructure> newExcleTableStructures;
}
