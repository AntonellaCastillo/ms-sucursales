package com.perfulandia.ms_sucursales.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.ms_sucursales.model.Envio;
import com.perfulandia.ms_sucursales.model.EstadoEnvio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    // Buscar envío por pedido
    Optional<Envio> findByIdPedido(Long idPedido);

    // Buscar envíos por estado
    List<Envio> findByEstado(EstadoEnvio estado);

    // Buscar envíos por sucursal
    List<Envio> findBySucursalIdSucursal(Long idSucursal);
}