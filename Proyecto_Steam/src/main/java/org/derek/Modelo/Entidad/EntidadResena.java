package org.derek.Modelo.Entidad;

import jakarta.persistence.*;
import org.derek.Modelo.DTO.Resena.EstadoResena;

import java.time.LocalDate;

@Entity
@Table(name = "resena")
public class EntidadResena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_juego")
    private Long idJuego;

    @Column(name = "recomendado")
    private boolean recomendado;

    @Column(name = "texto_resena", length = 8000)
    private String textoResena;

    @Column(name = "horas_jugadas")
    private Double horasJugadas;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "fecha_ultima_edicion")
    private LocalDate fechaUltimaEdicion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoResena estado;


    public EntidadResena() {
    }

    // Constructor creación
    public EntidadResena(Long idUsuario,
                         Long idJuego,
                         boolean recomendado,
                         String textoResena,
                         Double horasJugadas) {

        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.textoResena = textoResena;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = LocalDate.now();
        this.fechaUltimaEdicion = LocalDate.now();
        this.estado = EstadoResena.PUBLICADA;
    }

    // Constructor completo
    public EntidadResena(Long idResena,
                         Long idUsuario,
                         Long idJuego,
                         boolean recomendado,
                         String textoResena,
                         Double horasJugadas,
                         LocalDate fechaPublicacion,
                         LocalDate fechaUltimaEdicion,
                         EstadoResena estado) {

        this.idResena = idResena;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.textoResena = textoResena;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltimaEdicion = fechaUltimaEdicion;
        this.estado = estado;
    }

    public EntidadResena(Long idUsuario, Long idJuego, boolean recomendado, String textoResena, Long aLong) {
    }


    public EntidadResena(Long id, Long idUsuario, Long idJuego, boolean recomendado, String textoResena, Long aLong, LocalDate fechaPublicacion, LocalDate fechaUltimaEdicion, EstadoResena estado) {
    }

    public EntidadResena(Long aLong, Long aLong1, Long aLong2, boolean recomendado, String s, Long aLong3) {
    }


    // GETTERS
    public Long getIdResena() { return idResena; }
    public Long getIdUsuario() { return idUsuario; }
    public Long getIdJuego() { return idJuego; }
    public boolean isRecomendado() { return recomendado; }
    public String getTextoResena() { return textoResena; }
    public Double getHorasJugadas() { return horasJugadas; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public LocalDate getFechaUltimaEdicion() { return fechaUltimaEdicion; }
    public EstadoResena getEstado() { return estado; }

    // SETTERS
    public void setIdResena(Long idResena) { this.idResena = idResena; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public void setRecomendado(boolean recomendado) { this.recomendado = recomendado; }
    public void setTextoResena(String textoResena) { this.textoResena = textoResena; }
    public void setHorasJugadas(Double horasJugadas) { this.horasJugadas = horasJugadas; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public void setFechaUltimaEdicion(LocalDate fechaUltimaEdicion) { this.fechaUltimaEdicion = fechaUltimaEdicion; }
    public void setEstado(EstadoResena estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "EntidadResena{" +
                "idResena=" + idResena +
                ", idUsuario=" + idUsuario +
                ", idJuego=" + idJuego +
                ", recomendado=" + recomendado +
                ", textoResena='" + textoResena + '\'' +
                ", horasJugadas=" + horasJugadas +
                ", fechaPublicacion=" + fechaPublicacion +
                ", fechaUltimaEdicion=" + fechaUltimaEdicion +
                ", estado=" + estado +
                '}';
    }
}