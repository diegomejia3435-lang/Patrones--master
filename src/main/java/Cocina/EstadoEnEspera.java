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
public class EstadoEnEspera implements EstadoPreparacion {
    
    @Override
    public void siguienteEstado(ProductoPreparado producto) {
        // Al avanzar, el producto asume un nuevo comportamiento: Preparando
        producto.setEstado(new EstadoPreparando());
    }

    @Override
    public String getNombreEstado() {
        return "En Espera";
    }
}
