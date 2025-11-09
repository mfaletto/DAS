package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Le indica a Spring que esto es un Repositorio (un bean de acceso a datos)
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // 💡 JpaRepository<ClaseEntidad, TipoDeLaID>
    //
    // ¡Eso es todo!
    // Al extender JpaRepository, automáticamente heredamos métodos como:
    // .save(cliente)      -> Inserta o actualiza un cliente
    // .findById(id)      -> Busca un cliente por su nro_cliente
    // .findAll()         -> Trae todos los clientes
    // .deleteById(id)    -> Borra un cliente
}