package com.ristorino.repository;

import com.ristorino.dto.RegistroRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class AuthRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public AuthRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insertarClienteBasico(RegistroRequest r) {
        String sql = """
            INSERT INTO dbo.clientes
              (nombre, apellido, correo, clave_hash, telefonos)
            VALUES
              (:nombre, :apellido, :correo, :clave, :telefonos)
        """;
        // Para la entrega: guarda la clave en texto o aplica un hash simple si querés (BCrypt más adelante)
        jdbc.update(sql, Map.of(
                "nombre", r.getNombre(),
                "apellido", r.getApellido(),
                "correo", r.getCorreo(),
                "clave", r.getClave(),
                "telefonos", r.getTelefonos()
        ));
    }
}
