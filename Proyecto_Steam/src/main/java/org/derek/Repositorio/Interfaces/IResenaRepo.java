package org.derek.Repositorio.Interfaces;

import org.derek.Modelo.Entidad.EntidadResena;
import org.derek.Modelo.Form.FormResena;
import org.derek.Modelo.Form.Updates.ResenaUpdate;

import java.util.List;
import java.util.Optional;

public interface IResenaRepo extends ICrud<EntidadResena, FormResena, ResenaUpdate, Long>{
    Optional<EntidadResena> getByUserGameId(Long idUser, Long idGame);

    List<EntidadResena> getPoridJuego(Long id);

    List<EntidadResena> getPoridUsuario(Long id);
}
