# MS Sucursales y Logística — Perfulandia SPA

Microservicio encargado de la gestión de sucursales y operaciones logísticas
del sistema Perfulandia SPA. Forma parte de una arquitectura de microservicios.

## Tecnologías

- Java 25
- Spring Boot 4.0.7
- Maven
- MySQL (XAMPP)
- Spring Data JPA
- Bean Validation
- Lombok
- Spring Boot Actuator

## Dominio

Pertenece al dominio **Operaciones y Logística**, ya que se encarga de la
gestión de las sucursales y de operaciones logísticas como la planificación
de rutas de entrega de los pedidos.

## Responsabilidad

Administrar las sucursales (su información operativa, horarios y personal) y
gestionar las rutas de entrega de los pedidos.

## Historias de usuario

- **HU-31**: Configurar una nueva sucursal (rol Gerente). Registrar nombre,
  ubicación (lat/long) y horarios. → HTTP 201.
- **HU-32**: Actualizar la configuración de la sucursal (rol Gerente).
  Validar que el gerente solo modifique la sucursal que administra. → HTTP 200.
- **HU-35**: Planificar y optimizar rutas de entrega (rol Logística).

## Comunicación con otros microservicios

- **MS Sucursales/Logística → MS Envíos** (asíncrona / evento): al marcar un
  envío como "Entregado", emite un evento para actualizar el estado del pedido
  y notificar al cliente.

## Configuración

- **Puerto:** 8087
- **Base de datos:** db_perfulandia_logistica

## Cómo ejecutar

1. Tener XAMPP corriendo (MySQL activo).
2. Crear la base de datos `db_perfulandia_logistica`.
3. Ejecutar el proyecto desde `MsSucursalesApplication.java`.
4. El servicio queda disponible en `http://localhost:8087`.

## Autor

Antonella Castillo — Duoc UC — DSY1103 Desarrollo Fullstack I