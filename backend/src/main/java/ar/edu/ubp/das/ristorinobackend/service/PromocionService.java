package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.dto.ClickRequestDTO;
import ar.edu.ubp.das.ristorinobackend.dto.RegistroRequest;
import ar.edu.ubp.das.ristorinobackend.entity.ClicksContenidosRestaurantes;
import ar.edu.ubp.das.ristorinobackend.entity.Cliente;
import ar.edu.ubp.das.ristorinobackend.entity.ContenidosRestaurantes;
import ar.edu.ubp.das.ristorinobackend.entity.ContenidosRestaurantesId;
import ar.edu.ubp.das.ristorinobackend.repository.ClicksContenidosRepository;
import ar.edu.ubp.das.ristorinobackend.repository.ClienteRepository;
import ar.edu.ubp.das.ristorinobackend.repository.ContenidosRestaurantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // ⬅️ Marca esta clase como un Servicio de Spring
public class PromocionService {

    // 1. Inyectamos los repositorios que creamos en la Fase 1
    @Autowired
    private ContenidosRestaurantesRepository contenidosRepo;

    @Autowired
    private ClicksContenidosRepository clicksRepo;

    @Autowired
    private ClienteRepository clienteRepo;
    /**
     * Lógica para el Endpoint: GET /api/v1/promociones
     * (Requisito: Visualizar promociones)
     * * Simplemente busca y devuelve todas las promociones
     * que insertamos en la base de datos.
     */
    public List<ContenidosRestaurantes> getPromociones() {
        return contenidosRepo.findAll();
    }

    /**
     * Lógica para el Endpoint: POST /api/v1/promociones/clic
     * (Requisito: Registrar clic para monetización)
     *
     * Esta es la lógica clave del entregable.
     */
    public void registrarClic(ClickRequestDTO clickDTO) {
        // 1. Crear el ID compuesto de la promoción que se clickeó
        ContenidosRestaurantesId contenidoId = new ContenidosRestaurantesId();
        contenidoId.setNroRestaurante(clickDTO.getNroRestaurante());
        contenidoId.setNroIdioma(clickDTO.getNroIdioma());
        contenidoId.setNroContenido(clickDTO.getNroContenido());

        // 2. Buscar la promoción en la DB para obtener su costo
        Optional<ContenidosRestaurantes> promocionOpt = contenidosRepo.findById(contenidoId);
        if (!promocionOpt.isPresent()) {
            // Si no se encuentra la promoción, lanzamos un error
            throw new RuntimeException("Contenido (promoción) no encontrado");
        }
        ContenidosRestaurantes promocion = promocionOpt.get();

        // 3. Crear el nuevo registro de Clic
        ClicksContenidosRestaurantes nuevoClic = new ClicksContenidosRestaurantes();

        nuevoClic.setContenido(promocion); // Asigna la promoción
        nuevoClic.setFechaHoraRegistro(LocalDateTime.now()); // Setea la fecha y hora actual
        nuevoClic.setCostoClick(promocion.getCostoClick()); // Copia el costo desde la promoción
        nuevoClic.setNotificado(false); // Cumple el requisito ("sin notificaciones inmediatas")

        // (Opcional) Asignar cliente si estuviera logueado. Por ahora es NULL.
        // nuevoClic.setCliente(cliente);

        // 4. Guardar el nuevo clic en la base de datos
        clicksRepo.save(nuevoClic);
    }
    public void registrarCliente(RegistroRequest request) {
        Cliente nuevoCliente = new Cliente();

        // ⚠️ Buena Práctica: Aquí DEBERÍA encriptarse la clave (ej: BCrypt)
        // Por ahora, la guardamos en texto plano para la prueba.

        nuevoCliente.setNombre(request.getNombre());
        nuevoCliente.setApellido("N/A"); // El mockup no pide apellido, lo seteamos N/A
        nuevoCliente.setCorreo(request.getCorreo());
        nuevoCliente.setClave(request.getClave());
        nuevoCliente.setTelefonos(request.getTelefonos());
        nuevoCliente.setNroLocalidad(request.getNroLocalidad());
        nuevoCliente.setHabilitado(true);

        clienteRepo.save(nuevoCliente);
    }
}