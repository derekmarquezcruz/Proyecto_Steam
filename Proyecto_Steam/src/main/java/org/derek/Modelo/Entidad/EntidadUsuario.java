package org.derek.Modelo.Entidad;

import jakarta.persistence.*;
import org.derek.Modelo.DTO.Usuario.EstadoCuenta;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class EntidadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "email")
    private String email;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "nombre_real")
    private String nombreReal;

    @Column(name = "pais")
    private String pais;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "saldo_cartera")
    private float saldoCartera;

    @Column(name = "estado_cuenta")
    private EstadoCuenta estadoCuenta;

    public EntidadUsuario() {
    }

    // Constructor completo (actualización)
    public EntidadUsuario(Long idUsuario, String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNacimiento, LocalDate fechaRegistro, String avatar, float saldoCartera, EstadoCuenta estadoCuenta) {

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

    // Constructor creación
    public EntidadUsuario(String nombreUsuario,
                          String email,
                          String contrasena,
                          String nombreReal,
                          String pais,
                          LocalDate fechaNacimiento,
                          String avatar,
                          float saldoCartera) {

        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = LocalDate.now();
        this.avatar = avatar;
        this.saldoCartera = saldoCartera;
        this.estadoCuenta = EstadoCuenta.ACTIVA;
    }

    public EntidadUsuario(Object o, String s, String email, String contrasena, String s1, String pais, LocalDate localDate, String avatar, int i) {
    }



    // GETTERS
    public Long getIdUsuario() { return idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getEmail() { return email; }
    public String getContrasena() { return contrasena; }
    public String getNombreReal() { return nombreReal; }
    public String getPais() { return pais; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public String getAvatar() { return avatar; }
    public float getSaldoCartera() { return saldoCartera; }
    public EstadoCuenta getEstadoCuenta() { return estadoCuenta; }

    // SETTERS
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public void setEmail(String email) { this.email = email; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setNombreReal(String nombreReal) { this.nombreReal = nombreReal; }
    public void setPais(String pais) { this.pais = pais; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public void setSaldoCartera(float saldoCartera) { this.saldoCartera = saldoCartera; }
    public void setEstadoCuenta(EstadoCuenta estadoCuenta) { this.estadoCuenta = estadoCuenta; }

    @Override
    public String toString() {
        return "EntidadUsuario{" +
                "idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", nombreReal='" + nombreReal + '\'' +
                ", pais='" + pais + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaRegistro=" + fechaRegistro +
                ", avatar='" + avatar + '\'' +
                ", saldoCartera=" + saldoCartera +
                ", estadoCuenta=" + estadoCuenta +
                '}';
    }
}