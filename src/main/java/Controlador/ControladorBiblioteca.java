package Controlador;

import Modelo.Entidad.EntidadBiblioteca;
import Modelo.Form.FormBiblioteca;
import Repositorio.EnMemoria.BibliotecaEnMemoriaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControladorBiblioteca {

    private BibliotecaEnMemoriaRepo bibliotecaRepo = new BibliotecaEnMemoriaRepo();
    private List<String> errores = new ArrayList<>();

    /** Valida y añade un juego a la biblioteca de un usuario
     * @param form Formulario con los datos de la biblioteca
     * @return Lista de errores encontrados, si no encuentra ninguno devolvera la lista vacía
     * */
    public List<String> agregarJuego(FormBiblioteca form) {
        errores.clear();

        // Validaciones básicas del formulario
        errores.addAll(form.validate());

        // Validaciones dependientes del repositorio
        for (EntidadBiblioteca b : bibliotecaRepo.obtenerTodos()) {

            if (Objects.equals(b.getIdUsuario(), form.getIdUsuario()) && Objects.equals(b.getIdJuego(), form.getIdJuego())) {
                errores.add("El usuario ya tiene este juego en su biblioteca");
            }
        }

        if (errores.isEmpty()) {
            bibliotecaRepo.crear(form);
        }

        return errores;
    }

    /** Consulta la biblioteca de un usuario
     * @param idUsuario ID del usuario
     * @return Lista de entradas de biblioteca del usuario
     * */
    public List<EntidadBiblioteca> verBibliotecaUsuario(int idUsuario) {
        List<EntidadBiblioteca> resultados = new ArrayList<>();
        for (EntidadBiblioteca b : bibliotecaRepo.obtenerTodos()) {
            if (b.getIdUsuario() == idUsuario) {
                resultados.add(b);
            }
        }
        return resultados;
    }

    /** Elimina un juego de la biblioteca de un usuario
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @return true si se eliminó, false si no se encontró
     * */
    public boolean eliminarJuego(int idUsuario, int idJuego) {
        return bibliotecaRepo.obtenerTodos().removeIf(
                b -> b.getIdUsuario() == idUsuario && b.getIdJuego() == idJuego
        );
    }
}