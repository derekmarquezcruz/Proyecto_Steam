package org.derek.Repositorio.Hibernate;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.derek.Modelo.Entidad.EntidadCompra;
import org.derek.Modelo.Form.FormCompra;
import org.derek.Modelo.Form.Updates.CompraUpdate;
import org.derek.Repositorio.Interfaces.ICompraRepo;
import org.derek.Transaccion.IAdministradorSesion;

import java.util.List;
import java.util.Optional;

public class CompraRepoHibernate implements ICompraRepo {

    private final IAdministradorSesion gestorSesion;

    public CompraRepoHibernate(IAdministradorSesion gestorSesion) {
        this.gestorSesion = gestorSesion;
    }

    @Override
    public Optional<EntidadCompra> crear(FormCompra form) {

        var session = gestorSesion.getSesion();

        EntidadCompra compra = new EntidadCompra(
                form.idUsuario(),
                form.idJuego(),
                form.metodoPago(),
                form.precioSinDescuento(),
                form.descuentoAplicado()
        );

        session.persist(compra);
        return Optional.of(compra);
    }

    @Override
    public Optional<EntidadCompra> obtenerPorId(Long id) {
        var session = gestorSesion.getSesion();

        return Optional.ofNullable(session.find(EntidadCompra.class, id));
    }

    @Override
    public List<EntidadCompra> obtenerTodos() {
        var session = gestorSesion.getSesion();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EntidadCompra> cq = cb.createQuery(EntidadCompra.class);
        Root<EntidadCompra> root = cq.from(EntidadCompra.class);

        cq.select(root);

        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<EntidadCompra> update(Long id, CompraUpdate form) {

        var session = gestorSesion.getSesion();

        Optional<EntidadCompra> compraOpt = obtenerPorId(id);

        if (compraOpt.isEmpty()) {
            return Optional.empty();
        }

        EntidadCompra compraActualizada = new EntidadCompra(
                id,
                form.idUsuario(),
                form.idJuego(),
                form.fechaCompra().atStartOfDay(),
                form.metodoPago(),
                form.precioSinDescuento(),
                form.descuentoAplicado(),
                form.estado()
        );

        session.merge(compraActualizada);

        return obtenerPorId(id);
    }

    @Override
    public boolean eliminar(Long id) {

        var session = gestorSesion.getSesion();

        Optional<EntidadCompra> compraOpt = obtenerPorId(id);

        if (compraOpt.isEmpty()) {
            return false;
        }

        session.remove(compraOpt.get());
        return true;
    }
}