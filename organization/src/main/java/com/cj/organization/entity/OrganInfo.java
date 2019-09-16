package com.cj.organization.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by XD on 2018/11/14.
 */
@Data
public class OrganInfo {

    private Long oId;

    private String oName;
    
    private List<AdminAndInfo> adminAndInfos;

    private List<OrganInfo> organInfos;
}
