package com.perfulandia.ms_sucursales.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.perfulandia.ms_sucursales.service.SucursalService;

//Marca la clase como REST: crea las APIs REST
@RestController
@RequestMapping("/api/v1/sucursales") //RUTA BASE DE LA URL A LA QUE EL CLIENTE DEBE ENVIAR LA SOLICITUD
public class SucursalController 
{
    //Comunicacion con el Service
    @Autowired
    private SucursalService sucursalService; 
}   

