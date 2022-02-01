package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Almacen.
 */
@Entity
@Table(name = "almacen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Almacen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "pais", nullable = false)
    private String pais;

    @NotNull
    @Column(name = "provincia", nullable = false)
    private String provincia;

    @NotNull
    @Column(name = "sucursal", nullable = false)
    private String sucursal;

    @NotNull
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Column(name = "calle", nullable = false)
    private String calle;

    @NotNull
    @Column(name = "albaran", nullable = false)
    private String albaran;

    @Column(name = "stock_productos")
    private Integer stockProductos;

    @OneToMany(mappedBy = "almacen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "factura", "productos", "empleado", "empresa", "almacen" }, allowSetters = true)
    private Set<Pedido> pedidos = new HashSet<>();

    @OneToMany(mappedBy = "almacen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "iva", "componentes", "empresa", "pedido", "almacen" }, allowSetters = true)
    private Set<Producto> productos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "facturas", "clientes", "productos", "pedidos", "almacens", "empleados" }, allowSetters = true)
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Almacen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Almacen telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return this.pais;
    }

    public Almacen pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Almacen provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public Almacen sucursal(String sucursal) {
        this.setSucursal(sucursal);
        return this;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Almacen codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return this.calle;
    }

    public Almacen calle(String calle) {
        this.setCalle(calle);
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAlbaran() {
        return this.albaran;
    }

    public Almacen albaran(String albaran) {
        this.setAlbaran(albaran);
        return this;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public Integer getStockProductos() {
        return this.stockProductos;
    }

    public Almacen stockProductos(Integer stockProductos) {
        this.setStockProductos(stockProductos);
        return this;
    }

    public void setStockProductos(Integer stockProductos) {
        this.stockProductos = stockProductos;
    }

    public Set<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        if (this.pedidos != null) {
            this.pedidos.forEach(i -> i.setAlmacen(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setAlmacen(this));
        }
        this.pedidos = pedidos;
    }

    public Almacen pedidos(Set<Pedido> pedidos) {
        this.setPedidos(pedidos);
        return this;
    }

    public Almacen addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setAlmacen(this);
        return this;
    }

    public Almacen removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setAlmacen(null);
        return this;
    }

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        if (this.productos != null) {
            this.productos.forEach(i -> i.setAlmacen(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setAlmacen(this));
        }
        this.productos = productos;
    }

    public Almacen productos(Set<Producto> productos) {
        this.setProductos(productos);
        return this;
    }

    public Almacen addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setAlmacen(this);
        return this;
    }

    public Almacen removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setAlmacen(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Almacen empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Almacen)) {
            return false;
        }
        return id != null && id.equals(((Almacen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Almacen{" +
            "id=" + getId() +
            ", telefono='" + getTelefono() + "'" +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", sucursal='" + getSucursal() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", calle='" + getCalle() + "'" +
            ", albaran='" + getAlbaran() + "'" +
            ", stockProductos=" + getStockProductos() +
            "}";
    }
}
