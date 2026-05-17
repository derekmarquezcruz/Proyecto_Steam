package org.derek.Modelo.Form;

import org.derek.Controlador.Util;
import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.Form.Errores.TipoError;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public record FormUsuario(
        String nombreUsuario,
        String email,
        String contrasena,
        String nombreReal,
        String pais,
        LocalDate fechaNacimiento,
        String avatar
) {

    public static final int LONGITUD_MIN_NOMBRE = 3;
    public static final int LONGITUD_MAX_NOMBRE = 20;

    public static final int LONGITUD_MIN_NOMBRE_REAL = 2;
    public static final int LONGITUD_MAX_NOMBRE_REAL = 50;

    public static final int LONGITUD_MIN_CONTRASEÑA = 8;

    public static final int EDAD_MINIMA = 13;
    public static final int LONGITUD_MAX_AVATAR = 100;

    public List<ErrorDTO> validar() {

        List<ErrorDTO> errores = new ArrayList<>();

        // Usuario
        errores.addAll(validarNombreUsuario());

        // Email
        errores.addAll(validarEmail());

        // Contraseña
        errores.addAll(validarContraseña());

        // Nombre real
        errores.addAll(validarNombreReal());

        // País
        errores.addAll(validarPais());

        // Fecha nacimiento
        errores.addAll(validarFechaNacimiento());

        // Avatar
        errores.addAll(validarAvatar());

        return errores;
    }

    private List<ErrorDTO> validarNombreUsuario() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(nombreUsuario)) {
            errores.add(new ErrorDTO("NombreUsuario", TipoError.REQUERIDO));
        } else {
            if (nombreUsuario.length() < LONGITUD_MIN_NOMBRE) {
                errores.add(new ErrorDTO("NombreUsuario", TipoError.VALOR_DEMASIADO_BAJO));
            }
            if (nombreUsuario.length() > LONGITUD_MAX_NOMBRE) {
                errores.add(new ErrorDTO("NombreUsuario", TipoError.VALOR_DEMASIADO_ALTO));
            }
            if (!nombreUsuario.matches("^[a-zA-Z0-9_-]+$")) {
                errores.add(new ErrorDTO("NombreUsuario", TipoError.FORMATO_INVALIDO));
            }
            if (nombreUsuario.matches("^[0-9].*")) {
                errores.add(new ErrorDTO("NombreUsuario", TipoError.FORMATO_INVALIDO));
            }
        }

        return errores;
    }

    private List<ErrorDTO> validarEmail() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(email)) {
            errores.add(new ErrorDTO("Email", TipoError.REQUERIDO));
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errores.add(new ErrorDTO("Email", TipoError.FORMATO_INVALIDO));
        }

        return errores;
    }

    private List<ErrorDTO> validarContraseña() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(contrasena)) {
            errores.add(new ErrorDTO("Contraseña", TipoError.REQUERIDO));
        } else {
            if (contrasena.length() < LONGITUD_MIN_CONTRASEÑA) {
                errores.add(new ErrorDTO("Contraseña", TipoError.VALOR_DEMASIADO_BAJO));
            }
            if (!contrasena.matches(".*[A-Z].*") ||
                    !contrasena.matches(".*[a-z].*") ||
                    !contrasena.matches(".*[0-9].*")) {
                errores.add(new ErrorDTO("Contraseña", TipoError.FORMATO_INVALIDO));
            }
        }

        return errores;
    }

    private List<ErrorDTO> validarNombreReal() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(nombreReal)) {
            errores.add(new ErrorDTO("NombreReal", TipoError.REQUERIDO));
        } else {
            if (nombreReal.length() < LONGITUD_MIN_NOMBRE_REAL) {
                errores.add(new ErrorDTO("NombreReal", TipoError.VALOR_DEMASIADO_BAJO));
            }
            if (nombreReal.length() > LONGITUD_MAX_NOMBRE_REAL) {
                errores.add(new ErrorDTO("NombreReal", TipoError.VALOR_DEMASIADO_ALTO));
            }
        }

        return errores;
    }

    private List<ErrorDTO> validarPais() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (Util.checkCadenaBlankOrEmpty(pais)) {
            errores.add(new ErrorDTO("Pais", TipoError.REQUERIDO));
        }

        return errores;
    }

    private List<ErrorDTO> validarFechaNacimiento() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (fechaNacimiento == null) {
            errores.add(new ErrorDTO("FechaNacimiento", TipoError.REQUERIDO));
        } else {
            if (fechaNacimiento.isAfter(LocalDate.now())) {
                errores.add(new ErrorDTO("FechaNacimiento", TipoError.VALOR_DEMASIADO_ALTO));
            }
            if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < EDAD_MINIMA) {
                errores.add(new ErrorDTO("FechaNacimiento", TipoError.VALOR_DEMASIADO_BAJO));
            }
        }

        return errores;
    }

    private List<ErrorDTO> validarAvatar() {
        List<ErrorDTO> errores = new ArrayList<>();

        if (avatar != null && avatar.length() > LONGITUD_MAX_AVATAR) {
            errores.add(new ErrorDTO("Avatar", TipoError.VALOR_DEMASIADO_ALTO));
        }

        return errores;
    }


}