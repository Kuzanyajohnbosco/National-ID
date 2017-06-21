-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 21, 2017 at 02:00 AM
-- Server version: 5.1.53
-- PHP Version: 5.3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nationalid`
--

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE IF NOT EXISTS `members` (
  `memberID` int(200) NOT NULL,
  `nrmNo` varchar(500) NOT NULL,
  `surname` varchar(400) NOT NULL,
  `firstname` varchar(400) NOT NULL,
  `othername` varchar(500) NOT NULL,
  `sex` varchar(200) NOT NULL,
  `dob` date NOT NULL,
  `nationalid` varchar(600) NOT NULL,
  `voted` varchar(300) NOT NULL,
  `district` varchar(600) NOT NULL,
  `constituency` varchar(600) NOT NULL,
  `county` varchar(500) NOT NULL,
  `subcounty` varchar(600) NOT NULL,
  `parish` varchar(500) NOT NULL,
  `village` varchar(600) NOT NULL,
  PRIMARY KEY (`memberID`),
  UNIQUE KEY `memberID` (`memberID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `members`
--


-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(400) NOT NULL,
  `password` varchar(500) NOT NULL,
  `role` varchar(300) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `role`) VALUES
('me', '123', 'admin');
