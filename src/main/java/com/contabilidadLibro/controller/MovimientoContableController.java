package com.contabilidadLibro.controller;

import com.contabilidadLibro.dto.TransferenciaRequest;
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

    @GetMapping("/clientes")
    public ResponseEntity<List<String>> obtenerClientes() {
        List<String> clientes = movimientoContableService.obtenerClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/cuentas")
    public ResponseEntity<List<String>> obtenerCuentas() {
        List<String> cuentas = movimientoContableService.obtenerCuentas();
        return ResponseEntity.ok(cuentas);
    }

    // Actualizacion de Cuentas
    @PutMapping("/movimiento/{id}")
    public ResponseEntity<MovimientoContable> actualizarMovimiento(
            @PathVariable Long id,
            @RequestBody MovimientoContable movimientoActualizado) {

        MovimientoContable movimiento = movimientoContableService.actualizarMovimiento(id, movimientoActualizado);
        return ResponseEntity.ok(movimiento);
    }


    //Transferencia de Cuentas
    @PostMapping("/transferencia")
    public ResponseEntity<String> transferirEntreCuentas(@RequestBody TransferenciaRequest transferenciaRequest) {
        movimientoContableService.transferirEntreCuentas(
                transferenciaRequest.getCuentaOrigen(),
                transferenciaRequest.getCuentaDestino(),
                transferenciaRequest.getMonto()
        );
        return ResponseEntity.ok("Transferencia realizada con éxito.");
    }

    //Filtro
    @GetMapping("/movimientos/filtrar")
    public ResponseEntity<List<MovimientoContable>> filtrarMovimientos(
            @RequestParam(required = false) String clienteProveedor,
            @RequestParam(required = false) String numeroFactura,
            @RequestParam(required = false) String numeroCI) {

        List<MovimientoContable> movimientos = movimientoContableService.filtrarMovimientos(clienteProveedor, numeroFactura, numeroCI);
        return ResponseEntity.ok(movimientos);
    }
}
