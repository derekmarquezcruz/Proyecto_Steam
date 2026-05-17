package org.derek.Controlador;

import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Modelo.Form.Errores.ErrorDTO;

import java.util.List;

public class Util {

    /**
     * Comprueba si la cadena no es nula o vacia
     *
     * @param cadena cadena a comprobar
     * @return true si no es nula o vacia, false en cualquier otro caso
     */
    public static boolean checkCadenaBlankOrEmpty(String cadena) {

        return cadena == null || cadena.isBlank();
    }

    public static void throwException(List<ErrorDTO> errors) throws ExcepcionGenerica {
        if (!errors.isEmpty()) {
            throw new ExcepcionGenerica(errors.toString());
        }
    }
}
