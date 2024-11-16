<a name="readme-top"></a>

<br />
 

<div align="center">

 ![Diagrama de Classes da API](src/main/resources/assets/Logo_thunder_cats.png)
 

 
## Gestão de projetos API (css_avl_gestao_projetos)
 
... Esta API tem como objetivo prover um sistema para que sejam mantidos dados de projetos.  

 
 
## Diagrama da arquitetura da API 
 
A decisão arquitetural foi criada tendo como princípios fortes de SOLID. Para tal, foi usado para desenvolvimento de camadas de regra de negócio a notação de caso de usos(UseCases) afim de uma melhor implementação da métodologia BDD..

 
## Tecnologias
 
 
Estas são as tecnologias utilizadas nesse projeto.

* Java
* Spring Boot
* Actuator
* Spring Data JPA
* PostGreSQL
* lombok
* Logback 
* Gson
 
## Services Used
 
* Github
* ...
 
 
## Java
... 17
 
## Rodando a aplicação

### *Subindo o banco de dados em um DOCKER local*

- Supondo que já esteja instalado o DOCKER na máquina e funcionando. Através do CMD, navegue até a pasta css-ped-vendas onde está o arquivo docker-compose.yml, neste arquivo terá os dados de conexão da base de dados local. Execute o comando "docker compose up", será inicializado uma imagem do postgre sql local.
 
### *Usando o Git Bash*

- Navegue até o seu diretório de projetos de programação
- Faça o clone do projeto para a sua máquina local 
 
 
### *Atualizando o Maven e compilando*

Pode ser feito tanto pelo terminal quanto por uma IDE Java, a seguir as duas opções, finalizando na execução.
 
#### Através do terminal ou CMD

- Entre na raiz do projeto, pelo CMD é o seguinte comando: ```css_avl_gestao_projetos```
- Execute o comando via CMD ou terminal: ```mvn clean install```
- Execute o comando via CMD ou terminal: ```mvn clean package```

#### Usando a IDE

Através do Spring Tool Suite, Eclipse com o plugin STS ou outra IDE Java:
- Depois de importar o projeto deve atualizar as dependências do Maven clicando com o botão direito no projeto, buscando a opção ```Maven``` e clicar em ```Update Project```
- Na janela aberta selecione o projeto e clique no checkbox ```Force Update of Snapshots/Releases``` 
- Clique em ```Ok``` e aguarde
 
 
### *Acesando a API*

- Com a aplicação lançada, abra o navegador web e acesse o LINK http://localhost:8091/swagger-ui/index.html#.

![Logo of the project](src/main/resources/assets/Swagger_api_vendas.PNG)
  
## Links
 
 - Link do épico no jira: NA
 - Repository: https://github.com/cleberssanches1/css_avl_gestao_projetos
 
 
## Versionamento
 
1.0.0.0
 
 
## Autores
 
* **Cleber Santos Sanches**: @cleberssanches1 (https://github.com/cleberssanches1)
 
 
<p align="right">(<a href="#readme-top">volta ao topo</a>)</p>
