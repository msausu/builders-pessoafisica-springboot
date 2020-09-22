package sa.m.builders.api.client;

import static sa.m.builders.api.client.PessoaFisicaRestServiceApplication.PESSOA_FISICA_API_BASE;
import static sa.m.builders.api.client.PessoaFisicaRestServiceApplication.PESSOA_FISICA_COLLETION_NAME;
import sa.m.builders.api.client.model.CPF;
import sa.m.builders.api.client.model.PessoaFisica;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.IntStream;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import org.springframework.restdocs.restassured3.RestDocumentationFilter;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureRestDocs
public class PessoaFisicaDocumentedTest {

    @Value("${local.server.port}")
    private int port;

    @Value("http://localhost")
    private String host;

    @Autowired
    private RequestSpecification spec;

    @Test
    public void createSearchDeleteOkTest() {
        PessoaFisica pessoa = PessoaFisicaSample.TAMARA.instance();
        String nome = pessoa.getNome();
        CPF cpf = new CPF(pessoa.getCpf());
        given(spec)
                .filter(did(0, "pessoa-fisica-created"))
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when().post(base())
                .then().statusCode(HttpStatus.CREATED.value());
        given(spec)
                .filter(did(1, "pessoa-fisica-search-by-cpf"))
                .when().get(base() + "/search/buscaPorCpf?cpf=" + cpf)
                .then().assertThat().body("nome", equalTo(nome)
                );
        given(spec)
                .filter(did(2, "pessoa-fisica-search-by-nome"))
                .when().get(base() + "/search/buscaPorNome?nome=" + nome)
                .then().assertThat().body(jsonPathPessoaFisica() + "[0].cpf", equalTo(cpf.print())
                );
        String id = given(spec)
                .get(base() + "/search/buscaPorCpf?cpf=" + cpf)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract()
                .path("_links.self.href");;
        given(spec)
                .filter(did(3, "pessoa-fisica-deleted"))
                .when().delete(id)
                .then().assertThat().statusCode(HttpStatus.NO_CONTENT.value());
        given(spec)
                .filter(did(4, "pessoa-fisica-inexistent"))
                .when().get(base() + "/search/buscaPorCpf?cpf=" + cpf)
                .then().assertThat().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void modifyOkTest() {
        PessoaFisica pessoa = PessoaFisicaSample.ALBERTO.instance();
        final String nomeCriado = pessoa.getNome(), nomeAlterado = "Marcos";
        final String cpf = pessoa.getCpf();
        final Calendar dataNascimentoCriado = pessoa.getDataNascimento(), dataNascimentoAlterado = PessoaFisicaSample.cal(1998,1,1);
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
        String dataNascimentoPatch = "{\"dataNascimento\":" + "\"" + data.format(dataNascimentoAlterado.getTime()) + "\"}";
        given(spec)
                .filter(did(5, "pessoa-fisica-created"))
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when().post(base())
                .then().statusCode(HttpStatus.CREATED.value());
        String id = given(spec)
                .get(base() + "/search/buscaPorCpf?cpf=" + cpf)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .extract()
                .path("_links.self.href");
        pessoa.setNome(nomeAlterado);
        given(spec)
                .filter(did(6, "pessoa-fisica-modified"))
                .contentType(ContentType.JSON)
                .body(pessoa)
                .when().put(id)
                .then().assertThat().statusCode(HttpStatus.OK.value());
        given(spec)
                .filter(did(7, "pessoa-fisica-modified-check"))
                .when().get(base() + "/search/buscaPorCpf?cpf=" + cpf)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("nome", Matchers.equalTo(nomeAlterado))
                .and()
                .body("dataNascimento", Matchers.equalTo(data.format(dataNascimentoCriado.getTime())));
        given(spec)
                .filter(did(8, "pessoa-fisica-modified"))
                .baseUri(id)
                .contentType(ContentType.JSON)
                .body(dataNascimentoPatch)
                .when().patch()
                .then().assertThat().statusCode(HttpStatus.OK.value());
        given(spec)
                .filter(did(9, "pessoa-fisica-modified-check"))
                .when().get(base() + "/search/buscaPorCpf?cpf=" + cpf)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("dataNascimento", Matchers.equalTo(data.format(dataNascimentoAlterado.getTime())));
    }

    @Test
    public void pagedSearchTest() {
        final PessoaFisica[] data = new PessoaFisica[]{
            PessoaFisicaSample.XAVIER.instance(),
            PessoaFisicaSample.ROBERTO.instance(),
            PessoaFisicaSample.ADRIANA.instance(),
            PessoaFisicaSample.CAROL.instance()
        };
        final int pageSize = 3, pageNumber = 1;
        IntStream.range(0, data.length).forEach(i -> {
            given(spec)
                    .filter(did(10 + i, "pessoa-fisica-created"))
                    .contentType(ContentType.JSON)
                    .body(data[i])
                    .when().post(base())
                    .then().statusCode(HttpStatus.CREATED.value());
        });
        given(spec)
                .filter(did(14, "pessoa-fisica-paged-request"))
                .when().get(base() + "?page=" + pageNumber + "&size=" + pageSize)
                .then().assertThat().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("page.size", Matchers.equalTo(pageSize))
                .and()
                .body("page.number", Matchers.equalTo(pageNumber))
                .and()
                .body("page.totalElements", Matchers.greaterThan(data.length));
    }

    @Test
    public void createNOKTest() {
        final PessoaFisica[] bad = new PessoaFisica[]{
            PessoaFisicaSample.NAME_BAD_1.instance(),
            PessoaFisicaSample.NAME_BAD_2.instance(),
            PessoaFisicaSample.NAME_BAD_3.instance()
        };
        final PessoaFisica[] conflict = new PessoaFisica[]{
            PessoaFisicaSample.JOANA.instance(), // SAME CPF
            PessoaFisicaSample.JOANA.instance() // SAME CPF
        };
        final PessoaFisica[] err = new PessoaFisica[]{
            PessoaFisicaSample.TADEU.instance() // TOO OLD
        };
        IntStream.range(0, bad.length).forEach(i -> {
            given(spec)
                    .filter(did(100 + i, "pessoa-fisica-nok-created"))
                    .contentType(ContentType.JSON)
                    .body(bad[i])
                    .when().post(base())
                    .then().statusCode(HttpStatus.valueOf(500).value());
        });
        IntStream.range(0, conflict.length).forEach(i -> {
            given(spec)
                    .filter(did(120 + i, "pessoa-fisica-nok-created"))
                    .contentType(ContentType.JSON)
                    .body(conflict[i])
                    .when().post(base())
                    .then().statusCode(i == 0 ? HttpStatus.CREATED.value() : HttpStatus.CONFLICT.value());
        });
        IntStream.range(0, err.length).forEach(i -> {
            given(spec)
                    .filter(did(130 + i, "pessoa-fisica-nok-created"))
                    .contentType(ContentType.JSON)
                    .body(err[i])
                    .when().post(base())
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        });
    }

    @Test
    public void searchNOKTest() {
        given(spec)
                .filter(did(200, "pessoa-fisica-nok-search"))
                .contentType(ContentType.JSON)
                .when().get(base() + "/search/buscaPorCpf?cpf=abc") // BAD FORMAT
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
        given(spec)
                .filter(did(201, "pessoa-fisica-nok-search"))
                .contentType(ContentType.JSON)
                .when().get(base() + "/search/buscaPorCpf?cpf=684.792.573-00") // BAD CHECK
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private String base() {
        return host + "/" + PESSOA_FISICA_API_BASE;
    }

    private String jsonPathPessoaFisica() {
        return "_embedded." + PESSOA_FISICA_COLLETION_NAME;
    }

    private RestDocumentationFilter did(int documentCounter, String it) {
        return document(String.format("%02d-", documentCounter) + it);
    }
}
