package Modelo.Entidad;

import Modelo.DTO.Biblioteca.EstadoInstalacion;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EntidadBiblioteca {

    private Long idBiblioteca;
    private Long idUsuario;
    private Long idJuego;
    private LocalDate fechaAdquisicion;
    private Duration horasTotalesJugadas;
    private LocalDateTime ultimaFechaJuego;
    private EstadoInstalacion estadoInstalacion;

    // Constructor
    public EntidadBiblioteca(Long idBiblioteca, Long idUsuario, Long idJuego, LocalDate fechaAdquisicion, double horasTotalesJugadas, LocalDateTime ultimaFechaJuego, EstadoInstalacion estadoInstalacion) {
        this.idBiblioteca = idBiblioteca;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.horasTotalesJugadas = Duration.ofHours(0);
        this.ultimaFechaJuego = ultimaFechaJuego;
        this.estadoInstalacion = estadoInstalacion.NO_INSTALADO;
    }

    public EntidadBiblioteca(Long idBiblioteca, Long idUsuario, Long idJuego, LocalDate fechaAdquisicion, LocalDateTime ultimaFechaJuego) {
        this.idBiblioteca = idBiblioteca;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.horasTotalesJugadas = Duration.ofHours(0);
    }

    // Getters
    public Long getIdBiblioteca() {
        return idBiblioteca;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public Long getIdJuego() {
        return idJuego;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public Duration getHorasTotalesJugadas() {
        return horasTotalesJugadas;
    }

    public LocalDateTime getUltimaFechaJuego() {
        return ultimaFechaJuego;
    }

    public EstadoInstalacion getEstadoInstalacion() {
        return estadoInstalacion;
    }

    // Setters
    public void setHorasTotalesJugadas(Duration horasTotalesJugadas) {
        this.horasTotalesJugadas = horasTotalesJugadas;
    }

    public void setUltimaFechaJuego(LocalDateTime ultimaFechaJuego) {
        this.ultimaFechaJuego = ultimaFechaJuego;
    }

    public void setEstadoInstalacion(EstadoInstalacion estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }
}