/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pagos;

/**
 *
 * @author jcaba
 */
public class YapeAPIExterna {
    public boolean ejecutarTransferenciaRapida(String telefonoOrigen, double montoDolares) {
        System.out.println(" [Servidor Yape Externo] Conectando con API bancaria...");
        System.out.println(" [Servidor Yape Externo] Transferencia de S/" + montoDolares + " al número " + telefonoOrigen + " validada.");
        return true;
    }
}


