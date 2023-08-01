package com.xxw.shop.module.web.response;

import com.xxw.shop.module.util.exception.ErrorEnumInterface;
import com.xxw.shop.module.web.constant.SystemErrorEnumError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

public class ServerResponseEntity<T> implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ServerResponseEntity.class);

    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return Objects.equals(SystemErrorEnumError.OK.getCode(), this.code);
    }

    @Override
    public String toString() {
        return "ServerResponseEntity{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }

    public static <T> ServerResponseEntity<T> success(T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(SystemErrorEnumError.OK.getCode());
        serverResponseEntity.setMsg(SystemErrorEnumError.OK.getMsg());
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success() {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(SystemErrorEnumError.OK.getCode());
        serverResponseEntity.setMsg(SystemErrorEnumError.OK.getMsg());
        return serverResponseEntity;
    }

    /**
     * 前端显示失败消息
     *
     * @param msg 失败消息
     * @return
     */
    public static <T> ServerResponseEntity<T> showFailMsg(String msg) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(SystemErrorEnumError.SHOW_FAIL.getCode());
        serverResponseEntity.setMsg(msg);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ErrorEnumInterface errorEnum) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(errorEnum.getCode());
        serverResponseEntity.setMsg(errorEnum.getMsg());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ErrorEnumInterface errorEnum, T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(errorEnum.getCode());
        serverResponseEntity.setMsg(errorEnum.getMsg());
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> transform(ServerResponseEntity<?> oldServerResponseEntity) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(oldServerResponseEntity.getCode());
        serverResponseEntity.setMsg(oldServerResponseEntity.getMsg());
        return serverResponseEntity;
    }

}
