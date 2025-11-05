/* =========================================================
   Base de datos
========================================================= */
IF DB_ID('ristorinoDB') IS NULL
BEGIN
  CREATE DATABASE ristorinoDB;
END
GO
USE ristorinoDB;
GO

/* =========================================================
   Limpieza opcional (descomentá si necesitás resetear)
   OJO: esto borra todas las tablas si existen.
========================================================= */
/*
EXEC sp_msforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL';
EXEC sp_msforeachtable 'DROP TABLE ?';
*/

/* =========================================================
   Tablas base (catálogos independientes)
========================================================= */

-- Provincias
IF OBJECT_ID('dbo.provincias','U') IS NOT NULL DROP TABLE dbo.provincias;
CREATE TABLE dbo.provincias (
  cod_provincia      INT            NOT NULL,
  nom_provincia      VARCHAR(120)   NOT NULL,
  CONSTRAINT PK_provincias PRIMARY KEY (cod_provincia)
);

-- Idiomas
IF OBJECT_ID('dbo.idiomas','U') IS NOT NULL DROP TABLE dbo.idiomas;
CREATE TABLE dbo.idiomas (
  nro_idioma         INT            IDENTITY(1,1) NOT NULL,
  nom_idioma         VARCHAR(100)   NOT NULL,
  cod_idioma         VARCHAR(20)    NOT NULL, -- ej: es-AR, en-US
  CONSTRAINT PK_idiomas PRIMARY KEY (nro_idioma),
  CONSTRAINT UQ_idiomas_cod UNIQUE (cod_idioma)
);

-- Categorías de preferencias
IF OBJECT_ID('dbo.categorias_preferencias','U') IS NOT NULL DROP TABLE dbo.categorias_preferencias;
CREATE TABLE dbo.categorias_preferencias (
  cod_categoria      INT            IDENTITY(1,1) NOT NULL,
  nom_categoria      VARCHAR(150)   NOT NULL,
  CONSTRAINT PK_categorias_preferencias PRIMARY KEY (cod_categoria)
);

-- Atributos (para configuración de restaurantes)
IF OBJECT_ID('dbo.atributos','U') IS NOT NULL DROP TABLE dbo.atributos;
CREATE TABLE dbo.atributos (
  cod_atributo       INT            IDENTITY(1,1) NOT NULL,
  nom_atributo       VARCHAR(150)   NOT NULL,
  tipo_dato          VARCHAR(30)    NOT NULL, -- ej: string, int, decimal, bool, etc.
  CONSTRAINT PK_atributos PRIMARY KEY (cod_atributo),
  CONSTRAINT UQ_atributos_nom UNIQUE (nom_atributo)
);

/* =========================================================
   Localidades (depende de provincias)
========================================================= */
IF OBJECT_ID('dbo.localidades','U') IS NOT NULL DROP TABLE dbo.localidades;
CREATE TABLE dbo.localidades (
  nro_localidad      INT            IDENTITY(1,1) NOT NULL,
  nom_localidad      VARCHAR(150)   NOT NULL,
  cod_provincia      INT            NOT NULL,
  CONSTRAINT PK_localidades PRIMARY KEY (nro_localidad),
  CONSTRAINT FK_localidades_provincias
    FOREIGN KEY (cod_provincia)
    REFERENCES dbo.provincias (cod_provincia)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  -- AK: combinación provincia + nombre
  CONSTRAINT UQ_localidades_prov_nom UNIQUE (cod_provincia, nom_localidad)
);

 /* =========================================================
   Restaurantes y clientes (dependen de localidades)
========================================================= */

-- Restaurantes
IF OBJECT_ID('dbo.restaurantes','U') IS NOT NULL DROP TABLE dbo.restaurantes;
CREATE TABLE dbo.restaurantes (
  nro_restaurante    INT            IDENTITY(1,1) NOT NULL,
  razon_social       VARCHAR(200)   NOT NULL,
  cuit               VARCHAR(20)    NOT NULL,
  CONSTRAINT PK_restaurantes PRIMARY KEY (nro_restaurante),
  CONSTRAINT UQ_restaurantes_cuit UNIQUE (cuit)
);

-- Clientes
IF OBJECT_ID('dbo.clientes','U') IS NOT NULL DROP TABLE dbo.clientes;
CREATE TABLE dbo.clientes (
  nro_cliente        INT            IDENTITY(1,1) NOT NULL,
  apellido           VARCHAR(120)   NOT NULL,
  nombre             VARCHAR(120)   NOT NULL,
  clave              VARCHAR(255)   NOT NULL,      -- hash/clave
  correo             VARCHAR(180)   NOT NULL,      -- AK
  telefonos          VARCHAR(200)   NULL,
  nro_localidad      INT            NULL,
  habilitado         BIT            NOT NULL DEFAULT(1),
  CONSTRAINT PK_clientes PRIMARY KEY (nro_cliente),
  CONSTRAINT UQ_clientes_correo UNIQUE (correo),
  CONSTRAINT FK_clientes_localidades
    FOREIGN KEY (nro_localidad)
    REFERENCES dbo.localidades (nro_localidad)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Sucursales de restaurantes (depende de restaurantes y localidades)
========================================================= */

IF OBJECT_ID('dbo.sucursales_restaurantes','U') IS NOT NULL DROP TABLE dbo.sucursales_restaurantes;
CREATE TABLE dbo.sucursales_restaurantes (
  nro_restaurante          INT           NOT NULL,  -- AK1.1 indica que participa en clave/AK
  nro_sucursal             INT           NOT NULL,
  nom_sucursal             VARCHAR(150)  NOT NULL,
  calle                    VARCHAR(200)  NULL,
  nro_calle                VARCHAR(20)   NULL,
  barrio                   VARCHAR(150)  NULL,
  nro_localidad            INT           NOT NULL,
  cod_postal               VARCHAR(15)   NULL,
  telefonos                VARCHAR(200)  NULL,
  total_comensales         INT           NULL,
  min_tolerencia_reserva   INT           NULL,      -- minutos
  cod_sucursal_restaurante VARCHAR(50)   NOT NULL,  -- AK1.2
  CONSTRAINT PK_sucursales_restaurantes PRIMARY KEY (nro_restaurante, nro_sucursal),
  CONSTRAINT UQ_sucursales_cod UNIQUE (cod_sucursal_restaurante),
  CONSTRAINT FK_sucursales_restaurantes_restaurantes
    FOREIGN KEY (nro_restaurante)
    REFERENCES dbo.restaurantes (nro_restaurante)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_sucursales_restaurantes_localidades
    FOREIGN KEY (nro_localidad)
    REFERENCES dbo.localidades (nro_localidad)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Configuración de restaurantes (depende de restaurantes y atributos)
========================================================= */

IF OBJECT_ID('dbo.configuracion_restaurantes','U') IS NOT NULL DROP TABLE dbo.configuracion_restaurantes;
CREATE TABLE dbo.configuracion_restaurantes (
  nro_restaurante    INT           NOT NULL,
  cod_atributo       INT           NOT NULL,
  valor              VARCHAR(4000) NULL,
  CONSTRAINT PK_configuracion_restaurantes PRIMARY KEY (nro_restaurante, cod_atributo),
  CONSTRAINT FK_config_rest_rest
    FOREIGN KEY (nro_restaurante)
    REFERENCES dbo.restaurantes (nro_restaurante)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_config_rest_atrib
    FOREIGN KEY (cod_atributo)
    REFERENCES dbo.atributos (cod_atributo)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Dominio y traducciones de categorías y valores de dominio
========================================================= */

-- Dominio de categorías de preferencias
IF OBJECT_ID('dbo.dominio_categorias_preferencias','U') IS NOT NULL DROP TABLE dbo.dominio_categorias_preferencias;
CREATE TABLE dbo.dominio_categorias_preferencias (
  cod_categoria      INT           NOT NULL,
  nro_valor_dominio  INT           NOT NULL,
  nom_valor_dominio  VARCHAR(200)  NOT NULL,
  CONSTRAINT PK_dominio_cat_pref PRIMARY KEY (cod_categoria, nro_valor_dominio),
  CONSTRAINT FK_dom_cat_pref_cat
    FOREIGN KEY (cod_categoria)
    REFERENCES dbo.categorias_preferencias (cod_categoria)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Traducciones de categorías por idioma
IF OBJECT_ID('dbo.idiomas_categorias_preferencias','U') IS NOT NULL DROP TABLE dbo.idiomas_categorias_preferencias;
CREATE TABLE dbo.idiomas_categorias_preferencias (
  cod_categoria      INT           NOT NULL,
  nro_idioma         INT           NOT NULL,
  categoria          VARCHAR(200)  NOT NULL,
  desc_categoria     VARCHAR(1000) NULL,
  CONSTRAINT PK_idiomas_cat_pref PRIMARY KEY (cod_categoria, nro_idioma),
  CONSTRAINT FK_idiomas_cat_pref_cat
    FOREIGN KEY (cod_categoria)
    REFERENCES dbo.categorias_preferencias (cod_categoria)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_idiomas_cat_pref_idioma
    FOREIGN KEY (nro_idioma)
    REFERENCES dbo.idiomas (nro_idioma)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Traducciones de valores de dominio por idioma
IF OBJECT_ID('dbo.idiomas_dominio_cat_preferencias','U') IS NOT NULL DROP TABLE dbo.idiomas_dominio_cat_preferencias;
CREATE TABLE dbo.idiomas_dominio_cat_preferencias (
  cod_categoria      INT           NOT NULL,
  nro_valor_dominio  INT           NOT NULL,
  nro_idioma         INT           NOT NULL,
  valor_dominio      VARCHAR(200)  NOT NULL,
  desc_valor_dominio VARCHAR(1000) NULL,
  CONSTRAINT PK_idiomas_dom_cat_pref PRIMARY KEY (cod_categoria, nro_valor_dominio, nro_idioma),
  CONSTRAINT FK_idiomas_dom_cat_pref_dom
    FOREIGN KEY (cod_categoria, nro_valor_dominio)
    REFERENCES dbo.dominio_categorias_preferencias (cod_categoria, nro_valor_dominio)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_idiomas_dom_cat_pref_idioma
    FOREIGN KEY (nro_idioma)
    REFERENCES dbo.idiomas (nro_idioma)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Preferencias (clientes y restaurantes)
========================================================= */

-- Preferencias de clientes
IF OBJECT_ID('dbo.preferencias_clientes','U') IS NOT NULL DROP TABLE dbo.preferencias_clientes;
CREATE TABLE dbo.preferencias_clientes (
  nro_cliente        INT           NOT NULL,
  cod_categoria      INT           NOT NULL,
  nro_valor_dominio  INT           NOT NULL,
  observaciones      VARCHAR(1000) NULL,
  CONSTRAINT PK_preferencias_clientes PRIMARY KEY (nro_cliente, cod_categoria, nro_valor_dominio),
  CONSTRAINT FK_pref_cli_cliente
    FOREIGN KEY (nro_cliente)
    REFERENCES dbo.clientes (nro_cliente)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_pref_cli_dom
    FOREIGN KEY (cod_categoria, nro_valor_dominio)
    REFERENCES dbo.dominio_categorias_preferencias (cod_categoria, nro_valor_dominio)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Preferencias de restaurantes (asociables a sucursal)
IF OBJECT_ID('dbo.preferencias_restaurantes','U') IS NOT NULL DROP TABLE dbo.preferencias_restaurantes;
CREATE TABLE dbo.preferencias_restaurantes (
  nro_preferencia    INT           IDENTITY(1,1) NOT NULL,
  nro_restaurante    INT           NOT NULL,
  cod_categoria      INT           NOT NULL,
  nro_valor_dominio  INT           NOT NULL,
  nro_sucursal       INT           NULL,           -- si aplica a una sucursal específica
  observaciones      VARCHAR(1000) NULL,
  CONSTRAINT PK_preferencias_restaurantes PRIMARY KEY (nro_preferencia),
  CONSTRAINT FK_pref_rest_rest
    FOREIGN KEY (nro_restaurante)
    REFERENCES dbo.restaurantes (nro_restaurante)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_pref_rest_dom
    FOREIGN KEY (cod_categoria, nro_valor_dominio)
    REFERENCES dbo.dominio_categorias_preferencias (cod_categoria, nro_valor_dominio)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_pref_rest_sucursal
    FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales_restaurantes (nro_restaurante, nro_sucursal)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Contenidos promocionales
========================================================= */

IF OBJECT_ID('dbo.contenidos_restaurantes','U') IS NOT NULL DROP TABLE dbo.contenidos_restaurantes;
CREATE TABLE dbo.contenidos_restaurantes (
  nro_restaurante          INT            NOT NULL,
  nro_idioma               INT            NOT NULL,
  nro_contenido            INT            NOT NULL, -- numeración por restaurante/idioma
  nro_sucursal             INT            NULL,
  contenido_promocional    VARCHAR(MAX)   NULL,
  imagen_promocional       VARCHAR(500)   NULL,     -- URL/archivo
  contenido_a_publicar     VARCHAR(MAX)   NULL,
  fecha_ini_vigencia       DATETIME2      NULL,
  fecha_fin_vigencia       DATETIME2      NULL,
  costo_click              DECIMAL(10,2)  NULL,
  cod_contenido_restaurante VARCHAR(50)   NOT NULL, -- código único de negocio
  CONSTRAINT PK_contenidos_restaurantes PRIMARY KEY (nro_restaurante, nro_idioma, nro_contenido),
  CONSTRAINT UQ_contenidos_cod UNIQUE (cod_contenido_restaurante),
  CONSTRAINT FK_cont_rest_rest
    FOREIGN KEY (nro_restaurante)
    REFERENCES dbo.restaurantes (nro_restaurante)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_cont_rest_idioma
    FOREIGN KEY (nro_idioma)
    REFERENCES dbo.idiomas (nro_idioma)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_cont_rest_sucursal
    FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales_restaurantes (nro_restaurante, nro_sucursal)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Clicks de contenidos
IF OBJECT_ID('dbo.clicks_contenidos_restaurantes','U') IS NOT NULL DROP TABLE dbo.clicks_contenidos_restaurantes;
CREATE TABLE dbo.clicks_contenidos_restaurantes (
  nro_click              INT           IDENTITY(1,1) NOT NULL,
  nro_restaurante        INT           NOT NULL,
  nro_idioma             INT           NOT NULL,
  nro_contenido          INT           NOT NULL,
  fecha_hora_registro    DATETIME2     NOT NULL DEFAULT SYSUTCDATETIME(),
  nro_cliente            INT           NULL,
  costo_click            DECIMAL(10,2) NULL,
  notificado             BIT           NOT NULL DEFAULT(0),
  CONSTRAINT PK_clicks_contenidos PRIMARY KEY (nro_click),
  CONSTRAINT FK_clicks_contenidos_contenido
    FOREIGN KEY (nro_restaurante, nro_idioma, nro_contenido)
    REFERENCES dbo.contenidos_restaurantes (nro_restaurante, nro_idioma, nro_contenido)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_clicks_contenidos_cliente
    FOREIGN KEY (nro_cliente)
    REFERENCES dbo.clientes (nro_cliente)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Turnos, Zonas, y sus traducciones
========================================================= */

-- Turnos por sucursal
IF OBJECT_ID('dbo.turnos_sucursales_restaurantes','U') IS NOT NULL DROP TABLE dbo.turnos_sucursales_restaurantes;
CREATE TABLE dbo.turnos_sucursales_restaurantes (
  nro_restaurante   INT        NOT NULL,
  nro_sucursal      INT        NOT NULL,
  hora_desde        TIME(0)    NOT NULL,
  hora_hasta        TIME(0)    NOT NULL,
  habilitado        BIT        NOT NULL DEFAULT(1),
  CONSTRAINT PK_turnos_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal, hora_desde),
  CONSTRAINT FK_turnos_suc_rest_sucursal
    FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales_restaurantes (nro_restaurante, nro_sucursal)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Zonas por sucursal
IF OBJECT_ID('dbo.zonas_sucursales_restaurantes','U') IS NOT NULL DROP TABLE dbo.zonas_sucursales_restaurantes;
CREATE TABLE dbo.zonas_sucursales_restaurantes (
  nro_restaurante   INT           NOT NULL,
  nro_sucursal      INT           NOT NULL,
  cod_zona          INT           NOT NULL,
  desc_zona         VARCHAR(500)  NULL,
  cant_comensales   INT           NULL,
  permite_menores   BIT           NOT NULL DEFAULT(1),
  habilitada        BIT           NOT NULL DEFAULT(1),
  CONSTRAINT PK_zonas_sucursales PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona),
  CONSTRAINT FK_zonas_suc_rest_sucursal
    FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales_restaurantes (nro_restaurante, nro_sucursal)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Traducciones de zonas
IF OBJECT_ID('dbo.idiomas_zonas_suc_restaurantes','U') IS NOT NULL DROP TABLE dbo.idiomas_zonas_suc_restaurantes;
CREATE TABLE dbo.idiomas_zonas_suc_restaurantes (
  nro_restaurante   INT           NOT NULL,
  nro_sucursal      INT           NOT NULL,
  cod_zona          INT           NOT NULL,
  nro_idioma        INT           NOT NULL,
  zona              VARCHAR(200)  NOT NULL,
  desc_zona         VARCHAR(1000) NULL,
  CONSTRAINT PK_idiomas_zonas_suc PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona, nro_idioma),
  CONSTRAINT FK_idiomas_zonas_zona
    FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona)
    REFERENCES dbo.zonas_sucursales_restaurantes (nro_restaurante, nro_sucursal, cod_zona)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_idiomas_zonas_idioma
    FOREIGN KEY (nro_idioma)
    REFERENCES dbo.idiomas (nro_idioma)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Relación Zonas ↔ Turnos
IF OBJECT_ID('dbo.zonas_turnos_sucursales_restaurantes','U') IS NOT NULL DROP TABLE dbo.zonas_turnos_sucursales_restaurantes;
CREATE TABLE dbo.zonas_turnos_sucursales_restaurantes (
  nro_restaurante   INT        NOT NULL,
  nro_sucursal      INT        NOT NULL,
  cod_zona          INT        NOT NULL,
  hora_desde        TIME(0)    NOT NULL,
  permite_menores   BIT        NOT NULL DEFAULT(1),
  CONSTRAINT PK_zonas_turnos PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona, hora_desde),
  CONSTRAINT FK_zonas_turnos_zona
    FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona)
    REFERENCES dbo.zonas_sucursales_restaurantes (nro_restaurante, nro_sucursal, cod_zona)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_zonas_turnos_turno
    FOREIGN KEY (nro_restaurante, nro_sucursal, hora_desde)
    REFERENCES dbo.turnos_sucursales_restaurantes (nro_restaurante, nro_sucursal, hora_desde)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Estados de reservas y sus traducciones
========================================================= */

IF OBJECT_ID('dbo.estados_reservas','U') IS NOT NULL DROP TABLE dbo.estados_reservas;
CREATE TABLE dbo.estados_reservas (
  cod_estado        INT           IDENTITY(1,1) NOT NULL,
  nom_estado        VARCHAR(120)  NOT NULL,
  CONSTRAINT PK_estados_reservas PRIMARY KEY (cod_estado)
);

IF OBJECT_ID('dbo.idiomas_estados','U') IS NOT NULL DROP TABLE dbo.idiomas_estados;
CREATE TABLE dbo.idiomas_estados (
  cod_estado        INT           NOT NULL,
  nro_idioma        INT           NOT NULL,
  estado            VARCHAR(150)  NOT NULL,
  CONSTRAINT PK_idiomas_estados PRIMARY KEY (cod_estado, nro_idioma),
  CONSTRAINT FK_idiomas_estados_estado
    FOREIGN KEY (cod_estado)
    REFERENCES dbo.estados_reservas (cod_estado)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_idiomas_estados_idioma
    FOREIGN KEY (nro_idioma)
    REFERENCES dbo.idiomas (nro_idioma)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Reservas
========================================================= */

IF OBJECT_ID('dbo.reservas_restaurantes','U') IS NOT NULL DROP TABLE dbo.reservas_restaurantes;
CREATE TABLE dbo.reservas_restaurantes (
  nro_reserva          INT            IDENTITY(1,1) NOT NULL,
  nro_cliente          INT            NOT NULL,
  fecha_hora_registro  DATETIME2      NOT NULL DEFAULT SYSUTCDATETIME(),
  fecha_reserva        DATE           NOT NULL,
  nro_restaurante      INT            NOT NULL,
  nro_sucursal         INT            NOT NULL,
  cod_zona             INT            NOT NULL,
  hora_reserva         TIME(0)        NOT NULL,   -- referencia a turno.hora_desde
  cant_adultos         INT            NOT NULL,
  cant_menores         INT            NOT NULL DEFAULT(0),
  cod_estado           INT            NOT NULL,
  fecha_cancelacion    DATETIME2      NULL,
  costo_reserva        DECIMAL(10,2)  NULL,
  cod_reserva_sucursal VARCHAR(50)    NOT NULL,   -- AK
  CONSTRAINT PK_reservas_restaurantes PRIMARY KEY (nro_reserva),
  CONSTRAINT UQ_reservas_cod_sucursal UNIQUE (cod_reserva_sucursal),
  CONSTRAINT FK_reserva_cliente
    FOREIGN KEY (nro_cliente)
    REFERENCES dbo.clientes (nro_cliente)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_reserva_sucursal
    FOREIGN KEY (nro_restaurante, nro_sucursal)
    REFERENCES dbo.sucursales_restaurantes (nro_restaurante, nro_sucursal)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_reserva_zona
    FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona)
    REFERENCES dbo.zonas_sucursales_restaurantes (nro_restaurante, nro_sucursal, cod_zona)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_reserva_turno
    FOREIGN KEY (nro_restaurante, nro_sucursal, hora_reserva)
    REFERENCES dbo.turnos_sucursales_restaurantes (nro_restaurante, nro_sucursal, hora_desde)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_reserva_estado
    FOREIGN KEY (cod_estado)
    REFERENCES dbo.estados_reservas (cod_estado)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Preferencias aplicadas a una reserva (mapea valores/plantillas)
IF OBJECT_ID('dbo.preferencias_reservas_restaurantes','U') IS NOT NULL DROP TABLE dbo.preferencias_reservas_restaurantes;
CREATE TABLE dbo.preferencias_reservas_restaurantes (
  nro_cliente        INT           NOT NULL,
  nro_reserva        INT           NOT NULL,
  nro_restaurante    INT           NOT NULL,
  cod_categoria      INT           NOT NULL,
  nro_valor_dominio  INT           NOT NULL,
  nro_preferencia    INT           NULL,           -- si proviene de la tabla de preferencias del restaurante
  observaciones      VARCHAR(1000) NULL,
  CONSTRAINT PK_pref_reservas PRIMARY KEY (nro_cliente, nro_reserva, cod_categoria, nro_valor_dominio, nro_restaurante),
  CONSTRAINT FK_pref_reserva_reserva
    FOREIGN KEY (nro_reserva)
    REFERENCES dbo.reservas_restaurantes (nro_reserva)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_pref_reserva_cliente
    FOREIGN KEY (nro_cliente)
    REFERENCES dbo.clientes (nro_cliente)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_pref_reserva_dom
    FOREIGN KEY (cod_categoria, nro_valor_dominio)
    REFERENCES dbo.dominio_categorias_preferencias (cod_categoria, nro_valor_dominio)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_pref_reserva_pref_rest
    FOREIGN KEY (nro_preferencia)
    REFERENCES dbo.preferencias_restaurantes (nro_preferencia)
      ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_pref_reserva_rest
    FOREIGN KEY (nro_restaurante)
    REFERENCES dbo.restaurantes (nro_restaurante)
      ON DELETE NO ACTION ON UPDATE NO ACTION
);

 /* =========================================================
   Ventas/costos (tabla de costos genérica)
========================================================= */

IF OBJECT_ID('dbo.costos','U') IS NOT NULL DROP TABLE dbo.costos;
CREATE TABLE dbo.costos (
  tipo_costo         VARCHAR(50)   NOT NULL,
  fecha_ini_vigencia DATE          NOT NULL,
  fecha_fin_vigencia DATE          NULL,
  monto              DECIMAL(12,2) NOT NULL,
  CONSTRAINT PK_costos PRIMARY KEY (tipo_costo, fecha_ini_vigencia)
);
GO

/* =========================================================
   Índices de apoyo sugeridos (ajustables)
========================================================= */
CREATE INDEX IX_sucursales_localidad
  ON dbo.sucursales_restaurantes (nro_localidad);

CREATE INDEX IX_contenidos_vigencia
  ON dbo.contenidos_restaurantes (nro_restaurante, fecha_ini_vigencia, fecha_fin_vigencia);

CREATE INDEX IX_clicks_contenidos_ref
  ON dbo.clicks_contenidos_restaurantes (nro_restaurante, nro_idioma, nro_contenido);

CREATE INDEX IX_reservas_busquedas
  ON dbo.reservas_restaurantes (nro_restaurante, nro_sucursal, cod_zona, fecha_reserva, hora_reserva, cod_estado);

CREATE INDEX IX_turnos_sucursales_hora
  ON dbo.turnos_sucursales_restaurantes (nro_restaurante, nro_sucursal, hora_desde);
GO
