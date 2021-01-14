/*
 Navicat Premium Data Transfer

 Source Server         : My SQL
 Source Server Type    : MySQL
 Source Server Version : 100408
 Source Host           : localhost:3306
 Source Schema         : mobile

 Target Server Type    : MySQL
 Target Server Version : 100408
 File Encoding         : 65001

 Date: 15/01/2021 02:11:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tblaccount
-- ----------------------------
DROP TABLE IF EXISTS `tblaccount`;
CREATE TABLE `tblaccount`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fullname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblaccount
-- ----------------------------
INSERT INTO `tblaccount` VALUES (1, 'abc123', '123', 'tanhoang99.999@gmail.com', 'aA@123');

-- ----------------------------
-- Table structure for tblimage
-- ----------------------------
DROP TABLE IF EXISTS `tblimage`;
CREATE TABLE `tblimage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_account` int(11) NOT NULL,
  `last_edit` datetime(0) NULL DEFAULT NULL,
  `image` mediumblob NULL,
  `id_package` int(11) NOT NULL,
  `id_notebook` int(11) NOT NULL,
  PRIMARY KEY (`id`, `id_package`, `id_notebook`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblimage_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tblnotebook
-- ----------------------------
DROP TABLE IF EXISTS `tblnotebook`;
CREATE TABLE `tblnotebook`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_account` int(11) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_edit` datetime(0) NULL DEFAULT NULL,
  `remind` datetime(0) NULL DEFAULT NULL,
  `id_package` int(11) NOT NULL,
  PRIMARY KEY (`id`, `id_account`, `id_package`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblnotebook_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblnotebook
-- ----------------------------
INSERT INTO `tblnotebook` VALUES (46, 1, '1', '', '2021-01-14 18:12:49', NULL, 15);
INSERT INTO `tblnotebook` VALUES (47, 1, '2', '', '2021-01-14 18:15:11', NULL, 15);
INSERT INTO `tblnotebook` VALUES (48, 1, '3', '', '2021-01-14 18:16:04', NULL, 15);
INSERT INTO `tblnotebook` VALUES (52, 1, '4', '', '2021-01-14 18:19:18', NULL, 15);
INSERT INTO `tblnotebook` VALUES (53, 1, '1', '', '2021-01-14 18:19:27', NULL, 14);

-- ----------------------------
-- Table structure for tblpackage
-- ----------------------------
DROP TABLE IF EXISTS `tblpackage`;
CREATE TABLE `tblpackage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_account` int(11) NOT NULL,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_edit` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblpackage_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblpackage
-- ----------------------------
INSERT INTO `tblpackage` VALUES (14, 1, 'color_blue_green', '1', '2021-01-14 15:31:26');
INSERT INTO `tblpackage` VALUES (15, 1, 'color_red', '2', '2021-01-14 17:49:01');

-- ----------------------------
-- Triggers structure for table tblaccount
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_insert_tblaccount`;
delimiter ;;
CREATE TRIGGER `trigger_insert_tblaccount` BEFORE INSERT ON `tblaccount` FOR EACH ROW BEGIN 
	  IF ((EXISTS (SELECT * FROM tblaccount where new.username = username )) or (EXISTS (SELECT * FROM tblaccount where new.email = email ))) 	THEN
					SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Not insert, username is exists';				
		end if;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
