package com.perfulandia.ms_sucursales.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.perfulandia.ms_sucursales.model.Sucursal;
import com.perfulandia.ms_sucursales.service.SucursalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import jakarta.validation.Valid;



//Marca la clase como REST: crea las APIs REST
@RestController
@RequestMapping("/api/v1/sucursales") //RUTA BASE DE LA URL A LA QUE EL CLIENTE DEBE ENVIAR LA SOLICITUD
public class SucursalController 
{
    // Comunicacion con el Service
    @Autowired
    private SucursalService sucursalService;

    // Endpoint para guardar
    @PostMapping
    public Sucursal crearSucursal(@Valid @RequestBody Sucursal sucursal) 
    {
        return sucursalService.guardarSucursal(sucursal);
    }

    // Endpoint para listar
    @GetMapping
    public List<Sucursal> obtenerTodas() 
    {
        return sucursalService.listarSucursal();
    }

    // Endpoint para obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerPorId(@PathVariable Long id) 
    {
        return sucursalService.obtenerSucursalporId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Long id, @Valid @RequestBody Sucursal sucursal) 
    {
        Sucursal actualizada = sucursalService.actualizarSucursal(id, sucursal);
        return ResponseEntity.ok(actualizada);
    }

    // Endpoint para eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) 
    {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}

