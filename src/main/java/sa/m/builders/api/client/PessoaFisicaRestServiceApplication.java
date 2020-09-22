package sa.m.builders.api.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PessoaFisicaRestServiceApplication {

        public static final String 
                PESSOA_FISICA_API_BASE = "api", PESSOA_FISICA_COLLETION_NAME = "PESSOA_FISICA";
    
	public static void main(String[] args) {
		SpringApplication.run(PessoaFisicaRestServiceApplication.class, args);
	}

}
