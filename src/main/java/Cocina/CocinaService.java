/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cocina;

import Productos.Orden;
import Productos.Producto;
import Productos.ProductoPreparado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcaba
 */
public class CocinaService {
    private List<ProductoPreparado> colaDePedidos;

    public CocinaService() {
        this.colaDePedidos = new ArrayList<>();
    }

    // Filtra la boleta de compra y extrae únicamente los productos que requieren cocinado
    public void recibirPedido(Orden orden) {
        System.out.println("\n--- [Cocina] Recibiendo nueva orden ---");
        for (Producto item : orden.getItems()) {
            if (item instanceof ProductoPreparado) {
                ProductoPreparado preparado = (ProductoPreparado) item;
                colaDePedidos.add(preparado);
                System.out.println(" + Ingresó a cocina: " + preparado.getNombre() + " (" + preparado.getOpcionElegida() + ")");
            }
        }
    }

    // Avanza el estado de un producto individual por su índice en la cola
    public void procesarSiguientePaso(int indiceProducto) {
        if (indiceProducto >= 0 && indiceProducto < colaDePedidos.size()) {
            ProductoPreparado prod = colaDePedidos.get(indiceProducto);
            
            System.out.print(" -> Avanzando " + prod.getNombre() + " de [" + prod.getEstado().getNombreEstado() + "] a ");
            prod.avanzarPreparacion();
            System.out.println("[" + prod.getEstado().getNombreEstado() + "]");
        }
    }

    // Muestra el tablero completo para diagnósticos o para poblar un JTable en el futuro
    public void mostrarTableroCocina() {
        System.out.println("\n=== TABLERO DEL ÁREA DE PREPARACIÓN ===");
        if (colaDePedidos.isEmpty()) {
            System.out.println(" (No hay pedidos pendientes en cocina)");
        } else {
            for (int i = 0; i < colaDePedidos.size(); i++) {
                ProductoPreparado p = colaDePedidos.get(i);
                System.out.printf(" [%d] %s - Variedad: %s | Estado actual: %s%n", 
                                  i, p.getNombre(), p.getOpcionElegida(), p.getEstado().getNombreEstado());
            }
        }
        System.out.println("=======================================\n");
    }

    public List<ProductoPreparado> getColaDePedidos() {
        return colaDePedidos;
    }
}
