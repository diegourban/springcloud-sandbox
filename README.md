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

## Gerar imagens Docker
Executar `mvn spring-boot:build-image` em cada projeto.

Exemplo de execução de imagem: `docker run -p 8000:8000 diegourban/scs-currency-exchange-service:0.0.1-SNAPSHOT`

## Kubernetes (K8S)
Cloud providers e seus serviços de Kubernetes:
- AKS: Azure Kubernetes Service
- Amazon EKS: Elastic Kubernetes Service
- GKS: Google Kubernetes Engine

Nesse curso foi utilizado o GKS devido ao free-tier para testes.

O cluster do K8S é composto por Master Node(s) e Worker Node(s), onde o Master gerencia o cluster enquanto que o Worker executa a aplicação.

Criar um cluster de Kubernetes Google Cloud Platform(GCP):
1. Acessar https://console.cloud.google.com;
2. Pesquisar por Kubernetes e ativar o produto Kubernetes Engine;
3. Após ativo, criar um cluster com nome "cluster-springcloudsandbox";

Deploy via Cloud Shell:
1. Acessar https://console.cloud.google.com;
2. No menu de navegação, acesse Kubernetes Engine > Clusters;
3. Na lista, clique no registro de nome "cluster-springcloudsandbox";
4. No cabeçalho da página, clique no botão "Ativar o Cloud Shell";
5. Em seguida, clique no botão "Conectar" localizado na barra de ações logo abaixo;
6. Copie o comando e execute-o no Cloud Shell;
Exemplo:
`gcloud container clusters get-credentials cluster-springcloudsandbox --zone southamerica-east1-a --project spring-cloud-sandbox-306912`
7. Após conectado, execute o comando `kubectl version` para confirmar se o Kubernetes Controller está instalado;
8. 

Criar o deploy: kubectl create deployment hello-world-rest-api --image=in28min/hello-world-rest-api:0.0.1.RELEASE
Irá criar um deployment, replicaset e pod

Expor o deploy: kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080
Irá criar o serviço


Pod é a menor unidade de deploy. 
Você não pode ter um container sem um Pod. Um Pod pode ter múltiplos containers.
Cada Pod tem um endereço IP único. Os containers dentro do Pod compartilham recursos e podem conversar entre si usando localhost.


Comandos: https://github.com/in28minutes/spring-microservices-v2/tree/main/05.kubernetes#commands

Dados utilizados
Conta do Google Cloud Platform: springcloudsandbox@gmail.com
Nome do cluster: cluster-springcloudsandbox
