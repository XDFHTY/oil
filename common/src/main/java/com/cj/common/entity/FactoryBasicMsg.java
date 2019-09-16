package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "厂、矿基本信息表")
public class FactoryBasicMsg  implements Serializable {
    /**
     * 厂、矿基本信息表
     */
    @ApiModelProperty(name = "factoryId",value = "id",dataType = "Long")
    private Long factoryId;

    /**
     * 分公司ID,1-西南油气田分公司
     */
    @ApiModelProperty(name = "branchCompanyId",value = "分公司ID,1-西南油气田分公司",dataType = "Long")
    private Long branchCompanyId;

    /**
     * 厂、矿名称
     */
    @ApiModelProperty(name = "factoryName",value = "厂、矿名称",dataType = "String")
    private String factoryName;

    /**
     * 辖区等级（账号-辖区关联表用）
     */
    @ApiModelProperty(name = "popedomGrade",value = "辖区等级（账号-辖区关联表用）",dataType = "String")
    private String popedomGrade;

    /**
     * 厂、矿基本信息表
     * @return factory_id 厂、矿基本信息表
     */
    public Long getFactoryId() {
        return factoryId;
    }

    /**
     * 厂、矿基本信息表
     * @param factoryId 厂、矿基本信息表
     */
    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * 分公司ID,1-西南油气田分公司
     * @return branch_company_id 分公司ID,1-西南油气田分公司
     */
    public Long getBranchCompanyId() {
        return branchCompanyId;
    }

    /**
     * 分公司ID,1-西南油气田分公司
     * @param branchCompanyId 分公司ID,1-西南油气田分公司
     */
    public void setBranchCompanyId(Long branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    /**
     * 厂、矿名称
     * @return factory_name 厂、矿名称
     */
    public String getFactoryName() {
        return factoryName;
    }

    /**
     * 厂、矿名称
     * @param factoryName 厂、矿名称
     */
    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName == null ? null : factoryName.trim();
    }

    /**
     * 辖区等级（账号-辖区关联表用）
     * @return popedom_grade 辖区等级（账号-辖区关联表用）
     */
    public String getPopedomGrade() {
        return popedomGrade;
    }

    /**
     * 辖区等级（账号-辖区关联表用）
     * @param popedomGrade 辖区等级（账号-辖区关联表用）
     */
    public void setPopedomGrade(String popedomGrade) {
        this.popedomGrade = popedomGrade == null ? null : popedomGrade.trim();
    }
}