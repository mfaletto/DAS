USE ristorinoDB;
GO

-- 1. Insertar un idioma (Español)
-- Asumimos que la tabla está vacía y nro_idioma es IDENTITY(1,1)
SET IDENTITY_INSERT dbo.idiomas ON;
INSERT INTO dbo.idiomas (nro_idioma, nom_idioma, cod_idioma)
VALUES (1, 'Español (Argentina)', 'es-AR');
SET IDENTITY_INSERT dbo.idiomas OFF;
GO

-- 2. Insertar un restaurante
-- Asumimos que nro_restaurante es IDENTITY(1,1)
SET IDENTITY_INSERT dbo.restaurantes ON;
INSERT INTO dbo.restaurantes (nro_restaurante, razon_social, cuit)
VALUES (1, 'Bella Pizza Central', '30-12345678-9');
SET IDENTITY_INSERT dbo.restaurantes OFF;
GO

-- 3. Insertar promociones (Contenidos) para ese restaurante e idioma
-- La PK es (nro_restaurante, nro_idioma, nro_contenido).
INSERT INTO dbo.contenidos_restaurantes 
    (nro_restaurante, nro_idioma, nro_contenido, contenido_promocional, imagen_promocional, costo_click, fecha_ini_vigencia, fecha_fin_vigencia, cod_contenido_restaurante)
VALUES
(
    1, -- nro_restaurante (Bella Pizza Central)
    1, -- nro_idioma (Español)
    1, -- nro_contenido (ID manual 1)
    'Menú Estudiantil: 25% OFF en pizzas personales!', -- contenido_promocional
    'https://images.unsplash.com/photo-1541745537418-472147815d3e?w=600&h=400&fit=crop&q=80', -- imagen_promocional
    0.25, -- costo_click
    GETDATE(), -- fecha_ini_vigencia (ahora)
    NULL, -- fecha_fin_vigencia (sin vencimiento)
    'BP-PROMO-001' -- cod_contenido_restaurante (código único)
),
(
    1, -- nro_restaurante (Bella Pizza Central)
    1, -- nro_idioma (Español)
    2, -- nro_contenido (ID manual 2)
    'Postre Doble: Pide un plato principal y obtén el postre para compartir.',
    'https://images.unsplash.com/photo-1558985121-a477341e3305?w=600&h=400&fit=crop&q=80',
    0.30,
    GETDATE(),
    NULL,
    'BP-PROMO-002'
);
GO