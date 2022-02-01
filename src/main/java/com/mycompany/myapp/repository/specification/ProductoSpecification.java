package com.mycompany.myapp.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mycompany.myapp.domain.Producto;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductoSpecification extends JpaSpecificationExecutor<Producto> {

	public static Specification<Producto> searchingParam(String filter) {

		return new Specification<Producto>() {

			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {


				query.distinct(true);
				List<Predicate> ors = new ArrayList<Predicate>();

                Expression<String> nombre = root.get("nombre").as(String.class);
                Expression<String> numeroSerie = root.get("numeroSerie").as(String.class);
				Expression<String> marca = root.get("marca").as(String.class);
                Expression<String> modelo = root.get("modelo").as(String.class);
                Expression<String> descripcion = root.get("descripcion").as(String.class);
                Expression<String> peso = root.get("peso").as(String.class);
                Expression<String> fechaVenta = root.get("fechaVenta").as(String.class);
                Expression<String> precioCompra = root.get("precioCompra").as(String.class);
                Expression<String> precioBruto = root.get("precioBruto").as(String.class);
                Expression<String> precioIva = root.get("precioIva").as(String.class);
                Expression<String> vendido = root.get("vendido").as(String.class);


				//Join<Producto, Venta> venta = root.join("venta", JoinType.LEFT);
                //Join user?

				String[] searchParam = filter.split(" ");
				for (int i = 0; i < searchParam.length; i++) {

					List<Predicate> predicates = new ArrayList<Predicate>();
					predicates.add(builder.like(nombre, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(numeroSerie, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(marca, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(modelo, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(descripcion, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(peso, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(fechaVenta, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(precioCompra, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(precioBruto, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(precioIva, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(vendido, "%" + searchParam[i] + "%"));

					//predicates.add(builder.like(venta.<String>get("tipoPago"), "%" + searchParam[i] + "%"));
					//predicates.add(builder.like(venta.<String>get("total"), "%" + searchParam[i] + "%"));


					ors.add(builder.or(predicates.toArray(new Predicate[] {})));
				}
				Predicate result = builder.and(ors.toArray(new Predicate[] {}));
				return result;
			}

		};
        }
    }
