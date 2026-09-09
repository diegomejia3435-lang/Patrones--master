package Vistas;

import Cocina.CocinaService;
import Database.CatalogoDAO;
import Database.OrdenDAO;
import Pagos.MetodoPagoStrategy;
import Productos.Orden;
import Productos.Producto;
import Productos.ProductoFactory;
import Productos.ProductoPreparado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcaba
 */
public class TiendaFacade {
    
    // La fachada mantiene el control de los subsistemas internos
    private Orden ordenActual;
    private CocinaService cocinaService;
    private List<Producto> catalogoMemoria;
    
    // Subsistemas de persistencia de Base de Datos
    private CatalogoDAO catalogoDAO;
    private OrdenDAO ordenDAO;

    public TiendaFacade() {
        this.cocinaService = new CocinaService();
        this.catalogoDAO = new CatalogoDAO();
        this.ordenDAO = new OrdenDAO();
        this.ordenActual = null;
        
        // Jalar los 30 productos desde la BD de SQLite al arrancar el programa
        this.catalogoMemoria = catalogoDAO.obtenerCatalogo();
        System.out.println(" [Facade] Catalogo cargado con " + catalogoMemoria.size() + " productos desde SQLite.");
    }

    // --- 1. GESTIÓN DE CLIENTE Y SESIÓN ---
    public void nuevaSesionCliente(int idOrden, String nombreCliente) {
        this.ordenActual = new Orden(idOrden, nombreCliente);
        System.out.println(" [Facade] Sesion iniciada para: " + nombreCliente);
    }

    public Orden getOrdenActual() {
        return ordenActual;
    }

    // --- 2. GESTIÓN DEL CATÁLOGO (Puente con Factory y SQLite) ---
    public void cargarProductoAlCatalogo(int id, String nombre, String cat, double precio, int reqPrep, String opcion, int stock) {
        // CORREGIDO: Usamos tu metodo original .crear(...)
        Producto prod = ProductoFactory.crear(id, nombre, cat, precio, reqPrep, opcion, stock);
        catalogoMemoria.add(prod);
    }

    public List<Producto> getCatalogo() {
        return catalogoMemoria;
    }

    // --- 3. GESTIÓN DE COMPRAS ---
    public void agregarProductoAOrden(Producto producto) {
        if (ordenActual == null) {
            System.err.println(" [Error] Primero debes iniciar sesion con un cliente.");
            return;
        }
        ordenActual.agregarProducto(producto);
    }

    // --- 4. GESTIÓN DE COCINA (Puente con State) ---
    public void enviarPedidosACocina() {
        if (ordenActual != null) {
            cocinaService.recibirPedido(ordenActual);
        }
    }

    public void avanzarEstadoCocina(int indiceProducto) {
        cocinaService.procesarSiguientePaso(indiceProducto);
    }

    public List<ProductoPreparado> getMonitoreoCocina() {
        return cocinaService.getColaDePedidos();
    }

    // --- 5. GESTIÓN DE PAGO (Puente con Strategy) ---
    public boolean pagarOrden(MetodoPagoStrategy estrategiaPago) {
        if (ordenActual == null) return false;
        
        ordenActual.setMetodoPago(estrategiaPago);
        boolean exito = ordenActual.realizarCobro();
        
        if (exito) {
            System.out.println(" [Facade] Transaccion terminada con exito.");
            
            // CORREGIDO: Mandamos a guardar la orden cobrada en la Base de Datos
            System.out.println(" [Facade] Persistiendo orden en SQLite...");
            boolean guardado = ordenDAO.registrarOrden(ordenActual);
            
            if (guardado) {
                System.out.println(" [Facade] Historial guardado de forma relacional.");
            } else {
                System.out.println(" [Alerta] El cobro paso, pero la base de datos rechazo el guardado.");
            }
        }
        return exito;
    }
}
