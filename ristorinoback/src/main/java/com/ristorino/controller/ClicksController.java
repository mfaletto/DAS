package com.ristorino.controller;

import com.ristorino.dto.ClickRequest;
import com.ristorino.service.ClicksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ClicksController {

    private final ClicksService clicksService;

    public ClicksController(ClicksService service) {
        this.clicksService = service;
    }

    @PostMapping("/clicks")
    public ResponseEntity<?> registrarClick(@RequestBody ClickRequest req) {
        clicksService.registrarClick(req);
        return ResponseEntity.ok().build();
    }
}
