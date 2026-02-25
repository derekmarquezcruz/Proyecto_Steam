package Controlador;

import Modelo.DTO.*;

import Modelo.DTO.Juego.CategoriaJuego;
import Modelo.DTO.Juego.ClasificacionEdad;
import Modelo.DTO.Juego.EstadoJuego;
import Modelo.Entidad.EntidadJuego;
import Modelo.Form.FormJuego;
import Repositorio.EnMemoria.JuegoEnMemoriaRepo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorJuego {

    private JuegoEnMemoriaRepo juegoRepo = new JuegoEnMemoriaRepo();

    /**
     * Registra un nuevo juego en el catálogo
     * @param form Formulario con datos del juego
     * @return Lista de errores encontrados, vacía si se registró correctamente
     */
    public List<String> registrarJuego(FormJuego form) {
        List<String> errores = new ArrayList<>();

        // Validaciones del formulario
        errores.addAll(form.validate());


        if (form.getTitulo() != null && juegoRepo.obtenerTodos().stream().anyMatch(j -> j.getTitulo().equalsIgnoreCase(form.getTitulo()))) {
            errores.add("Ya existe");
        }

        if (errores.isEmpty()) {
            juegoRepo.crear(form);
        }

        return errores;
    }

    /**
     * Busca juegos en el catálogo según múltiples criterios opcionales
     * @param texto Texto libre para filtrar por título o descripción
     * @param categoria Categoría del juego a filtrar
     * @param minPrecio Precio mínimo del juego
     * @param maxPrecio Precio máximo del juego
     * @param clasificacion Clasificación por edad
     * @param estado Estado del juego
     * @return Lista de juegos que cumplen todos los criterios proporcionados
     */
    public List<EntidadJuego> buscarJuegos(Optional<String> texto, Optional<String> categoria, Optional<Float> minPrecio, Optional<Float> maxPrecio, Optional<ClasificacionEdad> clasificacion, Optional<EstadoJuego> estado) {

        List<EntidadJuego> resultado = new ArrayList<>();

        for (EntidadJuego juego : juegoRepo.obtenerTodos()) {

            boolean coincide = true;

            // Filtro por texto en título o descripción
            if (texto.isPresent()) {
                String t = texto.get().toLowerCase();
                if (!juego.getTitulo().toLowerCase().contains(t) && !juego.getDescripcion().toLowerCase().contains(t)) {
                    coincide = false;
                }
            }

            CategoriaJuego cat = CategoriaJuego.valueOf(categoria.get().toUpperCase());
            if (juego.getCategoria() != cat) {
                coincide = false;
            }

            //por precio minimo
            if (minPrecio.isPresent() && juego.getPrecioBase() < minPrecio.get()) {
                coincide = false;
            }

            //Por precio Maximo
            if (maxPrecio.isPresent() && juego.getPrecioBase() > maxPrecio.get()) {
                coincide = false;
            }

            //por clasificacion de edad
            if (clasificacion.isPresent() && juego.getClasificacionEdad() != clasificacion.get()) {
                coincide = false;
            }

            //por estado de juego
            if (estado.isPresent() && juego.getEstadoJuego() != estado.get()) {
                coincide = false;
            }

            // Si el juego cumple todos los criterios, se añade a la lista de resultados
            if (coincide) {
                resultado.add(juego);
            }
        }

        return resultado;
    }

    /** Lista todos los juegos disponibles
     * @return Lista con todos los juegos ordenados o por orden alfabetico, precio o fecha.
     * */
    public List<EntidadJuego> consultarCatalogo(Optional<OrdenarPorParametros> orden) {

        List<EntidadJuego> juegos = juegoRepo.obtenerTodos().stream()
                .filter(j -> j.getEstadoJuego() == EstadoJuego.DISPONIBLE)
                .toList();

        if (orden.isPresent()) {
            switch (orden.get()) {
                case ALFABETICO:
                    return juegos.stream()
                            .sorted((a, b) -> a.getTitulo().compareToIgnoreCase(b.getTitulo()))
                            .toList();

                case PRECIO:
                    return juegos.stream()
                            .sorted((a, b) -> Float.compare(a.getPrecioBase(), b.getPrecioBase()))
                            .toList();

                case FECHA:
                    return juegos.stream()
                            .sorted((a, b) -> a.getFechaLanzamiento().compareTo(b.getFechaLanzamiento()))
                            .toList();
            }
        }

        return juegos;
    }

    /**
     * Aplica un descuento a un juego específico
     * @param id del juego al que se quiere aplicar el descuento
     * @param porcentaje de descuento a aplicar
     * @return La entidad del juego con el descuento actualizado
     */
    public EntidadJuego aplicarDescuento(Long id, Optional<Integer> porcentaje) {
        return juegoRepo.actualizar(id, Optional.empty(), porcentaje);
    }

    /**
     * Cambia el estado de disponibilidad de un juego específico
     * @param id del juego cuyo estado se quiere modificar
     * @param nuevoEstado del juego
     * @return La entidad del juego con el estado actualizado
     */
    public EntidadJuego cambiarEstado(Long id, Optional<EstadoJuego> nuevoEstado) {
        return juegoRepo.actualizar(id, nuevoEstado, Optional.empty());
    }

    /**
     * Consulta un juego por ID
     * @param id Identificador del juego
     */
    public EntidadJuego verDetallesJuego(Long id) {
        return juegoRepo.obtenerPorId(id).orElseThrow(() -> new ExcepcionGenerica("No existe el juego por id: " + id));
    }

    /**
     * Elimina un juego del catálogo
     * @param id Identificador del juego a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarJuego(Long id) {
        return juegoRepo.eliminar(id);
    }

    /**
     * Lista todos los juegos del catálogo
     * @return Lista completa de juegos
     */
    public List<EntidadJuego> verTodos() {
        return juegoRepo.obtenerTodos();
    }
}