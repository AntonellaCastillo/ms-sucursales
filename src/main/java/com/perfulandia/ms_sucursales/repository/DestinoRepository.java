package com.perfulandia.ms_sucursales.repository;

import com.perfulandia.ms_sucursales.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// CAPA REPOSITORY de Destino - habla con la BD, hereda el CRUD de JpaRepository
@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {

    // Consulta personalizada: listar los destinos de una ruta, ordenados por "orden".
    // Spring Data arma el SQL solo: busca por el idRuta de la ruta y ordena por orden ascendente.
    List<Destino> findByRutaIdRutaOrderByOrdenAsc(Long idRuta);
}