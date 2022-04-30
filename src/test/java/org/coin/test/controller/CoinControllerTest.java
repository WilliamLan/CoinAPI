package org.coin.test.controller;

import org.coin.main.CoinDeskApplication;
import org.coin.util.JsonUtil;
import org.coin.vo.RespVO;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoinDeskApplication.class)
@WebAppConfiguration
public class CoinControllerTest {

	public static final String coinDeskUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

	private static final Logger logger = LoggerFactory.getLogger(CoinControllerTest.class);

	@Autowired
	private WebApplicationContext context;

	private MockMvc mock;

	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testFlow() throws Exception {
		testSearchOriginalCoinAPI();
		testSearchNewCoin();
		testSearchAllCoin();
		testSearchCoin();
		testCreateCoin();
		testUpdateCoin();
		testDeleteCoin();
	}

	@Test
	public void testSearchOriginalCoinAPI() throws Exception {
		String uri = "/coindesk/original/currentprice/get";
		MvcResult result = mock.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		String respStr = result.getResponse().getContentAsString();
		Assert.assertNotNull(respStr);
		Assert.assertNotNull(JsonUtil.convertTFromJSON(respStr, RespVO.class));
		Assert.assertEquals("0000", JsonUtil.convertTFromJSON(respStr, RespVO.class).getCode());
	}

	@Test
	public void testSearchNewCoin() throws Exception {
		String uri = "/coindesk/new/currentprice/get";
		MvcResult result = mock.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		String respStr = result.getResponse().getContentAsString();
		Assert.assertNotNull(respStr);
		Assert.assertNotNull(JsonUtil.convertTFromJSON(respStr, RespVO.class));
		Assert.assertEquals("0000", JsonUtil.convertTFromJSON(respStr, RespVO.class).getCode());
	}

	@Test
	public void testSearchAllCoin() throws Exception {
		String uri = "/coindesk/all/currentprice/get";
		MvcResult result = mock.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		String respStr = result.getResponse().getContentAsString();
		Assert.assertNotNull(respStr);
		Assert.assertNotNull(JsonUtil.convertTFromJSON(respStr, RespVO.class));
		Assert.assertEquals("0000", JsonUtil.convertTFromJSON(respStr, RespVO.class).getCode());
	}

	@Test
	public void testSearchCoin() throws Exception {
		String uri = "/coindesk/currentprice/get?coin=USD";
		MvcResult result = mock.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
		String respStr = result.getResponse().getContentAsString();
		Assert.assertNotNull(respStr);
		Assert.assertNotNull(JsonUtil.convertTFromJSON(respStr, RespVO.class));
		Assert.assertEquals("0000", JsonUtil.convertTFromJSON(respStr, RespVO.class).getCode());
	}

	@Test
	public void testCreateCoin() throws Exception {
		String uri = "/coindesk/currentprice/create";
		//
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		//
		JSONObject request = new JSONObject().put("code", "NTU").put("chineseName", "台幣").put("rate", 30.34);
		//
		MvcResult result = mock.perform(MockMvcRequestBuilders.post(uri).headers(httpHeaders)
				.content(request.toString()).accept(MediaType.APPLICATION_JSON)).andReturn();
		String respStr = result.getResponse().getContentAsString();
		Assert.assertNotNull(respStr);
		Assert.assertNotNull(JsonUtil.convertTFromJSON(respStr, RespVO.class));
	}

	@Test
	public void testUpdateCoin() throws Exception {
		String uri = "/coindesk/currentprice/update";
		//
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		//
		JSONObject request = new JSONObject().put("code", "NTD").put("chineseName", "台幣111").put("rate", 33.3412);
		//
		MvcResult result = mock.perform(MockMvcRequestBuilders.post(uri).headers(httpHeaders)
				.content(request.toString()).accept(MediaType.APPLICATION_JSON)).andReturn();
		String respStr = result.getResponse().getContentAsString();
		Assert.assertNotNull(respStr);
		Assert.assertNotNull(JsonUtil.convertTFromJSON(respStr, RespVO.class));
	}

	@Test
	public void testDeleteCoin() throws Exception {
		String uri = "/coindesk/currentprice/delete?coin=NTD";
		//
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		//
		JSONObject request = new JSONObject();
		//
		MvcResult result = mock.perform(MockMvcRequestBuilders.post(uri).headers(httpHeaders)
				.content(request.toString()).accept(MediaType.APPLICATION_JSON)).andReturn();
		String respStr = result.getResponse().getContentAsString();
		Assert.assertNotNull(respStr);
		Assert.assertNotNull(JsonUtil.convertTFromJSON(respStr, RespVO.class));
	}

}
