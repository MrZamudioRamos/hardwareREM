package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Pedido} entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    @NotNull
    private String direccionEntrega;

    @NotNull
    private LocalDate fechaPedido;

    @NotNull
    private LocalDate fechaEntrega;

    private Double descuento;

    @NotNull
    private String tipoPago;

    @NotNull
    private Double precioTotal;

    private String observaciones;

    @NotNull
    private Boolean entregado;

    @NotNull
    private Boolean enviado;

    private FacturaDTO factura;

    private EmpleadoDTO empleado;

    private EmpresaDTO empresa;

    private AlmacenDTO almacen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getEntregado() {
        return entregado;
    }

    public void setEntregado(Boolean entregado) {
        this.entregado = entregado;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public AlmacenDTO getAlmacen() {
        return almacen;
    }

    public void setAlmacen(AlmacenDTO almacen) {
        this.almacen = almacen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoDTO)) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pedidoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", direccionEntrega='" + getDireccionEntrega() + "'" +
            ", fechaPedido='" + getFechaPedido() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", descuento=" + getDescuento() +
            ", tipoPago='" + getTipoPago() + "'" +
            ", precioTotal=" + getPrecioTotal() +
            ", observaciones='" + getObservaciones() + "'" +
            ", entregado='" + getEntregado() + "'" +
            ", enviado='" + getEnviado() + "'" +
            ", factura=" + getFactura() +
            ", empleado=" + getEmpleado() +
            ", empresa=" + getEmpresa() +
            ", almacen=" + getAlmacen() +
            "}";
    }
}
