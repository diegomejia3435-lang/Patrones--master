/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cocina;

import Productos.ProductoPreparado;

/**
 *
 * @author jcaba
 */
public class EstadoListo implements EstadoPreparacion {
    
    @Override
    public void siguienteEstado(ProductoPreparado producto) {
        // Si intentan avanzar cuando ya está listo, evitamos errores pero no cambiamos de estado
        System.out.println(" [Alerta] El producto ya fue preparado y empaquetado. Listo para entregar.");
    }

    @Override
    public String getNombreEstado() {
        return "¡Listo y Empaquetado!";
    }
}
