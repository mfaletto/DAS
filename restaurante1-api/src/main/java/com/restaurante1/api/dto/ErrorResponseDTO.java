package com.restaurante1.api.dto;

import java.util.List;

public record ErrorResponseDTO(
    String error,
    Integer nroClick,
    List<String> details
) {
    public ErrorResponseDTO(String error, List<String> details) {
        this(error, null, details);
    }
    
    public ErrorResponseDTO(String error, Integer nroClick) {
        this(error, nroClick, null);
    }
}
