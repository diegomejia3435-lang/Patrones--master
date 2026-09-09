/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pagos;

/**
 *
 * @author jcaba
 */
public interface MetodoPagoStrategy {
    // Recibe el monto total de la Orden y procesa el pago
    // Devuelve true si el pago fue exitoso, false si fue rechazado
    boolean procesarPago(double montoTotal);
    
    // Para mostrar en la boleta con qué método pagó el cliente
    String getNombreMetodo();
}