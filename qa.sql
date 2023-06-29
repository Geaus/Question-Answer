/*
 Navicat Premium Data Transfer

 Source Server         : mysql_database
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : qa

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 27/06/2023 14:31:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `ques_id` int DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `answer_user_id_idx` (`user_id`),
  KEY `answer_ques_id_idx` (`ques_id`),
  CONSTRAINT `answer_ques_id` FOREIGN KEY (`ques_id`) REFERENCES `question` (`id`),
  CONSTRAINT `answer_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of answer
-- ----------------------------
BEGIN;
INSERT INTO `answer` (`id`, `user_id`, `ques_id`, `content`, `time`) VALUES (2, 1, 1, '你说的对，但是y86_64是由Randal E. Bryant为教学研发的一款ISA，后面忘了', '2023-06-27 14:00:01');
INSERT INTO `answer` (`id`, `user_id`, `ques_id`, `content`, `time`) VALUES (3, 3, 1, '没学过不知道', '2023-06-27 14:10:12');
INSERT INTO `answer` (`id`, `user_id`, `ques_id`, `content`, `time`) VALUES (4, 3, 2, '很容易啊只要上课不去，不要回复群里消息就行了，不过可能会挂科哦～', '2023-06-27 14:12:39');
COMMIT;

-- ----------------------------
-- Table structure for feedback_a
-- ----------------------------
DROP TABLE IF EXISTS `feedback_a`;
CREATE TABLE `feedback_a` (
  `id` int NOT NULL AUTO_INCREMENT,
  `like` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `ans_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `feed_a_user_id_idx` (`user_id`),
  KEY `feed_a_ans_id_idx` (`ans_id`),
  CONSTRAINT `feed_a_ans_id` FOREIGN KEY (`ans_id`) REFERENCES `answer` (`id`),
  CONSTRAINT `feed_a_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of feedback_a
-- ----------------------------
BEGIN;
INSERT INTO `feedback_a` (`id`, `like`, `user_id`, `ans_id`) VALUES (2, 1, 1, 2);
INSERT INTO `feedback_a` (`id`, `like`, `user_id`, `ans_id`) VALUES (3, 1, 2, 2);
COMMIT;

-- ----------------------------
-- Table structure for feedback_q
-- ----------------------------
DROP TABLE IF EXISTS `feedback_q`;
CREATE TABLE `feedback_q` (
  `id` int NOT NULL AUTO_INCREMENT,
  `like` int DEFAULT NULL,
  `bookmark` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `ques_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `feed_q_user_id_idx` (`user_id`),
  KEY `feed_q_ques_id_idx` (`ques_id`),
  CONSTRAINT `feed_q_ques_id` FOREIGN KEY (`ques_id`) REFERENCES `question` (`id`),
  CONSTRAINT `feed_q_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of feedback_q
-- ----------------------------
BEGIN;
INSERT INTO `feedback_q` (`id`, `like`, `bookmark`, `user_id`, `ques_id`) VALUES (1, -1, 1, 1, 2);
INSERT INTO `feedback_q` (`id`, `like`, `bookmark`, `user_id`, `ques_id`) VALUES (2, -1, 1, 2, 2);
COMMIT;

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_1_id` int DEFAULT NULL,
  `user_2_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_1_id_idx` (`user_1_id`),
  KEY `user_2_id_idx` (`user_2_id`),
  CONSTRAINT `user_1_id` FOREIGN KEY (`user_1_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_2_id` FOREIGN KEY (`user_2_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of follow
-- ----------------------------
BEGIN;
INSERT INTO `follow` (`id`, `user_1_id`, `user_2_id`) VALUES (1, 1, 3);
COMMIT;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `question_user_id_idx` (`user_id`),
  CONSTRAINT `question_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of question
-- ----------------------------
BEGIN;
INSERT INTO `question` (`id`, `title`, `content`, `user_id`, `time`) VALUES (1, '如何学习ICS', 'rt，ICS的lab好难做', 1, '2023-05-11 12:11:01');
INSERT INTO `question` (`id`, `title`, `content`, `user_id`, `time`) VALUES (2, '如何可以翘课并不被发现', 'rt，体育课好累真不想去', 1, '2023-06-12 13:12:13');
COMMIT;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tag
-- ----------------------------
BEGIN;
INSERT INTO `tag` (`id`, `content`) VALUES (1, '校园生活');
INSERT INTO `tag` (`id`, `content`) VALUES (2, '休闲娱乐');
INSERT INTO `tag` (`id`, `content`) VALUES (3, '课业学习');
COMMIT;

-- ----------------------------
-- Table structure for tag_ques_rel
-- ----------------------------
DROP TABLE IF EXISTS `tag_ques_rel`;
CREATE TABLE `tag_ques_rel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ques_id` int DEFAULT NULL,
  `tag_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ques_id_idx` (`ques_id`),
  KEY `tag_id_idx` (`tag_id`),
  CONSTRAINT `ques_id` FOREIGN KEY (`ques_id`) REFERENCES `question` (`id`),
  CONSTRAINT `tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tag_ques_rel
-- ----------------------------
BEGIN;
INSERT INTO `tag_ques_rel` (`id`, `ques_id`, `tag_id`) VALUES (1, 1, 1);
INSERT INTO `tag_ques_rel` (`id`, `ques_id`, `tag_id`) VALUES (2, 1, 3);
INSERT INTO `tag_ques_rel` (`id`, `ques_id`, `tag_id`) VALUES (3, 2, 1);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `type`, `username`, `password`, `email`) VALUES (1, 1, 'user1', '1', 'user1@sjtu.edu.cn');
INSERT INTO `user` (`id`, `type`, `username`, `password`, `email`) VALUES (2, 2, 'user2', '2', 'user2@sjtu.edu.cn');
INSERT INTO `user` (`id`, `type`, `username`, `password`, `email`) VALUES (3, 1, 'user3', '3', 'user2@sjtu.edu.cn');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
