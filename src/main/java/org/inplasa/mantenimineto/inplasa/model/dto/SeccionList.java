package org.inplasa.mantenimineto.inplasa.model.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeccionList implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotNull(message = "el nombre de la seccion no puede ser nulo.")
    private String nombre;
}
