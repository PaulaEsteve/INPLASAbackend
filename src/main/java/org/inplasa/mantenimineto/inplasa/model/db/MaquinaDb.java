package org.inplasa.mantenimineto.inplasa.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "maquina")
public class MaquinaDb implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "seccion_id", nullable = false)
    private SeccionDb seccion;

    /**
     * Exponer la FK como propiedad para MapStruct y Specification.
     * No se persistirá por duplicado en la tabla.
     */
    @Transient
    public Long getSeccionId() {
        return seccion != null ? seccion.getId() : null;
    }

    public void setSeccionId(Long seccionId) {
        if (this.seccion == null) {
            this.seccion = new SeccionDb();
        }
        this.seccion.setId(seccionId);
    }

}