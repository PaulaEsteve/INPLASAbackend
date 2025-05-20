package org.inplasa.mantenimineto.inplasa.controller;

import java.util.List;
import org.inplasa.mantenimineto.inplasa.exception.FiltroException;
import org.inplasa.mantenimineto.inplasa.filters.model.PaginaResponse;
import org.inplasa.mantenimineto.inplasa.filters.model.PeticionListadoFiltrado;
import org.inplasa.mantenimineto.inplasa.helper.BindingResultHelper;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaEdit;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaInfo;
import org.inplasa.mantenimineto.inplasa.model.dto.MaquinaList;
import org.inplasa.mantenimineto.inplasa.service.MaquinaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maquinas")
@CrossOrigin(origins = "http://localhost:4200")
public class MaquinaController {
    private final MaquinaService maquinaService;

    @PostMapping
    public ResponseEntity<MaquinaEdit> create(
            @Valid @RequestBody MaquinaEdit dto,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "MAQUINA_CREATE_VALIDATION");
        MaquinaEdit created = maquinaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaquinaInfo> read(@PathVariable String id) {
        Long idLong = Long.valueOf(id);
        MaquinaInfo info = maquinaService.read(idLong);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaquinaEdit> update(
            @PathVariable String id,
            @Valid @RequestBody MaquinaEdit dto,
            BindingResult bindingResult) {
        BindingResultHelper.validateBindingResult(bindingResult, "MAQUINA_UPDATE_VALIDATION");
        Long idLong = Long.valueOf(id);
        MaquinaEdit updated = maquinaService.update(idLong, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Long idLong = Long.valueOf(id);
        maquinaService.delete(idLong);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginaResponse<MaquinaList>> getAll(
            @RequestParam(required = false) List<String> filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") List<String> sort) throws FiltroException {
        return ResponseEntity.ok(maquinaService.findAll(filter, page, size, sort));
    }

    @PostMapping("/maquinas")
    public ResponseEntity<PaginaResponse<MaquinaList>> getAll(
            @Valid @RequestBody PeticionListadoFiltrado peticion) throws FiltroException {
        return ResponseEntity.ok(maquinaService.findAll(peticion));
    }
}