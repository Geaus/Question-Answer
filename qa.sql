-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: qa
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (2,1,1,'# **你说的对**\n\n但是y86\\_64是由Randal E. Bryant为教学研发的一款ISA，~~后面忘了~~\n\n![](https://booklibimg.kfzimg.com/data/book_lib_img_v2/isbn/1/f14b/f14ba96519795e282a7cf9fa5327daa7_1_1_300_300.jpg)','2023-06-27 14:00:01'),(3,3,1,'没学过不知道','2023-06-27 14:10:12'),(4,3,2,'很容易啊只要上课不去，不要回复群里消息就行了，不过可能会挂科哦～','2023-06-27 14:12:39'),(5,1,1,'lalefalwdadla,wdadaw\n','2023-06-28 10:48:00'),(6,1,1,'请输入你的回答sFESEfaesefws\n','2023-06-28 10:48:37'),(7,1,2,'aEFAWefAEfAfeAWFAWFZDVCADE\n','2023-06-28 10:49:42'),(8,1,1,'$L = \\frac{1}{2} \\rho v^2 S C_L$\n','2023-06-28 11:29:17'),(9,1,1,'请输入你的回答','2023-06-28 14:15:20'),(10,1,12,'学你awdnadkw\n','2023-06-28 16:24:39');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_a`
--

DROP TABLE IF EXISTS `feedback_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_a`
--

LOCK TABLES `feedback_a` WRITE;
/*!40000 ALTER TABLE `feedback_a` DISABLE KEYS */;
INSERT INTO `feedback_a` VALUES (3,1,2,2),(4,1,1,3),(5,-1,1,7),(8,1,1,2),(9,1,1,5),(10,1,1,6),(12,1,1,8),(13,-1,1,9);
/*!40000 ALTER TABLE `feedback_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_q`
--

DROP TABLE IF EXISTS `feedback_q`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_q`
--

LOCK TABLES `feedback_q` WRITE;
/*!40000 ALTER TABLE `feedback_q` DISABLE KEYS */;
INSERT INTO `feedback_q` VALUES (1,-1,1,1,2),(2,-1,1,2,2),(5,0,1,1,11),(6,1,1,1,14),(7,1,0,1,12),(8,1,1,1,13),(9,1,1,1,1);
/*!40000 ALTER TABLE `feedback_q` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_1_id` int DEFAULT NULL,
  `user_2_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_1_id_idx` (`user_1_id`),
  KEY `user_2_id_idx` (`user_2_id`),
  CONSTRAINT `user_1_id` FOREIGN KEY (`user_1_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_2_id` FOREIGN KEY (`user_2_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (2,1,2),(3,2,1),(37,1,3);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword`
--

DROP TABLE IF EXISTS `keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `keyword` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `keyword` varchar(1024) DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword`
--

LOCK TABLES `keyword` WRITE;
/*!40000 ALTER TABLE `keyword` DISABLE KEYS */;
INSERT INTO `keyword` VALUES (1,'ics',15),(2,'陈昊鹏',16),(3,'ics',16);
/*!40000 ALTER TABLE `keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `question_user_id_idx` (`user_id`),
  CONSTRAINT `question_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'如何学习ICS','rt，ICS的lab好难做',1,'2023-05-11 12:11:01'),(2,'如何可以翘课并不被发现','rt，体育课好累真不想去',1,'2023-06-12 13:12:13'),(11,'请问学校有什么好吃的','有什么好吃的',1,NULL),(12,'如何学习','awdawad',1,NULL),(13,'如何awdaIWAW','awdawda',1,'2023-06-28 16:14:32'),(14,'如斯肤色阿达五毒月','阿伟大武当',1,'2023-06-29 16:27:41'),(15,'如何·学习ICS','ICS',1,NULL),(16,'如何在陈昊鹏的带领下学习ICS','adwad',1,NULL);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'校园生活'),(2,'休闲娱乐'),(3,'课业学习');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_ques_rel`
--

DROP TABLE IF EXISTS `tag_ques_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag_ques_rel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ques_id` int DEFAULT NULL,
  `tag_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ques_id_idx` (`ques_id`),
  KEY `tag_id_idx` (`tag_id`),
  CONSTRAINT `ques_id` FOREIGN KEY (`ques_id`) REFERENCES `question` (`id`),
  CONSTRAINT `tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_ques_rel`
--

LOCK TABLES `tag_ques_rel` WRITE;
/*!40000 ALTER TABLE `tag_ques_rel` DISABLE KEYS */;
INSERT INTO `tag_ques_rel` VALUES (1,1,1),(2,1,3),(12,11,2),(13,11,1),(14,12,3),(15,13,3),(16,14,1),(17,15,3),(18,16,3);
/*!40000 ALTER TABLE `tag_ques_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'user1','1','user1@sjtu.edu.cn','https://xsgames.co/randomusers/avatar.php?g=pixel'),(2,2,'user2','2','user2@sjtu.edu.cn','https://xsgames.co/randomusers/avatar.php?g=pixel'),(3,1,'user3','3','user2@sjtu.edu.cn','https://xsgames.co/randomusers/avatar.php?g=pixel');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-30 10:11:52
