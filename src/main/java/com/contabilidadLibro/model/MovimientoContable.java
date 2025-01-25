package com.contabilidadLibro.model;


import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimientos_contables")
public class MovimientoContable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "fechaCompra")
    private LocalDate fechaCompra;

    @Column(name = "mesAno")
    private String mesAno;

    @Column(name = "cuenta")
    private String cuenta;

    @Column(name = "clienteProveedor")
    private String clienteProveedor;

    @Column(name = "numFactura")
    private String numeroFactura;

    @Column(name = "numID")
    private String numeroCI;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fechaPago")
    private LocalDate fechaPago;

    @Column(name = "ingreso")
    private BigDecimal ingreso;

    @Column(name = "egreso")
    private BigDecimal egreso;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "retencion")
    private BigDecimal retencion;

    //GETTERS Y SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getClienteProveedor() {
        return clienteProveedor;
    }

    public void setClienteProveedor(String clienteProveedor) {
        this.clienteProveedor = clienteProveedor;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNumeroCI() {
        return numeroCI;
    }

    public void setNumeroCI(String numeroCI) {
        this.numeroCI = numeroCI;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public BigDecimal getEgreso() {
        return egreso;
    }

    public void setEgreso(BigDecimal egreso) {
        this.egreso = egreso;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }
}
