package org.derek.Excepciones;

public class ExcepcionGenerica extends Exception {


    private String errores;

    public ExcepcionGenerica(String errores) {
        super(errores.toString());
        this.errores = errores;
    }

    public String getErrores() {
        return errores;
    }

}
