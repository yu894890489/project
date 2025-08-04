/*
SQLyog Professional v13.1.1 (64 bit)
MySQL - 8.0.29 : Database - project_manager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`project_manager` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `project_manager`;

/*Table structure for table `tower_comment` */

DROP TABLE IF EXISTS `tower_comment`;

CREATE TABLE `tower_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint NOT NULL DEFAULT '0',
  `type` int DEFAULT '1' COMMENT '1:需求',
  `ref_id` bigint NOT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `del_flag` int NOT NULL DEFAULT '0',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评论表';

/*Table structure for table `tower_project` */

DROP TABLE IF EXISTS `tower_project`;

CREATE TABLE `tower_project` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL COMMENT '项目名称',
  `code` varchar(50) NOT NULL COMMENT '项目编码',
  `type` varchar(50) NOT NULL COMMENT '项目类型',
  `initiation_date` date NOT NULL COMMENT '立项时间',
  `dept_id` bigint DEFAULT NULL COMMENT '归属区域',
  `delivery_date` date DEFAULT NULL COMMENT '交付时间',
  `status` varchar(50) DEFAULT NULL COMMENT '项目状态',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '项目金额(万)',
  `progress` int NOT NULL DEFAULT '0' COMMENT '项目进店',
  `remark` varchar(500) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '000000' COMMENT '租户id',
  `create_dept` bigint DEFAULT '103',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目表';

/*Table structure for table `tower_project_demand` */

DROP TABLE IF EXISTS `tower_project_demand`;

CREATE TABLE `tower_project_demand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint DEFAULT NULL,
  `dept_id` bigint DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL COMMENT '需求名称',
  `define_project_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `product_situation` varchar(50) DEFAULT NULL COMMENT '产品情况',
  `title` varchar(50) NOT NULL COMMENT '批次名称',
  `status` varchar(50) NOT NULL DEFAULT '0' COMMENT '需求状态',
  `type` varchar(50) NOT NULL COMMENT '需求类型',
  `cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '预估成本(万)',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `delivery_time` date DEFAULT NULL COMMENT '交付时间',
  `expect_delivery_time` date DEFAULT NULL COMMENT '期望交付时间',
  `signing_status` int DEFAULT '0' COMMENT '签约情况 1是0否',
  `confirm_status` int DEFAULT '0' COMMENT '需求确认状态',
  `development_status` int DEFAULT '0',
  `design_status` int DEFAULT '0' COMMENT '设计状态',
  `test_status` int DEFAULT '0',
  `delivery_status` int DEFAULT '0' COMMENT '交付状态',
  `operation_status` int DEFAULT '0' COMMENT '运维状态',
  `attachment` text COMMENT '需求附件 ',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审核人',
  `audit_opinion` varchar(500) DEFAULT NULL COMMENT '审核已经',
  `audit_attachment` text COMMENT '审核反馈附件',
  `progress` int DEFAULT '0' COMMENT '进度',
  `remark` text COMMENT '需求描述',
  `del_flag` int DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `tenant_id` varchar(10) DEFAULT '000000' COMMENT '租户id',
  `create_dept` bigint DEFAULT '103',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='需求管理';

/*Table structure for table `tower_project_demand_weekly_report` */

DROP TABLE IF EXISTS `tower_project_demand_weekly_report`;

CREATE TABLE `tower_project_demand_weekly_report` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL COMMENT '标题',
  `project_demand_id` bigint DEFAULT NULL COMMENT '需求id',
  `flling_date` date DEFAULT NULL COMMENT '填报日期',
  `content` text COMMENT '内容',
  `attachment` varchar(500) DEFAULT NULL COMMENT '附件',
  `del_flag` int NOT NULL DEFAULT '0',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目周报';

/*Table structure for table `tower_project_user` */

DROP TABLE IF EXISTS `tower_project_user`;

CREATE TABLE `tower_project_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL COMMENT '项目id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tower_project_user_pk` (`project_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目人员关联表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
