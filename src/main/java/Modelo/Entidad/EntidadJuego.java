package Modelo.Entidad;

import Modelo.DTO.Juego.CategoriaJuego;
import Modelo.DTO.Juego.ClasificacionEdad;
import Modelo.DTO.Juego.EstadoJuego;

import java.time.LocalDate;

public class EntidadJuego {

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
    public EntidadJuego(Long idJuego, String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, float precioBase, float descuentoActual, CategoriaJuego categoria, ClasificacionEdad clasificacionEdad, String[] idiomasDisponibles, EstadoJuego estadoJuego) {
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
        this.estadoJuego = EstadoJuego.DISPONIBLE;
    }

    // Getters
    public Long getIdJuego() {
        return idJuego;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public float getPrecioBase() {
        return precioBase;
    }

    public float getDescuentoActual() {
        return descuentoActual;
    }

    public CategoriaJuego getCategoria() {
        return categoria;
    }

    public ClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public String[] getIdiomasDisponibles() {
        return idiomasDisponibles;
    }

    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }

    //Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdiomasDisponibles(String[] idiomasDisponibles) {
        this.idiomasDisponibles = idiomasDisponibles;
    }

    public void setEstadoJuego(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    public void setDescuentoActual(float precioBase) {
        this.precioBase = precioBase;
    }
}