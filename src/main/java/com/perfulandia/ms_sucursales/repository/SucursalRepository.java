package com.perfulandia.ms_sucursales.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.ms_sucursales.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    // Buscar sucursal por nombre
    List<Sucursal> findByNombre(String nombre);

    // Buscar sucursales por ciudad
    List<Sucursal> findByCiudad(String ciudad);
}