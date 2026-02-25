package Modelo.Form;
import Modelo.DTO.Resena.EstadoResena;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FormResena {

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
    public FormResena(Long idResena, int idUsuario, int idJuego, boolean recomendado, String textoResena,
                      double horasJugadas, LocalDateTime fechaPublicacion, LocalDateTime fechaUltimaEdicion,EstadoResena estadoResena) {
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

    // Getters y Setters
    public Long getIdResena() {
        return idResena;
    }

    public void setIdResena(Long idResena) {
        this.idResena = idResena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public String getTextoResena() {
        return textoResena;
    }

    public void setTextoResena(String textoResena) {
        this.textoResena = textoResena;
    }

    public double getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(double horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDateTime getFechaUltimaEdicion() {
        return fechaUltimaEdicion;
    }

    public void setFechaUltimaEdicion(LocalDateTime fechaUltimaEdicion) {
        this.fechaUltimaEdicion = fechaUltimaEdicion;
    }

    public EstadoResena getEstadoResena() {
        return estadoResena;
    }

    public void setEstadoResena(EstadoResena estadoResena) {
        this.estadoResena = estadoResena;
    }

    /** Valida el formulario de reseña
     * @return Lista de errores encontrados, vacía si no hay errores
     */
    public List<String> validate() {
        List<String> errores = new ArrayList<>();

        // Usuario
        if (idUsuario <= 0) {
            errores.add("ID de usuario inválido");
        }

        // Juego
        if (idJuego <= 0) {
            errores.add("ID de juego inválido");
        }


        // Texto de la reseña
        if (textoResena == null || textoResena.trim().isEmpty()) {
            errores.add("El texto de la reseña es obligatorio");
        }
        else if (textoResena.length() < 50) {
            errores.add("El texto de la reseña debe tener al menos 50 caracteres");
        }
        else if (textoResena.length() > 8000) {
            errores.add("El texto de la reseña no puede superar 8000 caracteres");
        }

        return errores;
    }
}