/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Cocina;

import Productos.ProductoPreparado;

/**
 *
 * @author jcaba
 */
public interface EstadoPreparacion {
    // Ordena al estado actual que haga transitar al producto hacia su siguiente fase
    void siguienteEstado(ProductoPreparado producto);
    
    // Devuelve el nombre de la etapa actual (para la consola y los futuros JFrames)
    String getNombreEstado();
}
