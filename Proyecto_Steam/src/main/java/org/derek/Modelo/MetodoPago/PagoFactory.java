package org.derek.Modelo.MetodoPago;

import org.derek.Repositorio.Interfaces.IUsuarioRepo;

public class PagoFactory {

    public static IMetodoPago getMetodoPago(MetodoPago pm, IUsuarioRepo userRepo) {
        switch (pm) {

            case TARJETA_CREDITO:
                return new TarjetaCredito();

            case PAYPAL:
                return new Paypal();

            case CARTERA_STEAM:
                return new CarteraSteam(userRepo);

            case TRANSFERENCIA:
                return new Transferencia();

            case OTROS:
                return new Otros();

            default:
                throw new IllegalArgumentException(
                        "Método de pago no disponible: " + pm
                );
        }
    }
}
