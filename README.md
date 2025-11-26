# Demo Spring Boot API

API REST simples criada com Spring Boot.

## Endpoints disponíveis

- `GET /api/hello` - Retorna uma mensagem de saudação
- `GET /api/status` - Retorna o status da aplicação
- `POST /api/echo` - Retorna o payload recebido
- `GET /api/user/{id}` - Retorna informações de um usuário dummy

## Como executar

```bash
mvn spring-boot:run
```

Ou compile e execute o JAR:

```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## Requisitos

- Java 17+
- Maven 3.6+