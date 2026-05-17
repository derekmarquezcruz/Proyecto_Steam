package org.derek.Repositorio.EnMemoria;

import org.derek.Modelo.Entidad.EntidadBiblioteca;
import org.derek.Modelo.Form.FormBiblioteca;
import org.derek.Modelo.Form.Updates.BibiliotecaUpdate;
import org.derek.Repositorio.Interfaces.IBibliotecaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BibliotecaEnMemoriaRepo implements IBibliotecaRepo {

    private static final List<EntidadBiblioteca> BIBLIOTECAS = new ArrayList<>();
    private static Long ID_ACTUAL = 1L;

    @Override
    public Optional<EntidadBiblioteca> crear(FormBiblioteca form) {

        EntidadBiblioteca biblioteca = new EntidadBiblioteca(
                ID_ACTUAL++,
                form.idUsuario(),
                form.idJuego(),
                form.fechaAdquisicion()
        );

        BIBLIOTECAS.add(biblioteca);
        return Optional.of(biblioteca);
    }


    @Override
    public Optional<EntidadBiblioteca> obtenerPorId(Long id) {

        return BIBLIOTECAS.stream()
                .filter(b -> b.getIdBiblioteca().equals(id))
                .findFirst();
    }

    @Override
    public Optional<EntidadBiblioteca> getPorIdJuegoUsuario(Long idUsuario, Long idJuego) {

        return BIBLIOTECAS.stream()
                .filter(b -> Objects.equals(b.getIdUsuario(), idUsuario)
                        && Objects.equals(b.getIdJuego(), idJuego))
                .findFirst();
    }

    @Override
    public List<EntidadBiblioteca> obtenerTodos() {

        return new ArrayList<>(BIBLIOTECAS);
    }

    @Override
    public Optional<EntidadBiblioteca> update(Long id, BibiliotecaUpdate form) {

        Optional<EntidadBiblioteca> bibliotecaOpt = obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            return Optional.empty();
        }

        EntidadBiblioteca biblioteca = bibliotecaOpt.get();

        biblioteca.setIdUsuario(form.idUsuario());
        biblioteca.setIdJuego(form.idJuego());
        biblioteca.setFechaAdquisicion(form.fechaAdquisicion());
        biblioteca.setTiempoJugado(form.tiempoJugado());
        biblioteca.setUltimaSesion(form.ultimaSesion());
        biblioteca.setEstadoInstalacion(form.estadoInstalacion());

        return Optional.of(biblioteca);
    }

    @Override
    public boolean eliminar(Long id) {

        return BIBLIOTECAS.removeIf(b -> b.getIdBiblioteca().equals(id));
    }
}