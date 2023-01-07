package com.myboard.domain;

import jakarta.persistence.AttributeConverter;

public class BooleanToYNConverter implements AttributeConverter<Boolean, String>{
    // @return String / true인 경우 Y, false인 경우 N
    
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }
    
    //@return Boolean / 대소문자 상관없이 Y인 경우 true, N인 경우 false
    @Override
    public Boolean convertToEntityAttribute(String yn) {
        return "Y".equalsIgnoreCase(yn);
    }
}
