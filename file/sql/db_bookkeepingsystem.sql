/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50515
Source Host           : 127.0.0.1:3306
Source Database       : db_bookkeepingsystem

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2020-02-02 21:13:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_classification
-- ----------------------------
DROP TABLE IF EXISTS `tb_classification`;
CREATE TABLE `tb_classification` (
  `cId` int(11) NOT NULL AUTO_INCREMENT,
  `cName` varchar(20) NOT NULL,
  `cType` varchar(20) NOT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_classification
-- ----------------------------
INSERT INTO `tb_classification` VALUES ('1', '工资', '收入');
INSERT INTO `tb_classification` VALUES ('2', '补贴', '收入');
INSERT INTO `tb_classification` VALUES ('3', '奖金', '收入');
INSERT INTO `tb_classification` VALUES ('4', '饮食', '支出');
INSERT INTO `tb_classification` VALUES ('5', '服饰', '支出');
INSERT INTO `tb_classification` VALUES ('6', '交通', '支出');
INSERT INTO `tb_classification` VALUES ('7', '旅游', '支出');
INSERT INTO `tb_classification` VALUES ('8', '住宿', '支出');
INSERT INTO `tb_classification` VALUES ('9', '文娱', '支出');
INSERT INTO `tb_classification` VALUES ('10', '生活用品', '支出');
INSERT INTO `tb_classification` VALUES ('11', '亲人给予', '收入');
INSERT INTO `tb_classification` VALUES ('12', '外借', '支出');
INSERT INTO `tb_classification` VALUES ('13', '借还', '收入');
INSERT INTO `tb_classification` VALUES ('14', '饭卡', '支出');
INSERT INTO `tb_classification` VALUES ('15', '代付', '支出');
INSERT INTO `tb_classification` VALUES ('16', '付还', '收入');

-- ----------------------------
-- Table structure for tb_records
-- ----------------------------
DROP TABLE IF EXISTS `tb_records`;
CREATE TABLE `tb_records` (
  `rId` int(11) NOT NULL AUTO_INCREMENT,
  `uId` int(11) NOT NULL,
  `rType` varchar(20) NOT NULL,
  `rMoney` float NOT NULL,
  `rClassification` varchar(20) NOT NULL,
  `rMemo` varchar(3000) NOT NULL,
  `rDate` date NOT NULL,
  PRIMARY KEY (`rId`),
  KEY `uId` (`uId`),
  CONSTRAINT `tb_records_ibfk_1` FOREIGN KEY (`uId`) REFERENCES `tb_users` (`uId`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_records
-- ----------------------------
INSERT INTO `tb_records` VALUES ('1', '1', '收入', '300', '奖金', '获得奖金300元', '2019-11-12');
INSERT INTO `tb_records` VALUES ('2', '1', '支出', '300', '外借', '借给某某某300', '2019-11-13');
INSERT INTO `tb_records` VALUES ('3', '1', '支出', '11', '饮食', '食堂晚饭11', '2019-11-13');
INSERT INTO `tb_records` VALUES ('4', '1', '支出', '3', '饮食', '早饭', '2019-11-13');
INSERT INTO `tb_records` VALUES ('5', '1', '支出', '100', '饭卡', '充饭卡100', '2019-11-14');
INSERT INTO `tb_records` VALUES ('6', '1', '支出', '3', '饮食', '早晨一杯粥', '2019-11-15');
INSERT INTO `tb_records` VALUES ('7', '1', '支出', '42', '生活用品', '一瓶400毫升的洗发液', '2019-11-15');
INSERT INTO `tb_records` VALUES ('8', '1', '支出', '6.5', '饮食', '小零食', '2019-11-15');
INSERT INTO `tb_records` VALUES ('9', '1', '支出', '3.5', '饮食', '早饭，一个包子', '2019-11-16');
INSERT INTO `tb_records` VALUES ('10', '1', '支出', '15', '外借', '借给某某某15充会员', '2019-11-16');

-- ----------------------------
-- Table structure for tb_users
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
  `uId` int(11) NOT NULL AUTO_INCREMENT,
  `uName` varchar(20) NOT NULL,
  `uPassword` varchar(40) NOT NULL,
  `uImagePath` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uId`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'src/AccountSystem/images/panda.png');
INSERT INTO `tb_users` VALUES ('18', '张三', 'e10adc3949ba59abbe56e057f20f883e', 'src\\AccountSystem\\images\\panda.png');
