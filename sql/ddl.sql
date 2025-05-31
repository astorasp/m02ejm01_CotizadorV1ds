-- Crear base de datos
CREATE DATABASE IF NOT EXISTS cotizador
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE cotizador;

-- Tabla para tipos de componentes
CREATE TABLE cotipo_componente (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Tabla principal de componentes
CREATE TABLE cocomponente (
    id_componente VARCHAR(50) PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    costo DECIMAL(20,2) NOT NULL,
    precio_base DECIMAL(20,2) NOT NULL,
    id_tipo_componente SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (id_tipo_componente) REFERENCES cotipo_componente(id)
) ENGINE=InnoDB;

-- Tablas específicas por tipo
CREATE TABLE codisco_duro (
    id_componente VARCHAR(50) PRIMARY KEY,
    capacidad_alm VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_componente) REFERENCES cocomponente(id_componente) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE cotarjeta_video (
    id_componente VARCHAR(50) PRIMARY KEY,
    memoria VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_componente) REFERENCES cocomponente(id_componente) ON DELETE CASCADE
) ENGINE=InnoDB;


-- Tabla para la relación composite (PC -> componentes)
CREATE TABLE copc_partes (
    id_pc VARCHAR(50) NOT NULL,
    id_componente VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_pc, id_componente),
    FOREIGN KEY (id_componente) REFERENCES cocomponente(id_componente) ON DELETE CASCADE,
    CONSTRAINT chk_no_self_reference CHECK (id_pc != id_componente)
) ENGINE=InnoDB;

-- Tabla de cotizaciones
CREATE TABLE cocotizacion (
    folio INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    fecha VARCHAR(20) NOT NULL,    
    impuestos DECIMAL(20,2) NOT NULL,
    subtotal DECIMAL(20,2) NOT NULL,
    total DECIMAL(20,2) NOT NULL
) ENGINE=InnoDB;

-- Tabla de detalles de cotización
CREATE TABLE codetalle_cotizacion (
    folio INT NOT NULL,
    num_detalle INT UNSIGNED NOT NULL,
    cantidad INT UNSIGNED NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    id_componente VARCHAR(50) NOT NULL,
    precio_base DECIMAL(20,2) NOT NULL,
    PRIMARY KEY (folio, num_detalle),
    FOREIGN KEY (id_componente) REFERENCES cocomponente(id_componente)
) ENGINE=InnoDB;

-- Tabla de proveedores
CREATE TABLE coproveedor (
    cve VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    razon_social VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Tabla de pedidos
CREATE TABLE copedido (
    num_pedido INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    fecha_emision DATE NOT NULL,
    fecha_entrega DATE NOT NULL,
    nivel_surtido INT NOT NULL,
    cve_proveedor VARCHAR(50) NOT NULL,
    FOREIGN KEY (cve_proveedor) REFERENCES coproveedor(cve)
) ENGINE=InnoDB;

-- Tabla de detalles de pedido
CREATE TABLE codetalle_pedido (
    cantidad INT UNSIGNED NOT NULL,
    id_componente VARCHAR(50) NOT NULL,
    num_detalle INT UNSIGNED NOT NULL,
    num_pedido INT UNSIGNED NOT NULL,
    precio_unitario DECIMAL(20,2) NOT NULL,
    total_cotizado DECIMAL(20,2) NOT NULL,
    PRIMARY KEY (num_pedido, num_detalle),
    FOREIGN KEY (num_pedido) REFERENCES copedido(num_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_componente) REFERENCES cocomponente(id_componente)
) ENGINE=InnoDB;

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_componente_tipo ON cocomponente (id_tipo_componente);
CREATE INDEX idx_pcpartes_pc ON copc_partes (id_pc);
CREATE INDEX idx_detalle_cotizacion_cotizacion ON codetalle_cotizacion (folio,num_detalle);
CREATE INDEX idx_detalle_pedido_pedido ON codetalle_pedido (num_pedido);