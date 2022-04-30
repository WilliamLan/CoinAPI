package org.coin.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Time implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * updated
	 */
	private String updated;

	/**
	 * updatedISO
	 */
	private String updatedISO;

	/**
	 * updateduk
	 */
	private String updateduk;

}
