/*
 Navicat Premium Data Transfer

 Source Server         : My SQL
 Source Server Type    : MySQL
 Source Server Version : 100411
 Source Host           : localhost:3306
 Source Schema         : mobile

 Target Server Type    : MySQL
 Target Server Version : 100411
 File Encoding         : 65001

 Date: 14/11/2020 15:06:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tblaccount
-- ----------------------------
DROP TABLE IF EXISTS `tblaccount`;
CREATE TABLE `tblaccount`  (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fullname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblaccount
-- ----------------------------
INSERT INTO `tblaccount` VALUES ('1', '1', '1', '1');
INSERT INTO `tblaccount` VALUES ('2', '2', '2', '2');

-- ----------------------------
-- Triggers structure for table tblaccount
-- ----------------------------
DROP TRIGGER IF EXISTS `trigger_insert_tblaccount`;
delimiter ;;
CREATE TRIGGER `trigger_insert_tblaccount` BEFORE INSERT ON `tblaccount` FOR EACH ROW BEGIN
	  IF (EXISTS (SELECT * FROM tblaccount where new.username = username ) or EXISTS (SELECT * FROM tblaccount where new.email = email )) 	
					SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Not insert, username is exists';				
		end if;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
