package org.derek.Repositorio.EnMemoria;

import java.util.ArrayList;
import java.util.List;


import org.derek.Modelo.Entidad.Pais;
import org.derek.Repositorio.Interfaces.IPaisRepo;

import java.util.Objects;
import java.util.Optional;

public class PaisEnMemoriaRepo implements IPaisRepo {

    private static final List<Pais> PAISES = new ArrayList<>();
    private static Long idActual = 1L;

    @Override
    public Optional<Pais> crear(String nombre) {

        Pais pais = new Pais(idActual++, nombre);
        PAISES.add(pais);

        return Optional.of(pais);
    }

    @Override
    public Optional<Pais> obtenerPorId(Long id) {

        return PAISES.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst();
    }

    @Override
    public List<Pais> obtenerTodos() {

        return new ArrayList<>(PAISES);
    }

    @Override
    public Optional<Pais> update(Long id, Pais form) {

        Optional<Pais> paisOpt = obtenerPorId(id);

        if (paisOpt.isEmpty()) {
            return Optional.empty();
        }

        Pais pais = paisOpt.get();
        pais.setNombre(form.getNombre());

        return Optional.of(pais);
    }

    @Override
    public boolean eliminar(Long id) {

        return PAISES.removeIf(p -> p.getId().equals(id));
    }
}