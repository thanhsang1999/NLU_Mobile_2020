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

 Date: 14/01/2021 18:41:48
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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblpackage
-- ----------------------------
INSERT INTO `tblpackage` VALUES (2, 1, 'color_red', 'asd', '2021-01-14 17:55:39');
INSERT INTO `tblpackage` VALUES (3, 1, 'color_blue_green', '2', '2021-01-14 18:34:09');
INSERT INTO `tblpackage` VALUES (4, 1, 'color_green', '3', '2021-01-14 18:39:44');

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
