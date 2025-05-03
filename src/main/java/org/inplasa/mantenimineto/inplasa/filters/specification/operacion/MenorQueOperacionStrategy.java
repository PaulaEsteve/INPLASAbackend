package org.inplasa.mantenimineto.inplasa.filters.specification.operacion;

import org.inplasa.mantenimineto.inplasa.filters.model.FiltroBusqueda;
import org.inplasa.mantenimineto.inplasa.filters.model.TipoOperacionBusqueda;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MenorQueOperacionStrategy implements OperacionBusquedaStrategy {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Predicate crearPredicado(
        Root<?> root, 
        CriteriaBuilder criteriaBuilder, 
        FiltroBusqueda filtro
    ) {
        return criteriaBuilder.lessThan(
            root.get(filtro.getAtributo()), 
            (Comparable) filtro.getValor()
        );
    }

    @Override
    public boolean soportaOperacion(TipoOperacionBusqueda operacion) {
        return operacion == TipoOperacionBusqueda.MENOR_QUE;
    }
}
