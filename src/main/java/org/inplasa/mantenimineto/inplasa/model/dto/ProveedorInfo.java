package org.inplasa.mantenimineto.inplasa.model.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ProveedorInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotNull(message = "el nombre del proveedor no puede ser nulo.")
    private String nombre;

    private String descripcion;
}
