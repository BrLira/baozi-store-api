# Baozi Store API

Projeto desenvolvido para a atividade pratica da disciplina **Desenvolvimento Web Back-End** da UNINTER.

- **Aluno:** Adauto Brenne Roberto Vieira Lira
- **RU:** 5048958
- **Repositorio:** https://github.com/BrLira/baozi-store-api

## Tecnologias

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- Maven

## Como executar

1. Instale o Java 17 e o Maven.
2. Abra um terminal na pasta do projeto.
3. Execute `mvn spring-boot:run`.
4. A API ficara disponivel em `http://localhost:8080/api`.
5. Importe o arquivo `postman/Baozi_Store_API.postman_collection.json` no Postman.
6. Execute as requisicoes na ordem numerada.

## Endpoints

| Entidade | Criar | Listar | Consultar | Atualizar | Apagar |
| --- | --- | --- | --- | --- | --- |
| Cliente | `POST /api/clientes` | `GET /api/clientes` | `GET /api/clientes/{id}` | `PUT /api/clientes/{id}` | `DELETE /api/clientes/{id}` |
| Produto | `POST /api/produtos` | `GET /api/produtos` | `GET /api/produtos/{id}` | `PUT /api/produtos/{id}` | `DELETE /api/produtos/{id}` |
| Pedido | `POST /api/pedidos` | `GET /api/pedidos` | `GET /api/pedidos/{id}` | `PUT /api/pedidos/{id}` | `DELETE /api/pedidos/{id}` |

## Banco H2

O banco funciona em memoria e e criado automaticamente ao iniciar o projeto. O console pode ser acessado em `http://localhost:8080/h2-console` usando:

- JDBC URL: `jdbc:h2:mem:baozistore`
- Usuario: `sa`
- Senha: deixe em branco

## Testes automatizados

Execute `mvn test`. O teste de integracao cria cliente, produto e pedido, realiza consultas e apaga os registros.
