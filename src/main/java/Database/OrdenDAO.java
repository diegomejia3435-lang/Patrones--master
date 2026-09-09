
package Database;


import Productos.Orden;
import Productos.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrdenDAO {

    // Guarda la orden y sus detalles en una sola transacción segura
    public boolean registrarOrden(Orden orden) {
        String sqlOrden = "INSERT INTO ordenes (total, metodo_pago, estado_actual) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario, opcion_seleccionada) VALUES (?, ?, ?, ?, ?)";
        
        
        Connection con = ConexionBD.getConexion();
        if (con == null) return false;

        try {
            con.setAutoCommit(false);

            // 1. Insertar Cabecera de la Órden
            int idOrdenGenerado = -1;
            try (PreparedStatement ps = con.prepareStatement(sqlOrden, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDouble(1, orden.calcularTotal()); // Asegúrate de tener este método en tu clase Orden
                ps.setString(2, "Procesado"); // O el nombre de tu estrategia
                ps.setString(3, "En Espera");
                ps.executeUpdate();

                // Recuperamos el ID autoincremental que SQLite le asignó a la órden
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idOrdenGenerado = rs.getInt(1);
                    }
                }
            }

    
              if (idOrdenGenerado != -1) {
                try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                    // CORREGIDO: Usamos orden.getItems() que es tu metodo real en Orden.java
                    for (Producto prod : orden.getItems()) { 
                        psDetalle.setInt(1, idOrdenGenerado);
                        psDetalle.setInt(2, prod.getId());
                        psDetalle.setInt(3, 1); // Cantidad por defecto
                        psDetalle.setDouble(4, prod.getPrecio());
                        
                        // REVISAR AQUÍ: Si te sigue saliendo rojo getOpcion(), borra esa palabra,
                        // pon "prod." y presiona Ctrl + Espacio para elegir tu getter de opciones.
                        psDetalle.setString(5, prod.getOpcionesDisponibles());
                        
                        psDetalle.addBatch();
                    }
                    psDetalle.executeBatch(); // Inserta todos los productos en bloque
                }
            }

            con.commit(); // Si todo salió bien, guardamos en disco duro
            return true;

        } catch (SQLException e) {
            System.out.println("[ERROR] No se pudo guardar la orden en la BD: " + e.getMessage());
            try { con.rollback(); } catch (SQLException ex) { } // Revierte cambios si falla
            return false;
        }
    }
}
