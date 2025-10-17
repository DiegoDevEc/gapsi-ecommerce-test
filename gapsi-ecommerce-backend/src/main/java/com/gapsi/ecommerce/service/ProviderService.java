package com.gapsi.ecommerce.service;

import com.gapsi.ecommerce.dto.PagedProviderResponse;
import com.gapsi.ecommerce.dto.ProviderRequestDto;
import com.gapsi.ecommerce.dto.ProviderResponseDto;

/**
 * Servicio para gestionar operaciones de negocio de proveedores
 */
public interface ProviderService {

    /**
     * Obtiene lista paginada de proveedores
     *
     * @param page Número de página (base 0)
     * @param size Tamaño de página
     * @return Respuesta paginada personalizada
     */
    PagedProviderResponse findAllPaginated(int page, int size);

    /**
     * Crea un nuevo proveedor
     *
     * @param requestDto Datos del proveedor
     * @return Proveedor creado
     */
    ProviderResponseDto createProvider(ProviderRequestDto requestDto);

    /**
     * Elimina un proveedor por ID
     *
     * @param id ID del proveedor
     */
    void deleteProvider(Long id);

    /**
     * Busca un proveedor por ID
     *
     * @param id ID del proveedor
     * @return Proveedor encontrado
     */
    ProviderResponseDto findById(Long id);
}
