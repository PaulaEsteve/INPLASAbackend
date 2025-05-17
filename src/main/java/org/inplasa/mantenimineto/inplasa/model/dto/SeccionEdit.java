package org.inplasa.mantenimineto.inplasa.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeccionEdit implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotNull(message = "el nombre de la seccion no puede ser nulo.")
    private String nombre;
}
