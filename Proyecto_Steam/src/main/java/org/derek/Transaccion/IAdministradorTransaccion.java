package org.derek.Transaccion;

import org.derek.Excepciones.ExcepcionGenerica;

import java.util.Optional;

public interface IAdministradorTransaccion {

    <T> T enTransaccion(ProveedorExcepciones<T> trabajo) throws ExcepcionGenerica;

}
