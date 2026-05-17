package org.derek.Repositorio.EnMemoria;

import org.derek.Modelo.Entidad.EntidadCompra;
import org.derek.Modelo.Form.FormCompra;
import org.derek.Modelo.Form.Updates.CompraUpdate;
import org.derek.Repositorio.Interfaces.ICompraRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraEnMemoriaRepo implements ICompraRepo {

    private static final List<EntidadCompra> COMPRAS = new ArrayList<>();
    private static Long ID_ACTUAL = 1L;

    @Override
    public Optional<EntidadCompra> crear(FormCompra form) {

        EntidadCompra compra = new EntidadCompra(
                ID_ACTUAL++,
                form.idUsuario(),
                form.idJuego(),
                form.metodoPago(),
                form.precioSinDescuento(),
                form.descuentoAplicado()
        );

        COMPRAS.add(compra);
        return Optional.of(compra);
    }

    @Override
    public Optional<EntidadCompra> obtenerPorId(Long id) {

        return COMPRAS.stream()
                .filter(c -> c.getIdCompra().equals(id))
                .findFirst();
    }

    @Override
    public List<EntidadCompra> obtenerTodos() {
        return new ArrayList<>(COMPRAS);
    }

    public List<EntidadCompra> obtenerComprasDeUsuario(Long idUsuario) {

        return COMPRAS.stream()
                .filter(c -> c.getIdUsuario().equals(idUsuario))
                .toList();
    }

    @Override
    public Optional<EntidadCompra> update(Long id, CompraUpdate form) {

        Optional<EntidadCompra> compraOpt = obtenerPorId(id);

        if (compraOpt.isEmpty()) {
            throw new IllegalArgumentException("Compra no encontrada");
        }

        EntidadCompra compra = compraOpt.get();

        compra.setIdUsuario(form.idUsuario());
        compra.setIdJuego(form.idJuego());
        compra.setFechaCompra(LocalDateTime.from(form.fechaCompra()));
        compra.setMetodoPago(form.metodoPago());
        compra.setPrecioSinDescuento(form.precioSinDescuento());
        compra.setDescuentoAplicado(form.descuentoAplicado());
        compra.setEstado(form.estado());

        return Optional.of(compra);
    }

    @Override
    public boolean eliminar(Long id) {

        return COMPRAS.removeIf(c -> c.getIdCompra().equals(id));
    }
}