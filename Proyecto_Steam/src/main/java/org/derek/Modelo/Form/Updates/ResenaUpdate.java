package org.derek.Modelo.Form.Updates;

import org.derek.Modelo.DTO.Resena.EstadoResena;

import java.time.LocalDate;

public record ResenaUpdate (
        Long idResena,
        Long ididUsuario,
        Long idJuego,
        boolean recomendado,
        String textoResena,
        Double horasJugadas,
        LocalDate fechaPublicacion,
        LocalDate fechaUltimaEdicion,
        EstadoResena estado

){
}
