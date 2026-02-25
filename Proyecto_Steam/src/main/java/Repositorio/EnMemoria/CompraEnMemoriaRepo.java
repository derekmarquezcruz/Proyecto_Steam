package Repositorio.EnMemoria;

import Modelo.Entidad.EntidadCompra;
import Modelo.Form.FormCompra;
import Repositorio.Interfaces.ICompraRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraEnMemoriaRepo implements ICompraRepo {

    private final List<EntidadCompra> compras = new ArrayList<>();
    private static Long idCounter = 1L;

    @Override
    public Optional<EntidadCompra> crear(FormCompra form) {
        var compra = new EntidadCompra(idCounter++, form.getIdUsuario(), form.getIdJuego(), form.getFechaCompra(), form.getMetodoPago(), form.getPrecioSinDescuento(), form.getPrecioFinal(), form.getDescuentoAplicado(), form.getEstadoCompra());
        compras.add(compra);
        return Optional.of(compra);
    }

    @Override
    public Optional<EntidadCompra> obtenerPorId(Long id) {
        return compras.stream()
                .filter(c -> c.getIdCompra().equals(id))
                .findFirst();
    }

    @Override
    public List<EntidadCompra> obtenerTodos() {
        return new ArrayList<>(compras);
    }

    @Override
    public boolean eliminar(Long id) {
        return compras.removeIf(c -> c.getIdCompra().equals(id));
    }
}