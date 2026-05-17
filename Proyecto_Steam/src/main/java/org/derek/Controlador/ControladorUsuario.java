package org.derek.Controlador;

import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Mapper.Mapper;
import org.derek.Modelo.DTO.Usuario.EstadoCuenta;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;
import org.derek.Modelo.Entidad.EntidadUsuario;
import org.derek.Modelo.Form.FormUsuario;
import org.derek.Modelo.Form.Updates.UsuarioUpdate;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;
import org.derek.Repositorio.Interfaces.IPaisRepo;
import org.derek.Repositorio.Interfaces.IUsuarioRepo;
import org.derek.Transaccion.IAdministradorTransaccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorUsuario {

    public static final float MIN_VALUE = 5;
    public static final float MAX_VALUE = 500;

    private final IUsuarioRepo usuarioRepo;
    private final IPaisRepo countryRepo;
    private final IAdministradorTransaccion tm;

    public ControladorUsuario(IUsuarioRepo usuarioRepo,
                              IPaisRepo countryRepo,
                              IAdministradorTransaccion tm) {
        this.usuarioRepo = usuarioRepo;
        this.countryRepo = countryRepo;
        this.tm = tm;
    }

    // =========================
    // REGISTRAR USUARIO
    // =========================
    public UsuarioDTO registrarUsuario(FormUsuario form) throws ExcepcionGenerica {

        if (form == null) {
            throw new ExcepcionGenerica(
                    List.of(new ErrorDTO("UsuarioForm", TipoError.REQUERIDO)).toString()
            );
        }

        return tm.enTransaccion(() -> {

            List<ErrorDTO> errores = new ArrayList<>();

            errores.addAll(form.validar());
            errores.addAll(validar(form));

            Util.throwException(errores);

            EntidadUsuario usuario = usuarioRepo.crear(form)
                    .orElseThrow(() -> new ExcepcionGenerica(
                            List.of(new ErrorDTO("Usuario", TipoError.NO_ENCONTRADO)).toString()
                    ));

            return Mapper.mapFrom(usuario);
        });
    }

    // =========================
    // PERFIL USUARIO
    // =========================
    public UsuarioDTO mostrarPerfil(Optional<Long> id,
                                    Optional<String> nombre) throws ExcepcionGenerica {

        if (id.isEmpty() && nombre.isEmpty()) {
            throw new ExcepcionGenerica(
                    List.of(new ErrorDTO("IdUsuario, Nombre", TipoError.REQUERIDO)).toString()
            );
        }

        EntidadUsuario usuario = tm.enTransaccion(() -> {

            if (id.isPresent()) {
                return usuarioRepo.obtenerPorId(id.get()).orElse(null);
            }

            return usuarioRepo.obtenerTodos().stream()
                    .filter(u -> u.getNombreUsuario().trim()
                            .equalsIgnoreCase(nombre.get().trim()))
                    .findFirst()
                    .orElse(null);
        });

        if (usuario == null) {
            throw new ExcepcionGenerica(
                    List.of(new ErrorDTO("Usuario", TipoError.NO_ENCONTRADO)).toString()
            );
        }

        return Mapper.mapFrom(usuario);
    }

    // =========================
    // AÑADIR SALDO
    // =========================
    public UsuarioDTO añadirSaldo(Long id, Float dinero) throws ExcepcionGenerica {

        List<ErrorDTO> errores = new ArrayList<>();

        if (id == null) {
            errores.add(new ErrorDTO("IdUsuario", TipoError.REQUERIDO));
        }

        if (dinero == null) {
            errores.add(new ErrorDTO("Dinero", TipoError.REQUERIDO));
        } else {
            if (dinero < MIN_VALUE) {
                errores.add(new ErrorDTO("Dinero", TipoError.VALOR_DEMASIADO_BAJO));
            }
            if (dinero > MAX_VALUE) {
                errores.add(new ErrorDTO("Dinero", TipoError.VALOR_DEMASIADO_ALTO));
            }
        }

        Util.throwException(errores);

        EntidadUsuario usuario = tm.enTransaccion(() -> {

            EntidadUsuario u = usuarioRepo.obtenerPorId(id).orElse(null);

            if (u == null) {
                throw new ExcepcionGenerica(
                        List.of(new ErrorDTO("Usuario", TipoError.NO_ENCONTRADO)).toString()
                );
            }

            if (u.getEstadoCuenta() != EstadoCuenta.ACTIVA) {
                throw new ExcepcionGenerica(
                        List.of(new ErrorDTO("EstadoUsuario", TipoError.ESTADO_INCORRECTO)).toString()
                );
            }

            float nuevoSaldo = u.getSaldoCartera() + dinero;

            UsuarioUpdate update = new UsuarioUpdate(
                    u.getNombreUsuario(),
                    u.getEmail(),
                    u.getContrasena(),
                    u.getNombreReal(),
                    u.getPais(),
                    u.getFechaNacimiento(),
                    u.getFechaRegistro(),
                    u.getAvatar(),
                    nuevoSaldo,
                    u.getEstadoCuenta()
            );

            return usuarioRepo.update(id, update).orElse(null);
        });

        return Mapper.mapFrom(usuario);
    }

    // =========================
    // VER SALDO
    // =========================
    public UsuarioDTO verSaldo(Long id) throws ExcepcionGenerica {

        if (id == null) {
            throw new ExcepcionGenerica(
                    List.of(new ErrorDTO("IdUsuario", TipoError.REQUERIDO)).toString()
            );
        }

        EntidadUsuario usuario = tm.enTransaccion(
                () -> usuarioRepo.obtenerPorId(id).orElse(null)
        );

        if (usuario == null) {
            throw new ExcepcionGenerica(
                    List.of(new ErrorDTO("Usuario", TipoError.NO_ENCONTRADO)).toString()
            );
        }

        return Mapper.mapFrom(usuario);
    }

    // =========================
    // VALIDACIÓN CON DATOS
    // =========================
    public List<ErrorDTO> validar(FormUsuario form) {

        List<ErrorDTO> errores = new ArrayList<>();

        if (form == null) {
            errores.add(new ErrorDTO("UsuarioForm", TipoError.REQUERIDO));
            return errores;
        }

        List<EntidadUsuario> usuarios = usuarioRepo.obtenerTodos();

        if (usuarios.stream().anyMatch(u -> u.getNombreUsuario().equals(form.nombreUsuario()))) {
            errores.add(new ErrorDTO("UserName", TipoError.DUPLICADO));
        }

        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(form.email()))) {
            errores.add(new ErrorDTO("Email", TipoError.DUPLICADO));
        }

        if (countryRepo.obtenerTodos().stream()
                .noneMatch(c -> c.getNombre().equals(form.pais()))) {

            errores.add(new ErrorDTO("Country", TipoError.NO_ENCONTRADO));
        }

        return errores;
    }
}