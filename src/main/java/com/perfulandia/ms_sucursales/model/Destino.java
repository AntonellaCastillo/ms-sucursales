package com.perfulandia.ms_sucursales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

// ENTIDAD DESTINO  (DETALLE / cada parada del recorrido)
// QUÉ es: representa UNA parada dentro de una Ruta.
// CÓMO funciona: tiene su PROPIO idDestino (por eso una ruta puede tener
//         muchas paradas distintas). El campo "orden" define la secuencia
//         (1, 2, 3...). Cada parada tiene su PROPIO estado, así una puede
//         estar ENTREGADA y otra FALLIDA en la misma ruta.
// PARA QUÉ: HU-56 (ver destinos en orden) y HU-57 (marcar cada parada).
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "destino")
public class Destino {

    // Clave primaria PROPIA: identifica cada parada por separado.
    // Por esto una ruta puede tener varias paradas sin confundirlas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDestino;

    // RELACIÓN muchos a uno: muchos Destino pertenecen a UNA Ruta.
    // @JoinColumn crea la columna FK "id_ruta" en la tabla destino.
    // Esta es la relación REAL interna (Ruta y Destino viven en este mismo MS).
    // @JsonIgnore: corta el bucle infinito al serializar. Cuando se pide una Ruta
    //         con sus destinos, cada destino NO vuelve a incluir la ruta completa.
    @ManyToOne
    @JoinColumn(name = "id_ruta")
    @JsonIgnore
    private Ruta ruta;

    // idEnvio: Id Externo (referencia a un Envio de OTRO microservicio).
    // Dice QUÉ pedido se entrega en esta parada. Por eso es solo un Long.
    @NotNull(message = "El idEnvio es obligatorio")
    private Long idEnvio;

    // orden: la secuencia de la parada en el recorrido (1, 2, 3...).
    // Es int -> @NotNull + @Min (nunca @NotBlank, que es solo para texto).
    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden mínimo es 1")
    private Integer orden;

    // Dirección de la parada (texto)
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    // Coordenadas (decimales exactos con BigDecimal)
    @NotNull(message = "La latitud es obligatoria")
    @DecimalMin(value = "-90.0", message = "La latitud mínima es -90")
    private BigDecimal latitud;

    @NotNull(message = "La longitud es obligatoria")
    @DecimalMin(value = "-180.0", message = "La longitud mínima es -180")
    private BigDecimal longitud;

    // Estado PROPIO de esta parada (enum). Guardado como texto en la BD.
    // @Enumerated(STRING): guarda "PENDIENTE"/"ENTREGADO"/"FALLIDO", no un número.
    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoDestino estado;
}