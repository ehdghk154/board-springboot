package com.myboard.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String>{
    /**
     * Boolean 값을 Y 또는 N으로 변환
     * true인 경우 Y, false인 경우 N
     */
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return(attribute != null && attribute) ? "Y" : "N";
    }
    
    /**
     * Y 또는 N을 Boolean으로 변환
     * 대소문자 상관X
     * Y인 경우 true, N인 경우 false
     */
    public Boolean convertToEntityAttribute(String  yn) {
        return "Y".equalsIgnoreCase(yn);
    }
}
