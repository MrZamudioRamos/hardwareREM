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
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 9, min = 9)
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @NotNull
    @Size(max = 100, min = 3)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 100, min = 3)
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @NotNull
    @Size(max = 15, min = 9)
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Email
    @Column(name = "mail", nullable = false)
    private String mail;

    @NotNull
    @Size(max = 150, min = 3)
    @Column(name = "pais", nullable = false)
    private String pais;

    @NotNull
    @Size(max = 150, min = 3)
    @Column(name = "provincia", nullable = false)
    private String provincia;

    @NotNull
    @Size(max = 9, min = 5)
    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @NotNull
    @Size(max = 150, min = 3)
    @Column(name = "calle", nullable = false)
    private String calle;

    @Column(name = "numero_pedidos")
    private Integer numeroPedidos;

    @NotNull
    @Column(name = "tipo_contrato", nullable = false)
    private String tipoContrato;

    @Column(name = "comision")
    private Double comision;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @NotNull
    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "factura", "productos", "empleado", "empresa", "almacen" }, allowSetters = true)
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "facturas", "clientes", "productos", "pedidos", "almacens", "empleados" }, allowSetters = true)
    private Empresa empresa;

    @OneToOne
    @MapsId("id")
    private User user;


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empleado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return this.dni;
    }

    public Empleado dni(String dni) {
        this.setDni(dni);
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Empleado nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Empleado apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Empleado telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return this.mail;
    }

    public Empleado mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPais() {
        return this.pais;
    }

    public Empleado pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Empleado provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Empleado codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return this.calle;
    }

    public Empleado calle(String calle) {
        this.setCalle(calle);
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumeroPedidos() {
        return this.numeroPedidos;
    }

    public Empleado numeroPedidos(Integer numeroPedidos) {
        this.setNumeroPedidos(numeroPedidos);
        return this;
    }

    public void setNumeroPedidos(Integer numeroPedidos) {
        this.numeroPedidos = numeroPedidos;
    }

    public String getTipoContrato() {
        return this.tipoContrato;
    }

    public Empleado tipoContrato(String tipoContrato) {
        this.setTipoContrato(tipoContrato);
        return this;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Double getComision() {
        return this.comision;
    }

    public Empleado comision(Double comision) {
        this.setComision(comision);
        return this;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Empleado activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public Empleado contrasena(String contrasena) {
        this.setContrasena(contrasena);
        return this;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Set<Pedido> getPedidos() {
        return this.pedidos;
    }



    public void setPedidos(Set<Pedido> pedidos) {
        if (this.pedidos != null) {
            this.pedidos.forEach(i -> i.setEmpleado(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setEmpleado(this));
        }
        this.pedidos = pedidos;
    }

    public Empleado pedidos(Set<Pedido> pedidos) {
        this.setPedidos(pedidos);
        return this;
    }

    public Empleado addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setEmpleado(this);
        return this;
    }

    public Empleado removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setEmpleado(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empleado empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleado)) {
            return false;
        }
        return id != null && id.equals(((Empleado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", dni='" + getDni() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", mail='" + getMail() + "'" +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", calle='" + getCalle() + "'" +
            ", numeroPedidos=" + getNumeroPedidos() +
            ", tipoContrato='" + getTipoContrato() + "'" +
            ", comision=" + getComision() +
            ", activo='" + getActivo() + "'" +
            ", contrasena='" + getContrasena() + "'" +
            "}";
    }
}
