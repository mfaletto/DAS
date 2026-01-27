package ar.edu.ubp.das.ristorinobackend.controller;

import ar.edu.ubp.das.ristorinobackend.bean.ClickBean;
import ar.edu.ubp.das.ristorinobackend.repository.ClicksContenidosRepository;
import ar.edu.ubp.das.ristorinobackend.service.ProcesoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificacionController {

    @Autowired
    private ProcesoNotificacionService procesoNotificacionService;

    @Autowired
    private ClicksContenidosRepository clicksRepository;

    @GetMapping("/pendientes")
    public ResponseEntity<List<ClickBean>> obtenerPendientes() {
        List<ClickBean> pendientes = clicksRepository.obtenerClicksPendientes();
        return ResponseEntity.ok(pendientes);
    }

    @PostMapping("/procesar")
    public ResponseEntity<ProcesoNotificacionService.ProcesoNotificacionResult> procesarPendientes() {
        ProcesoNotificacionService.ProcesoNotificacionResult resultado =
                procesoNotificacionService.procesarClicsPendientes();
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/marcar/{id}")
    public ResponseEntity<Void> marcarNotificado(@PathVariable("id") Integer nroClick) {
        ClickBean bean = new ClickBean();
        bean.setNroClick(nroClick);
        bean.setNotificado(true);
        clicksRepository.actualizarEstadoNotificado(bean);
        return ResponseEntity.ok().build();
    }
}


