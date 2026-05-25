package com.perfulandia.ms_sucursales.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.ms_sucursales.model.Sucursal;
import com.perfulandia.ms_sucursales.repository.SucursalRepository;

@Service
public class SucursalService {

    private static final Logger log = LoggerFactory.getLogger(SucursalService.class);

    @Autowired
    private SucursalRepository sucursalRepository;

    // Listar todas las sucursales
    public List<Sucursal> findAll() {
        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll();
    }

    // Buscar sucursal por id
    public Optional<Sucursal> findById(Long id) {
        log.info("Buscando sucursal con id: {}", id);
        return sucursalRepository.findById(id);
    }

    // Buscar sucursales por ciudad
    public List<Sucursal> findByCiudad(String ciudad) {
        log.info("Buscando sucursales en ciudad: {}", ciudad);
        return sucursalRepository.findByCiudad(ciudad);
    }

    // Crear sucursal
    public Sucursal save(Sucursal sucursal) {
        log.info("Creando sucursal: {}", sucursal.getNombre());
        Sucursal guardada = sucursalRepository.save(sucursal);
        log.info("Sucursal creada con id: {}", guardada.getIdSucursal());
        return guardada;
    }

    // Actualizar sucursal
    public Optional<Sucursal> actualizar(Long id, Sucursal sucursalActualizada) {
        log.info("Actualizando sucursal con id: {}", id);
        return sucursalRepository.findById(id).map(sucursal -> {
            sucursal.setNombre(sucursalActualizada.getNombre());
            sucursal.setDireccion(sucursalActualizada.getDireccion());
            sucursal.setCiudad(sucursalActualizada.getCiudad());
            log.info("Sucursal {} actualizada correctamente", id);
            return sucursalRepository.save(sucursal);
        });
    }

    // Eliminar sucursal
    public boolean eliminar(Long id) {
        log.info("Eliminando sucursal con id: {}", id);
        if (sucursalRepository.existsById(id)) {
            sucursalRepository.deleteById(id);
            log.info("Sucursal {} eliminada correctamente", id);
            return true;
        }
        log.warn("Sucursal {} no encontrada para eliminar", id);
        return false;
    }
}