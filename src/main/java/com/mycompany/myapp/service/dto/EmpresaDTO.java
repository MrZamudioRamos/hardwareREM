package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Empresa} entity.
 */
public class EmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreSocial;

    @NotNull
    private String cif;

    @NotNull
    private String telefono;

    @NotNull
    private String mail;

    @NotNull
    private String pais;

    @NotNull
    private String provincia;

    @NotNull
    private String sucursal;

    @NotNull
    private String codigoPostal;

    @NotNull
    private String calle;

    @NotNull
    private String categoria;

    @NotNull
    private LocalDate fechaCreacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSocial() {
        return nombreSocial;
    }

    public void setNombreSocial(String nombreSocial) {
        this.nombreSocial = nombreSocial;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpresaDTO)) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaDTO{" +
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
