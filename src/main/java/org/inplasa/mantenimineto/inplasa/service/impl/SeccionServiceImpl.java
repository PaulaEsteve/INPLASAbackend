package org.inplasa.mantenimineto.inplasa.service.impl;

import java.util.List;

import org.inplasa.mantenimineto.inplasa.exception.EntityIllegalArgumentException;
import org.inplasa.mantenimineto.inplasa.exception.EntityNotFoundException;
import org.inplasa.mantenimineto.inplasa.exception.FiltroException;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.filters.model.PeticionListadoFiltrado;
import org.inplasa.mantenimineto.inplasa.filters.specification.FiltroBusquedaSpecification;
import org.inplasa.mantenimineto.inplasa.filters.utils.PaginationFactory;
import org.inplasa.mantenimineto.inplasa.filters.utils.PeticionListadoFiltradoConverter;
import org.inplasa.mantenimineto.inplasa.model.db.SeccionDb;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionList;
import org.inplasa.mantenimineto.inplasa.repository.SeccionRepository;
import org.inplasa.mantenimineto.inplasa.service.SeccionService;
import org.inplasa.mantenimineto.inplasa.service.mapper.SeccionMapper;
import org.springframework.stereotype.Service;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;

@Service
public class SeccionServiceImpl implements SeccionService{
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionListadoFiltradoConverter;
    private final SeccionRepository seccionRepository;

    public SeccionServiceImpl(SeccionRepository seccionRepository, PaginationFactory paginationFactory, PeticionListadoFiltradoConverter peticionListadoFiltradoConverter) {
        this.seccionRepository = seccionRepository;
        this.paginationFactory = paginationFactory;
        this.peticionListadoFiltradoConverter = peticionListadoFiltradoConverter;
    }

    @Override
    public SeccionEdit create(SeccionEdit seccionEdit) {
        SeccionDb entity = SeccionMapper.INSTANCE.seccionEditToSeccionDb(seccionEdit);
        return SeccionMapper.INSTANCE.seccionDbToSeccionEdit(seccionRepository.save(entity));
    }

    @Override
    public SeccionInfo read(Long id) {
        SeccionDb entity = seccionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("SECCION_NOT_FOUND",
                "No se encontró la sección"));
        return SeccionMapper.INSTANCE.seccionDbToSeccionInfo(entity);
    }

    @Override
    public SeccionEdit update(Long id, SeccionEdit seccionEdit) {
        if (!id.equals(seccionEdit.getId())) { // Evitamos errores e inconsistencias en la lógica de negocio
            throw new EntityIllegalArgumentException("SECCION_ID_MISMATCH",
            "El ID proporcionado no coincide con el ID de la sección.");
        }
        SeccionDb entity = seccionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("SECCION_NOT_FOUND",
                "No se encontró la sección"));
        SeccionMapper.INSTANCE.updateSeccionDbFromSeccionEdit(seccionEdit, entity);
        return SeccionMapper.INSTANCE.seccionDbToSeccionEdit(seccionRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (seccionRepository.existsById(id)) {
            seccionRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("SECCION_NOT_FOUND",
                "No se encontró la sección");
            
        }
    }

    @Override
    public PaginaResponse<SeccionList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<SeccionDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<SeccionDb>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el
            // catch
            Page<SeccionDb> page = seccionRepository.findAll(filtrosBusquedaSpecification, pageable);
            // Devolver respuesta
            return SeccionMapper.pageToPaginaResponseSeccionList(
                    page,
                    peticionListadoFiltrado.getListaFiltros(),
                    peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            String cause = "";
            if (e.getRootCause() != null) {
                if (e.getCause().getMessage() != null)
                    cause = e.getRootCause().getMessage();
            }
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato",
                    e.getMessage() + ":" + cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }

    @Override
    public PaginaResponse<SeccionList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException {
        PeticionListadoFiltrado peticionListadoFiltrado = peticionListadoFiltradoConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticionListadoFiltrado);
    }
}
