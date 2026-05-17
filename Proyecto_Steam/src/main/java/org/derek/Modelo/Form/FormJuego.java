package org.derek.Modelo.Form;
import org.derek.Controlador.Util;
import org.derek.Modelo.DTO.Juego.ClasificacionEdad;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record FormJuego(
        String titulo,
        String descripcion,
        String desarrollador,
        LocalDate fechaLanzamiento,
        String categoria,
        float precioBase,
        ClasificacionEdad ClasificacionEdad,
        List<String> idiomasDisponibles) {

    public static final int MAX_TITULO = 100;
    public static final int MAX_DESCRIPCION= 2000;
    public static final int MIN_DEV = 2;
    public static final int MAX_DEV = 100;
    public static final int MIN_PRECIO_BASE = 0;
    public static final double MAX_PRECIO_BASE = 999.99;
    public static final int DECIMALES = 2;
    public static final int MAX_LANG = 200;


    //Validaciones

    /**
     * Valida que los datos del juego se hayan introducido correctamente
     *
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacia
     *
     */
    public List<ErrorDTO> validate() {

        List<ErrorDTO> errores = new ArrayList<>();


        //Titulo Game
        errores.addAll(validateGameTittle());

        //Descripcion Game (opcional)
        errores.addAll(validateDescripcion());

        //Desarrollador
        errores.addAll(validateDesarrollador());

        //Fecha de lanzamiento
        errores.addAll(validateFechaLanzamiento());

        //Pais
        errores.addAll(validatePrecioBase());

        //Clasificacion de edad
        errores.addAll(validateClasificacionEdad());

        //Idiomas
        errores.addAll(validateIdiomas());


        return errores;
    }


    /**
     * Valida que el titulo del juego se haya introducido correctamente
     *
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacia
     *
     */
    private List<ErrorDTO> validateGameTittle() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(titulo)) {
            errores.add(new ErrorDTO("Titulo", TipoError.REQUERIDO));
        }
        if (titulo.length() > MAX_TITULO) {
            errores.add(new ErrorDTO("Titulo", TipoError.VALOR_DEMASIADO_ALTO));
        }
        return errores;
    }

    /**
     * Valida que la descripcion del juego se haya introducido correctamente
     *
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacia
     *
     */
    private List<ErrorDTO> validateDescripcion() {
        List<ErrorDTO> errores = new ArrayList<>();

            if (!Util.checkCadenaBlankOrEmpty(descripcion)) {
            if (descripcion.length() > MAX_DESCRIPCION) {
                errores.add(new ErrorDTO("Descripcion", TipoError.VALOR_DEMASIADO_ALTO));
            }
        }
        return errores;
    }

    /**
     * Valida que el desarrollador del juego se haya introducido correctamente
     *
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacia
     *
     */
    private List<ErrorDTO> validateDesarrollador() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(desarrollador)) {
            errores.add(new ErrorDTO("Desarrollador", TipoError.REQUERIDO));
        }
        if (desarrollador.length() < MIN_DEV) {
            errores.add(new ErrorDTO("Desarrollador", TipoError.VALOR_DEMASIADO_BAJO));
        }
        if (desarrollador.length() > MAX_DEV) {
            errores.add(new ErrorDTO("Desarrollador", TipoError.VALOR_DEMASIADO_ALTO));
        }
        return errores;
    }

    /**
     * Valida que la fecha de lanzamiento del juego se haya introducido correctamente
     *
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacia
     *
     */
    private List<ErrorDTO> validateFechaLanzamiento() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (fechaLanzamiento == null) {
            errores.add(new ErrorDTO("FechaLanzamiento", TipoError.REQUERIDO));
        } else if (fechaLanzamiento.isBefore(LocalDate.now())) {
            errores.add(new ErrorDTO("FechaLanzamiento", TipoError.VALOR_DEMASIADO_BAJO));
        }
        return errores;
    }

    /**
     * Valida que el precio base del juego se haya introducido correctamente
     *
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacia
     *
     */
    private List<ErrorDTO> validatePrecioBase() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (precioBase < MIN_PRECIO_BASE) {
            errores.add(new ErrorDTO("PrecioBase", TipoError.VALOR_DEMASIADO_BAJO));
        }
        if (precioBase > MAX_PRECIO_BASE) {
            errores.add(new ErrorDTO("PrecioBase", TipoError.VALOR_DEMASIADO_ALTO));

        }
        var value = new BigDecimal(String.valueOf(precioBase));

        if (value.stripTrailingZeros().scale() > DECIMALES) {
            errores.add(new ErrorDTO("PrecioBase", TipoError.FORMATO_INVALIDO));
        }

        return errores;
    }


    /**
     * Valida que la clasificacion del juego se haya introducido correctamente
     *
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacia
     *
     */
    private List<ErrorDTO> validateClasificacionEdad() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(ClasificacionEdad.name())) {
            errores.add(new ErrorDTO("ClasificacionEdad", TipoError.REQUERIDO));
        }
        return errores;
    }

    /**
     * Valida que los idiomas del juego se haya introducido correctamente
     *
     * @return Lista de errores
     *
     */
    private List<ErrorDTO> validateIdiomas() {
        List<ErrorDTO> errores = new ArrayList<>();
        List<String> idiomas;

        //Si el array no es null en la posicion cero es que el usuario le puso idiomas al juego, y si no es que esta vacio
        if (idiomasDisponibles.getFirst() != null) {
            idiomas = idiomasDisponibles.stream()
                    .filter(l -> l.length() > MAX_LANG)
                    .toList();
            if (!idiomas.isEmpty()) {
                errores.add(new ErrorDTO("Idiomas", TipoError.FORMATO_INVALIDO));
            }
        }
        return errores;
    }
}
