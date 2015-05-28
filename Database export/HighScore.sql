-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Värd: sql5.freemysqlhosting.net
-- Skapad: 28 maj 2015 kl 21:25
-- Serverversion: 5.5.43-0ubuntu0.14.04.1
-- PHP-version: 5.3.28

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databas: `sql574872`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `HighScore`
--

CREATE TABLE IF NOT EXISTS `HighScore` (
  `Name` varchar(8) NOT NULL,
  `Score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `HighScore`
--

INSERT INTO `HighScore` (`Name`, `Score`) VALUES
('shabi', 20),
('now', 3),
('group 10', 4),
('group 7', 10),
('this', 2),
('this', 2),
('suprbad', 5003),
('supebad', 500),
('uperbad', 3500),
('perbad', 5400),
('rbad', 53500),
('Name', 20),
('Name', 10),
('Name', 10),
('Name', 10),
('Name', 350),
('TCH', 20),
('TCH', 20),
('new test', 250),
('Basis\n\n', 100),
('polio', 0),
('Blank', 600),
('Bane', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
