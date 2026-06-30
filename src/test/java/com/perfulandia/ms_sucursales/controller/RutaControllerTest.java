package com.perfulandia.ms_sucursales.controller;

import com.perfulandia.ms_sucursales.model.Ruta;
import com.perfulandia.ms_sucursales.service.RutaService;
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
class RutaControllerTest {

    @Mock
    private RutaService rutaService;

    @InjectMocks
    private RutaController rutaController;

    @Test
    void crearRuta_devuelve201() {
        Ruta r = new Ruta();
        r.setIdRuta(1L);
        when(rutaService.guardarRuta(any(Ruta.class))).thenReturn(r);

        ResponseEntity<Ruta> respuesta = rutaController.crearRuta(r);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    void obtenerTodas_devuelveLista() {
        when(rutaService.listarRutas()).thenReturn(Arrays.asList(new Ruta(), new Ruta()));
        List<Ruta> respuesta = rutaController.obtenerTodas();
        assertEquals(2, respuesta.size());
    }

    @Test
    void obtenerPorId_existe_devuelve200() {
        Ruta r = new Ruta();
        r.setIdRuta(1L);
        when(rutaService.obtenerRutaPorId(1L)).thenReturn(Optional.of(r));

        ResponseEntity<Ruta> respuesta = rutaController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void obtenerPorId_noExiste_devuelve404() {
        when(rutaService.obtenerRutaPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Ruta> respuesta = rutaController.obtenerPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void obtenerPorTransportista_devuelveLista() {
        when(rutaService.listarRutasPorTransportista(5L)).thenReturn(Arrays.asList(new Ruta()));
        List<Ruta> respuesta = rutaController.obtenerPorTransportista(5L);
        assertEquals(1, respuesta.size());
    }

    @Test
    void actualizar_devuelve200() {
        Ruta r = new Ruta();
        when(rutaService.actualizarRuta(anyLong(), any(Ruta.class))).thenReturn(r);

        ResponseEntity<Ruta> respuesta = rutaController.actualizar(1L, r);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void eliminar_devuelve204() {
        doNothing().when(rutaService).eliminarRuta(1L);

        ResponseEntity<Void> respuesta = rutaController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }
}