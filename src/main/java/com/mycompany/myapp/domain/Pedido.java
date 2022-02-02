package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 300, min = 1)
    @Column(name = "direccion_entrega", nullable = false)
    private String direccionEntrega;

    @NotNull
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fechaPedido;

    @NotNull
    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "descuento")
    private Double descuento;

    @NotNull
    @Column(name = "tipo_pago", nullable = false)
    private String tipoPago;

    @NotNull
    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    @Size(max = 300)
    @Column(name = "observaciones")
    private String observaciones;

    @NotNull
    @Column(name = "entregado", nullable = false)
    private Boolean entregado;

    @NotNull
    @Column(name = "enviado", nullable = false)
    private Boolean enviado;

    @JsonIgnoreProperties(value = { "cliente", "empresa" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Factura factura;

    @OneToMany(mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "iva", "componentes", "empresa", "pedido", "almacen" }, allowSetters = true)
    private Set<Producto> productos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "pedidos", "empresa" }, allowSetters = true)
    private Empleado empleado;

    @ManyToOne
    @JsonIgnoreProperties(value = { "facturas", "clientes", "productos", "pedidos", "almacens", "empleados" }, allowSetters = true)
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pedidos", "productos", "empresa" }, allowSetters = true)
    private Almacen almacen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pedido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return this.direccionEntrega;
    }

    public Pedido direccionEntrega(String direccionEntrega) {
        this.setDireccionEntrega(direccionEntrega);
        return this;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public LocalDate getFechaPedido() {
        return this.fechaPedido;
    }

    public Pedido fechaPedido(LocalDate fechaPedido) {
        this.setFechaPedido(fechaPedido);
        return this;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaEntrega() {
        return this.fechaEntrega;
    }

    public Pedido fechaEntrega(LocalDate fechaEntrega) {
        this.setFechaEntrega(fechaEntrega);
        return this;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Double getDescuento() {
        return this.descuento;
    }

    public Pedido descuento(Double descuento) {
        this.setDescuento(descuento);
        return this;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getTipoPago() {
        return this.tipoPago;
    }

    public Pedido tipoPago(String tipoPago) {
        this.setTipoPago(tipoPago);
        return this;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Double getPrecioTotal() {
        return this.precioTotal;
    }

    public Pedido precioTotal(Double precioTotal) {
        this.setPrecioTotal(precioTotal);
        return this;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Pedido observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getEntregado() {
        return this.entregado;
    }

    public Pedido entregado(Boolean entregado) {
        this.setEntregado(entregado);
        return this;
    }

    public void setEntregado(Boolean entregado) {
        this.entregado = entregado;
    }

    public Boolean getEnviado() {
        return this.enviado;
    }

    public Pedido enviado(Boolean enviado) {
        this.setEnviado(enviado);
        return this;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public Factura getFactura() {
        return this.factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Pedido factura(Factura factura) {
        this.setFactura(factura);
        return this;
    }

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        if (this.productos != null) {
            this.productos.forEach(i -> i.setPedido(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setPedido(this));
        }
        this.productos = productos;
    }

    public Pedido productos(Set<Producto> productos) {
        this.setProductos(productos);
        return this;
    }

    public Pedido addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setPedido(this);
        return this;
    }

    public Pedido removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setPedido(null);
        return this;
    }

    public Empleado getEmpleado() {
        return this.empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Pedido empleado(Empleado empleado) {
        this.setEmpleado(empleado);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Pedido empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Almacen getAlmacen() {
        return this.almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Pedido almacen(Almacen almacen) {
        this.setAlmacen(almacen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
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
            "}";
    }
}
