ALTER TABLE `agroasset`  ADD COLUMN `updated_on` DATETIME NULL DEFAULT NULL;
ALTER TABLE `agroasset_extract`  ADD COLUMN `updated_on` DATETIME NULL DEFAULT NULL;
ALTER TABLE `agroasset_infuse`  ADD COLUMN `updated_on` DATETIME NULL DEFAULT NULL;
ALTER TABLE `agroasset_inspect`  ADD COLUMN `updated_on` DATETIME NULL DEFAULT NULL;