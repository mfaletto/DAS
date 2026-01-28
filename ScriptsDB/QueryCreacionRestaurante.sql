/* ============================================
   CLEAN BUILD - RISTORINO (SQL Server, dbo)
   Sin cascadas para evitar multiple cascade paths
============================================ */
use restaurantes_ristorino
SET NOCOUNT ON;
GO

/* ============================================
   0) DROP en orden inverso
============================================ */
IF OBJECT_ID('dbo.clicks_contenidos', 'U') IS NOT NULL DROP TABLE dbo.clicks_contenidos;
IF OBJECT_ID('dbo.reservas_sucursales', 'U') IS NOT NULL DROP TABLE dbo.reservas_sucursales;
IF OBJECT_ID('dbo.estilos_sucursales', 'U') IS NOT NULL DROP TABLE dbo.estilos_sucursales;
IF OBJECT_ID('dbo.especialidades_alimentarias_sucursales', 'U') IS NOT NULL DROP TABLE dbo.especialidades_alimentarias_sucursales;
IF OBJECT_ID('dbo.tipos_comidas_sucursales', 'U') IS NOT NULL DROP TABLE dbo.tipos_comidas_sucursales;
IF OBJECT_ID('dbo.zonas_turnos_sucursales', 'U') IS NOT NULL DROP TABLE dbo.zonas_turnos_sucursales;
IF OBJECT_ID('dbo.turnos_sucursales', 'U') IS NOT NULL DROP TABLE dbo.turnos_sucursales;
IF OBJECT_ID('dbo.zonas_sucursales', 'U') IS NOT NULL DROP TABLE dbo.zonas_sucursales;
IF OBJECT_ID('dbo.contenidos', 'U') IS NOT NULL DROP TABLE dbo.contenidos;
IF OBJECT_ID('dbo.clientes', 'U') IS NOT NULL DROP TABLE dbo.clientes;
IF OBJECT_ID('dbo.sucursales', 'U') IS NOT NULL DROP TABLE dbo.sucursales;
IF OBJECT_ID('dbo.restaurantes', 'U') IS NOT NULL DROP TABLE dbo.restaurantes;
IF OBJECT_ID('dbo.estilos', 'U') IS NOT NULL DROP TABLE dbo.estilos;
IF OBJECT_ID('dbo.especialidades_alimentarias', 'U') IS NOT NULL DROP TABLE dbo.especialidades_alimentarias;
IF OBJECT_ID('dbo.tipos_comidas', 'U') IS NOT NULL DROP TABLE dbo.tipos_comidas;
IF OBJECT_ID('dbo.zonas', 'U') IS NOT NULL DROP TABLE dbo.zonas;
IF OBJECT_ID('dbo.categorias_precios', 'U') IS NOT NULL DROP TABLE dbo.categorias_precios;
IF OBJECT_ID('dbo.localidades', 'U') IS NOT NULL DROP TABLE dbo.localidades;
IF OBJECT_ID('dbo.provincias', 'U') IS NOT NULL DROP TABLE dbo.provincias;
GO

/* ============================================
   1) Tablas maestras
============================================ */
CREATE TABLE dbo.provincias (
  cod_provincia   INT NOT NULL PRIMARY KEY,
  nom_provincia   VARCHAR(120) NOT NULL
);
GO

CREATE TABLE dbo.localidades (
  nro_localidad   INT NOT NULL PRIMARY KEY,
  nom_localidad   VARCHAR(120) NOT NULL,
  cod_provincia   INT NOT NULL,
  CONSTRAINT uq_localidad_por_prov UNIQUE (cod_provincia, nom_localidad),
  CONSTRAINT fk_localidades_prov FOREIGN KEY (cod_provincia)
    REFERENCES dbo.provincias(cod_provincia)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

CREATE TABLE dbo.categorias_precios (
  nro_categoria   INT NOT NULL PRIMARY KEY,
  nom_categoria   VARCHAR(100) NOT NULL UNIQUE
);
GO

CREATE TABLE dbo.zonas (
  cod_zona        INT NOT NULL PRIMARY KEY,
  nom_zona        VARCHAR(100) NOT NULL UNIQUE
);
GO

CREATE TABLE dbo.tipos_comidas (
  nro_tipo_comida INT NOT NULL PRIMARY KEY,
  nom_tipo_comida VARCHAR(120) NOT NULL UNIQUE
);
GO

CREATE TABLE dbo.especialidades_alimentarias (
  nro_restriccion INT NOT NULL PRIMARY KEY,
  nom_restriccion VARCHAR(120) NOT NULL UNIQUE
);
GO

CREATE TABLE dbo.estilos (
  nro_estilo      INT NOT NULL PRIMARY KEY,
  nom_estilo      VARCHAR(120) NOT NULL UNIQUE
);
GO

/* ============================================
   2) Restaurantes, Sucursales, Contenidos
============================================ */
CREATE TABLE dbo.restaurantes (
  nro_restaurante INT NOT NULL PRIMARY KEY,
  razon_social    VARCHAR(200) NOT NULL,
  cuit            VARCHAR(20)  NOT NULL,
  CONSTRAINT uq_restaurantes_cuit UNIQUE (cuit)
);
GO

CREATE TABLE dbo.sucursales (
  nro_restaurante        INT NOT NULL,
  nro_sucursal           INT NOT NULL,
  nom_sucursal           VARCHAR(150) NOT NULL,
  calle                  VARCHAR(150) NULL,
  nro_calle              INT NULL,
  barrio                 VARCHAR(150) NULL,
  nro_localidad          INT NOT NULL,
  cod_postal             VARCHAR(20) NULL,
  telefonos              VARCHAR(200) NULL,
  total_comensales       INT NOT NULL CONSTRAINT df_suc_total_comensales DEFAULT (0),
  min_tolerancia_reserva INT NOT NULL CONSTRAINT df_suc_tolerancia DEFAULT (0),
  nro_categoria          INT NOT NULL,
  CONSTRAINT pk_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal),
  CONSTRAINT fk_suc_rest FOREIGN KEY (nro_restaurante)
    REFERENCES dbo.restaurantes(nro_restaurante)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_suc_loc FOREIGN KEY (nro_localidad)
    REFERENCES dbo.localidades(nro_localidad)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_suc_categoria FOREIGN KEY (nro_categoria)
    REFERENCES dbo.categorias_precios(nro_categoria)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

CREATE TABLE dbo.contenidos (
  nro_restaurante       INT NOT NULL,
  nro_contenido         INT NOT NULL,
  contenido_a_publicar  NVARCHAR(MAX) NULL,
  imagen_a_publicar     NVARCHAR(MAX) NULL,
  publicado             BIT NOT NULL CONSTRAINT df_cont_publicado DEFAULT (0),
  costo_click           DECIMAL(12,2) NOT NULL CONSTRAINT df_cont_costo DEFAULT (0),
  nro_sucursal          INT NULL, -- opcional
  CONSTRAINT pk_contenidos PRIMARY KEY (nro_restaurante, nro_contenido),
  CONSTRAINT fk_cont_rest FOREIGN KEY (nro_restaurante)
    REFERENCES dbo.restaurantes(nro_restaurante)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_cont_suc FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales(nro_restaurante, nro_sucursal)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

/* ============================================
   3) Zonas y Turnos por sucursal
============================================ */
CREATE TABLE dbo.zonas_sucursales (
  nro_restaurante  INT NOT NULL,
  nro_sucursal     INT NOT NULL,
  cod_zona         INT NOT NULL,
  cant_comensales  INT NOT NULL CONSTRAINT df_zs_cant DEFAULT (0),
  permite_menores  BIT NOT NULL CONSTRAINT df_zs_menores DEFAULT (1),
  habilitada       BIT NOT NULL CONSTRAINT df_zs_hab DEFAULT (1),
  CONSTRAINT pk_zonas_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona),
  CONSTRAINT fk_zs_suc FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales(nro_restaurante, nro_sucursal)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_zs_zona FOREIGN KEY (cod_zona)
    REFERENCES dbo.zonas(cod_zona)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

CREATE TABLE dbo.turnos_sucursales (
  nro_restaurante  INT NOT NULL,
  nro_sucursal     INT NOT NULL,
  hora_desde       TIME(0) NOT NULL,
  hora_hasta       TIME(0) NOT NULL,
  habilitado       BIT NOT NULL CONSTRAINT df_ts_hab DEFAULT (1),
  CONSTRAINT pk_turnos_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal, hora_desde),
  CONSTRAINT chk_turno_rango CHECK (hora_hasta > hora_desde),
  CONSTRAINT fk_ts_suc FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales(nro_restaurante, nro_sucursal)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

CREATE TABLE dbo.zonas_turnos_sucursales (
  nro_restaurante  INT NOT NULL,
  nro_sucursal     INT NOT NULL,
  cod_zona         INT NOT NULL,
  hora_desde       TIME(0) NOT NULL,
  permite_menores  BIT NOT NULL CONSTRAINT df_zts_menores DEFAULT (1),
  CONSTRAINT pk_zonas_turnos_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona, hora_desde),
  CONSTRAINT fk_zts_zs FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona)
    REFERENCES dbo.zonas_sucursales(nro_restaurante, nro_sucursal, cod_zona)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_zts_ts FOREIGN KEY (nro_restaurante, nro_sucursal, hora_desde)
    REFERENCES dbo.turnos_sucursales(nro_restaurante, nro_sucursal, hora_desde)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

/* ============================================
   4) Oferta gastronómica por sucursal
============================================ */
CREATE TABLE dbo.tipos_comidas_sucursales (
  nro_restaurante  INT NOT NULL,
  nro_sucursal     INT NOT NULL,
  nro_tipo_comida  INT NOT NULL,
  habilitado       BIT NOT NULL CONSTRAINT df_tcs_hab DEFAULT (1),
  CONSTRAINT pk_tipos_comidas_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal, nro_tipo_comida),
  CONSTRAINT fk_tcs_suc FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales(nro_restaurante, nro_sucursal)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tcs_tipo FOREIGN KEY (nro_tipo_comida)
    REFERENCES dbo.tipos_comidas(nro_tipo_comida)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

CREATE TABLE dbo.especialidades_alimentarias_sucursales (
  nro_restaurante  INT NOT NULL,
  nro_sucursal     INT NOT NULL,
  nro_restriccion  INT NOT NULL,
  habilitada       BIT NOT NULL CONSTRAINT df_eas_hab DEFAULT (1),
  CONSTRAINT pk_especialidades_alimentarias_suc PRIMARY KEY (nro_restaurante, nro_sucursal, nro_restriccion),
  CONSTRAINT fk_eas_suc FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales(nro_restaurante, nro_sucursal)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_eas_restr FOREIGN KEY (nro_restriccion)
    REFERENCES dbo.especialidades_alimentarias(nro_restriccion)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

CREATE TABLE dbo.estilos_sucursales (
  nro_restaurante  INT NOT NULL,
  nro_sucursal     INT NOT NULL,
  nro_estilo       INT NOT NULL,
  habilitado       BIT NOT NULL CONSTRAINT df_es_hab DEFAULT (1),
  CONSTRAINT pk_estilos_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal, nro_estilo),
  CONSTRAINT fk_es_suc FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales(nro_restaurante, nro_sucursal)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_es_estilo FOREIGN KEY (nro_estilo)
    REFERENCES dbo.estilos(nro_estilo)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

/* ============================================
   5) Clientes y reservas
============================================ */
CREATE TABLE dbo.clientes (
  nro_cliente      INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  apellido         VARCHAR(120) NOT NULL,
  nombre           VARCHAR(120) NOT NULL,
  correo           VARCHAR(200) NOT NULL,
  telefonos        VARCHAR(200) NULL,
  CONSTRAINT uq_clientes_correo UNIQUE (correo)
);
GO

CREATE TABLE dbo.reservas_sucursales (
  cod_reserva          INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  fecha_hora_registro  DATETIME2(0) NOT NULL CONSTRAINT df_res_fhr DEFAULT (SYSDATETIME()),
  nro_cliente          INT NOT NULL,
  fecha_reserva        DATE NOT NULL,
  nro_restaurante      INT NOT NULL,
  nro_sucursal         INT NOT NULL,
  cod_zona             INT NOT NULL,
  hora_reserva         TIME(0) NOT NULL,
  cant_adultos         INT NOT NULL CONSTRAINT df_res_adultos DEFAULT (1),
  cant_menores         INT NOT NULL CONSTRAINT df_res_menores DEFAULT (0),
  costo_reserva        DECIMAL(12,2) NOT NULL CONSTRAINT df_res_costo DEFAULT (0),
  cancelada            BIT NOT NULL CONSTRAINT df_res_cancelada DEFAULT (0),
  fecha_cancelacion    DATETIME2(0) NULL,
  CONSTRAINT fk_res_cliente FOREIGN KEY (nro_cliente)
    REFERENCES dbo.clientes(nro_cliente)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_res_sucursal FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales(nro_restaurante, nro_sucursal)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_res_zona_sucursal FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona)
    REFERENCES dbo.zonas_sucursales(nro_restaurante, nro_sucursal, cod_zona)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_res_turno_sucursal FOREIGN KEY (nro_restaurante, nro_sucursal, hora_reserva)
    REFERENCES dbo.turnos_sucursales(nro_restaurante, nro_sucursal, hora_desde)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
GO

/* ============================================
   6) Clicks de contenidos
============================================ */
CREATE TABLE dbo.clicks_contenidos (
  nro_restaurante     INT NOT NULL,
  nro_contenido       INT NOT NULL,
  nro_click           INT NOT NULL,
  fecha_hora_registro DATETIME2(0) NOT NULL CONSTRAINT df_cc_fhr DEFAULT (SYSDATETIME()),
  nro_cliente         INT NULL,
  costo_click         DECIMAL(12,2) NOT NULL CONSTRAINT df_cc_costo DEFAULT (0),
  CONSTRAINT pk_clicks_contenidos PRIMARY KEY (nro_restaurante, nro_contenido, nro_click),
  CONSTRAINT fk_cc_contenido FOREIGN KEY (nro_restaurante, nro_contenido)
    REFERENCES dbo.contenidos(nro_restaurante, nro_contenido)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_cc_cliente FOREIGN KEY (nro_cliente)
    REFERENCES dbo.clientes(nro_cliente)
    ON UPDATE NO ACTION ON DELETE SET NULL
);
GO

/* ============================================
   7) Índices
============================================ */
CREATE INDEX idx_sucursales_localidad
  ON dbo.sucursales (nro_localidad);

CREATE INDEX idx_zs_habilitada
  ON dbo.zonas_sucursales (nro_restaurante, nro_sucursal, habilitada);

CREATE INDEX idx_ts_habilitado
  ON dbo.turnos_sucursales (nro_restaurante, nro_sucursal, habilitado, hora_desde);

CREATE INDEX idx_reservas_fecha
  ON dbo.reservas_sucursales (fecha_reserva, nro_restaurante, nro_sucursal);

CREATE INDEX idx_contenidos_publicado
  ON dbo.contenidos (nro_restaurante, publicado);
GO

USE restaurantes_ristorino;
GO

-- Soporte para notificaciones en clicks_contenidos
ALTER TABLE dbo.clicks_contenidos
ADD notificado BIT NOT NULL DEFAULT 0,
    fecha_hora_notificacion DATETIME2 NULL;
GO

-- Soporte para autenticación en clientes (si no existe)
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.clientes') AND name = 'clave_hash')
BEGIN
    ALTER TABLE dbo.clientes ADD clave_hash VARCHAR(255) NULL;
END
GO