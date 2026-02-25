package Repositorio.Interfaces;

import Modelo.Entidad.EntidadUsuario;
import Modelo.Form.FormUsuario;

import java.util.Optional;

public interface IUsuarioRepo extends ICrud<EntidadUsuario, FormUsuario, Long> {

    EntidadUsuario actualizarSaldoCartera(Long id, Optional<Float> montoOpt);
}