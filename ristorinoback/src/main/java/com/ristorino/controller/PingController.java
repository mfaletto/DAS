// controller/PingController.java
package com.ristorino.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PingController {
    @GetMapping("/ping")
    public String ping() { return "pong"; }
}
