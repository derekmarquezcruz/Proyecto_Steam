package org.derek.Repositorio.Hibernate;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.derek.Modelo.Entidad.EntidadUsuario;
import org.derek.Modelo.Form.FormUsuario;
import org.derek.Modelo.Form.Updates.UsuarioUpdate;
import org.derek.Repositorio.Interfaces.IUsuarioRepo;
import org.derek.Transaccion.IAdministradorSesion;

import java.util.List;
import java.util.Optional;

public class UsuarioRepoHibernate implements IUsuarioRepo {

    private final IAdministradorSesion gestorSesion;

    public UsuarioRepoHibernate(IAdministradorSesion gestorSesion) {
        this.gestorSesion = gestorSesion;
    }

    @Override
    public Optional<EntidadUsuario> crear(FormUsuario form) {

        var session = gestorSesion.getSesion();

        EntidadUsuario usuario = new EntidadUsuario(
                null,
                form.nombreUsuario(),
                form.email(),
                form.contrasena(),
                form.nombreReal(),
                form.pais(),
                form.fechaNacimiento(),
                form.avatar(),
                0
        );

        session.persist(usuario);
        return Optional.of(usuario);
    }

    @Override
    public Optional<EntidadUsuario> obtenerPorId(Long id) {

        var session = gestorSesion.getSesion();

        return Optional.ofNullable(session.find(EntidadUsuario.class, id));
    }

    @Override
    public List<EntidadUsuario> obtenerTodos() {

        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadUsuario> cq = cb.createQuery(EntidadUsuario.class);
        Root<EntidadUsuario> root = cq.from(EntidadUsuario.class);

        cq.select(root);

        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<EntidadUsuario> update(Long id, UsuarioUpdate form) {

        var session = gestorSesion.getSesion();

        Optional<EntidadUsuario> usuarioOpt = obtenerPorId(id);

        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        EntidadUsuario usuarioActualizado = new EntidadUsuario(
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

        session.merge(usuarioActualizado);

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {

        var session = gestorSesion.getSesion();

        Optional<EntidadUsuario> usuarioOpt = obtenerPorId(id);

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        session.remove(usuarioOpt.get());
        return true;
    }
}
