package org.inplasa.mantenimineto.inplasa.service.mapper;

import java.util.List;

import org.inplasa.mantenimineto.inplasa.filters.model.FiltroBusqueda;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.model.db.ProveedorDb;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface ProveedorMapper {
    ProveedorMapper INSTANCE = Mappers.getMapper(ProveedorMapper.class);
    
    ProveedorEdit proveedorDbToProveedorEdit(ProveedorDb proveedorDb);
    ProveedorDb proveedorEditToProveedorDb(ProveedorEdit proveedorEdit);

    ProveedorInfo proveedorDbToProveedorInfo(ProveedorDb proveedorDb);
    ProveedorDb proveedorInfoToProveedorDb(ProveedorInfo proveedorInfo);
    
    List<ProveedorList> proveedorDbToProveedorList(List<ProveedorDb> proveedorDb);
    void updateProveedorDbFromProveedorEdit(ProveedorEdit proveedorEdit,@MappingTarget ProveedorDb proveedorDb);

    static PaginaResponse<ProveedorList> pageToPaginaResponseProveedorList(
            Page<ProveedorDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.proveedorDbToProveedorList(page.getContent()),
                filtros,
                ordenaciones);
    }
}