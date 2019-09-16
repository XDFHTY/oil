package com.cj.exclepublic.read;

import com.cj.core.domain.ApiResult;
import org.springframework.web.multipart.MultipartFile;

public interface ReadService {

    /**
     * 读取excle表结构
     * @param multipartFile
     * @return
     */
    public ApiResult readExcle(MultipartFile multipartFile, Boolean b) throws Exception;
}
