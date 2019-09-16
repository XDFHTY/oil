package com.cj.exclegrade.server;

import com.cj.core.domain.ApiResult;
import com.cj.exclegrade.vo.EnvironmentGradeVo;
import com.cj.exclegrade.vo.VoEnvironment;
import com.cj.exclegrade.vo.VoEnvironmentStation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface EnvironmentGradeService {


    ApiResult addEnvironmentGrade(EnvironmentGradeVo environmentGrade,HttpServletRequest request);

    ApiResult updateEnvironmentGrade(EnvironmentGradeVo environmentGradeVo,HttpServletRequest request);

    ApiResult findEnvironmentGrade(VoEnvironmentStation query,HttpSession httpSession);

    ApiResult findFactoryEnvironmentGrade(VoEnvironment query);

    ApiResult getStationResult(VoEnvironmentStation voEnvironmentStation,HttpSession session);
}
