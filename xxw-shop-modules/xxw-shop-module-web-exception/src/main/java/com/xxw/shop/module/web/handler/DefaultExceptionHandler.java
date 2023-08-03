package com.xxw.shop.module.web.handler;

import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.exception.ElasticsearchException;
import com.xxw.shop.module.common.exception.SystemException;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Order(999)
@Slf4j(topic = "exLog")
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ServerResponseEntity<Void> exceptionHandler(Exception ex) {
        return internalHandlerException(ex);
    }

    private ServerResponseEntity<Void> internalHandlerException(Exception ex) {
        if (ex instanceof BusinessException) {
            return handelException((BusinessException) ex);
        } else if (ex instanceof ElasticsearchException) {
            return handelException((ElasticsearchException) ex);
        } else if (ex instanceof SystemException) {
            return handelException((SystemException) ex);
        } else if (ex instanceof RestClientException) {
            return handelException((RestClientException) ex);
        } else if (ex instanceof NoHandlerFoundException) {
            return handelException((NoHandlerFoundException) ex);
        } else if (ex instanceof MissingServletRequestParameterException) {
            return handelException((MissingServletRequestParameterException) ex);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return handelException((HttpMediaTypeNotSupportedException) ex);
        } else if (ex instanceof HttpMessageNotReadableException) {
            return handelException((HttpMessageNotReadableException) ex);
        }
        return handelException(ex);
    }

    private ServerResponseEntity<Void> handelException(Exception ex) {
        log.error("Exception:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.UNKNOWN_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(BusinessException ex) {
        log.warn("BusinessException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.BUSINESS_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(ElasticsearchException ex) {
        log.warn("ElasticsearchException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.ELASTICSEARCH_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(SystemException ex) {
        log.warn("SystemException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.SYSTEM_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(RestClientException ex) {
        log.warn("RestClientException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.REST_CLIENT_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(NoHandlerFoundException ex) {
        log.warn("NoHandlerFoundException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.NO_HANDLER_FOUND_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(MissingServletRequestParameterException ex) {
        log.warn("MissingServletRequestParameterException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(HttpMediaTypeNotSupportedException ex) {
        log.warn("HttpMediaTypeNotSupportedException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION);
    }

    private ServerResponseEntity<Void> handelException(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException:{}", ExceptionUtils.getStackTrace(ex));
        return ServerResponseEntity.fail(SystemErrorEnumError.HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
    }
}
