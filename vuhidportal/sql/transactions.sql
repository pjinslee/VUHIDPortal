-- Database: `vuhid-portal`
-- 
-- --------------------------------------------------------
-- 
-- Table structure for table `transactions`
-- 

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `ID` bigint(20) NOT NULL auto_increment,
  `Hash` char(40) collate utf8_bin NOT NULL,
  `Type` tinyint(4) NOT NULL,
  `Time` datetime NOT NULL default '0000-00-00 00:00:00',
  `Completed` boolean NOT NULL default false,
  `ReturnValue` longtext collate utf8_bin,
  `InputValue1` longtext collate utf8_bin,
  `InputValue2` longtext collate utf8_bin,
  `InputValue3` longtext collate utf8_bin,
  `InputValue4` longtext collate utf8_bin,
  PRIMARY KEY  (`ID`),
  KEY `Hash` (`Hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin PACK_KEYS=0 AUTO_INCREMENT=1 ;
