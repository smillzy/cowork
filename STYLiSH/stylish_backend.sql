-- MySQL dump 10.13  Distrib 8.2.0, for macos13.5 (arm64)
--
-- Host: localhost    Database: stylish_db
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint unsigned NOT NULL,
  `picture` varchar(255) NOT NULL,
  `story` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product` (`product_id`),
  CONSTRAINT `campaign_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,201807242228,'assets/campaigns/201807242228_keyvisual.jpg','於是\r\n我也想要給你\r\n一個那麼美好的自己。\r\n不朽《與自己和好如初》'),(2,201807242222,'assets/campaigns/201807242222_keyvisual.jpg','永遠\r\n展現自信與專業\r\n無法抵擋的男人魅力。\r\n復古《再一次經典》'),(3,201807202140,'assets/campaigns/201807202140_keyvisual.jpg','瞬間\r\n在城市的角落\r\n找到失落多時的記憶。\r\n印象《都會故事集》');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (1,'FFFFFF','白色'),(2,'DDFFBB','亮綠'),(3,'CCCCCC','淺灰'),(4,'BB7744','淺棕'),(5,'DDF0FF','淺藍'),(6,'334455','深藍'),(7,'FFDDDD','粉紅'),(8,'FFFFFF','White'),(9,'FFFFFE','White'),(10,'FFFFFE','White'),(11,'FFFFFC','White'),(12,'FFFFFD','White'),(13,'FFFFFA','White'),(14,'FFFFFB','White');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hot`
--

DROP TABLE IF EXISTS `hot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hot`
--

LOCK TABLES `hot` WRITE;
/*!40000 ALTER TABLE `hot` DISABLE KEYS */;
INSERT INTO `hot` VALUES (1,'冬季新品搶先看'),(2,'百搭穿搭必敗品');
/*!40000 ALTER TABLE `hot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hot_product`
--

DROP TABLE IF EXISTS `hot_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hot_product` (
  `hot_id` bigint unsigned NOT NULL,
  `product_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`hot_id`,`product_id`),
  KEY `product` (`product_id`),
  CONSTRAINT `hot_product_ibfk_1` FOREIGN KEY (`hot_id`) REFERENCES `hot` (`id`),
  CONSTRAINT `hot_product_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hot_product`
--

LOCK TABLES `hot_product` WRITE;
/*!40000 ALTER TABLE `hot_product` DISABLE KEYS */;
INSERT INTO `hot_product` VALUES (2,201807202140),(1,201807202157),(2,201807242211),(1,201807242216),(2,201807242228),(1,201807242232);
/*!40000 ALTER TABLE `hot_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_table` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `number` varchar(255) NOT NULL,
  `time` bigint unsigned NOT NULL,
  `status` int NOT NULL,
  `details` json NOT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `total` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `order_table_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5001 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_table`
--

LOCK TABLES `order_table` WRITE;
/*!40000 ALTER TABLE `order_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint unsigned NOT NULL,
  `details` json NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_table` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(127) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `price` int unsigned NOT NULL,
  `texture` varchar(127) NOT NULL,
  `wash` varchar(127) NOT NULL,
  `place` varchar(127) NOT NULL,
  `note` varchar(127) NOT NULL,
  `story` text NOT NULL,
  `main_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category` (`category`),
  KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=201902191248 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (201807201824,'women','前開衩扭結洋裝','厚薄：薄\r\n彈性：無',799,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807201824_main.jpg'),(201807202140,'women','透肌澎澎防曬襯衫','厚薄：薄\r\n彈性：無',599,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807202140_main.jpg'),(201807202150,'women','小扇紋細織上衣','厚薄：薄\r\n彈性：無',599,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807202150_main.jpg'),(201807202157,'women','活力花紋長筒牛仔褲','厚薄：薄\r\n彈性：無',1299,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807202157_main.jpg'),(201807242211,'men','純色輕薄百搭襯衫','厚薄：薄\r\n彈性：無',799,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807242211_main.jpg'),(201807242216,'men','時尚輕鬆休閒西裝','厚薄：薄\r\n彈性：無',2399,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807242216_main.jpg'),(201807242222,'men','經典商務西裝','厚薄：薄\r\n彈性：無',3999,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807242222_main.jpg'),(201807242228,'accessories','夏日海灘戶外遮陽帽','厚薄：薄\r\n彈性：無',1499,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807242228_main.jpg'),(201807242230,'accessories','經典牛仔帽','厚薄：薄\r\n彈性：無',799,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807242230_main.jpg'),(201807242232,'accessories','卡哇伊多功能隨身包','厚薄：薄\r\n彈性：無',1299,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807242232_main.jpg'),(201807242234,'accessories','柔軟氣質羊毛圍巾','厚薄：薄\r\n彈性：無',1799,'棉 100%','手洗，溫水','中國','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201807242234_main.jpg'),(201902191210,'women','精緻扭結洋裝','厚薄：薄\r\n彈性：無',999,'棉 100%','手洗','越南','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201902191210_main.jpg'),(201902191242,'women','透肌澎澎薄紗襯衫','厚薄：薄\r\n彈性：無',999,'棉 100%','手洗','越南','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201902191242_main.jpg'),(201902191245,'women','小扇紋質感上衣','厚薄：薄\r\n彈性：無',999,'棉 100%','手洗','越南','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201902191245_main.jpg'),(201902191247,'women','經典修身長筒牛仔褲','厚薄：薄\r\n彈性：無',1999,'棉 100%','手洗','越南','實品顏色依單品照為主','O.N.S is all about options, which is why we took our staple polo shirt and upgraded it with slubby linen jersey, making it even lighter for those who prefer their summer style extra-breezy.','assets/products/201902191247_main.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint unsigned NOT NULL,
  `image` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,201807242228,'assets/products/201807242228_0.jpg'),(2,201807242228,'assets/products/201807242228_1.jpg'),(3,201807242228,'assets/products/201807242228_0.jpg'),(4,201807242228,'assets/products/201807242228_1.jpg'),(5,201807242230,'assets/products/201807242230_0.jpg'),(6,201807242230,'assets/products/201807242230_1.jpg'),(7,201807242230,'assets/products/201807242230_0.jpg'),(8,201807242230,'assets/products/201807242230_1.jpg'),(9,201807242232,'assets/products/201807242232_0.jpg'),(10,201807242232,'assets/products/201807242232_1.jpg'),(11,201807242232,'assets/products/201807242232_0.jpg'),(12,201807242232,'assets/products/201807242232_1.jpg'),(13,201807242234,'assets/products/201807242234_0.jpg'),(14,201807242234,'assets/products/201807242234_1.jpg'),(15,201807242234,'assets/products/201807242234_0.jpg'),(16,201807242234,'assets/products/201807242234_1.jpg'),(17,201807242211,'assets/products/201807242211_0.jpg'),(18,201807242211,'assets/products/201807242211_1.jpg'),(19,201807242211,'assets/products/201807242211_0.jpg'),(20,201807242211,'assets/products/201807242211_1.jpg'),(21,201807242216,'assets/products/201807242216_0.jpg'),(22,201807242216,'assets/products/201807242216_1.jpg'),(23,201807242216,'assets/products/201807242216_0.jpg'),(24,201807242216,'assets/products/201807242216_1.jpg'),(25,201807242222,'assets/products/201807242222_0.jpg'),(26,201807242222,'assets/products/201807242222_1.jpg'),(27,201807242222,'assets/products/201807242222_0.jpg'),(28,201807242222,'assets/products/201807242222_1.jpg'),(29,201807201824,'assets/products/201807201824_0.jpg'),(30,201807201824,'assets/products/201807201824_1.jpg'),(31,201807201824,'assets/products/201807201824_0.jpg'),(32,201807201824,'assets/products/201807201824_1.jpg'),(33,201807202140,'assets/products/201807202140_0.jpg'),(34,201807202140,'assets/products/201807202140_1.jpg'),(35,201807202140,'assets/products/201807202140_0.jpg'),(36,201807202140,'assets/products/201807202140_1.jpg'),(37,201807202150,'assets/products/201807202150_0.jpg'),(38,201807202150,'assets/products/201807202150_1.jpg'),(39,201807202150,'assets/products/201807202150_0.jpg'),(40,201807202150,'assets/products/201807202150_1.jpg'),(41,201807202157,'assets/products/201807202157_0.jpg'),(42,201807202157,'assets/products/201807202157_1.jpg'),(43,201807202157,'assets/products/201807202157_0.jpg'),(44,201807202157,'assets/products/201807202157_1.jpg'),(45,201902191210,'assets/products/201902191210_0.jpg'),(46,201902191210,'assets/products/201902191210_1.jpg'),(47,201902191210,'assets/products/201902191210_0.jpg'),(48,201902191210,'assets/products/201902191210_1.jpg'),(49,201902191242,'assets/products/201902191242_0.jpg'),(50,201902191242,'assets/products/201902191242_1.jpg'),(51,201902191242,'assets/products/201902191242_0.jpg'),(52,201902191242,'assets/products/201902191242_1.jpg'),(53,201902191245,'assets/products/201902191245_0.jpg'),(54,201902191245,'assets/products/201902191245_1.jpg'),(55,201902191245,'assets/products/201902191245_0.jpg'),(56,201902191245,'assets/products/201902191245_1.jpg'),(57,201902191247,'assets/products/201902191245_0.jpg'),(58,201902191247,'assets/products/201902191247_1.jpg'),(59,201902191247,'assets/products/201902191247_0.jpg'),(60,201902191247,'assets/products/201902191247_1.jpg');
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_id` int unsigned DEFAULT NULL,
  `provider` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(127) NOT NULL,
  `picture` varchar(500) DEFAULT NULL,
  `access_token` varchar(1000) NOT NULL DEFAULT '',
  `access_expired` bigint unsigned NOT NULL,
  `login_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `user` (`provider`,`email`,`password`),
  KEY `access_token` (`access_token`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10273 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `access_expired` bigint NOT NULL,
  `access_token` varchar(1000) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL,
  `login_at` datetime(6) DEFAULT NULL,
  `name` varchar(127) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` varchar(500) DEFAULT NULL,
  `provider` varchar(15) NOT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eamk4l51hm6yqb8xw37i23kb5` (`email`),
  KEY `role_id` (`role_id`),
  KEY `user` (`provider`,`email`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_table`
--

LOCK TABLES `user_table` WRITE;
/*!40000 ALTER TABLE `user_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint unsigned NOT NULL,
  `color_id` int unsigned DEFAULT NULL,
  `size` varchar(15) NOT NULL,
  `stock` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product` (`product_id`),
  KEY `FKgw0qk7aq0hn5w64uxrsc2yaju` (`color_id`),
  CONSTRAINT `FKgw0qk7aq0hn5w64uxrsc2yaju` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `variant_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES (27,201807201824,1,'S',2),(28,201807201824,1,'M',1),(29,201807201824,1,'L',2),(30,201807201824,2,'S',9),(31,201807201824,2,'M',0),(32,201807201824,2,'L',5),(33,201807201824,3,'S',8),(34,201807201824,3,'M',5),(35,201807201824,3,'L',9),(36,201807202140,2,'S',7),(37,201807202140,2,'M',5),(38,201807202140,2,'L',8),(39,201807202140,3,'S',1),(40,201807202140,3,'M',6),(41,201807202140,3,'L',2),(42,201807202150,2,'S',3),(43,201807202150,2,'M',5),(44,201807202150,3,'S',4),(45,201807202150,3,'M',1),(46,201807202150,4,'S',2),(47,201807202150,4,'M',6),(48,201807202157,5,'S',8),(49,201807202157,5,'M',5),(50,201807202157,5,'L',6),(51,201807202157,3,'S',0),(52,201807202157,3,'M',6),(53,201807202157,3,'L',5),(54,201807202157,6,'S',2),(55,201807202157,6,'M',7),(56,201807202157,6,'L',9),(57,201807242211,1,'M',5),(58,201807242211,1,'L',7),(59,201807242211,1,'XL',1),(60,201807242211,5,'M',1),(61,201807242211,5,'L',4),(62,201807242211,5,'XL',3),(63,201807242216,1,'S',10),(64,201807242216,1,'M',5),(65,201807242216,1,'L',6),(66,201807242216,3,'S',1),(67,201807242216,3,'M',3),(68,201807242216,3,'L',10),(69,201807242222,6,'S',9),(70,201807242222,6,'M',5),(71,201807242222,6,'L',1),(72,201807242222,6,'XL',9),(73,201807242228,5,'M',7),(74,201807242228,5,'L',1),(75,201807242228,4,'M',3),(76,201807242228,4,'L',1),(77,201807242230,4,'M',5),(78,201807242230,4,'L',1),(79,201807242230,6,'M',5),(80,201807242230,6,'L',2),(81,201807242232,1,'F',1),(82,201807242232,7,'F',1),(83,201807242234,1,'F',4),(84,201807242234,5,'F',7),(101,201902191210,1,'S',0),(102,201902191210,1,'M',9),(103,201902191210,7,'S',2),(104,201902191210,7,'M',1),(105,201902191242,2,'M',3),(106,201902191242,2,'L',9),(107,201902191242,5,'M',2),(108,201902191242,5,'L',6),(109,201902191245,1,'M',2),(110,201902191245,1,'L',6),(111,201902191245,3,'M',5),(112,201902191245,3,'L',8),(113,201902191247,1,'S',9),(114,201902191247,1,'M',4),(115,201902191247,1,'L',2),(116,201902191247,5,'S',0),(117,201902191247,5,'M',10),(118,201902191247,5,'L',5);
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-16 13:53:21
