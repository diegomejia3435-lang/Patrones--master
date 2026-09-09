/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto;
import Productos.ProductoFactory;
import Productos.Producto;
import Productos.ProductoDirecto;
import Productos.ProductoPreparado;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
/**
 *
 * @author jcaba
 */
public class ProductoFactoryTest {

    @Test
    @DisplayName("Debe crear un ProductoDirecto con stock cuando requierePreparacion es 0")
    public void testCrearProductoDirecto() {
        // 1. PREPARACIÓN Y EJECUCIÓN: Llamamos a la fábrica como si viniera de SQLite
        Producto prod = ProductoFactory.crear(101, "Gaseosa Kola 500ml", "Bebidas", 3.50, 0, null, 50);

        // 2. VERIFICACIÓN (Asserts): Comprobamos que no sea nulo y sea del tipo correcto
        assertNotNull(prod, "El producto no debería ser nulo");
        assertTrue(prod instanceof ProductoDirecto, "Debería haber instanciado un ProductoDirecto");
        assertEquals("Gaseosa Kola 500ml", prod.getNombre());
        assertEquals(3.50, prod.getPrecio());
        
        // Verificamos que el stock físico se haya guardado bien
        ProductoDirecto directo = (ProductoDirecto) prod;
        assertEquals(50, directo.getStock(), "El stock inicial debe ser 50");
    }

    @Test
    @DisplayName("Debe crear un ProductoPreparado con opción y en estado 'En Espera' cuando es 1")
    public void testCrearProductoPreparado() {
        // 1. PREPARACIÓN Y EJECUCIÓN: Simulamos pedir una hamburguesa de Pollo
        Producto prod = ProductoFactory.crear(201, "Hamburguesa Clásica", "Comida Rápida", 8.50, 1, "Pollo", -1);

        // 2. VERIFICACIÓN: Comprobamos que sea un producto que va a cocina
        assertNotNull(prod);
        assertTrue(prod instanceof ProductoPreparado, "Debería haber instanciado un ProductoPreparado");
        
        ProductoPreparado preparado = (ProductoPreparado) prod;
        assertEquals("Pollo", preparado.getOpcionElegida(), "La opción elegida debería ser 'Pollo'");
        assertEquals("En Espera", preparado.getEstado().getNombreEstado(), "Todo pedido nuevo debe nacer en estado 'En Espera'");
    }
}