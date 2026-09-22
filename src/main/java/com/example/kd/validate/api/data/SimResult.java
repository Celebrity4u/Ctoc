/**
 * Copyright 2014-现在 国防科技大学
 */
package com.example.kd.validate.api.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 接口返回对象实体
 * 
 * @author zl
 * @param <T>
 */
public class SimResult<T extends Serializable> implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(SimResult.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */

	private Integer code = ResultEnum.ERROR.getCode();

	/**
	 * 错误信息
	 */
	private String msg = null;

	/**
	 * 返回结果实体
	 */
	private T data = null;

	public SimResult() {
	}

	private SimResult(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static <T extends Serializable> SimResult<T> error(String msg) {
		logger.debug("返回错误：code={}, msg={}", ResultEnum.ERROR.getCode(), msg);
		return new SimResult<T>(ResultEnum.ERROR.getCode(), msg, null);
	}
	public static <T extends Serializable> SimResult<T> error(String msg, Integer code) {
		logger.debug("返回错误：code={}, msg={}", code, msg);
		return new SimResult<T>(code, msg, null);
	}
	public static <T extends Serializable> SimResult<T> errorApplication(String msg) {
		logger.debug("返回错误：code={}, msg={}", ResultEnum.ERROR_Application.getCode(), msg);
		return new SimResult<T>(ResultEnum.ERROR_Application.getCode(), msg, null);
	}
	public static <T extends Serializable> SimResult<T> errorApplicationAndData(String msg, T data) {
		logger.debug("返回错误：code={}, msg={}", ResultEnum.ERROR_Application.getCode(), msg);
		return new SimResult<T>(ResultEnum.ERROR_Application.getCode(), msg, data);
	}
	public static <T extends Serializable> SimResult<T> errorConfclick(String msg, T data) {
		logger.debug("返回错误：code={}, msg={}", ResultEnum.ERROR_CONFLICK.getCode(), msg);
		return new SimResult<T>(ResultEnum.ERROR_CONFLICK.getCode(), msg, data);
	}
	public static <T extends Serializable> SimResult<T> errorCreateRoom(String msg, T data) {
		logger.debug("返回错误：code={}, msg={}", ResultEnum.CREATE_ROOM.getCode(), msg);
		return new SimResult<T>(ResultEnum.CREATE_ROOM.getCode(), msg, data);
	}
	public static <T extends Serializable> SimResult<T> errorScenerioID(String msg) {
		logger.debug("返回错误：code={}, msg={}", ResultEnum.ERROR_ScenerioID.getCode(), msg);
		return new SimResult<T>(ResultEnum.ERROR_ScenerioID.getCode(), msg, null);
	}
	public static <T extends Serializable> SimResult<T> error(ResultEnum resultEnum) {
		logger.debug("返回错误：code={}, msg={}", resultEnum.getCode(), resultEnum.getDesc());
		return new SimResult<T>(resultEnum.getCode(), resultEnum.getDesc(), null);
	}

	public static <T extends Serializable> SimResult<T> error(int code, String msg) {
		logger.debug("返回错误：code={}, msg={}", code, msg);
		return new SimResult<T>(code, msg, null);
	}

	public static <T extends Serializable> SimResult<T> success(T data) {
		return new SimResult<T>(ResultEnum.SUCCESS.getCode(), "", data);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

}
