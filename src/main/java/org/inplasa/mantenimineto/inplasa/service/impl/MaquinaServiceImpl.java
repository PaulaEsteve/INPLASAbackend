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
import org.inplasa.mantenimineto.inplasa.model.db.MaquinaDb;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaList;
import org.inplasa.mantenimineto.inplasa.repository.MaquinaRepository;
import org.inplasa.mantenimineto.inplasa.service.MaquinaService;
import org.inplasa.mantenimineto.inplasa.service.mapper.MaquinaMapper;
import org.springframework.stereotype.Service;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;

@Service
public class MaquinaServiceImpl implements MaquinaService {
    private final MaquinaRepository maquinaRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionListadoFiltradoConverter;

    public MaquinaServiceImpl(MaquinaRepository maquinaRepository,
                              PaginationFactory paginationFactory,
                              PeticionListadoFiltradoConverter peticionListadoFiltradoConverter) {
        this.maquinaRepository = maquinaRepository;
        this.paginationFactory = paginationFactory;
        this.peticionListadoFiltradoConverter = peticionListadoFiltradoConverter;
    }

    @Override
    public MaquinaEdit create(MaquinaEdit dto) {
        MaquinaDb entity = MaquinaMapper.INSTANCE.maquinaEditToMaquinaDb(dto);
        return MaquinaMapper.INSTANCE.maquinaDbToMaquinaEdit(maquinaRepository.save(entity));
    }

    @Override
    public MaquinaInfo read(Long id) {
        MaquinaDb entity = maquinaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("MAQUINA_NOT_FOUND","No se encontró la máquina"));
        return MaquinaMapper.INSTANCE.maquinaDbToMaquinaInfo(entity);
    }

    @Override
    public MaquinaEdit update(Long id, MaquinaEdit dto) {
        if (!id.equals(dto.getId())) {
            throw new EntityIllegalArgumentException("MAQUINA_ID_MISMATCH","El ID proporcionado no coincide con la máquina.");
        }
        MaquinaDb entity = maquinaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("MAQUINA_NOT_FOUND","No se encontró la máquina"));
        MaquinaMapper.INSTANCE.updateMaquinaDbFromMaquinaEdit(dto, entity);
        return MaquinaMapper.INSTANCE.maquinaDbToMaquinaEdit(maquinaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (maquinaRepository.existsById(id)) {
            maquinaRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("MAQUINA_NOT_FOUND","No se encontró la máquina");
        }
    }

    @Override
    public PaginaResponse<MaquinaList> findAll(PeticionListadoFiltrado peticion) throws FiltroException {
        try {
            Pageable pageable = paginationFactory.createPageable(peticion);
            Specification<MaquinaDb> spec = new FiltroBusquedaSpecification<>(peticion.getListaFiltros());
            Page<MaquinaDb> page = maquinaRepository.findAll(spec, pageable);
            return MaquinaMapper.pageToPaginaResponseMaquinaList(page, peticion.getListaFiltros(), peticion.getSort());
        } catch (JpaSystemException e) {
            String cause = e.getRootCause() != null ? e.getRootCause().getMessage() : "";
            throw new FiltroException("BAD_OPERATOR_FILTER","Error: operación inválida sobre atributo", e.getMessage()+":"+cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER","Error: atributo de ordenación inválido", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER","Error: filtro inválido", e.getMessage());
        }
    }

    @Override
    public PaginaResponse<MaquinaList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException {
        PeticionListadoFiltrado peticion = peticionListadoFiltradoConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }
}