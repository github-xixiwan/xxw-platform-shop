package com.xxw.shop.module.web.handler;

import cn.hutool.core.util.StrUtil;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.exception.ElasticsearchException;
import com.xxw.shop.module.common.exception.SystemException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@Order(999)
@Slf4j(topic = "exLog")
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerResponseEntity<Object>> exceptionHandler(Exception ex) {
        return internalHandlerException(ex);
    }

    private ResponseEntity<ServerResponseEntity<Object>> internalHandlerException(Exception ex) {
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
        } else if (ex instanceof MethodArgumentNotValidException) {
            return handelException((MethodArgumentNotValidException) ex, null);
        } else if (ex instanceof BindException) {
            return handelException(null, (BindException) ex);
        }
        return handelException(ex);
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(Exception ex) {
        log.error("Exception:{}", ExceptionUtils.getStackTrace(ex));
        if (StrUtil.isNotBlank(RootContext.getXID())) {
            try {
                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
            } catch (TransactionException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.EXCEPTION));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(BusinessException ex) {
        log.warn("BusinessException:{}", ExceptionUtils.getStackTrace(ex));
        // 失败返回消息 状态码固定为直接显示消息的状态码
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(ex.getMessage()));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(ElasticsearchException ex) {
        log.warn("ElasticsearchException:{}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(ex.getMessage()));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(SystemException ex) {
        log.warn("SystemException:{}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.EXCEPTION));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(RestClientException ex) {
        log.warn("RestClientException:{}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.EXCEPTION));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(NoHandlerFoundException ex) {
        log.warn("NoHandlerFoundException:{}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.EXCEPTION));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(MissingServletRequestParameterException ex) {
        log.warn("MissingServletRequestParameterException:{}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.EXCEPTION));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(HttpMediaTypeNotSupportedException ex) {
        log.warn("HttpMediaTypeNotSupportedException:{}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.EXCEPTION));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException:{}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.HTTP_MESSAGE_NOT_READABLE));
    }

    private ResponseEntity<ServerResponseEntity<Object>> handelException(MethodArgumentNotValidException ex1,
                                                                         BindException ex2) {
        List<FieldError> fieldErrors = null;
        if (ex1 != null) {
            log.warn("MethodArgumentNotValidException:{}", ExceptionUtils.getStackTrace(ex1));
            fieldErrors = ((MethodArgumentNotValidException) ex1).getBindingResult().getFieldErrors();
        }
        if (ex2 != null) {
            log.warn("BindException:{}", ExceptionUtils.getStackTrace(ex2));
            fieldErrors = ((BindException) ex2).getBindingResult().getFieldErrors();
        }
        if (fieldErrors == null) {
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.METHOD_ARGUMENT_NOT_VALID));
        }

        List<String> defaultMessages = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            defaultMessages.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(SystemErrorEnumError.METHOD_ARGUMENT_NOT_VALID, defaultMessages));
    }
}
