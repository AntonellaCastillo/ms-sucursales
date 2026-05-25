CREATE TABLE sucursal (
    id_sucursal BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL
);

CREATE TABLE envio (
    id_envio BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_sucursal BIGINT NOT NULL,
    id_pedido BIGINT NOT NULL,
    direccion_destino VARCHAR(255) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_despacho DATETIME NOT NULL,
    fecha_entrega DATETIME,
    FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
);