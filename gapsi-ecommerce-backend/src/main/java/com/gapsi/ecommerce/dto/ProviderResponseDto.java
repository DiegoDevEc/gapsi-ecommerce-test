package com.gapsi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para responder con datos de proveedor
 * Solo expone los campos necesarios al cliente
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderResponseDto {

    private Long id;
    private String name;
    private String companyName;
    private String address;
}
