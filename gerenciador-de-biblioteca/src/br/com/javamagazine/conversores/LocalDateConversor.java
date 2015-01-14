package br.com.javamagazine.conversores;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConversor implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate dataDaEntidade) {
        return (dataDaEntidade == null) ? null : Date.valueOf(dataDaEntidade);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dataDoBancoDeDados) {
        return (dataDoBancoDeDados == null) ? null : dataDoBancoDeDados.toLocalDate();
    }
}