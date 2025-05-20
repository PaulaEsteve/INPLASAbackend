package org.inplasa.mantenimineto.inplasa.repository;

import org.inplasa.mantenimineto.inplasa.model.db.MaquinaDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MaquinaRepository extends JpaRepository<MaquinaDb, Long>, JpaSpecificationExecutor<MaquinaDb> {
}