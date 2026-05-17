package org.derek.Modelo.MetodoPago;

import org.derek.Excepciones.ExcepcionGenerica;

public class Otros implements IMetodoPago {

    @Override
    public void HacerPago(float gameCost, Long userId) throws ExcepcionGenerica {

        System.out.println("Procesando pago.....");
    }
}
