/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Productos;

/**
 *
 * @author jcaba
 */
public class ProductoFactory {

    /**
     * Instancia el tipo correcto de Producto según las banderas de la base de datos.
     *
     * @param id ID único en SQLite.
     * @param nombre Nombre del producto.
     * @param categoria Categoría (Bebidas, Comida Rápida, etc.).
     * @param precio Precio unitario.
     * @param requierePreparacion 0 para directo, 1 para preparado.
     * @param opcionesDisponibles Variantes disponibles en la BD (ej. "Con queso, Sin cremas").
     * @param stock Stock disponible en tienda.
     */
    public static Producto crear(int id, String nombre, String categoria, double precio, 
                                 int requierePreparacion, String opcionesDisponibles, int stock) {
        
        if (requierePreparacion == 1) {
            // Validacion de seguridad por si olvidan pasar las opciones desde la BD
            String variantes = (opcionesDisponibles != null && !opcionesDisponibles.isEmpty()) ? opcionesDisponibles : "Estándar";
            
            // CORREGIDO: Invocamos al nuevo constructor de ProductoPreparado pasándole los 7 parámetros relacionales
            return new ProductoPreparado(id, nombre, categoria, precio, requierePreparacion, variantes, stock);
        } else {
            // CORREGIDO: Invocamos al nuevo constructor de ProductoDirecto pasándole los 7 parámetros relacionales
            return new ProductoDirecto(id, nombre, categoria, precio, requierePreparacion, opcionesDisponibles, stock);
        }
    }
}
