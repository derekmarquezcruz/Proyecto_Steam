package org.derek.Modelo.Form.Updates;

import org.derek.Modelo.DTO.Usuario.EstadoCuenta;

import java.time.LocalDate;

public record UsuarioUpdate (
        String nombreUsuario,
        String email,
        String contrasena,
        String nombreReal,
        String pais,
        LocalDate fechaNacimiento,
        LocalDate fechaRegistro,
        String avatar,
        float saldoCartera,
        EstadoCuenta estadoCuenta
){
}
