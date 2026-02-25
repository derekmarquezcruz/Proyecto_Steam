package Repositorio.EnMemoria;

import Modelo.Entidad.EntidadResena;
import Modelo.Form.FormResena;
import Repositorio.Interfaces.IResenaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResenaEnMemoriaRepo implements IResenaRepo {

    private final List<EntidadResena> resenas = new ArrayList<>();
    private static Long idCounter = 1L;

    @Override
    public Optional<EntidadResena> crear(FormResena form) {
        var resena = new EntidadResena(idCounter++, form.getIdUsuario(), form.getIdJuego(), form.isRecomendado(), form.getTextoResena(), form.getHorasJugadas(), form.getFechaPublicacion(), form.getFechaUltimaEdicion(), form.getEstadoResena());
        resenas.add(resena);
        return Optional.of(resena);
    }

    @Override
    public Optional<EntidadResena> obtenerPorId(Long id) {
        return resenas.stream()
                .filter(r -> r.getIdResena().equals(id))
                .findFirst();
    }

    @Override
    public List<EntidadResena> obtenerTodos() {
        return new ArrayList<>(resenas);
    }

    @Override
    public boolean eliminar(Long id) {
        return resenas.removeIf(r -> r.getIdResena().equals(id));
    }
}