package org.derek.Modelo.DTO.Juego;

import java.time.LocalDate;
import java.util.List;

public record JuegoDTO(
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
    EstadoJuego estadoJuego){
}