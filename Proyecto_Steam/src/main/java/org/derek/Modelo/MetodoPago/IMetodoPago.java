package org.derek.Modelo.MetodoPago;

import org.derek.Excepciones.ExcepcionGenerica;

public interface IMetodoPago{

    void HacerPago(float CosteJuego, Long IdUsuario) throws ExcepcionGenerica;
}
