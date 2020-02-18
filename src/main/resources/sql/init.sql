drop table if exists Users, Events;

create TABLE Users (
   id bigint auto_increment,
   firstName VARCHAR(50) NOT NULL,
   lastName VARCHAR(20) NOT NULL,
   email    VARCHAR(20) NOT NULL,
   birthDate DATE
);

create TABLE Events (
   id bigint auto_increment,
   name VARCHAR(50) NOT NULL,
   rating VARCHAR(20) NOT NULL,
   basePrice    double NOT NULL
);