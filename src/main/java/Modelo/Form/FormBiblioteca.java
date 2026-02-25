package Modelo.Form;

import Modelo.DTO.Biblioteca.EstadoInstalacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FormBiblioteca {

    private Long idBiblioteca;
    private Long idUsuario;
    private Long idJuego;
    private LocalDate fechaAdquisicion;
    private double horasTotalesJugadas;
    private LocalDateTime ultimaFechaJuego;
    private EstadoInstalacion estadoInstalacion;

    // Constructor
    public FormBiblioteca(Long idBiblioteca, Long idUsuario, Long idJuego, LocalDate fechaAdquisicion, double horasTotalesJugadas, LocalDateTime ultimaFechaJuego, EstadoInstalacion estadoInstalacion) {
        this.idBiblioteca = idBiblioteca;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.horasTotalesJugadas = horasTotalesJugadas;
        this.ultimaFechaJuego = ultimaFechaJuego;
        this.estadoInstalacion = estadoInstalacion;
    }

    // Getters y Setters
    public Long getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(Long idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(Long idJuego) {
        this.idJuego = idJuego;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public double getHorasTotalesJugadas() {
        return horasTotalesJugadas;
    }

    public void setHorasTotalesJugadas(double horasTotalesJugadas) {
        this.horasTotalesJugadas = horasTotalesJugadas;
    }

    public LocalDateTime getUltimaFechaJuego() {
        return ultimaFechaJuego;
    }

    public void setUltimaFechaJuego(LocalDateTime ultimaFechaJuego) {
        this.ultimaFechaJuego = ultimaFechaJuego;
    }

    public EstadoInstalacion getEstadoInstalacion() {
        return estadoInstalacion;
    }

    public void setEstadoInstalacion(EstadoInstalacion estadoInstalacion) {
        this.estadoInstalacion = estadoInstalacion;
    }

    /** Valida todos los campos del formulario */
    public List<String> validate() {
        List<String> errores = new ArrayList<>();

        // Usuario y juego obligatorios
        if (idUsuario == null) {
            errores.add("El usuario es obligatorio");
        }

        if (idJuego == null) {
            errores.add("El juego es obligatorio");
        }

        // Fecha de adquisición obligatoria y no futura
        if (fechaAdquisicion == null) {
            errores.add("La fecha de adquisición es obligatoria");
        }
        else if (fechaAdquisicion.isAfter(LocalDate.now())) {
            errores.add("La fecha de adquisición no puede ser futura");
        }

        // Tiempo de juego total no negativo
        if (horasTotalesJugadas < 0) {
            errores.add("El tiempo total de juego no puede ser negativo");
        }

        // Última fecha de juego no puede ser antes de la adquisición
        if (ultimaFechaJuego != null && fechaAdquisicion != null && ultimaFechaJuego.isBefore(fechaAdquisicion.atStartOfDay())) {
            errores.add("La última fecha de juego no puede ser anterior a la fecha de adquisición");
        }

        // Estado de instalación válido
        if (estadoInstalacion == null) {
            errores.add("El estado de instalación es obligatorio");
        }
        else if (!estadoInstalacion.equals("INSTALADO") && !estadoInstalacion.equals("NO_INSTALADO")) {
            errores.add("Estado de instalación inválido");
        }

        return errores;
    }
}