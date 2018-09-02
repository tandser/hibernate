package ru.tandser.hibernate;

import ru.tandser.hibernate.models.Amount;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AmountConverter implements AttributeConverter<Amount, String> {

    @Override
    public String convertToDatabaseColumn(Amount attribute) {
        return attribute.toString();
    }

    @Override
    public Amount convertToEntityAttribute(String dbData) {
        return Amount.valueOf(dbData);
    }
}