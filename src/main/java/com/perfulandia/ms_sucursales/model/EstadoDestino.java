package com.perfulandia.ms_sucursales.model;
 
// ENUM EstadoDestino
// QUÉ: define los únicos estados válidos de una parada (Destino).
// PARA QUÉ: limitar los valores posibles. Una parada solo puede estar
//           en uno de estos 3 estados, nunca un texto libre.
public enum EstadoDestino 
{
    PENDIENTE,   // la parada aún no se visita
    ENTREGADO,   // se entregó el pedido en esa parada
    FALLIDO      // no se pudo entregar (cliente ausente, etc.)
}
 