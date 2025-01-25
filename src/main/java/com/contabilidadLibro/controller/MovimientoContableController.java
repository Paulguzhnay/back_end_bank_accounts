package com.contabilidadLibro.controller;

import com.contabilidadLibro.model.MovimientoContable;
import com.contabilidadLibro.service.MovimientoContableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovimientoContableController {
    @Autowired
    private MovimientoContableService movimientoContableService;

    @PostMapping("/ingreso")
    public ResponseEntity<MovimientoContable> registrarIngreso(@RequestBody MovimientoContable movimiento) {
        MovimientoContable nuevoMovimiento = movimientoContableService.crearMovimiento(movimiento);
        return ResponseEntity.ok(nuevoMovimiento);
    }

    @GetMapping("/{cuenta}/saldo")
    public ResponseEntity<BigDecimal> obtenerSaldoPorCuenta(@PathVariable String cuenta) {
        BigDecimal saldo = movimientoContableService.obtenerSaldoPorCuenta(cuenta);
        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/{cuenta}/movimientos")
    public ResponseEntity<List<MovimientoContable>> obtenerMovimientosPorCuenta(@PathVariable String cuenta) {
        List<MovimientoContable> movimientos = movimientoContableService.obtenerMovimientosPorCuenta(cuenta);
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoContable>> obtenerTodosMovimientos() {
        List<MovimientoContable> movimientos = movimientoContableService.obtenerTodosMovimientos();
        return ResponseEntity.ok(movimientos);
    }
}
