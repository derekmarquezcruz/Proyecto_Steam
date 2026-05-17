package org.derek.Controlador.ResenaControlador;

import org.derek.Controlador.Util;
import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Mapper.Mapper;
import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Resena.ResenaDTO;
import org.derek.Modelo.DTO.Resena.EstadoResena;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;
import org.derek.Modelo.Entidad.EntidadJuego;
import org.derek.Modelo.Entidad.EntidadBiblioteca;
import org.derek.Modelo.Entidad.EntidadResena;
import org.derek.Modelo.Entidad.EntidadUsuario;
import org.derek.Modelo.Form.FormResena;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;
import org.derek.Modelo.Form.Updates.ResenaUpdate;
import org.derek.Repositorio.Interfaces.IJuegoRepo;
import org.derek.Repositorio.Interfaces.IBibliotecaRepo;
import org.derek.Repositorio.Interfaces.IResenaRepo;
import org.derek.Repositorio.Interfaces.IUsuarioRepo;
import org.derek.Transaccion.IAdministradorTransaccion;
import org.hibernate.query.Order;

import java.time.LocalDate;
import java.util.*;

import static org.derek.Controlador.ResenaControlador.OrdenarPorParametros.FECHA;
import static org.derek.Controlador.ResenaControlador.OrdenarPorParametros.HORAS;

public class ControladorResena {

    private final IResenaRepo resenaRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IJuegoRepo juegoRepo;
    private final IBibliotecaRepo bibliotecaRepo;
    private final IAdministradorTransaccion tm;

    // Constructor
    public ControladorResena(IResenaRepo resenaRepo,
                             IUsuarioRepo usuarioRepo,
                             IJuegoRepo juegoRepo,
                             IBibliotecaRepo bibliotecaRepo,
                             IAdministradorTransaccion tm) {

        this.resenaRepo = resenaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
        this.bibliotecaRepo = bibliotecaRepo;
        this.tm = tm;
    }

    /**
     * Crear una nueva reseña o actualizarla si ya existe
     *
     * @param idUsuario    id del usuario que hace la reseña
     * @param idJuego      id del juego
     * @param recomendado  si recomienda el juego o no
     * @param textoResena  texto de la reseña
     *
     * @return ReviewDTO
     */
    public ResenaDTO escribirResena(Long idUsuario,
                                    Long idJuego,
                                    boolean recomendado,
                                    String textoResena) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idUsuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        if (idJuego == null) {
            errores.add(new ErrorDTO("IdJuego", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return (ResenaDTO) tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            // Validar usuario
            EntidadUsuario usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);
            if (usuario == null) {
                erroresTransaccion.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            }

            // Validar juego
            EntidadJuego juego = juegoRepo.obtenerPorId(idJuego).orElse(null);
            if (juego == null) {
                erroresTransaccion.add(new ErrorDTO("IdJuego", TipoError.NO_ENCONTRADO));
            }

            // Validar texto reseña
            if (Util.checkCadenaBlankOrEmpty(textoResena)) {
                erroresTransaccion.add(new ErrorDTO("TextoResena", TipoError.REQUERIDO));
            }

            // Validar que exista biblioteca para ese usuario y juego
            EntidadBiblioteca biblioteca = null;
            if (usuario != null && juego != null) {
                biblioteca = bibliotecaRepo.getPorIdJuegoUsuario(idUsuario, idJuego).orElse(null);

                if (biblioteca == null) {
                    erroresTransaccion.add(new ErrorDTO("BibliotecaUsuarioJuego", TipoError.NO_ENCONTRADO));
                }
            }

            Util.throwException(erroresTransaccion);

            EntidadResena nuevaResena = null;

            // Ver si ya existe reseña previa
            EntidadResena resenaExistente = resenaRepo.getByUserGameId(idUsuario, idJuego).orElse(null);

            if (resenaExistente == null) {

                FormResena form = new FormResena(
                        idUsuario,
                        idJuego,
                        recomendado,
                        textoResena,
                        biblioteca.getTiempoJugado()
                );

                erroresTransaccion.addAll(form.validar());
                Util.throwException(erroresTransaccion);

                nuevaResena = resenaRepo.crear(form).orElse(null);

            }

            Optional<UsuarioDTO> usuarioDTO = Optional.ofNullable(Mapper.mapFrom(usuario));
            Optional<JuegoDTO> juegoDTO = Optional.ofNullable(Mapper.mapFrom(juego));

            return Mapper.mapFrom(nuevaResena, usuarioDTO, juegoDTO);
        });
    }

    /**
     * Eliminar una reseña (cambiar estado a ELIMINADA)
     */
    public ResenaDTO eliminar(Long idResena, Long idUsuario) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idUsuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        if (idResena == null) {
            errores.add(new ErrorDTO("IdResena", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return (ResenaDTO) tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            EntidadUsuario usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);
            if (usuario == null) {
                erroresTransaccion.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            }

            EntidadResena resena = resenaRepo.obtenerPorId(idResena).orElse(null);
            if (resena == null) {
                erroresTransaccion.add(new ErrorDTO("IdResena", TipoError.NO_ENCONTRADO));
            }

            Util.throwException(erroresTransaccion);

            if (!Objects.equals(resena.getIdUsuario(), idUsuario)) {
                throw new ExcepcionGenerica(List.of(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO)).toString());
            }

            if (resena.getEstado() == EstadoResena.ELIMINADA) {
                throw new ExcepcionGenerica(List.of(new ErrorDTO("EstadoResena", TipoError.DUPLICADO)).toString());
            }

            ResenaUpdate update = new ResenaUpdate(
                    resena.getIdResena(),
                    resena.getIdUsuario(),
                    resena.getIdJuego(),
                    resena.isRecomendado(),
                    resena.getTextoResena(),
                    resena.getHorasJugadas(),
                    resena.getFechaPublicacion(),
                    LocalDate.now(),
                    EstadoResena.ELIMINADA
            );

            EntidadResena resenaActualizada = resenaRepo.update(idResena, update).orElse(null);

            EntidadJuego juego = juegoRepo.obtenerPorId(resena.getIdJuego()).orElse(null);

            Optional<UsuarioDTO> usuarioDTO = Optional.ofNullable(Mapper.mapFrom(usuario));
            Optional<JuegoDTO> juegoDTO = Optional.ofNullable(Mapper.mapFrom(juego));

            return Mapper.mapFrom(resenaActualizada, usuarioDTO, juegoDTO);
        });
    }

    /**
     * Mostrar reseñas de un juego con filtros opcionales
     */
    public List<Object> mostrarResenas(Long idJuego,
                                       Optional<Boolean> recomendado,
                                       Optional<Order> orden) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idJuego == null) {
            errores.add(new ErrorDTO("IdJuego", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            EntidadJuego juego = juegoRepo.obtenerPorId(idJuego).orElse(null);
            if (juego == null) {
                erroresTransaccion.add(new ErrorDTO("IdJuego", TipoError.NO_ENCONTRADO));
            }

            Util.throwException(erroresTransaccion);

            List<EntidadResena> resenas = resenaRepo.getPoridJuego(idJuego).stream()
                    .filter(r -> r.getEstado() == EstadoResena.PUBLICADA)
                    .toList();

            if (recomendado.isPresent()) {
                resenas = resenas.stream()
                        .filter(r -> r.isRecomendado() == recomendado.get())
                        .toList();
            }

            if (orden.isPresent()) {
                switch (orden.get()) {
                    case FECHA:
                        resenas = resenas.stream()
                                .sorted(Comparator.comparing(EntidadResena::getFechaPublicacion))
                                .toList();
                        break;

                    case HORAS:
                        resenas = resenas.stream()
                                .sorted(Comparator.comparing(EntidadResena::getHorasJugadas))
                                .toList();
                        break;
                    default:

                        throw new IllegalStateException("Unexpected value: " + orden.get());
                }
            }

            Optional<JuegoDTO> juegoDTO = Optional.ofNullable(Mapper.mapFrom(juego));

            return resenas.stream()
                    .map(r -> Mapper.mapFrom(
                            r,
                            Optional.ofNullable(
                                    Mapper.mapFrom(usuarioRepo.obtenerPorId(r.getIdUsuario()).orElse(null))
                            ),
                            juegoDTO
                    ))
                    .toList();
        });
    }

    /**
     * Ocultar una reseña (cambiar estado a OCULTA)
     */
    public ResenaUpdate ocultarResena(Long idResena, Long idUsuario) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idUsuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        if (idResena == null) {
            errores.add(new ErrorDTO("IdResena", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return (ResenaUpdate) tm.enTransaccion(() -> {

            List<ErrorDTO> erroresTransaccion = new ArrayList<>();

            EntidadUsuario usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);
            if (usuario == null) {
                erroresTransaccion.add(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO));
            }

            EntidadResena resena = resenaRepo.obtenerPorId(idResena).orElse(null);
            if (resena == null) {
                erroresTransaccion.add(new ErrorDTO("IdResena", TipoError.NO_ENCONTRADO));
            }

            Util.throwException(erroresTransaccion);

            if (!Objects.equals(resena.getIdUsuario(), idUsuario)) {
                throw new ExcepcionGenerica(List.of(new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO)).toString());
            }

            if (resena.getEstado() != EstadoResena.PUBLICADA) {
                throw new ExcepcionGenerica(List.of(new ErrorDTO("EstadoResena", TipoError.ESTADO_INCORRECTO)).toString());
            }

            ResenaUpdate update = new ResenaUpdate(
                    resena.getIdResena(),
                    resena.getIdUsuario(),
                    resena.getIdJuego(),
                    resena.isRecomendado(),
                    resena.getTextoResena(),
                    resena.getHorasJugadas(),
                    resena.getFechaPublicacion(),
                    LocalDate.now(),
                    EstadoResena.OCULTA
            );

            EntidadResena resenaActualizada = resenaRepo.update(idResena, update).orElse(null);

            EntidadJuego juego = juegoRepo.obtenerPorId(resena.getIdJuego()).orElse(null);

            Optional<UsuarioDTO> usuarioDTO = Optional.ofNullable(Mapper.mapFrom(usuario));
            Optional<JuegoDTO> juegoDTO = Optional.ofNullable(Mapper.mapFrom(juego));

            return Mapper.mapFrom(resenaActualizada, usuarioDTO, juegoDTO);
        });
    }

    /**
     * Mostrar todas las reseñas de un usuario
     */
    public List<Object> mostrarResenasDeUsuario(Long idUsuario, Optional<Order> orden) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (idUsuario == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        Util.throwException(errores);

        return tm.enTransaccion(() -> {

            EntidadUsuario usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);

            if (usuario == null) {
                throw new ExcepcionGenerica(List.of(
                        new ErrorDTO("IdUsuario", TipoError.NO_ENCONTRADO)
                ).toString());
            }

            List<EntidadResena> resenas = resenaRepo.getPoridUsuario(idUsuario);

            if (orden.isPresent()) {
                switch (orden.get()) {
                    case FECHA:
                        resenas = resenas.stream()
                                .sorted(Comparator.comparing(EntidadResena::getFechaPublicacion))
                                .toList();
                        break;

                    case HORAS:
                        resenas = resenas.stream()
                                .sorted(Comparator.comparing(EntidadResena::getHorasJugadas))
                                .toList();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + orden.get());
                }
            }

            Optional<UsuarioDTO> usuarioDTO = Optional.ofNullable(Mapper.mapFrom(usuario));

            return resenas.stream()
                    .map(r -> Mapper.mapFrom(
                            r,
                            usuarioDTO,
                            Optional.ofNullable(
                                    Mapper.mapFrom(juegoRepo.obtenerPorId(r.getIdJuego()).orElse(null))
                            )
                    ))
                    .toList();
        });
    }
}