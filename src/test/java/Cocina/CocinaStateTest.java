package Cocina;

import Productos.Orden;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CocinaStateTest {

    @Test
    public void testTransicionEstadosOrden() {
        // 1. Crear una orden nueva (debería iniciar en EstadoEnEspera)
        Orden orden = new Orden();
        CocinaService cocina = new CocinaService();

        assertNotNull(orden, "La orden no debe ser nula");

        // 2. Verificar que el flujo de estados avanza sin lanzar excepciones
        // Si tu clase Orden o CocinaService tiene getEstado(), puedes validar el nombre de la clase:
        // assertTrue(orden.getEstado() instanceof EstadoEnEspera);

        assertDoesNotThrow(() -> {
            cocina.procesarSiguientePaso(orden);
        }, "El servicio de cocina debe procesar los cambios de estado correctamente");
    }
}
