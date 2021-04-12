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


# Problemas encontrados no projeto e soluções desenvolvidas

>**_CRITERIOS_** Detalhes sobre a solução, gostaríamos de saber qual foi o seu racional nas decisões;

## Etapas do Projeto:
>**_Nota_** O projeto está dividido em duas etapas de descisão.

### 1ª Etapa:

A primeira era simples só construir uma api que recebe uma String e retorna um boolean, estava fazendo pelo método de requisição GET.

#### Problemas encontrados nessa parte:
##### Problema 1:
O Primeiro problema foi que não consegui validar dados duplicados utilizando expressões regulares e patters, no caso o codigo estava assim:
```JAVA
private static final String REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$@#!%^&*)(+-])(?=.*[0-9a-zA-Z])[0-9a-zA-Z$@#!%^&*)(+-]{9,}$";

public Boolean isValid(String password) {
		LOGGER.info("Validando padrão da senha.");		
		Pattern pattern = Pattern.compile(REGEX);
		if(!password.isEmpty() && password != null && pattern.matcher(password).matches()) {		
			return true;
		} 
		return false;
	}
```
##### Solução Problema 1:
Solução para esse problema foi resolver isso com esquema de comparações de vetores, comparando letra por letra, gostaria de ter feito com expressão regulares mas iria gastar um tempinho para pesquisar, então fiz uma comparação de letras usando a logica de programação que iria ter o mesmo resultado, no caso o codigo ficou assim:

```Java
private static final String REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$@#!%^&*)(+-])(?=.*[0-9a-zA-Z])[0-9a-zA-Z$@#!%^&*)(+-]{9,}$";
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordServiceImpl.class);
	private String brokenPassword[];
	private int numberInteractions;
	
	@Override
	public Boolean isValid(String password) {
		LOGGER.info("Validando padrão da senha.");		
		Pattern pattern = Pattern.compile(REGEX);
		if(!password.isEmpty() && password != null && pattern.matcher(password).matches()) {
			this.brokenPassword = password.toUpperCase().split("");
			for(String letterPassword: brokenPassword) {
				numberInteractions = 0;
				for(int i = 0; i<brokenPassword.length;i++) {
					if(letterPassword.equals(brokenPassword[i])) {
						numberInteractions++;
					}
					if(numberInteractions>1) {
						return false;
					}
				}
			}
			
			return true;
		} 
		return false;
	}

```
##### Problema 2:
O segundo foi que usar o método de requisição GET me gerou problemas nos testes integrados pois alguns testes que adicionei para validar todos os cenários possíveis estavam dando bad request e algumas senhas validas estavam retornando como senha não validas, fui pesquisar e li que tem alguns tipos de caracteres que são considerados como caracteres irregulares ou caracteres de operações logicas dentro da URL e Uri.

>**_Nota_** Padrões de URL e Uri consultados para requisições GET:  rfc-3986, rfc 11738(Urls)e rfc-2396(Uris).
```
// casos de sucesso comentados que geram problemas na uri e url no request por GET passando o valor na URL 

	 
//caracteres inrregulares de URI ou operadores de URL -  %^# - Bad request consultar rfc-3986 e rfc-11738(URLs) e rfc-2396(URIs)
//# - no começo da frase e irregular e no meio e espaço em branco e no final da frase é irregular 
//		 passwordList.add("#3wE9!fhk");
//		 passwordList.add("AbT*9!fo%");
//		 passwordList.add("AbT^9!zok");
//		 passwordList.add("%3wE9!fhk");
//		 passwordList.add("AbT#9!fZk");
		 

// casos de sucesso comentados que geram problemas na uri e url	no request por GET passando a senha por parametro para URL 
//caracteres inrregulares de URI ou operadores de URL -  %^#&+ - Bad request
// + - considerado como operador logico - URI não reconhece como caracter
//# e & -considerado com espaço em branco não reconhece como caracter
		 
//		 passwordList.add("#3wE9!fhk");
//		 passwordList.add("AbT*9!fo%");
//		 passwordList.add("AbT^9!zok");
//		 passwordList.add("%3wE9!fhk");
//		 passwordList.add("AbT#9!fZk");	
//		 passwordList.add("&3wE9!fhk");
//		 passwordList.add("+3wE9!fhk");	 
//		 passwordList.add("+bT(9!fhk");

````

##### Solução 2:
Percebi que teria que mudar a forma como iria tratar esses dados, senão não iria atender a todos os casos de caracteres que estavam propostos no desafio. Então resolvi fazer um CRUD com POST,GET,DELETE,PUT, assim poderia passar uma String e não iria ter conflito de caracteres, e o método de validação da senha iria ficar como um serviço do POST(Create) e do PUT(Update), mas para isso iria precisar ter um repositório de dados.

### 2ª Etapa:

#### Problemas encontrados nessa parte:

##### Problema:
Construir esse CRUD da forma correta, com Desing de API, SOLID, Arquitetura Limpa e os testes unitários e integrados em 3 dias no meu tempo livre.
>**_Nota_** Estava no final do 2º dia e só faltavam 5 dias e ainda tinha que escrever o Readme.md.

##### Solução:
A comunicação entre as classe  e suas estruturas ficaria da seguinte forma:

>**_Nota_** Consulta de técnicas descritas por Robert C. Martin, em seu livro Clean Arquiteture.

Utilizei o conceito de package by component, aonde a visão está centrada nos services,  gerando assim um equilíbrio de acoplamento, coesão abstração e extensibilidade na aplicação. 

##### Estruturei as camadas de acessos da seguinte forma:

A camada de serviço fica como uma interface que gera padrões de senhas e quem firmar um contrato com essa classe deve implementa-lo no seu serviço de senhas, e com isso todas as camadas são independentes, gerando assim uma fácil manutenção no código e segurança na api.

**Estrutura do Projeto e comunicação das classes:**

 - User -> PasswordController

 - PasswordController -> PasswordService(Interface)  <- (Implementa)PasswordServiceImpl -> Password Repository(Interface)

 - PasswordControler-> PasswordService (Interface) <- (Implementa)PasswordServiceImpl -> PasswordEntity(model)

 - PasswordController-> PasswordEntity(model)

 - PasswordController-> PasswordService (Interface) -> PasswordEntity(model)

**Frameworks utilizados na aplicação Spring-boot WEB:**

 - Spring- boot Test.

 - Spring-boot Data JPA.

 - JUnit5.

 - H2 – data base (banco de dados criado na memoria volátil, escolhi esse banco de dados pois não sabia aonde o projeto seria executado, ele precisa de menos permições do que se fosse um banco de dados MySQL-Server ou PostgreSQL-server.

 - Spring MockMvc – para fazer as requisições HTTP na classe de testes Integrados.
 
 **Configuração do application.properties**
````
server.port=8585
#datasource
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:password-form
spring.datasource.username=sa
spring.datasource.password=
#jpa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
#h2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
````
**Como ficou as classes do projeto:** 

 - PasswordController -  responsável pelo controle logico da aplicação.
 ```java
 
@RestController
@RequestMapping("/validationPassword")
public class PasswordController {
	private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private PasswordRepository passwordRepository;
	
	@GetMapping
	private  ResponseEntity<Boolean> validation(@RequestParam(value = "password") String password) throws Exception {
		boolean isPasswordIsValid =this.passwordService.isValid(password);
		return new ResponseEntity<>(isPasswordIsValid, HttpStatus.OK);

	}
	@GetMapping("/{password}")
	private ResponseEntity<Boolean> validationByName(@PathVariable String password) throws Exception {
		boolean isPasswordIsValid =this.passwordService.isValid(password);
		return new ResponseEntity<>(isPasswordIsValid, HttpStatus.OK);

	}
	
	@GetMapping("/password")
    public List<PasswordEntity> Get() {
        return this.passwordRepository.findAll();
    }

	@GetMapping("/password/{id}")
    public ResponseEntity<PasswordEntity> GetById(@PathVariable(value = "id") long id)
    {
        Optional<PasswordEntity> passwordEntity = this.passwordRepository.findById(id);
        if(passwordEntity.isPresent())
            return new ResponseEntity<PasswordEntity>(passwordEntity.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@PostMapping("/password")
    public ResponseEntity<PasswordEntity> Post(@Valid @RequestBody PasswordEntity passwordEntity)
    {	
		Optional<PasswordEntity> passwordValidation= this.passwordRepository.findById(passwordEntity.getId());
        if(passwordValidation.isPresent() != true){
			passwordEntity.setIsValidPassword(this.passwordService.isValid(passwordEntity.getPassword()));
			if(passwordEntity.isValidPassword()) {
				 this.passwordRepository.save(passwordEntity);
				 return new ResponseEntity<PasswordEntity>(passwordEntity, HttpStatus.CREATED);
			}
			 return new ResponseEntity<>(passwordEntity,HttpStatus.NOT_ACCEPTABLE);
        }
        else
        	return new ResponseEntity<>(passwordEntity,HttpStatus.CONFLICT);
    }
	
	@PutMapping(path = "/password/v1/{id}" , produces = { "application/json" })
    public ResponseEntity<PasswordEntity> Put(@PathVariable Long id, @Valid @RequestBody PasswordEntity newPasswordEntity)
    {	
        Optional<PasswordEntity> oldPasswordEntity = this.passwordRepository.findById(id);
        if(oldPasswordEntity.isPresent()){
        	newPasswordEntity.setIsValidPassword(this.passwordService.isValid(newPasswordEntity.getPassword()));
        	if(newPasswordEntity.isValidPassword() && id == newPasswordEntity.getId()) {
        		PasswordEntity passwordEntity = oldPasswordEntity.get();
            	passwordEntity.setPassword(newPasswordEntity.getPassword());
            	this.passwordRepository.save(passwordEntity);
                return new ResponseEntity<PasswordEntity>(passwordEntity, HttpStatus.CREATED);
        	}
        	else
        		return new ResponseEntity<>(newPasswordEntity,HttpStatus.NOT_ACCEPTABLE);        	
        }
        else
            return new ResponseEntity<>(newPasswordEntity,HttpStatus.NOT_FOUND);
    }
	
	@DeleteMapping("/password/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<PasswordEntity> passwordEntity = this.passwordRepository.findById(id);
        if(passwordEntity.isPresent()){
        	this.passwordRepository.delete(passwordEntity.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	@DeleteMapping("/password")
    public ResponseEntity<Object> DeleteAll()
    {	
		if(this.passwordRepository.findAll().size()>0) {
			this.passwordRepository.deleteAll();
	        return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
    }
	
}

 ```

 - PasswordService – interface do serviço de validar senha.
 ```Java
 public interface PasswordService {
	Boolean isValid(String password);
}
 ```

 - PasswordServiceImpl – implementa(firma contrato) com a PasswordService e realiza a logica da validação da senha conforme o desafio proposto.
```Java
@Service
public class PasswordServiceImpl implements PasswordService{
	private static final String REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$@#!%^&*)(+-])(?=.*[0-9a-zA-Z])[0-9a-zA-Z$@#!%^&*)(+-]{9,}$";
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordServiceImpl.class);
	private String brokenPassword[];
	private int numberInteractions;
	
	@Override
	public Boolean isValid(String password) {
		LOGGER.info("Validando padrão da senha.");		
		Pattern pattern = Pattern.compile(REGEX);
		if(!password.isEmpty() && password != null && pattern.matcher(password).matches()) {
			this.brokenPassword = password.toUpperCase().split("");
			for(String letterPassword: brokenPassword) {
				numberInteractions = 0;
				for(int i = 0; i<brokenPassword.length;i++) {
					if(letterPassword.equals(brokenPassword[i])) {
						numberInteractions++;
					}
					if(numberInteractions>1) {
						return false;
					}
				}
			}
			
			return true;
		} 
		return false;
	}
	
	
}
 ```
 - PasswordEntity -  Modelo do objeto para manipulação dos dados.
```Java
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class PasswordEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String password;
	private boolean isValidPassword;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isValidPassword() {
		return isValidPassword;
	}
	public void setIsValidPassword(boolean isValidPassword) {
		this.isValidPassword = isValidPassword;
	}
	
}
 ```
 - Password Repository -  interface que herda os recursos do spring JPA Repository para criação do banco de dados de acordo com sua entidade(PasswordEntity) .
```Java
public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {

}
 ```

**Classes de testes unitários e integrados:**

 - PasswordJavaApiUnitTests – Responsavél pelo testes unitários do projeto de acordo com as regras estabelecidas para o desafio.
 ```Java
 
@SpringBootTest (classes = {PasswordServiceImpl.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
public class PasswordJavaApiUnitTests {
	private List<String> passwordList;
	 @Autowired
	 PasswordService passwordService;
	 
	 @Test
	 public void validationSucessPasswordGET() throws Exception{
		 passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok"); 
		 passwordList.add("AbT*9!fo%");
		 passwordList.add("AbT^9!zok");
		 passwordList.add("AbT#9!fZk");
		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("+bT(9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");
		 passwordList.add("#T3w9!fhk");
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("%3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
		 
		 boolean booleansList[] = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertEquals(passwordService.isValid(passwordList.get(i)),booleansList[i]);
		 }
	 }
	 
	 @Test
	 public void validationPasswordNotAvailable() throws Exception{
		 passwordList = new ArrayList<String>();
		 passwordList.add("");
		 passwordList.add("aa");
		 passwordList.add("ab");
		 passwordList.add("AAAbbbCc");
		 passwordList.add("AbTp9!foo");
		 passwordList.add("AbTp9!foA");
		 passwordList.add("AbTp9 fok");
		 passwordList.add("-T-w9!fhk");
		 passwordList.add("         ");
		 passwordList.add("1234567890");
		 
		 boolean booleansList2[] = {false,false,false,false,false,false,false,false,false,false};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertEquals(passwordService.isValid(passwordList.get(i)),booleansList2[i]);
		 }
	 }

	 @Test
	 public void validationNegationPasswordNotAvailable() throws Exception{
		 passwordList = new ArrayList<String>();
		 passwordList.add("");
		 passwordList.add("aa");
		 passwordList.add("ab");
		 passwordList.add("AAAbbbCc");
		 passwordList.add("AbTp9!foo");
		 passwordList.add("AbTp9!foA");
		 passwordList.add("AbTp9 fok");
		 passwordList.add("AbTp9!fok");
		 passwordList.add("AbTp9!@@@");
		 passwordList.add("A$TA9!@@@");
		 passwordList.add("AbTA9!---");
		 passwordList.add("#bTA9!-++");
		 passwordList.add("Ab(A^!-++");
		 passwordList.add("AbK(01@%)");
		 
		 boolean booleansList3[] = {true,true,true,true,true,true,true,false,true,true,true,true,true,false};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertNotEquals(passwordService.isValid(passwordList.get(i)),booleansList3[i]);
		 }
	 }
	 
	 @Test
	 public void validationNegationPasswordSucessGET() throws Exception{
		 passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok"); 
		 passwordList.add("AbT*9!fo%");
		 passwordList.add("AbT^9!zok");
		 passwordList.add("AbT#9!fZk");
		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("+bT(9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");
		 passwordList.add("#T3w9!fhk");
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("%3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
		 
		 boolean booleansList4[] = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertNotEquals(passwordService.isValid(passwordList.get(i)),booleansList4[i]);
		 }
	 }
}
 ```

 - PasswordJavaApiIntegrationTest– Responsavél pelo testes integrados para validar as resquisições HTTP conforme as regras estabelecidas no projeto.
```Java



@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PasswordJavaApiIntegrationTest {
	
	private PasswordEntity passwordEntity;
	
	@LocalServerPort
    private int port;
	
	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	public void contextLoad() {
		assertNotNull(mvc);
	}
	
	@Test
	@Order(2)
	public void testAPiGETURISucess() throws Exception {
		List<String> passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok");
		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("+bT(9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");		 
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
// casos de sucesso comentador que geram problemas na uri e url no request por GET
	 
//caracteres inrregulares de URI ou operadores de URL -  %^# - Bad request consultar rfc-3986 e rfc-11738(URLs) e rfc-2396(URIs)
//# - no começo da frase e irregular e no meio e espaço em branco e no final da frase é irregular 
//		 passwordList.add("#3wE9!fhk");
//		 passwordList.add("AbT*9!fo%");
//		 passwordList.add("AbT^9!zok");
//		 passwordList.add("%3wE9!fhk");
//		 passwordList.add("AbT#9!fZk");
		 
		for(String password: passwordList) {
			this.mvc.perform(get("/validationPassword/"+password)).andExpect(status().isOk());
		}
	}
	
	@Test
	@Order(3)
	public void testAPiGETParameterSucess() throws Exception {
		List<String> passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok"); 

		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");		 
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");


// casos de sucesso comentador que geram problemas na uri e url	no request por GET	 
//caracteres inrregulares de URI ou operadores de URL -  %^#&+ - Bad request
// + - considerado como operador logico - URI não reconhece como caracter
//# e & -considerado com espaço em branco não reconhece como caracter
		 
//		 passwordList.add("#3wE9!fhk");
//		 passwordList.add("AbT*9!fo%");
//		 passwordList.add("AbT^9!zok");
//		 passwordList.add("%3wE9!fhk");
//		 passwordList.add("AbT#9!fZk");	
//		 passwordList.add("&3wE9!fhk");
//		 passwordList.add("+3wE9!fhk");	 
//		 passwordList.add("+bT(9!fhk");
		 
		for(String password: passwordList) {
			this.mvc.perform(get("/validationPassword?password="+password)).andExpect(status().isOk());
		}	
		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(4)
	public void testClassPasswordControllerCRUDComplete() throws Exception {
		List<String> passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok"); 
		 passwordList.add("AbT*9!fo%");
		 passwordList.add("AbT^9!zok");
		 passwordList.add("AbT#9!fZk");
		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("+bT(9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");		 
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("%3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
		 passwordList.add("#3wE9!fhk");
		 
		long id= 1;
		this.passwordEntity = new PasswordEntity();
		for(String password :  passwordList) {
			passwordEntity.setId(id);
			passwordEntity.setPassword(password);
			
			this.mvc.perform(post("/validationPassword/password")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.content(asJsonString(passwordEntity)))
			.andExpect(status().isCreated());
			
			id++;
		}
		id = 4;
		this.passwordEntity.setId(id);
		this.passwordEntity.setPassword("#3wE9!fhk");
		PasswordEntity passwordEntitycomparation = this.passwordEntity;
		this.mvc.perform(post("/validationPassword/password")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
      		.content(asJsonString(passwordEntity)))
		.andExpect(status().isConflict()).andReturn().equals(passwordEntitycomparation);
		
		id = 4;
		this.passwordEntity.setId(id);
		this.passwordEntity.setPassword("#3wE9!fhk");
		passwordEntitycomparation = this.passwordEntity;
		this.mvc.perform(put("/validationPassword/password/v1/"+id)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
      		.content(asJsonString(passwordEntity)))
		.andExpect(status().isCreated()).andReturn().equals(passwordEntitycomparation);
		
		this.mvc.perform(get("/validationPassword/password/"+4)).andExpect(status().isOk()).andReturn().equals(passwordEntitycomparation);		
		
		id = 5;
		this.passwordEntity.setId(id);
		this.passwordEntity.setPassword("#3wE9!fhk");
		passwordEntitycomparation = this.passwordEntity;
		this.mvc.perform(put("/validationPassword/password/v1/"+1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
      		.content(asJsonString(passwordEntity)))
		.andExpect(status().isNotAcceptable()).andReturn().equals(passwordEntitycomparation);
		
		this.mvc.perform(delete("/validationPassword/password/"+1)).andExpect(status().isAccepted());
		
		this.mvc.perform(delete("/validationPassword/password")).andExpect(status().isAccepted());
		
	}
	
	@Test
	@Order(5)
	public void testReturnPasswordStatusNotAceeptablePOST() throws Exception {
		List<String> passwordList = new ArrayList<String>();
		 passwordList.add("");
		 passwordList.add("aa");
		 passwordList.add("ab");
		 passwordList.add("AAAbbbCc");
		 passwordList.add("AbTp9!foo");
		 passwordList.add("AbTp9!foA");
		 passwordList.add("AbTp9 fok");
		 passwordList.add("-T-w9!fhk");
		 passwordList.add("         ");
		 passwordList.add("1234567890");
		 
		long id= 1;
		PasswordEntity passwordEntity = new PasswordEntity();
		for(String password :  passwordList) {
			passwordEntity.setId(id);
			passwordEntity.setPassword(password);
			
			this.mvc.perform(post("/validationPassword/password")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.content(asJsonString(passwordEntity)))
			.andExpect(status().isNotAcceptable());
			
			id++;
		}
		
	}
	
	@Test
	@Order(6)
	public void testReturnPasswordGETNotFound() throws Exception {
		this.mvc.perform(get("/validationPassword/password/1")).andExpect(status().isNotFound());	
		
	}
	
	@Test
	@Order(7)
	public void testReturnPasswordPUTNotFound() throws Exception {
		PasswordEntity obj = new PasswordEntity();
		long id = 1;
		obj.setId(id);
		obj.setPassword("#3wE9!fhk");
		
		this.mvc.perform(put("/validationPassword/password/v1/"+id)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(asJsonString(obj)))
		.andExpect(status().isNotFound());
		
	}
	@Test
	@Order(8)
	public void testReturnPasswordDeleteNotFound() throws Exception {
		PasswordEntity passwordEntity = new PasswordEntity();
		long id = 1;
		passwordEntity.setId(id);
		passwordEntity.setPassword("#3wE9!fhk");
		
		this.mvc.perform(delete("/validationPassword/password/1")).andExpect(status().isNotFound());
		
	}
	@Test
	@Order(9)
	public void testReturnPasswordDeleteAllNotFound() throws Exception {
		PasswordEntity passwordEntity = new PasswordEntity();
		long id = 1;
		passwordEntity.setId(id);
		passwordEntity.setPassword("#3wE9!fhk");
		
		this.mvc.perform(delete("/validationPassword/password")).andExpect(status().isNotFound());
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
 ```


