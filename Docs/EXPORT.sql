-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: pro.freedb.tech
-- Tiempo de generación: 04-06-2025 a las 22:56:21
-- Versión del servidor: 8.0.42-0ubuntu0.22.04.1
-- Versión de PHP: 8.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `CONTROLESCOLAR`
--
CREATE DATABASE IF NOT EXISTS `CONTROLESCOLAR` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `CONTROLESCOLAR`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Alumno`
--

CREATE TABLE `Alumno` (
  `idAlumno` int NOT NULL,
  `no_control` varchar(10) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `primer_apellido` varchar(100) NOT NULL,
  `segundo_apellido` varchar(100) DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `correo_electronico` varchar(100) NOT NULL,
  `grado_alumno` varchar(45) NOT NULL,
  `no_telefono` char(10) NOT NULL,
  `carrera` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Alumno`
--

INSERT INTO `Alumno` (`idAlumno`, `no_control`, `nombre`, `primer_apellido`, `segundo_apellido`, `fecha_nacimiento`, `correo_electronico`, `grado_alumno`, `no_telefono`, `carrera`) VALUES
(9, '202345', 'Abaroa', 'Ricardo', 'Nuñez', '2005-01-01', 'diego@gmail.com', '4', '6122472060', 'IDS'),
(12, '2004', 'Pedro', 'Arce', 'Davis', '2005-01-01', 'pedro@gmail.com', '4', '6545682378', 'IDS '),
(14, '215', 'Roberto', 'rgb', 'efjdwrf', '2005-01-01', 'rgooeg', '8', '6122172060', 'btemom'),
(16, '23245', 'Jose', 'Reyes', 'Hinojosa', '2005-01-01', 'rey@gmail.com', '8', '6124567897', 'Recursos Humanos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Alumno_has_Grupo`
--

CREATE TABLE `Alumno_has_Grupo` (
  `Alumno_idAlumno` int NOT NULL,
  `Grupo_idGrupo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Asignatura`
--

CREATE TABLE `Asignatura` (
  `idAsignatura` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Docente`
--

CREATE TABLE `Docente` (
  `idDocente` int NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `primer_apellido` varchar(100) NOT NULL,
  `segundo_apellido` varchar(100) DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `correo_electronico` varchar(100) NOT NULL,
  `materia` varchar(45) NOT NULL,
  `no_telefono` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Docente`
--

INSERT INTO `Docente` (`idDocente`, `nombres`, `primer_apellido`, `segundo_apellido`, `fecha_nacimiento`, `correo_electronico`, `materia`, `no_telefono`) VALUES
(1, 'Antonio', 'Geraldo', 'Lucero', '2025-06-18', 'geraldo@gmail.com', 'Español', '6122172060'),
(2, 'Veronica', 'Carrillo', NULL, '2025-06-16', 'vero@gmail.com', 'Inlges', '6121234567'),
(3, 'ramon', 'Nuñez', 'Diego', '2005-01-01', 'diego@gmail.com', 'Derecho', '6122172060'),
(4, 'Emilio', 'Nuñez', 'Abaroa ', '2005-01-01', 'as@gmail.com', 'Su6', '612172060'),
(5, 'Reni', 'Villag', 'Flore', '2005-01-01', 'villa@gmail.com', 'HCI', '6122172060'),
(564, 'Diego', 'Nuñez', 'Abaroa', '2005-01-01', 'abaroa@gmail.com', 'BASE', '6122172060');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Docente_has_Asignatura`
--

CREATE TABLE `Docente_has_Asignatura` (
  `Docente_idDocente` int NOT NULL,
  `Asignatura_idAsignatura` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Grupo`
--

CREATE TABLE `Grupo` (
  `idGrupo` int NOT NULL,
  `nombre_grupo` varchar(50) NOT NULL,
  `turno` enum('TM','TV') NOT NULL,
  `periodo` varchar(45) NOT NULL,
  `Asignatura_idAsignatura` int NOT NULL,
  `Docente_idDocente` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Grupo`
--

INSERT INTO `Grupo` (`idGrupo`, `nombre_grupo`, `turno`, `periodo`, `Asignatura_idAsignatura`, `Docente_idDocente`) VALUES
(1, 'IDS', 'TM', '2035', 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuario`
--

CREATE TABLE `Usuario` (
  `idUsuario` int NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `correo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Usuario`
--

INSERT INTO `Usuario` (`idUsuario`, `usuario`, `contrasena`, `correo`) VALUES
(1, 'Aabroa26', 'vildosola', 'emiliano@gmail.com'),
(2, 'diegoabaroa', 'vildosola', 'vildosola@gmail.com'),
(3, 'Karo', 'diego@gmail.com', 'vildosola'),
(4, 'Fernando', 'fernando', 'fernando@gmail.com'),
(5, '1', '1', '1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Alumno`
--
ALTER TABLE `Alumno`
  ADD PRIMARY KEY (`idAlumno`),
  ADD UNIQUE KEY `no_control_UNIQUE` (`no_control`),
  ADD UNIQUE KEY `idAlumno_UNIQUE` (`idAlumno`);

--
-- Indices de la tabla `Alumno_has_Grupo`
--
ALTER TABLE `Alumno_has_Grupo`
  ADD PRIMARY KEY (`Alumno_idAlumno`,`Grupo_idGrupo`),
  ADD KEY `fk_Alumno_has_Grupo_Grupo1_idx` (`Grupo_idGrupo`),
  ADD KEY `fk_Alumno_has_Grupo_Alumno_idx` (`Alumno_idAlumno`);

--
-- Indices de la tabla `Asignatura`
--
ALTER TABLE `Asignatura`
  ADD PRIMARY KEY (`idAsignatura`),
  ADD UNIQUE KEY `idAsignatura_UNIQUE` (`idAsignatura`);

--
-- Indices de la tabla `Docente`
--
ALTER TABLE `Docente`
  ADD PRIMARY KEY (`idDocente`),
  ADD UNIQUE KEY `idDocente_UNIQUE` (`idDocente`);

--
-- Indices de la tabla `Docente_has_Asignatura`
--
ALTER TABLE `Docente_has_Asignatura`
  ADD PRIMARY KEY (`Docente_idDocente`,`Asignatura_idAsignatura`),
  ADD KEY `fk_Docente_has_Asignatura_Asignatura1_idx` (`Asignatura_idAsignatura`),
  ADD KEY `fk_Docente_has_Asignatura_Docente1_idx` (`Docente_idDocente`);

--
-- Indices de la tabla `Grupo`
--
ALTER TABLE `Grupo`
  ADD PRIMARY KEY (`idGrupo`),
  ADD UNIQUE KEY `idGrupo_UNIQUE` (`idGrupo`),
  ADD KEY `fk_Grupo_Asignatura1_idx` (`Asignatura_idAsignatura`),
  ADD KEY `fk_Grupo_Docente1_idx` (`Docente_idDocente`);

--
-- Indices de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `usuario_UNIQUE` (`usuario`),
  ADD UNIQUE KEY `idUsuario_UNIQUE` (`idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Alumno`
--
ALTER TABLE `Alumno`
  MODIFY `idAlumno` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `Asignatura`
--
ALTER TABLE `Asignatura`
  MODIFY `idAsignatura` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Docente`
--
ALTER TABLE `Docente`
  MODIFY `idDocente` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=565;

--
-- AUTO_INCREMENT de la tabla `Grupo`
--
ALTER TABLE `Grupo`
  MODIFY `idGrupo` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  MODIFY `idUsuario` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Alumno_has_Grupo`
--
ALTER TABLE `Alumno_has_Grupo`
  ADD CONSTRAINT `fk_Alumno_has_Grupo_Alumno` FOREIGN KEY (`Alumno_idAlumno`) REFERENCES `Alumno` (`idAlumno`),
  ADD CONSTRAINT `fk_Alumno_has_Grupo_Grupo1` FOREIGN KEY (`Grupo_idGrupo`) REFERENCES `Grupo` (`idGrupo`);

--
-- Filtros para la tabla `Docente_has_Asignatura`
--
ALTER TABLE `Docente_has_Asignatura`
  ADD CONSTRAINT `fk_Docente_has_Asignatura_Docente1` FOREIGN KEY (`Docente_idDocente`) REFERENCES `Docente` (`idDocente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
