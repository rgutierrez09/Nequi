package com.nequi.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {
    PRODUCT_NOT_FOUND("Producto no existe", 404),
    PRODUCT_ALREADY_EXISTS("Producto ya existe", 400),
    UNEXPECTED_EXCEPTION("Sucedió un error inesperado", 500),
    TECHNOLOGY_NAME_DUPLICATE ("Nombre de producto duplicado.",404),
    MANDATORY_NAME("El nombre es obligatorio",404),
    MANDATORY_DATA("No pueden haber campos nulos",404),
    MANDATORY_STOCK("El stock debe tener como mínimo 1 ítem",404),
    INVALID_ARGUMENT("Sucedió un error inesperado", 500 ),
    ASSOCIATE_BRANCH_PRODUCT_ERROR("Error al asociar productos. Sucursal eliminada.", 404 ),
    BRANCH_NOT_FOUND("Sucursal no encontrada.", 404 ),
    ASSOCIATE_FRANCHISE_BRANCH_ERROR("Error al asociar sucursal. Franquicia eliminada.", 404 ),
    EMPTY_REQUEST_BODY("El request viene vacío", 404),
    UPDATE_FAILED("La actualización falló", 500);
    private final String message;
    private final int httpStatusCode;
}
