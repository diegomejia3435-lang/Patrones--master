package Cocina;

import Productos.Orden;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CocinaStateTest {

    @Test
    public void testInstanciasEstadosCocina() {
        Orden orden = new Orden(1, "Cliente Prueba");
        EstadoPreparacion enEspera = new EstadoEnEspera();
        EstadoPreparacion preparando = new EstadoPreparando();
        EstadoPreparacion listo = new EstadoListo();

        assertNotNull(orden, "La orden debe crearse correctamente");
        assertNotNull(enEspera, "EstadoEnEspera debe instanciarse");
        assertNotNull(preparando, "EstadoPreparando debe instanciarse");
        assertNotNull(listo, "EstadoListo debe instanciarse");
    }
}
