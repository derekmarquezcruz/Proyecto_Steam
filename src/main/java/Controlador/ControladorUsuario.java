package Controlador;
import Modelo.Entidad.EntidadUsuario;
import Modelo.Form.FormUsuario;
import Repositorio.EnMemoria.UsuarioEnMemoriaRepo;
import Repositorio.EnMemoria.CiudadEnMemoriaRepo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorUsuario {

    private UsuarioEnMemoriaRepo usuarioRepo = new UsuarioEnMemoriaRepo();
    private CiudadEnMemoriaRepo ciudadRepo = new CiudadEnMemoriaRepo();

    /**
     * Registra un nuevo usuario
     * @param form Formulario con los datos introducidos por el usuario
     * @return Lista de errores encontrados, vacía si no hay errores
     */
    public List<String> registrarUsuario(FormUsuario form) {
        List<String> errores = new ArrayList<>();

        // Validaciones básicas del formulario
        errores.addAll(form.validate());

        // Validaciones que dependen de datos externos
        errores.addAll(validarFormDependencias(form));

        // Si no hay errores, se crea el usuario
        if (errores.isEmpty()) {
            usuarioRepo.crear(form);
        }

        return errores;
    }

    /**
     * Valida datos que dependen de otros registros del sistema
     * @param form Formulario del usuario
     * @return Lista de errores encontrados
     */
    private List<String> validarFormDependencias(FormUsuario form) {
        List<String> errores = new ArrayList<>();

        // Nombre de usuario único
        if (usuarioRepo.obtenerTodos().stream().anyMatch(u -> u.getNombreUsuario().equals(form.getNombreUsuario()))) {
            errores.add("El nombre de usuario ya existe");
        }

        // Email único
        if (usuarioRepo.obtenerTodos().stream().anyMatch(u -> u.getEmail().equals(form.getEmail()))) {
            errores.add("El email ya existe");
        }

        // País válido
        if (!ciudadRepo.getCiudades().contains(form.getPais())) {
            errores.add("País no válido");
        }

        // Edad mínima 13 años
        if (form.getFechaNacimiento() != null && Period.between(form.getFechaNacimiento(), LocalDate.now()).getYears() < 13) {
            errores.add("El usuario debe tener al menos 13 años");
        }

        return errores;
    }

    /**
     * Consulta un usuario por su ID
     * @param id Identificador del usuario
     * @return Lista de datos del usuario: nombre, avatar, país, fecha de registro
     */
    public List<String> verUsuarioPorId(Long id) {
        EntidadUsuario usuario = usuarioRepo.obtenerPorId(id)
                .orElseThrow(() -> new ExcepcionGenerica("Usuario no encontrado"));

        return List.of(usuario.getNombreUsuario(), usuario.getAvatar(), usuario.getPais(), usuario.getFechaRegistro().toString()
        );
    }

    /**
     * Consulta un usuario por nombre de usuario
     * @param nombreUsuario Nombre del usuario
     * @return Lista de datos del usuario: nombre, avatar, país, fecha de registro
     */
    public List<String> verUsuarioPorNombre(String nombreUsuario) {
        EntidadUsuario usuario = usuarioRepo.obtenerTodos().stream()
                .filter(u -> u.getNombreUsuario().equals(nombreUsuario))
                .findFirst()
                .orElseThrow(() -> new ExcepcionGenerica("Usuario no encontrado"));

        return List.of(usuario.getNombreUsuario(), usuario.getAvatar(), usuario.getPais(), usuario.getFechaRegistro().toString()
        );
    }

    /**
     * Recarga saldo en la cartera del usuario
     * @param id Identificador del usuario
     * @param cantidad Cantidad a añadir
     * @return Nuevo saldo
     */
    public float recargarCartera(Long id, Optional<Float> cantidad) {
        EntidadUsuario usuario = usuarioRepo.obtenerPorId(id)
                .orElseThrow(() -> new ExcepcionGenerica("Usuario no encontrado"));

        if (cantidad.isEmpty()) {
            throw new ExcepcionGenerica("No se proporcionó cantidad a recargar");
        }

        float monto = cantidad.get();

        if (monto < 5 || monto > 500) {
            throw new ExcepcionGenerica("El monto debe estar entre 5 y 500");
        }


        return usuario.getSaldoCartera();
    }

    /**
     * Consulta el saldo actual de la cartera del usuario
     * @param id Identificador del usuario
     * @return Saldo actual
     */
    public float consultarCartera(Long id) {
        EntidadUsuario usuario = usuarioRepo.obtenerPorId(id).orElseThrow(() -> new ExcepcionGenerica("Usuario no encontrado"));

        return usuario.getSaldoCartera();
    }

    /**
     * Elimina un usuario del sistema
     * @param id Identificador del usuario
     * @return true si se eliminó correctamente
     */
    public boolean eliminarUsuario(Long id) {
        return usuarioRepo.eliminar(id);
    }
}