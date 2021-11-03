insert into booking (booking_number, check_in, check_out)
values (1, '2021-12-26 10:00:00', '2021-12-30 10:00:00' ),
       (2, '2022-01-05 15:00:00', '2022-01-15 10:00:00' );

insert into category (title, description, price_per_night)
values ('Single Bed', 'hier könnte ihre werbung stehen', 50),
       ('Double Bed', '........', 75);

insert into booking_for_category (booking_number, category_title, number_of_rooms)
values (1, 'Single Bed', 3),
       (2, 'Single Bed', 2),
       (2, 'Double Bed', 1)