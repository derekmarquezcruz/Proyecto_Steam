package org.derek.Modelo.MetodoPago;

import org.derek.Excepciones.ExcepcionGenerica;

public class Paypal implements IMetodoPago {

    @Override
    public void HacerPago(float CosteJuego, Long IdUsuario) throws ExcepcionGenerica {
        System.out.println("Procesando pago con Paypal.....");
    }
}
