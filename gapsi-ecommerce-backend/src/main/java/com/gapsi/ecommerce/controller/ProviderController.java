package com.gapsi.ecommerce.controller;

import com.gapsi.ecommerce.dto.ApiResponse;
import com.gapsi.ecommerce.dto.PagedProviderResponse;
import com.gapsi.ecommerce.dto.ProviderRequestDto;
import com.gapsi.ecommerce.dto.ProviderResponseDto;
import com.gapsi.ecommerce.service.ProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestión de proveedores
 * Patrón: REST Controller Pattern
 * Responsabilidad: Manejar peticiones HTTP y delegar al servicio
 */
@RestController
@RequestMapping("/api/v1/providers")
@Tag(name = "Proveedores", description = "API para gestión de proveedores")
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * Obtiene lista paginada de proveedores
     * GET /api/v1/providers?page=0&size=10
     * Optimizado para virtual scroll en React
     */
    @GetMapping
    @Operation(
            summary = "Obtener proveedores paginados",
            description = "Retorna una lista paginada de todos los proveedores. Optimizado para virtual scroll en React con estructura JSON estable."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista de proveedores obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    public ResponseEntity<ApiResponse<PagedProviderResponse>> getAllProviders(
            @Parameter(description = "Número de página (inicia en 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        logger.info("GET /api/v1/providers - page: {}, size: {}", page, size);

        PagedProviderResponse pagedResponse = providerService.findAllPaginated(page, size);

        ApiResponse<PagedProviderResponse> response = ApiResponse.success(
                "Proveedores obtenidos exitosamente",
                pagedResponse
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un proveedor por ID
     * GET /api/v1/providers/{id}
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener proveedor por ID",
            description = "Retorna un proveedor específico por su ID"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Proveedor encontrado",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Proveedor no encontrado"
            )
    })
    public ResponseEntity<ApiResponse<ProviderResponseDto>> getProviderById(
            @Parameter(description = "ID del proveedor", example = "1")
            @PathVariable Long id
    ) {
        logger.info("GET /api/v1/providers/{}", id);

        ProviderResponseDto provider = providerService.findById(id);

        ApiResponse<ProviderResponseDto> response = ApiResponse.success(
                "Proveedor encontrado",
                provider
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo proveedor
     * POST /api/v1/providers
     */
    @PostMapping
    @Operation(
            summary = "Crear nuevo proveedor",
            description = "Crea un nuevo proveedor validando que el nombre no esté duplicado"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Proveedor creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Ya existe un proveedor con ese nombre"
            )
    })
    public ResponseEntity<ApiResponse<ProviderResponseDto>> createProvider(
            @Valid @RequestBody ProviderRequestDto requestDto
    ) {
        logger.info("POST /api/v1/providers - nombre: {}", requestDto.getName());

        ProviderResponseDto createdProvider = providerService.createProvider(requestDto);

        ApiResponse<ProviderResponseDto> response = ApiResponse.success(
                "Proveedor creado exitosamente",
                createdProvider
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Elimina un proveedor por ID
     * DELETE /api/v1/providers/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar proveedor",
            description = "Elimina un proveedor por su ID"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Proveedor eliminado exitosamente"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Proveedor no encontrado"
            )
    })
    public ResponseEntity<ApiResponse<Void>> deleteProvider(
            @Parameter(description = "ID del proveedor a eliminar", example = "1")
            @PathVariable Long id
    ) {
        logger.info("DELETE /api/v1/providers/{}", id);

        providerService.deleteProvider(id);

        ApiResponse<Void> response = ApiResponse.success(
                "Proveedor eliminado exitosamente",
                null
        );

        return ResponseEntity.ok(response);
    }
}
