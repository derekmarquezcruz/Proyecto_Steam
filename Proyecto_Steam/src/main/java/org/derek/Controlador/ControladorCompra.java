package org.derek.Controlador;

import org.derek.Controlador.Util;
import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Mapper.Mapper;
import org.derek.Modelo.DTO.Compra.CompraDTO;
import org.derek.Modelo.DTO.Compra.EstadoCompra;
import org.derek.Modelo.DTO.Juego.EstadoJuego;
import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Usuario.EstadoCuenta;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;
import org.derek.Modelo.Entidad.*;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;
import org.derek.Modelo.Form.FormCompra;
import org.derek.Modelo.Form.Updates.CompraUpdate;
import org.derek.Modelo.Form.Updates.UsuarioUpdate;
import org.derek.Modelo.MetodoPago.PagoFactory;
import org.derek.Modelo.MetodoPago.IMetodoPago;
import org.derek.Modelo.MetodoPago.MetodoPago;
import org.derek.Repositorio.Interfaces.IBibliotecaRepo;
import org.derek.Repositorio.Interfaces.ICompraRepo;
import org.derek.Repositorio.Interfaces.IJuegoRepo;
import org.derek.Repositorio.Interfaces.IUsuarioRepo;
import org.derek.Transaccion.IAdministradorTransaccion;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ControladorCompra {

    public static final int LIMITE_DIAS_REEMBOLSO = 14;
    public static final int LIMITE_HORAS_REEMBOLSO = 2;

    public static final int MIN_PRECIO_BASE = 0;
    public static final int MIN_DESCUENTO = 0;
    public static final int MAX_DESCUENTO = 100;

    private final ICompraRepo compraRepo;
    private final IJuegoRepo juegoRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IBibliotecaRepo bibliotecaRepo;
    private final IAdministradorTransaccion tm;

    public ControladorCompra(ICompraRepo compraRepo,
                             IJuegoRepo juegoRepo,
                             IUsuarioRepo usuarioRepo,
                             IBibliotecaRepo bibliotecaRepo,
                             IAdministradorTransaccion tm) {

        this.compraRepo = compraRepo;
        this.juegoRepo = juegoRepo;
        this.usuarioRepo = usuarioRepo;
        this.bibliotecaRepo = bibliotecaRepo;
        this.tm = tm;
    }

    /**
     * Crear una nueva compra (pendiente)
     */
    public CompraDTO realizarCompra(Long idUsuario, Long idJuego, MetodoPago metodoPago) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idUsuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        if (idJuego == null) {
            errores.add(new ErrorDTO("IdJuego", TipoError.REQUERIDO));
        }

        if (metodoPago == null) {
            errores.add(new ErrorDTO("MetodoPago", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            EntidadJuego juego = juegoRepo.obtenerPorId(idJuego).orElse(null);
            EntidadUsuario usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);

            if (usuario == null) {
                erroresTransaccion.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            }

            if (juego == null) {
                erroresTransaccion.add(new ErrorDTO("IdJuego", TipoError.NO_ENCONTRADO));
            }

            if (usuario != null && juego != null) {

                if (juego.getPrecioBase() < MIN_PRECIO_BASE) {
                    erroresTransaccion.add(new ErrorDTO("PrecioBase", TipoError.VALOR_DEMASIADO_BAJO));
                }

                if (juego.getDescuentoActual() < MIN_DESCUENTO) {
                    erroresTransaccion.add(new ErrorDTO("DescuentoActual", TipoError.VALOR_DEMASIADO_BAJO));
                }

                if (juego.getDescuentoActual() > MAX_DESCUENTO) {
                    erroresTransaccion.add(new ErrorDTO("DescuentoActual", TipoError.VALOR_DEMASIADO_ALTO));
                }

                EntidadCompra compraAnterior = compraRepo.obtenerTodos().stream()
                        .filter(c -> Objects.equals(c.getIdUsuario(), usuario.getIdUsuario())
                                && Objects.equals(c.getIdJuego(), juego.getIdJuego()))
                        .findFirst()
                        .orElse(null);

                if (compraAnterior != null && compraAnterior.getEstado() == EstadoCompra.COMPLETADA) {
                    erroresTransaccion.add(new ErrorDTO("EstadoCompra", TipoError.DUPLICADO));
                }

                erroresTransaccion.addAll(validar(usuario, juego));
            }

            Util.throwException(erroresTransaccion);

            float descuento = juego.getPrecioBase() * juego.getDescuentoActual() / 100;
            float precioFinal = juego.getPrecioBase() - descuento;

            FormCompra formCompra = new FormCompra(
                    usuario.getIdUsuario(),
                    juego.getIdJuego(),
                    metodoPago,
                    juego.getPrecioBase(),
                    precioFinal
            );


            Util.throwException(erroresTransaccion);

            EntidadCompra nuevaCompra = compraRepo.crear(formCompra).orElse(null);

            Optional<UsuarioDTO> usuarioDTO = Optional.ofNullable(Mapper.mapFrom(usuario));
            Optional<JuegoDTO> juegoDTO = Optional.ofNullable(Mapper.mapFrom(juego));

            return Mapper.mapFrom(nuevaCompra, usuarioDTO, juegoDTO);
        });
    }

    /**
     * Procesar pago de una compra pendiente
     */
    public CompraDTO procesarPago(Long idCompra) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idCompra == null) {
            errores.add(new ErrorDTO("IdCompra", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            EntidadCompra compra = compraRepo.obtenerPorId(idCompra).orElse(null);

            if (compra == null) {
                throw new ExcepcionGenerica(List.of(new ErrorDTO("IdCompra", TipoError.NO_ENCONTRADO)).toString());
            }

            EntidadJuego juego = juegoRepo.obtenerPorId(compra.getIdJuego()).orElse(null);
            EntidadUsuario usuario = usuarioRepo.obtenerPorId(compra.getIdUsuario()).orElse(null);

            if (usuario == null) {
                erroresTransaccion.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            }

            if (juego == null) {
                erroresTransaccion.add(new ErrorDTO("IdJuego", TipoError.NO_ENCONTRADO));
            }

            if (compra.getEstado() != EstadoCompra.PENDIENTE) {
                erroresTransaccion.add(new ErrorDTO("EstadoCompra", TipoError.ESTADO_INCORRECTO));
            }

            Util.throwException(erroresTransaccion);

            MetodoPago metodoPago = compra.getMetodoPago();

            if (metodoPago == null) {
                throw new ExcepcionGenerica(List.of(new ErrorDTO("MetodoPago", TipoError.NO_ENCONTRADO)).toString());
            }

            IMetodoPago metodo = PagoFactory.getMetodoPago(metodoPago, usuarioRepo);

            try {

                metodo.HacerPago(compra.getDescuentoAplicado(), compra.getIdUsuario());

                CompraUpdate update = new CompraUpdate(
                        compra.getIdCompra(),
                        compra.getIdUsuario(),
                        compra.getIdJuego(),
                        compra.getFechaCompra(),
                        compra.getMetodoPago(),
                        compra.getPrecioSinDescuento(),
                        compra.getDescuentoAplicado(),
                        EstadoCompra.COMPLETADA
                );

                compraRepo.update(idCompra, update)
                        .orElseThrow(() -> new ExcepcionGenerica(List.of(new ErrorDTO("Compra", TipoError.NO_ACTUALIZADO)).toString()));

            } catch (ExcepcionGenerica e) {

                CompraUpdate update = new CompraUpdate(
                        compra.getIdCompra(),
                        compra.getIdUsuario(),
                        compra.getIdJuego(),
                        compra.getFechaCompra(),
                        compra.getMetodoPago(),
                        compra.getPrecioSinDescuento(),
                        compra.getDescuentoAplicado(),
                        EstadoCompra.CANCELADA
                );

                compraRepo.update(idCompra, update)
                        .orElseThrow(() -> new ExcepcionGenerica(List.of(new ErrorDTO("Compra", TipoError.NO_ACTUALIZADO)).toString()));
            }

            EntidadCompra compraActualizada = compraRepo.obtenerPorId(idCompra).orElse(null);

            Optional<UsuarioDTO> usuarioDTO = Optional.ofNullable(Mapper.mapFrom(usuario));
            Optional<JuegoDTO> juegoDTO = Optional.ofNullable(Mapper.mapFrom(juego));

            return Mapper.mapFrom(compraActualizada, usuarioDTO, juegoDTO);
        });
    }

    /**
     * Consultar historial de compras de un usuario
     */
    public List<Object> consultarHistorial(Long idUsuario,
                                           Optional<EstadoCompra> estado,
                                           Optional<LocalDate> fechaMin,
                                           Optional<LocalDate> fechaMax) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idUsuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            EntidadUsuario usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);

            if (usuario == null) {
                erroresTransaccion.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            }

            if (fechaMin.isPresent() && fechaMax.isPresent()) {
                if (fechaMin.get().isAfter(fechaMax.get())) {
                    erroresTransaccion.add(new ErrorDTO("FechaCompra", TipoError.VALOR_DEMASIADO_BAJO));
                }
            }

            Util.throwException(erroresTransaccion);

            List<EntidadCompra> compras = compraRepo.obtenerTodos().stream()
                    .filter(c -> Objects.equals(c.getIdUsuario(), idUsuario))
                    .toList();

            if (estado.isPresent()) {
                compras = compras.stream()
                        .filter(c -> c.getEstado() == estado.get())
                        .toList();
            }

            if (fechaMin.isPresent() && fechaMax.isEmpty()) {
                compras = compras.stream()
                        .filter(c -> !c.getFechaCompra().isBefore(fechaMin.get().atStartOfDay()))
                        .toList();
            }

            if (fechaMax.isPresent() && fechaMin.isEmpty()) {
                compras = compras.stream()
                        .filter(c -> !c.getFechaCompra().isAfter(fechaMax.get().atStartOfDay()))
                        .toList();
            }

            if (fechaMin.isPresent() && fechaMax.isPresent()) {
                compras = compras.stream()
                        .filter(c ->
                                !c.getFechaCompra().isBefore(fechaMin.get().atStartOfDay()) &&
                                        !c.getFechaCompra().isAfter(fechaMax.get().atStartOfDay()))
                        .toList();
            }


            return null;
        });
    }

    /**
     * Solicitar reembolso de una compra
     */
    public void solicitarReembolso(Long idCompra, String motivo) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idCompra == null) {
            errores.add(new ErrorDTO("IdCompra", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            EntidadCompra compra = compraRepo.obtenerPorId(idCompra).orElse(null);

            if (compra == null) {
                erroresTransaccion.add(new ErrorDTO("IdCompra", TipoError.NO_ENCONTRADO));
            } else {

                if (compra.getEstado() != EstadoCompra.COMPLETADA) {
                    throw new ExcepcionGenerica(List.of(new ErrorDTO("EstadoCompra", TipoError.ESTADO_INCORRECTO)).toString());
                }

                EntidadBiblioteca biblioteca = bibliotecaRepo.getPorIdJuegoUsuario(compra.getIdUsuario(), compra.getIdJuego()).orElse(null);

                if (biblioteca == null) {
                    erroresTransaccion.add(new ErrorDTO("BibliotecaUsuarioJuego", TipoError.NO_ENCONTRADO));
                } else {

                    long dias = ChronoUnit.DAYS.between(biblioteca.getFechaAdquisicion(), LocalDate.now());

                    if (dias > LIMITE_DIAS_REEMBOLSO) {
                        erroresTransaccion.add(new ErrorDTO("FechaCompra", TipoError.VALOR_DEMASIADO_ALTO));
                    }

                    if (biblioteca.getTiempoJugado() > LIMITE_HORAS_REEMBOLSO) {
                        erroresTransaccion.add(new ErrorDTO("TiempoJugado", TipoError.VALOR_DEMASIADO_ALTO));
                    }
                }
            }

            EntidadUsuario usuario = null;

            if (compra != null) {
                usuario = usuarioRepo.obtenerPorId(compra.getIdUsuario()).orElse(null);

                if (usuario == null) {
                    erroresTransaccion.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
                }
            }

            Util.throwException(erroresTransaccion);

            float monto = compra.getDescuentoAplicado();

            UsuarioUpdate updateUsuario = new UsuarioUpdate(
                    usuario.getNombreUsuario(),
                    usuario.getEmail(),
                    usuario.getContrasena(),
                    usuario.getNombreReal(),
                    usuario.getPais(),
                    usuario.getFechaNacimiento(),
                    usuario.getFechaRegistro(),
                    usuario.getAvatar(),
                    usuario.getSaldoCartera() + monto,
                    usuario.getEstadoCuenta()
            );

            EntidadUsuario usuarioActualizado = usuarioRepo.update(usuario.getIdUsuario(), updateUsuario).orElse(null);

            CompraUpdate updateCompra = new CompraUpdate(
                    compra.getIdCompra(),
                    compra.getIdUsuario(),
                    compra.getIdJuego(),
                    compra.getFechaCompra(),
                    compra.getMetodoPago(),
                    compra.getPrecioSinDescuento(),
                    compra.getDescuentoAplicado(),
                    EstadoCompra.REEMBOLSADA
            );

            EntidadCompra compraActualizada = compraRepo.update(idCompra, updateCompra).orElse(null);

            boolean bibliotecaEliminada = false;

            EntidadBiblioteca biblioteca = bibliotecaRepo.getPorIdJuegoUsuario(compra.getIdUsuario(), compra.getIdJuego()).orElse(null);
            if (biblioteca != null) {
                bibliotecaEliminada = bibliotecaRepo.eliminar(biblioteca.getIdBiblioteca());
            }

            if (usuarioActualizado == null) {
                erroresTransaccion.add(new ErrorDTO("Usuario", TipoError.NO_ACTUALIZADO));
            }

            if (compraActualizada == null) {
                erroresTransaccion.add(new ErrorDTO("Compra", TipoError.NO_ACTUALIZADO));
            }

            if (!bibliotecaEliminada) {
                erroresTransaccion.add(new ErrorDTO("Biblioteca", TipoError.NO_ELIMINADO));
            }

            Util.throwException(erroresTransaccion);

            return true;
        });
    }

    /**
     * Validaciones que requieren acceso a repositorios
     */
    public List<ErrorDTO> validar(EntidadUsuario usuario, EntidadJuego juego) {

        List<ErrorDTO> errores = new ArrayList<>();

        if (usuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        if (juego == null) {
            errores.add(new ErrorDTO("IdJuego", TipoError.REQUERIDO));
        }

        if (usuario != null && juego != null) {

            if (usuarioRepo.obtenerPorId(usuario.getIdUsuario()).isEmpty()) {
                errores.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            }

            if (usuario.getEstadoCuenta() != EstadoCuenta.ACTIVA) {
                errores.add(new ErrorDTO("EstadoCuenta", TipoError.ESTADO_INCORRECTO));
            }

            if (juegoRepo.obtenerPorId(juego.getIdJuego()).isEmpty()) {
                errores.add(new ErrorDTO("IdJuego", TipoError.NO_ENCONTRADO));
            }

            if (juego.getEstado() == null || juego.getEstado() == EstadoJuego.NO_DISPONIBLE) {
                errores.add(new ErrorDTO("EstadoJuego", TipoError.ESTADO_INCORRECTO));
            }
        }

        return errores;
    }
}