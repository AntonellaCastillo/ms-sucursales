package com.perfulandia.ms_sucursales.service;

import com.perfulandia.ms_sucursales.dto.EnvioActualizadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Cliente de comunicación con MS Envíos (comunicación REST entre microservicios).
@Service
public class EnvioClienteService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ms.envios.url:http://localhost:8091}")
    private String urlEnvios;

    // Avisa a MS Envíos que un destino se entregó, para que actualice el pedido.
    // Va en try/catch para ser RESILIENTE: si Envíos está caído, Sucursales NO se cae.
    public void notificarEntrega(Long idEnvio, String estado) {
        try {
            EnvioActualizadoDTO dto = new EnvioActualizadoDTO(idEnvio, estado);
            String url = urlEnvios + "/api/v1/envios/" + idEnvio + "/estado?nuevoEstado=" + estado;
            restTemplate.put(url, dto);
        } catch (Exception e) {
            // Resiliencia: si Envíos no responde, Sucursales sigue funcionando.
            System.out.println("No se pudo notificar a MS Envios: " + e.getMessage());
        }
    }
}