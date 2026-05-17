package org.derek.Repositorio.Interfaces;

import org.derek.Modelo.Entidad.EntidadUsuario;
import org.derek.Modelo.Form.Updates.UsuarioUpdate;
import org.derek.Modelo.Form.FormUsuario;

public interface IUsuarioRepo extends ICrud<EntidadUsuario, FormUsuario, UsuarioUpdate, Long> {

}
