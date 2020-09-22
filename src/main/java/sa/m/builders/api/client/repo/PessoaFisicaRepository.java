package sa.m.builders.api.client.repo;

/**
 *
 * @author msa
 */
import static sa.m.builders.api.client.PessoaFisicaRestServiceApplication.PESSOA_FISICA_API_BASE;
import static sa.m.builders.api.client.PessoaFisicaRestServiceApplication.PESSOA_FISICA_COLLETION_NAME;
import sa.m.builders.api.client.model.PessoaFisica;
import sa.m.builders.api.client.model.CPF;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = PESSOA_FISICA_COLLETION_NAME, path = PESSOA_FISICA_API_BASE)
public interface PessoaFisicaRepository extends PagingAndSortingRepository<PessoaFisica, Long> {

    @Override
    PessoaFisica save(PessoaFisica pessoa);
    
    @RestResource(path = "buscaPorNome", rel = "porNome")
    List<PessoaFisica> findByNome(@Param("nome") String nome);

    @RestResource(path = "buscaPorCpf", rel = "porCPF")    
    PessoaFisica findByCpf(@Param("cpf") CPF cpf);

}