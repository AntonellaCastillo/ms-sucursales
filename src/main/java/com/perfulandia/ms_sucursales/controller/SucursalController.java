package com.perfulandia.ms_sucursales.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.ms_sucursales.model.Sucursal;
import com.perfulandia.ms_sucursales.service.SucursalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    private static final Logger log = LoggerFactory.getLogger(SucursalController.class);

    @Autowired
    private SucursalService sucursalService;

    // GET — Listar todas las sucursales
    @GetMapping
    public ResponseEntity<?> getSucursales() {
        log.info("GET /api/v1/sucursales - Listar todas las sucursales");
        List<Sucursal> lista = sucursalService.findAll();
        if (lista.isEmpty()) {
            log.warn("No hay sucursales registradas");
            return ResponseEntity.status(404).body("No hay sucursales registradas");
        }
        return ResponseEntity.ok(lista);
    }

    // GET — Buscar sucursal por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getSucursalById(@PathVariable Long id) {
        log.info("GET /api/v1/sucursales/{} - Buscar sucursal por id", id);
        return sucursalService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Sucursal con id {} no encontrada", id);
                    return ResponseEntity.status(404).build();
                });
    }

    // GET — Buscar sucursales por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<?> getSucursalesByCiudad(@PathVariable String ciudad) {
        log.info("GET /api/v1/sucursales/ciudad/{} - Buscar sucursales por ciudad", ciudad);
        List<Sucursal> lista = sucursalService.findByCiudad(ciudad);
        if (lista.isEmpty()) {
            log.warn("No hay sucursales en la ciudad {}", ciudad);
            return ResponseEntity.status(404).body("No hay sucursales en esa ciudad");
        }
        return ResponseEntity.ok(lista);
    }

    // POST — Crear sucursal
    @PostMapping
    public ResponseEntity<?> postSucursal(@Valid @RequestBody Sucursal sucursal) {
        log.info("POST /api/v1/sucursales - Crear nueva sucursal");
        Sucursal nueva = sucursalService.save(sucursal);
        log.info("Sucursal creada con id: {}", nueva.getIdSucursal());
        return ResponseEntity.status(201).body(nueva);
    }

    // PUT — Actualizar sucursal
    @PutMapping("/{id}")
    public ResponseEntity<?> putSucursal(@PathVariable Long id,
            @Valid @RequestBody Sucursal sucursal) {
        log.info("PUT /api/v1/sucursales/{} - Actualizar sucursal", id);
        return sucursalService.actualizar(id, sucursal)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Sucursal con id {} no encontrada para actualizar", id);
                    return ResponseEntity.status(404).build();
                });
    }

    // DELETE — Eliminar sucursal
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSucursal(@PathVariable Long id) {
        log.info("DELETE /api/v1/sucursales/{} - Eliminar sucursal", id);
        if (sucursalService.eliminar(id)) {
            return ResponseEntity.ok("Sucursal eliminada correctamente");
        }
        log.warn("Sucursal con id {} no encontrada", id);
        return ResponseEntity.status(404).body("Sucursal no encontrada");
    }
}