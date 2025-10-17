package com.gapsi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta de bienvenida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WelcomeResponse {

    /**
     * Mensaje de bienvenida
     */
    private String welcomeMessage;

    /**
     * Versión de la API
     */
    private String version;
}
