package com.mycompany.myapp.repository.specification;

import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.domain.Empresa;
import com.mycompany.myapp.domain.Factura;
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

public interface FacturaSpecification extends JpaSpecificationExecutor<Factura> {
    public static Specification<Factura> searchingParam(String filter) {
        return new Specification<Factura>() {
            private static final long serialVersionUID = 1L;

            public Predicate toPredicate(Root<Factura> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                query.distinct(true);
                List<Predicate> ors = new ArrayList<Predicate>();

                Expression<String> fechaFacturacion = root.get("fechaFacturacion").as(String.class);
                Expression<String> fechaVencimiento = root.get("fechaVencimiento").as(String.class);

                // Join<Factura, Pedido> pedido = root.join("pedido", JoinType.LEFT);
                //Join<Factura, Cliente> cliente = root.join("cliente", JoinType.LEFT);
                //Join<Factura, Empresa> empresa = root.join("pedido", JoinType.LEFT);

                String[] searchParam = filter.split(" ");
                for (int i = 0; i < searchParam.length; i++) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    predicates.add(builder.like(fechaFacturacion, "%" + searchParam[i] + "%"));
                    predicates.add(builder.like(fechaVencimiento, "%" + searchParam[i] + "%"));

                    // JOIN PEDIDO
                    // predicates.add(builder.like(pedido.<String>get("fechaPedido"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("fechaEntrega"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("direccionEntrega"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("descuento"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("tipoPago"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("precioTotal"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("observaciones"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("entregado"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(pedido.<String>get("enviado"), "%" + searchParam[i] + "%"));
                    // // JOIN CLIENTE
                    // predicates.add(builder.like(empresa.<String>get("nombreSocial"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("cif"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("telefono"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("mail"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("pais"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("provincia"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("sucursal"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("calle"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("codigoPostal"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("categoria"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(empresa.<String>get("fechaCreacion"), "%" + searchParam[i] + "%"));
                    // // JOIN EMPRESA
                    // predicates.add(builder.like(cliente.<String>get("dni"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("nombre"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("apellidos"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("telefono"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("mail"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("provincia"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("codigoPostal"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("calle"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("fidelizado"), "%" + searchParam[i] + "%"));
                    // predicates.add(builder.like(cliente.<String>get("activo"), "%" + searchParam[i] + "%"));

                    ors.add(builder.or(predicates.toArray(new Predicate[] {})));
                }
                Predicate result = builder.and(ors.toArray(new Predicate[] {}));
                return result;
            }
        };
    }
}
