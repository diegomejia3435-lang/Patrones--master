
package Database;


import Productos.Producto;
import Productos.ProductoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogoDAO {

    // Método oficial para jalar los 30 productos de SQLite a la memoria de tu Facade
    public List<Producto> obtenerCatalogo() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, categoria, precio, requiere_preparacion, opciones_disponibles, stock FROM catalogo_tienda";
        
        // Usamos la conexión estricta que reparamos antes
        try (Connection con = ConexionBD.getConexion()) {
            if (con == null) return lista; // Escudo por si el archivo .db no está en su sitio
            
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String categoria = rs.getString("categoria");
                    double precio = rs.getDouble("precio");
                    int reqPrep = rs.getInt("requiere_preparacion");
                    String opciones = rs.getString("opciones_disponibles");
                    int stock = rs.getInt("stock");
                    
                    // Conexión con tu patrón Factory Method exacto: ProductoFactory.crear(...)
                    Producto prod = ProductoFactory.crear(id, nombre, categoria, precio, reqPrep, opciones, stock);
                    lista.add(prod);
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Error crítico al mapear catalogo_tienda: " + e.getMessage());
        }
        return lista;
    }
}
