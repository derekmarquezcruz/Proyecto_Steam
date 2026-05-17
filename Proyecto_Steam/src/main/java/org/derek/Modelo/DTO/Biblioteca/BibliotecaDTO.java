package org.derek.Modelo.DTO.Biblioteca;

import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record BibliotecaDTO(

     Long idUsuario,
     Long idJuego,
     UsuarioDTO usuario,
     JuegoDTO juego,
     LocalDate fechaAdquisicion,
     double horasTotalesJugadas,
     LocalDateTime tiempoJugado,
     List<String> LenguajesDisponibles,
     EstadoInstalacion estadoInstalacion){

    
}
