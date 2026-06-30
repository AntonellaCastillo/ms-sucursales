package com.perfulandia.ms_sucursales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// CAPA CONFIG: registra el RestTemplate como @Bean para comunicarse con otros MS.
@Configuration
public class RestTemplateConfig 
{

    @Bean
    public RestTemplate restTemplate() 
    {
        return new RestTemplate();
    }
}