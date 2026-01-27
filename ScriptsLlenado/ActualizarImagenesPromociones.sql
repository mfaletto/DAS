USE ristorinoDB;
GO

-- Script para actualizar las imágenes promocionales de los restaurantes
-- Restaurante 1: Bella Pizza Central
-- Restaurante 2: Ristorino Gourmet

-- Actualizar todas las promociones del Restaurante 1 (Bella Pizza Central)
UPDATE dbo.contenidos_restaurantes
SET imagen_promocional = 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cmVzdGF1cmFudHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&q=60&w=600'
WHERE nro_restaurante = 1;
GO

-- Actualizar todas las promociones del Restaurante 2 (Ristorino Gourmet)
UPDATE dbo.contenidos_restaurantes
SET imagen_promocional = 'https://plus.unsplash.com/premium_photo-1661953124283-76d0a8436b87?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8cmVzdGF1cmFudHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&q=60&w=600'
WHERE nro_restaurante = 2;
GO

-- Verificar los cambios
SELECT 
    nro_restaurante,
    nro_contenido,
    contenido_promocional,
    imagen_promocional,
    LEN(imagen_promocional) AS longitud_url
FROM dbo.contenidos_restaurantes
ORDER BY nro_restaurante, nro_contenido;
GO

