package com.ristorino.service;

import com.ristorino.dto.ClickRequest;
import com.ristorino.repository.ClicksRepository;
import org.springframework.stereotype.Service;

@Service
public class ClicksService {
    private final ClicksRepository repo;

    public ClicksService(ClicksRepository repo) {
        this.repo = repo;
    }

    public void registrarClick(ClickRequest req) {
        repo.insertarClick(req);
    }
}
