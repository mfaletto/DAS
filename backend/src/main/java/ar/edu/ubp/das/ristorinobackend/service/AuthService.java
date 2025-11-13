package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.bean.ClienteBean;
import ar.edu.ubp.das.ristorinobackend.dto.LoginRequest;
import ar.edu.ubp.das.ristorinobackend.dto.LoginResponse;
import ar.edu.ubp.das.ristorinobackend.dto.RegistroRequest;
import ar.edu.ubp.das.ristorinobackend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public void registrar(RegistroRequest request) {
        clienteRepository.findByCorreo(request.getCorreo()).ifPresent(existing -> {
            throw new IllegalArgumentException("El correo ya está registrado");
        });

        ClienteBean nuevoCliente = new ClienteBean();
        nuevoCliente.setNombre(request.getNombre());
        String apellido = request.getApellido();
        nuevoCliente.setApellido(apellido != null && !apellido.isBlank() ? apellido : "N/A");
        nuevoCliente.setCorreo(request.getCorreo());
        nuevoCliente.setTelefonos(request.getTelefonos());
        nuevoCliente.setNroLocalidad(request.getNroLocalidad());
        nuevoCliente.setHabilitado(Boolean.TRUE);
        nuevoCliente.setClaveHash(passwordEncoder.encode(request.getClave()));

        clienteRepository.registrar(nuevoCliente);
    }

    public LoginResponse login(LoginRequest request) {
        ClienteBean cliente = clienteRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getClave(), cliente.getClaveHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtService.generarToken(
                cliente.getCorreo(),
                Map.of(
                        "nroCliente", cliente.getNroCliente(),
                        "rol", "USER"
                )
        );

        long expiresIn = jwtService.getTtlMinutes() * 60;
        return new LoginResponse(token, expiresIn);
    }
}
