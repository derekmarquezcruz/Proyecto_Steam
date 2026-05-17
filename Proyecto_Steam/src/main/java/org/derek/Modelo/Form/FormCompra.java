package org.derek.Modelo.Form;

import org.derek.Modelo.Form.Errores.ErrorDTO;
import org.derek.Modelo.MetodoPago.MetodoPago;

import java.util.Collection;


public record FormCompra(Long idUsuario,
                           Long idJuego,
                           MetodoPago metodoPago,
                           float precioSinDescuento,
                           float descuentoAplicado) {



}
