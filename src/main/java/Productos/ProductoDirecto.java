/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Productos;

/**
 *
 * @author jcaba
 */
public class ProductoDirecto extends Producto {

    public ProductoDirecto(int id, String nombre, String categoria, double precio, int requierePreparacion, String opcionesDisponibles, int stock) {
        super(id, nombre, categoria, precio, requierePreparacion, opcionesDisponibles, stock);
    }
    
    
    public void reducirStock(int cantidad) {
        if (this.stock >= cantidad) {
            this.stock -= cantidad;
        } else {
            throw new IllegalStateException("Stock insuficiente para: " + nombre);
        }
    }

    @Override
    public String generarLineaBoleta() {
        // Mantenemos tu formato exacto de boleta usando el stock heredado
        return String.format("[Estante] %s - S/ %.2f (Stock restante: %d)", nombre, precio, stock);
    } 
}
