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
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

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

    @NotNull
    @Column(name = "fidelizado", nullable = false)
    private Boolean fidelizado;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cliente", "empresa" }, allowSetters = true)
    private Set<Factura> facturas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "facturas", "clientes", "productos", "pedidos", "almacens", "empleados" }, allowSetters = true)
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return this.dni;
    }

    public Cliente dni(String dni) {
        this.setDni(dni);
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Cliente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Cliente apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Cliente telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return this.mail;
    }

    public Cliente mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPais() {
        return this.pais;
    }

    public Cliente pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Cliente provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public Cliente codigoPostal(String codigoPostal) {
        this.setCodigoPostal(codigoPostal);
        return this;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return this.calle;
    }

    public Cliente calle(String calle) {
        this.setCalle(calle);
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Boolean getFidelizado() {
        return this.fidelizado;
    }

    public Cliente fidelizado(Boolean fidelizado) {
        this.setFidelizado(fidelizado);
        return this;
    }

    public void setFidelizado(Boolean fidelizado) {
        this.fidelizado = fidelizado;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public Cliente activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Factura> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        if (this.facturas != null) {
            this.facturas.forEach(i -> i.setCliente(null));
        }
        if (facturas != null) {
            facturas.forEach(i -> i.setCliente(this));
        }
        this.facturas = facturas;
    }

    public Cliente facturas(Set<Factura> facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public Cliente addFactura(Factura factura) {
        this.facturas.add(factura);
        factura.setCliente(this);
        return this;
    }

    public Cliente removeFactura(Factura factura) {
        this.facturas.remove(factura);
        factura.setCliente(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cliente empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
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
            ", fidelizado='" + getFidelizado() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
