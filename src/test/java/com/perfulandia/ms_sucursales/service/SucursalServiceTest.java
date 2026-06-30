package com.perfulandia.ms_sucursales.service;

import com.perfulandia.ms_sucursales.model.Sucursal;
import com.perfulandia.ms_sucursales.repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @Test
    void guardarSucursal_guardaYDevuelve() {
        Sucursal s = new Sucursal();
        s.setNombre("Sucursal Centro");
        when(sucursalRepository.save(any(Sucursal.class))).thenAnswer(i -> i.getArgument(0));

        Sucursal resultado = sucursalService.guardarSucursal(s);

        assertEquals("Sucursal Centro", resultado.getNombre());
        verify(sucursalRepository).save(s);
    }

    @Test
    void listarSucursal_devuelveLista() {
        when(sucursalRepository.findAll()).thenReturn(Arrays.asList(new Sucursal(), new Sucursal()));
        assertEquals(2, sucursalService.listarSucursal().size());
    }

    @Test
    void obtenerSucursalporId_existe_devuelveSucursal() {
        Sucursal s = new Sucursal();
        s.setIdSucursal(1L);
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(s));

        Optional<Sucursal> resultado = sucursalService.obtenerSucursalporId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdSucursal());
    }

    @Test
    void obtenerSucursalporId_noExiste_devuelveVacio() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(sucursalService.obtenerSucursalporId(99L).isEmpty());
    }

    @Test
    void actualizarSucursal_existe_actualiza() {
        Sucursal existente = new Sucursal();
        existente.setIdSucursal(1L);
        existente.setNombre("Viejo");
        Sucursal nuevo = new Sucursal();
        nuevo.setNombre("Nuevo");
        nuevo.setDireccion("Av. Nueva 123");
        nuevo.setLatitud(new BigDecimal("-33.4"));
        nuevo.setLongitud(new BigDecimal("-70.6"));
        nuevo.setHorario("9-18");
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(sucursalRepository.save(any(Sucursal.class))).thenAnswer(i -> i.getArgument(0));

        Sucursal resultado = sucursalService.actualizarSucursal(1L, nuevo);

        assertEquals("Nuevo", resultado.getNombre());
    }

    @Test
    void actualizarSucursal_noExiste_lanzaExcepcion() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());
        Sucursal nuevo = new Sucursal();
        assertThrows(RuntimeException.class,
                () -> sucursalService.actualizarSucursal(99L, nuevo));
    }

    @Test
    void eliminarSucursal_llamaDeleteById() {
        sucursalService.eliminarSucursal(1L);
        verify(sucursalRepository).deleteById(1L);
    }
}