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
import org.hibernate.validator.constraints.Range;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50, min = 1)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 300, min = 1)
    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;

    @NotNull
    @Size(max = 50, min = 1)
    @Column(name = "marca", nullable = false)
    private String marca;

    @NotNull
    @Size(max = 150, min = 1)
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @NotNull
    @Size(max = 300, min = 1)
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Range(min = (long) 0.0)
    @Column(name = "peso", nullable = false)
    private Double peso;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @NotNull
    @Range(min = (long) 0.0)
    @Column(name = "precio_compra", nullable = false)
    private Double precioCompra;

    @NotNull
    @Range(min = (long) 0.0)
    @Column(name = "precio_bruto", nullable = false)
    private Double precioBruto;

    @NotNull
    @Range(min = (long) 0.0)
    @Column(name = "precio_iva", nullable = false)
    private Double precioIva;

    @NotNull
    @Column(name = "vendido", nullable = false)
    private Boolean vendido;

    @OneToOne
    @JoinColumn(unique = true)
    private Iva iva;

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "producto" }, allowSetters = true)
    private Set<Componente> componentes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "facturas", "clientes", "productos", "pedidos", "almacens", "empleados" }, allowSetters = true)
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties(value = { "factura", "productos", "empleado", "empresa", "almacen" }, allowSetters = true)
    private Pedido pedido;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pedidos", "productos", "empresa" }, allowSetters = true)
    private Almacen almacen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Producto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Producto numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarca() {
        return this.marca;
    }

    public Producto marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Producto modelo(String modelo) {
        this.setModelo(modelo);
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPeso() {
        return this.peso;
    }

    public Producto peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaVenta() {
        return this.fechaVenta;
    }

    public Producto fechaVenta(LocalDate fechaVenta) {
        this.setFechaVenta(fechaVenta);
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getPrecioCompra() {
        return this.precioCompra;
    }

    public Producto precioCompra(Double precioCompra) {
        this.setPrecioCompra(precioCompra);
        return this;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioBruto() {
        return this.precioBruto;
    }

    public Producto precioBruto(Double precioBruto) {
        this.setPrecioBruto(precioBruto);
        return this;
    }

    public void setPrecioBruto(Double precioBruto) {
        this.precioBruto = precioBruto;
    }

    public Double getPrecioIva() {
        return this.precioIva;
    }

    public Producto precioIva(Double precioIva) {
        this.setPrecioIva(precioIva);
        return this;
    }

    public void setPrecioIva(Double precioIva) {
        this.precioIva = precioIva;
    }

    public Boolean getVendido() {
        return this.vendido;
    }

    public Producto vendido(Boolean vendido) {
        this.setVendido(vendido);
        return this;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public Iva getIva() {
        return this.iva;
    }

    public void setIva(Iva iva) {
        this.iva = iva;
    }

    public Producto iva(Iva iva) {
        this.setIva(iva);
        return this;
    }

    public Set<Componente> getComponentes() {
        return this.componentes;
    }

    public void setComponentes(Set<Componente> componentes) {
        if (this.componentes != null) {
            this.componentes.forEach(i -> i.setProducto(null));
        }
        if (componentes != null) {
            componentes.forEach(i -> i.setProducto(this));
        }
        this.componentes = componentes;
    }

    public Producto componentes(Set<Componente> componentes) {
        this.setComponentes(componentes);
        return this;
    }

    public Producto addComponente(Componente componente) {
        this.componentes.add(componente);
        componente.setProducto(this);
        return this;
    }

    public Producto removeComponente(Componente componente) {
        this.componentes.remove(componente);
        componente.setProducto(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Producto empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto pedido(Pedido pedido) {
        this.setPedido(pedido);
        return this;
    }

    public Almacen getAlmacen() {
        return this.almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Producto almacen(Almacen almacen) {
        this.setAlmacen(almacen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
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
            "}";
    }
}
