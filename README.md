# MS Sucursales y Logística - Perfulandia

Microservicio encargado de gestionar las sucursales y los envíos en el ecosistema Perfulandia.

## Información del microservicio
- **Puerto:** 8087
- **Base de datos:** db_perfulandia_logistica
- **Tecnología:** Spring Boot 4.0.6, Java 25, MySQL

## Funcionalidades
- Crear y gestionar sucursales
- Listar sucursales
- Crear envíos
- Actualizar estado de envíos
- Seguimiento de envíos

## Endpoints
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | /api/v1/sucursales | Crear sucursal |
| GET | /api/v1/sucursales | Listar todas las sucursales |
| GET | /api/v1/sucursales/{id} | Buscar sucursal por ID |
| PUT | /api/v1/sucursales/{id} | Actualizar sucursal |
| DELETE | /api/v1/sucursales/{id} | Eliminar sucursal |
| POST | /api/v1/envios | Crear envío |
| GET | /api/v1/envios | Listar todos los envíos |
| GET | /api/v1/envios/{id} | Buscar envío por ID |
| GET | /api/v1/envios/pedido/{idPedido} | Buscar envío por pedido |
| PUT | /api/v1/envios/{id}/estado | Actualizar estado del envío |

## Conexión con otros microservicios
- **MS Pedidos (8091):** Actualiza el estado del pedido cuando el envío cambia
- **MS Notificaciones (8089):** Notifica al cliente sobre el estado del envío

## Cómo ejecutar
1. Tener XAMPP corriendo con MySQL
2. Crear la base de datos `db_perfulandia_logistica`
3. Ejecutar el proyecto con `./mvnw spring-boot:run`