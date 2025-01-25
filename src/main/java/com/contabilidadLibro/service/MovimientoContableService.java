package com.contabilidadLibro.service;

import com.contabilidadLibro.model.MovimientoContable;
import com.contabilidadLibro.repository.MovimientoContableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimientoContableService {

    @Autowired
    private MovimientoContableRepository repository;

    public MovimientoContable crearMovimiento(MovimientoContable movimiento) {
        BigDecimal saldoActual = obtenerSaldoPorCuenta(movimiento.getCuenta());
        movimiento.setSaldo(saldoActual.add(
                        movimiento.getIngreso() != null ? movimiento.getIngreso() : BigDecimal.ZERO)
                .subtract(movimiento.getEgreso() != null ? movimiento.getEgreso() : BigDecimal.ZERO)
        );
        return repository.save(movimiento);
    }

    public BigDecimal obtenerSaldoPorCuenta(String cuenta) {
        List<MovimientoContable> movimientos = repository.findByCuenta(cuenta);
        return movimientos.stream()
                .map(mov -> mov.getIngreso().subtract(mov.getEgreso()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MovimientoContable> obtenerMovimientosPorCuenta(String cuenta) {
        return repository.findByCuenta(cuenta);
    }

    public List<MovimientoContable> obtenerTodosMovimientos() {
        return repository.findAll();
    }


}
