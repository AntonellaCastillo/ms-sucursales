package com.perfulandia.ms_sucursales.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// DTO - Data Transfer Object
// QUÉ: el "sobre" con solo los datos que MS Envíos necesita cuando una parada se entrega.
// CÓMO: clase simple, SIN @Entity (no se guarda en BD). Solo campos + Lombok.
// PARA QUÉ: avisar a MS Envíos (evento envio.actualizado) sin exponer mi entidad Destino.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioActualizadoDTO {

    // El envío que se entregó (Envíos lo usa para ubicar su pedido)
    private Long idEnvio;

    // El nuevo estado del envío (ej: "ENTREGADO")
    private String estado;
}