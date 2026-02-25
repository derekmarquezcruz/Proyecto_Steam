package Repositorio.Interfaces;

import Modelo.Entidad.EntidadBiblioteca;
import Modelo.Form.FormBiblioteca;

public interface IBibliotecaRepo <P, P1, L extends Number> extends ICrud<EntidadBiblioteca, FormBiblioteca, Long> {
}