/*
Navicat MySQL Data Transfer

Source Server         : ccy001-root
Source Server Version : 50717
Source Host           : 39.108.0.229:3306
Source Database       : zook

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-28 14:44:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `AID` varchar(200) NOT NULL,
  `ADMINNAME` varchar(100) DEFAULT NULL,
  `ADMINPWD` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`AID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_zkconfig
-- ----------------------------
DROP TABLE IF EXISTS `t_zkconfig`;
CREATE TABLE `t_zkconfig` (
  `ZKID` varchar(200) NOT NULL,
  `ZKDESCRIPTION` varchar(100) DEFAULT NULL,
  `ZKCONNECTSTR` varchar(100) DEFAULT NULL,
  `ZKSESSIONTIMEOUT` varchar(100) DEFAULT NULL,
  `ZKUSERNAME` varchar(100) DEFAULT NULL,
  `ZKUSERPWD` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ZKID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
