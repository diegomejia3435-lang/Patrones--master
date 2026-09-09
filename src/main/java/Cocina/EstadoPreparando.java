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
public class EstadoPreparando implements EstadoPreparacion {
    
    @Override
    public void siguienteEstado(ProductoPreparado producto) {
        // Al terminar el tiempo de cocina, transiciona al estado final
        producto.setEstado(new EstadoListo());
    }

    @Override
    public String getNombreEstado() {
        return "Preparando en Cocina";
    }
}
