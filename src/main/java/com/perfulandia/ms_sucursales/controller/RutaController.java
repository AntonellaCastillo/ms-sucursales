package com.perfulandia.ms_sucursales.controller;

import com.perfulandia.ms_sucursales.model.Ruta;
import com.perfulandia.ms_sucursales.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;


// Controlador REST de Ruta
@RestController
@RequestMapping("/api/v1/rutas") // ruta base
public class RutaController {

     // Comunicacion con el Service
    @Autowired
    private RutaService rutaService;

    // POST: crear una ruta
    // POST: crear una ruta
    @PostMapping
    public ResponseEntity<Ruta> crearRuta(@Valid @RequestBody Ruta ruta) 
    {
        Ruta nueva = rutaService.guardarRuta(ruta);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED); // 201
    }

    // GET: listar todas las rutas
    @GetMapping
    public List<Ruta> obtenerTodas() 
    {
        return rutaService.listarRutas();
    }

    // GET por id: obtener una ruta
    @GetMapping("/{id}")
    public ResponseEntity<Ruta> obtenerPorId(@PathVariable Long id) 
    {
        return rutaService.obtenerRutaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET especial (HU-56): listar las rutas de un transportista
    @GetMapping("/transportista/{idTransportista}")
    public List<Ruta> obtenerPorTransportista(@PathVariable Long idTransportista) 
    {
        return rutaService.listarRutasPorTransportista(idTransportista);
    }

    // PUT: actualizar una ruta
    @PutMapping("/{id}")
    public ResponseEntity<Ruta> actualizar(@PathVariable Long id, @Valid @RequestBody Ruta ruta) 
    {
        Ruta actualizada = rutaService.actualizarRuta(id, ruta);
        return ResponseEntity.ok(actualizada);
    }

    // DELETE: eliminar una ruta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) 
    {
        rutaService.eliminarRuta(id);
        return ResponseEntity.noContent().build();
    }
}