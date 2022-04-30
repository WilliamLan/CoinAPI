package org.coin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewCoinResp implements Serializable {

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
	 * update time
	 */
	private String updateTime;

	public NewCoinResp(String code, String chineseName, BigDecimal rate, String updateTime) {
		super();
		this.code = code;
		this.chineseName = chineseName;
		this.rate = rate;
		this.updateTime = updateTime;
	}

}
