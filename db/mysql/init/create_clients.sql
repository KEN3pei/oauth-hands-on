CREATE TABLE `clients` (
  `client_id` varchar(100) NOT NULL,
  `secret` varchar(100) NOT NULL,
  `redirect_uri` varchar(100) NOT NULL,
  `scope` varchar(100) NOT NULL,
  `response_type` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
