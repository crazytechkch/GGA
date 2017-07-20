CREATE TABLE agroasset (
  id int(10) NOT NULL AUTO_INCREMENT,
  farm_id int(10) NOT NULL,
  entity_status_id int(10) NOT NULL,
  code varchar(10) NOT NULL,
  dcode varchar(5) DEFAULT NULL,
  nickname varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  date datetime DEFAULT NULL,
  geo_info_id int(11) DEFAULT NULL,
  interv_extract int(10) DEFAULT NULL,
  interv_infuse int(10) DEFAULT NULL,
  interv_inspect int(10) DEFAULT NULL,
  prodtype_id int(11) NOT NULL,
  remark text CHARACTER SET latin1,
  PRIMARY KEY (id),
  CONSTRAINT code_UNIQUE UNIQUE (code),
  FOREIGN KEY (entity_status_id) REFERENCES entity_status(id) ON UPDATE CASCADE,
  FOREIGN KEY (farm_id) REFERENCES farm(id) ON UPDATE CASCADE,
  FOREIGN KEY (geo_info_id) REFERENCES geo_info(id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (prodtype_id) REFERENCES prod_type(id) ON UPDATE CASCADE
)

DROP TABLE IF EXISTS `entity_status`;
CREATE TABLE `entity_status` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(50) DEFAULT NULL,
  `status_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
LOCK TABLES `entity_status` WRITE;
INSERT INTO `entity_status` VALUES (1,'active','enity is active'),(2,'inactive','entity is inactive'),(3,'dead','entity is dead');
UNLOCK TABLES;

DROP TABLE IF EXISTS `farm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `farm` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `entity_status_id` int(10) NOT NULL,
  `code` varchar(5) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `address_1` varchar(50) DEFAULT NULL,
  `address_2` varchar(50) DEFAULT NULL,
  `address_3` varchar(50) DEFAULT NULL,
  `country_code` varchar(10) DEFAULT NULL,
  `geo_info_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `remark` text CHARACTER SET latin1,
  PRIMARY KEY (`id`),
  CONSTRAINT `code_UNIQUE` UNIQUE (`code`),
  FOREIGN KEY (`entity_status_id`) REFERENCES `entity_status` (`id`) ON UPDATE CASCADE,
  FOREIGN KEY (`geo_info_id`) REFERENCES `geo_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);
LOCK TABLES `farm` WRITE;
INSERT INTO `farm` VALUES (1,1,'AAA','Gaharu Gading Lundu',NULL,NULL,'Lundu, Sarawak, Malaysia','1',1,'2016-07-19 00:00:00',NULL);
UNLOCK TABLES;

DROP TABLE IF EXISTS `geo_info`;
CREATE TABLE `geo_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` decimal(11,8) DEFAULT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  `geo_infocol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
LOCK TABLES `geo_info` WRITE;
INSERT INTO `geo_info` VALUES (1,1.70593900,109.86384400,NULL);
UNLOCK TABLES;

DROP TABLE IF EXISTS `hive_extract`;
CREATE TABLE `hive_extract` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `hive_id` int(10) NOT NULL,
  `volume_uom_id` int(11) DEFAULT NULL,
  `weight_uom_id` int(11) DEFAULT NULL,
  `date` int(10) DEFAULT NULL,
  `extract_type` int(10) DEFAULT NULL,
  `weight` decimal(50,10) DEFAULT NULL,
  `volume` decimal(50,10) DEFAULT NULL,
  `remark` text CHARACTER SET latin1,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`hive_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`volume_uom_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`weight_uom_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);
LOCK TABLES `hive_extract` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `hive_inspect`;
CREATE TABLE `hive_inspect` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `hive_id` int(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `inspect_type` int(10) DEFAULT NULL,
  `actions` text,
  `remark` text,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`hive_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
LOCK TABLES `hive_inspect` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `infuse_presription`;
CREATE TABLE `infuse_presription` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `infuser_type_id` int(10) NOT NULL,
  `volume_uom_id` int(10) DEFAULT NULL,
  `weight_uom_id` int(10) DEFAULT NULL,
  `volume` decimal(50,10) DEFAULT NULL,
  `weight` decimal(50,10) DEFAULT NULL,
  `remark` text CHARACTER SET latin1,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`infuser_type_id`) REFERENCES `infuser` (`id`) ON UPDATE CASCADE,
  FOREIGN KEY (`volume_uom_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`weight_uom_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

LOCK TABLES `infuse_presription` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `infuser`;
CREATE TABLE `infuser` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `category` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `description` text CHARACTER SET latin1,
  `max_dose` decimal(20,4) DEFAULT NULL,
  `min_dose` decimal(20,4) DEFAULT NULL,
  `uom_minmax_weight_id` int(11) DEFAULT NULL,
  `uom_minmax_vol_id` int(11) DEFAULT NULL,
  `prodtype_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `code_UNIQUE` UNIQUE (`code`),
  FOREIGN KEY (`uom_minmax_vol_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`uom_minmax_weight_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`prodtype_id`) REFERENCES `prod_type` (`id`) ON UPDATE CASCADE
);
LOCK TABLES `infuser` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `prod_type`;
CREATE TABLE `prod_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET latin1 NOT NULL,
  `code` varchar(10) CHARACTER SET latin1 NOT NULL,
  `unit_amount` int(11) DEFAULT NULL,
  `unit_uom_weight` int(11) DEFAULT NULL,
  `unit_uom_volume` int(11) DEFAULT NULL,
  `unit_price` decimal(12,2) DEFAULT NULL,
  `unit_price_supplier` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `code_UNIQUE` UNIQUE (`code`),
  FOREIGN KEY (`unit_uom_volume`) REFERENCES `volume_uom` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (`unit_uom_weight`) REFERENCES `weight_uom` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
LOCK TABLES `prod_type` WRITE;
INSERT INTO `prod_type` VALUES (1,'Gaharu Tree','AA',NULL,NULL,NULL,NULL,NULL),(2,'Trigona Hive','BA',NULL,NULL,NULL,NULL,NULL),(3,'Gaharu Tea 30g','AB',30,1,NULL,18.00,15.00),(4,'Gaharu Tea 60g','AC',60,1,NULL,36.00,30.00),(5,'Trigona Honey 1kg','BB',1,3,NULL,150.00,120.00),(6,'Infuser','DA',NULL,NULL,NULL,NULL,NULL),(7,'Gaharu Seedling (A. malaccensis)','AD',NULL,NULL,NULL,NULL,NULL);
UNLOCK TABLES;

DROP TABLE IF EXISTS `tree_extract`;
CREATE TABLE `tree_extract` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tree_id` int(10) NOT NULL,
  `volume_uom_id` int(10) DEFAULT NULL,
  `weight_uom_id` int(10) DEFAULT NULL,
  `date` int(10) DEFAULT NULL,
  `extract_type` int(10) DEFAULT NULL,
  `weight` decimal(20,4) DEFAULT NULL,
  `volume` decimal(20,4) DEFAULT NULL,
  `remark` text CHARACTER SET latin1,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`tree_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`volume_uom_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`weight_uom_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);
LOCK TABLES `tree_extract` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `tree_infuse`;
CREATE TABLE `tree_infuse` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tree_id` int(10) NOT NULL,
  `infuse_presription_id` int(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `actions` text CHARACTER SET latin1,
  `remark` text CHARACTER SET latin1,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`infuse_presription_id`) REFERENCES `infuse_presription` (`id`) ON UPDATE CASCADE,
  FOREIGN KEY (`tree_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
LOCK TABLES `tree_infuse` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `tree_inspect`;
CREATE TABLE `tree_inspect` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tree_id` int(10) NOT NULL,
  `date` datetime DEFAULT NULL,
  `inspect_type` int(10) DEFAULT NULL,
  `actions` text CHARACTER SET latin1,
  `remark` text CHARACTER SET latin1,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`tree_id`) REFERENCES `agroasset` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
LOCK TABLES `tree_inspect` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `v_hive`;
CREATE VIEW `v_hive` AS SELECT *
 FROM agroasset
 WHERE prodtype_id = 2;

DROP TABLE IF EXISTS `v_tree`;
CREATE VIEW `v_tree` AS SELECT *
 FROM agroasset
 WHERE prodtype_id = 1;

DROP TABLE IF EXISTS `volume_uom`;
CREATE TABLE `volume_uom` (
  `id` int(11) NOT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `base_ratio` decimal(50,10) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
LOCK TABLES `volume_uom` WRITE;
INSERT INTO `volume_uom` VALUES (1,'ml','milliliters',1.0000000000),(2,'l','liters',1000.0000000000),(3,'cc','cubic centimeter',1.0000000000);
UNLOCK TABLES;

DROP TABLE IF EXISTS `weight_uom`;
CREATE TABLE `weight_uom` (
  `id` int(11) NOT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `base_ratio` decimal(50,10) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

LOCK TABLES `weight_uom` WRITE;
INSERT INTO `weight_uom` VALUES (1,'g','gram',1.0000000000),(2,'mg','milligram',0.0010000000),(3,'kg','kilogram',1000.0000000000),(4,'ton','ton',1000000.0000000000),(5,'lb','pound',0.0022046200),(6,'oz','ounce',0.0352740000);
UNLOCK TABLES;