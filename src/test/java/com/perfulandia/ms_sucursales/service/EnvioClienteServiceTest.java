package com.perfulandia.ms_sucursales.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

// Prueba del EnvioClienteService (comunicación REST con resiliencia).
@ExtendWith(MockitoExtension.class)
class EnvioClienteServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EnvioClienteService envioClienteService;

    // Verifica que notificarEntrega llame al RestTemplate (comunicación normal)
    @Test
    void notificarEntrega_llamaRestTemplate() {
        envioClienteService.notificarEntrega(1L, "ENTREGADO");
        verify(restTemplate).put(anyString(), any());
    }

    // Verifica la RESILIENCIA: si el RestTemplate falla, NO lanza excepción (no se cae)
    @Test
    void notificarEntrega_siFallaNoLanzaExcepcion() {
        doThrow(new RuntimeException("Envios caido")).when(restTemplate).put(anyString(), any());

        // No debe lanzar excepción: el try/catch la atrapa (resiliencia)
        envioClienteService.notificarEntrega(1L, "ENTREGADO");

        verify(restTemplate).put(anyString(), any());
    }
}