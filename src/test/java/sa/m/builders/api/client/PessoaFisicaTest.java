package sa.m.builders.api.client;

import sa.m.builders.api.client.model.CPF;
import sa.m.builders.api.client.model.PessoaFisica;
import sa.m.builders.api.client.repo.PessoaFisicaRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = { PessoaFisicaRestServiceApplication.class, JpaConfiguration.class })
@AutoConfigureRestDocs
public class PessoaFisicaTest {

    @Autowired
    PessoaFisicaRepository repo;

    @Test
    void findFirstPageOfPessoaFisica() {
        PessoaFisica[] data = new PessoaFisica[] {
            PessoaFisicaSample.PEDRO.instance(),
            PessoaFisicaSample.PEDRO_ALVARES.instance(),
            PessoaFisicaSample.ANDRE_DAS_NEVES.instance(),
            PessoaFisicaSample.RICARDO_MAGNO_SOBRINHO.instance()
        };
        Arrays.stream(data).forEach(p -> repo.save(p));
        Page<PessoaFisica> pessoas = this.repo.findAll(PageRequest.of(0, 10));
        assertThat(pessoas.getTotalElements()).isGreaterThan(data.length - 1);
    }

    @Test
    void findByName() {
        PessoaFisica[] data = new PessoaFisica[] {
            PessoaFisicaSample.CARLOS_X_1.instance(),
            PessoaFisicaSample.CARLOS_X_2.instance()
        };
        Arrays.stream(data).forEach(p -> repo.save(p));
        List<PessoaFisica> pessoas = this.repo.findByNome(PessoaFisicaSample.CARLOS_X_1.nome());
        assertThat(pessoas).isNotNull();
        assertThat(pessoas.size()).isEqualTo(data.length);
    }

    @Test
    void findByCPF() {
        repo.save(PessoaFisicaSample.REGINA.instance());
        PessoaFisica pessoa = this.repo.findByCpf(new CPF(PessoaFisicaSample.REGINA.cpf()));
        assertThat(pessoa.getNome()).isEqualTo(PessoaFisicaSample.REGINA.nome());
    }

}
