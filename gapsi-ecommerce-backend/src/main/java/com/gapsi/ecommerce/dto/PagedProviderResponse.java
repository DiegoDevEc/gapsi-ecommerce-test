package com.gapsi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta paginada personalizado
 * Optimizado para virtual scroll en React
 * Estructura estable y simple para serialización JSON
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedProviderResponse {

    /**
     * Lista de proveedores en la página actual
     */
    private List<ProviderResponseDto> items;

    /**
     * Número total de elementos en todas las páginas
     */
    private long totalItems;

    /**
     * Número total de páginas
     */
    private int totalPages;

    /**
     * Página actual (base 0)
     */
    private int currentPage;

    /**
     * Tamaño de página (elementos por página)
     */
    private int pageSize;

    /**
     * Indica si hay una página siguiente
     */
    private boolean hasNext;

    /**
     * Indica si hay una página anterior
     */
    private boolean hasPrevious;

    /**
     * Indica si es la primera página
     */
    private boolean isFirst;

    /**
     * Indica si es la última página
     */
    private boolean isLast;
}
