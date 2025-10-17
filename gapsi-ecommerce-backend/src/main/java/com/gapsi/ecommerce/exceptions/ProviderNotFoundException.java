package com.gapsi.ecommerce.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un proveedor
 */
public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException(String message) {
        super(message);
    }

    public ProviderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
