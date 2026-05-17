package org.derek.Repositorio.Hibernate;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.derek.Modelo.Entidad.EntidadResena;
import org.derek.Modelo.Form.FormResena;
import org.derek.Modelo.Form.Updates.ResenaUpdate;
import org.derek.Repositorio.Interfaces.IResenaRepo;
import org.derek.Transaccion.IAdministradorSesion;

import java.util.List;
import java.util.Optional;

public class ResenaRepoHibernate implements IResenaRepo {

    private final IAdministradorSesion gestorSesion;

    public ResenaRepoHibernate(IAdministradorSesion gestorSesion) {
        this.gestorSesion = gestorSesion;
    }

    @Override
    public Optional<EntidadResena> getByUserGameId(Long idUsuario, Long idJuego) {

        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadResena> cq = cb.createQuery(EntidadResena.class);
        Root<EntidadResena> root = cq.from(EntidadResena.class);

        cq.select(root).where(
                cb.and(
                        cb.equal(root.get("idUsuario"), idUsuario),
                        cb.equal(root.get("idJuego"), idJuego)
                )
        );

        return session.createQuery(cq).uniqueResultOptional();
    }

    @Override
    public List<EntidadResena> getPoridJuego(Long idJuego) {

        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadResena> cq = cb.createQuery(EntidadResena.class);
        Root<EntidadResena> root = cq.from(EntidadResena.class);

        cq.select(root).where(cb.equal(root.get("idJuego"), idJuego));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public List<EntidadResena> getPoridUsuario(Long idUsuario) {

        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadResena> cq = cb.createQuery(EntidadResena.class);
        Root<EntidadResena> root = cq.from(EntidadResena.class);

        cq.select(root).where(cb.equal(root.get("idUsuario"), idUsuario));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<EntidadResena> crear(FormResena form) {

        var session = gestorSesion.getSesion();

        EntidadResena resena = new EntidadResena(
                form.idUsuario(),
                form.idJuego(),
                form.recomendado(),
                form.textoResena(),
                form.horasJugadas()
        );

        session.persist(resena);
        return Optional.of(resena);
    }

    @Override
    public Optional<EntidadResena> obtenerPorId(Long id) {

        var session = gestorSesion.getSesion();

        return Optional.ofNullable(session.find(EntidadResena.class, id));
    }

    @Override
    public List<EntidadResena> obtenerTodos() {

        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadResena> cq = cb.createQuery(EntidadResena.class);
        Root<EntidadResena> root = cq.from(EntidadResena.class);

        cq.select(root);

        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<EntidadResena> update(Long id, ResenaUpdate form) {

        var session = gestorSesion.getSesion();

        Optional<EntidadResena> resenaOpt = obtenerPorId(id);

        if (resenaOpt.isEmpty()) {
            return Optional.empty();
        }

        EntidadResena resenaActualizada = new EntidadResena(
                id,
                form.ididUsuario(),
                form.idJuego(),
                form.recomendado(),
                form.textoResena(),
                form.horasJugadas(),
                form.fechaPublicacion(),
                form.fechaUltimaEdicion(),
                form.estado()
        );

        session.merge(resenaActualizada);

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {

        var session = gestorSesion.getSesion();

        Optional<EntidadResena> resenaOpt = obtenerPorId(id);

        if (resenaOpt.isEmpty()) {
            return false;
        }

        session.remove(resenaOpt.get());
        return true;
    }
}