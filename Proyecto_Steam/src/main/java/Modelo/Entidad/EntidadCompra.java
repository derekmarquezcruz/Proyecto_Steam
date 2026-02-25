package Modelo.Entidad;

import Modelo.DTO.Compra.EstadoCompra;
import Modelo.DTO.Compra.MetodoPago;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EntidadCompra {

    private Long idCompra;
    private Long idUsuario;
    private Long idJuego;
    private LocalDate fechaCompra;
    private MetodoPago metodoPago;
    private double precioSinDescuento;
    private double precioFinal;
    private float descuentoAplicado;
    private EstadoCompra estado;

    // Constructor
    public EntidadCompra(Long idCompra, Long idUsuario, Long idJuego, LocalDateTime fechaCompra, MetodoPago metodoPago, double precioSinDescuento, double precioFinal, float descuentoAplicado, EstadoCompra estado) {
        this.idCompra = idCompra;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaCompra = LocalDate.now();
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.precioFinal = precioFinal;
        this.descuentoAplicado = descuentoAplicado;
        this.estado = estado.PENDIENTE;
    }

    // Getters
    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public Long getIdJuego() {
        return idJuego;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public double getPrecioSinDescuento() {
        return precioSinDescuento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public float getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    //setters
    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdJuego(Long idJuego) {
        this.idJuego = idJuego;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setPrecioSinDescuento(double precioSinDescuento) {
        this.precioSinDescuento = precioSinDescuento;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public void setDescuentoAplicado(float descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }
}