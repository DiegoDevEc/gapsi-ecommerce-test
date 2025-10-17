package com.gapsi.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provider {
    private Long id;
    private String name;
    private String companyName;
    private String address;
}
