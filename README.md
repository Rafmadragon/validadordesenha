# validadordesenha
Projeto validar senha (desafio)

# Intruções básicas de como executar o projeto validadordesenha

### Programas que precisam estar instalados para rodar o projeto:

- Maven

- JDK11 ou superior

- Git bash

- Postman – (opcional) caso queira testar a api funcionando na pratica

> **_NOTA_** -Ter configurado maven Home e java home no Windows ou mac.

Utilizar o códigos abaixo para verificar maven e jdk estão instalados:

```
$  mvn -version
```

Verificar a versão do JAVA se e 11 ou superior.

### Baixar Projeto validadordesenha no git:

Baixar o projeto do git pelo terminal (prompt de comando) na pasta que deseja salva-lo

```
$  git clone https://github.com/Rafmadragon/validadordesenha.git
```
Acessar a pasta raiz do projeto aonde está o arquivo pom.xml

```
$  cd validadordesenha/projetoitisenha
```
Para verificar a existência do arquivo pom.xml  executar o comando:

Caso esteja no terminal Windows:
```
$ dir
```
Caso esteja no terminal Mac , git bash , linux:
```
$ ls
```
> **_NOTA_**  -Ele irá listar todos os arquivos não ocultos do projeto, verifique se o pom.xml está nessa pasta.

## Executar Projeto Spring-boot
> **_NOTA_**  -Para executar o projeto valdiadordesenha, entre na pasta que esteja com o pom.xml.

### Iniciar aplicação WEB:
Comando para executar projeto inteiro do spring boot, para acessar a API web que ira validar a senha conforme os requisitos do desafio.
```
$ mvn spring-boot:run
```
> **_NOTA_** - Esperar até aparecer uma mensagem que ele foi inicializado(“Started ProjetoitisenhaApplication in 2.601 seconds (JVM running)”), quando ela aparecer já pode acessar as URLs para testes de API pelo postman ou navegador.

> **_ALERTA_**  -Caso apareça uma mensagem informando que a porta 8585 está sendo usada siga as seguintes etapas a seguir e execute o comando de inicialização do spring novamente.

### Liberar porta 8585:
Consultar portas que estão sendo utilizadas.
```
$ netstat -a -n –o
```
>**_NOTA_**  -Ira aparecer essas colunas (Proto,  Endereço local ,        Endereço externo ,      Estado  ,         PID), olhar a de endereço local e PID.

Olhar a lista de portas que estão ativas e verifique se a 8585 (na coluna endereço local) está sendo usada, se estiver aparecendo pegue o numero do PID dela e execute o comando 
a abaixo:
```
$ taskkill -f -im  NumeroDoPID
```
>**_NOTA_**  -Isso irá finalizar o processo e você poderá rodar o spring boot novamente.

>**_NOTA_**  -Caso esteja usando uma ide para executar o projeto.
 ou
No arquivo **application.properties** está à configuração da porta do servidor e só trocar para outra porta caso essa não esteja funcionando.

### Finalizar projeto spring-boot no terminal:
Para finalizar esse processo depois que realizou os testes nas Urls , clique em cima do prompt de comando e aperte o comando:
```
ctrl+c
```

>**_NOTA_**  -Esse comando irá finalizar o spring – boot e a aplicação será encerrada.




## Executar Testes integrados e Teste unitários da aplicação
Para executar os testes unitários e testes integrados do projeto digite o comando:
```
$ mvn integration-test
```
Ou
```
$ mvn test
```
>**_NOTA_**  -Valide se o build sucess apareceu após terminar os processo.




![image](https://user-images.githubusercontent.com/49215188/114322701-00bec000-9af8-11eb-892b-83c9e6693e7e.png)
