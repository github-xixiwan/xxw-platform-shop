package com.xxw.shop.module.web.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(998)
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException ex) throws Exception {
        ServerResponseEntity<Object> fail = ServerResponseEntity.fail(SystemErrorEnumError.SHOW_FAIL);
        if (ex instanceof FlowException) {
            fail = ServerResponseEntity.fail(SystemErrorEnumError.SENTINEL_FLOW_EXCEPTION);
        } else if (ex instanceof DegradeException) {
            fail = ServerResponseEntity.fail(SystemErrorEnumError.SENTINEL_DEGRADE_EXCEPTION);
        } else if (ex instanceof ParamFlowException) {
            fail = ServerResponseEntity.fail(SystemErrorEnumError.SENTINEL_PARAM_FLOW_EXCEPTION);
        } else if (ex instanceof SystemBlockException) {
            fail = ServerResponseEntity.fail(SystemErrorEnumError.SENTINEL_SYSTEM_BLOCK_EXCEPTION);
        } else if (ex instanceof AuthorityException) {
            fail = ServerResponseEntity.fail(SystemErrorEnumError.SENTINEL_AUTHORITY_EXCEPTION);
        }
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), fail);
    }
}
