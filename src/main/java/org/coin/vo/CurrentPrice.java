package org.coin.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * time
	 */
	private Time time;

	/**
	 * disclaimer
	 */
	private String disclaimer;

	/**
	 * chartName
	 */
	private String chartName;

	/**
	 * Currency
	 */
	private Price bpi;

}
