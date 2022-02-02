package com.mycompany.myapp.repository.specification;

import com.mycompany.myapp.domain.Pedido;
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

public interface PedidoSpecification extends JpaSpecificationExecutor<Pedido> {
    public static Specification<Pedido> searchingParam(String filter) {
        return new Specification<Pedido>() {
            private static final long serialVersionUID = 1L;

            public Predicate toPredicate(Root<Pedido> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                query.distinct(true);
                List<Predicate> ors = new ArrayList<Predicate>();

                Expression<String> fechaEntrega = root.get("fechaEntrega").as(String.class);
                Expression<String> fechaPedido = root.get("fechaPedido").as(String.class);
                Expression<String> direccionEntrega = root.get("direccionEntrega").as(String.class);
                Expression<String> descuento = root.get("descuento").as(String.class);
                Expression<String> tipoPago = root.get("tipoPago").as(String.class);
                Expression<String> precioTotal = root.get("precioTotal").as(String.class);
                Expression<String> entregado = root.get("entregado").as(String.class);
                Expression<String> observaciones = root.get("entregado").as(String.class);
                Expression<String> enviado = root.get("entregado").as(String.class);

                //Join<Pedido, Venta> venta = root.join("venta", JoinType.LEFT);
                //Join user?

                String[] searchParam = filter.split(" ");
                for (int i = 0; i < searchParam.length; i++) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    predicates.add(builder.like(fechaPedido, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(fechaEntrega, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(direccionEntrega, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(descuento, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(tipoPago, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(precioTotal, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(observaciones, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(entregado, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(enviado, "%" + searchParam[i] + "%"));

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
