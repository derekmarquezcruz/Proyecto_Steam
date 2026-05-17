package org.derek.Modelo.MetodoPago;

import org.derek.Controlador.Util;
import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Modelo.Entidad.EntidadUsuario;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;
import org.derek.Modelo.Form.Updates.UsuarioUpdate;
import org.derek.Repositorio.Interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;


public class CarteraSteam implements IMetodoPago {

    private IUsuarioRepo usuarioRepo;

    public CarteraSteam(IUsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public void HacerPago(float costeJuego, Long idUsuario) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();
        EntidadUsuario usuario = null;

        // Compruebo que el costeJuego y el idUsuario no sean null
        if (costeJuego <= 0) {
            errores.add(new ErrorDTO("CosteJuego", TipoError.VALOR_DEMASIADO_BAJO));
        }

        if (idUsuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        if (costeJuego > 0 && idUsuario != null) {

            // Compruebo que el usuario exista
            usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);

            if (usuario == null) {
                errores.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            } else {
                // Compruebo que el usuario tenga saldo suficiente
                if (usuario.getSaldoCartera() < costeJuego) {
                    errores.add(new ErrorDTO("SaldoCartera", TipoError.VALOR_DEMASIADO_BAJO));
                }
            }
        }

        Util.throwException(errores);

        UsuarioUpdate form = new UsuarioUpdate(
                usuario.getNombreUsuario(),
                usuario.getEmail(),
                usuario.getContrasena(),
                usuario.getNombreReal(),
                usuario.getPais(),
                usuario.getFechaNacimiento(),
                usuario.getFechaRegistro(),
                usuario.getAvatar(),
                usuario.getSaldoCartera() - costeJuego,
                usuario.getEstadoCuenta()
        );

        usuarioRepo.update(usuario.getIdUsuario(), form).orElseThrow(() -> new ExcepcionGenerica(List.of(new ErrorDTO("SaldoCartera", TipoError.NO_ACTUALIZADO)).toString()
                ));
    }
}