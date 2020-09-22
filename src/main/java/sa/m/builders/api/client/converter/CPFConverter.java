package sa.m.builders.api.client.converter;

import sa.m.builders.api.client.model.CPF;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author msa
 */
@Converter
public class CPFConverter implements AttributeConverter<CPF, Long> {

    @Override
    public Long convertToDatabaseColumn(CPF cpf) {
        return cpf.asNumber();
    }

    @Override
    public CPF convertToEntityAttribute(Long cpf) {
        return new CPF(String.format("%011d", cpf), false);
    }
}
