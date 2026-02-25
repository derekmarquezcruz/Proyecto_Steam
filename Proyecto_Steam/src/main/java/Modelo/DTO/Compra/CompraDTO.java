package Modelo.DTO.Compra;

import java.time.LocalDateTime;

public class CompraDTO {

    private Long idCompra;
    private Long idUsuario;
    private Long idJuego;
    private LocalDateTime fechaCompra;
    private MetodoPago metodoPago;
    private double precioSinDescuento;
    private float descuentoAplicado;
    private EstadoCompra estadoCompra;

    // Constructor
    public CompraDTO(Long idCompra, Long idUsuario, Long idJuego, LocalDateTime fechaCompra, MetodoPago metodoPago, double precioSinDescuento, float descuentoAplicado, EstadoCompra estadoCompra) {
        this.idCompra = idCompra;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaCompra = fechaCompra;
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.descuentoAplicado = descuentoAplicado;
        this.estadoCompra = estadoCompra;
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

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public double getPrecioSinDescuento() {
        return precioSinDescuento;
    }

    public float getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    // Setter
    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }
}