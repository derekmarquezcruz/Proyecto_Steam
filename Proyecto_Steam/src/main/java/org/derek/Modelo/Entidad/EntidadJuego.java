package org.derek.Modelo.Entidad;

import jakarta.persistence.*;
import org.derek.Modelo.DTO.Juego.ClasificacionEdad;
import org.derek.Modelo.DTO.Juego.EstadoJuego;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "juegos")
public class EntidadJuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJuego;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "desarrollador")
    private String desarrollador;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    @Column(name = "precio_base")
    private float precioBase;

    @Column(name = "descuento_actual")
    private int descuentoActual;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "clasificacion_edad")
    private ClasificacionEdad clasificacionEdad;

    @Column(name = "idiomas")
    private List<String> idiomasDisponibles;

    @Column(name = "estado")
    private EstadoJuego estado;


    public EntidadJuego() {
    }

    // Constructor creación
    public EntidadJuego(String titulo,
                        String descripcion,
                        String desarrollador,
                        LocalDate fechaLanzamiento,
                        float precioBase,
                        String categoria,
                        ClasificacionEdad clasificacionEdad,
                        List<String> idiomasDisponibles) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.descuentoActual = 0;
        this.categoria = categoria;
        this.clasificacionEdad = clasificacionEdad;
        this.idiomasDisponibles = idiomasDisponibles;
        this.estado = EstadoJuego.DISPONIBLE;
    }

    // Constructor completo
    public EntidadJuego(Long idJuego,
                        String titulo,
                        String descripcion,
                        String desarrollador,
                        LocalDate fechaLanzamiento,
                        float precioBase,
                        int descuentoActual,
                        String categoria,
                        ClasificacionEdad clasificacionEdad,
                        List<String> idiomasDisponibles,
                        EstadoJuego estado) {

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
        this.estado = estado;
    }

    public EntidadJuego(String titulo, String titulo1, String descripcion, String desarrollador, LocalDate localDate, float v, ClasificacionEdad clasificacionEdad, List<String> idiomasDisponibles) {
    }

    public EntidadJuego(Long aLong, String titulo, String descripcion, String desarrollador, LocalDate localDate, float v, String categoria, ClasificacionEdad clasificacionEdad, List<String> strings) {
    }


    // GETTERS
    public Long getIdJuego() { return idJuego; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getDesarrollador() { return desarrollador; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public float getPrecioBase() { return precioBase; }
    public int getDescuentoActual() { return descuentoActual; }
    public String getCategoria() { return categoria; }
    public ClasificacionEdad getClasificacionEdad() { return clasificacionEdad; }
    public List<String> getIdiomasDisponibles() { return idiomasDisponibles; }
    public EstadoJuego getEstado() { return estado; }

    // SETTERS
    public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setDesarrollador(String desarrollador) { this.desarrollador = desarrollador; }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
    public void setPrecioBase(float precioBase) { this.precioBase = precioBase; }
    public void setDescuentoActual(int descuentoActual) { this.descuentoActual = descuentoActual; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setClasificacionEdad(ClasificacionEdad clasificacionEdad) { this.clasificacionEdad = clasificacionEdad; }
    public void setIdiomasDisponibles(List<String> idiomasDisponibles) { this.idiomasDisponibles = idiomasDisponibles; }
    public void setEstado(EstadoJuego estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "EntidadJuego{" +
                "idJuego=" + idJuego +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", desarrollador='" + desarrollador + '\'' +
                ", fechaLanzamiento=" + fechaLanzamiento +
                ", precioBase=" + precioBase +
                ", descuentoActual=" + descuentoActual +
                ", categoria='" + categoria + '\'' +
                ", clasificacionEdad=" + clasificacionEdad +
                ", idiomasDisponibles=" + idiomasDisponibles +
                ", estado=" + estado +
                '}';
    }


}