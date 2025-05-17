package org.inplasa.mantenimineto.inplasa.repository;

import org.inplasa.mantenimineto.inplasa.model.db.SeccionDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeccionRepository extends JpaRepository<SeccionDb, Long> , JpaSpecificationExecutor<SeccionDb>{
    
}
