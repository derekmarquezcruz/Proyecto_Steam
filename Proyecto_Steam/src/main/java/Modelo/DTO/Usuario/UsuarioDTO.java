package Modelo.DTO.Usuario;

import java.time.LocalDate;

public class UsuarioDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String email;
    private String contrasena;
    private String nombreReal;
    private String pais;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private String avatar;
    private float saldoCartera;
    private EstadoCuenta estadoCuenta;

    // Constructor
    public UsuarioDTO(Long idUsuario, String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNacimiento, LocalDate fechaRegistro, String avatar, float saldoCartera, EstadoCuenta estadoCuenta) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.saldoCartera = saldoCartera;
        this.estadoCuenta = estadoCuenta;
    }

    // Getters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public String getPais() {
        return pais;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getAvatar() {
        return avatar;
    }

    public float getSaldoCartera() {
        return saldoCartera;
    }

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    // Setters
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSaldoCartera(float saldoCartera) {
        this.saldoCartera = saldoCartera;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
}