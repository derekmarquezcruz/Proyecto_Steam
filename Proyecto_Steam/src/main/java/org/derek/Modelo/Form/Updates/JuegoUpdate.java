package org.derek.Modelo.Form.Updates;

import org.derek.Modelo.DTO.Juego.ClasificacionEdad;
import org.derek.Modelo.DTO.Juego.EstadoJuego;

import java.time.LocalDate;
import java.util.List;

public record JuegoUpdate (
        Long idJuego,
        String titulo,
        String descripcion,
        String desarrollador,
        LocalDate fechaLanzamiento,
        float precioBase,
        int descuentoActual,
        String categoria,
        ClasificacionEdad clasificacionEdad,
        List<String> idiomasDisponibles,
        EstadoJuego estado){
}
