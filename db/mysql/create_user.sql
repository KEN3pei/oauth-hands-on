-- oauth_db.users definition

CREATE TABLE `users` (
  `id` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `introduce` varchar(500) DEFAULT NULL,
  `password` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;