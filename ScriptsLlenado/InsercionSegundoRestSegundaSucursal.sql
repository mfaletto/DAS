USE ristorinoDB;
GO

-- 1. Insertar el SEGUNDO RESTAURANTE
-- Asumimos que nro_restaurante es IDENTITY(1,1)
-- Si Bella Pizza es ID 1, Ristorino Gourmet debería ser ID 2.
SET IDENTITY_INSERT dbo.restaurantes ON;
INSERT INTO dbo.restaurantes (nro_restaurante, razon_social, cuit)
VALUES (2, 'Ristorino Gourmet', '30-98765432-1');
SET IDENTITY_INSERT dbo.restaurantes OFF;
GO

-- 2. Insertar la SEGUNDA SUCURSAL (para Ristorino Gourmet)
INSERT INTO dbo.sucursales_restaurantes (
    nro_restaurante, 
    nro_sucursal, 
    nom_sucursal, 
    calle, 
    nro_calle, 
    barrio, 
    nro_localidad,
    cod_sucursal_restaurante
)
VALUES (
    2, -- nro_restaurante (Ristorino Gourmet)
    1, -- nro_sucursal
    'Sucursal Gourmet', 
    'Av. Hipolito Yrigoyen', 
    '450', 
    'Nueva Córdoba', 
    (SELECT nro_localidad FROM dbo.localidades WHERE nom_localidad = 'Córdoba Capital'),
    'RG-SUC-001'
);
GO

-- 3. Insertar DOS NUEVAS PROMOCIONES para Ristorino Gourmet (ID 2)
INSERT INTO dbo.contenidos_restaurantes 
    (nro_restaurante, nro_idioma, nro_contenido, contenido_promocional, imagen_promocional, costo_click, fecha_ini_vigencia, fecha_fin_vigencia, cod_contenido_restaurante)
VALUES
(
    2, -- nro_restaurante (Ristorino Gourmet)
    1, -- nro_idioma (Español)
    3, -- nro_contenido (ID 3)
    'Cena Degustación de Primavera (Nueva Carta)', 
    'https://images.unsplash.com/photo-1551025217-488f219d3e8e?w=600&h=400&fit=crop&q=80', -- Imagen Gourmet
    0.45, -- costo_click
    GETDATE(), 
    NULL, 
    'RG-PROMO-003'
),
(
    2, -- nro_restaurante (Ristorino Gourmet)
    1, -- nro_idioma (Español)
    4, -- nro_contenido (ID 4)
    '2x1 en Postres de Autor',
    'https://images.unsplash.com/photo-1542475142-326922d0d0f7?w=600&h=400&fit=crop&q=80', -- Imagen de postre
    0.20,
    GETDATE(),
    NULL,
    'RG-PROMO-004'
);
GO