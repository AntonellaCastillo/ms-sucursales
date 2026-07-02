package com.perfulandia.ms_sucursales;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RutaITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarRutas_devuelve200() throws Exception {
        mockMvc.perform(get("/api/v1/rutas"))
                .andExpect(status().isOk());
    }

    @Test
    void crearRuta_valida_devuelve201() throws Exception {
        String body = """
                {
                  "idTransportista": 1,
                  "fecha": "2026-07-01",
                  "zona": "Santiago Centro"
                }
                """;

        mockMvc.perform(post("/api/v1/rutas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRuta", notNullValue()))
                .andExpect(jsonPath("$.idTransportista").value(1))
                .andExpect(jsonPath("$.fecha").value("2026-07-01"))
                .andExpect(jsonPath("$.zona").value("Santiago Centro"));
    }

    @Test
    void obtenerRutaNoExistente_devuelve404() throws Exception {
        mockMvc.perform(get("/api/v1/rutas/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerRutasPorTransportista_devuelve200() throws Exception {
        mockMvc.perform(get("/api/v1/rutas/transportista/1"))
                .andExpect(status().isOk());
    }
}