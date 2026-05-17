package org.derek.Transaccion;

import java.util.Optional;

import org.derek.Excepciones.ExcepcionGenerica;

public class NoOpAdministradorTransaccion implements IAdministradorTransaccion {

    @Override
    public <T> T enTransaccion(ProveedorExcepciones<T> trabajo) throws ExcepcionGenerica {
        try {
            return trabajo.get();
        } catch (ExcepcionGenerica e) {
            throw e;
        } catch (Exception e) {
            try {
                return (T) Optional.empty();
            } catch (ClassCastException ex) {
                return null;
            }
        }
    }
}