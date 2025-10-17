package com.gapsi.ecommerce.exceptions;

/**
 * Excepción lanzada cuando se intenta crear un proveedor duplicado
 */
public class DuplicateProviderException extends RuntimeException {

    public DuplicateProviderException(String message) {
        super(message);
    }

    public DuplicateProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
