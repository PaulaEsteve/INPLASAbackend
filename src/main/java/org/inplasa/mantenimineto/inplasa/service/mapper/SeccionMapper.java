package org.inplasa.mantenimineto.inplasa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

import org.inplasa.mantenimineto.inplasa.filters.model.FiltroBusqueda;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.model.db.SeccionDb;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionList;

@Mapper
public interface SeccionMapper {
    SeccionMapper INSTANCE = Mappers.getMapper(SeccionMapper.class);

    SeccionEdit seccionDbToSeccionEdit(SeccionDb seccionDb);

    SeccionDb seccionEditToSeccionDb(SeccionEdit seccionEdit);

    SeccionInfo seccionDbToSeccionInfo(SeccionDb seccionDb);

    SeccionDb seccionInfoToSeccionDb(SeccionInfo seccionInfo);

    List<SeccionList> seccionDbToSeccionList(List<SeccionDb> seccionDb);

    void updateSeccionDbFromSeccionEdit(SeccionEdit seccionEdit, @MappingTarget SeccionDb seccionDb);

    static PaginaResponse<SeccionList> pageToPaginaResponseSeccionList(
            Page<SeccionDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.seccionDbToSeccionList(page.getContent()),
                filtros,
                ordenaciones);
    }
}
