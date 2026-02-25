package Modelo.Form;

import Modelo.DTO.Juego.CategoriaJuego;
import Modelo.DTO.Juego.ClasificacionEdad;
import Modelo.DTO.Juego.EstadoJuego;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FormJuego {

    private Long idJuego;
    private String titulo;
    private String descripcion;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private float precioBase;
    private float descuentoActual;
    private CategoriaJuego categoria;
    private ClasificacionEdad clasificacionEdad;
    private String[] idiomasDisponibles;
    private EstadoJuego estadoJuego;

    // Constructor
    public FormJuego(Long idJuego, String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, float precioBase, float descuentoActual, CategoriaJuego categoria, ClasificacionEdad clasificacionEdad, String[] idiomasDisponibles, EstadoJuego estadoJuego) {
        this.idJuego = idJuego;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.categoria = categoria;
        this.clasificacionEdad = clasificacionEdad;
        this.idiomasDisponibles = idiomasDisponibles;
        this.estadoJuego = estadoJuego;
    }

    // Getters y Setters
    public Long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(Long idJuego) {
        this.idJuego = idJuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public float getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(float precioBase) {
        this.precioBase = precioBase;
    }

    public float getDescuentoActual() {
        return descuentoActual;
    }

    public void setDescuentoActual(float descuentoActual) {
        this.descuentoActual = descuentoActual;
    }

    public CategoriaJuego getCategoria() {
        return categoria; }

    public void setCategoria(CategoriaJuego categoria) {
        this.categoria = categoria;
    }

    public ClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(ClasificacionEdad clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }

    public String[] getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public void setIdiomasDisponibles(String[] idiomasDisponibles) {
        this.idiomasDisponibles = idiomasDisponibles;
    }

    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    /**
     * Valida todos los campos del formulario de juego
     * @return Lista de errores encontrados, vacía si está correcto
     */
    public List<String> validate() {

        List<String> errores = new ArrayList<>();

        errores.addAll(validateTitulo());

        errores.addAll(validateDescripcion());

        errores.addAll(validateDesarrollador());

        errores.addAll(validateFechaLanzamiento());

        errores.addAll(validatePrecioBase());

        errores.addAll(validateCategoria());

        errores.addAll(validateClasificacionEdad());

        errores.addAll(validateIdiomas());

        return errores;
    }
    /** Valida que el título del juego no esté vacío y tenga menos de 100 caracteres */
    private List<String> validateTitulo() {
        List<String> errores = new ArrayList<>();

        if(titulo == null || titulo.isBlank()) {
            errores.add("El título del juego es obligatorio");
        }

        else if(titulo.length() > 100) {
            errores.add("El título es demasiado largo");
        }

        return errores;
    }

    /** Valida que el título del juego no esté vacío y tenga menos de 100 caracteres */
    private List<String> validateDescripcion() {
        List<String> errores = new ArrayList<>();

        if(descripcion != null && descripcion.length() > 2000) {
            errores.add("La descripción es demasiado larga");
        }

        return errores;
    }

    /** Valida que la descripción no supere 2000 caracteres */
    private List<String> validateDesarrollador() {
        List<String> errores = new ArrayList<>();

        if(desarrollador == null || desarrollador.isBlank()) {
            errores.add("El desarrollador es obligatorio");
        }

        else if(desarrollador.length() < 2) {
            errores.add("El nombre del desarrollador es demasiado corto");
        }

        else if(desarrollador.length() > 100) {
            errores.add("El nombre del desarrollador es demasiado largo");
        }

        return errores;
    }

    /** Valida fecha de lanzamiento obligatoria y que no pueda ser pasada */

    private List<String> validateFechaLanzamiento() {
        List<String> errores = new ArrayList<>();

        if(fechaLanzamiento == null) {
            errores.add("La fecha de lanzamiento es obligatoria");
        }
        else if(fechaLanzamiento.isBefore(LocalDate.now())) {
            errores.add("La fecha de lanzamiento no puede ser pasada");
        }

        return errores;
    }

    /** Valida precio base obligatorio, rango válido y máximo 2 decimales */
    private List<String> validatePrecioBase() {
        List<String> errores = new ArrayList<>();

        if(precioBase < 0 || precioBase > 999.99) {
            errores.add("El precio base es inválido");
        }

        BigDecimal value = BigDecimal.valueOf(precioBase);

        if(value.scale() > 2) {
            errores.add("El precio solo puede tener 2 decimales");
        }

        return errores;
    }

    /** Valida que la categoría no sea nula */
    private List<String> validateCategoria() {
        List<String> errores = new ArrayList<>();

        if(categoria == null) errores.add("La categoría es obligatoria");

        return errores;
    }

    /** Valida que la clasificación por edad no sea nula */
    private List<String> validateClasificacionEdad() {
        List<String> errores = new ArrayList<>();

        if(clasificacionEdad == null) {
            errores.add("La clasificación por edad es obligatoria");
        }

        return errores;
    }

    /** Valida que haya al menos un idioma disponible */
    private List<String> validateIdiomas() {
        List<String> errores = new ArrayList<>();

        if(idiomasDisponibles == null) {
            errores.add("Debe especificar al menos un idioma disponible");
        }

        return errores;
    }
}