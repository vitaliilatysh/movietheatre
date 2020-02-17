drop table if exists Users;

create TABLE Users (
   id bigint auto_increment,
   firstName VARCHAR(50) NOT NULL,
   lastName VARCHAR(20) NOT NULL,
   email    VARCHAR(20) NOT NULL,
   birthDate DATE
);