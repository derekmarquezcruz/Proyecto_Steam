package org.derek.Modelo.DTO.Usuario;

import java.time.LocalDate;

public record UsuarioDTO(

    Long idUsuario,
    String nombreUsuario,
    String email,
    String nombreReal,
    String pais,
    LocalDate fechaNacimiento,
    LocalDate fechaRegistro,
    String avatar,
    float saldoCartera,
    EstadoCuenta estadoCuenta){
    }

