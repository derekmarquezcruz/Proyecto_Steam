package org.derek.Modelo.DTO.Compra;

import org.derek.Modelo.DTO.Juego.JuegoDTO;
import org.derek.Modelo.DTO.Usuario.UsuarioDTO;
import org.derek.Modelo.MetodoPago.MetodoPago;

import java.time.LocalDateTime;

public record CompraDTO(

     Long idCompra,
     Long idUsuario,
     Long idJuego,
     UsuarioDTO usuario,
     JuegoDTO juego,
     LocalDateTime fechaCompra,
     MetodoPago metodoPago,
     float precioSinDescuento,
     float descuentoAplicado,
     EstadoCompra estadoCompra){

}