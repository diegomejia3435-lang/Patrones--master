/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pagos;

/**
 *
 * @author jcaba
 */

public class TarjetaAdapter implements MetodoPagoStrategy {
    
    private POSBancarioAPIExterna posExterno; // Instancia de la red bancaria
    private String numeroTarjeta;
    private String titular;

    public TarjetaAdapter(String numeroTarjeta, String titular) {
        this.posExterno = new POSBancarioAPIExterna();
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
    }

    @Override
    public boolean procesarPago(double montoTotal) {
        System.out.println("\n [Adapter] Traduciendo orden de cobro al POS físico de la tienda...");
        // Traducimos nuestro procesarPago al método de la API bancaria:
        return posExterno.enviarCobroRedBancaria(this.numeroTarjeta, this.titular, montoTotal);
    }

    @Override
    public String getNombreMetodo() {
        // Mostramos solo los últimos 4 dígitos por seguridad
        String ultimos4 = numeroTarjeta.length() >= 4 ? numeroTarjeta.substring(numeroTarjeta.length() - 4) : "0000";
        return "Tarjeta POS (Vía Adapter API - ****-" + ultimos4 + ")";
    }
}
