package org.coin.service;

import java.util.List;

import org.coin.exception.APIException;
import org.coin.vo.CoinReq;
import org.coin.vo.CoinResp;
import org.coin.vo.CurrentPrice;
import org.coin.vo.NewCoinResp;

public interface CurrencyService {

	public CurrentPrice getAllOriginalCurrentPrice() throws APIException;

	public List<NewCoinResp> getAllNewCurrentPrice() throws APIException;

	public List<CoinResp> getAllCurrency();

	public CoinResp getCurrency(String code) throws APIException;

	public Boolean createCurrency(CoinReq req) throws APIException;

	public Boolean updateCurrency(CoinReq req) throws APIException;

	public Boolean deleteCurrency(String code) throws APIException;

}
