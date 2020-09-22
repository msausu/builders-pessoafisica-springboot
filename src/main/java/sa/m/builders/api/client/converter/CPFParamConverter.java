package sa.m.builders.api.client.converter;

import sa.m.builders.api.client.model.CPF;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author msa
 */
@Component
@ConfigurationPropertiesBinding
class CPFParamConverter implements Converter<String, CPF> {

    @Override
    public CPF convert(String cpf) {
        return new CPF(cpf);
    }
}
