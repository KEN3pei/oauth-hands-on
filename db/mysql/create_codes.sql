CREATE TABLE `codes` (
  `code` varchar(100) NOT NULL,
  `query` JSON,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
