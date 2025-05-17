package org.inplasa.mantenimineto.inplasa.controller;

import java.util.List;

import org.inplasa.mantenimineto.inplasa.exception.FiltroException;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.filters.model.PeticionListadoFiltrado;
import org.inplasa.mantenimineto.inplasa.helper.BindingResultHelper;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.SeccionList;
import org.inplasa.mantenimineto.inplasa.service.SeccionService;
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
@RequestMapping("/api/secciones")
@CrossOrigin(origins = "http://localhost:4200")
public class SeccionController {
    private final SeccionService seccionService;

    @PostMapping
    public ResponseEntity<SeccionEdit> create(
            @Valid @RequestBody SeccionEdit dto,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "SECCION_CREATE_VALIDATION");
        SeccionEdit created = seccionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeccionInfo> read(@PathVariable String id) {
        Long idLong = Long.valueOf(id);
        SeccionInfo info = seccionService.read(idLong);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeccionEdit> update(
            @PathVariable String id,
            @Valid @RequestBody SeccionEdit dto,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "SECCION_UPDATE_VALIDATION");
        Long idLong = Long.valueOf(id);
        SeccionEdit updated = seccionService.update(idLong, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Long idLong = Long.valueOf(id);
        seccionService.delete(idLong);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginaResponse<SeccionList>> getAll(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {

        return ResponseEntity.ok(seccionService.findAll(filter, page, size, sort));
    }

    @PostMapping("/secciones")
    public ResponseEntity<PaginaResponse<SeccionList>> getAll(
            @Valid @RequestBody PeticionListadoFiltrado peticion) throws FiltroException {
        return ResponseEntity.ok(seccionService.findAll(peticion));
    }
}
