package com.cj.exclegrade.pojo;

/**
 * 描述:environment_target表的实体类
 * @version
 * @author:  XD
 * @创建时间: 2018-10-22
 */
public class EnvironmentTarget {
    /**
     * 
     */
    private Long environmentTargetId;

    /**
     * 指标名字
     */
    private String targent;

    /**
     * 
     */
    private Long print;

    /**
     * 
     * @return environment_target_id 
     */
    public Long getEnvironmentTargetId() {
        return environmentTargetId;
    }

    /**
     * 
     * @param environmentTargetId 
     */
    public void setEnvironmentTargetId(Long environmentTargetId) {
        this.environmentTargetId = environmentTargetId;
    }

    /**
     * 指标名字
     * @return targent 指标名字
     */
    public String getTargent() {
        return targent;
    }

    /**
     * 指标名字
     * @param targent 指标名字
     */
    public void setTargent(String targent) {
        this.targent = targent == null ? null : targent.trim();
    }

    /**
     * 
     * @return print 
     */
    public Long getPrint() {
        return print;
    }

    /**
     * 
     * @param print 
     */
    public void setPrint(Long print) {
        this.print = print;
    }
}