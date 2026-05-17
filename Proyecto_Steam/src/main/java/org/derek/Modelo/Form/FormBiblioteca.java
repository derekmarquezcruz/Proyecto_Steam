package org.derek.Modelo.Form;


import java.time.LocalDate;


    public record FormBiblioteca(Long idUsuario,
                              Long idJuego,
                              LocalDate fechaAdquisicion) {

    }
