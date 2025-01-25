package com.contabilidadLibro.repository;

import com.contabilidadLibro.model.MovimientoContable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MovimientoContableRepository extends JpaRepository<MovimientoContable, Long> {
    List<MovimientoContable> findByCuenta(String cuenta);
}
