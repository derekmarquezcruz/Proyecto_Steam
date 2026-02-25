package Repositorio.EnMemoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria de ciudades (para validar países/ciudades de los usuarios).
 */
public class CiudadEnMemoriaRepo {

    private final List<String> ciudades;

    public CiudadEnMemoriaRepo() {
        ciudades = new ArrayList<>();
        // Lista de ciudades provisionales
        ciudades.add("Madrid");
        ciudades.add("Barcelona");
        ciudades.add("Buenos Aires");
    }

    /**
     * Devuelve la lista completa de ciudades.
     * @return Lista de nombres de ciudades
     */
    public List<String> getCiudades() {
        return new ArrayList<>(ciudades);
    }

    /**
     * Comprueba si una ciudad existe en la lista.
     * @param ciudad Nombre de la ciudad
     * @return true si existe, false si no
     */
    public boolean existe(String ciudad) {
        return ciudades.contains(ciudad);
    }
}