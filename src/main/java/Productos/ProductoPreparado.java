/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Productos;

import Cocina.EstadoEnEspera;
import Cocina.EstadoPreparacion;

/**
 *
 * @author jcaba
 */
public class ProductoPreparado extends Producto {
    private String opcionElegida;
    
    // Aquí reside la magia del patrón State: una variable del tipo de la interfaz
    private EstadoPreparacion estado;

    // Constructor actualizado para mapear los 7 campos relacionales de SQLite
    public ProductoPreparado(int id, String nombre, String categoria, double precio, int requierePreparacion, String opcionesDisponibles, int stock) {
        // Pasamos todos los parámetros obligatorios a la clase padre (Producto)
        super(id, nombre, categoria, precio, requierePreparacion, opcionesDisponibles, stock);
        
        //corercion para test de (por defecto)
        this.opcionElegida = opcionesDisponibles;
        
        // Todo producto recién pedido nace estrictamente en estado "En Espera"
        this.estado = new EstadoEnEspera(); 
    }

    // Método clave: permite que un estado viejo se reemplace a sí mismo por uno nuevo
    public void setEstado(EstadoPreparacion estado) {
        this.estado = estado;
    }

    public String getOpcionElegida() { 
        return opcionElegida; 
    }
    
    // Setter adicional útil para cuando el cliente personalice su hamburguesa/pizza
    public void setOpcionElegida(String opcionElegida) {
        this.opcionElegida = opcionElegida;
    }
    
    public EstadoPreparacion getEstado() {
        return estado;
    }

    // El producto no decide cómo avanzar; le delega esa responsabilidad a su estado actual
    public void avanzarPreparacion() {
        this.estado.siguienteEstado(this);
    }

    @Override
    public String generarLineaBoleta() {
        // Consultamos el nombre del estado dinámicamente
        return String.format("[Cocina] %s (Opción: %s) - Estado: %s - S/ %.2f", 
                             nombre, opcionElegida, estado.getNombreEstado(), precio);
    }
}
