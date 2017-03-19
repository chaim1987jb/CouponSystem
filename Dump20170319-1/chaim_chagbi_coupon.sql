-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: chaim_chagbi
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `ID` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(100) NOT NULL,
  `START_DATE` datetime NOT NULL,
  `END_DATE` datetime NOT NULL,
  `AMOUNT` int(11) unsigned NOT NULL,
  `TYPE` varchar(20) NOT NULL,
  `MESSAGE` varchar(150) NOT NULL,
  `PRICE` double unsigned NOT NULL,
  `IMAGE` varchar(150) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,'New pc almost free','2017-03-16 00:00:00','2017-03-23 00:00:00',54,'ELECTRICITY','This is very nice sell offer so use your ,,',6000.99,'http://link_to_coupon'),(2,'Weekend in spa','2017-03-16 00:00:00','2020-02-01 00:00:00',21,'OTHER','Take a rest with our ',55,'this emage for coupon'),(3,'25% discount on buying new leptop','2017-03-18 00:00:00','2017-04-17 00:00:00',99,'ELECTRICITY','If you buing our leptop you can get 25% discount',2500,'http://ibm.com/coupon_0k8'),(4,'13% discount of buying new tablet','2017-03-18 00:00:00','2017-04-02 00:00:00',298,'ELECTRICITY','If you buing our tablet you can get 13% discount',65,'http://ibm.com/coupon_0k99'),(5,'20% discount of buying new software support','2017-03-18 00:00:00','2017-04-05 00:00:00',187,'ELECTRICITY','If you buing our software you can get 20% discount',80,'http://ibm.com/coupon_0k92'),(6,'8% discount if you by 2 meals','2017-03-18 00:00:00','2017-03-20 00:00:00',200,'RESTURANS','http://achla.com/coupon_0a2',2,'http://achla.com/coupon_0a2'),(7,'15% discount buying 4 meals','2017-03-18 00:00:00','2017-05-25 00:00:00',819,'RESTURANS','15% discount if you by meals',2,'http://achla.com/coupon_0a8'),(8,'250 nis discount for 100 first member for 12 month','2017-03-18 00:00:00','2017-04-07 00:00:00',78,'HEALTH','250 nis discount for 100 first member for 12 months ',20,'http://TARDET.com/coupon_0t16'),(9,'2+1 jars of pure protein ','2017-03-18 00:00:00','2017-04-01 00:00:00',699,'HEALTH','Buy 2 jars of pure protein get 1 extra',5,'http://TARGET.com/coupon_0t23'),(10,'3+2 packages of rice','2017-03-18 00:00:00','2017-03-25 00:00:00',100,'FOOD','if you by 3 packages of rice you get 2 free',5,'http://MAYA.com/coupon_0m70'),(11,'4+3 packages of beans','2017-03-18 00:00:00','2017-03-20 00:00:00',128,'FOOD','if you by 4 packages of beans you get 3 free',40,'http://MAYA.com/coupon_0m75'),(12,'2 pair of shoes with 1 ','2017-03-18 00:00:00','2017-03-20 00:00:00',100,'SPORTS','if you by 2 pair of sports shoes you get 1 free',100,'http://MEGA.com/coupon_0m85'),(13,'20% discount on sports wears','2017-03-18 00:00:00','2017-03-21 00:00:00',400,'SPORTS','3 dayes last to sale',5,'http://MEGA.com/coupon_0m88'),(14,'basketball made leather at 50 nis','2017-03-18 00:00:00','2017-06-08 00:00:00',498,'SPORTS','The owner got crazy',5,'http://MEGA.com/coupon_0m81'),(15,'swiss penknife in 100 nis','2017-03-18 00:00:00','2017-04-17 00:00:00',700,'TRAVALLING','http://LAMETAYEL.com/coupon_0l65',10,'htthttp://LAMETAYEL.com/coupon_0l64'),(16,'tent with sleeping beg ion 200 nis','2017-03-18 00:00:00','2017-04-27 00:00:00',98,'TRAVALLING','you want to travel after you see this price',15,'htthttp://LAMETAYEL.com/coupon_0l66'),(17,'flashlight at 50 nis','2017-03-18 00:00:00','2017-03-20 00:00:00',198,'TRAVALLING','this is amazing voucher',12,'htthttp://LAMETAYEL.com/coupon_0l69'),(18,'22% discount all the producs','2017-03-18 00:00:00','2017-05-07 00:00:00',199,'CAMPING','this the best sale',18,'htthttp://REQUSHET.com/coupon_0r45'),(19,'1+1 to warm wears','2017-03-18 00:00:00','2017-04-27 00:00:00',24,'CAMPING','good thing minimum price',40,'htthttp://REQUSHET.com/coupon_0r45'),(20,'18% discount to hotels','2017-03-18 00:00:00','2017-05-07 00:00:00',99,'CAMPING','huge holidwys at small price at the hotels arrangements',50,'htthttp://REQUSHET.com/coupon_0r42'),(21,'150 nis for guest room','2017-03-18 00:00:00','2017-04-02 00:00:00',78,'CAMPING','The best price ever',40,'htthttp://REQUSHET.com/coupon_0r40'),(22,'22% discount of this weekend','2017-03-18 00:00:00','2017-03-20 00:00:00',10,'CAMPING','good price you can\'t achieve',10,'htthttp://REQUSHET.com/coupon_0r47'),(23,'15% discount for hospitality for weekend','2017-03-18 00:00:00','2017-04-07 00:00:00',19,'OTHER','very good price small charge',20,'htthttp://kfar bloom.com/coupon_0k23'),(24,'all family vacation pay for parents','2017-03-18 00:00:00','2017-03-28 00:00:00',10,'OTHER','all family include with parents payment',50,'htthttp://kfar bloom.com/coupon_0k28'),(25,'17% discount of all the products','2017-03-18 00:00:00','2017-03-20 00:00:00',200,'OTHER','all the products you ever want with small price',40,'htthttp://ksp.com/coupon_0k11'),(26,'Wireless headphones in 100 nis','2017-03-18 00:00:00','2017-03-23 00:00:00',99,'OTHER','The best price of this quality',10,'htthttp://ksp.com/coupon_0k17'),(27,'buy computer and get screen 75% discount','2017-03-18 00:00:00','2017-03-25 00:00:00',38,'OTHER','best price for this quality',100,'htthttp://ksp.com/coupon_0k12'),(28,'Video card','2017-03-18 00:00:00','2017-03-26 00:00:00',100,'OTHER','The low price you ever get',15,'htthttp://ksp.com/coupon_0k9');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-19 21:01:31
