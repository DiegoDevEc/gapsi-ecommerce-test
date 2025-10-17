package com.gapsi.ecommerce.mapper;

import com.gapsi.ecommerce.dto.ProviderRequestDto;
import com.gapsi.ecommerce.dto.ProviderResponseDto;
import com.gapsi.ecommerce.model.Provider;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades Provider y DTOs
 * Patrón: Mapper Pattern
 * Responsabilidad: Separar la capa de dominio de la capa de presentación
 */
@Component
public class ProviderMapper {

    /**
     * Convierte un ProviderRequestDto a Provider entity
     *
     * @param dto DTO con datos de entrada
     * @return Provider entity
     */
    public Provider toEntity(ProviderRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Provider.builder()
                .name(dto.getName())
                .companyName(dto.getCompanyName())
                .address(dto.getAddress())
                .build();
    }

    /**
     * Convierte un Provider entity a ProviderResponseDto
     *
     * @param provider Provider entity
     * @return DTO con datos de respuesta
     */
    public ProviderResponseDto toDto(Provider provider) {
        if (provider == null) {
            return null;
        }

        return ProviderResponseDto.builder()
                .id(provider.getId())
                .name(provider.getName())
                .companyName(provider.getCompanyName())
                .address(provider.getAddress())
                .build();
    }

    /**
     * Actualiza un Provider entity existente con datos del DTO
     *
     * @param provider Provider entity a actualizar
     * @param dto DTO con nuevos datos
     */
    public void updateEntityFromDto(Provider provider, ProviderRequestDto dto) {
        if (provider == null || dto == null) {
            return;
        }

        provider.setName(dto.getName());
        provider.setCompanyName(dto.getCompanyName());
        provider.setAddress(dto.getAddress());
    }
}
