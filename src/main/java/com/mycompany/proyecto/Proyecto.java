package com.mycompany.proyecto;


import Database.ConexionBD;
import Vistas.TiendaFacade;
import Vistas.VistaInicio;
import java.sql.Connection;
import javax.swing.SwingUtilities;

/**
 * Clase principal de inicio del Sistema de Gestión de Delivery
 * @version 1.0.0
 * @author jcaba
 */
public class Proyecto {

    public static void main(String[] args) {
        System.out.println("=== ARRANCANDO SISTEMA DE DELIVERY (MODO INTERFAZ GRAFICA) ===");
        System.out.println("[INFO] Inicio de sesion: " + java.time.LocalDateTime.now());
        
        // 1. Escudo obligatorio de base de datos relacional externa y manual
        Connection con = ConexionBD.getConexion();
        if (con == null) {
            System.out.println("[SISTEMA BLOQUEADO] No se puede arrancar la interfaz grafica sin la base de datos.");
            return;
        }

        // 2. Inicializamos el backend centralizado y sincronizado (Tu Facade)
        TiendaFacade fachada = new TiendaFacade();

        // 3. Hilo seguro de Swing para renderizar la Pantalla de Bienvenida (Vista 1)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VistaInicio pantallaInicio = new VistaInicio(fachada);
                pantallaInicio.setVisible(true); 
            }
        });
    }
}
