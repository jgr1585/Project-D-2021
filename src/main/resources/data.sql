insert into booking (id, booking_id, check_in, check_out, guest_first_name, guest_last_name, guest_street, guest_zip,
                     guest_city, guest_country, representative_first_name, representative_last_name,
                     representative_street, representative_zip, representative_city, representative_country,
                     representative_email, representative_phone)
values (111, 'dom-id-book-111', '2021-12-26 10:00:00', '2021-12-30 10:00:00', 'Max', 'Mustermann', 'Musterstraße 5',
        '1234', 'Dornbirn', 'Austria', 'Max', 'Mustermann', 'Musterstraße 5',
        '1234', 'Dornbirn', 'Austria', 'mustermann@mustermail.com', '123456789'),
       (222, 'dom-id-book-222', '2022-12-26 10:00:00', '2022-12-30 10:00:00', 'Julia', 'Musterfrau', 'Mustergasse 5',
        '1234', 'Feldkirch', 'Austria', 'Max', 'Musterfrau', 'Musterstraße 5',
        '1234', 'Dornbirn', 'Austria', 'mustermann@mustermail.com', '123456789');


insert into category (id, category_id, title, description, price_per_night)
values (111, 'dom-id-cat-111', 'Single Bed', 'hier könnte ihre werbung stehen', 50),
       (222, 'dom-id-cat-222', 'Double Bed', '........', 75);

insert into booking_for_category (booking_id, category_id, number_of_rooms)
values (111, 111, 3),
       (222, 111, 2),
       (222, 222, 1)