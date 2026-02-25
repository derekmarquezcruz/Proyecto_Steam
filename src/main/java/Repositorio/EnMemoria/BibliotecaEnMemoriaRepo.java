package Repositorio.EnMemoria;

import Modelo.Entidad.EntidadBiblioteca;
import Modelo.Form.FormBiblioteca;
import Repositorio.Interfaces.IBibliotecaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BibliotecaEnMemoriaRepo implements IBibliotecaRepo<EntidadBiblioteca, FormBiblioteca, Long> {

    private final List<EntidadBiblioteca> bibliotecas = new ArrayList<>();
    private static Long idCounter = 1L;

    @Override
    public Optional<EntidadBiblioteca> crear(FormBiblioteca form) {
        var biblioteca = new EntidadBiblioteca(idCounter++, form.getIdUsuario(), form.getIdJuego(), form.getFechaAdquisicion(), form.getHorasTotalesJugadas(), form.getUltimaFechaJuego(), form.getEstadoInstalacion());
        bibliotecas.add(biblioteca);
        return Optional.of(biblioteca);
    }

    @Override
    public Optional<EntidadBiblioteca> obtenerPorId(Long id) {
        return bibliotecas.stream()
                .filter(b -> b.getIdBiblioteca().equals(id))
                .findFirst();
    }

    @Override
    public List<EntidadBiblioteca> obtenerTodos() {
        return new ArrayList<>(bibliotecas);
    }

    @Override
    public boolean eliminar(Long id) {
        return bibliotecas.removeIf(b -> b.getIdBiblioteca().equals(id));
    }
}