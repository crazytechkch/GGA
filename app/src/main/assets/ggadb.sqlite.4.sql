DROP TABLE IF EXISTS "hive_extract";
DROP TABLE IF EXISTS "tree_extract";
DROP TABLE IF EXISTS "agroasset_extract";
CREATE TABLE "agroasset_extract" ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `agroasset_id` int(10) NOT NULL, `volume_uom_id` int(11) DEFAULT NULL, `weight_uom_id` int(11) DEFAULT NULL, `extract_type` int(11) DEFAULT NULL, `date` int(10) DEFAULT NULL, `pod_count` int(10) DEFAULT NULL, `weight` decimal(50,10) DEFAULT NULL, `volume` decimal(50,10) DEFAULT NULL, `remark` text, FOREIGN KEY (`agroasset_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (`volume_uom_id`) REFERENCES `volume_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY (`weight_uom_id`) REFERENCES `weight_uom` (`id`) ON DELETE SET NULL ON UPDATE CASCADE );
DROP TABLE IF EXISTS "hive_inspect";
DROP TABLE IF EXISTS "tree_inspect";
DROP TABLE IF EXISTS "agroasset_inspect";
CREATE TABLE `agroasset_inspect` (  `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  `agroasset_id` int(10) NOT NULL,  `date` datetime DEFAULT NULL,  `inspect_type` int(10) DEFAULT NULL,  `actions` text,  `remark` text,  FOREIGN KEY (`agroasset_id`) REFERENCES `agroasset` (`id`) ON DELETE CASCADE ON UPDATE CASCADE);
DROP TABLE IF EXISTS "tree_infuse";
DROP TABLE IF EXISTS "agroasset_infuse";
CREATE TABLE "agroasset_infuse" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`agroasset_id`	int(10) NOT NULL,
	`infuse_presription_id`	int(10) NOT NULL,
	`date`	datetime DEFAULT NULL,
	`actions`	text,
	`remark`	text,
	FOREIGN KEY(`agroasset_id`) REFERENCES `agroasset`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(`infuse_presription_id`) REFERENCES `infuse_presription`(`id`) ON UPDATE CASCADE
);
DROP VIEW IF EXISTS `v_extract_hive`;
CREATE VIEW `v_extract_hive` AS SELECT ae.*,a.prodtype_id FROM agroasset_extract ae INNER JOIN agroasset a ON ae.agroasset_id = a.id WHERE prodtype_id = 2;
DROP VIEW IF EXISTS `v_inspect_hive`;
CREATE VIEW `v_inspect_hive` AS SELECT ae.*,a.prodtype_id FROM agroasset_inspect ae INNER JOIN agroasset a ON ae.agroasset_id = a.id WHERE prodtype_id = 2;
DROP VIEW IF EXISTS `v_infuse_tree`;
CREATE VIEW `v_infuse_tree` AS SELECT ae.*,a.prodtype_id FROM agroasset_infuse ae INNER JOIN agroasset a ON ae.agroasset_id = a.id WHERE prodtype_id = 1;
DROP VIEW IF EXISTS `v_extract_tree`;
CREATE VIEW `v_extract_tree` AS SELECT ae.*,a.prodtype_id FROM agroasset_extract ae INNER JOIN agroasset a ON ae.agroasset_id = a.id WHERE prodtype_id = 1;
DROP VIEW IF EXISTS `v_inspect_tree`;
CREATE VIEW `v_inspect_tree` AS SELECT ae.*,a.prodtype_id FROM agroasset_inspect ae INNER JOIN agroasset a ON ae.agroasset_id = a.id WHERE prodtype_id = 1;
