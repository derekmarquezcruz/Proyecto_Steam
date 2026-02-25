package Modelo.DTO.Biblioteca;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BibliotecaDTO {

    private Long idBiblioteca;
    private int idUsuario;
    private int idJuego;
    private LocalDate fechaAdquisicion;
    private double horasTotalesJugadas;
    private LocalDateTime ultimaFechaJuego;
    private EstadoInstalacion estadoInstalacion;

    // Constructor
    public BibliotecaDTO(Long idBiblioteca, int idUsuario, int idJuego, LocalDate fechaAdquisicion, double horasTotalesJugadas, LocalDateTime ultimaFechaJuego, EstadoInstalacion estadoInstalacion) {
        this.idBiblioteca = idBiblioteca;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.horasTotalesJugadas = horasTotalesJugadas;
        this.ultimaFechaJuego = ultimaFechaJuego;
        this.estadoInstalacion = estadoInstalacion;
    }

    // Getters
    public Long getIdBiblioteca() {
        return idBiblioteca;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public double getHorasTotalesJugadas() {
        return horasTotalesJugadas;
    }

    public LocalDateTime getUltimaFechaJuego() {
        return ultimaFechaJuego;
    }

    public EstadoInstalacion getEstadoInstalacion() {
        return estadoInstalacion;
    }

    // Setters
    public void setEstadoInstalacion(EstadoInstalacion estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }

    public void setUltimaFechaJuego(LocalDateTime ultimaFechaJuego) {
        this.ultimaFechaJuego = ultimaFechaJuego;
    }

    public void setHorasTotalesJugadas(double horasTotalesJugadas) {
        this.horasTotalesJugadas = horasTotalesJugadas;
    }
}