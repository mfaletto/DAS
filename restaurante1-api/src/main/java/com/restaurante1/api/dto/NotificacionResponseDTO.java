package com.restaurante1.api.dto;

public record NotificacionResponseDTO(
    String status,
    Integer nroClick,
    String fechaHoraRecibido
) {}
