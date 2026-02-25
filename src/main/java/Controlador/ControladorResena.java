package Controlador;

import Modelo.Entidad.EntidadResena;
import Modelo.Form.FormResena;
import Repositorio.EnMemoria.ResenaEnMemoriaRepo;

import java.util.ArrayList;
import java.util.List;

public class ControladorResena {

    private ResenaEnMemoriaRepo resenaRepo = new ResenaEnMemoriaRepo();

    private List<String> errores = new ArrayList<>();

    /** Valida y registra una nueva reseña
     * @param form Formulario con los datos de la reseña
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacía
     * */
    public List<String> agregarResena(FormResena form) {
        errores.clear();

        errores.addAll(form.validate());

        for (EntidadResena r : resenaRepo.obtenerTodos()) {

            if (r.getIdUsuario() == form.getIdUsuario() && r.getIdJuego() == form.getIdJuego()) {
                errores.add("El usuario ya tiene una reseña para este juego");
            }
        }

        if (errores.isEmpty()) {
            resenaRepo.crear(form);
        }

        return errores;
    }

    /** Consulta todas las reseñas de un juego
     * @param idJuego del juego
     * @return Lista de reseñas del juego
     * */
    public List<EntidadResena> verResenasJuego(int idJuego) {
        List<EntidadResena> resultados = new ArrayList<>();

        for (EntidadResena r : resenaRepo.obtenerTodos()) {
            if (r.getIdJuego() == idJuego) {
                resultados.add(r);
            }
        }
        return resultados;
    }

    /** Elimina la reseña de un usuario
     * @param idUsuario del usuario
     * @param idJuego del juego
     * @return true si se eliminó, false si no se encontró
     * */
    public boolean eliminarResena(int idUsuario, int idJuego) {
        return resenaRepo.obtenerTodos().removeIf(r -> r.getIdUsuario() == idUsuario && r.getIdJuego() == idJuego
        );
    }
}