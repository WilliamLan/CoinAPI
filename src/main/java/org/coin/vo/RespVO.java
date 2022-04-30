package org.coin.vo;

import java.io.Serializable;

public class RespVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RespVO() {
		super();
	}

	/**
	 * status code
	 */
	private String code;

	/**
	 * status msg
	 */
	private String msg;

	/**
	 * return data
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

}
