/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pagos;

/**
 *
 * @author jcaba
 */
public class YapeAdapter implements MetodoPagoStrategy {
    
    private YapeAPIExterna apiYape; // Instancia del sistema incompatible
    private String celularCliente;

    public YapeAdapter(String celularCliente) {
        this.apiYape = new YapeAPIExterna();
        this.celularCliente = celularCliente;
    }

    @Override
    public boolean procesarPago(double montoTotal) {
        System.out.println("\n [Adapter] Traduciendo orden de cobro a la API externa de Yape...");
        // Traducimos nuestro método "procesarPago" al método raro de la API externa:
        return apiYape.ejecutarTransferenciaRapida(this.celularCliente, montoTotal);
    }

    @Override
    public String getNombreMetodo() {
        return "Yape (Vía Adapter API - " + celularCliente + ")";
    }
}
