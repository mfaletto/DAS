package com.restaurante1.api.service;

import com.restaurante1.api.dto.ClickNotificacionDTO;
import com.restaurante1.api.dto.NotificacionResponseDTO;
import com.restaurante1.api.repository.ClicksNotificadosRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificacionesClicksService {

    private final ClicksNotificadosRepository repository;

    public NotificacionesClicksService(ClicksNotificadosRepository repository) {
        this.repository = repository;
    }

    public NotificacionResponseDTO procesarNotificacion(ClickNotificacionDTO click) {
        repository.insertar(click);
        
        return new NotificacionResponseDTO(
            "RECIBIDO",
            click.nroClick(),
            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}
