# Spring Cloud Config Server

Serviço centralizado de configuração das aplicações.
O Spring Cloud Config Server irá apontar para um repositório git contendo todos os arquivos application.properties dos serviços. Ver [repositório centralizado de configurações](/git-localconfig-repo).

Cada serviço, por sua vez, irá definir o endereço (URL) do servidor de configuração. Ver [application.properties do limits-service](/limits-service/src/main/resources/application.properties).

Executar com perfil nativo para carregar do repositório local `mvn clean package -Dspring.profiles.active=native`.