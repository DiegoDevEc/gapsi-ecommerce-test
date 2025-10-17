package com.gapsi.ecommerce.service;

import com.gapsi.ecommerce.dto.PagedProviderResponse;
import com.gapsi.ecommerce.dto.ProviderRequestDto;
import com.gapsi.ecommerce.dto.ProviderResponseDto;
import com.gapsi.ecommerce.exceptions.DuplicateProviderException;
import com.gapsi.ecommerce.exceptions.ProviderNotFoundException;
import com.gapsi.ecommerce.mapper.ProviderMapper;
import com.gapsi.ecommerce.model.Provider;
import com.gapsi.ecommerce.repository.JsonDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de proveedores
 * Patrón: Service Layer Pattern
 * Responsabilidad: Lógica de negocio y validaciones
 */
@Service
public class ProviderServiceImpl implements ProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private final JsonDatabase jsonDatabase;
    private final ProviderMapper providerMapper;

    public ProviderServiceImpl(JsonDatabase jsonDatabase, ProviderMapper providerMapper) {
        this.jsonDatabase = jsonDatabase;
        this.providerMapper = providerMapper;
    }

    @Override
    public PagedProviderResponse findAllPaginated(int page, int size) {
        logger.info("Consultando proveedores paginados - Página: {}, Tamaño: {}", page, size);

        List<Provider> allProviders = jsonDatabase.findAll();
        long totalItems = allProviders.size();

        // Calcular índices de paginación
        int start = page * size;
        int end = Math.min(start + size, allProviders.size());

        // Obtener sublist para la página actual
        List<Provider> pageProviders = start < allProviders.size()
                ? allProviders.subList(start, end)
                : List.of();

        // Convertir a DTOs
        List<ProviderResponseDto> items = pageProviders.stream()
                .map(providerMapper::toDto)
                .collect(Collectors.toList());

        // Calcular metadatos de paginación
        int totalPages = (int) Math.ceil((double) totalItems / size);
        boolean hasNext = page < totalPages - 1;
        boolean hasPrevious = page > 0;
        boolean isFirst = page == 0;
        boolean isLast = page >= totalPages - 1;

        logger.info("Retornando {} proveedores de un total de {}", items.size(), totalItems);

        return PagedProviderResponse.builder()
                .items(items)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .currentPage(page)
                .pageSize(size)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .isFirst(isFirst)
                .isLast(isLast)
                .build();
    }

    @Override
    public ProviderResponseDto createProvider(ProviderRequestDto requestDto) {
        logger.info("Creando nuevo proveedor con nombre: {}", requestDto.getName());

        // Validar duplicados por nombre
        if (jsonDatabase.existsByName(requestDto.getName())) {
            logger.warn("Intento de crear proveedor duplicado: {}", requestDto.getName());
            throw new DuplicateProviderException(
                    "Ya existe un proveedor con el nombre: " + requestDto.getName());
        }

        // Convertir DTO a entidad
        Provider provider = providerMapper.toEntity(requestDto);

        // Guardar en BD
        Provider savedProvider = jsonDatabase.save(provider);

        logger.info("Proveedor creado exitosamente con ID: {}", savedProvider.getId());

        // Retornar DTO de respuesta
        return providerMapper.toDto(savedProvider);
    }

    @Override
    public void deleteProvider(Long id) {
        logger.info("Eliminando proveedor con ID: {}", id);

        // Verificar que existe
        if (!jsonDatabase.findById(id).isPresent()) {
            logger.warn("No se encontró proveedor con ID: {}", id);
            throw new ProviderNotFoundException("No se encontró proveedor con ID: " + id);
        }

        // Eliminar
        boolean deleted = jsonDatabase.deleteById(id);

        if (deleted) {
            logger.info("Proveedor con ID {} eliminado exitosamente", id);
        }
    }

    @Override
    public ProviderResponseDto findById(Long id) {
        logger.info("Buscando proveedor con ID: {}", id);

        Provider provider = jsonDatabase.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró proveedor con ID: {}", id);
                    return new ProviderNotFoundException("No se encontró proveedor con ID: " + id);
                });

        return providerMapper.toDto(provider);
    }
}
