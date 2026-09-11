
package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;

public class ConexionBD {
    // LLAVE DE APERTURA DE LA CLASE

    private static Connection conexion = null;

    // CORREGIDO: Ahora la variable vive dentro de la clase y NetBeans no se quejará
    private static final String URL = "jdbc:sqlite:proyecto/delivery_db.db";
    // ... El resto de tus métodos (getConexion, etc.) continúan abajo

        public static Connection getConexion() {
        try {
            // El truco clave: Si la conexion es nula O fue cerrada en el camino, la volvemos a abrir
            if (conexion == null || conexion.isClosed()) {
                // RUTA ABSOLUTA EXACTA (La misma que ya te funciono limpio antes)
                String urlFija = "jdbc:sqlite:C:/Users/LAB-USR-AREQUIPA/Downloads/Patrones--master/delivery_db.db";

                Class.forName("org.sqlite.JDBC");
                org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
                config.setOpenMode(org.sqlite.SQLiteOpenMode.READWRITE);

                conexion = DriverManager.getConnection(urlFija, config.toProperties());
                System.out.println("[BD] Conexion restablecida de forma automatica y segura.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] No se encontro el Driver de SQLite: " + e.getMessage());
            conexion = null;
        } catch (SQLException e) {
            System.out.println("[CRITICO] Error al reabrir la base de datos: " + e.getMessage());
            conexion = null;
        }
        return conexion;
    }
}

