package org.inplasa.mantenimineto.inplasa.model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaquinaEdit implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotNull(message = "el nombre de la máquina no puede ser nulo.")
    private String nombre;

    @NotNull(message = "la sección es obligatoria.")
    private Long seccionId;
}