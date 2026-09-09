/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Productos;

import Pagos.MetodoPagoStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcaba
 */
public class Orden {
    private int idOrden;
    private String nombreCliente;
    private List<Producto> items;
    private boolean pagada;
    
    // --- NUEVO ATRIBUTO DEL PATRÓN STRATEGY ---
    private MetodoPagoStrategy metodoPago;

    public Orden(int idOrden, String nombreCliente) {
        this.idOrden = idOrden;
        this.nombreCliente = nombreCliente;
        this.items = new ArrayList<>();
        this.pagada = false;
        this.metodoPago = null; // Inicia sin método de pago asignado
    }

    public void agregarProducto(Producto producto) {
        items.add(producto);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Producto p : items) {
            total += p.getPrecio();
        }
        return total;
    }

    // --- MÉTODOS DEL PATRÓN STRATEGY ---
    public void setMetodoPago(MetodoPagoStrategy metodoPago) {
        this.metodoPago = metodoPago;
    }

    public boolean realizarCobro() {
        if (this.metodoPago == null) {
            System.err.println(" [Error de Caja] No se ha seleccionado ningún método de pago para la orden.");
            return false;
        }

        double total = calcularTotal();
        boolean pagoExitoso = this.metodoPago.procesarPago(total);

        if (pagoExitoso) {
            this.pagada = true;
            System.out.println(" [Caja] ¡Cuenta saldada y cerrada con éxito!");
        }
        return pagoExitoso;
    }
    // ------------------------------------

    public void imprimirBoleta() {
        System.out.println("\n==========================================");
        System.out.println("  CUENTA CLIENTE: " + nombreCliente);
        System.out.println("==========================================");
        for (Producto p : items) {
            System.out.println(" * " + p.generarLineaBoleta());
        }
        System.out.println("------------------------------------------");
        System.out.printf(" TOTAL A PAGAR: S/ %.2f%n", calcularTotal());
        
        // Si ya se eligió un método, lo mostramos en el recibo
        if (metodoPago != null) {
            System.out.println(" FORMA DE PAGO: " + metodoPago.getNombreMetodo());
            System.out.println(" ESTADO: " + (pagada ? "PAGADO" : "PENDIENTE"));
        }
        System.out.println("==========================================\n");
    }

    public List<Producto> getItems() { return items; }
    public boolean isPagada() { return pagada; }
    public String getNombreCliente() { return nombreCliente; }
}
