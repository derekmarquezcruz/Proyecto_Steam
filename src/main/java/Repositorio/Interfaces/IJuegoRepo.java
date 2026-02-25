package Repositorio.Interfaces;

import Modelo.DTO.Juego.EstadoJuego;
import Modelo.Entidad.EntidadJuego;
import Modelo.Form.FormJuego;

import java.util.Optional;

public interface IJuegoRepo extends ICrud<EntidadJuego, FormJuego, Long> {

    EntidadJuego actualizar(Long id, Optional<EstadoJuego> estado, Optional<Integer> descuento);
}