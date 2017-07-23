DROP TABLE IF EXISTS `entity_status`;
CREATE TABLE `entity_status` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  `status_name` varchar(50) DEFAULT NULL,
  `status_description` varchar(200) DEFAULT NULL
);
INSERT INTO `entity_status` VALUES (1,'active','In healthy/working condition');
INSERT INTO `entity_status` VALUES (2,'inactive','Not in use');
INSERT INTO `entity_status` VALUES (3,'untopped','The hive is not topped with extraction box');
INSERT INTO `entity_status` VALUES (4,'emptied (natural)','The hive is emptied naturally');
INSERT INTO `entity_status` VALUES (5,'emptied (induced)','The hive is emptied through human activity');
INSERT INTO `entity_status` VALUES (6,'disposed','The asset is no longer available');