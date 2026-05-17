package org.derek.Modelo.Form.Errores;


public enum TipoError {
    REQUERIDO("El campo es obligatorio"),
    FORMATO_INVALIDO("El formato es inválido"),
    VALOR_DEMASIADO_ALTO("El valor es demasiado alto"),
    VALOR_DEMASIADO_BAJO("El valor es demasiado bajo"),
    NO_ENCONTRADO("No se encontró el elemento"),
    DUPLICADO("El elemento está duplicado"),
    NO_ACTUALIZADO("No se puedo actualizar"),
    NO_ELIMINADO("No se puedo eliminar"),
    ESTADO_INCORRECTO("Estado Incorrecto"),;

    private final String mensaje;

    TipoError(String mensaje) {
        this.mensaje = mensaje;
    }
}
