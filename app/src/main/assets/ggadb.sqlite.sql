CREATE TABLE entity_status (
  id INTEGER UNSIGNED NOT NULL,
  status_name VARCHAR(50) NULL,
  status_description VARCHAR(200) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE farm (
  id INTEGER UNSIGNED NOT NULL,
  entity_status_id INTEGER UNSIGNED NOT NULL,
  farmname VARCHAR(50) NULL,
  address_1 VARCHAR(50) NULL,
  address_2 VARCHAR(50) NULL,
  address_3 VARCHAR(50) NULL,
  country_code VARCHAR(10) NULL,
  region_code VARCHAR(10) NULL,
  geo_id INTEGER UNSIGNED NULL,
  geo_lat DECIMAL(10,10) NULL,
  geo_long DECIMAL(10,10) NULL,
  date DATETIME NULL,
  remark TEXT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE hive (
  id INTEGER UNSIGNED NOT NULL,
  entity_status_id INTEGER UNSIGNED NOT NULL,
  farm_id INTEGER UNSIGNED NOT NULL,
  nickname VARCHAR(50) NULL,
  date DATETIME NULL,
  geo_id INTEGER UNSIGNED NULL,
  geo_lat DECIMAL(10,10) NULL,
  geo_long DECIMAL(10,10) NULL,
  geo_col VARCHAR(20) NULL,
  geo_row VARCHAR(20) NULL,
  interv_extract INTEGER UNSIGNED NULL,
  interv_inspect INTEGER UNSIGNED NULL,
  remark TEXT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE hive_extract (
  id INTEGER UNSIGNED NOT NULL,
  hive_id INTEGER UNSIGNED NOT NULL,
  volume_uom_id INTEGER UNSIGNED NULL,
  weight_uom_id INTEGER UNSIGNED NULL,
  date INTEGER UNSIGNED NULL,
  extract_type INTEGER UNSIGNED NULL,
  weight DECIMAL(50,10) NULL,
  volume DECIMAL(50,10) NULL,
  remark TEXT NULL,
  PRIMARY KEY(id, hive_id)
);

CREATE TABLE hive_inspect (
  id INTEGER UNSIGNED NOT NULL,
  hive_id INTEGER UNSIGNED NOT NULL,
  date DATETIME NULL,
  inspect_type INTEGER UNSIGNED NULL,
  actions TEXT NULL,
  remark TEXT NULL,
  PRIMARY KEY(id, hive_id)
);

CREATE TABLE infuser_type (
  id INTEGER UNSIGNED NOT NULL,
  name VARCHAR(200) NULL,
  date DATETIME NULL,
  volume DECIMAL(50,10) NULL,
  weight DECIMAL(50,10) NULL,
  category VARCHAR(200) NULL,
  description TEXT NULL,
  max_dose DECIMAL(50,10) NULL,
  min_dose DECIMAL(50,10) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE infuse_presription (
  id INTEGER UNSIGNED NOT NULL,
  infuser_type_id INTEGER UNSIGNED NOT NULL,
  volume_uom_id INTEGER UNSIGNED NOT NULL,
  weight_uom_id INTEGER UNSIGNED NOT NULL,
  volume DECIMAL(50,10) NULL,
  weight DECIMAL(50,10) NULL,
  remark TEXT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE tree (
  id INTEGER UNSIGNED NOT NULL,
  farm_id INTEGER UNSIGNED NOT NULL,
  entity_status_id INTEGER UNSIGNED NOT NULL,
  nickname VARCHAR(50) NULL,
  date DATETIME NULL,
  geo_id INTEGER UNSIGNED NULL,
  geo_lat DECIMAL(10,10) NULL,
  geo_long DECIMAL(10,10) NULL,
  geo_col VARCHAR(20) NULL,
  geo_row VARCHAR(20) NULL,
  interv_extract INTEGER UNSIGNED NULL,
  interv_inspect INTEGER UNSIGNED NULL,
  remark TEXT NULL,
  interv_infuse INTEGER UNSIGNED NULL,
  PRIMARY KEY(id)
);

CREATE TABLE tree_extract (
  id INTEGER UNSIGNED NOT NULL,
  tree_id INTEGER UNSIGNED NOT NULL,
  volume_uom_id INTEGER UNSIGNED NOT NULL,
  weight_uom_id INTEGER UNSIGNED NOT NULL,
  date INTEGER UNSIGNED NULL,
  extract_type INTEGER UNSIGNED NULL,
  weight DECIMAL(50,10) NULL,
  volume DECIMAL(50,10) NULL,
  remark TEXT NULL,
  PRIMARY KEY(id, tree_id)
);

CREATE TABLE tree_infuse (
  id INTEGER UNSIGNED NOT NULL,
  tree_id INTEGER UNSIGNED NOT NULL,
  infuse_presription_id INTEGER UNSIGNED NOT NULL,
  date DATETIME NULL,
  actions TEXT NULL,
  remark TEXT NULL,
  PRIMARY KEY(id, tree_id, infuse_presription_id)
);

CREATE TABLE tree_inspect (
  id INTEGER UNSIGNED NOT NULL,
  tree_id INTEGER UNSIGNED NOT NULL,
  date DATETIME NULL,
  inspect_type INTEGER UNSIGNED NULL,
  actions TEXT NULL,
  remark TEXT NULL,
  PRIMARY KEY(id, tree_id)
);

CREATE TABLE volume_uom (
  id INTEGER UNSIGNED NOT NULL,
  unit VARCHAR(10) NULL,
  name VARCHAR(100) NULL,
  base_ratio DECIMAL(50,10) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE weight_uom (
  id INTEGER UNSIGNED NOT NULL,
  unit VARCHAR(10) NULL,
  name VARCHAR(100) NULL,
  base_ratio DECIMAL(50,10) NULL,
  PRIMARY KEY(id)
);

INSERT INTO `entity_status` VALUES (1,'active','enity is active'),(2,'inactive','entity is inactive'),(3,'dead','entity is dead');
INSERT INTO `farm` VALUES (1,1,'Gaharu Gading Lundu',NULL,NULL,'Lundu, Sarawak, Malaysia','1','1',1,NULL,NULL,'2016-07-19 00:00:00',NULL);
INSERT INTO `volume_uom` VALUES (1,'ml','milliliters',1),(2,'l','liters',1000),(3,'cc','cubic centimeter',1);
INSERT INTO `weight_uom` VALUES (1,'g','gram',1),(2,'mg','milligram',0.001),(3,'kg','kilogram',1000),(4,'ton','ton',1000000),(5,'lb','pound',0.00220462),(6,'oz','ounce',0.035274);


