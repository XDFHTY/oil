package com.cj.analysis.server;

import com.cj.analysis.vo.VoLedger;
import com.cj.core.domain.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EnvironmentLeaderServer {
    ApiResult getEnvironmentLeader(VoLedger query, HttpServletRequest request);

    ApiResult getExcelEnvironmentLeader(VoLedger query, HttpServletResponse response, HttpServletRequest request);
}
