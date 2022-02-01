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
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100, min = 3)
    @Column(name = "nombre_social", nullable = false)
    private String nombreSocial;

    @NotNull
    @Size(max = 9, min = 9)
    @Column(name = "cif", nullable = false, unique = true)
    private String cif;

    @NotNull
    @Size(max = 15, min = 9)
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Email
    @Column(name = "mail", nullable = false)
    private String mail;

    @NotNull
    @Size(max = 50, min = 3)
    @Column(name = "pais", nullable = false)
    private String pais;

    @NotNull
    @Size(max = 50, min = 3)
    @Column(name = "provincia", nullable = false)
    private String provincia;

    @NotNull
    @Size(max = 50, min = 3)
    @Column(name = "sucursal", nullable = false)
    private String sucursal;

    @NotNull
    @Size(min = 5, max = 10)
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Size(max = 50, min = 3)
    @Column(name = "calle", nullable = false)
    private String calle;

    @NotNull
    @Size(max = 50, min = 3)
    @Column(name = "categoria", nullable = false)
    private String categoria;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cliente", "empresa" }, allowSetters = true)
    private Set<Factura> facturas = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "facturas", "empresa" }, allowSetters = true)
    private Set<Cliente> clientes = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "iva", "componentes", "empresa", "pedido", "almacen" }, allowSetters = true)
    private Set<Producto> productos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "factura", "productos", "empleado", "empresa", "almacen" }, allowSetters = true)
    private Set<Pedido> pedidos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedidos", "productos", "empresa" }, allowSetters = true)
    private Set<Almacen> almacens = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedidos", "empresa" }, allowSetters = true)
    private Set<Empleado> empleados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSocial() {
        return this.nombreSocial;
    }

    public Empresa nombreSocial(String nombreSocial) {
        this.setNombreSocial(nombreSocial);
        return this;
    }

    public void setNombreSocial(String nombreSocial) {
        this.nombreSocial = nombreSocial;
    }

    public String getCif() {
        return this.cif;
    }

    public Empresa cif(String cif) {
        this.setCif(cif);
        return this;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Empresa telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return this.mail;
    }

    public Empresa mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPais() {
        return this.pais;
    }

    public Empresa pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Empresa provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getSucursal() {
        return this.sucursal;
    }

    public Empresa sucursal(String sucursal) {
        this.setSucursal(sucursal);
        return this;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Empresa codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return this.calle;
    }

    public Empresa calle(String calle) {
        this.setCalle(calle);
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Empresa categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Empresa fechaCreacion(LocalDate fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Factura> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        if (this.facturas != null) {
            this.facturas.forEach(i -> i.setEmpresa(null));
        }
        if (facturas != null) {
            facturas.forEach(i -> i.setEmpresa(this));
        }
        this.facturas = facturas;
    }

    public Empresa facturas(Set<Factura> facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public Empresa addFactura(Factura factura) {
        this.facturas.add(factura);
        factura.setEmpresa(this);
        return this;
    }

    public Empresa removeFactura(Factura factura) {
        this.facturas.remove(factura);
        factura.setEmpresa(null);
        return this;
    }

    public Set<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        if (this.clientes != null) {
            this.clientes.forEach(i -> i.setEmpresa(null));
        }
        if (clientes != null) {
            clientes.forEach(i -> i.setEmpresa(this));
        }
        this.clientes = clientes;
    }

    public Empresa clientes(Set<Cliente> clientes) {
        this.setClientes(clientes);
        return this;
    }

    public Empresa addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setEmpresa(this);
        return this;
    }

    public Empresa removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setEmpresa(null);
        return this;
    }

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        if (this.productos != null) {
            this.productos.forEach(i -> i.setEmpresa(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setEmpresa(this));
        }
        this.productos = productos;
    }

    public Empresa productos(Set<Producto> productos) {
        this.setProductos(productos);
        return this;
    }

    public Empresa addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setEmpresa(this);
        return this;
    }

    public Empresa removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setEmpresa(null);
        return this;
    }

    public Set<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        if (this.pedidos != null) {
            this.pedidos.forEach(i -> i.setEmpresa(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setEmpresa(this));
        }
        this.pedidos = pedidos;
    }

    public Empresa pedidos(Set<Pedido> pedidos) {
        this.setPedidos(pedidos);
        return this;
    }

    public Empresa addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setEmpresa(this);
        return this;
    }

    public Empresa removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setEmpresa(null);
        return this;
    }

    public Set<Almacen> getAlmacens() {
        return this.almacens;
    }

    public void setAlmacens(Set<Almacen> almacens) {
        if (this.almacens != null) {
            this.almacens.forEach(i -> i.setEmpresa(null));
        }
        if (almacens != null) {
            almacens.forEach(i -> i.setEmpresa(this));
        }
        this.almacens = almacens;
    }

    public Empresa almacens(Set<Almacen> almacens) {
        this.setAlmacens(almacens);
        return this;
    }

    public Empresa addAlmacen(Almacen almacen) {
        this.almacens.add(almacen);
        almacen.setEmpresa(this);
        return this;
    }

    public Empresa removeAlmacen(Almacen almacen) {
        this.almacens.remove(almacen);
        almacen.setEmpresa(null);
        return this;
    }

    public Set<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        if (this.empleados != null) {
            this.empleados.forEach(i -> i.setEmpresa(null));
        }
        if (empleados != null) {
            empleados.forEach(i -> i.setEmpresa(this));
        }
        this.empleados = empleados;
    }

    public Empresa empleados(Set<Empleado> empleados) {
        this.setEmpleados(empleados);
        return this;
    }

    public Empresa addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
        empleado.setEmpresa(this);
        return this;
    }

    public Empresa removeEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
        empleado.setEmpresa(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nombreSocial='" + getNombreSocial() + "'" +
            ", cif='" + getCif() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", mail='" + getMail() + "'" +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", sucursal='" + getSucursal() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", calle='" + getCalle() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}
