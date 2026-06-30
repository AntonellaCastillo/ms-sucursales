package com.perfulandia.ms_sucursales.service;

import com.perfulandia.ms_sucursales.model.Destino;
import com.perfulandia.ms_sucursales.model.EstadoDestino;
import com.perfulandia.ms_sucursales.repository.DestinoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DestinoServiceTest {

    @Mock
    private DestinoRepository destinoRepository;

    @InjectMocks
    private DestinoService destinoService;

    @Test
    void guardarDestino_guardaYDevuelve() {
        Destino d = new Destino();
        d.setOrden(1);
        when(destinoRepository.save(any(Destino.class))).thenAnswer(i -> i.getArgument(0));

        Destino resultado = destinoService.guardarDestino(d);

        assertEquals(1, resultado.getOrden());
        verify(destinoRepository).save(d);
    }

    @Test
    void listarDestinos_devuelveLista() {
        when(destinoRepository.findAll()).thenReturn(Arrays.asList(new Destino(), new Destino()));
        assertEquals(2, destinoService.listarDestinos().size());
    }

    @Test
    void obtenerDestinoPorId_existe_devuelveDestino() {
        Destino d = new Destino();
        d.setIdDestino(1L);
        when(destinoRepository.findById(1L)).thenReturn(Optional.of(d));

        Optional<Destino> resultado = destinoService.obtenerDestinoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdDestino());
    }

    @Test
    void obtenerDestinoPorId_noExiste_devuelveVacio() {
        when(destinoRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(destinoService.obtenerDestinoPorId(99L).isEmpty());
    }

    @Test
    void listarDestinosPorRuta_devuelveLista() {
        when(destinoRepository.findByRutaIdRutaOrderByOrdenAsc(1L)).thenReturn(Arrays.asList(new Destino()));
        assertEquals(1, destinoService.listarDestinosPorRuta(1L).size());
    }

    @Test
    void marcarEstado_existe_cambiaEstado() {
        Destino d = new Destino();
        d.setIdDestino(1L);
        d.setEstado(EstadoDestino.PENDIENTE);
        when(destinoRepository.findById(1L)).thenReturn(Optional.of(d));
        when(destinoRepository.save(any(Destino.class))).thenAnswer(i -> i.getArgument(0));

        Destino resultado = destinoService.marcarEstado(1L, EstadoDestino.ENTREGADO);

        assertEquals(EstadoDestino.ENTREGADO, resultado.getEstado());
    }

    @Test
    void marcarEstado_noExiste_lanzaExcepcion() {
        when(destinoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> destinoService.marcarEstado(99L, EstadoDestino.ENTREGADO));
    }

    @Test
    void eliminarDestino_llamaDeleteById() {
        destinoService.eliminarDestino(1L);
        verify(destinoRepository).deleteById(1L);
    }
}