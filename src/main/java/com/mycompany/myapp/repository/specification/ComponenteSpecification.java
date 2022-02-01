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

import com.mycompany.myapp.domain.Componente;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComponenteSpecification extends JpaSpecificationExecutor<Componente> {

	public static Specification<Componente> searchingParam(String filter) {

		return new Specification<Componente>() {

			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<Componente> root, CriteriaQuery<?> query, CriteriaBuilder builder) {


				query.distinct(true);
				List<Predicate> ors = new ArrayList<Predicate>();

                Expression<String> nombre = root.get("nombre").as(String.class);
                Expression<String> numeroSerie = root.get("numeroSerie").as(String.class);
				Expression<String> marca = root.get("marca").as(String.class);
                Expression<String> modelo = root.get("modelo").as(String.class);
                Expression<String> peso = root.get("peso").as(String.class);
                Expression<String> descripcion = root.get("descripcion").as(String.class);


				//Join<Componente, Venta> venta = root.join("venta", JoinType.LEFT);
                //Join user?

				String[] searchParam = filter.split(" ");
				for (int i = 0; i < searchParam.length; i++) {

					List<Predicate> predicates = new ArrayList<Predicate>();
					predicates.add(builder.like(nombre, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(numeroSerie, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(marca, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(modelo, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(peso, "%" + searchParam[i] + "%"));
					predicates.add(builder.like(descripcion, "%" + searchParam[i] + "%"));

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
