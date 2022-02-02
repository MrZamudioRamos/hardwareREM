package com.mycompany.myapp.repository.specification;

import com.mycompany.myapp.domain.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmpleadoSpecification extends JpaSpecificationExecutor<Empleado> {
    public static Specification<Empleado> searchingParam(String filter) {
        return new Specification<Empleado>() {
            private static final long serialVersionUID = 1L;

            public Predicate toPredicate(Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                query.distinct(true);
                List<Predicate> ors = new ArrayList<Predicate>();

                Expression<String> dni = root.get("dni").as(String.class);
                Expression<String> nombre = root.get("nombre").as(String.class);
                Expression<String> apellidos = root.get("apellidos").as(String.class);
                Expression<String> telefono = root.get("telefono").as(String.class);
                Expression<String> mail = root.get("mail").as(String.class);
                Expression<String> pais = root.get("pais").as(String.class);
                Expression<String> provincia = root.get("provincia").as(String.class);
                Expression<String> codigoPostal = root.get("codigoPostal").as(String.class);
                Expression<String> calle = root.get("calle").as(String.class);
                Expression<String> numeroPedidos = root.get("numeroPedidos").as(String.class);
                Expression<String> tipoContrato = root.get("tipoContrato").as(String.class);
                Expression<String> comision = root.get("comision").as(String.class);
                Expression<String> activo = root.get("activo").as(String.class);

                //Join<Empleado, Venta> venta = root.join("venta", JoinType.LEFT);
                //Join user?

                String[] searchParam = filter.split(" ");
                for (int i = 0; i < searchParam.length; i++) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    predicates.add(builder.like(nombre, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(dni, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(apellidos, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(telefono, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(mail, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(pais, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(provincia, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(codigoPostal, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(calle, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(numeroPedidos, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(tipoContrato, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(comision, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(activo, "%" + searchParam[i] + "%"));

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
