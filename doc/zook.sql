/*
Navicat MySQL Data Transfer

Source Server         : ccy004-root
Source Server Version : 50717
Source Host           : 39.107.126.75:3306
Source Database       : zook

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-13 23:46:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_zkconfig
-- ----------------------------
DROP TABLE IF EXISTS `t_zkconfig`;
CREATE TABLE `t_zkconfig` (
  `zk_id` varchar(200) NOT NULL,
  `zk_description` varchar(100) DEFAULT NULL,
  `zk_connect_str` varchar(100) DEFAULT NULL,
  `zk_session_time_out` varchar(100) DEFAULT NULL,
  `zk_user_name` varchar(100) DEFAULT NULL,
  `zk_user_pwd` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`zk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
