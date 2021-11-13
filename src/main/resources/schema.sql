drop table if exists booking_for_category;
drop table if exists booking;
drop table if exists category;

drop table if exists stay_in_room;
drop table if exists stay;
drop table if exists room;

create table room
(
    id          int auto_increment not null,
    room_id     varchar            not null,

    category_id int                not null,

    primary key (id),
    unique (room_id),
    foreign key (category_id) references category (id) on delete restrict,
);

create table stay
(
    id                        int auto_increment not null,
    stay_id                   varchar            not null,

    check_in                  datetime           not null,
    check_out                 datetime           not null,

    guest_first_name          varchar            not null,
    guest_last_name           varchar            not null,
    guest_street              varchar            not null,
    guest_zip                 varchar            not null,
    guest_city                varchar            not null,
    guest_country             varchar            not null,

    representative_first_name varchar            not null,
    representative_last_name  varchar            not null,
    representative_street     varchar            not null,
    representative_zip        varchar            not null,
    representative_city       varchar            not null,
    representative_country    varchar            not null,
    representative_email      varchar            not null,
    representative_phone      varchar            not null,

    primary key (id),
    unique (stay_id),
    check check_in < check_out
);

create table stay_in_room
(
    stay_id int not null,
    room_id int not null,

    foreign key (stay_id) references stay(id) on delete cascade,
    foreign key (room_id) references room(id) on delete restrict,
);

create table booking
(
    id                        int auto_increment not null,
    booking_id                varchar            not null,

    check_in                  datetime           not null,
    check_out                 datetime           not null,

    guest_first_name          varchar            not null,
    guest_last_name           varchar            not null,
    guest_street              varchar            not null,
    guest_zip                 varchar            not null,
    guest_city                varchar            not null,
    guest_country             varchar            not null,

    representative_first_name varchar            not null,
    representative_last_name  varchar            not null,
    representative_street     varchar            not null,
    representative_zip        varchar            not null,
    representative_city       varchar            not null,
    representative_country    varchar            not null,
    representative_email      varchar            not null,
    representative_phone      varchar            not null,

    primary key (id),
    unique (booking_id),
    check check_in < check_out
);

create table category
(
    id              int auto_increment not null,
    category_id     varchar            not null,

    title           varchar            not null,
    description     varchar,
    price_per_night decimal            not null,

    primary key (id),
    unique (category_id),
    check price_per_night > 0
);

create table booking_for_category
(
    booking_id      int     not null,
    category_id     varchar not null,

    number_of_rooms int     not null,

    primary key (booking_id, category_id),
    foreign key (booking_id) references booking (id) on delete cascade,
    foreign key (category_id) references category (id) on delete restrict,
    check number_of_rooms > 0
);