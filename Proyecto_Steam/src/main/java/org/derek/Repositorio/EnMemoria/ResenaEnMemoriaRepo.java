package org.derek.Repositorio.EnMemoria;

import org.derek.Modelo.Entidad.EntidadResena;
import org.derek.Modelo.Form.FormResena;
import org.derek.Modelo.Form.Updates.ResenaUpdate;
import org.derek.Repositorio.Interfaces.IResenaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ResenaEnMemoriaRepo implements IResenaRepo {

    private static final List<EntidadResena> REVISIONES = new ArrayList<>();
    private static Long SIGUIENTE_ID = 1L;

    @Override
    public Optional<EntidadResena> crear(FormResena form) {
        var review = new EntidadResena(
                SIGUIENTE_ID++,
                form.idUsuario(),
                form.idJuego(),
                form.recomendado(),
                form.textoResena(),
                form.horasJugadas()
        );

        REVISIONES.add(review);
        return Optional.of(review);
    }

    @Override
    public Optional<EntidadResena> getByUserGameId(Long idUsuario, Long idJuego) {
        return obtenerTodos().stream()
                .filter(r -> Objects.equals(r.getIdUsuario(), idUsuario)
                        && Objects.equals(r.getIdJuego(), idJuego))
                .findFirst();
    }



    @Override
    public Optional<EntidadResena> obtenerPorId(Long id) {
        return REVISIONES.stream()
                .filter(r -> r.getIdResena().equals(id))
                .findFirst();
    }

    @Override
    public List<EntidadResena> getPoridJuego(Long idJuego) {
        return REVISIONES.stream()
                .filter(r -> r.getIdJuego().equals(idJuego))
                .toList();
    }

    @Override
    public List<EntidadResena> getPoridUsuario(Long idUsuario) {
        return REVISIONES.stream()
                .filter(r -> r.getIdUsuario().equals(idUsuario))
                .toList();
    }

    @Override
    public List<EntidadResena> obtenerTodos() {
        return new ArrayList<>(REVISIONES);
    }

    @Override
    public Optional<EntidadResena> update(Long id, ResenaUpdate form) {
        obtenerPorId(id).orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada"));

        var resenaActualizada = new EntidadResena(
                form.idResena(),
                form.ididUsuario(),
                form.idJuego(),
                form.recomendado(),
                form.textoResena(),
                form.horasJugadas(),
                form.fechaPublicacion(),
                form.fechaUltimaEdicion(),
                form.estado()
        );

        REVISIONES.removeIf(r -> r.getIdResena().equals(id));
        REVISIONES.add(resenaActualizada);

        return Optional.of(resenaActualizada);
    }

    @Override
    public boolean eliminar(Long id) {
        return REVISIONES.removeIf(r -> r.getIdResena().equals(id));
    }


}