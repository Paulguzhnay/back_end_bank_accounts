package com.contabilidadLibro.repository;

import com.contabilidadLibro.model.MovimientoContable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MovimientoContableRepository extends JpaRepository<MovimientoContable, Long> {
    List<MovimientoContable> findByCuenta(String cuenta);

    @Query("SELECT DISTINCT m.clienteProveedor FROM MovimientoContable m WHERE m.clienteProveedor IS NOT NULL")
    List<String> findDistinctClientes();

    @Query("SELECT DISTINCT m.cuenta FROM MovimientoContable m WHERE m.cuenta IS NOT NULL")
    List<String> findDistinctCuentas();

    @Query("SELECT m FROM MovimientoContable m WHERE " +
            "(:clienteProveedor IS NULL OR m.clienteProveedor = :clienteProveedor) AND " +
            "(:numeroFactura IS NULL OR m.numeroFactura = :numeroFactura) AND " +
            "(:numeroCI IS NULL OR m.numeroCI = :numeroCI)")
    List<MovimientoContable> filtrarMovimientos(
            @Param("clienteProveedor") String clienteProveedor,
            @Param("numeroFactura") String numeroFactura,
            @Param("numeroCI") String numeroCI);

}
