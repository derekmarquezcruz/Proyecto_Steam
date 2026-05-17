package org.derek.Repositorio.Hibernate;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.derek.Modelo.Entidad.Pais;
import org.derek.Repositorio.Interfaces.IPaisRepo;
import org.derek.Transaccion.IAdministradorSesion;

import java.util.List;
import java.util.Optional;

public class PaisRepoHibernate implements IPaisRepo {

    private final IAdministradorSesion gestorSesion;

    public PaisRepoHibernate(IAdministradorSesion gestorSesion) {
        this.gestorSesion = gestorSesion;
    }

    @Override
    public Optional<Pais> crear(String nombrePais) {
        var session = gestorSesion.getSesion();

        var pais = new Pais(null, nombrePais);
        session.persist(pais);

        return Optional.of(pais);
    }

    @Override
    public Optional<Pais> obtenerPorId(Long id) {
        var session = gestorSesion.getSesion();

        var pais = session.find(Pais.class, id);
        return Optional.ofNullable(pais);
    }

    @Override
    public List<Pais> obtenerTodos() {
        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pais> cq = cb.createQuery(Pais.class);
        Root<Pais> root = cq.from(Pais.class);

        cq.select(root);

        return session.createQuery(cq).getResultList();
    }



    @Override
    public Optional<Pais> update(Long id, Pais form) {
        var session = gestorSesion.getSesion();

        Optional<Pais> paisOpt = obtenerPorId(id);

        if (paisOpt.isEmpty()) {
            return Optional.empty();
        }

        Pais paisActualizado = new Pais(id, form.getNombre());
        session.merge(paisActualizado);

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {
        var session = gestorSesion.getSesion();

        Optional<Pais> paisOpt = obtenerPorId(id);

        if (paisOpt.isEmpty()) {
            return false;
        }

        session.remove(paisOpt.get());
        return true;
    }
}
