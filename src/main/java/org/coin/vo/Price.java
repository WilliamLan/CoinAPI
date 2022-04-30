package org.coin.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * USD
	 */
	@JsonProperty("USD")
	private Currency USD;

	/**
	 * GBP
	 */
	@JsonProperty("GBP")
	private Currency GBP;

	/**
	 * EUR
	 */
	@JsonProperty("EUR")
	private Currency EUR;

}
