package Controlador;

import Modelo.DTO.Compra.EstadoCompra;
import Modelo.DTO.Compra.MetodoPago;
import Modelo.DTO.Juego.EstadoJuego;
import Modelo.Entidad.EntidadCompra;
import Modelo.Entidad.EntidadUsuario;
import Modelo.Entidad.EntidadJuego;
import Modelo.Form.FormCompra;
import Repositorio.EnMemoria.CompraEnMemoriaRepo;
import Repositorio.EnMemoria.UsuarioEnMemoriaRepo;
import Repositorio.EnMemoria.JuegoEnMemoriaRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorCompra {

    private CompraEnMemoriaRepo compraRepo = new CompraEnMemoriaRepo();

    private UsuarioEnMemoriaRepo usuarioRepo = new UsuarioEnMemoriaRepo();

    private JuegoEnMemoriaRepo juegoRepo = new JuegoEnMemoriaRepo();

    /** Realiza una compra de un juego por un usuario
     * @param form Formulario con los datos de la compra
     * @return Lista de errores encontrados, vacía si la compra se realiza correctamente
     */
    public List<String> realizarCompra(FormCompra form) {
        List<String> errores = new ArrayList<>();

        // Validación del formulario
        errores.addAll(form.validate());

        // Usuario existe y está activo
        Optional<EntidadUsuario> usuarioOpt = usuarioRepo.obtenerPorId(form.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            errores.add("El usuario no existe");
        }

        // Juego existe y está en estado DISPONIBLE, PREVENTA o ACCESO_ANTICIPADO
        Optional<EntidadJuego> juegoOpt = juegoRepo.obtenerPorId(form.getIdJuego());
        if (juegoOpt.isEmpty()) {
            errores.add("El juego no existe");
        }
        else {
            EstadoJuego estadoJuego = juegoOpt.get().getEstadoJuego();
            if (!(estadoJuego == EstadoJuego.DISPONIBLE || estadoJuego == EstadoJuego.PREVENTA || estadoJuego == EstadoJuego.ACCESO_ANTICIPADO)) {
                errores.add("El juego no puede comprarse en este estado");
            }
        }

        // Verifica saldo si usa cartera
        if (form.getMetodoPago() == MetodoPago.CARTERA_STEAM && usuarioOpt.isPresent()) {
            float saldo = usuarioOpt.get().getSaldoCartera();
            if (saldo < form.getPrecioSinDescuento()) {
                errores.add("Saldo insuficiente en la cartera Steam");
            }
        }

        // Fecha de compra no puede modificarse
        form.setFechaCompra(LocalDateTime.now());

        if (errores.isEmpty()) {
            compraRepo.crear(form);
        }

        return errores;
    }

    /** Consulta todas las compras de un usuario
     * @param idUsuario
     * @return Lista de compras del usuario
     */
    public List<EntidadCompra> verComprasPorUsuario(Long idUsuario) {
        return compraRepo.obtenerTodos().stream()
                .filter(c -> c.getIdUsuario().equals(idUsuario))
                .toList();
    }

    /** Elimina una compra (solo si está en estado CANCELADA o PENDIENTE)
     * @param idCompra
     * @return true si se eliminó
     */
    public boolean eliminarCompra(Long idCompra) {
        Optional<EntidadCompra> compraOpt = compraRepo.obtenerPorId(idCompra);
        if (compraOpt.isPresent()) {
            EstadoCompra estado = compraOpt.get().getEstado();
        if (estado == EstadoCompra.PENDIENTE || estado == EstadoCompra.CANCELADA) {
                return compraRepo.eliminar(idCompra);
            }
        }
        return false;
    }
}