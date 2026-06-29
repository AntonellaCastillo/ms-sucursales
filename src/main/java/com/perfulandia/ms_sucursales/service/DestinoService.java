package com.perfulandia.ms_sucursales.service;

import com.perfulandia.ms_sucursales.model.Destino;
import com.perfulandia.ms_sucursales.model.EstadoDestino;
import com.perfulandia.ms_sucursales.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DestinoService 
{

    // Comunicacion con el Repository de Destino
    @Autowired
    private DestinoRepository destinoRepository;

    //Métodos
    
    // Guardar un destino (una parada)
    public Destino guardarDestino(Destino destino) 
    {
        return destinoRepository.save(destino);
    }

    // Listar todos los destinos
    public List<Destino> listarDestinos() 
    {
        return destinoRepository.findAll();
    }

    // Buscar un destino por su id
    public Optional<Destino> obtenerDestinoPorId(Long id) 
    {
        return destinoRepository.findById(id);
    }

    // HU-56: listar los destinos de una ruta, ordenados por su orden de visita
    public List<Destino> listarDestinosPorRuta(Long idRuta) 
    {
        return destinoRepository.findByRutaIdRutaOrderByOrdenAsc(idRuta);
    }

    // HU-57: marcar una parada como ENTREGADO o FALLIDO (regla de negocio)
    // Recibe el id del destino y el nuevo estado, busca la parada y le cambia el estado.
    public Destino marcarEstado(Long id, EstadoDestino nuevoEstado) 
    {
        Destino destino = destinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("destino no existe"));

        destino.setEstado(nuevoEstado);
        return destinoRepository.save(destino);
    }

    // Eliminar un destino por su id
    public void eliminarDestino(Long id) 
    {
        destinoRepository.deleteById(id);
    }
}