package Repositorio.EnMemoria;

import Controlador.ExcepcionGenerica;
import Modelo.DTO.Usuario.EstadoCuenta;
import Modelo.Entidad.EntidadUsuario;
import Modelo.Form.FormUsuario;
import Repositorio.Interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioEnMemoriaRepo implements IUsuarioRepo {

    private final List<EntidadUsuario> usuarios = new ArrayList<>();
    private static Long idCounter = 1L;

    @Override
    public Optional<EntidadUsuario> crear(FormUsuario form) {
        EntidadUsuario user = new EntidadUsuario(idCounter++, form.getNombreUsuario(), form.getEmail(), form.getContrasena(), form.getNombreReal(), form.getPais(), form.getFechaNacimiento(), form.getAvatar(), 0, EstadoCuenta.ACTIVA);
        usuarios.add(user);

        return Optional.of(user);
    }

    @Override
    public Optional<EntidadUsuario> obtenerPorId(Long id) {
        return usuarios.stream().filter(u -> u.getIdUsuario().equals(id)).findFirst();
    }

    @Override
    public List<EntidadUsuario> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public EntidadUsuario actualizarSaldoCartera(Long id, Optional<Float> montoOpt) {
        // Busco el usuario
        EntidadUsuario usuario = obtenerPorId(id)
                .orElseThrow(() -> new ExcepcionGenerica("El usuario no existe"));

        // Compruebo que la cuenta esté activa
        if (!usuario.getEstadoCuenta().equals(EstadoCuenta.ACTIVA)) {
            throw new ExcepcionGenerica("La cuenta del usuario no está activa");
        }

        // Obtengo el monto a añadir
        float monto = montoOpt.orElseThrow(() -> new ExcepcionGenerica("No se proporcionó un valor para recargar"));

        // Verifico que el monto esté entre 5 y 500
        if (monto < 5 || monto > 500) {
            throw new ExcepcionGenerica("El monto a recargar debe estar entre 5 y 500");
        }

        // Sumo el monto al saldo actual
        float nuevoSaldo = usuario.getSaldoCartera() + monto;

        // Creo un nuevo objeto usuario con el saldo actualizado
        EntidadUsuario usuarioActualizado = new EntidadUsuario(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getEmail(), usuario.getContrasena(), usuario.getNombreReal(), usuario.getPais(), usuario.getFechaNacimiento(), usuario.getAvatar(), nuevoSaldo, EstadoCuenta.ACTIVA
        );

        // Reemplazo el usuario antiguo por el actualizado
        usuarios.removeIf(u -> u.getIdUsuario().equals(id));
        usuarios.add(usuarioActualizado);

        return usuarioActualizado;
    }

    @Override
    public boolean eliminar(Long id) {
        return usuarios.removeIf(u -> u.getIdUsuario().equals(id));
    }
}