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

 Date: 19/01/2021 01:02:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tblaccessnoteshared
-- ----------------------------
DROP TABLE IF EXISTS `tblaccessnoteshared`;
CREATE TABLE `tblaccessnoteshared`  (
  `id` int(11) NOT NULL,
  `id_account` int(11) NOT NULL,
  `id_noteshared` int(11) NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `id_account`, `id_noteshared`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblaccessnoteshared
-- ----------------------------
INSERT INTO `tblaccessnoteshared` VALUES (13, 41, 13, 'thiendaopk11@gmail.com', 'thiendaopk1@gmail.com');
INSERT INTO `tblaccessnoteshared` VALUES (14, 1, 13, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (15, 41, 14, 'thiendaopk11@gmail.com', 'thiendaopk1@gmail.com');
INSERT INTO `tblaccessnoteshared` VALUES (16, 1, 14, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (19, 1, 16, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (20, 44, 16, 'hoang@gmail.com', 'hoang');
INSERT INTO `tblaccessnoteshared` VALUES (21, 1, 17, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (22, 44, 17, 'hoang@gmail.com', 'hoang');
INSERT INTO `tblaccessnoteshared` VALUES (23, 44, 18, 'hoang@gmail.com', 'hoang');
INSERT INTO `tblaccessnoteshared` VALUES (24, 1, 18, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (25, 1, 19, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (26, 44, 19, 'hoang@gmail.com', 'hoang');
INSERT INTO `tblaccessnoteshared` VALUES (27, 1, 20, 'tanhoang9911.999@gmail.com', 'abc123');
INSERT INTO `tblaccessnoteshared` VALUES (28, 44, 20, 'hoang@gmail.com', 'hoang');

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
  `outside` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_outside` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dateofbirth` date NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblaccount
-- ----------------------------
INSERT INTO `tblaccount` VALUES (1, 'abc123', '12345', 'tanhoang9911.999@gmail.com', 'aA@123', NULL, NULL, '2021-02-18', 'male');
INSERT INTO `tblaccount` VALUES (41, 'thiendaopk1@gmail.com', 'Trí Thiện1', 'thiendaopk11@gmail.com', NULL, 'facebook', '2755863414664627', '1905-02-06', 'male');
INSERT INTO `tblaccount` VALUES (44, 'hoang', 'hoang', 'hoang@gmail.com', 'aA@123', NULL, NULL, NULL, NULL);
INSERT INTO `tblaccount` VALUES (45, 'abc123464326478', 'Sang Nguyễn Huy Thành2321321', 'nguyenhuythanhsang@gmail.com3213213', 'aA@123', NULL, NULL, '1999-01-18', 'male');
INSERT INTO `tblaccount` VALUES (46, 'hoang1', 'hoang1', 'hoang1@gmail.com', 'aA@123', NULL, NULL, NULL, NULL);
INSERT INTO `tblaccount` VALUES (47, 'hoang2', 'hoang2', 'hoang2@gmail.com', 'aA@123', NULL, NULL, NULL, NULL);
INSERT INTO `tblaccount` VALUES (49, 'hoang22', 'hoang', 'hoang12@gmail.com', 'aA@123', NULL, NULL, NULL, NULL);
INSERT INTO `tblaccount` VALUES (50, 'hoang3', 'hoang', 'hoang3@gmail.com', 'aA@123', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tblimage
-- ----------------------------
DROP TABLE IF EXISTS `tblimage`;
CREATE TABLE `tblimage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_account` int(11) NOT NULL,
  `last_edit` datetime(0) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_package` int(11) NOT NULL,
  `id_notebook` int(11) NOT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblimage_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbllogin
-- ----------------------------
DROP TABLE IF EXISTS `tbllogin`;
CREATE TABLE `tbllogin`  (
  `id` int(11) NOT NULL,
  `id_account` int(11) NOT NULL,
  `id_device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `star` int(11) NOT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblnotebook_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 81 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblnotebook
-- ----------------------------
INSERT INTO `tblnotebook` VALUES (71, 41, 'test share', 'test share', '2021-01-18 14:37:03', NULL, 1, 0);

-- ----------------------------
-- Table structure for tblnoteshared
-- ----------------------------
DROP TABLE IF EXISTS `tblnoteshared`;
CREATE TABLE `tblnoteshared`  (
  `id` int(11) NOT NULL,
  `id_account` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_edit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remind` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `id_account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `tblnoteshared` VALUES (17, 1, '', '1234', '2021-01-18 16:18:34', NULL, 'abc123');
INSERT INTO `tblnoteshared` VALUES (18, 44, '', '12313', '2021-01-18 17:29:03', NULL, 'hoang');
INSERT INTO `tblnoteshared` VALUES (19, 1, '', '123', '2021-01-18 17:31:57', NULL, 'abc123');
INSERT INTO `tblnoteshared` VALUES (20, 1, '', 'shared', '2021-01-18 17:32:28', NULL, 'abc123');

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
  PRIMARY KEY (`id`, `id_account`) USING BTREE,
  INDEX `id_account`(`id_account`) USING BTREE,
  CONSTRAINT `tblpackage_ibfk_1` FOREIGN KEY (`id_account`) REFERENCES `tblaccount` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tblpackage
-- ----------------------------
INSERT INTO `tblpackage` VALUES (1, 41, 'color_blue', 'Default', '2021-01-17 13:46:38');
INSERT INTO `tblpackage` VALUES (1, 44, 'color_blue', 'Default', '2021-01-18 17:28:54');
INSERT INTO `tblpackage` VALUES (14, 1, 'color_blue_green', '21121', '2021-01-15 10:18:18');
INSERT INTO `tblpackage` VALUES (15, 1, 'color_red', '2', '2021-01-14 17:49:01');
INSERT INTO `tblpackage` VALUES (16, 1, 'color_green', '3', '2021-01-16 10:24:49');
INSERT INTO `tblpackage` VALUES (17, 1, 'color_blue_green', 'demo packed', '2021-01-18 14:01:03');

SET FOREIGN_KEY_CHECKS = 1;
