package com.perfulandia.ms_sucursales.repository;

import com.perfulandia.ms_sucursales.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// CAPA REPOSITORY de Ruta - habla con la BD, hereda el CRUD de JpaRepository
@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    // Consulta personalizada (HU-56): listar solo las rutas de un transportista.
    // Spring Data crea el SQL solo a partir del nombre del método.
    List<Ruta> findByIdTransportista(Long idTransportista);
}