package com.cj.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "管理员与场站-作业区-气矿-分公司的管辖关系")
public class AuthAdminPopedom  implements Serializable {
    /**
     * 管理员与场站-作业区-气矿-分公司的管辖关系
     */
    @ApiModelProperty(name = "authAdminPopedom",value = "管理员与场站-作业区-气矿-分公司的管辖关系",dataType = "Long")
    private Long authAdminPopedom;

    /**
     * 管理员ID
     */
    @ApiModelProperty(name = "adminId",value = "管理员ID",dataType = "Long")
    private Long adminId;

    /**
     * 被管辖的区域ID
     */
    @ApiModelProperty(name = "popedomId",value = "被管辖的区域ID",dataType = "Long")
    private Long popedomId;

    /**
     * 管辖区域等级，1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线
     */
    @ApiModelProperty(name = "popedomGrade",value = "管辖区域等级，1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线",dataType = "String")
    private String popedomGrade;

    /**
     * 管理员与场站-作业区-气矿-分公司的管辖关系
     * @return auth_admin_popedom 管理员与场站-作业区-气矿-分公司的管辖关系
     */
    public Long getAuthAdminPopedom() {
        return authAdminPopedom;
    }

    /**
     * 管理员与场站-作业区-气矿-分公司的管辖关系
     * @param authAdminPopedom 管理员与场站-作业区-气矿-分公司的管辖关系
     */
    public void setAuthAdminPopedom(Long authAdminPopedom) {
        this.authAdminPopedom = authAdminPopedom;
    }

    /**
     * 管理员ID
     * @return admin_id 管理员ID
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * 管理员ID
     * @param adminId 管理员ID
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * 被管辖的区域ID
     * @return popedom_id 被管辖的区域ID
     */
    public Long getPopedomId() {
        return popedomId;
    }

    /**
     * 被管辖的区域ID
     * @param popedomId 被管辖的区域ID
     */
    public void setPopedomId(Long popedomId) {
        this.popedomId = popedomId;
    }

    /**
     * 管辖区域等级，1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线
     * @return popedom_grade 管辖区域等级，1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线
     */
    public String getPopedomGrade() {
        return popedomGrade;
    }

    /**
     * 管辖区域等级，1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线
     * @param popedomGrade 管辖区域等级，1-分公司，2-厂、矿，3-作业区，4-中心站，5-场站/管线
     */
    public void setPopedomGrade(String popedomGrade) {
        this.popedomGrade = popedomGrade == null ? null : popedomGrade.trim();
    }
}