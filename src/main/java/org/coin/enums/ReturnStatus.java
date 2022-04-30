package org.coin.enums;

public enum ReturnStatus {

	SUCCESS("0000", "success"), FAILURE("9000", "failure"), INVALID_PARAMETER("1000", "invalid parameter"),
	UNAUTHORIZED("1003", "no authorized"), ERROR_SERVER_INTERNAL("9999", "plz call admin"),
	FORBIDDEN("403", "forbidden");

	public final String code;
	public final String msg;

	ReturnStatus(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
