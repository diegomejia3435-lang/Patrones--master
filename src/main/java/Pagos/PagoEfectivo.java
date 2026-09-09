/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pagos;

/**
 *
 * @author jcaba
 */
public class PagoEfectivo implements MetodoPagoStrategy {
    private double montoEntregadoPorCliente;

    public PagoEfectivo(double montoEntregadoPorCliente) {
        this.montoEntregadoPorCliente = montoEntregadoPorCliente;
    }

    @Override
    public boolean procesarPago(double montoTotal) {
        System.out.println("\n [Caja] Procesando pago en Efectivo...");
        if (montoEntregadoPorCliente >= montoTotal) {
            double vuelto = montoEntregadoPorCliente - montoTotal;
            System.out.printf("  -> Pago recibido: S/ %.2f | Total: S/ %.2f | Vuelto: S/ %.2f%n", 
                              montoEntregadoPorCliente, montoTotal, vuelto);
            return true;
        } else {
            System.out.printf("  [Rechazado] Dinero insuficiente. Faltan S/ %.2f%n", 
                              (montoTotal - montoEntregadoPorCliente));
            return false;
        }
    }

    @Override
    public String getNombreMetodo() {
        return "Efectivo";
    }
}
