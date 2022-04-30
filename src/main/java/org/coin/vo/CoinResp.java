package org.coin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoinResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * code
	 */
	private String code;

	/**
	 * Chinese name
	 */
	private String chineseName;

	/**
	 * rate
	 */
	private BigDecimal rate;

	/**
	 * system time
	 */
	private String sysTime;

}
