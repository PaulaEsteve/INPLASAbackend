package org.inplasa.mantenimineto.inplasa.service;

import java.util.List;
import org.inplasa.mantenimineto.inplasa.exception.FiltroException;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.filters.model.PeticionListadoFiltrado;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaList;

public interface MaquinaService {
    MaquinaEdit create(MaquinaEdit dto);
    MaquinaEdit update(Long id, MaquinaEdit dto);
    MaquinaInfo read(Long id);
    void delete(Long id);
    PaginaResponse<MaquinaList> findAll(PeticionListadoFiltrado peticion) throws FiltroException;
    PaginaResponse<MaquinaList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
}