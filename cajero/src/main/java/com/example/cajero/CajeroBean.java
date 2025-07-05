package com.example.cajero;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("cajeroBean")
@SessionScoped
public class CajeroBean implements Serializable{
    private List<Cliente> clientes;
    private String numeroCuenta;
    private String pinIngresado;
    private double monto;
    private String mensaje;
    private String mensajeStyleClass;

    @PostConstruct
    public void init() {
        clientes = new ArrayList<>();
        clientes.add(new Cliente("1001", 500.0, "1234"));
        clientes.add(new Cliente("1002", 800.0, "5678"));
        clientes.add(new Cliente("1003", 300.0, "0000"));
        clientes.add(new Cliente("1004", 950.5, "2468"));
        clientes.add(new Cliente("1005", 120.75, "1357"));
        resetearValores();
    }
    public void resetearValores() {
        numeroCuenta = "";
        pinIngresado = "";
        monto=0;
        mensaje = "";
        mensajeStyleClass="";
    }

    public void realizarDeposito() {
        Cliente cliente = buscarCliente(numeroCuenta);
        if (cliente == null) {
            mensaje = "Cuenta no encontrada.";
            this.mensajeStyleClass = "error";
            return;
        }
        if (!cliente.getPin().equals(pinIngresado)) {
            mensaje = "PIN incorrecto.";
            this.mensajeStyleClass = "error";
            return;
        }
        if (monto <= 0) {
            mensaje = "El monto a depositar debe ser mayor que cero.";
            this.mensajeStyleClass = "advertencia";
            return;
        }

        cliente.setSaldo(cliente.getSaldo() + monto);
        mensaje = "Deposito exitoso. Nuevo saldo: L. " + cliente.getSaldo();
        this.mensajeStyleClass = "exito";
    }

    public void realizarRetiro() {
        Cliente cliente = buscarCliente(numeroCuenta);
        if (cliente == null) {
            mensaje = "Cuenta no encontrada.";
            this.mensajeStyleClass = "advertencia";
            return;
        }
        if (!cliente.getPin().equals(pinIngresado)) {
            mensaje = "PIN incorrecto.";
            this.mensajeStyleClass = "error";
            return;
        }
        if (monto <= 0) {
            mensaje = "El monto a retirar debe ser mayor que cero.";
            this.mensajeStyleClass = "advertencia";
            return;
        }
        if (cliente.getSaldo() < monto) {
            mensaje = "Saldo insuficiente para realizar el retiro.";
            this.mensajeStyleClass = "advertencia";
            return;
        }
        boolean esDivisionEntera = (monto % 100) == 0;
        if (esDivisionEntera==false) {
            mensaje = "Error: La cantidad debe ser un múltiplo de 100";
            mensajeStyleClass = "error";
            return;
        }

        cliente.setSaldo(cliente.getSaldo() - monto);
        mensaje = "Retiro exitoso. Nuevo saldo: L. " + cliente.getSaldo();
        this.mensajeStyleClass = "exito";
    }

    private Cliente buscarCliente(String cuenta) {
        for (Cliente c : clientes) {
            if (c.getNumeroCuenta().equals(cuenta)) {
                return c;
            }
        }
        return null;
    }

    // Getters y setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getPinIngresado() {
        return pinIngresado;
    }

    public void setPinIngresado(String pinIngresado) {
        this.pinIngresado = pinIngresado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeStyleClass() {
        return mensajeStyleClass;
    }

    public void setMensajeStyleClass(String mensajeStyleClass) {
        this.mensajeStyleClass = mensajeStyleClass;
    }
}
// FINAL
