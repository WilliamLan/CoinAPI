package org.coin.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.coin.config.PropertyConfig;
import org.coin.entity.Currency;
import org.coin.enums.ReturnStatus;
import org.coin.exception.APIException;
import org.coin.repository.CurrencyRepository;
import org.coin.service.CurrencyService;
import org.coin.util.DateUtil;
import org.coin.util.HttpClientUtil;
import org.coin.util.JsonUtil;
import org.coin.vo.CoinReq;
import org.coin.vo.CoinResp;
import org.coin.vo.CurrentPrice;
import org.coin.vo.NewCoinResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private PropertyConfig config;

	@Autowired
	private CurrencyRepository currencyRep;

	private Map<String, String> tempCurrency = new HashMap<String, String>();

	@PostConstruct
	public void initData() {
		try {
			currencyRep.findAll().forEach(x -> {
				tempCurrency.put(x.getCode(), x.getName());
			});
		} catch (Exception e) {
			log.error("init data error!!", e);
		}
	}

	/**
	 * get all original currency price
	 * 
	 * @throws APIException
	 */
	@Override
	public CurrentPrice getAllOriginalCurrentPrice() throws APIException {
		CurrentPrice price = null;
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		try {
			String returnStr = HttpClientUtil.executeByHttpClientForJson(config.getPriceURL(), "", headers,
					HttpMethod.GET, true);
			price = JsonUtil.convertTFromJSON(returnStr, CurrentPrice.class);
			if (price == null) {
				throw new APIException(ReturnStatus.FAILURE.code, "get all new current price error");
			}
		} catch (APIException ex) {
			log.error("get all original current price error!!", ex);
			throw ex;
		} catch (Exception e) {
			log.error("get all original current price error!!", e);
			throw new APIException(ReturnStatus.ERROR_SERVER_INTERNAL.code, ReturnStatus.ERROR_SERVER_INTERNAL.msg);
		}
		return price;
	}

	/**
	 * get all new currency price
	 * 
	 * @throws APIException
	 */
	@Override
	public List<NewCoinResp> getAllNewCurrentPrice() throws APIException {
		List<NewCoinResp> priceList = new ArrayList<NewCoinResp>();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		try {
			String returnStr = HttpClientUtil.executeByHttpClientForJson(config.getPriceURL(), "", headers,
					HttpMethod.GET, true);
			CurrentPrice price = JsonUtil.convertTFromJSON(returnStr, CurrentPrice.class);
			if (price == null) {
				throw new APIException(ReturnStatus.FAILURE.code, "get all new current price error");
			}
			String updateTimeStr = DateUtil.convertZT2String(price.getTime().getUpdatedISO());
			// USD
			String usdCN = tempCurrency.get(price.getBpi().getUSD().getCode());
			usdCN = StringUtils.isNotBlank(usdCN) ? usdCN : "Non";
			priceList.add(new NewCoinResp(price.getBpi().getUSD().getCode(), usdCN,
					price.getBpi().getUSD().getRate_float(), updateTimeStr));
			// GBP
			String gbpCN = tempCurrency.get(price.getBpi().getGBP().getCode());
			gbpCN = StringUtils.isNotBlank(gbpCN) ? gbpCN : "Non";
			priceList.add(new NewCoinResp(price.getBpi().getGBP().getCode(), gbpCN,
					price.getBpi().getGBP().getRate_float(), updateTimeStr));
			// EUR
			String eurCN = tempCurrency.get(price.getBpi().getEUR().getCode());
			eurCN = StringUtils.isNotBlank(eurCN) ? eurCN : "Non";
			priceList.add(new NewCoinResp(price.getBpi().getEUR().getCode(), eurCN,
					price.getBpi().getEUR().getRate_float(), updateTimeStr));
		} catch (APIException ex) {
			log.error("get all new current price error!!", ex);
			throw ex;
		} catch (Exception e) {
			log.error("get all new current price error!!", e);
			throw new APIException(ReturnStatus.ERROR_SERVER_INTERNAL.code, ReturnStatus.ERROR_SERVER_INTERNAL.msg);
		}
		return priceList;
	}

	/**
	 * get all currency
	 */
	@Override
	public List<CoinResp> getAllCurrency() {
		List<CoinResp> coinList = new ArrayList<CoinResp>();
		currencyRep.findAll().stream().filter(x -> x != null).forEach(x -> {
			CoinResp coin = new CoinResp();
			coin.setCode(x.getCode());
			coin.setChineseName(x.getName());
			coin.setRate(x.getRate());
			coin.setSysTime(DateUtil.convertLT2String(LocalDateTime.now()));
			coinList.add(coin);
		});
		return coinList;
	}

	/**
	 * get currency
	 */
	@Override
	public CoinResp getCurrency(String code) throws APIException {
		CoinResp coin = new CoinResp();
		Currency currency = currencyRep.findByCode(code);
		if (currency == null) {
			throw new APIException(ReturnStatus.FAILURE.code, "no currency find error");
		}
		coin.setCode(currency.getCode());
		coin.setChineseName(currency.getName());
		coin.setRate(currency.getRate());
		coin.setSysTime(DateUtil.convertLT2String(LocalDateTime.now()));
		return coin;
	}

	/**
	 * create currency
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = APIException.class)
	public Boolean createCurrency(CoinReq req) throws APIException {
		Boolean isSuccess = Boolean.FALSE;
		try {
			Currency existedCY = currencyRep.findByCode(req.getCode());
			if (existedCY != null) {
				throw new APIException(ReturnStatus.INVALID_PARAMETER.code, "currency is existed");
			}
			Currency currency = new Currency();
			currency.setCode(req.getCode());
			currency.setName(req.getChineseName());
			currency.setRate(req.getRate());
			Currency savedCY = currencyRep.save(currency);
			if (savedCY != null) {
				isSuccess = Boolean.TRUE;
			}
		} catch (APIException ex) {
			log.error("create currency price error!!", ex);
			throw ex;
		} catch (Exception e) {
			log.error("create currency price error!!", e);
			throw new APIException(ReturnStatus.ERROR_SERVER_INTERNAL.code, ReturnStatus.ERROR_SERVER_INTERNAL.msg);
		}
		return isSuccess;
	}

	/**
	 * update currency
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = APIException.class)
	public Boolean updateCurrency(CoinReq req) throws APIException {
		Boolean isSuccess = Boolean.FALSE;
		try {
			Currency existedCY = currencyRep.findByCode(req.getCode());
			if (existedCY == null) {
				throw new APIException(ReturnStatus.INVALID_PARAMETER.code, "currency is not existed");
			}
			existedCY.setName(req.getChineseName());
			existedCY.setRate(req.getRate());
			Currency savedCY = currencyRep.save(existedCY);
			if (savedCY != null) {
				isSuccess = Boolean.TRUE;
			}
		} catch (APIException ex) {
			log.error("update currency price error!!", ex);
			throw ex;
		} catch (Exception e) {
			log.error("update currency price error!!", e);
			throw new APIException(ReturnStatus.ERROR_SERVER_INTERNAL.code, ReturnStatus.ERROR_SERVER_INTERNAL.msg);
		}
		return isSuccess;
	}

	/**
	 * delete currency
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = APIException.class)
	public Boolean deleteCurrency(String coin) throws APIException {
		Boolean isSuccess = Boolean.FALSE;
		try {
			Currency existedCY = currencyRep.findByCode(coin);
			if (existedCY == null) {
				throw new APIException(ReturnStatus.INVALID_PARAMETER.code, "currency is not existed");
			}
			currencyRep.delete(existedCY);
			isSuccess = Boolean.TRUE;
		} catch (APIException ex) {
			log.error("delete currency price error!!", ex);
			throw ex;
		} catch (Exception e) {
			log.error("delete currency price error!!", e);
			throw new APIException(ReturnStatus.ERROR_SERVER_INTERNAL.code, ReturnStatus.ERROR_SERVER_INTERNAL.msg);
		}
		return isSuccess;
	}

}
