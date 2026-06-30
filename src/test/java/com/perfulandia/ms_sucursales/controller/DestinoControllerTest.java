package com.perfulandia.ms_sucursales.controller;

import com.perfulandia.ms_sucursales.model.Destino;
import com.perfulandia.ms_sucursales.model.EstadoDestino;
import com.perfulandia.ms_sucursales.service.DestinoService;
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
class DestinoControllerTest {

    @Mock
    private DestinoService destinoService;

    @InjectMocks
    private DestinoController destinoController;

    @Test
    void crearDestino_devuelve201() {
        Destino d = new Destino();
        d.setIdDestino(1L);
        when(destinoService.guardarDestino(any(Destino.class))).thenReturn(d);

        ResponseEntity<Destino> respuesta = destinoController.crearDestino(d);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
    }

    @Test
    void obtenerTodos_devuelveLista() {
        when(destinoService.listarDestinos()).thenReturn(Arrays.asList(new Destino(), new Destino()));
        List<Destino> respuesta = destinoController.obtenerTodos();
        assertEquals(2, respuesta.size());
    }

    @Test
    void obtenerPorId_existe_devuelve200() {
        Destino d = new Destino();
        d.setIdDestino(1L);
        when(destinoService.obtenerDestinoPorId(1L)).thenReturn(Optional.of(d));

        ResponseEntity<Destino> respuesta = destinoController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void obtenerPorId_noExiste_devuelve404() {
        when(destinoService.obtenerDestinoPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Destino> respuesta = destinoController.obtenerPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void obtenerPorRuta_devuelveLista() {
        when(destinoService.listarDestinosPorRuta(1L)).thenReturn(Arrays.asList(new Destino()));
        List<Destino> respuesta = destinoController.obtenerPorRuta(1L);
        assertEquals(1, respuesta.size());
    }

    @Test
    void marcarEstado_devuelve200() {
        Destino d = new Destino();
        when(destinoService.marcarEstado(anyLong(), any(EstadoDestino.class))).thenReturn(d);

        ResponseEntity<Destino> respuesta = destinoController.marcarEstado(1L, EstadoDestino.ENTREGADO);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void eliminar_devuelve204() {
        doNothing().when(destinoService).eliminarDestino(1L);

        ResponseEntity<Void> respuesta = destinoController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }
}