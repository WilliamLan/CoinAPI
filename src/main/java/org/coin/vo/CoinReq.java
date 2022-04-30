package org.coin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoinReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * code
	 */
	@NotBlank
	@NotNull
	@Valid
	private String code;

	/**
	 * Chinese name
	 */
	@NotBlank
	@NotNull
	@Valid
	private String chineseName;

	/**
	 * rate
	 */
	@DecimalMin(value = "0")
	private BigDecimal rate;

}
