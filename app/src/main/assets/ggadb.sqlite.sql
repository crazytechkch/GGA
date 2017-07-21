DROP TABLE IF EXISTS `agroasset`;
CREATE TABLE agroasset (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  farm_id int(10) NOT NULL,
  entity_status_id int(10) NOT NULL,
  code varchar(10) NOT NULL,
  dcode varchar(5) DEFAULT NULL,
  nickname varchar(50) DEFAULT NULL,
  date datetime DEFAULT NULL,
  geo_info_id int(11) DEFAULT NULL,
  interv_extract int(10) DEFAULT NULL,
  interv_infuse int(10) DEFAULT NULL,
  interv_inspect int(10) DEFAULT NULL,
  prodtype_id int(11) NOT NULL,
  remark text,
  CONSTRAINT code_UNIQUE UNIQUE (code),
  FOREIGN KEY (entity_status_id) REFERENCES entity_status(id) ON UPDATE CASCADE,
  FOREIGN KEY (farm_id) REFERENCES farm(id) ON UPDATE CASCADE,
  FOREIGN KEY (geo_info_id) REFERENCES geo_info(id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (prodtype_id) REFERENCES prod_type(id) ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `entity_status`;
CREATE TABLE `entity_status` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `status_name` varchar(50) DEFAULT NULL,
  `status_description` varchar(200) DEFAULT NULL
);
INSERT INTO `entity_status` VALUES (1,'active',NULL),(2,'inactive',NULL),(3,'empty',NULL),(4,'disposed',NULL);

DROP TABLE IF EXISTS `farm`;
CREATE TABLE `farm` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `entity_status_id` int(10) NOT NULL,
  `code` varchar(5) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `address_1` varchar(50) DEFAULT NULL,
  `address_2` varchar(50) DEFAULT NULL,
  `address_3` varchar(50) DEFAULT NULL,
  `country_code` varchar(10) DEFAULT NULL,
  `geo_info_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `remark` text,
  CONSTRAINT `code_UNIQUE` UNIQUE (`code`),
  FOREIGN KEY (`entity_status_id`) REFERENCES `entity_status` (`id`) ON UPDATE CASCADE,
  FOREIGN KEY (`geo_info_id`) REFERENCES `geo_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);
INSERT INTO `farm` VALUES (1,1,'AAA','Gaharu Gading Lundu',NULL,NULL,'Lundu, Sarawak, Malaysia','1',1,'2016-07-19 00:00:00',NULL);

DROP TABLE IF EXISTS `geo_info`;
CREATE TABLE `geo_info` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `latitude` decimal(11,8) DEFAULT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  `geo_infocol` varchar(45) DEFAULT NULL
);
INSERT INTO `geo_info` VALUES (1,1.70593900,109.86384400,NULL);

DROP TABLE IF EXISTS `hive_extract`;
CREATE TABLE `hive_extract` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `hive_id` int(10) NOT NULL,
  `volume_uom_id` int(11) DEFAULT NULL,
  `weight_uom_id` int(11) DEFAULT NULL,
  `date` int(10) DEFAULT NULL,
  `extract_type` int(10) DEFAULT NULL,
  `weight` decimal(50,10) DEFAULT NULL,
  `volume` decimal(50,10) DEFAULT NULL,
  `remark` text,
  FOREIGN KEY (`hive_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`volume_uom_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`weight_uom_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `hive_inspect`;
CREATE TABLE `hive_inspect` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `hive_id` int(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `inspect_type` int(10) DEFAULT NULL,
  `actions` text,
  `remark` text,
  FOREIGN KEY (`hive_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `infuse_presription`;
CREATE TABLE `infuse_presription` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `infuser_type_id` int(10) NOT NULL,
  `volume_uom_id` int(10) DEFAULT NULL,
  `weight_uom_id` int(10) DEFAULT NULL,
  `volume` decimal(50,10) DEFAULT NULL,
  `weight` decimal(50,10) DEFAULT NULL,
  `remark` text,
  FOREIGN KEY (`infuser_type_id`) REFERENCES `infuser` (`id`) ON UPDATE CASCADE,
  FOREIGN KEY (`volume_uom_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`weight_uom_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `infuser`;
CREATE TABLE `infuser` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `category` varchar(200) DEFAULT NULL,
  `description` text,
  `max_dose` decimal(20,4) DEFAULT NULL,
  `min_dose` decimal(20,4) DEFAULT NULL,
  `uom_minmax_weight_id` int(11) DEFAULT NULL,
  `uom_minmax_vol_id` int(11) DEFAULT NULL,
  `prodtype_id` int(11) NOT NULL,
  CONSTRAINT `code_UNIQUE` UNIQUE (`code`),
  FOREIGN KEY (`uom_minmax_vol_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`uom_minmax_weight_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`prodtype_id`) REFERENCES `prod_type` (`id`) ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `prod_type`;
CREATE TABLE `prod_type` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `name` varchar(200) NOT NULL,
  `code` varchar(10) NOT NULL,
  `unit_amount` int(11) DEFAULT NULL,
  `unit_uom_weight` int(11) DEFAULT NULL,
  `unit_uom_volume` int(11) DEFAULT NULL,
  `unit_price` decimal(12,2) DEFAULT NULL,
  `unit_price_supplier` decimal(12,2) DEFAULT NULL,
  CONSTRAINT `code_UNIQUE` UNIQUE (`code`),
  FOREIGN KEY (`unit_uom_volume`) REFERENCES `volume_uom` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (`unit_uom_weight`) REFERENCES `weight_uom` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
INSERT INTO `prod_type` VALUES (1,'Gaharu Tree','AA',NULL,NULL,NULL,NULL,NULL),(2,'Trigona Hive','BA',NULL,NULL,NULL,NULL,NULL),(3,'Gaharu Tea 30g','AB',30,1,NULL,18.00,15.00),(4,'Gaharu Tea 60g','AC',60,1,NULL,36.00,30.00),(5,'Trigona Honey 1kg','BB',1,3,NULL,150.00,120.00),(6,'Infuser','DA',NULL,NULL,NULL,NULL,NULL),(7,'Gaharu Seedling (A. malaccensis)','AD',NULL,NULL,NULL,NULL,NULL);

DROP TABLE IF EXISTS `tree_extract`;
CREATE TABLE `tree_extract` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `tree_id` int(10) NOT NULL,
  `volume_uom_id` int(10) DEFAULT NULL,
  `weight_uom_id` int(10) DEFAULT NULL,
  `date` int(10) DEFAULT NULL,
  `extract_type` int(10) DEFAULT NULL,
  `weight` decimal(20,4) DEFAULT NULL,
  `volume` decimal(20,4) DEFAULT NULL,
  `remark` text,
  FOREIGN KEY (`tree_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`volume_uom_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`weight_uom_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `tree_infuse`;
CREATE TABLE `tree_infuse` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `tree_id` int(10) NOT NULL,
  `infuse_presription_id` int(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `actions` text,
  `remark` text,
  FOREIGN KEY (`infuse_presription_id`) REFERENCES `infuse_presription` (`id`) ON UPDATE CASCADE,
  FOREIGN KEY (`tree_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `tree_inspect`;
CREATE TABLE `tree_inspect` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `tree_id` int(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `inspect_type` int(10) DEFAULT NULL,
  `actions` text,
  `remark` text,
  FOREIGN KEY (`tree_id`) REFERENCES `agroasset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP VIEW IF EXISTS `v_hive`;
CREATE VIEW `v_hive` AS SELECT *
 FROM agroasset
 WHERE prodtype_id = 2;

DROP VIEW IF EXISTS `v_tree`;
CREATE VIEW `v_tree` AS SELECT *
 FROM agroasset
 WHERE prodtype_id = 1;

DROP TABLE IF EXISTS `volume_uom`;
CREATE TABLE `volume_uom` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `unit` varchar(10) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `base_ratio` decimal(50,10) DEFAULT NULL
);
INSERT INTO `volume_uom` VALUES (1,'ml','milliliters',1.0000000000),(2,'l','liters',1000.0000000000),(3,'cc','cubic centimeter',1.0000000000);

DROP TABLE IF EXISTS `weight_uom`;
CREATE TABLE `weight_uom` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `unit` varchar(10) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `base_ratio` decimal(50,10) DEFAULT NULL
);
INSERT INTO `weight_uom` VALUES (1,'g','gram',1.0000000000),(2,'mg','milligram',0.0010000000),(3,'kg','kilogram',1000.0000000000),(4,'ton','ton',1000000.0000000000),(5,'lb','pound',0.0022046200),(6,'oz','ounce',0.0352740000);
