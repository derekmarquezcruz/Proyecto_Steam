package org.derek.Repositorio.Hibernate;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.derek.Modelo.Entidad.EntidadBiblioteca;
import org.derek.Modelo.Form.FormBiblioteca;
import org.derek.Modelo.Form.Updates.BibiliotecaUpdate;
import org.derek.Repositorio.Interfaces.IBibliotecaRepo;
import org.derek.Transaccion.IAdministradorSesion;

import java.util.List;
import java.util.Optional;

public class BibliotecaRepoHibernate implements IBibliotecaRepo {

    private final IAdministradorSesion gestorSesion;

    public BibliotecaRepoHibernate(IAdministradorSesion gestorSesion) {
        this.gestorSesion = gestorSesion;
    }

    @Override
    public Optional<EntidadBiblioteca> getPorIdJuegoUsuario(Long idUsuario, Long idJuego) {
        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadBiblioteca> cq = cb.createQuery(EntidadBiblioteca.class);
        Root<EntidadBiblioteca> root = cq.from(EntidadBiblioteca.class);

        cq.select(root).where(
                cb.and(
                        cb.equal(root.get("idUsuario"), idUsuario),
                        cb.equal(root.get("idJuego"), idJuego)
                )
        );

        return session.createQuery(cq).uniqueResultOptional();
    }

    @Override
    public Optional<EntidadBiblioteca> crear(FormBiblioteca form) {

        var session = gestorSesion.getSesion();

        EntidadBiblioteca biblioteca = new EntidadBiblioteca(
                form.idUsuario(),
                form.idJuego(),
                form.fechaAdquisicion()
        );

        session.persist(biblioteca);
        return Optional.of(biblioteca);
    }

    @Override
    public Optional<EntidadBiblioteca> obtenerPorId(Long id) {
        var session = gestorSesion.getSesion();

        return Optional.ofNullable(session.find(EntidadBiblioteca.class, id));
    }

    @Override
    public List<EntidadBiblioteca> obtenerTodos() {
        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadBiblioteca> cq = cb.createQuery(EntidadBiblioteca.class);
        Root<EntidadBiblioteca> root = cq.from(EntidadBiblioteca.class);

        cq.select(root);

        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<EntidadBiblioteca> update(Long id, BibiliotecaUpdate form) {
        var session = gestorSesion.getSesion();

        Optional<EntidadBiblioteca> bibliotecaOpt = obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            return Optional.empty();
        }

        EntidadBiblioteca bibliotecaActualizada = new EntidadBiblioteca(
                id,
                form.idUsuario(),
                form.idJuego(),
                form.fechaAdquisicion(),
                form.tiempoJugado(),
                form.ultimaSesion(),
                form.estadoInstalacion()
        );

        session.merge(bibliotecaActualizada);

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {
        var session = gestorSesion.getSesion();

        Optional<EntidadBiblioteca> bibliotecaOpt = obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            return false;
        }

        session.remove(bibliotecaOpt.get());
        return true;
    }
}