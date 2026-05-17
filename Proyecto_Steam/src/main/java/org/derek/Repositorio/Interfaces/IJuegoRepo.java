package org.derek.Repositorio.Interfaces;

import org.derek.Modelo.Entidad.EntidadJuego;
import org.derek.Modelo.Form.FormJuego;
import org.derek.Modelo.Form.Updates.JuegoUpdate;

import java.util.Optional;

public interface IJuegoRepo extends ICrud<EntidadJuego, FormJuego, JuegoUpdate, Long> {

    public Optional<EntidadJuego> encontrarPorNombre(String name);

    Optional<EntidadJuego> buscarPorNombre(String titulo);
}
