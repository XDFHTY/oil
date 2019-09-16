package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "分公司信息表")
public class BranchCompany  implements Serializable {
    /**
     * 分公司信息表
     */
    @ApiModelProperty(name = "branchCompanyId",value = "id",dataType = "Long")
    private Long branchCompanyId;

    /**
     * 分公司名称
     */
    @ApiModelProperty(name = "branchCompanyName",value = "分公司名称",dataType = "String")
    private String branchCompanyName;

    /**
     * 辖区等级（账号-辖区关联表用）
     */
    @ApiModelProperty(name = "popedomGrade",value = "辖区等级（账号-辖区关联表用）",dataType = "String")
    private String popedomGrade;

    /**
     * 分公司信息表
     * @return branch_company_id 分公司信息表
     */
    public Long getBranchCompanyId() {
        return branchCompanyId;
    }

    /**
     * 分公司信息表
     * @param branchCompanyId 分公司信息表
     */
    public void setBranchCompanyId(Long branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    /**
     * 分公司名称
     * @return branch_company_name 分公司名称
     */
    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    /**
     * 分公司名称
     * @param branchCompanyName 分公司名称
     */
    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName == null ? null : branchCompanyName.trim();
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