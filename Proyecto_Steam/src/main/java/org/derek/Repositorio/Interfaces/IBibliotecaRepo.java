package org.derek.Repositorio.Interfaces;

import org.derek.Modelo.Entidad.EntidadBiblioteca;
import org.derek.Modelo.Form.FormBiblioteca;
import org.derek.Modelo.Form.Updates.BibiliotecaUpdate;

import java.util.Optional;

public interface IBibliotecaRepo extends ICrud <EntidadBiblioteca, FormBiblioteca, BibiliotecaUpdate, Long>{

    Optional<EntidadBiblioteca> getPorIdJuegoUsuario(Long idUser, Long idGame);
}