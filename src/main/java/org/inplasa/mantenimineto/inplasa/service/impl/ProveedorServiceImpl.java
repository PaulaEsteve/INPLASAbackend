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
import org.inplasa.mantenimineto.inplasa.model.db.ProveedorDb;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorList;
import org.inplasa.mantenimineto.inplasa.repository.ProveedorRepository;
import org.inplasa.mantenimineto.inplasa.service.ProveedorService;
import org.inplasa.mantenimineto.inplasa.service.mapper.ProveedorMapper;
import org.springframework.stereotype.Service;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;


@Service
public class ProveedorServiceImpl implements ProveedorService{
    private final ProveedorRepository proveedorRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionListadoFiltradoConverter;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, PaginationFactory paginationFactory, PeticionListadoFiltradoConverter peticionListadoFiltradoConverter) {
        this.proveedorRepository = proveedorRepository;
        this.paginationFactory = paginationFactory;
        this.peticionListadoFiltradoConverter = peticionListadoFiltradoConverter;
    }

    @Override
    public ProveedorEdit create(ProveedorEdit proveedorEdit) {
        ProveedorDb entity = ProveedorMapper.INSTANCE.proveedorEditToProveedorDb(proveedorEdit);
        return ProveedorMapper.INSTANCE.proveedorDbToProveedorEdit(proveedorRepository.save(entity));
    }

    @Override
    public ProveedorInfo read(Long id) {
        ProveedorDb entity = proveedorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PROVEEDOR_NOT_FOUND",
                "No se encontró el proveedor"));
        return ProveedorMapper.INSTANCE.proveedorDbToProveedorInfo(entity);
    }

    @Override
    public ProveedorEdit update(Long id, ProveedorEdit proveedorEdit) {
        if (!id.equals(proveedorEdit.getId())) { // Evitamos errores e inconsistencias en la lógica de negocio
            throw new EntityIllegalArgumentException("PROVEEDOR_ID_MISMATCH",
            "El ID proporcionado no coincide con el ID del proveedor.");
        }

        ProveedorDb entity = proveedorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PROVEEDOR_NOT_FOUND_FOR_UPDATE",
                "no se encontró el proveedor"));

        ProveedorMapper.INSTANCE.updateProveedorDbFromProveedorEdit(proveedorEdit, entity);
        return ProveedorMapper.INSTANCE.proveedorDbToProveedorEdit(proveedorRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (proveedorRepository.existsById(id)) {
            proveedorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("PROVEEDOR_NOT_FOUND_FOR_DELETE",
                "No se puede eliminar. El proveedor con ID " + id + " no existe.");
        }
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<ProveedorList> findAll(PeticionListadoFiltrado peticionListadoFiltrado)
            throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<ProveedorDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<ProveedorDb>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar: puede producir cualquier de los errores controlados en el
            // catch
            Page<ProveedorDb> page = proveedorRepository.findAll(filtrosBusquedaSpecification, pageable);
            // Devolver respuesta
            return ProveedorMapper.pageToPaginaResponseProveedorList(
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
    public PaginaResponse<ProveedorList> findAll(List<String> filter, int page, int size, List<String> sort)
            throws FiltroException {
        PeticionListadoFiltrado peticion = peticionListadoFiltradoConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }
}
