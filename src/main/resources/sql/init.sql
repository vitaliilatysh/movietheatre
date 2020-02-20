drop table if exists Users, Events, Discounts, Auditoriums, Seats, Tickets;

create table Users (
    id              int             auto_increment,
    firstName       varchar(50)     not null,
    lastName        varchar(50)     not null,
    email           varchar(50)     not null,
    birthDate       date
);

create table Events (
    id              int             auto_increment,
    name            varchar(50)     not null,
    rating          varchar(10)     not null,
    basePrice       double          not null
);

create table Discounts (
    id              int             auto_increment,
    type            varchar(10)     not null,
    user_id         int,
    foreign key (user_id)       references Users(id) on delete cascade
);

create table Auditoriums(
    id              int             auto_increment,
    name            varchar(50)     not null
);

create table Seats (
    id              int             auto_increment,
    number          int             not null,
    type            varchar(10)     not null,
    auditorium_id   int             not null,
    foreign key (auditorium_id) references Auditoriums(id) on delete cascade
);

create table Tickets (
    id              int             auto_increment,
    user_id         int             not null,
    airDate         date            not null,
    booked          boolean         not null,
    event_id        int             not null,
    seat_id         int             not null,
    foreign key (event_id)      references Events(id) on delete cascade,
    foreign key (seat_id)       references Seats(id) on delete cascade,
    foreign key (user_id)       references Users(id) on delete cascade

);