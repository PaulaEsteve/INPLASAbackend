package org.inplasa.mantenimineto.inplasa.controller;

import java.util.List;

import org.inplasa.mantenimineto.inplasa.exception.FiltroException;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.filters.model.PeticionListadoFiltrado;
import org.inplasa.mantenimineto.inplasa.helper.BindingResultHelper;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.ProveedorList;
import org.inplasa.mantenimineto.inplasa.service.ProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "http://localhost:4200")

public class ProveedorController {
    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorEdit> create(
            @Valid @RequestBody ProveedorEdit dto,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "PROVEEDOR_CREATE_VALIDATION");
        ProveedorEdit created = proveedorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorInfo> read(@PathVariable String id) {
        Long idLong = Long.valueOf(id);
        ProveedorInfo info = proveedorService.read(idLong);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorEdit> update(
            @PathVariable String id,
            @Valid @RequestBody ProveedorEdit dto,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "PROVEEDOR_UPDATE_VALIDATION");
        Long idLong = Long.valueOf(id);
        ProveedorEdit updated = proveedorService.update(idLong, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Long idLong = Long.valueOf(id);
        proveedorService.delete(idLong);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginaResponse<ProveedorList>> getAll(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {

        return ResponseEntity.ok(proveedorService.findAll(filter, page, size, sort));   
    }

    @PostMapping("/proveedores")
    public ResponseEntity<PaginaResponse<ProveedorList>> getAll(
            @Valid @RequestBody PeticionListadoFiltrado peticion) throws FiltroException {
        return ResponseEntity.ok(proveedorService.findAll(peticion));
    }
}
