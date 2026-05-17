package org.derek.Controlador.JuegoControlador;

import org.derek.Controlador.Util;
import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Mapper.Mapper;
import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Juego.ClasificacionEdad;
import org.derek.Modelo.DTO.Juego.EstadoJuego;
import org.derek.Modelo.Entidad.EntidadJuego;
import org.derek.Modelo.Form.FormJuego;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;
import org.derek.Modelo.Form.Updates.JuegoUpdate;
import org.derek.Repositorio.Interfaces.IJuegoRepo;
import org.derek.Transaccion.IAdministradorTransaccion;

import java.util.*;

public class ControladorJuego {

    public static final int MIN_DISCOUNT = 0;
    public static final int MAX_DISCOUNT = 100;

    private final IJuegoRepo juegoRepo;
    private final IAdministradorTransaccion tm;

    public ControladorJuego(IJuegoRepo juegoRepo, IAdministradorTransaccion tm) {
        this.juegoRepo = juegoRepo;
        this.tm = tm;
    }

    /**
     * Registrar nuevo juego
     */
    public JuegoDTO addNewGame(FormJuego gameForm) throws ExcepcionGenerica {

        if (gameForm == null) {
            throw new ExcepcionGenerica(
                    List.of(new ErrorDTO("Form", TipoError.REQUERIDO)).toString()
            );
        }

        var created = tm.enTransaccion(() -> {
            List<ErrorDTO> errors = new ArrayList<>();

            errors.addAll(gameForm.validate());
            errors.addAll(validate(gameForm));

            Util.throwException(errors);

            return juegoRepo.crear(gameForm);
        }).orElse(null);

        return Mapper.mapFrom(created);
    }

    /**
     * Buscar juegos con filtros
     */
    public List<JuegoDTO> findGames(
            Optional<String> texto,
            Optional<String> category,
            Optional<Integer> minPrice,
            Optional<Integer> maxPrice,
            Optional<ClasificacionEdad> ageClasification,
            Optional<EstadoJuego> gameState
    ) throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (texto.isEmpty() && category.isEmpty() && minPrice.isEmpty()
                && maxPrice.isEmpty() && ageClasification.isEmpty() && gameState.isEmpty()) {

            errors.add(new ErrorDTO(
                    "text,category,minPrice,maxPrice,ageClasification,gameState",
                    TipoError.REQUERIDO
            ));
        } else {

            if (minPrice.isPresent() && maxPrice.isPresent()) {
                if (minPrice.get() > maxPrice.get()) {
                    errors.add(new ErrorDTO("MinPrice, MaxPrice", TipoError.FORMATO_INVALIDO));
                }
            }
        }

        Util.throwException(errors);

        return tm.enTransaccion(() ->
                juegoRepo.obtenerTodos().stream()
                        .filter(g -> texto.isEmpty() ||
                                (!texto.get().isBlank() &&
                                        ((g.getTitulo() != null &&
                                                g.getTitulo().toLowerCase().contains(texto.get().toLowerCase()))
                                                ||
                                                (g.getDescripcion() != null &&
                                                        g.getDescripcion().toLowerCase().contains(texto.get().toLowerCase()))
                                        )))
                        .filter(g -> category.isEmpty() ||
                                (!category.get().isBlank() &&
                                        g.getCategoria() != null &&
                                        g.getCategoria().toLowerCase().contains(category.get().toLowerCase())))
                        .filter(g -> minPrice.isEmpty() || g.getPrecioBase() >= minPrice.get())
                        .filter(g -> maxPrice.isEmpty() || g.getPrecioBase() <= maxPrice.get())
                        .filter(g -> ageClasification.isEmpty() ||
                                g.getClasificacionEdad().equals(ageClasification.get()))
                        .filter(g -> gameState.isEmpty() ||
                                g.getEstado().equals(gameState.get()))
                        .map(Mapper::mapFrom)
                        .toList()
        );
    }

    /**
     * Consultar catálogo
     */
    public List<JuegoDTO> consultWholeCatalogue(Optional<OrdenarPorParametros> orderParameter) throws ExcepcionGenerica {

        List<JuegoDTO> games = tm.enTransaccion(() ->
                juegoRepo.obtenerTodos().stream()
                        .filter(g -> g.getEstado() != EstadoJuego.NO_DISPONIBLE)
                        .map(Mapper::mapFrom)
                        .toList()
        );

        if (orderParameter.isPresent()) {
            switch (orderParameter.get()) {
                case ALFABETICO:
                    return games.stream()
                            .sorted(Comparator.comparing(JuegoDTO::titulo, String.CASE_INSENSITIVE_ORDER))
                            .toList();

                case PRECIO:
                    return games.stream()
                            .sorted(Comparator.comparing(JuegoDTO::precioBase))
                            .toList();

                case FECHA:
                    return games.stream()
                            .sorted(Comparator.comparing(JuegoDTO::fechaLanzamiento))
                            .toList();
            }
        }

        return games;
    }

    /**
     * Detalles juego
     */
    public JuegoDTO consultGameDetails(Long id) throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (id == null) {
            errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));
        }

        Util.throwException(errors);

        EntidadJuego game = tm.enTransaccion(() ->
                juegoRepo.obtenerPorId(id).orElse(null)
        );

        if (game == null) {
            errors.add(new ErrorDTO("GameId", TipoError.NO_ENCONTRADO));
        }

        Util.throwException(errors);

        return Mapper.mapFrom(game);
    }

    /**
     * Aplicar descuento
     */
    public JuegoDTO applyDiscount(Long id, Integer percent) throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (id == null) {
            errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));
        }

        if (percent == null) {
            errors.add(new ErrorDTO("Discount", TipoError.REQUERIDO));
        } else {
            if (percent < MIN_DISCOUNT) {
                errors.add(new ErrorDTO("Discount", TipoError.VALOR_DEMASIADO_BAJO));
            }
            if (percent > MAX_DISCOUNT) {
                errors.add(new ErrorDTO("Discount", TipoError.VALOR_DEMASIADO_ALTO));
            }
        }

        Util.throwException(errors);

        EntidadJuego updated = tm.enTransaccion(() -> {

            EntidadJuego entity = juegoRepo.obtenerPorId(id).orElse(null);

            if (entity == null) {
                throw new ExcepcionGenerica(
                        List.of(new ErrorDTO("GameId", TipoError.NO_ENCONTRADO)).toString()
                );
            }

            JuegoUpdate form = new JuegoUpdate(
                    entity.getIdJuego(),
                    entity.getTitulo(),
                    entity.getDescripcion(),
                    entity.getDesarrollador(),
                    entity.getFechaLanzamiento(),
                    entity.getPrecioBase(),
                    percent,
                    entity.getCategoria(),
                    entity.getClasificacionEdad(),
                    entity.getIdiomasDisponibles(),
                    entity.getEstado()
            );

            return juegoRepo.update(id, form).orElse(null);
        });

        return Mapper.mapFrom(updated);
    }

    /**
     * Cambiar estado
     */
    public JuegoDTO changeGameState(Long id, EstadoJuego nuevoEstado) throws ExcepcionGenerica {

        List<ErrorDTO> errors = new ArrayList<>();

        if (id == null) {
            errors.add(new ErrorDTO("GameId", TipoError.REQUERIDO));
        }

        if (nuevoEstado == null) {
            errors.add(new ErrorDTO("GameState", TipoError.REQUERIDO));
        }

        Util.throwException(errors);

        EntidadJuego updated = tm.enTransaccion(() -> {

            EntidadJuego entity = juegoRepo.obtenerPorId(id).orElse(null);

            if (entity == null) {
                throw new ExcepcionGenerica(
                        List.of(new ErrorDTO("GameId", TipoError.NO_ENCONTRADO)).toString()
                );
            }

            JuegoUpdate form = new JuegoUpdate(
                    entity.getIdJuego(),
                    entity.getTitulo(),
                    entity.getDescripcion(),
                    entity.getDesarrollador(),
                    entity.getFechaLanzamiento(),
                    entity.getPrecioBase(),
                    entity.getDescuentoActual(),
                    entity.getCategoria(),
                    entity.getClasificacionEdad(),
                    entity.getIdiomasDisponibles(),
                    nuevoEstado
            );

            return juegoRepo.update(id, form).orElse(null);
        });

        return Mapper.mapFrom(updated);
    }

    /**
     * Validate business rules
     */
    public List<ErrorDTO> validate(FormJuego game) {

        List<ErrorDTO> errors = new ArrayList<>();

        if (game == null) {
            errors.add(new ErrorDTO("GameForm", TipoError.REQUERIDO));
        } else {

            if (juegoRepo.obtenerTodos().stream()
                    .anyMatch(g -> g.getTitulo().equals(game.titulo()))) {
                errors.add(new ErrorDTO("Tittle", TipoError.DUPLICADO));
            }

            if (Arrays.stream(ClasificacionEdad.values())
                    .noneMatch(c -> c.equals(game.ClasificacionEdad()))) {
                errors.add(new ErrorDTO("AgeClasification", TipoError.NO_ENCONTRADO));
            }
        }

        return errors;
    }
}