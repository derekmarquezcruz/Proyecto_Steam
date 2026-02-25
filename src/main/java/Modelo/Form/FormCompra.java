package Modelo.Form;

import Modelo.DTO.Compra.EstadoCompra;
import Modelo.DTO.Compra.MetodoPago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FormCompra {

    private Long idCompra;
    private Long idUsuario;
    private Long idJuego;
    private LocalDateTime fechaCompra;
    private MetodoPago metodoPago;
    private double precioSinDescuento;
    private double precioFinal;
    private float DescuentoAplicado;
    private EstadoCompra estadoCompra;

    // Constructor
    public FormCompra(Long idCompra, Long idUsuario, Long idJuego, LocalDateTime fechaCompra, MetodoPago metodoPago, double precioSinDescuento, double precioFinal, float DescuentoAplicado, EstadoCompra estadoCompra) {
        this.idCompra = idCompra;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.fechaCompra = fechaCompra;
        this.metodoPago = metodoPago;
        this.precioSinDescuento = precioSinDescuento;
        this.precioFinal = precioFinal;
        this.DescuentoAplicado =  DescuentoAplicado;
        this.estadoCompra = estadoCompra;
    }

    // Getters y Setters
    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(Long idJuego) {
        this.idJuego = idJuego;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getPrecioSinDescuento() {
        return precioSinDescuento;
    }

    public void setPrecioSinDescuento(double precioSinDescuento) {
        this.precioSinDescuento = precioSinDescuento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public float getDescuentoAplicado() {
        return DescuentoAplicado;
    }

    public void setDescuentoAplicado(float DescuentoAplicado) {
        this.DescuentoAplicado = DescuentoAplicado;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    /**
     * Valida todos los campos de la compra
     * @return Lista de errores encontrados
     */
    public List<String> validate() {
        List<String> errores = new ArrayList<>();

        // Campos obligatorios
        if (idUsuario == null){
            errores.add("El usuario es obligatorio");
        }

        if (idJuego == null){
            errores.add("El juego es obligatorio");
        }

        if (metodoPago == null){
            errores.add("El método de pago es obligatorio");
        }

        if (precioSinDescuento <= 0){
            errores.add("El precio debe ser positivo");
        }

        // Descuento válido
        if (DescuentoAplicado < 0 || DescuentoAplicado > 100) {
            errores.add("El descuento debe estar entre 0 y 100");
        }

        // Estado válido
        if (estadoCompra == null) {
            errores.add("Estado de compra inválido");
        }

        // Fecha de compra no puede ser futura
        if (fechaCompra != null && fechaCompra.isAfter(LocalDateTime.now())) {
            errores.add("La fecha de compra no puede ser futura");
        }

        return errores;
    }
}