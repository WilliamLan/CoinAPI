package org.coin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * code
	 */
	private String code;

	/**
	 * symbol
	 */
	private String symbol;

	/**
	 * rate
	 */
	private String rate;

	/**
	 * description
	 */
	private String description;

	/**
	 * rate_float
	 */
	private BigDecimal rate_float;

}
