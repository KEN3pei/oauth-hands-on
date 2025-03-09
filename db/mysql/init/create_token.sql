CREATE TABLE `token` (
  `access_token` varchar(500) NOT NULL,
  `client_id` varchar(100) NOT NULL,
  `query` JSON,
  PRIMARY KEY (`access_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
