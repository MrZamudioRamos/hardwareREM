package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Componente} entity.
 */
public class ComponenteDTO implements Serializable {

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

    private ProductoDTO producto;

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

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComponenteDTO)) {
            return false;
        }

        ComponenteDTO componenteDTO = (ComponenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, componenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComponenteDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", peso=" + getPeso() +
            ", producto=" + getProducto() +
            "}";
    }
}
