package org.inplasa.mantenimineto.inplasa.service;

import java.util.List;

import org.inplasa.mantenimineto.inplasa.exception.FiltroException;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.filters.model.PeticionListadoFiltrado;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorList;

public interface ProveedorService {
    public ProveedorEdit create(ProveedorEdit proveedorEdit);
    public ProveedorEdit update(Long id, ProveedorEdit proveedorEdit);
    public ProveedorInfo read(Long id);
    public void delete(Long id);

    PaginaResponse<ProveedorList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
    PaginaResponse<ProveedorList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
}
