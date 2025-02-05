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
        // Obtener saldo actual de la cuenta
        BigDecimal saldoActual = obtenerSaldoPorCuenta(movimiento.getCuenta());

        // Si el ingreso es diferente de null, se suma al saldo actual
        if (movimiento.getIngreso() != null) {
            saldoActual = saldoActual.add(movimiento.getIngreso());
        }

        // Si el egreso es diferente de null, se resta del saldo actual
        if (movimiento.getEgreso() != null) {
            saldoActual = saldoActual.subtract(movimiento.getEgreso());
        }

        // Asignar el saldo actualizado
        movimiento.setSaldo(saldoActual);

        // Guardar el movimiento
        return repository.save(movimiento);
    }

    public BigDecimal obtenerSaldoPorCuenta(String cuenta) {
        List<MovimientoContable> movimientos = repository.findByCuenta(cuenta);

        return movimientos.stream()
                .map(mov -> {
                    BigDecimal ingreso = mov.getIngreso() != null ? mov.getIngreso() : BigDecimal.ZERO;
                    BigDecimal egreso = mov.getEgreso() != null ? mov.getEgreso() : BigDecimal.ZERO;
                    return ingreso.subtract(egreso);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MovimientoContable> obtenerMovimientosPorCuenta(String cuenta) {
        return repository.findByCuenta(cuenta);
    }

    public List<String> obtenerClientes() {
        return repository.findDistinctClientes();
    }

    public List<String> obtenerCuentas() {
        return repository.findDistinctCuentas();
    }

    public List<MovimientoContable> obtenerTodosMovimientos() {
        return repository.findAll();
    }
}
