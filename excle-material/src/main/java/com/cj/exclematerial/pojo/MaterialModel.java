package com.cj.exclematerial.pojo;

import com.cj.common.entity.Material;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author： 刘磊
 * @Description: ${description}
 * @date： 2019/7/10 9:29
 **/
@ApiModel(value = "新增物资实体类")
@Data
public class MaterialModel extends Material {

    /**
     * 所属矿，厂ID
     */
    @ApiModelProperty(name = "factoryId",value = "所属矿，厂ID",dataType = "Long")
    private Long factoryId;
}
