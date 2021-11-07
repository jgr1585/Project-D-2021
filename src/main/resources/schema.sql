drop table if exists booking_for_category;
drop table if exists booking;
drop table if exists category;

create table booking (
    id int auto_increment not null,

    booking_id varchar not null,
    check_in datetime not null,
    check_out datetime not null,

    guest_name varchar not null,
    guest_address varchar not null,

    representative_name varchar not null,
    representative_email varchar not null,
    representative_address varchar not null,
    representative_phone varchar not null,

    primary key (id),
    unique (booking_id),
    check check_in < check_out
);

create table category (
    id int auto_increment not null,

    category_id varchar not null,
    title varchar not null,
    description varchar,
    price_per_night decimal not null,

    primary key (id),
    unique (category_id),
    check price_per_night > 0
);

create table booking_for_category (
    booking_id int not null,
    category_id varchar not null,
    number_of_rooms int not null,

    primary key (booking_id, category_id),
    foreign key (booking_id) references booking(id) on delete cascade,
    foreign key (category_id) references category(id) on delete cascade,
    check number_of_rooms > 0
);