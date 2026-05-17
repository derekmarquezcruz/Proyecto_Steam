package org.derek.Modelo.Entidad;

import jakarta.persistence.*;
import org.derek.Modelo.DTO.Biblioteca.EstadoInstalacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "bibliotecas")
public class EntidadBiblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBiblioteca;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_juego")
    private Long idJuego;

    @Column(name = "fecha_adquisicion")
    private LocalDate fechaAdquisicion;

    @Column(name = "tiempo_jugado")
    private Long tiempoJugado;

    @Column(name = "ultima_sesion")
    private LocalDateTime ultimaSesion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_instalacion")
    private EstadoInstalacion estadoInstalacion;


    public EntidadBiblioteca(Long id, Long idUsuario, Long idJuego, LocalDate fechaAdquisicion, Long tiempoJugado, Date date, EstadoInstalacion estadoInstalacion) {
    }


    public EntidadBiblioteca(Long idBiblioteca, Long idUsuario,
                             Long idJuego,
                             LocalDate fechaAdquisicion) {
        this.idBiblioteca = idBiblioteca;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJugado = 0L;
        this.ultimaSesion = null;
        this.estadoInstalacion = EstadoInstalacion.NO_INSTALADO;
    }


    public EntidadBiblioteca(Long idBiblioteca,
                             Long idUsuario,
                             Long idJuego,
                             LocalDate fechaAdquisicion,
                             Long tiempoJugado,
                             LocalDateTime ultimaSesion,
                             EstadoInstalacion estadoInstalacion) {

        this.idBiblioteca = idBiblioteca;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJugado = tiempoJugado;
        this.ultimaSesion = ultimaSesion;
        this.estadoInstalacion = estadoInstalacion;
    }

    public EntidadBiblioteca(Long aLong, Long aLong1, LocalDate localDate) {
    }

    // GETTERS
    public Long getIdBiblioteca() { return idBiblioteca; }
    public Long getIdUsuario() { return idUsuario; }
    public Long getIdJuego() { return idJuego; }
    public LocalDate getFechaAdquisicion() { return fechaAdquisicion; }
    public Long getTiempoJugado() { return tiempoJugado; }
    public LocalDateTime getUltimaSesion() { return ultimaSesion; }
    public EstadoInstalacion getEstadoInstalacion() { return estadoInstalacion; }

    // SETTERS
    public void setIdBiblioteca(Long idBiblioteca) { this.idBiblioteca = idBiblioteca; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public void setFechaAdquisicion(LocalDate fechaAdquisicion) { this.fechaAdquisicion = fechaAdquisicion; }
    public void setTiempoJugado(Long tiempoJugado) { this.tiempoJugado = tiempoJugado; }
    public void setUltimaSesion(LocalDateTime ultimaSesion) { this.ultimaSesion = ultimaSesion; }
    public void setEstadoInstalacion(EstadoInstalacion estadoInstalacion) { this.estadoInstalacion = estadoInstalacion; }

    @Override
    public String toString() {
        return "EntidadBiblioteca{" +
                "idBiblioteca=" + idBiblioteca +
                ", idUsuario=" + idUsuario +
                ", idJuego=" + idJuego +
                ", fechaAdquisicion=" + fechaAdquisicion +
                ", tiempoJugado=" + tiempoJugado +
                ", ultimaSesion=" + ultimaSesion +
                ", estadoInstalacion=" + estadoInstalacion +
                '}';
    }
}