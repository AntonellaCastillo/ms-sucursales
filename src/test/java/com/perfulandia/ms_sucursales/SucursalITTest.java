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
class SucursalITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarSucursales_devuelve200() throws Exception {
        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk());
    }

    @Test
    void crearSucursal_valida_devuelve201() throws Exception {
        String body = """
                {
                  "nombre":"Sucursal Concepción",
                  "direccion":"Av. O'Higgins 123",
                  "latitud":-36.82699,
                  "longitud":-73.04977,
                  "horario":"09:00 - 20:00"
                }
                """;

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSucursal", notNullValue()))
                .andExpect(jsonPath("$.nombre").value("Sucursal Concepción"));
    }

    @Test
    void obtenerSucursalNoExistente_devuelve404() throws Exception {
        mockMvc.perform(get("/api/v1/sucursales/999999"))
                .andExpect(status().isNotFound());
    }
}