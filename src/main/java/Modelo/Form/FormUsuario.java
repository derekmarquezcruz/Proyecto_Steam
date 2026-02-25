package Modelo.Form;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class FormUsuario {

    private Long idUsuario;
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombreReal;
    private String pais;
    private LocalDate fechaNacimiento;
    private String avatar;

    // Constructor
    public FormUsuario(Long idUsuario, String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNacimiento, String avatar) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
        this.avatar = avatar;
    }

    // Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    // Validaciónes
    public List<String> validate() {

        List<String> errores = new ArrayList<>();

        errores.addAll(validateNombreUsuario());

        errores.addAll(validateEmail());

        errores.addAll(validateContrasena());

        errores.addAll(validateNombreReal());

        errores.addAll(validatePais());

        errores.addAll(validateFechaNacimiento());

        errores.addAll(validateAvatar());

        return errores;
    }

    private List<String> validateNombreUsuario() {
        List<String> errores = new ArrayList<>();
        if(nombreUsuario == null || nombreUsuario.isBlank()) {
            errores.add("El nombre de usuario es obligatorio");
        }

        else if(nombreUsuario.length() < 3){
            errores.add("El nombre de usuario es demasiado corto");
        }

        else if(nombreUsuario.length() > 20){
            errores.add("El nombre de usuario es demasiado largo");
        }

        else if(!nombreUsuario.matches("^[a-zA-Z0-9_-]+$")) {
            errores.add("El formato del nombre de usuario es inválido");
        }

        else if(nombreUsuario.matches("^[0-9].*")){
            errores.add("El nombre de usuario no puede empezar con número");
        }

        return errores;
    }

    private List<String> validateEmail() {
        List<String> errores = new ArrayList<>();
        if(email == null || email.isBlank()) {
            errores.add("El email es obligatorio");
        }
        else if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errores.add("Formato de email inválido");
        }

        return errores;
    }

    private List<String> validateContrasena() {
        List<String> errores = new ArrayList<>();
        if(contrasena == null || contrasena.isBlank()){
            errores.add("La contraseña es obligatoria");
        }

        else if(contrasena.length() < 8) {
            errores.add("La contraseña es demasiado corta");
        }

        else if(!contrasena.matches(".*[A-Z].*") || !contrasena.matches(".*[a-z].*") || !contrasena.matches(".*[0-9].*")){
            errores.add("La contraseña debe tener mayúsculas, minúsculas y números");
        }

        return errores;
    }

    private List<String> validateNombreReal() {
        List<String> errores = new ArrayList<>();
        if(nombreReal == null || nombreReal.isBlank()) {
            errores.add("El nombre real es obligatorio");
        }
        else if(nombreReal.length() < 2) {
            errores.add("El nombre real es demasiado corto");
        }

        else if(nombreReal.length() > 50) {
            errores.add("El nombre real es demasiado largo");
        }
        return errores;
    }

    private List<String> validatePais() {
        List<String> errores = new ArrayList<>();
        if(pais == null || pais.isBlank()) {
            errores.add("El país es obligatorio");
        }
        return errores;
    }

    private List<String> validateFechaNacimiento() {
        List<String> errores = new ArrayList<>();

        if(fechaNacimiento == null)  {
            errores.add("La fecha de nacimiento es obligatoria");
        }

        else if(fechaNacimiento.isAfter(LocalDate.now())) {
            errores.add("La fecha de nacimiento no puede estar en el futuro");
        }

        else if(Period.between(fechaNacimiento, LocalDate.now()).getYears() < 13) {
            errores.add("El usuario debe ser mayor de 13 años");
        }

        return errores;
    }

    private List<String> validateAvatar() {
        List<String> errores = new ArrayList<>();

        if(avatar != null && avatar.length() > 100) {
            errores.add("El avatar es demasiado largo");
        }

        return errores;
    }
}