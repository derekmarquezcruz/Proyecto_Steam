package org.derek.Modelo.Form.Updates;

import org.derek.Modelo.DTO.Compra.EstadoCompra;
import org.derek.Modelo.MetodoPago.MetodoPago;

import java.time.LocalDate;

public record CompraUpdate (
   Long idCompra,
   Long idUsuario,
   Long idJuego,
   LocalDate fechaCompra,
   MetodoPago metodoPago,
   float precioSinDescuento,
   float descuentoAplicado,
   EstadoCompra estado)
{

}
