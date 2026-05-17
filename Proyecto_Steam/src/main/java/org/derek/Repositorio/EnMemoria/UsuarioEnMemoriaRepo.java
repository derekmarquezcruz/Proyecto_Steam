package org.derek.Repositorio.EnMemoria;

import org.derek.Modelo.Entidad.EntidadUsuario;
import org.derek.Modelo.Form.Updates.UsuarioUpdate;
import org.derek.Modelo.Form.FormUsuario;
import org.derek.Repositorio.Interfaces.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioEnMemoriaRepo implements IUsuarioRepo {

    private static final List<EntidadUsuario> USERS = new ArrayList<>();
    private static Long NEXT_ID = 1L;

    @Override
    public Optional<EntidadUsuario> crear(FormUsuario form) {
        var user = new EntidadUsuario(
                NEXT_ID++,
                form.nombreUsuario(),
                form.email(),
                form.contrasena(),
                form.nombreReal(),
                form.pais(),
                form.fechaNacimiento(),
                form.avatar(),
                0
        );

        USERS.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<EntidadUsuario> obtenerPorId(Long id) {
        return USERS.stream()
                .filter(u -> u.getIdUsuario().equals(id))
                .findFirst();
    }

    @Override
    public List<EntidadUsuario> obtenerTodos() {
        return new ArrayList<>(USERS);
    }

    @Override
    public Optional<EntidadUsuario> update(Long id, UsuarioUpdate form) {

        var user = obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        var userUpdated = new EntidadUsuario(
                id,
                form.nombreUsuario(),
                form.email(),
                form.contrasena(),
                form.nombreReal(),
                form.pais(),
                form.fechaNacimiento(),
                form.fechaRegistro(),
                form.avatar(),
                form.saldoCartera(),
                form.estadoCuenta()
        );

        USERS.removeIf(u -> u.getIdUsuario().equals(id));
        USERS.add(userUpdated);

        return Optional.of(userUpdated);
    }

    @Override
    public boolean eliminar(Long id) {
        return USERS.removeIf(u -> u.getIdUsuario().equals(id));
    }
}