package Modelo.DTO.Resena;

import java.time.LocalDateTime;

public class ResenaDTO {

    private Long idResena;
    private int idUsuario;
    private int idJuego;
    private boolean recomendado;
    private String textoResena;
    private double horasJugadas;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime fechaUltimaEdicion;
    private EstadoResena estadoResena;

    // Constructor
    public ResenaDTO(Long idResena, int idUsuario, int idJuego, boolean recomendado, String textoResena, double horasJugadas, LocalDateTime fechaPublicacion, LocalDateTime fechaUltimaEdicion, EstadoResena estadoResena) {
        this.idResena = idResena;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.textoResena = textoResena;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltimaEdicion = fechaUltimaEdicion;
        this.estadoResena = estadoResena;
    }

    // Getters
    public Long getIdResena() {
        return idResena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public String getTextoResena() {
        return textoResena;
    }

    public double getHorasJugadas() {
        return horasJugadas;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public LocalDateTime getFechaUltimaEdicion() {
        return fechaUltimaEdicion;
    }

    public EstadoResena getEstadoResena() {
        return estadoResena;
    }


    // Setters
    public void setTextoResena(String textoResena) {
        this.textoResena = textoResena;
    }

    public void setHorasJugadas(double horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public void setFechaUltimaEdicion(LocalDateTime fechaUltimaEdicion) {
        this.fechaUltimaEdicion = fechaUltimaEdicion;
    }

    public void setEstadoResena(EstadoResena estadoResena) {
        this.estadoResena = estadoResena;
    }
}