package org.derek.Transaccion;

import org.derek.Excepciones.ExcepcionGenerica;


public interface ProveedorExcepciones<T> {

    T get() throws ExcepcionGenerica;
}
