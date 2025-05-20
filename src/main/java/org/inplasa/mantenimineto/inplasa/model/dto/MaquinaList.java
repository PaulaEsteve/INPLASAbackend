package org.inplasa.mantenimineto.inplasa.model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaquinaList implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String seccionNombre;
}
