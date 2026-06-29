package com.perfulandia.ms_sucursales.controller;

import com.perfulandia.ms_sucursales.model.Destino;
import com.perfulandia.ms_sucursales.model.EstadoDestino;
import com.perfulandia.ms_sucursales.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST de Destino
@RestController
@RequestMapping("/api/v1/destinos") // ruta base
public class DestinoController {

    // Comunicacion con el Service
    @Autowired
    private DestinoService destinoService;

    
    //Endpoints 
    
    // POST: crear un destino (una parada)
    @PostMapping
    public Destino crearDestino(@RequestBody Destino destino) 
    {
        return destinoService.guardarDestino(destino);
    }

    // GET: listar todos los destinos
    @GetMapping
    public List<Destino> obtenerTodos() 
    {
        return destinoService.listarDestinos();
    }

    // GET por id: obtener un destino
    @GetMapping("/{id}")
    public ResponseEntity<Destino> obtenerPorId(@PathVariable Long id) 
    {
        return destinoService.obtenerDestinoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET especial (HU-56): listar los destinos de una ruta, en orden
    // La URL queda /api/v1/destinos/ruta/5
    @GetMapping("/ruta/{idRuta}")
    public List<Destino> obtenerPorRuta(@PathVariable Long idRuta) 
    {
        return destinoService.listarDestinosPorRuta(idRuta);
    }

    // PUT especial (HU-57): marcar la parada como ENTREGADO o FALLIDO
    // El nuevo estado llega como parámetro en la URL: /api/v1/destinos/5/estado?nuevoEstado=ENTREGADO
    @PutMapping("/{id}/estado")
    public ResponseEntity<Destino> marcarEstado(@PathVariable Long id,
                                                @RequestParam EstadoDestino nuevoEstado) {
        try 
        {
            Destino actualizado = destinoService.marcarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: eliminar un destino
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) 
    {
        destinoService.eliminarDestino(id);
        return ResponseEntity.noContent().build();
    }
}