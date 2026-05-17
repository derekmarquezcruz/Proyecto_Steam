package org.derek.Modelo.Form;
import java.util.ArrayList;
import java.util.List;


import org.derek.Controlador.Util;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;



public record FormResena(
        Long idUsuario,
        Long idJuego,
        boolean recomendado,
        String textoResena,
        Long horasJugadas
) {

    public static final int MIN_TEXTO = 50;
    public static final int MAX_TEXTO = 8000;

    public List<ErrorDTO> validar() {

        List<ErrorDTO> errores = new ArrayList<>();

        // Usuario
        if (idUsuario == null) {
            errores.add(new ErrorDTO("Usuario", TipoError.REQUERIDO));
        }

        // Juego
        if (idJuego == null) {
            errores.add(new ErrorDTO("Juego", TipoError.REQUERIDO));
        }

        // Texto reseña
        if (Util.checkCadenaBlankOrEmpty(textoResena)) {
            errores.add(new ErrorDTO("Reseña", TipoError.REQUERIDO));
        } else {
            if (textoResena.length() < MIN_TEXTO) {
                errores.add(new ErrorDTO("Reseña", TipoError.VALOR_DEMASIADO_BAJO));
            }
            if (textoResena.length() > MAX_TEXTO) {
                errores.add(new ErrorDTO("Reseña", TipoError.VALOR_DEMASIADO_ALTO));
            }
        }

        // Horas jugadas
        if (horasJugadas == null) {
            errores.add(new ErrorDTO("HorasJugadas", TipoError.REQUERIDO));
        } else if (horasJugadas < 0) {
            errores.add(new ErrorDTO("HorasJugadas", TipoError.VALOR_DEMASIADO_BAJO));
        }

        return errores;
    }
}