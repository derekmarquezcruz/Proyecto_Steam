package org.derek.Modelo.DTO.Resena;

import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;

import java.time.LocalDateTime;

public record ResenaDTO (

    Long idResena,
    int idUsuario,
    int idJuego,
    UsuarioDTO usuario,
    JuegoDTO juego,
    boolean recomendado,
    String textoResena,
    Long horasJugadas,
    LocalDateTime fechaPublicacion,
    LocalDateTime fechaUltimaEdicion,
    EstadoResena estadoResena){

}

