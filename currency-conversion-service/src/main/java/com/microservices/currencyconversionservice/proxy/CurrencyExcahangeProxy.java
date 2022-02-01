package com.microservices.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.currencyconversionservice.bean.CurrencyConverserion;


//@FeignClient(name="currency-exchange-service", url="localhost:8000")
@FeignClient(name="currency-exchange")
public interface CurrencyExcahangeProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConverserion retrieveExchangeValue(@PathVariable(value = "from") String from, 
			@PathVariable(value = "to") String to);
	
}
