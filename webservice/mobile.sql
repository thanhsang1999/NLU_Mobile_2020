/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 100414
 Source Host           : localhost:3306
 Source Schema         : mobile

 Target Server Type    : MySQL
 Target Server Version : 100414
 File Encoding         : 65001

 Date: 18/01/2021 21:39:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tblaccessnoteshared
-- ----------------------------
DROP TABLE IF EXISTS `tblaccessnoteshared`;
CREATE TABLE `tblaccessnoteshared`  (
  `id` int NOT NULL,
  `id_account` int NOT NULL,
  `id_noteshared` int NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `id_account`, `id_noteshared`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tblaccessnoteshared
-- ----------------------------
INSERT INTO `tblaccessnoteshared` VALUES (13, 41, 13, 'thiendaopk11@gmail.com', 'thiendaopk1@gmail.com');
INSERT INTO `tblaccessnoteshared` VALUES (14, 1, 13, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (15, 41, 14, 'thiendaopk11@gmail.com', 'thiendaopk1@gmail.com');
INSERT INTO `tblaccessnoteshared` VALUES (16, 1, 14, 'tanhoang9911.999@gmail.com', 'abc123');

-- ----------------------------
-- Table structure for tblaccount
-- ----------------------------
DROP TABLE IF EXISTS `tblaccount`;
CREATE TABLE `tblaccount`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fullname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `outside` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_outside` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dateofbirth` date NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tblaccount
-- ----------------------------
INSERT INTO `tblaccount` VALUES (1, 'abc123', '12345', 'tanhoang9911.999@gmail.com', 'aA@123', NULL, NULL, '2021-02-18', 'male');
INSERT INTO `tblaccount` VALUES (41, 'thiendaopk1@gmail.com', 'Trí Thiện1', 'thiendaopk11@gmail.com', NULL, 'facebook', '2755863414664627', '1905-02-06', 'male');
INSERT INTO `tblaccount` VALUES (44, 'hoang', 'hoang', 'hoang@gmail.com', 'aA@123', NULL, NULL, NULL, NULL);
INSERT INTO `tblaccount` VALUES (45, 'abc123464326478', 'Sang Nguyễn Huy Thành2321321', 'nguyenhuythanhsang@gmail.com3213213', 'aA@123', NULL, NULL, '1999-01-18', 'male');

-- ----------------------------
-- Table structure for tblimage
-- ----------------------------
DROP TABLE IF EXISTS `tblimage`;
CREATE TABLE `tblimage`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_account` int NOT NULL,
  `last_edit` datetime(0) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_package` int NOT NULL,
  `id_notebook` int NOT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblimage_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tblimage
-- ----------------------------

-- ----------------------------
-- Table structure for tbllogin
-- ----------------------------
DROP TABLE IF EXISTS `tbllogin`;
CREATE TABLE `tbllogin`  (
  `id` int NOT NULL,
  `id_account` int NOT NULL,
  `id_device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tbllogin
-- ----------------------------

-- ----------------------------
-- Table structure for tblnotebook
-- ----------------------------
DROP TABLE IF EXISTS `tblnotebook`;
CREATE TABLE `tblnotebook`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_account` int NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_edit` datetime(0) NULL DEFAULT NULL,
  `remind` datetime(0) NULL DEFAULT NULL,
  `id_package` int NOT NULL,
  `star` int NOT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblnotebook_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tblnotebook
-- ----------------------------
INSERT INTO `tblnotebook` VALUES (46, 1, '1', '', '2021-01-18 14:00:48', NULL, 15, 0);
INSERT INTO `tblnotebook` VALUES (48, 1, '3', '', '2021-01-18 13:53:30', NULL, 15, 1);
INSERT INTO `tblnotebook` VALUES (65, 1, 'heiejdj', '', '2021-01-18 14:00:40', NULL, 16, 1);
INSERT INTO `tblnotebook` VALUES (66, 1, 'demo', 'title demo', '2021-01-18 13:57:41', NULL, 16, 0);
INSERT INTO `tblnotebook` VALUES (67, 1, 'hình ảnh', '', '2021-01-18 13:58:09', NULL, 16, 0);
INSERT INTO `tblnotebook` VALUES (68, 1, 'hình ảnh chụp', '', '2021-01-18 13:58:34', NULL, 16, 0);
INSERT INTO `tblnotebook` VALUES (69, 1, 'lời nhắc', '', '2021-01-18 13:59:00', NULL, 16, 0);
INSERT INTO `tblnotebook` VALUES (70, 1, 'tạo note trong packed', '', '2021-01-18 14:01:18', NULL, 17, 0);
INSERT INTO `tblnotebook` VALUES (71, 41, 'test share', 'test share', '2021-01-18 14:37:03', NULL, 1, 0);

-- ----------------------------
-- Table structure for tblnoteshared
-- ----------------------------
DROP TABLE IF EXISTS `tblnoteshared`;
CREATE TABLE `tblnoteshared`  (
  `id` int NOT NULL,
  `id_account` int NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_edit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remind` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tblnoteshared
-- ----------------------------
INSERT INTO `tblnoteshared` VALUES (9, 41, '', '123', '2021-01-18 09:32:52', NULL, 'thiendaopk1@gmail.com');
INSERT INTO `tblnoteshared` VALUES (10, 41, '', '123', '2021-01-18 09:41:28', NULL, 'thiendaopk1@gmail.com');
INSERT INTO `tblnoteshared` VALUES (11, 41, '', '123', '2021-01-18 09:47:02', NULL, 'thiendaopk1@gmail.com');
INSERT INTO `tblnoteshared` VALUES (12, 41, '', '123', '2021-01-18 09:49:48', NULL, 'thiendaopk1@gmail.com');
INSERT INTO `tblnoteshared` VALUES (13, 41, '', '123', '2021-01-18 09:52:29', NULL, 'thiendaopk1@gmail.com');
INSERT INTO `tblnoteshared` VALUES (14, 41, '', '123', '2021-01-18 10:05:31', NULL, 'thiendaopk1@gmail.com');
INSERT INTO `tblnoteshared` VALUES (15, 1, '', '1', '2021-01-18 13:59:58', NULL, 'abc123');
INSERT INTO `tblnoteshared` VALUES (16, 1, '', 'bbhbbb', '2021-01-18 14:00:25', NULL, 'abc123');

-- ----------------------------
-- Table structure for tblpackage
-- ----------------------------
DROP TABLE IF EXISTS `tblpackage`;
CREATE TABLE `tblpackage`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_account` int NOT NULL,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_edit` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblpackage_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tblpackage
-- ----------------------------
INSERT INTO `tblpackage` VALUES (1, 41, 'color_blue', 'Default', '2021-01-17 13:46:38');
INSERT INTO `tblpackage` VALUES (14, 1, 'color_blue_green', '21121', '2021-01-15 10:18:18');
INSERT INTO `tblpackage` VALUES (15, 1, 'color_red', '2', '2021-01-14 17:49:01');
INSERT INTO `tblpackage` VALUES (16, 1, 'color_green', '3', '2021-01-16 10:24:49');
INSERT INTO `tblpackage` VALUES (17, 1, 'color_blue_green', 'demo packed', '2021-01-18 14:01:03');

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
