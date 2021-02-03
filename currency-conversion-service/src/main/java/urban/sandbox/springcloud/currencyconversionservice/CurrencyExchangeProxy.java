package urban.sandbox.springcloud.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Instância fixa
//@FeignClient(name="currency-exchange", url="localhost:8000")

// Client side load balancing embutido no eureka client (versões anteriores era o ribbon)
// Sem definir a url, o serviço irá conversar com Eureka para obter a instância por meio de um balanceador de carga
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	CurrencyConversion retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to);

}
