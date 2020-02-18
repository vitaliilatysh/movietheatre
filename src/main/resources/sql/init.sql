drop table if exists Users, Events, Discounts;

create TABLE Users (
   id           int             auto_increment,
   firstName    VARCHAR(50)     NOT NULL,
   lastName     VARCHAR(20)     NOT NULL,
   email        VARCHAR(20)     NOT NULL,
   birthDate    DATE
);

create TABLE Events (
   id           int             auto_increment,
   name         VARCHAR(50)     NOT NULL,
   rating       VARCHAR(20)     NOT NULL,
   basePrice    double          NOT NULL
);

create TABLE Discounts (
   id           int             auto_increment,
   type         VARCHAR(50)     NOT NULL,
   user_id      int,
   foreign key (user_id) references Users(id) on delete cascade
);