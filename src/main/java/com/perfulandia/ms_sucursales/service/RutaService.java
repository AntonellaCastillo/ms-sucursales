package com.perfulandia.ms_sucursales.service;

import com.perfulandia.ms_sucursales.exception.RecursoNoEncontradoException;
import com.perfulandia.ms_sucursales.model.Ruta;
import com.perfulandia.ms_sucursales.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RutaService {

    // Comunicacion con el Repository de Ruta
    @Autowired
    private RutaRepository rutaRepository;


    //Metodos

    // Guardar una ruta nueva (con sus destinos, por el cascade)
    public Ruta guardarRuta(Ruta ruta) 
    {
        return rutaRepository.save(ruta);
    }

    // Listar todas las rutas
    public List<Ruta> listarRutas() 
    {
        return rutaRepository.findAll();
    }

    // Buscar una ruta por su id
    public Optional<Ruta> obtenerRutaPorId(Long id) 
    {
        return rutaRepository.findById(id);
    }

    // HU-56: listar solo las rutas de un transportista (cada uno ve las suyas)
    public List<Ruta> listarRutasPorTransportista(Long idTransportista) 
    {
        return rutaRepository.findByIdTransportista(idTransportista);
    }

    // Actualizar una ruta existente
    public Ruta actualizarRuta(Long id, Ruta ruta) 
    {
        Ruta existente = rutaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ruta no existe"));

        existente.setIdTransportista(ruta.getIdTransportista());
        existente.setFecha(ruta.getFecha());
        existente.setZona(ruta.getZona());

        return rutaRepository.save(existente);
    }

    // Eliminar una ruta por su id
    public void eliminarRuta(Long id) 
    {
        rutaRepository.deleteById(id);
    }
}