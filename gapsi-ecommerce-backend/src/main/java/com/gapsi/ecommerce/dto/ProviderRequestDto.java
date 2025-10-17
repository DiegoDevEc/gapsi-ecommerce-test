package com.gapsi.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir datos de proveedor en las peticiones
 * Aplica validaciones de negocio
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre de la empresa debe tener entre 3 y 200 caracteres")
    private String companyName;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 10, max = 300, message = "La dirección debe tener entre 10 y 300 caracteres")
    private String address;
}
