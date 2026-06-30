package com.perfulandia.ms_sucursales.service;

import com.perfulandia.ms_sucursales.model.Ruta;
import com.perfulandia.ms_sucursales.repository.RutaRepository;
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
class RutaServiceTest {

    @Mock
    private RutaRepository rutaRepository;

    @InjectMocks
    private RutaService rutaService;

    @Test
    void guardarRuta_guardaYDevuelve() {
        Ruta r = new Ruta();
        r.setZona("Santiago Centro");
        when(rutaRepository.save(any(Ruta.class))).thenAnswer(i -> i.getArgument(0));

        Ruta resultado = rutaService.guardarRuta(r);

        assertEquals("Santiago Centro", resultado.getZona());
        verify(rutaRepository).save(r);
    }

    @Test
    void listarRutas_devuelveLista() {
        when(rutaRepository.findAll()).thenReturn(Arrays.asList(new Ruta(), new Ruta()));
        assertEquals(2, rutaService.listarRutas().size());
    }

    @Test
    void obtenerRutaPorId_existe_devuelveRuta() {
        Ruta r = new Ruta();
        r.setIdRuta(1L);
        when(rutaRepository.findById(1L)).thenReturn(Optional.of(r));

        Optional<Ruta> resultado = rutaService.obtenerRutaPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdRuta());
    }

    @Test
    void obtenerRutaPorId_noExiste_devuelveVacio() {
        when(rutaRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(rutaService.obtenerRutaPorId(99L).isEmpty());
    }

    @Test
    void listarRutasPorTransportista_devuelveLista() {
        when(rutaRepository.findByIdTransportista(5L)).thenReturn(Arrays.asList(new Ruta()));
        assertEquals(1, rutaService.listarRutasPorTransportista(5L).size());
    }

    @Test
    void actualizarRuta_existe_actualiza() {
        Ruta existente = new Ruta();
        existente.setIdRuta(1L);
        existente.setZona("Vieja");
        Ruta nuevo = new Ruta();
        nuevo.setIdTransportista(2L);
        nuevo.setZona("Nueva");
        when(rutaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(rutaRepository.save(any(Ruta.class))).thenAnswer(i -> i.getArgument(0));

        Ruta resultado = rutaService.actualizarRuta(1L, nuevo);

        assertEquals("Nueva", resultado.getZona());
    }

    @Test
    void actualizarRuta_noExiste_lanzaExcepcion() {
        when(rutaRepository.findById(99L)).thenReturn(Optional.empty());
        Ruta nuevo = new Ruta();
        assertThrows(RuntimeException.class,
                () -> rutaService.actualizarRuta(99L, nuevo));
    }

    @Test
    void eliminarRuta_llamaDeleteById() {
        rutaService.eliminarRuta(1L);
        verify(rutaRepository).deleteById(1L);
    }
}