package Productos;

/**
 *
 * @author jcaba
 */
public abstract class Producto {
    protected int id;
    protected String nombre;
    protected String categoria;
    protected double precio;
    // --- NUEVOS ATRIBUTOS PARA CALZAR CON LA BASE DE DATOS RECONOCIDA ---
    protected int requierePreparacion; // 0 = No, 1 = Sí
    protected String opcionesDisponibles;
    protected int stock;

    public Producto(int id, String nombre, String categoria, double precio, int requierePreparacion, String opcionesDisponibles, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.requierePreparacion = requierePreparacion;
        this.opcionesDisponibles = opcionesDisponibles;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    // --- GETTERS COMPLEMENTARIOS PARA PERSISTENCIA ---
    public int getRequierePreparacion() { return requierePreparacion; }
    public String getOpcionesDisponibles() { return opcionesDisponibles; }
    public int getStock() { return stock; }

    // Método abstracto: obliga a las subclases a explicar cómo se muestran en la boleta
    public abstract String generarLineaBoleta();
}
