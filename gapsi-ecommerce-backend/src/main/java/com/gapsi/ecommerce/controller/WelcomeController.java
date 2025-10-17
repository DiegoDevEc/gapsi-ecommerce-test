package com.gapsi.ecommerce.controller;

import com.gapsi.ecommerce.dto.ApiResponse;
import com.gapsi.ecommerce.dto.WelcomeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para exponer datos de Bienvenida y version del api
 * Patrón: REST Controller Pattern
 * Responsabilidad: Manejar peticiones HTTP y retornar información de bienvenida
 */
@RestController
@RequestMapping("/api/v1/welcome")
@Tag(name = "Bienvenida", description = "API para entregar la versión y mensaje de bienvenida")
public class WelcomeController {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @Value("${api.user-welcome}")
    private String welcomeMessage;

    @Value("${api.version}")
    private String apiVersion;

    /**
     * Obtiene mensaje de bienvenida y versión de la API
     * GET /api/v1/welcome
     */
    @GetMapping
    @Operation(
            summary = "Obtener mensaje de bienvenida y versión",
            description = "Retorna el mensaje de bienvenida personalizado y la versión de la API"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Información obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    public ResponseEntity<ApiResponse<WelcomeResponse>> getWelcome() {
        logger.info("GET /api/v1/welcome - Solicitando mensaje de bienvenida y versión");

        WelcomeResponse welcomeResponse = WelcomeResponse.builder()
                .welcomeMessage(welcomeMessage)
                .version(apiVersion)
                .build();

        ApiResponse<WelcomeResponse> response = ApiResponse.success(
                "Información de bienvenida obtenida exitosamente",
                welcomeResponse
        );

        return ResponseEntity.ok(response);
    }
}
