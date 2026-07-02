package com.perfulandia.ms_sucursales;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DestinoITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarDestinos_devuelve200() throws Exception {
        mockMvc.perform(get("/api/v1/destinos"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerDestinoNoExistente_devuelve404() throws Exception {
        mockMvc.perform(get("/api/v1/destinos/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerDestinosPorRuta_devuelve200() throws Exception {
        mockMvc.perform(get("/api/v1/destinos/ruta/1"))
                .andExpect(status().isOk());
    }
}