-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: quotesdb
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `quotes`
--

DROP TABLE IF EXISTS `quotes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quotes` (
  `message` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quotes`
--

LOCK TABLES `quotes` WRITE;
/*!40000 ALTER TABLE `quotes` DISABLE KEYS */;
INSERT INTO `quotes` VALUES ('Life is wonderful. Without it we\'d all be dead.\r'),('Daddy, why doesn\'t this magnet pick up this floppy disk?\r'),('Give me ambiguity or give me something else.\r'),('I.R.S.: We\'ve got what it takes to take what you\'ve got!\r'),('We are born naked, wet and hungry. Then things get worse.\r'),('Make it idiot proof and someone will make a better idiot.\r'),('He who laughs last thinks slowest!\r'),('Always remember you\'re unique, just like everyone else.\r'),('\"More hay, Trigger?\" \"No thanks, Roy, I\'m stuffed!\"\r'),('A flashlight is a case for holding dead batteries.\r'),('Lottery: A tax on people who are bad at math.\r'),('Error, no keyboard - press F1 to continue.\r'),('There\'s too much blood in my caffeine system.\r'),('Artificial Intelligence usually beats real stupidity.\r'),('Hard work has a future payoff. Laziness pays off now.\r'),('\"Very funny, Scotty. Now beam down my clothes.\"\r'),('Puritanism: The haunting fear that someone, somewhere may be happy.\r'),('Consciousness: that annoying time between naps.\r'),('Don\'t take life too seriously, you won\'t get out alive.\r'),('I don\'t suffer from insanity. I enjoy every minute of it.\r'),('Better to understand a little than to misunderstand a lot.\r'),('The gene pool could use a little chlorine.\r'),('When there\'s a will, I want to be in it.\r'),('Okay, who put a \"stop payment\" on my reality check?\r'),('We have enough youth, how about a fountain of SMART?\r'),('Programming is an art form that fights back.\r'),('\"Daddy, what does FORMATTING DRIVE C mean?\"\r'),('All wiyht. Rho sritched mg kegtops awound?\r'),('My mail reader can beat up your mail reader.\r'),('Never forget: 2 + 2 = 5 for extremely large values of 2.\r'),('Nobody has ever, ever, EVER learned all of WordPerfect.\r'),('To define recursion, we must first define recursion.\r'),('Good programming is 99% sweat and 1% coffee.\r'),('Home is where you hang your @\r'),('The E-mail of the species is more deadly than the mail.\r'),('A journey of a thousand sites begins with a single click.\r'),('You can\'t teach a new mouse old clicks.\r'),('Great groups from little icons grow.\r'),('Speak softly and carry a cellular phone.\r'),('C: is the root of all directories.\r'),('Don\'t put all your hypes in one home page.\r'),('Pentium wise; pen and paper foolish.\r'),('The modem is the message.\r'),('Too many clicks spoil the browse.\r'),('The geek shall inherit the earth.\r'),('A chat has nine lives.\r'),('Don\'t byte off more than you can view.\r'),('Fax is stranger than fiction.\r'),('What boots up must come down.\r'),('Windows will never cease.   (ed. oh sure...)\r'),('In Gates we trust.    (ed.  yeah right....)\r'),('Virtual reality is its own reward.\r'),('Modulation in all things.\r'),('A user and his leisure time are soon parted.\r'),('There\'s no place like http://www.home.com\r'),('Know what to expect before you connect.\r'),('Oh, what a tangled website we weave when first we practice.\r'),('Speed thrills.\r'),('Give a man a fish and you feed him for a day; teach him to use the Net and he won\'t bother you for w');
/*!40000 ALTER TABLE `quotes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-06 20:02:09
