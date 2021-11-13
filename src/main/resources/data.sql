-- noinspection SqlWithoutWhereForFile

delete from stay_in_room;
delete from stay;
delete from room;

delete from booking_for_category;
delete from booking;
delete from category;

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
       (222, 222, 1);

insert into room (id, room_id, category_id)
values (111, 'dom-id-room-111', 111),
       (112, 'dom-id-room-112', 111),
       (113, 'dom-id-room-113', 111),
       (114, 'dom-id-room-114', 111),
       (115, 'dom-id-room-115', 111),

       (221, 'dom-id-room-221', 222),
       (222, 'dom-id-room-222', 222),
       (223, 'dom-id-room-223', 222),
       (224, 'dom-id-room-224', 222),
       (225, 'dom-id-room-225', 222);

insert into stay (id, stay_id, check_in, check_out,
                  guest_first_name, guest_last_name, guest_street, guest_zip, guest_city, guest_country,
                  representative_first_name, representative_last_name,
                  representative_street, representative_zip, representative_city, representative_country,
                  representative_email, representative_phone)
values (111, 'dom-id-stay-111', '2021-11-13 10:00:00', '2021-11-20 10:00:00',
        'John', 'Thompson', 'Hans-Mauracher-Straße 162', '4117', 'Wien', 'Austria',
        'John', 'Thompson', 'Hans-Mauracher-Straße 162', '4117', 'Wien', 'Austria',
        'j.thompson@randatmail.com', '137-3936-04'),
       (222, 'dom-id-stay-222', '2021-11-10 10:00:00', '2021-11-15 10:00:00',
        'Emma', 'Ross', 'Gösting 13b', '9542', 'Linz', 'Austria',
        'Emma', 'Ross', 'Gösting 13b', '9542', 'Linz', 'Austria',
        'e.ross@randatmail.com', '559-1716-40');

insert into stay_in_room (stay_id, room_id)
values (111, 114),
       (111, 115),
       (222, 223);