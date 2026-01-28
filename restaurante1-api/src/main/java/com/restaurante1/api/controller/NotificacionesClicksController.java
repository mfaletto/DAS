package com.restaurante1.api.controller;

import com.restaurante1.api.dto.ClickNotificacionDTO;
import com.restaurante1.api.dto.NotificacionResponseDTO;
import com.restaurante1.api.service.NotificacionesClicksService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionesClicksController {

    private final NotificacionesClicksService service;

    public NotificacionesClicksController(NotificacionesClicksService service) {
        this.service = service;
    }

    @PostMapping("/clicks")
    public ResponseEntity<NotificacionResponseDTO> recibirClick(@Valid @RequestBody ClickNotificacionDTO click) {
        NotificacionResponseDTO response = service.procesarNotificacion(click);
        return ResponseEntity.ok(response);
    }
}
