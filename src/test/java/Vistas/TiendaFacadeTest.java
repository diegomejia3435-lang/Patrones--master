/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Pagos.PagoEfectivo;
import Productos.Producto;
import Productos.ProductoFactory;
import Productos.ProductoPreparado;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jcaba
 */
public class TiendaFacadeTest {

    private TiendaFacade facade;

    @BeforeEach
    public void setUp() {
        // Antes de cada test, instanciamos una fachada completamente limpia
        facade = new TiendaFacade();
    }

    @Test
    @DisplayName("Debe abrir una nueva sesión de cliente e inicializar un carrito vacío")
    public void testNuevaSesionCliente() {
        facade.nuevaSesionCliente(1, "Ana Morales");
        
        assertNotNull(facade.getOrdenActual(), "La orden no debe ser nula tras iniciar sesión");
        assertTrue(facade.getOrdenActual().getItems().isEmpty(), "El carrito recién creado debe iniciar sin productos");
    }

    @Test
    @DisplayName("Debe coordinar el flujo de compra: sumar al carrito y procesar el pago final")
    public void testFlujoDeCompraYPago() {
        facade.nuevaSesionCliente(2, "Carlos Perez");

        // Usamos los 7 parámetros de tu base de datos para crear una gaseosa y una empanada
        Producto gaseosa = ProductoFactory.crear(101, "Gaseosa Kola", "Bebidas", 3.50, 0, null, 50);
        Producto empanada = ProductoFactory.crear(201, "Empanada", "Comida Rápida", 4.50, 1, "Carne,Pollo", -1);

        // Agregamos mediante la fachada
        facade.agregarProductoAOrden(gaseosa);
        facade.agregarProductoAOrden(empanada);

        assertEquals(2, facade.getOrdenActual().getItems().size(), "El carrito debe registrar exactamente 2 productos");
        assertEquals(8.00, facade.getOrdenActual().calcularTotal(), 0.001, "El total calculado por la fachada debe ser S/ 8.00");

        // Ejecutamos el cobro con un billete de S/ 10.00 (Suficiente para S/ 8.00)
        boolean cobroExitoso = facade.pagarOrden(new PagoEfectivo(10.00));
        
        assertTrue(cobroExitoso, "El cobro procesado por la fachada debe retornar true");
        assertTrue(facade.getOrdenActual().isPagada(), "La orden debe quedar marcada internamente como pagada");
    }

    @Test
    @DisplayName("Debe enviar productos a la cocina y permitir cambiar sus estados dinámicamente")
    public void testFlujoDeCocinaDesdeFacade() {
        facade.nuevaSesionCliente(3, "Luisa Gomez");
        
        // Creamos un pastel que requiere preparación (requierePreparacion = 1)
        Producto pastel = ProductoFactory.crear(301, "Pastel Dulce", "Dulces", 6.00, 1, "Chocolate,Vainilla", -1);
        facade.agregarProductoAOrden(pastel);

        // La fachada envía la orden al CocinaService
        facade.enviarPedidosACocina();
        
        assertFalse(facade.getMonitoreoCocina().isEmpty(), "La cola de monitoreo de cocina no debe estar vacía");
        
        ProductoPreparado enCocina = facade.getMonitoreoCocina().get(0);
        assertEquals("En Espera", enCocina.getEstado().getNombreEstado(), "El pedido debe ingresar a cocina en estado 'En Espera'");

        // Simulamos un pulso del temporizador (avanzar al siguiente estado)
        facade.avanzarEstadoCocina(0);
        assertEquals("Preparando en Cocina", enCocina.getEstado().getNombreEstado(), "La fachada debe transicionar el pedido a 'Preparando en Cocina'");
    }
}
