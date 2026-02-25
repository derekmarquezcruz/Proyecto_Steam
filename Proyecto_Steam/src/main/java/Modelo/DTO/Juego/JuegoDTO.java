package Modelo.DTO.Juego;

import java.time.LocalDate;

public class JuegoDTO {

    private Long idJuego;
    private String titulo;
    private String descripcion;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private double precioBase;
    private int descuentoActual;
    private CategoriaJuego categoria;
    private ClasificacionEdad clasificacionEdad;
    private String[] idiomasDisponibles;
    private EstadoJuego estadoJuego;

    // Constructor único
    public JuegoDTO(Long idJuego, String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, double precioBase, int descuentoActual, CategoriaJuego categoria, ClasificacionEdad clasificacionEdad, String[] idiomasDisponibles, EstadoJuego estadoJuego) {
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

    public double getPrecioBase() {
        return precioBase;
    }

    public int getDescuentoActual() {
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

    // Setters
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
}