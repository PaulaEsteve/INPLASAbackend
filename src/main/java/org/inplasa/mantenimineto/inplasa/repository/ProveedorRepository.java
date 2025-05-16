package org.inplasa.mantenimineto.inplasa.repository;

import org.inplasa.mantenimineto.inplasa.model.db.ProveedorDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProveedorRepository extends JpaRepository<ProveedorDb, Long> , JpaSpecificationExecutor<ProveedorDb> {
        
}
