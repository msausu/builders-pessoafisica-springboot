# builders-pessoafisica-springboot
very simple API for brazilian individuals

1. Java Spring Boot project (using necessary modules):
  - REST API;
  - PessoaFísica/Customer CRUD (id, nome/name, CPF, dataNascimento/birthDate);
  - CRUD must have API GET, POST, DELETE, PATCH and PUT.
2. The GET API must accept "query strings" to search for customers by CPF and name;
3. Client response should be paged;
4. The customer has the age calculated considering the date of birth;
5. At the root of the project, there must be a Postman to assess the api.

---

## Options

1. Maven;
2. Spring Boot modules: `RepositoryRestResource`,` AutoConfigureRestDocs`;
3. Unit tests (API) using `junit` and` restassured` (restassured.io);
4. The application ![documentation](doc/site/index.html), ![tests performed](doc/site/surefire-report.html) and ![services](doc/generated-docs/rest-api-cliente.html) can be found in `target/site` (generated via` mvn site: site`);
5. `Postman` tests are generated via` Spring Docs` and converted to `Postman` via` restdocs-to-postman`;
6. The tests are run via `newman` in a` run-postman-tests.sh` shell script.

---

## Generation of artifacts

`bash generate-artifact.sh`

or

`mvn clean site`

---

## Postman testing

`bash run-postman-tests.sh`

![Postman tests](doc/postman-pessoa-fisica.gif)
