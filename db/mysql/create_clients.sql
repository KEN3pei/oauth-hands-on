CREATE TABLE `clients` (
  `client_id` varchar(100) NOT NULL,
  `query` JSON,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
