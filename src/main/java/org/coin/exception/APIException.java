package org.coin.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class APIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String msg;

	public APIException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
