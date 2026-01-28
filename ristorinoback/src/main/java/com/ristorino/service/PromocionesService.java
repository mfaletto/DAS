package com.ristorino.service;

import com.ristorino.dto.PromocionDTO;
import com.ristorino.repository.PromocionesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromocionesService {
    private final PromocionesRepository repo;

    public PromocionesService(PromocionesRepository repo) {
        this.repo = repo;
    }

    public List<PromocionDTO> listarPromocionesVigentes() {
        return repo.listarPromocionesVigentes();
    }
}
