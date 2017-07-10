/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : mybatis_test

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2016-12-16 11:01:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `password` varchar(128) NOT NULL,
  `address` varchar(32) NOT NULL,
  `phone` varchar(16) NOT NULL,
  `company` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('3', 'Jerry', 'Jerry', 'BeiJing', '119', 'AGM');
INSERT INTO `person` VALUES ('4', 'te', 'tes', 'BeiJing', '1778', 'MyWif');
INSERT INTO `person` VALUES ('5', 'Test0', 'PWD0', 'Address0', '1778j0', 'Alpha Go 0');
INSERT INTO `person` VALUES ('6', 'Test1', 'PWD1', 'Address1', '1778j1', 'Alpha Go 1');
INSERT INTO `person` VALUES ('7', 'Test0', 'PWD0', 'Address0', '1778j0', 'Alpha Go 0');
INSERT INTO `person` VALUES ('8', 'Test1', 'PWD1', 'Address1', '1778j1', 'Alpha Go 1');
INSERT INTO `person` VALUES ('9', 'Test0', 'PWD0', 'Address0', '1778j0', 'Alpha Go 0');
INSERT INTO `person` VALUES ('10', 'Test1', 'PWD1', 'Address1', '1778j1', 'Alpha Go 1');
INSERT INTO `person` VALUES ('11', 'Test0', 'PWD0', 'Address0', '1778j0', 'Alpha Go 0');
INSERT INTO `person` VALUES ('12', 'Test1', 'PWD1', 'Address1', '1778j1', 'Alpha Go 1');
INSERT INTO `person` VALUES ('13', 'Test0', 'PWD0', 'Address0', '1778j0', 'Alpha Go 0');
INSERT INTO `person` VALUES ('14', 'Test1', 'PWD1', 'Address1', '1778j1', 'Alpha Go 1');
