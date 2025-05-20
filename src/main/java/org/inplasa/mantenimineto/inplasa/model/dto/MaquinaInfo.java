package org.inplasa.mantenimineto.inplasa.model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
  
public class MaquinaInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private Long seccionId;
    private String seccionNombre;
}
