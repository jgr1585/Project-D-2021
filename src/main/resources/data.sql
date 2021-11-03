insert into booking (id, booking_id, check_in, check_out, guest_name, guest_address,
                     representative_name, representative_email, representative_address, representative_phone)
values (1, 'dom-id-book-1', '2021-12-26 10:00:00', '2021-12-30 10:00:00', 'Max Mustermann', 'Musterstraße 5, Musterstadt 1234',
        'Max Mustermann', 'mustermann@mustermail.com', 'Musterstraße 5, Musterstadt 1234', '123456789'),
       (2, 'dom-id-book-2', '2022-01-05 15:00:00', '2022-01-15 10:00:00', 'Miriam Musterfrau', 'Mustergasse 16, Musterdorf 4321',
        'Miriam Musterfrau', 'musterfrau@mustermail.com', 'Mustergasse 16, Musterdorf 4321', '987654321');


insert into category (id, category_id, title, description, price_per_night)
values (1, 'dom-id-cat-1', 'Single Bed', 'hier könnte ihre werbung stehen', 50),
       (2, 'dom-id-cat-2', 'Double Bed', '........', 75);

insert into booking_for_category (booking_id, category_id, number_of_rooms)
values (1, 1, 3),
       (2, 1, 2),
       (2, 2, 1)