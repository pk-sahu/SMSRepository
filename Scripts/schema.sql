CREATE TABLE STD_INFO (
  ID int(10) NOT NULL AUTO_INCREMENT,
  NAME varchar(25) DEFAULT NULL,
  GENDER varchar(10) DEFAULT NULL,
  BIRTHPLACE varchar(20) DEFAULT NULL,
  DOB date DEFAULT NULL,
  CASTE varchar(15) DEFAULT NULL,
  PHONE bigint(11) DEFAULT NULL,
  PRIMARY KEY (ID)
);

create table users (
    username varchar(50) not null,
    country varchar(30),
    enabled smallint,
    full_name varchar(100),
    password varchar(800) not null,
    role varchar(50),
    primary key (username)
);
INSERT INTO `users` (`username`, `password`, `full_name`, `role`, `country`, `enabled`) VALUES 
('kedar', '$2a$10$Pbqq0rUvu3jZGoB/.vzaheaDwUmoGSoSQY1NNOh1.KVit3A7qtuHS', 'Kedar V', 'ROLE_ADMIN', 'US', 1),
('akshay', '$2a$10$w/wNygSCaypyFev2qOE68e62cjSbv/IgUdMen814m08JCZlXX079e', 'Akshay D', 'ROLE_USER', 'India', 1); 
