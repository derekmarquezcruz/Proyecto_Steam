package Repositorio.EnMemoria;

import Modelo.DTO.Juego.EstadoJuego;
import Modelo.Entidad.EntidadJuego;
import Modelo.Form.FormJuego;
import Repositorio.Interfaces.IJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuegoEnMemoriaRepo implements IJuegoRepo {

    private final List<EntidadJuego> juegos = new ArrayList<>();
    private static Long idCounter = 1L;

    @Override
    public Optional<EntidadJuego> crear(FormJuego form) {
        var juego = new EntidadJuego(idCounter++, form.getTitulo(), form.getDescripcion(), form.getDesarrollador(), form.getFechaLanzamiento(), form.getPrecioBase(), form.getDescuentoActual(), form.getCategoria(), form.getClasificacionEdad(), form.getIdiomasDisponibles(), form.getEstadoJuego());
        juegos.add(juego);
        return Optional.of(juego);
    }

    @Override
    public Optional<EntidadJuego> obtenerPorId(Long id) {
        return juegos.stream()
                .filter(j -> j.getIdJuego().equals(id))
                .findFirst();
    }


    @Override
    public EntidadJuego actualizar(Long id, Optional<EstadoJuego> estado, Optional<Integer> descuento) {

        Optional<EntidadJuego> juegoOpt = obtenerPorId(id);

        // Si no existe el juego
        if (juegoOpt.isEmpty()) {
            return null;
        }

        EntidadJuego juego = juegoOpt.get();

        // Cambiar estado si viene informado
        if (estado.isPresent()) {
            juego.setEstadoJuego(estado.get());
        }

        // Cambiar descuento si viene informado
        if (descuento.isPresent()) {
            int valor = descuento.get();

            // Validación del descuento
            if (valor >= 0 && valor <= 90) {
                juego.setDescuentoActual(valor);
            }
        }

        // Reemplazo el juego en la lista
        juegos.removeIf(j -> j.getIdJuego().equals(id));
        juegos.add(juego);

        return juego;
    }


    @Override
    public List<EntidadJuego> obtenerTodos() {
        return new ArrayList<>(juegos);
    }

    @Override
    public boolean eliminar(Long id) {
        return juegos.removeIf(j -> j.getIdJuego().equals(id));
    }
}