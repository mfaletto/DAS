package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.bean.ClickBean;
import ar.edu.ubp.das.ristorinobackend.bean.ContenidoBean;
import ar.edu.ubp.das.ristorinobackend.bean.ContenidoIdBean;
import ar.edu.ubp.das.ristorinobackend.component.UrlHasher;
import ar.edu.ubp.das.ristorinobackend.dto.ClickRequestDTO;
import ar.edu.ubp.das.ristorinobackend.repository.ClicksContenidosRepository;
import ar.edu.ubp.das.ristorinobackend.repository.ContenidosRestaurantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class PromocionService {

    @Value("${ristorino.portal.nro:1}")
    private Integer defaultPortalId;

    @Value("${ristorino.portal.idioma:1}")
    private Integer defaultIdiomaId;

    @Autowired
    private ContenidosRestaurantesRepository contenidosRepo;

    @Autowired
    private ClicksContenidosRepository clicksRepo;

    @Autowired
    private UrlHasher urlHasher;

    private final Map<String, String> mapaImagenesPorHash = new ConcurrentHashMap<>();

    public List<ContenidoBean> getPromociones() {
        List<ContenidoBean> promociones = cargarPromociones();
        return promociones.stream()
                .map(this::protegerImagen)
                .collect(Collectors.toList());
    }

    public void registrarClic(ClickRequestDTO clickDTO) {
        ContenidoIdBean contenidoId = new ContenidoIdBean();
        contenidoId.setNroRestaurante(clickDTO.getNroRestaurante());
        contenidoId.setNroIdioma(clickDTO.getNroIdioma());
        contenidoId.setNroContenido(clickDTO.getNroContenido());

        Optional<ContenidoBean> promocionOpt = contenidosRepo.findById(contenidoId);
        ContenidoBean promocion = promocionOpt.orElseThrow(() -> new IllegalArgumentException("Contenido no encontrado"));

        ClickBean nuevoClic = new ClickBean();
        nuevoClic.setNroCliente(clickDTO.getNroCliente());
        nuevoClic.setNroRestaurante(clickDTO.getNroRestaurante());
        nuevoClic.setNroIdioma(clickDTO.getNroIdioma());
        nuevoClic.setNroContenido(clickDTO.getNroContenido());
        nuevoClic.setFechaHoraRegistro(LocalDateTime.now());
        nuevoClic.setCostoClick(promocion.getCostoClick());

        if (clicksRepo.existeClickReciente(nuevoClic)) {
            return; // Evita monetizar múltiples veces con F5
        }

        clicksRepo.registrarClick(nuevoClic);
    }

    private ContenidoBean protegerImagen(ContenidoBean contenido) {
        String hash = urlHasher.hash(contenido.getImagenPromocional());
        contenido.setImagenPromocionalHash(hash);
        contenido.setImagenPromocionalUrl(urlHasher.buildPublicUrl(hash));
        mapaImagenesPorHash.put(hash, contenido.getImagenPromocional());
        contenido.setImagenPromocional(null);
        return contenido;
    }

    public Optional<String> obtenerUrlImagenPorHash(String hash) {
        String url = mapaImagenesPorHash.get(hash);
        if (url != null) {
            return Optional.of(url);
        }
        cargarPromociones().forEach(this::protegerImagen);
        return Optional.ofNullable(mapaImagenesPorHash.get(hash));
    }

    private List<ContenidoBean> cargarPromociones() {
        List<ContenidoBean> promociones = contenidosRepo.findPromocionesDisponibles(defaultPortalId, defaultIdiomaId);
        mapaImagenesPorHash.clear();
        return promociones;
    }
}