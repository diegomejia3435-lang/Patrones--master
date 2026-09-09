/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pagos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jcaba
 */
public class MetodoPagoStrategyTest {

    @Test
    @DisplayName("PagoEfectivo debe aprobar la transacción si el dinero entregado cubre el total")
    public void testPagoEfectivoSuficiente() {
        // El cliente entrega S/ 20.00 para pagar una boleta de S/ 15.50
        PagoEfectivo pago = new PagoEfectivo(20.00);
        
        boolean resultado = pago.procesarPago(15.50);
        
        assertTrue(resultado, "El cobro debería ser aprobado y calcular el vuelto");
        assertEquals("Efectivo", pago.getNombreMetodo());
    }

    @Test
    @DisplayName("PagoEfectivo debe rechazar la transacción si el dinero entregado es menor al total")
    public void testPagoEfectivoInsuficiente() {
        // El cliente intenta pagar una boleta de S/ 15.50 con solo S/ 10.00
        PagoEfectivo pago = new PagoEfectivo(10.00);
        
        boolean resultado = pago.procesarPago(15.50);
        
        assertFalse(resultado, "El cobro debe ser rechazado por fondos insuficientes");
    }

    @Test
    @DisplayName("YapeAdapter debe traducir y procesar el cobro mediante la API externa simulada")
    public void testYapeAdapter() {
        YapeAdapter yape = new YapeAdapter("987654321");
        
        boolean resultado = yape.procesarPago(25.00);
        
        assertTrue(resultado, "El cobro por Yape debería procesarse exitosamente");
        assertTrue(yape.getNombreMetodo().contains("987654321"), "El nombre del método debe incluir el teléfono registrado");
    }

    @Test
    @DisplayName("TarjetaAdapter debe conectar con el POS externo y enmascarar la tarjeta")
    public void testTarjetaAdapter() {
        TarjetaAdapter tarjeta = new TarjetaAdapter("4557880012345678", "Juan Perez");
        
        boolean resultado = tarjeta.procesarPago(50.00);
        
        assertTrue(resultado, "El cobro con tarjeta debería ser autorizado por el POS bancario");
        assertTrue(tarjeta.getNombreMetodo().contains("5678"), "Por seguridad solo debe mostrar los últimos 4 dígitos");
    }
}