package org.inplasa.mantenimineto.inplasa.service;

import java.util.List;

import org.inplasa.mantenimineto.inplasa.exception.FiltroException;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.filters.model.PeticionListadoFiltrado;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionList;

public interface SeccionService {
    public SeccionEdit create(SeccionEdit seccionEdit);
    public SeccionEdit update(Long id, SeccionEdit seccionEdit);
    public SeccionInfo read(Long id);
    public void delete(Long id);

    PaginaResponse<SeccionList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
    PaginaResponse<SeccionList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
}
