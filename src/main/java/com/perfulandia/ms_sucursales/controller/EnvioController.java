package com.perfulandia.ms_sucursales.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.perfulandia.ms_sucursales.model.Envio;
import com.perfulandia.ms_sucursales.model.EstadoEnvio;
import com.perfulandia.ms_sucursales.service.EnvioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/envios")
public class EnvioController {

    private static final Logger log = LoggerFactory.getLogger(EnvioController.class);

    @Autowired
    private EnvioService envioService;

    // GET — Listar todos los envíos
    @GetMapping
    public ResponseEntity<?> getEnvios() {
        log.info("GET /api/v1/envios - Listar todos los envios");
        List<Envio> lista = envioService.findAll();
        if (lista.isEmpty()) {
            log.warn("No hay envios registrados");
            return ResponseEntity.status(404).body("No hay envios registrados");
        }
        return ResponseEntity.ok(lista);
    }

    // GET — Buscar envío por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getEnvioById(@PathVariable Long id) {
        log.info("GET /api/v1/envios/{} - Buscar envio por id", id);
        return envioService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Envio con id {} no encontrado", id);
                    return ResponseEntity.status(404).build();
                });
    }

    // GET — Buscar envío por pedido
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<?> getEnvioByPedido(@PathVariable Long idPedido) {
        log.info("GET /api/v1/envios/pedido/{} - Buscar envio por pedido", idPedido);
        return envioService.findByIdPedido(idPedido)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("No hay envio para el pedido {}", idPedido);
                    return ResponseEntity.status(404).build();
                });
    }

    // GET — Buscar envíos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> getEnviosByEstado(@PathVariable EstadoEnvio estado) {
        log.info("GET /api/v1/envios/estado/{} - Buscar envios por estado", estado);
        List<Envio> lista = envioService.findByEstado(estado);
        if (lista.isEmpty()) {
            log.warn("No hay envios con estado {}", estado);
            return ResponseEntity.status(404).body("No hay envios con ese estado");
        }
        return ResponseEntity.ok(lista);
    }

    // GET — Buscar envíos por sucursal
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<?> getEnviosBySucursal(@PathVariable Long idSucursal) {
        log.info("GET /api/v1/envios/sucursal/{} - Buscar envios por sucursal", idSucursal);
        List<Envio> lista = envioService.findBySucursal(idSucursal);
        if (lista.isEmpty()) {
            log.warn("No hay envios de la sucursal {}", idSucursal);
            return ResponseEntity.status(404).body("No hay envios de esa sucursal");
        }
        return ResponseEntity.ok(lista);
    }

    // POST — Crear envío
    @PostMapping
    public ResponseEntity<?> postEnvio(@Valid @RequestBody Envio envio) {
        log.info("POST /api/v1/envios - Crear nuevo envio");
        Envio nuevo = envioService.save(envio);
        log.info("Envio creado con id: {}", nuevo.getIdEnvio());
        return ResponseEntity.status(201).body(nuevo);
    }

    // PUT — Actualizar estado del envío
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id,
            @RequestParam EstadoEnvio estado) {
        log.info("PUT /api/v1/envios/{}/estado - Actualizar estado a {}", id, estado);
        try {
            return envioService.actualizarEstado(id, estado)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        log.warn("Envio con id {} no encontrado", id);
                        return ResponseEntity.status(404).build();
                    });
        } catch (RuntimeException e) {
            log.error("Error al actualizar estado del envio {}: {}", id, e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}