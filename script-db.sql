-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.19 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para super_alfredo
CREATE DATABASE IF NOT EXISTS `super_alfredo` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `super_alfredo`;

-- Volcando estructura para tabla super_alfredo.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `categoria_UN` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla super_alfredo.categoria: ~3 rows (aproximadamente)
DELETE FROM `categoria`;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`id`, `nombre`) VALUES
	(1, 'Comestibles'),
	(3, 'Electronica'),
	(4, 'Juguetes'),
	(2, 'Platos Cocinados');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;

-- Volcando estructura para función super_alfredo.fn_saludar_idioma
DELIMITER //
CREATE FUNCTION `fn_saludar_idioma`(
	`pNombre` VARCHAR(50),
	`pIdioma` VARCHAR(50)
) RETURNS varchar(50) CHARSET utf8
    DETERMINISTIC
BEGIN

	DECLARE saludo VARCHAR(50);
	
	CASE pIdioma
		WHEN 'es' THEN SET saludo = CONCAT('Hola, ', pNombre);
		WHEN 'eu' THEN SET saludo = CONCAT('Kaixo, ', pNombre);
		WHEN 'en' THEN SET saludo = CONCAT('Hello, ', pNombre);
	END CASE;
	
	RETURN saludo;
	
END//
DELIMITER ;

-- Volcando estructura para procedimiento super_alfredo.pa_actualizar_categoria
DELIMITER //
CREATE PROCEDURE `pa_actualizar_categoria`(
	IN `pNombre` VARCHAR(100),
	IN `pId` INT
)
BEGIN

UPDATE categoria SET nombre = pNombre WHERE id = pId;

END//
DELIMITER ;

-- Volcando estructura para procedimiento super_alfredo.pa_categoria_por_id
DELIMITER //
CREATE PROCEDURE `pa_categoria_por_id`( IN pId INT )
BEGIN
	
	SELECT id, nombre FROM categoria WHERE id = pId;

END//
DELIMITER ;

-- Volcando estructura para procedimiento super_alfredo.pa_eliminar_categoria
DELIMITER //
CREATE PROCEDURE `pa_eliminar_categoria`(
	IN `pId` INT
)
BEGIN
	DELETE FROM categoria WHERE id = pId;
END//
DELIMITER ;

-- Volcando estructura para procedimiento super_alfredo.pa_insertar_categoria
DELIMITER //
CREATE PROCEDURE `pa_insertar_categoria`(
	IN `pNombre` VARCHAR(100),
	OUT `pIdGenerado` INT
)
BEGIN
	
	
	-- realizamos la insert
	INSERT INTO categoria ( nombre ) VALUES ( TRIM(pNombre) );

	-- guardamos en el parametro de salida el ultimo id generado usando una función
	SET pIdGenerado = LAST_INSERT_ID();
	
END//
DELIMITER ;

-- Volcando estructura para procedimiento super_alfredo.pa_listar_categorias
DELIMITER //
CREATE PROCEDURE `pa_listar_categorias`()
BEGIN
	
	-- sentencias de SQL
	SELECT id, nombre FROM categoria ORDER BY nombre ASC; 
	
END//
DELIMITER ;

-- Volcando estructura para procedimiento super_alfredo.pa_productos_por_categoria
DELIMITER //
CREATE PROCEDURE `pa_productos_por_categoria`( IN idCategoria INT, IN numReg INT )
BEGIN
	
	
	SELECT 
		u.id 'usuario_id', 
		u.nombre 'usuario_nombre', 
		p.id  'producto_id', 
		p.nombre 'producto_nombre', 
		precio, 
		imagen, 
		c.id 'categoria_id', 
		c.nombre 'categoria_nombre' 
	FROM 
		producto p , categoria c, usuario u 
	WHERE 
		p.id_categoria  = c.id AND p.id_usuario = u.id 	
		AND fecha_validado IS NOT NULL 
		AND c.id = idCategoria  -- filtramos por el id de la categoria
		ORDER BY p.id DESC LIMIT numReg ;	
	
END//
DELIMITER ;

-- Volcando estructura para tabla super_alfredo.personas
CREATE TABLE IF NOT EXISTS `personas` (
  `nombre` varchar(50) DEFAULT NULL,
  `empresa` varchar(50) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `telefono` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `numero_personal` varchar(50) DEFAULT NULL,
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla super_alfredo.personas: ~95 rows (aproximadamente)
DELETE FROM `personas`;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` (`nombre`, `empresa`, `fecha_nacimiento`, `telefono`, `email`, `numero_personal`) VALUES
	('Magee', 'Amet Risus LLC', '2019-03-21', '(014644) 35372', 'Quisque@Donec.ca', '16480805 5737'),
	('Kermit', 'Eu Ultrices Sit Institute', '2021-02-19', '07833 704365', 'urna.suscipit@Nullamvelitdui.ca', '16800511 2696'),
	('Oleg', 'Et Libero Proin Institute', '2021-02-27', '(016977) 8497', 'egestas.a@sapien.ca', '16000321 4483'),
	('Cassidy', 'Dolor Donec Fringilla Foundation', '2019-11-18', '0800 1111', 'metus@posuere.co.uk', '16220610 4107'),
	('Aaron', 'Ultrices Sit Amet Institute', '2020-08-01', '(029) 5692 5522', 'hendrerit@congueIn.net', '16920711 2666'),
	('Hedwig', 'Vivamus Sit Amet PC', '2020-09-21', '(01114) 796988', 'urna.Nunc@pretiumaliquet.org', '16060504 4254'),
	('Alan', 'Velit Inc.', '2019-09-18', '0823 656 1652', 'consectetuer.rhoncus@convallis.co.uk', '16121130 9222'),
	('Emery', 'Ipsum Incorporated', '2020-03-25', '0845 46 42', 'felis@vitaeposuere.org', '16340516 2904'),
	('Macy', 'Eget LLC', '2020-09-24', '0845 46 44', 'sed@etpede.ca', '16640324 0499'),
	('Walker', 'Mi Eleifend Corporation', '2019-04-30', '(016977) 7321', 'a@fames.org', '16890809 3142'),
	('Xavier', 'Pellentesque A Corp.', '2019-05-27', '056 4837 6400', 'Donec@euenimEtiam.net', '16850219 0682'),
	('Dale', 'Vehicula Inc.', '2019-10-14', '070 0975 7149', 'lectus@auctorvitaealiquet.com', '16420823 7430'),
	('Cole', 'Ut Lacus Corp.', '2019-05-07', '0991 287 1462', 'dui.Cras.pellentesque@ipsumdolor.com', '16580426 9495'),
	('Zephr', 'Ridiculus Mus Donec Corporation', '2019-04-25', '0381 992 9618', 'sem.eget.massa@liberoettristique.ca', '16041212 2210'),
	('Charissa', 'A Auctor Non Corp.', '2019-05-25', '070 7555 0912', 'Class@estacmattis.co.uk', '16520502 0190'),
	('Lance', 'Quisque Fringilla Limited', '2020-04-26', '056 4769 5917', 'massa.Quisque.porttitor@non.org', '16140702 9923'),
	('David', 'Ultricies Adipiscing Enim Ltd', '2020-09-20', '076 2136 0696', 'Ut.nec@metusIn.net', '16380804 2182'),
	('Ezra', 'Vel Est Industries', '2020-07-04', '055 1520 6504', 'justo.Praesent.luctus@afacilisisnon.org', '16580330 3394'),
	('Quinn', 'Nunc Sed PC', '2020-05-08', '0919 186 2967', 'conubia.nostra.per@primisinfaucibus.co.uk', '16661123 0464'),
	('Cherokee', 'Quisque Imperdiet Erat Limited', '2021-02-23', '(016738) 91265', 'ultrices.sit@bibendumsed.org', '16700420 0759'),
	('Cody', 'Molestie Tellus Aenean Industries', '2021-02-11', '0346 671 6570', 'varius.orci.in@a.org', '16430720 0628'),
	('Katelyn', 'Tristique Senectus Ltd', '2019-10-04', '070 3800 6293', 'felis@ultrices.edu', '16121119 5118'),
	('Carly', 'Non Limited', '2020-04-02', '(01039) 947781', 'porta@eratvolutpatNulla.edu', '16061022 3059'),
	('Asher', 'Lorem Eu Foundation', '2019-12-24', '0988 123 1201', 'neque.Sed.eget@velconvallisin.edu', '16280208 4422'),
	('Larissa', 'Libero Nec Ligula Company', '2020-03-04', '070 3510 5809', 'Duis@amifringilla.net', '16170616 5725'),
	('Teegan', 'Sed LLP', '2019-07-09', '(01130) 300631', 'enim@lacus.com', '16341126 1484'),
	('Courtney', 'Duis PC', '2019-06-03', '(016977) 4627', 'egestas.Sed@bibendumullamcorperDuis.com', '16090905 6665'),
	('Rhona', 'Ut Cursus Luctus Company', '2019-10-08', '07624 259077', 'viverra.Maecenas.iaculis@liberodui.ca', '16050418 5174'),
	('Caldwell', 'Nunc Corporation', '2020-07-12', '(013143) 90182', 'purus.Nullam.scelerisque@ametrisusDonec.co.uk', '16781022 5826'),
	('Wendy', 'Feugiat Tellus Lorem Foundation', '2020-03-26', '0849 069 1762', 'nisl@enimEtiam.co.uk', '16451003 5928'),
	('Rajah', 'Fringilla Est Mauris Industries', '2020-03-17', '0387 420 4205', 'dui@lobortis.net', '16730804 1867'),
	('Evan', 'Per Inceptos LLC', '2019-10-30', '(01701) 433407', 'senectus.et.netus@faucibus.co.uk', '16851204 5926'),
	('Danielle', 'Pharetra Felis Corp.', '2021-03-07', '0304 160 8887', 'ac.risus.Morbi@amet.ca', '16250805 8670'),
	('Camilla', 'Orci Foundation', '2020-10-06', '0800 503 7685', 'cursus.et@tinciduntorci.com', '16381007 8836'),
	('Tana', 'Elit Sed Consequat Corporation', '2019-03-17', '0800 1111', 'amet.luctus.vulputate@anteVivamus.co.uk', '16440524 2258'),
	('Lunea', 'Elementum Industries', '2019-06-21', '070 1947 8693', 'lobortis@ametanteVivamus.edu', '16691130 7988'),
	('Leslie', 'Magna Sed LLP', '2019-08-18', '(0113) 657 5404', 'Pellentesque.ultricies@lobortisClass.com', '16070118 4806'),
	('Cruz', 'Eu Sem Pellentesque LLP', '2019-05-12', '0800 429768', 'et.netus@Quisque.net', '16670727 3774'),
	('Eden', 'Urna Vivamus Corporation', '2019-05-25', '0500 275174', 'ullamcorper@enim.com', '16280818 8888'),
	('Byron', 'Tellus PC', '2020-06-22', '(011138) 50933', 'et.ipsum.cursus@estNunc.org', '16060813 1140'),
	('Ryder', 'Sit Amet Incorporated', '2019-05-29', '(018523) 23300', 'consequat@ornaretortor.org', '16810307 7783'),
	('Ronan', 'Sed Corp.', '2019-06-26', '07624 054658', 'Donec.fringilla@nibhAliquamornare.net', '16940118 7530'),
	('Norman', 'Ad Litora Torquent Associates', '2020-07-04', '0866 090 1995', 'sed.orci.lobortis@a.co.uk', '16310127 4805'),
	('Whoopi', 'Non Ante Bibendum Consulting', '2019-07-30', '0800 204902', 'laoreet.posuere.enim@magnaDuis.edu', '16500710 5355'),
	('Honorato', 'Sed Id Foundation', '2020-01-12', '(0131) 955 9734', 'sed.dolor.Fusce@id.edu', '16510325 8207'),
	('Tallulah', 'Nec Quam Curabitur Inc.', '2019-10-30', '055 7769 1979', 'natoque.penatibus.et@Praesentinterdumligula.co.uk', '16081006 1093'),
	('Gloria', 'Integer Tincidunt Aliquam LLP', '2019-04-29', '070 4052 7026', 'elit.Nulla@intempuseu.org', '16940418 9442'),
	('Ira', 'Nascetur Ridiculus Mus Corporation', '2019-08-26', '076 3736 3347', 'magna@scelerisqueneque.org', '16370321 4431'),
	('Barry', 'Enim Gravida Sit Corp.', '2019-11-06', '(01673) 02368', 'malesuada.vel.convallis@ac.co.uk', '16381122 4066'),
	('Callum', 'At Inc.', '2020-01-30', '0800 794435', 'sollicitudin.orci@nisiMauris.org', '16520514 2747'),
	('Zia', 'Nunc Ullamcorper PC', '2020-05-15', '070 9113 3299', 'nec@nonummy.com', '16740217 9738'),
	('Logan', 'In Mi Pede Corp.', '2021-03-15', '(01643) 208822', 'odio.auctor.vitae@elit.org', '16090917 8097'),
	('Xena', 'Enim Incorporated', '2020-11-02', '07624 004571', 'Aliquam@elementumat.ca', '16901213 5399'),
	('Paul', 'Mi Lacinia Mattis Corporation', '2020-12-06', '0500 074019', 'Quisque.ornare@posuereatvelit.edu', '16740511 7339'),
	('Elmo', 'Phasellus Corp.', '2020-12-08', '0394 786 2174', 'auctor@risusat.org', '16300706 7634'),
	('Gil', 'Sem Ut Dolor Incorporated', '2020-10-01', '(017601) 78286', 'ante.Vivamus@vestibulum.co.uk', '16741205 6694'),
	('Eliana', 'Lectus Pede Limited', '2020-07-14', '0800 221064', 'auctor.non@lacus.co.uk', '16851010 6225'),
	('Ursula', 'Etiam Ligula Tortor Corporation', '2019-03-17', '0800 1111', 'nunc@duiFusce.org', '16720526 4000'),
	('Colby', 'Faucibus Leo LLC', '2019-09-18', '0800 1111', 'orci.Phasellus@egetnisi.com', '16520811 8132'),
	('Basil', 'Nulla Facilisi Sed Inc.', '2019-08-25', '0800 1111', 'Duis.ac@inmolestie.net', '16080628 7439'),
	('Justine', 'Egestas Corporation', '2020-02-20', '(010366) 89884', 'aliquet.magna.a@vulputateposuere.co.uk', '16880605 9849'),
	('Craig', 'Nunc Interdum Feugiat Incorporated', '2020-10-29', '0800 091830', 'dui@massaSuspendisseeleifend.ca', '16791113 8126'),
	('Candace', 'Nunc Ut Inc.', '2020-04-19', '(024) 4762 1768', 'lorem.auctor.quis@egestashendrerit.ca', '16130711 6523'),
	('Burton', 'Amet Institute', '2019-08-30', '(0101) 676 9473', 'sit.amet@Integerin.ca', '16420702 2163'),
	('Sean', 'Semper Et Lacinia Inc.', '2021-03-09', '07546 555927', 'aliquet.diam@Suspendissealiquet.edu', '16870506 1813'),
	('Urielle', 'Non Magna Incorporated', '2020-01-17', '(01523) 769372', 'mauris.elit@imperdieterat.edu', '16130624 7451'),
	('Tanek', 'Orci Luctus Et LLC', '2020-12-08', '07500 463195', 'in@insodales.net', '16730208 5639'),
	('Guy', 'Velit Inc.', '2020-11-23', '(016977) 0514', 'sed.facilisis.vitae@nasceturridiculusmus.com', '16301110 8762'),
	('Uta', 'Penatibus Et Associates', '2020-11-26', '0500 599638', 'natoque@ipsumportaelit.net', '16490406 5085'),
	('Desiree', 'Dignissim Limited', '2020-07-28', '0845 46 41', 'quam@ametconsectetuer.ca', '16630905 7153'),
	('Kenneth', 'At Arcu Company', '2019-04-20', '(016977) 3603', 'sed@faucibus.edu', '16321019 8135'),
	('Talon', 'Donec Tempus Lorem Limited', '2019-12-23', '0812 011 7170', 'eget.magna.Suspendisse@odiovelest.org', '16560205 0238'),
	('Yuli', 'Pellentesque Ut LLP', '2020-09-19', '076 4611 0255', 'enim@lobortis.org', '16670910 3011'),
	('Jennifer', 'Mauris Suspendisse Corp.', '2019-11-30', '07810 645686', 'lacinia.vitae@consectetueradipiscing.org', '16740624 8158'),
	('Camille', 'Pede Cras PC', '2020-07-02', '0500 807918', 'cursus@risus.co.uk', '16240415 0159'),
	('Amos', 'Orci Foundation', '2020-07-22', '056 0733 8679', 'semper.pretium@tacitisociosqu.com', '16121210 5744'),
	('Fallon', 'Mauris Institute', '2019-07-16', '(01384) 489127', 'rhoncus.Donec@Phasellus.org', '16680803 5825'),
	('Micah', 'Nec Consulting', '2020-11-02', '(024) 2128 6389', 'taciti.sociosqu@feugiat.co.uk', '16371212 5883'),
	('Darius', 'Aliquam Rutrum Lorem Consulting', '2019-06-27', '(014922) 11255', 'sagittis@arcu.edu', '16140520 0492'),
	('Audra', 'Donec Luctus Incorporated', '2019-09-19', '0851 086 6511', 'risus.Nunc.ac@magnaLoremipsum.co.uk', '16030803 2747'),
	('Jonas', 'Fringilla Cursus Purus Ltd', '2020-07-01', '07063 251896', 'egestas@parturientmontes.edu', '16540927 8156'),
	('Daryl', 'Dui Cras Institute', '2019-10-21', '0800 447 0181', 'quam.dignissim.pharetra@tortordictum.net', '16150822 9620'),
	('Xyla', 'Adipiscing Ligula Aenean Corp.', '2021-02-22', '(019460) 03687', 'Nulla.semper.tellus@etnetus.co.uk', '16380519 1479'),
	('Caesar', 'Donec Feugiat Metus Associates', '2019-10-03', '(0118) 154 9777', 'ut.mi@tinciduntcongue.net', '16391224 7842'),
	('Carolyn', 'Enim Nec Tempus Foundation', '2020-03-11', '0300 648 7645', 'convallis.ante.lectus@atvelitPellentesque.ca', '16361204 7575'),
	('Wanda', 'Arcu Imperdiet Ullamcorper Consulting', '2021-03-08', '0800 799454', 'Nullam@mattisInteger.com', '16260913 0816'),
	('Porter', 'Placerat Velit PC', '2019-03-23', '07857 033642', 'lectus.justo@pharetrased.co.uk', '16440206 5520'),
	('Jordan', 'Natoque Penatibus Et PC', '2019-07-27', '0994 252 6078', 'eu.ultrices.sit@odio.net', '16730926 9954'),
	('Cadman', 'Eget PC', '2019-12-27', '(016977) 6196', 'molestie@Duis.edu', '16461219 1819'),
	('Hiram', 'Id Ante Institute', '2020-05-12', '(01495) 69931', 'facilisi.Sed@sitametmassa.net', '16421210 6498'),
	('Oscar', 'Pede Ac Ltd', '2019-03-20', '(016977) 5617', 'enim.diam@risusNulla.ca', '16710319 2279'),
	('Levi', 'Adipiscing LLP', '2021-03-04', '07292 218989', 'aliquet.metus@sitametdapibus.edu', '16940224 1641'),
	('Bell', 'Vel Corp.', '2020-03-27', '0308 232 0867', 'placerat@Donecporttitortellus.co.uk', '16840904 3976'),
	('Shoshana', 'Ultricies Sem Magna Corporation', '2020-09-23', '07258 154345', 'amet@nuncQuisqueornare.edu', '16150512 9088'),
	('Lani', 'Consectetuer Ltd', '2019-10-16', '0800 091 0384', 'Nullam@gravida.net', '16701114 6201');
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;

-- Volcando estructura para tabla super_alfredo.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_usuario` int NOT NULL,
  `precio` decimal(10,2) NOT NULL DEFAULT '0.00',
  `imagen` varchar(255) NOT NULL DEFAULT 'https://picsum.photos/100/100',
  `id_categoria` int NOT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_validado` datetime DEFAULT NULL COMMENT 'si tiene valor NULL este producto no es visible en la parte publica, tiene que ser validado por un administrador',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_usuario` (`id_usuario`),
  KEY `FK_categoria` (`id_categoria`),
  CONSTRAINT `FK_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FK_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla super_alfredo.producto: ~16 rows (aproximadamente)
DELETE FROM `producto`;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id`, `nombre`, `id_usuario`, `precio`, `imagen`, `id_categoria`, `fecha_creacion`, `fecha_validado`) VALUES
	(13, 'macarrones con pesto', 6, 6.20, 'imagenes/', 2, '2020-07-13 12:26:38', NULL),
	(15, 'acelgas con Jamon con brillo', 6, 0.00, 'https://i.blogs.es/6b70d4/acelgas-arco-jamon/1366_2000.jpg', 1, '2020-07-13 12:26:38', NULL),
	(16, 'patatas gordas', 6, 1.00, 'https://ep01.epimg.net/elcomidista/imagenes/2017/02/22/articulo/1487804099_363696_1487804800_sumario_normal.jpg', 1, '2020-07-13 12:26:38', NULL),
	(26, 'alubias a la vizcaina', 6, 7.00, 'imagenes/', 2, '2020-07-13 12:26:38', NULL),
	(27, 'Tortilla de patatas', 6, 2.00, 'https://www.hogarmania.com/archivos/201610/tortilla-patatas-xl-668x400x80xX.jpg', 2, '2020-07-13 12:26:38', '2020-07-13 12:58:23'),
	(28, 'prueba', 6, 10.00, 'https://picsum.photos/100/100', 3, '2020-07-22 10:09:05', NULL),
	(138, 'Chocolate', 69, 175.00, 'http://lorempixel.com/150/150/abstract/', 1, '1993-09-05 11:16:44', '2002-03-08 17:03:13'),
	(139, 'PaleGoldenRod', 70, 51.00, 'http://lorempixel.com/150/150/abstract/', 2, '2019-11-23 19:50:09', '1981-08-23 09:03:18'),
	(140, 'LightSkyBlue', 71, 42.00, 'http://lorempixel.com/150/150/abstract/', 3, '2005-07-28 09:17:18', '2001-08-22 16:33:19'),
	(141, 'DarkGray', 72, 65.00, 'http://lorempixel.com/150/150/abstract/', 1, '2017-07-30 18:55:07', '2002-02-17 15:24:56'),
	(142, 'SeaGreen', 73, 189.00, 'http://lorempixel.com/150/150/abstract/', 2, '1989-05-06 08:16:47', '2013-10-20 02:00:05'),
	(143, 'Cyan', 74, 148.00, 'http://lorempixel.com/150/150/abstract/', 3, '2002-04-21 13:12:39', '2008-02-06 19:13:55'),
	(144, 'Violet', 75, 169.00, 'http://lorempixel.com/150/150/abstract/', 1, '1987-11-01 02:10:31', '1992-09-25 15:23:47'),
	(145, 'MediumTurquoise', 76, 193.00, 'http://lorempixel.com/150/150/abstract/', 2, '2011-05-29 01:34:20', '2004-06-24 15:12:09'),
	(146, 'Black', 77, 105.00, 'http://lorempixel.com/150/150/abstract/', 3, '1972-09-20 23:24:28', '2019-04-02 10:24:08'),
	(147, 'Tan', 78, 185.00, 'http://lorempixel.com/150/150/abstract/', 1, '1973-03-11 05:31:15', '1983-04-25 13:24:51');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

-- Volcando estructura para tabla super_alfredo.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '1: Usuario normal   2: Administrador',
  `nombre` varchar(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla super_alfredo.rol: ~2 rows (aproximadamente)
DELETE FROM `rol`;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`id`, `nombre`) VALUES
	(2, 'administrador'),
	(1, 'usuario');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

-- Volcando estructura para tabla super_alfredo.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `contrasenia` varchar(50) NOT NULL DEFAULT '0',
  `id_rol` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `FK_rol` (`id_rol`),
  CONSTRAINT `FK_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla super_alfredo.usuario: ~14 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nombre`, `contrasenia`, `id_rol`) VALUES
	(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 2),
	(6, 'pepe', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(7, 'manolito gafotas 77', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(8, 'Dummy', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(69, 'Prof. Gabriel Aufderhar MD', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(70, 'Ryan Lynch', 'e10adc3949ba59abbe56e057f20f883e', 2),
	(71, 'Marta Reynolds', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(72, 'Devon Cartwright', 'e10adc3949ba59abbe56e057f20f883e', 2),
	(73, 'Mariam Olson I', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(74, 'Francisco West', 'e10adc3949ba59abbe56e057f20f883e', 2),
	(75, 'Mr. Albin Reinger DDS', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(76, 'Adan Balistreri', 'e10adc3949ba59abbe56e057f20f883e', 2),
	(77, 'Dr. Raymond Ward DDS', 'e10adc3949ba59abbe56e057f20f883e', 1),
	(78, 'Prof. Linnea Little', 'e10adc3949ba59abbe56e057f20f883e', 2);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

-- Volcando estructura para vista super_alfredo.v_usuario_productos
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `v_usuario_productos` (
	`id_usuario` INT(10,0) NOT NULL,
	`aprobados` BIGINT(19,0) NOT NULL,
	`pendientes` DECIMAL(23,0) NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista super_alfredo.v_usuario_productos
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `v_usuario_productos`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `v_usuario_productos` AS select `p`.`id_usuario` AS `id_usuario`,count(`p`.`fecha_validado`) AS `aprobados`,sum((`p`.`fecha_validado` is null)) AS `pendientes` from `producto` `p` group by `p`.`id_usuario`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
