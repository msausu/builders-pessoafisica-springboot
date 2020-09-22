** spec **

1. Projeto Java Spring Boot (utilizando módulos necessários):
  - API REST;
  - CRUD de Cliente (id, nome, CPF, dataNascimento); 
  - o CRUD deve possuir na API GET, POST, DELETE, PATCH e PUT.
2. A API de GET deve aceitar "query strings" para pesquisar os clientes por CPF e nome; 
3. É necessário que na API os clientes voltem paginados;
4. O cliente possui a idade calculada considerando a data de nascimento;
5. Na há um Postman para apreciação da api.

---

## Opções

1. Maven;
2. Modulos Spring Boot: `RepositoryRestResource`, `AutoConfigureRestDocs`;
3. Testes unitários (API) usando `junit` e `restassured` (restassured.io);
4. A documentação da aplicação, testes efetuados e serviços encontra-se em `target/site` (gerado via `mvn site:site`);
5. Os testes `Postman` são gerados via `Spring Docs` e convertidos para `Postman` via `restdocs-to-postman`;
6. A execução dos testes ocorre via `newman` em um shell script `run-postman-tests.sh`;

---

## Geração dos artefatos

`bash generate-artifact.sh`

ou

`mvn clean test package site:site`

---

## Testes postman

`bash run-postman-tests.sh`

![Testes Postman](./doc/postman-pessoa-fisica.gif)

