package sa.m.builders.api.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author msa
 */
@Configuration
@EnableJpaRepositories(basePackages = { "io.platformbuilders.sample.api" })
@PropertySource("persistence-test.properties")
@Profile("test")
@EnableTransactionManagement
public class JpaConfiguration {
}
