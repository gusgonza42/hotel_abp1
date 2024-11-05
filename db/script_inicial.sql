DROP
    DATABASE IF EXISTS hotel;
CREATE
    DATABASE IF NOT EXISTS hotel
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;
USE hotel;

-- usar utf8mb4 para las inserciones
SET NAMES 'utf8mb4';

CREATE TABLE usuarios
(
    id_usuario     INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100),
    email          VARCHAR(100) UNIQUE,
    password       VARCHAR(100),
    rol            ENUM ('admin', 'recepcionista', 'cliente'),
    estado         ENUM ('inactivo', 'activo') DEFAULT 'activo',
    fecha_registro TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE habitaciones
(
    id_habitacion   INT AUTO_INCREMENT PRIMARY KEY,
    tipo_habitacion ENUM ('sencilla', 'doble', 'suite'),
    imagen          BLOB,
    precio          DECIMAL(10, 2),
    estado          ENUM ('disponible', 'ocupada', 'mantenimiento') DEFAULT 'disponible'
);

CREATE TABLE actividades
(
    id_actividad     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_actividad VARCHAR(100),
    descripcion      TEXT,
    imagen           BLOB,
    precio           DECIMAL(10, 2),
    cupo             INT,
    fecha_actividad  DATE
);

CREATE TABLE reserva_habitaciones
(
    id_reserva_habitacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario            INT,
    id_habitacion         INT,
    fecha_entrada         DATE,
    fecha_salida          DATE,
    estado                ENUM ('reservado', 'cancelado', 'completado') DEFAULT 'reservado',
    fecha_reserva         TIMESTAMP                                     DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reserva_habitaciones_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario),
    CONSTRAINT fk_reserva_habitaciones_habitacion FOREIGN KEY (id_habitacion) REFERENCES habitaciones (id_habitacion)
);

CREATE TABLE reserva_actividades
(
    id_reserva_actividad INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario           INT,
    id_actividad         INT,
    estado               ENUM ('reservado', 'cancelado', 'completado') DEFAULT 'reservado',
    fecha_reserva        TIMESTAMP                                     DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reserva_actividades_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario),
    CONSTRAINT fk_reserva_actividades_actividad FOREIGN KEY (id_actividad) REFERENCES actividades (id_actividad)
);

-- Inserciones con caracteres especiales <<a, password1, password2, password3>>
INSERT INTO usuarios (nombre, email, password, rol)
VALUES ('AdminTester', 'a', 'gAPPpM8BLUOADhSI1TmslQ==', 'admin'),
       ('Juan Pérez', 'juan@example.com', '7gvoBpoXhPmjxcLgI6Atmg==', 'cliente'),
       ('Maria López', 'maria@example.com', 'Dlj1Rspas9QQ8SOwWWRbwA==', 'admin'),
       ('Carlos Rodríguez', 'carlos@example.com', 'o689qseK/uF6YYOl6nS6+w==', 'recepcionista');

INSERT INTO habitaciones (tipo_habitacion, precio, estado)
VALUES ('sencilla', 50.00, 'disponible'),
       ('doble', 80.00, 'disponible'),
       ('suite', 150.00, 'disponible');

INSERT INTO actividades (nombre_actividad, descripcion, precio, cupo, fecha_actividad)
VALUES ('Yoga en la playa', 'Clase de yoga al aire libre frente al mar.', 20.00, 10, '2024-10-01'),
       ('Cata de vinos', 'Degustación de una selección de vinos locales.', 50.00, 5, '2024-10-03'),
       ('Caminata al amanecer', 'Disfruta de una caminata al amanecer por los alrededores del hotel.', 15.00, 8,
        '2024-10-05'),
       ('Clases de cocina', 'Aprende a cocinar los platos típicos de la región en una clase divertida.', 30.00, 12,
        '2024-10-12'),
       ('Baile de salón', 'Sesión de baile para principiantes y expertos.', 10.00, 20, '2024-10-15'),
       ('Curso de cerámica', 'Crea tus propias piezas de cerámica en este taller artesanal.', 50.00, 6, '2024-10-20'),
       ('Cata de quesos', 'Disfruta de una cata de quesos locales con explicaciones de un experto.', 35.00, 10,
        '2024-10-25'),
       ('Paseo en bote', 'Explora el lago en un paseo en bote guiado.', 25.00, 8, '2024-10-28'),
       ('Taller de escritura creativa', 'Deja volar tu imaginación en un taller de escritura creativa.', 15.00, 12,
        '2024-10-30');

INSERT INTO reserva_habitaciones (id_usuario, id_habitacion, fecha_entrada, fecha_salida, estado)
VALUES
    -- AdminTester
    (1, 1, '2024-11-21', '2024-11-26', 'reservado'),  -- Reservado, tres semanas desde hoy
    (1, 2, '2024-08-10', '2024-08-15', 'cancelado'),  -- Cancelado, fecha pasada
    (1, 3, '2024-09-05', '2024-09-10', 'completado'), -- Completado, fecha pasada

    -- Juan Pérez
    (2, 2, '2024-11-21', '2024-11-26', 'reservado'),  -- Reservado, tres semanas desde hoy
    (2, 1, '2024-08-20', '2024-08-25', 'cancelado'),  -- Cancelado, fecha pasada
    (2, 3, '2024-09-10', '2024-09-15', 'completado'), -- Completado, fecha pasada

    -- Maria López
    (3, 3, '2024-11-22', '2024-11-27', 'reservado'),  -- Reservado, tres semanas desde hoy
    (3, 1, '2024-08-25', '2024-08-30', 'cancelado'),  -- Cancelado, fecha pasada
    (3, 2, '2024-09-18', '2024-09-23', 'completado'), -- Completado, fecha pasada

    -- Carlos Rodríguez
    (4, 1, '2024-11-22', '2024-11-27', 'reservado'),  -- Reservado, tres semanas desde hoy
    (4, 3, '2024-07-15', '2024-07-20', 'cancelado'),  -- Cancelado, fecha pasada
    (4, 2, '2024-09-01', '2024-09-05', 'completado'); -- Completado, fecha pasada


INSERT INTO reserva_actividades (id_usuario, id_actividad, estado)
VALUES
    -- AdminTester
    (1, 1, 'reservado'),  -- Actividad futura
    (1, 2, 'reservado'),  -- Actividad futura
    (1, 3, 'cancelado'),  -- Actividad pasada
    (1, 4, 'completado'), -- Actividad pasada
    (1, 5, 'completado'), -- Actividad pasada

    -- Juan Pérez
    (2, 2, 'reservado'),  -- Actividad futura
    (2, 4, 'reservado'),  -- Actividad futura
    (2, 6, 'cancelado'),  -- Actividad pasada
    (2, 7, 'completado'), -- Actividad pasada
    (2, 1, 'completado'), -- Actividad pasada

    -- Maria López
    (3, 3, 'reservado'),  -- Actividad futura
    (3, 5, 'reservado'),  -- Actividad futura
    (3, 8, 'cancelado'),  -- Actividad pasada
    (3, 2, 'completado'), -- Actividad pasada
    (3, 6, 'completado'), -- Actividad pasada

    -- Carlos Rodríguez
    (4, 4, 'reservado'),  -- Actividad futura
    (4, 7, 'reservado'),  -- Actividad futura
    (4, 5, 'cancelado'),  -- Actividad pasada
    (4, 3, 'completado'), -- Actividad pasada
    (4, 8, 'completado'); -- Actividad pasada