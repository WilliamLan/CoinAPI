package org.coin.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "currency")
@Getter
@Setter
public class Currency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * code
	 */
	@Column(name = "code")
	private String code;

	/**
	 * name
	 */
	@Column(name = "name")
	private String name;

	/**
	 * rate
	 */
	@Column(name = "rate", precision = 10, scale = 4)
	private BigDecimal rate;

}
