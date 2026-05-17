package org.derek.Modelo.Form.Updates;

import org.derek.Modelo.DTO.Biblioteca.EstadoInstalacion;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record BibiliotecaUpdate(
        Long idBiblioteca,
        Long idUsuario,
        Long idJuego,
        LocalDate fechaAdquisicion,
        Long tiempoJugado,
        LocalDateTime ultimaSesion,
        EstadoInstalacion estadoInstalacion) {
}
