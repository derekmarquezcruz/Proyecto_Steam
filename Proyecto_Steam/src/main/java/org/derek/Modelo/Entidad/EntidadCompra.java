package org.derek.Modelo.Entidad;

import jakarta.persistence.*;
import org.derek.Modelo.DTO.Compra.EstadoCompra;
import org.derek.Modelo.MetodoPago.MetodoPago;

import java.time.LocalDateTime;

@Entity
@Table(name = "compra")
public class EntidadCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_juego")
    private Long idJuego;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "precio_sin_descuento")
    private float precioSinDescuento;

    @Column(name = "descuento_aplicado")
    private float descuentoAplicado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCompra estado;


    public EntidadCompra() {
    }

    // Constructor de creación
    public EntidadCompra(Long idUsuario,
                         Long idJuego,
                         MetodoPago metodoPago,
                         float precioSinDescuento,
                         float descuentoAplicado) {

        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaCompra = LocalDateTime.now();
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
        this.estado = EstadoCompra.PENDIENTE;
    }

    // Constructor
    public EntidadCompra(Long idCompra,
                         Long idUsuario,
                         Long idJuego,
                         LocalDateTime fechaCompra,
                         MetodoPago metodoPago,
                         float precioSinDescuento,
                         float descuentoAplicado,
                         EstadoCompra estado) {

        this.idCompra = idCompra;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaCompra = fechaCompra;
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
        this.estado = estado;
    }

    public EntidadCompra(Long aLong, Long aLong1, Long aLong2, MetodoPago metodoPago, float v, float v1) {
    }

    // GETTERS

    public Long getIdCompra() { return idCompra; }
    public Long getIdUsuario() { return idUsuario; }
    public Long getIdJuego() { return idJuego; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public float getPrecioSinDescuento() { return precioSinDescuento; }
    public float getDescuentoAplicado() { return descuentoAplicado; }
    public EstadoCompra getEstado() { return estado; }

    // SETTERS
    public void setIdCompra(Long idCompra) { this.idCompra = idCompra; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public void setIdJuego(Long idJuego) { this.idJuego = idJuego; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public void setPrecioSinDescuento(float precioSinDescuento) { this.precioSinDescuento = precioSinDescuento; }
    public void setDescuentoAplicado(float descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }
    public void setEstado(EstadoCompra estado) { this.estado = estado; }

    // TO STRING
    @Override
    public String toString() {
        return "EntidadCompra{" +
                "idCompra=" + idCompra +
                ", idUsuario=" + idUsuario +
                ", idJuego=" + idJuego +
                ", fechaCompra=" + fechaCompra +
                ", metodoPago=" + metodoPago +
                ", precioSinDescuento=" + precioSinDescuento +
                ", descuentoAplicado=" + descuentoAplicado +
                ", estado=" + estado +
                '}';
    }
}