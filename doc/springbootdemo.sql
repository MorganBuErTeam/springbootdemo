/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : springbootdemo

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-01-02 22:35:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `cid` int(10) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `pid` int(10) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '北京市', '1');
INSERT INTO `city` VALUES ('2', '天津市', '2');
INSERT INTO `city` VALUES ('3', '上海市', '3');
INSERT INTO `city` VALUES ('5', '洛阳市', '4');

-- ----------------------------
-- Table structure for t_materiel
-- ----------------------------
DROP TABLE IF EXISTS `t_materiel`;
CREATE TABLE `t_materiel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '条码',
  `area_type` varchar(11) DEFAULT NULL,
  `materiel_no` text COMMENT '物料号',
  `materiel_name` varchar(50) DEFAULT NULL COMMENT '物料名',
  `materiel_names` varchar(50) DEFAULT NULL COMMENT '物料全名',
  `model` text COMMENT '规格型号',
  `attribute` varchar(50) DEFAULT NULL COMMENT '物料属性(自制件/外购件)',
  `measm_sheet` varchar(50) DEFAULT NULL COMMENT '基本计量单位(pcs)',
  `bin_location` varchar(50) DEFAULT NULL COMMENT '储位',
  `default_warehouse` varchar(50) DEFAULT NULL COMMENT '默认仓库',
  `fullbox_count` varchar(11) DEFAULT NULL COMMENT '满箱数量',
  `weight` varchar(11) DEFAULT NULL COMMENT '重量 KG',
  `frequency` varchar(11) DEFAULT NULL COMMENT '频次',
  `attrition_rate` text COMMENT '损耗率',
  `safety_number` int(11) DEFAULT NULL COMMENT '库存安全数',
  `state` int(11) DEFAULT NULL COMMENT '状态:1.正常2下架',
  `create_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `memo` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_materiel
-- ----------------------------

-- ----------------------------
-- Table structure for t_mes_info
-- ----------------------------
DROP TABLE IF EXISTS `t_mes_info`;
CREATE TABLE `t_mes_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL COMMENT 'json表里的task_id',
  `code` varchar(30) DEFAULT NULL COMMENT '编号(字母+数)',
  `request_Time` datetime DEFAULT NULL,
  `takeGood_Code` varchar(20) DEFAULT NULL COMMENT '取货编号',
  `discharging_Code` varchar(20) DEFAULT NULL COMMENT '送货编号',
  `task_type` varchar(200) DEFAULT NULL COMMENT '任务类型(01-上料,02-下料)',
  `json_message_info` varchar(200) DEFAULT NULL COMMENT 'json文件',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(01-处理成功，02-处理失败)',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `create_date` datetime DEFAULT NULL COMMENT '新增时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=77683 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mes_info
-- ----------------------------
INSERT INTO `t_mes_info` VALUES ('75827', '835', 'mes1565832178000', '2019-08-15 09:22:58', 'shykjsjrSL01', 'shykjsjrJC01', '01', '{\"code\":200,\"message\":\"OK\",\"results\":{\"taskId\":835,\"taskType\":\"01\",\"takeGoodCode\":\"shykjsjrSL01\",\"dischargingCode\":\"shykjsjrJC01\",\"requestTime\":\"2019-08-15T09:22:58.3197247+08:00\"}}', '01', null, '2019-08-15 09:23:43');
INSERT INTO `t_mes_info` VALUES ('75828', '0', 'mes20190815092343853', null, null, null, '请求mes,shykjsjrSL01,机构是否运动到位', '\"{\\\"code\\\":200,\\\"message\\\":\\\"OK\\\",\\\"results\\\":\\\"OK可以继续动作\\\"}\"', null, null, '2019-08-15 09:24:56');
INSERT INTO `t_mes_info` VALUES ('75829', '0', null, null, null, null, '离开料架位请求mes的参数是:shykjsjrSL01', '\"{\\\"code\\\":200,\\\"message\\\":\\\"OK\\\",\\\"results\\\":\\\"料架运动请求成功！\\\"}\"', null, null, '2019-08-15 09:25:46');
INSERT INTO `t_mes_info` VALUES ('75830', '0', 'mes20190815092343853', null, null, null, '请求mes,shykjsjrJC01,机构是否运动到位', '\"{\\\"code\\\":200,\\\"message\\\":\\\"OK\\\",\\\"results\\\":\\\"OK可以继续动作\\\"}\"', null, null, '2019-08-15 09:26:38');
INSERT INTO `t_mes_info` VALUES ('75831', '0', null, null, null, null, '机床位上料离开,回复mes的taskid是:835', 'mes返回data:\"{\\\"code\\\":200,\\\"message\\\":\\\"OK\\\",\\\"results\\\":\\\"完成任务成功\\\"}\"', null, null, '2019-08-15 09:27:39');
INSERT INTO `t_mes_info` VALUES ('75832', '836', 'mes1565834033000', '2019-08-15 09:53:53', 'shykjsjrSL03', 'shykjsjrJC02', '01', '{\"code\":200,\"message\":\"OK\",\"results\":{\"taskId\":836,\"taskType\":\"01\",\"takeGoodCode\":\"shykjsjrSL03\",\"dischargingCode\":\"shykjsjrJC02\",\"requestTime\":\"2019-08-15T09:53:53.5841171+08:00\"}}', '01', null, '2019-08-15 09:54:39');

-- ----------------------------
-- Table structure for t_subtask
-- ----------------------------
DROP TABLE IF EXISTS `t_subtask`;
CREATE TABLE `t_subtask` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(16) DEFAULT NULL COMMENT '大任务编号',
  `agv_code` varchar(16) DEFAULT NULL COMMENT '小车编号',
  `rout_code` varchar(255) DEFAULT NULL COMMENT '关联路径',
  `start_sit_code` int(16) DEFAULT NULL COMMENT '起始点',
  `pick_sit_code` int(16) DEFAULT NULL COMMENT '取货点',
  `put_sit_code` int(16) DEFAULT NULL COMMENT '放货点',
  `state` int(15) DEFAULT NULL COMMENT '任务状态: 1:未执行  2:执行中  3:完成 4.删除(非物理) 5:强制完成',
  `taskInfo` varchar(16) DEFAULT NULL COMMENT '任务信息',
  `remark` varchar(16) DEFAULT NULL COMMENT '备注',
  `type` varchar(16) DEFAULT NULL COMMENT '任务类型',
  `create_date` datetime DEFAULT NULL COMMENT '产生时间',
  `real_start_date` datetime DEFAULT NULL COMMENT '响应时间',
  `finish_date` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=494 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='子任务信息表';

-- ----------------------------
-- Records of t_subtask
-- ----------------------------
INSERT INTO `t_subtask` VALUES ('489', '23441', '1', null, null, '1', '1', '5', null, null, '废料桶搬运', '2019-12-06 16:18:53', null, null);
INSERT INTO `t_subtask` VALUES ('490', '23441', '2', null, null, '1', '1', '3', null, null, '废料桶搬运', '2019-12-06 16:18:56', null, null);
INSERT INTO `t_subtask` VALUES ('491', '23441', '3', null, null, '1', '1', '4', null, null, '内部转运', '2019-12-06 16:19:00', null, null);
INSERT INTO `t_subtask` VALUES ('492', '23441', '4', null, null, '1', '1', '3', null, null, '内部转运', '2019-12-06 16:19:04', null, null);
INSERT INTO `t_subtask` VALUES ('493', '23441', '5', null, null, '1', '1', '3', null, null, '内部转运', '2019-12-06 16:19:07', null, null);

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agvCode` int(11) DEFAULT NULL,
  `taskName` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `state` int(11) DEFAULT NULL COMMENT '任务状态: 1:未执行  2:执行中  3:完成 4.删除(非物理)',
  `taskType` varchar(11) DEFAULT NULL COMMENT '任务类型',
  `create_date` datetime DEFAULT NULL COMMENT '产生时间',
  `real_start_date` datetime DEFAULT NULL COMMENT '实际发车时间/响应时间',
  `finish_date` datetime DEFAULT NULL COMMENT '完成时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '任务信息/备注',
  `sort` int(11) DEFAULT NULL COMMENT '优先级，用于上移下移',
  `count` varchar(11) DEFAULT NULL COMMENT '搬运次数',
  `finishCount` varchar(11) DEFAULT NULL,
  `frequency` varchar(16) DEFAULT NULL COMMENT '频率 1:有限循环 2:无限循环',
  `start_sit_code` varchar(255) DEFAULT NULL COMMENT '取货点',
  `target_sit_code` varchar(255) DEFAULT NULL COMMENT '放货点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23444 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='任务信息表';

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES ('23440', '1', '1号库-2号库', '1', '内部转运', '2019-12-18 16:13:41', null, null, null, null, '5', '2', '有限循环', '1', '2');
INSERT INTO `t_task` VALUES ('23441', '1', '2号库-3号库', '4', '减容车间', '2019-12-18 16:14:36', null, null, null, null, '10', '3', '无限循环', '2', '3');
INSERT INTO `t_task` VALUES ('23442', '2', '1号库', '5', '内部转运', '2019-12-20 15:12:38', null, null, null, null, '', '3', '循环', '1号库-2工位', '1号库-2工位');
INSERT INTO `t_task` VALUES ('23443', null, '取4', '7', '减容车间', null, null, null, null, null, '', null, 'on', '1号库-2工位', '1号库-2工位');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `acount_number` varchar(16) DEFAULT NULL COMMENT '登录账号',
  `pwd` char(6) DEFAULT NULL COMMENT '用户密码',
  `u_type` varchar(2) DEFAULT NULL COMMENT '用户类型01 管理员  02 操作员',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `create_user` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` int(11) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL COMMENT '01-无效,02-有效',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '123', '1', null, null, null, null, '1', null);
INSERT INTO `t_user` VALUES ('2', 'user', '123', '2', null, null, null, null, null, null);
