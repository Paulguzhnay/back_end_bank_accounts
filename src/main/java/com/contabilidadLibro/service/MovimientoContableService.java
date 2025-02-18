package com.contabilidadLibro.service;

import com.contabilidadLibro.model.MovimientoContable;
import com.contabilidadLibro.repository.MovimientoContableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    //ACTUALIZAR CUENTAS
    public MovimientoContable actualizarMovimiento(Long id, MovimientoContable movimientoActualizado) {
        // Buscar si el movimiento existe
        MovimientoContable movimientoExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        // Actualizar solo los campos modificados
        movimientoExistente.setFecha(movimientoActualizado.getFecha());
        movimientoExistente.setFechaCompra(movimientoActualizado.getFechaCompra());
        movimientoExistente.setCuenta(movimientoActualizado.getCuenta());
        movimientoExistente.setClienteProveedor(movimientoActualizado.getClienteProveedor());
        movimientoExistente.setNumeroFactura(movimientoActualizado.getNumeroFactura());
        movimientoExistente.setNumeroCI(movimientoActualizado.getNumeroCI());
        movimientoExistente.setDescripcion(movimientoActualizado.getDescripcion());
        movimientoExistente.setFechaPago(movimientoActualizado.getFechaPago());

        // Si el ingreso o egreso cambia, recalculamos el saldo
        if (movimientoActualizado.getIngreso() != null || movimientoActualizado.getEgreso() != null) {
            BigDecimal saldoActual = obtenerSaldoPorCuenta(movimientoActualizado.getCuenta());

            if (movimientoActualizado.getIngreso() != null) {
                saldoActual = saldoActual.add(movimientoActualizado.getIngreso());
            }
            if (movimientoActualizado.getEgreso() != null) {
                saldoActual = saldoActual.subtract(movimientoActualizado.getEgreso());
            }

            movimientoExistente.setIngreso(movimientoActualizado.getIngreso());
            movimientoExistente.setEgreso(movimientoActualizado.getEgreso());
            movimientoExistente.setSaldo(saldoActual);
        }

        return repository.save(movimientoExistente);
    }

    //TRANSACCIONES
    @Transactional
    public void transferirEntreCuentas(String cuentaOrigen, String cuentaDestino, BigDecimal monto) {
        // Obtener saldo actual de la cuenta origen
        BigDecimal saldoOrigen = obtenerSaldoPorCuenta(cuentaOrigen);

        // Validar si la cuenta origen tiene saldo suficiente
        if (saldoOrigen.compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente en la cuenta origen");
        }

        // Crear movimiento de egreso en la cuenta origen
        MovimientoContable movimientoEgreso = new MovimientoContable();
        movimientoEgreso.setCuenta(cuentaOrigen);
        movimientoEgreso.setEgreso(monto);
        movimientoEgreso.setSaldo(saldoOrigen.subtract(monto));
        repository.save(movimientoEgreso);

        // Obtener saldo actual de la cuenta destino
        BigDecimal saldoDestino = obtenerSaldoPorCuenta(cuentaDestino);

        // Crear movimiento de ingreso en la cuenta destino
        MovimientoContable movimientoIngreso = new MovimientoContable();
        movimientoIngreso.setCuenta(cuentaDestino);
        movimientoIngreso.setIngreso(monto);
        movimientoIngreso.setSaldo(saldoDestino.add(monto));
        repository.save(movimientoIngreso);
    }

    //Filtros
    public List<MovimientoContable> filtrarMovimientos(String clienteProveedor, String numeroFactura, String numeroCI) {
        return repository.filtrarMovimientos(clienteProveedor, numeroFactura, numeroCI);
    }
}
