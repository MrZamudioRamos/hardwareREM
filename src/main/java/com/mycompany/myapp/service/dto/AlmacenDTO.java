package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Almacen} entity.
 */
public class AlmacenDTO implements Serializable {

    private Long id;

    @NotNull
    private String telefono;

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
    private String albaran;

    private Integer stockProductos;

    private EmpresaDTO empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public Integer getStockProductos() {
        return stockProductos;
    }

    public void setStockProductos(Integer stockProductos) {
        this.stockProductos = stockProductos;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlmacenDTO)) {
            return false;
        }

        AlmacenDTO almacenDTO = (AlmacenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, almacenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlmacenDTO{" +
            "id=" + getId() +
            ", telefono='" + getTelefono() + "'" +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", sucursal='" + getSucursal() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", calle='" + getCalle() + "'" +
            ", albaran='" + getAlbaran() + "'" +
            ", stockProductos=" + getStockProductos() +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
