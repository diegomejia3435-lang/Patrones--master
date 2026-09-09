# Sistema de Gestión de Tienda y Delivery (Patrones de Diseño)

Este proyecto implementa una arquitectura orientada a objetos en Java utilizando múltiples Patrones de Diseño GoF (Gang of Four):

## Patrones Implementados

- **Strategy**: `MetodoPagoStrategy` (PagoEfectivo, TarjetaAdapter, YapeAdapter).
- **Adapter**: `TarjetaAdapter` y `YapeAdapter` adaptan APIs externas (`POSBancarioAPIExterna`, `YapeAPIExterna`).
- **Factory Method**: `ProductoFactory` para la instanciación de `ProductoDirecto` y `ProductoPreparado`.
- **Facade**: `TiendaFacade` simplifica el flujo de compra y la interfaz con la base de datos y vistas.
- **State**: `EstadoPreparacion` (EstadoEnEspera, EstadoPreparando, EstadoListo) gestiona el estado de la cocina.
- **DAO (Data Access Object)**: `CatalogoDAO` y `OrdenDAO` para persistencia en SQLite (`delivery_db.db`).

## Estructura del Proyecto
- `src/main/java/Cocina/`: Lógica del patrón State para la cocina.
- `src/main/java/Database/`: Persistencia con SQLite.
- `src/main/java/Pagos/`: Patrón Strategy y Adapter.
- `src/main/java/Productos/`: Patrón Factory Method.
- `src/main/java/Vistas/`: Interfaces de usuario Swing y Facade.
