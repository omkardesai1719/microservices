package com.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.currencyconversionservice.bean.CurrencyConverserion;
import com.microservices.currencyconversionservice.proxy.CurrencyExcahangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExcahangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverserion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put("from", from);
		parameters.put("to", to);
		
		ResponseEntity<CurrencyConverserion> Exchange = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConverserion.class, parameters);
		
		CurrencyConverserion response = Exchange.getBody();
		
		return new CurrencyConverserion(response.getId(), from, to, response.getConversionMultiple(), quantity, 
				quantity.multiply(response.getConversionMultiple()), response.getEnvironment()+ " Rest API");
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConverserion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
				
		CurrencyConverserion response = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConverserion(response.getId(), from, to, response.getConversionMultiple(), quantity, 
				quantity.multiply(response.getConversionMultiple()), response.getEnvironment()+ " Feign");
	}
}
