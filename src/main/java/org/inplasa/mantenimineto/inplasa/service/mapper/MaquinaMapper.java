package org.inplasa.mantenimineto.inplasa.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import java.util.List;
import org.inplasa.mantenimineto.inplasa.filters.model.FiltroBusqueda;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.model.db.MaquinaDb;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaList;

@Mapper
public interface MaquinaMapper {
    MaquinaMapper INSTANCE = Mappers.getMapper(MaquinaMapper.class);

    /** Crear el DTO de edición a partir de la entidad */
    @Mapping(source = "seccion.id", target = "seccionId")
    MaquinaEdit maquinaDbToMaquinaEdit(MaquinaDb db);

    /** Crear la entidad a partir del DTO de edición */
    @Mapping(source = "seccionId", target = "seccionId")
    MaquinaDb maquinaEditToMaquinaDb(MaquinaEdit dto);

    /** DTO de lectura con nombre de sección */
    @Mapping(source = "seccion.id", target = "seccionId")
    @Mapping(source = "seccion.nombre", target = "seccionNombre")
    MaquinaInfo maquinaDbToMaquinaInfo(MaquinaDb db);

    /** Crear la entidad a partir del DTO de lectura (por si lo necesitas) */
    @Mapping(source = "seccionId", target = "seccionId")
    MaquinaDb maquinaInfoToMaquinaDb(MaquinaInfo info);

    /** Lista reducida con nombre de sección */
    @Mapping(source = "seccion.nombre", target = "seccionNombre")
    List<MaquinaList> maquinaDbToMaquinaList(List<MaquinaDb> dbList);

    /** Actualizar entidad existente desde el DTO de edición */
    @Mapping(source = "seccionId", target = "seccionId")
    void updateMaquinaDbFromMaquinaEdit(MaquinaEdit dto, @MappingTarget MaquinaDb db);

    /** Convertir página de entidades en respuesta paginada */
    static PaginaResponse<MaquinaList> pageToPaginaResponseMaquinaList(
            Page<MaquinaDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                INSTANCE.maquinaDbToMaquinaList(page.getContent()),
                filtros,
                ordenaciones);
    }
}
