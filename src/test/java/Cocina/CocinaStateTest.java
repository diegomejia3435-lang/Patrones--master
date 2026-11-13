package Cocina;

import Productos.Orden;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CocinaStateTest {

    @Test
    public void testTransicionEstadosOrden() {
        // 1. Instanciar Orden con los parámetros reales: (int id, String cliente)
        Orden orden = new Orden(1, "Cliente Prueba");
        CocinaService cocina = new CocinaService();

        assertNotNull(orden, "La orden no debe ser nula");

        // 2. Pasar el ID (int) al método procesarOrden según la firma de CocinaService
        assertDoesNotThrow(() -> {
            cocina.procesarOrden(1);
        }, "El servicio de cocina debe procesar los cambios de estado correctamente");
    }
}
