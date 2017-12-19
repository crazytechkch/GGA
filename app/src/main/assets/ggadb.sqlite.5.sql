DROP TABLE IF EXISTS "agrolog";
CREATE TABLE "agrolog" (
    `id`, INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    `agroasset_id` int(10) NOT NULL,
    `datetime` datetime DEFAULT NULL,
    `remark` text,
    FOREIGN KEY(`agroasset_id`) REFERENCES `agroasset`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
)