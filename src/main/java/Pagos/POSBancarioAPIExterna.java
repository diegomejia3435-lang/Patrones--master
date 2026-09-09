/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pagos;

/**
 *
 * @author jcaba
 */
public class POSBancarioAPIExterna {
    public boolean enviarCobroRedBancaria(String numeroTarjeta, String nombreTitular, double montoCobro) {
        System.out.println(" [POS Bancario Externo] Conectando con la red Visa/Mastercard...");
        System.out.println(" [POS Bancario Externo] Autorizando cobro de S/" + montoCobro + " a la tarjeta de " + nombreTitular);
        return true;
    }
}

