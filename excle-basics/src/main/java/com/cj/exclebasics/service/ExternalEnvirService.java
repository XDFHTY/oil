package com.cj.exclebasics.service;

import com.cj.common.entity.ExternalEnvirImg;
import com.cj.exclebasics.domain.NexusImg;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ExternalEnvirService {


    /**
     *查询外环境关系图
     * @param map
     * @return
     */
    public List<ExternalEnvirImg> findNexusImg(Map map, HttpServletRequest request);

    /**
     *新增外环境关系图
     * @param nexusImg
     * @return
     */
    public int addNexusImg(NexusImg nexusImg, HttpServletRequest request);

    /**
     *编辑外环境关系图
     * @param nexusImg
     * @return
     */
    public int updateNexusImg(NexusImg nexusImg, HttpServletRequest request);
}
