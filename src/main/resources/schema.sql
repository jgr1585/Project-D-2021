drop table if exists booking_for_category;
drop table if exists booking;
drop table if exists category;

create table booking (
    booking_number int auto_increment not null,
    check_in datetime not null,
    check_out datetime not null,

    primary key (booking_number),
    check check_in < check_out
);

create table category (
    title varchar not null,
    description varchar,
    price_per_night decimal not null,

    primary key (title),
    check price_per_night > 0
);

create table booking_for_category (
    booking_number int not null,
    category_title varchar not null,
    number_of_rooms int not null,

    primary key (booking_number, category_title),
    foreign key (booking_number) references booking(booking_number),
    foreign key (category_title) references category(title),
    check number_of_rooms > 0
);