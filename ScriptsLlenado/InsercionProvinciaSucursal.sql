USE ristorinoDB;
GO

-- ===============================================
-- 1. Insertar Provincia (Maestro necesario para FK)
-- ===============================================
-- La tabla 'provincias' tiene cod_provincia (INT) como PK
INSERT INTO dbo.provincias (cod_provincia, nom_provincia)
VALUES (1, 'Córdoba');
GO

-- ===============================================
-- 2. Insertar Localidad (Maestro necesario para FK)
-- ===============================================
-- La tabla 'localidades' tiene nro_localidad (IDENTITY)
INSERT INTO dbo.localidades (nom_localidad, cod_provincia)
VALUES ('Córdoba Capital', 1);
GO

-- ===============================================
-- 3. Insertar la Sucursal (Lógica de Negocio)
-- ===============================================
-- Utilizamos el nro_restaurante = 1 (Bella Pizza Central)
-- y el nro_localidad = 1 (asumiendo que es el ID que se autogeneró)

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
    1, -- nro_restaurante (Debe coincidir con la PK de dbo.restaurantes)
    1, -- nro_sucursal (El primer local del restaurante)
    'Sucursal Centro', 
    'Av. General Paz', 
    '250', 
    'Centro', 
    (SELECT nro_localidad FROM dbo.localidades WHERE nom_localidad = 'Córdoba Capital'), -- Busca el ID autogenerado
    'BP-SUC-001'
);
GO