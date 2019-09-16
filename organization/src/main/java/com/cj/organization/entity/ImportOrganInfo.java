package com.cj.organization.entity;

import com.cj.common.utils.excle.IsNeeded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 导入组织结构关系实体类
 * Created by XD on 2018/10/10.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportOrganInfo implements Serializable {
    //组织机构上级名称
    @IsNeeded
    private String superiorName;
    //组织机构名称
    @IsNeeded
    private String name;
}
