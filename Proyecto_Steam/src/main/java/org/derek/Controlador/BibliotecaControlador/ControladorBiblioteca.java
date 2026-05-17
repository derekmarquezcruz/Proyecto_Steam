package org.derek.Controlador.BibliotecaControlador;

import org.derek.Controlador.Util;
import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Mapper.Mapper;
import org.derek.Modelo.DTO.Biblioteca.BibliotecaDTO;
import org.derek.Modelo.DTO.Biblioteca.EstadoInstalacion;
import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;
import org.derek.Modelo.Entidad.EntidadBiblioteca;
import org.derek.Modelo.Entidad.EntidadJuego;
import org.derek.Modelo.Entidad.EntidadUsuario;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;
import org.derek.Modelo.Form.FormBiblioteca;
import org.derek.Modelo.Form.Updates.BibiliotecaUpdate;
import org.derek.Repositorio.Interfaces.IBibliotecaRepo;
import org.derek.Repositorio.Interfaces.IJuegoRepo;
import org.derek.Repositorio.Interfaces.IUsuarioRepo;
import org.derek.Transaccion.IAdministradorTransaccion;

import java.time.LocalDate;
import java.util.*;

public class ControladorBiblioteca {

    private final IBibliotecaRepo bibliotecaRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IJuegoRepo juegoRepo;
    private final IAdministradorTransaccion tm;

    public ControladorBiblioteca(IBibliotecaRepo bibliotecaRepo,
                                 IUsuarioRepo usuarioRepo,
                                 IJuegoRepo juegoRepo,
                                 IAdministradorTransaccion tm) {

        this.bibliotecaRepo = bibliotecaRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
        this.tm = tm;
    }

    /**
     * Mostrar biblioteca personal
     */
    public List<BibliotecaDTO> showPersonalLibrary(Long userId,
                                                   Optional<OrdenarPorParametros> order)
            throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) {
            errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        }

        Util.throwException(errors);

        List<BibliotecaDTO> libraries = showLibraryStats(userId);

        if (order.isPresent()) {
            switch (order.get()) {

                case ALFABETICO:
                    return libraries.stream()
                            .sorted((a, b) -> a.juego().titulo()
                                    .compareToIgnoreCase(b.juego().titulo()))
                            .toList();

                case TIPO_JUEGO:
                    return libraries.stream()
                            .sorted((a, b) -> a.juego().categoria()
                                    .compareToIgnoreCase(b.juego().categoria()))
                            .toList();

                case ULTIMA_SESION:
                    return libraries.stream()
                            .sorted((a, b) -> a.tiempoJugado()
                                    .compareTo(b.tiempoJugado()))
                            .toList();

                case FECHA_ADQUISICION:
                    return libraries.stream()
                            .sorted((a, b) -> a.fechaAdquisicion()
                                    .compareTo(b.fechaAdquisicion()))
                            .toList();
            }
        }

        return libraries;
    }

    /**
     * Añadir juego a biblioteca
     */
    public BibliotecaDTO addGameToLibrary(Long gameId, Long userId)
            throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) {
            errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        }
        if (gameId == null) {
            errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));
        }

        Util.throwException(errors);

        return tm.enTransaccion(() -> {

            List<ErrorDTO> txErrors = validate(gameId, userId);
            Util.throwException(txErrors);

            FormBiblioteca form = new FormBiblioteca(userId, gameId, LocalDate.now());

            EntidadBiblioteca created = bibliotecaRepo.crear(form).orElse(null);

            Optional<UsuarioDTO> userDTO =
                    Optional.ofNullable(Mapper.mapFrom(
                            usuarioRepo.obtenerPorId(userId).orElse(null)));

            Optional<JuegoDTO> gameDTO =
                    Optional.ofNullable(Mapper.mapFrom(
                            juegoRepo.obtenerPorId(gameId).orElse(null)));

            return Mapper.mapFrom(created, userDTO, gameDTO);
        });
    }

    /**
     * Eliminar juego de biblioteca
     */
    public void deleteLibrary(Long userId, Long gameId)
            throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) {
            errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        }
        if (gameId == null) {
            errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));
        }

        Util.throwException(errors);

        tm.enTransaccion(() -> {

            EntidadBiblioteca library =
                    bibliotecaRepo.getPorIdJuegoUsuario(userId, gameId)
                            .orElse(null);

            if (library == null) {
                throw new ExcepcionGenerica(List.of(
                        new ErrorDTO("Library", TipoError.NO_ENCONTRADO)
                ).toString());
            }

            boolean deleted = bibliotecaRepo.eliminar(library.getIdBiblioteca());

            if (!deleted) {
                throw new ExcepcionGenerica(List.of(
                        new ErrorDTO("Library", TipoError.NO_ELIMINADO)
                ).toString());
            }

            return true;
        });
    }

    /**
     * Actualizar tiempo jugado
     */
    public BibliotecaDTO updateGameTime(Long userId, Long gameId, Long time)
            throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        if (gameId == null) errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));

        if (time == null) {
            errors.add(new ErrorDTO("TiempoJugado", TipoError.REQUERIDO));
        } else if (time <= 0) {
            errors.add(new ErrorDTO("TiempoJugado", TipoError.VALOR_DEMASIADO_BAJO));
        }

        Util.throwException(errors);

        return tm.enTransaccion(() -> {

            EntidadBiblioteca library =
                    bibliotecaRepo.getPorIdJuegoUsuario(userId, gameId)
                            .orElse(null);

            if (library == null) {
                throw new ExcepcionGenerica(List.of(
                        new ErrorDTO("Library", TipoError.NO_ENCONTRADO)
                ).toString());
            }

            long updatedTime = library.getTiempoJugado() + time;

            BibiliotecaUpdate update = new BibiliotecaUpdate(
                    library.getIdBiblioteca(),
                    library.getIdUsuario(),
                    library.getIdJuego(),
                    library.getFechaAdquisicion(),
                    updatedTime,
                    library.getUltimaSesion(),
                    library.getEstadoInstalacion()
            );

            EntidadBiblioteca updated =
                    bibliotecaRepo.update(library.getIdBiblioteca(), update)
                            .orElse(null);

            Optional<UsuarioDTO> userDTO =
                    Optional.ofNullable(Mapper.mapFrom(
                            usuarioRepo.obtenerPorId(userId).orElse(null)));

            Optional<JuegoDTO> gameDTO =
                    Optional.ofNullable(Mapper.mapFrom(
                            juegoRepo.obtenerPorId(gameId).orElse(null)));

            return Mapper.mapFrom(updated, userDTO, gameDTO);
        });
    }

    /**
     * Consultar última sesión
     */
    public BibliotecaDTO consultLastSession(Long userId, Long gameId)
            throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        if (gameId == null) errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));

        Util.throwException(errors);

        return tm.enTransaccion(() -> {

            EntidadBiblioteca lib =
                    bibliotecaRepo.getPorIdJuegoUsuario(userId, gameId)
                            .orElse(null);

            Optional<UsuarioDTO> userDTO =
                    Optional.ofNullable(Mapper.mapFrom(
                            usuarioRepo.obtenerPorId(userId).orElse(null)));

            Optional<JuegoDTO> gameDTO =
                    Optional.ofNullable(Mapper.mapFrom(
                            juegoRepo.obtenerPorId(gameId).orElse(null)));

            return Mapper.mapFrom(lib, userDTO, gameDTO);
        });
    }

    /**
     * Filtrar biblioteca
     */
    public List<BibliotecaDTO> filterLibrary(Long userId,
                                             Optional<String> text,
                                             Optional<EstadoInstalacion> state)
            throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) {
            errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        }

        Util.throwException(errors);

        return tm.enTransaccion(() -> {

            EntidadUsuario user =
                    usuarioRepo.obtenerPorId(userId).orElse(null);

            if (user == null) {
                throw new ExcepcionGenerica(List.of(
                        new ErrorDTO("UserId", TipoError.NO_ENCONTRADO)
                ).toString());
            }

            List<BibliotecaDTO> libraries =
                    bibliotecaRepo.obtenerTodos().stream()
                            .filter(l -> Objects.equals(l.getIdUsuario(), userId))
                            .map(l -> Mapper.mapFrom(
                                    l,
                                    Optional.ofNullable(Mapper.mapFrom(user)),
                                    Optional.ofNullable(
                                            Mapper.mapFrom(
                                                    juegoRepo.obtenerPorId(l.getIdJuego()).orElse(null)
                                            )
                                    )
                            ))
                            .toList();

            if (text.isPresent()) {
                String t = text.get().toLowerCase();
                libraries = libraries.stream()
                        .filter(l -> l.juego().titulo() != null &&
                                l.juego().titulo().toLowerCase().contains(t))
                        .toList();
            }

            if (state.isPresent()) {
                libraries = libraries.stream()
                        .filter(l -> l.estadoInstalacion() == state.get())
                        .toList();
            }

            return libraries;
        });
    }

    /**
     * Stats biblioteca
     */
    public List<BibliotecaDTO> showLibraryStats(Long userId)
            throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) {
            errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        }

        Util.throwException(errors);

        return tm.enTransaccion(() -> {

            EntidadUsuario user =
                    usuarioRepo.obtenerPorId(userId).orElse(null);

            if (user == null) {
                throw new ExcepcionGenerica(List.of(
                        new ErrorDTO("UserId", TipoError.NO_ENCONTRADO)
                ).toString());
            }

            Optional<UsuarioDTO> userDTO =
                    Optional.ofNullable(Mapper.mapFrom(user));

            return bibliotecaRepo.obtenerTodos().stream()
                    .filter(l -> Objects.equals(l.getIdUsuario(), userId))
                    .map(l -> Mapper.mapFrom(
                            l,
                            userDTO,
                            Optional.ofNullable(
                                    Mapper.mapFrom(
                                            juegoRepo.obtenerPorId(l.getIdJuego()).orElse(null)
                                    )
                            )
                    ))
                    .toList();
        });
    }

    /**
     * Validación business
     */
    public List<ErrorDTO> validate(Long gameId, Long userId) {

        List<ErrorDTO> errors = new ArrayList<>();

        if (userId == null) errors.add(new ErrorDTO("UserId", TipoError.REQUERIDO));
        if (gameId == null) errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));

        if (userId != null && gameId != null) {

            if (usuarioRepo.obtenerPorId(userId).isEmpty()) {
                errors.add(new ErrorDTO("UserId", TipoError.NO_ENCONTRADO));
            }

            if (juegoRepo.obtenerPorId(gameId).isEmpty()) {
                errors.add(new ErrorDTO("GameId", TipoError.NO_ENCONTRADO));
            }

            if (bibliotecaRepo.obtenerTodos().stream()
                    .anyMatch(l -> Objects.equals(l.getIdUsuario(), userId)
                            && Objects.equals(l.getIdJuego(), gameId))) {

                errors.add(new ErrorDTO("UserGame", TipoError.DUPLICADO));
            }
        }

        return errors;
    }
}