package com.restaurante1.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ClickNotificacionDTO(
    @NotNull(message = "nroClick es requerido")
    @Positive(message = "nroClick debe ser mayor a 0")
    Integer nroClick,

    @NotNull(message = "nroRestaurante es requerido")
    @Positive(message = "nroRestaurante debe ser mayor a 0")
    Integer nroRestaurante,

    @NotNull(message = "nroIdioma es requerido")
    @Positive(message = "nroIdioma debe ser mayor a 0")
    Integer nroIdioma,

    @NotNull(message = "nroContenido es requerido")
    @Positive(message = "nroContenido debe ser mayor a 0")
    Integer nroContenido,

    @NotBlank(message = "fechaHoraRegistro es requerido")
    String fechaHoraRegistro,

    Integer nroCliente,

    BigDecimal costoClick,

    @NotNull(message = "notificado es requerido")
    Boolean notificado
) {}
