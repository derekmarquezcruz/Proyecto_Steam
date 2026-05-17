package org.derek.Mapper;

import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Biblioteca.BibliotecaDTO;
import org.derek.Modelo.DTO.Compra.CompraDTO;
import org.derek.Modelo.DTO.Resena.ResenaDTO;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;

import org.derek.Modelo.Entidad.EntidadJuego;
import org.derek.Modelo.Entidad.EntidadBiblioteca;
import org.derek.Modelo.Entidad.EntidadCompra;
import org.derek.Modelo.Entidad.EntidadResena;
import org.derek.Modelo.Entidad.EntidadUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Mapper {

    // =========================
    // USUARIO
    // =========================
    public static UsuarioDTO mapFrom(EntidadUsuario entity) {
        if (entity == null) return null;

        return new UsuarioDTO(
                entity.getIdUsuario(),
                entity.getNombreUsuario(),
                entity.getEmail(),
                entity.getNombreReal(),
                entity.getPais(),
                entity.getFechaNacimiento(),
                entity.getFechaRegistro(),
                entity.getAvatar(),
                entity.getSaldoCartera(),
                entity.getEstadoCuenta()
        );
    }

    // =========================
    // JUEGO
    // =========================
    public static JuegoDTO mapFrom(EntidadJuego entity) {
        if (entity == null) return null;

        List<String> languages = entity.getIdiomasDisponibles();
        if (languages == null) {
            languages = new ArrayList<>();
        }

        return new JuegoDTO(
                entity.getIdJuego(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getDesarrollador(),
                entity.getFechaLanzamiento(),
                entity.getPrecioBase(),
                entity.getDescuentoActual(),
                entity.getCategoria(),
                entity.getClasificacionEdad(),
                languages,
                entity.getEstado()
        );
    }

    // =========================
    // COMPRA
    // =========================
    public static CompraDTO mapFrom(
            EntidadCompra entity,
            UsuarioDTO usuarioDTO,
            JuegoDTO juegoDTO
    ) {
        if (entity == null) return null;

        return new CompraDTO(
                entity.getIdCompra(),
                entity.getIdUsuario(),
                usuarioDTO,
                entity.getIdJuego(),
                juegoDTO,
                entity.getFechaCompra(),
                entity.getMetodoPago(),
                entity.getPrecioSinDescuento(),
                entity.getDescuentoAplicado(),
                entity.getEstado()
        );
    }

    // =========================
    // BIBLIOTECA
    // =========================
    public static BibliotecaDTO mapFrom(
            EntidadBiblioteca entidad,
            UsuarioDTO usuarioDTO,
            JuegoDTO juegoDTO
    ) {
        if (entidad == null) return null;

        return new BibliotecaDTO(
                entidad.getIdBiblioteca(),
                entidad.getIdUsuario(),
                usuarioDTO,
                entidad.getIdJuego(),
                juegoDTO,
                entidad.getFechaAdquisicion(),
                entidad.getTiempoJugado(),
                entidad.getUltimaSesion(),
                entidad.getEstadoInstalacion()
        );
    }

    // =========================
    // RESEÑA
    // =========================
    public static ResenaDTO mapFrom(
            EntidadResena entity,
            UsuarioDTO usuarioDTO,
            JuegoDTO juegoDTO
    ) {
        if (entity == null) return null;

        return new ResenaDTO(
                entity.getIdResena(),
                entity.getIdUsuario(),
                usuarioDTO,
                entity.getIdJuego(),
                juegoDTO,
                entity.isRecomendado(),
                entity.getTextoResena(),
                entity.getHorasJugadas(),
                entity.getFechaPublicacion(),
                entity.getFechaUltimaEdicion(),
                entity.getEstado()
        );
    }

    public static <T> Object mapFrom(EntidadResena r, Optional<UsuarioDTO> usuarioDTO, Optional<T> t) {
        return null;
    }



}
