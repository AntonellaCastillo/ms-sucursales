package com.perfulandia.ms_sucursales.controller;

import com.perfulandia.ms_sucursales.model.Sucursal;
import com.perfulandia.ms_sucursales.service.SucursalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalControllerTest {

    @Mock
    private SucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;

    @Test
    void crearSucursal_devuelve201() {
        Sucursal s = new Sucursal();
        s.setIdSucursal(1L);
        when(sucursalService.guardarSucursal(any(Sucursal.class))).thenReturn(s);

        ResponseEntity<Sucursal> respuesta = sucursalController.crearSucursal(s);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    void obtenerTodas_devuelveLista() {
        when(sucursalService.listarSucursal()).thenReturn(Arrays.asList(new Sucursal(), new Sucursal()));
        List<Sucursal> respuesta = sucursalController.obtenerTodas();
        assertEquals(2, respuesta.size());
    }

    @Test
    void obtenerPorId_existe_devuelve200() {
        Sucursal s = new Sucursal();
        s.setIdSucursal(1L);
        when(sucursalService.obtenerSucursalporId(1L)).thenReturn(Optional.of(s));

        ResponseEntity<Sucursal> respuesta = sucursalController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void obtenerPorId_noExiste_devuelve404() {
        when(sucursalService.obtenerSucursalporId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Sucursal> respuesta = sucursalController.obtenerPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void actualizar_devuelve200() {
        Sucursal s = new Sucursal();
        when(sucursalService.actualizarSucursal(anyLong(), any(Sucursal.class))).thenReturn(s);

        ResponseEntity<Sucursal> respuesta = sucursalController.actualizar(1L, s);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void eliminar_devuelve204() {
        doNothing().when(sucursalService).eliminarSucursal(1L);

        ResponseEntity<Void> respuesta = sucursalController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }
}