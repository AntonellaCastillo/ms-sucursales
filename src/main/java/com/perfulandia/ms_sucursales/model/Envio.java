package com.perfulandia.ms_sucursales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @NotNull(message = "El id del pedido es obligatorio")
    private Long idPedido;

    @NotBlank(message = "La direccion destino es obligatoria")
    private String direccionDestino;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    @NotNull(message = "La fecha de despacho es obligatoria")
    private LocalDateTime fechaDespacho;

    private LocalDateTime fechaEntrega;
}