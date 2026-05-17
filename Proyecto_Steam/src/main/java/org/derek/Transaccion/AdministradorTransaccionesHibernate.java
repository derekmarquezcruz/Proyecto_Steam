package org.derek.Transaccion;

import HibernateU.HibernateUtil;
import org.derek.Transaccion.IAdministradorSesion;
import org.derek.Transaccion.IAdministradorTransaccion;
import org.derek.Excepciones.ExcepcionGenerica;
import org.derek.Transaccion.ProveedorExcepciones;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

/**
 * Implementación Hibernate de {@link IAdministradorTransaccion}.
 * Gestiona el ciclo de vida de la sesión y la transacción.
 * Expone {@link #getSesion()} para que los repositorios puedan
 * acceder a la sesión activa durante el bloque de trabajo.
 */
public class AdministradorTransaccionesHibernate implements IAdministradorTransaccion, IAdministradorSesion {

    private Session sesion;

    @Override
    public <T> T enTransaccion(ProveedorExcepciones<T> trabajo) throws ExcepcionGenerica {
        Transaction transaccion = null;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            this.sesion = s;

            try {
                transaccion = s.beginTransaction();

                T resultado = trabajo.get();

                transaccion.commit();

                return resultado;

            } catch (Exception e) {

                if (transaccion != null) {
                    transaccion.rollback();
                }

                throw e;
            }

        } finally {
            this.sesion = null;
        }
    }

    /**
     * Devuelve la sesión activa dentro de un bloque {@link #enTransaccion}.
     */
    public Session getSesion() {
        return sesion;
    }
}