package org.coin.controller;

import java.util.List;

import org.coin.enums.ReturnStatus;
import org.coin.exception.APIException;
import org.coin.service.CurrencyService;
import org.coin.vo.CoinReq;
import org.coin.vo.CoinResp;
import org.coin.vo.CurrentPrice;
import org.coin.vo.NewCoinResp;
import org.coin.vo.RespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/coindesk")
public class CoinDeskAPIController {

	@Autowired
	private CurrencyService currencyService;

	/**
	 * get all new current price
	 * 
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value = "/original/currentprice/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RespVO<CurrentPrice>> getOriginalCurrentPrice() throws APIException {
		RespVO<CurrentPrice> returnVO = new RespVO<CurrentPrice>();
		returnVO.setCode(ReturnStatus.SUCCESS.code);
		returnVO.setMsg(ReturnStatus.SUCCESS.msg);
		returnVO.setData(currencyService.getAllOriginalCurrentPrice());
		ResponseEntity<RespVO<CurrentPrice>> result = new ResponseEntity<RespVO<CurrentPrice>>(returnVO, HttpStatus.OK);
		return result;
	}

	/**
	 * get all new current price
	 * 
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value = "/new/currentprice/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RespVO<List<NewCoinResp>>> getNewCurrentPrice() throws APIException {
		RespVO<List<NewCoinResp>> returnVO = new RespVO<List<NewCoinResp>>();
		returnVO.setCode(ReturnStatus.SUCCESS.code);
		returnVO.setMsg(ReturnStatus.SUCCESS.msg);
		returnVO.setData(currencyService.getAllNewCurrentPrice());
		ResponseEntity<RespVO<List<NewCoinResp>>> result = new ResponseEntity<RespVO<List<NewCoinResp>>>(returnVO,
				HttpStatus.OK);
		return result;
	}

	/**
	 * get all current price
	 * 
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value = "/all/currentprice/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RespVO<List<CoinResp>>> getAllCurrentPrice() throws APIException {
		RespVO<List<CoinResp>> returnVO = new RespVO<List<CoinResp>>();
		returnVO.setCode(ReturnStatus.SUCCESS.code);
		returnVO.setMsg(ReturnStatus.SUCCESS.msg);
		returnVO.setData(currencyService.getAllCurrency());
		ResponseEntity<RespVO<List<CoinResp>>> result = new ResponseEntity<RespVO<List<CoinResp>>>(returnVO,
				HttpStatus.OK);
		return result;
	}

	/**
	 * get current price
	 * 
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value = "/currentprice/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RespVO<CoinResp>> getCurrentPrice(
			@RequestParam(name = "coin", required = true) @Validated String coin) throws APIException {
		RespVO<CoinResp> returnVO = new RespVO<CoinResp>();
		returnVO.setCode(ReturnStatus.SUCCESS.code);
		returnVO.setMsg(ReturnStatus.SUCCESS.msg);
		returnVO.setData(currencyService.getCurrency(coin));
		ResponseEntity<RespVO<CoinResp>> result = new ResponseEntity<RespVO<CoinResp>>(returnVO, HttpStatus.OK);
		return result;
	}

	/**
	 * create currency price
	 * 
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value = "/currentprice/create", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RespVO<Boolean>> createCurrentPrice(@RequestBody @Validated CoinReq coin)
			throws APIException {
		RespVO<Boolean> returnVO = new RespVO<Boolean>();
		returnVO.setCode(ReturnStatus.SUCCESS.code);
		returnVO.setMsg(ReturnStatus.SUCCESS.msg);
		returnVO.setData(currencyService.createCurrency(coin));
		ResponseEntity<RespVO<Boolean>> result = new ResponseEntity<RespVO<Boolean>>(returnVO, HttpStatus.OK);
		return result;
	}

	/**
	 * update currency price
	 * 
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value = "/currentprice/update", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RespVO<Boolean>> updateCurrentPrice(@RequestBody @Validated CoinReq coin)
			throws APIException {
		RespVO<Boolean> returnVO = new RespVO<Boolean>();
		returnVO.setCode(ReturnStatus.SUCCESS.code);
		returnVO.setMsg(ReturnStatus.SUCCESS.msg);
		returnVO.setData(currencyService.updateCurrency(coin));
		ResponseEntity<RespVO<Boolean>> result = new ResponseEntity<RespVO<Boolean>>(returnVO, HttpStatus.OK);
		return result;
	}

	/**
	 * delete currency price
	 * 
	 * @return
	 * @throws APIException
	 */
	@RequestMapping(value = "/currentprice/delete", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RespVO<Boolean>> deleteCurrentPrice(
			@RequestParam(name = "coin", required = true) @Validated String coin) throws APIException {
		RespVO<Boolean> returnVO = new RespVO<Boolean>();
		returnVO.setCode(ReturnStatus.SUCCESS.code);
		returnVO.setMsg(ReturnStatus.SUCCESS.msg);
		returnVO.setData(currencyService.deleteCurrency(coin));
		ResponseEntity<RespVO<Boolean>> result = new ResponseEntity<RespVO<Boolean>>(returnVO, HttpStatus.OK);
		return result;
	}

}
