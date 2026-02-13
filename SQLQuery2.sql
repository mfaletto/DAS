USE ristorino_db;
GO

CREATE TABLE clicks_contenidos (
    nro_click INT PRIMARY KEY IDENTITY(1,1),
    nro_restaurante INT NOT NULL,
    nro_cliente INT NOT NULL,
    titulo_contenido VARCHAR(100), -- Guardamos qué vio
    fecha_hora_registro DATETIME DEFAULT GETDATE(),
    costo_click DECIMAL(10, 2) DEFAULT 0.50, -- Simulamos un costo
    
    FOREIGN KEY (nro_restaurante) REFERENCES restaurantes(nro_restaurante),
    FOREIGN KEY (nro_cliente) REFERENCES clientes(nro_cliente)
);
GO