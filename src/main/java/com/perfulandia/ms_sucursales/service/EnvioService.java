package com.perfulandia.ms_sucursales.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.perfulandia.ms_sucursales.dto.PedidoDTO;
import com.perfulandia.ms_sucursales.model.Envio;
import com.perfulandia.ms_sucursales.model.EstadoEnvio;
import com.perfulandia.ms_sucursales.repository.EnvioRepository;

@Service
public class EnvioService {

    private static final Logger log = LoggerFactory.getLogger(EnvioService.class);

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String MS_PEDIDOS_URL = "http://localhost:8091/api/v1/pedidos";
    private static final String MS_NOTIFICACIONES_URL = "http://localhost:8089/api/v1/notificaciones";
    private static final String MS_INVENTARIO_URL = "http://localhost:8083/api/v1/inventario";

    public List<Envio> findAll() {
        log.info("Listando todos los envios");
        return envioRepository.findAll();
    }

    public Optional<Envio> findById(Long id) {
        log.info("Buscando envio con id: {}", id);
        return envioRepository.findById(id);
    }

    public Optional<Envio> findByIdPedido(Long idPedido) {
        log.info("Buscando envio del pedido: {}", idPedido);
        return envioRepository.findByIdPedido(idPedido);
    }

    public List<Envio> findByEstado(EstadoEnvio estado) {
        log.info("Buscando envios con estado: {}", estado);
        return envioRepository.findByEstado(estado);
    }

    public List<Envio> findBySucursal(Long idSucursal) {
        log.info("Buscando envios de la sucursal: {}", idSucursal);
        return envioRepository.findBySucursalIdSucursal(idSucursal);
    }

    // Crear envío — igual que el profesor con PedidoDTO
    public Envio save(Envio envio) {
        log.info("Creando envio para pedido: {}", envio.getIdPedido());

        // Verificar stock en MS Inventario
        try {
            restTemplate.getForObject(MS_INVENTARIO_URL, Object.class);
            log.info("Stock verificado correctamente en MS Inventario");
        } catch (Exception e) {
            log.warn("MS Inventario no disponible: {}. Creando envio en contingencia", e.getMessage());
        }

        // Verificar que el pedido existe en MS Pedidos
        String url = MS_PEDIDOS_URL + "/" + envio.getIdPedido();
        PedidoDTO pedido = restTemplate.getForObject(url, PedidoDTO.class);

        if (pedido != null) {
            log.info("Pedido {} verificado. Cliente: {}", pedido.getIdPedido(), pedido.getIdCliente());
        } else {
            log.warn("Pedido {} no encontrado. Creando envio en contingencia", envio.getIdPedido());
        }

        Envio guardado = envioRepository.save(envio);
        log.info("Envio creado con id: {}", guardado.getIdEnvio());

        // Notificar a MS Notificaciones
        try {
            restTemplate.postForObject(MS_NOTIFICACIONES_URL, guardado, String.class);
            log.info("MS Notificaciones notificado correctamente");
        } catch (Exception e) {
            log.warn("MS Notificaciones no disponible: {}", e.getMessage());
        }

        return guardado;
    }

    // Actualizar estado del envío — Regla de negocio
    public Optional<Envio> actualizarEstado(Long id, EstadoEnvio nuevoEstado) {
        log.info("Actualizando estado del envio {} a {}", id, nuevoEstado);
        return envioRepository.findById(id).map(envio -> {
            if (envio.getEstado().equals(EstadoEnvio.ENTREGADO) ||
                envio.getEstado().equals(EstadoEnvio.FALLIDO)) {
                log.warn("No se puede cambiar estado de envio {} porque está {}", id, envio.getEstado());
                throw new RuntimeException("No se puede cambiar el estado de un envio ENTREGADO o FALLIDO");
            }
            envio.setEstado(nuevoEstado);

            // Si el envío fue entregado, registrar fecha y notificar
            if (nuevoEstado.equals(EstadoEnvio.ENTREGADO)) {
                envio.setFechaEntrega(LocalDateTime.now());
                log.info("Fecha de entrega registrada para envio {}", id);

                // Notificar a MS Pedidos
                try {
                    restTemplate.put(
                        MS_PEDIDOS_URL + "/" + envio.getIdPedido() + "/estado?estado=ENTREGADO",
                        null
                    );
                    log.info("MS Pedidos notificado del estado ENTREGADO");
                } catch (Exception e) {
                    log.warn("MS Pedidos no disponible: {}", e.getMessage());
                }

                // Notificar a MS Notificaciones
                try {
                    restTemplate.postForObject(MS_NOTIFICACIONES_URL, envio, String.class);
                    log.info("MS Notificaciones notificado correctamente");
                } catch (Exception e) {
                    log.warn("MS Notificaciones no disponible: {}", e.getMessage());
                }
            }

            log.info("Estado del envio {} actualizado a {}", id, nuevoEstado);
            return envioRepository.save(envio);
        });
    }
}