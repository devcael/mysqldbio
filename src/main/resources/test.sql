/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

--
-- start  table dump : adicionais
--

CREATE TABLE IF NOT EXISTS `adicionais` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `quantidade_maxima` int(11) DEFAULT NULL,
  `grupo_adicionais_id` int(11) DEFAULT NULL,
  `codigo_adicional` int(11) DEFAULT NULL,
  "asdasd;dasdasd",
  PRIMARY KEY (`codigo`),
  KEY `FKn5q3h09bra2quv51y6suneweq` (`grupo_adicionais_id`),
  KEY `FKpeimpd7tm83dvgjldbeobb6wq` (`codigo_adicional`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;



--
-- end  table dump : adicionais
--



SELECT * FROM produto p WHERE p.codigo IN (SELECT it.id_item
FROM itensvenda it) LIMIT 10;

--
-- Inserts of adicionais
--

SELECT * FROM cabecalho WHERE (SELECT COUNT(*) FROM cabecalho) = 0);

INSERT INTO `adicionais`(`codigo`, `quan;tidade_maxima`, `grupo_adicionais_id`, `codigo_adicional`) VALUES
(1, null, 1, 1),
(2, null, 1, 3),
(3, null, 1, 2);


INSERT INTO `adicionais`(`codigo`, `quan;tidade_maxima`, `grupo_adicionais_id`, `codigo_adicional`) VALUES
(1, null, 1, "aslasd a;"),
(2, null, 1, 3),
(3, null, 1, 2);
