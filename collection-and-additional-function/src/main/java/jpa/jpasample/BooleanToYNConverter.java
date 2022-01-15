package jpa.jpasample;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)    // 글로벌 설정 (모든 Boolean 타입에 컨버터 적용)
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String daData) {
        return "Y".equals(daData);
    }
}
