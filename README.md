# springcloud-sandbox
Spring Cloud Sandbox

## Dependências
- JDK 15;
- Docker;

## Spring Cloud Config Server
Exemplo utilizando o servidor centralizado de configuração do spring.
Para mais detalhes, ver [SpringCloudConfigServer.md](/.docs/SpringCloudConfigServer.md)

## Distributed Tracing
Rastreamento distribuido de logs utilizando o Zipkin.

Rodar o comando `docker run -p 9411:9411 openzipkin/zipkin:2.23` para rodar o Zipkin.
Cada serviço deve definir o endereço do Zipkin no application.properties e adicionar a dependência no pom.
O Zipkin é responsável por adicionar uma tag em cada log que permite você rastrear por quais serviços a requisição passou.
A busca é feita via http://localhost:9411/zipkin/.

## Naming Server
Server Discovery utilizando Eureka.

Servidor responsável por gerenciar o registro dos serviços que fazem parte do contexto.
Cada serviço deve definir o endereço do Eureka no application.properties.

## API Gateway
Gateway de roteamento e balanceamento de carga para os demais serviços.

## Currency Exchange Service
Serviço de taxa de câmbio.
Ele acessa uma base em memória (H2), se registra no Eureka, habilita o trace do logs via Zipkin e utiliza o Resilience4j para tolerância de falhas.

## Currency Conversion Service
Serviço de conversão de valores.
Ele recebe solicitações de conversão que delega para o serviço de taxa de câmbio.
Este serviço também se registra no Eureka e habilita o trace do logs via Zipkin.

## Utilização

1. Rodar o rastreamento distribuido com Zipkin;
2. Executar a classe [`NamingServerApplication.java`](/naming-server/src/main/java/urban/sandbox/springcloud/namingserver/NamingServerApplication.java);
3. Executar a classe [`CurrencyExchangeServiceApplication.java`](/currency-exchange-service/src/main/java/urban/sandbox/springcloud/currencyexchangeservice/CurrencyExchangeServiceApplication.java);
4. Executar a classe [`CurrencyConversionServiceApplication.java`](/currency-conversion-service/src/main/java/urban/sandbox/springcloud/currencyconversionservice/CurrencyConversionServiceApplication.java);
5. Executar a classe [`ApiGatewayApplication.java`](/api-gateway/src/main/java/urban/sandbox/springcloud/apigateway/ApiGatewayApplication.java);

Acessar http://localhost:8761/ para conferir os serviços registrados no Eureka.

Acessar http://localhost:9411/zipkin/ para rastrear logs;

Acessar http://localhost:15672 para conferir as mensagens no rabbitmq;

Currency Exchange
mvn spring-boot:build-image
docker run -p 8000:8000 diegourban/scs-currency-exchange-service:0.0.1-SNAPSHOT