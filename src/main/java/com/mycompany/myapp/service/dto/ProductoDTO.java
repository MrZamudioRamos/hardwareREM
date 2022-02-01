package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String numeroSerie;

    @NotNull
    private String marca;

    @NotNull
    private String modelo;

    @NotNull
    private String descripcion;

    @NotNull
    private Double peso;

    private LocalDate fechaVenta;

    @NotNull
    private Double precioCompra;

    @NotNull
    private Double precioBruto;

    @NotNull
    private Double precioIva;

    @NotNull
    private Boolean vendido;

    private IvaDTO iva;

    private EmpresaDTO empresa;

    private PedidoDTO pedido;

    private AlmacenDTO almacen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioBruto() {
        return precioBruto;
    }

    public void setPrecioBruto(Double precioBruto) {
        this.precioBruto = precioBruto;
    }

    public Double getPrecioIva() {
        return precioIva;
    }

    public void setPrecioIva(Double precioIva) {
        this.precioIva = precioIva;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public IvaDTO getIva() {
        return iva;
    }

    public void setIva(IvaDTO iva) {
        this.iva = iva;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
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
        if (!(o instanceof ProductoDTO)) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", peso=" + getPeso() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioCompra=" + getPrecioCompra() +
            ", precioBruto=" + getPrecioBruto() +
            ", precioIva=" + getPrecioIva() +
            ", vendido='" + getVendido() + "'" +
            ", iva=" + getIva() +
            ", empresa=" + getEmpresa() +
            ", pedido=" + getPedido() +
            ", almacen=" + getAlmacen() +
            "}";
    }
}
