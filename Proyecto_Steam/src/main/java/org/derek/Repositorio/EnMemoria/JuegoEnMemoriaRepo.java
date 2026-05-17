package org.derek.Repositorio.EnMemoria;


import org.derek.Modelo.Entidad.EntidadJuego;
import org.derek.Modelo.Form.FormJuego;
import org.derek.Modelo.Form.Updates.JuegoUpdate;
import org.derek.Repositorio.Interfaces.IJuegoRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuegoEnMemoriaRepo implements IJuegoRepo {

    private static final List<EntidadJuego> JUEGOS = new ArrayList<>();
    private static Long ID_ACTUAL = 1L;

    @Override
    public Optional<EntidadJuego> crear(FormJuego form) {

        EntidadJuego juego = new EntidadJuego(
                ID_ACTUAL++,
                form.titulo(),
                form.descripcion(),
                form.desarrollador(),
                form.fechaLanzamiento(),
                form.precioBase(),
                form.categoria(),
                form.ClasificacionEdad(),
                form.idiomasDisponibles()
        );

        JUEGOS.add(juego);
        return Optional.of(juego);
    }

    @Override
    public Optional<EntidadJuego> obtenerPorId(Long id) {

        return JUEGOS.stream()
                .filter(j -> j.getIdJuego().equals(id))
                .findFirst();
    }

    @Override
    public List<EntidadJuego> obtenerTodos() {

        return new ArrayList<>(JUEGOS);
    }

    @Override
    public Optional<EntidadJuego> update(Long id, JuegoUpdate form) {

        Optional<EntidadJuego> juegoOpt = obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            return Optional.empty();
        }

        EntidadJuego juego = juegoOpt.get();

        juego.setTitulo(form.titulo());
        juego.setDescripcion(form.descripcion());
        juego.setDesarrollador(form.desarrollador());
        juego.setFechaLanzamiento(form.fechaLanzamiento());
        juego.setPrecioBase(form.precioBase());
        juego.setDescuentoActual(form.descuentoActual());
        juego.setCategoria(form.categoria());
        juego.setClasificacionEdad(form.clasificacionEdad());
        juego.setIdiomasDisponibles(form.idiomasDisponibles());
        juego.setEstado(form.estado());

        return Optional.of(juego);
    }

    @Override
    public boolean eliminar(Long id) {

        return JUEGOS.removeIf(j -> j.getIdJuego().equals(id));
    }


    @Override
    public Optional<EntidadJuego> encontrarPorNombre(String name) {
        return JUEGOS.stream().filter(g -> g.getTitulo().equals(name)).findFirst();
    }



    @Override
    public Optional<EntidadJuego> buscarPorNombre(String nombre) {

        return JUEGOS.stream()
                .filter(j -> j.getTitulo().equals(nombre))
                .findFirst();
    }


}