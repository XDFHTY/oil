package com.cj.exclebasics.mapper;

import com.cj.common.entity.ExternalEnvirImg;

import java.util.List;
import java.util.Map;

public interface ExternalEnvirImgMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long externalEnvirImgId);

    /**
     *
     * @mbggenerated
     */
    int insert(ExternalEnvirImg record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(ExternalEnvirImg record);

    /**
     *
     * @mbggenerated
     */
    ExternalEnvirImg selectByPrimaryKey(Long externalEnvirImgId);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ExternalEnvirImg record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ExternalEnvirImg record);


    /**
     *查询外环境关系图
     * @param map
     * @return
     */
    public List<ExternalEnvirImg> findNexusImg(Map map);

    /**
     *新增外环境关系图
     * @param externalEnvirImgs
     * @return
     */
    public int addNexusImg(List<ExternalEnvirImg> externalEnvirImgs);

    /**
     *编辑外环境关系图
     * @param externalEnvirImgs
     * @return
     */
    public int updateNexusImg(List<ExternalEnvirImg> externalEnvirImgs);
}