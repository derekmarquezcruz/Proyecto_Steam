package org.derek.Repositorio.Hibernate;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.derek.Modelo.Entidad.EntidadJuego;
import org.derek.Modelo.Form.FormJuego;
import org.derek.Modelo.Form.Updates.JuegoUpdate;
import org.derek.Repositorio.Interfaces.IJuegoRepo;
import org.derek.Transaccion.IAdministradorSesion;

import java.util.List;
import java.util.Optional;

public class JuegoRepoHibernate implements IJuegoRepo {

    private final IAdministradorSesion gestorSesion;

    public JuegoRepoHibernate(IAdministradorSesion gestorSesion) {
        this.gestorSesion = gestorSesion;
    }

    @Override
    public Optional<EntidadJuego> crear(FormJuego form) {

        var session = gestorSesion.getSesion();

        EntidadJuego juego = new EntidadJuego(
                null,
                form.titulo(),
                form.descripcion(),
                form.desarrollador(),
                form.fechaLanzamiento(),
                form.precioBase(),
                form.ClasificacionEdad(),
                form.idiomasDisponibles()
        );

        session.persist(juego);
        return Optional.of(juego);
    }

    @Override
    public Optional<EntidadJuego> obtenerPorId(Long id) {
        var session = gestorSesion.getSesion();

        EntidadJuego juego = session.find(EntidadJuego.class, id);
        return Optional.ofNullable(juego);
    }

    @Override
    public List<EntidadJuego> obtenerTodos() {
        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadJuego> cq = cb.createQuery(EntidadJuego.class);
        Root<EntidadJuego> root = cq.from(EntidadJuego.class);

        cq.select(root);

        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<EntidadJuego> update(Long id, JuegoUpdate form) {
        var session = gestorSesion.getSesion();

        Optional<EntidadJuego> juegoOpt = obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            return Optional.empty();
        }

        EntidadJuego juegoActualizado = new EntidadJuego(
                id,
                form.titulo(),
                form.descripcion(),
                form.desarrollador(),
                form.fechaLanzamiento(),
                form.precioBase(),
                form.descuentoActual(),
                form.categoria(),
                form.clasificacionEdad(),
                form.idiomasDisponibles(),
                form.estado()
        );

        session.merge(juegoActualizado);

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {
        var session = gestorSesion.getSesion();

        Optional<EntidadJuego> juegoOpt = obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            return false;
        }

        session.remove(juegoOpt.get());
        return true;
    }

    @Override
    public Optional<EntidadJuego>encontrarPorNombre(String titulo) {
        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadJuego> cq = cb.createQuery(EntidadJuego.class);
        Root<EntidadJuego> root = cq.from(EntidadJuego.class);

        cq.select(root).where(cb.equal(root.get("titulo"), titulo));

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<EntidadJuego> buscarPorNombre(String titulo) {
        return Optional.empty();
    }
}
