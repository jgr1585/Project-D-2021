-- noinspection SqlWithoutWhereForFile

delete from stay_in_room;
delete from stay;
delete from room;

delete from booking_for_category;
delete from booking;
delete from category;

delete from season;
delete from bill_entry;
delete from bill;
delete from organization;

insert into booking (id, booking_id, check_in, check_out,booking_State, guest_first_name, guest_last_name, guest_street, guest_zip,
                     guest_city, guest_country, representative_first_name, representative_last_name,
                     representative_street, representative_zip, representative_city, representative_country,
                     representative_email, representative_phone, representative_payment_method, representative_credit_card_number, organization_id)
values (111, 'dom-id-book-111', '2021-12-26 10:00:00', '2021-12-30 10:00:00','booked', 'Max', 'Mustermann', 'Musterstraße 5',
        '1234', 'Dornbirn', 'Austria', 'Max', 'Mustermann', 'Musterstraße 5',
        '1234', 'Dornbirn', 'Austria', 'mustermann@mustermail.com', '123456789', 'CreditCard', '4111 1111 1111 1111', ''),
       (222, 'dom-id-book-222', '2022-12-26 10:00:00', '2022-12-30 10:00:00','booked', 'Julia', 'Musterfrau', 'Mustergasse 5',
        '1234', 'Feldkirch', 'Austria', 'Max', 'Musterfrau', 'Musterstraße 5',
        '1234', 'Dornbirn', 'Austria', 'mustermann@mustermail.com', '123456789', 'Cash', '5555 5555 5555 4444', 'dom-id-org-111');


insert into category (id, category_id, title, description)
values (111, 'dom-id-cat-111', 'Single Room', 'A room assigned to one person with one bed.'),
       (222, 'dom-id-cat-222', 'Double Room', 'A room assigned to two persons with a double bed.');
#        (333, 'dom-id-cat-333', 'Family Room', 'A room assigned to a maximum of five persons with a mixture of single and double beds.'),
#        (444, 'dom-id-cat-444', 'Suite', 'A luxurious apartment assigned to a maximum of six persons with several bedrooms.');

insert into booking_for_category (booking_id, category_id, number_of_rooms)
values (111, 111, 3),
       (222, 111, 2),
       (222, 222, 1);

insert into room (id, room_id, category_id)
values (111, 'R111', 111),
       (112, 'R112', 111),
       (113, 'R113', 111),
       (114, 'R114', 111),
       (115, 'R115', 111),

       (221, 'R221', 222),
       (222, 'R222', 222),
       (223, 'R223', 222),
       (224, 'R224', 222),
       (225, 'R225', 222);

#        (331, 'R331', 333),
#        (332, 'R332', 333),
#        (333, 'R333', 333),
#
#        (441, 'R441', 444),
#        (442, 'R442', 444);

insert into stay (id, stay_id, check_in, check_out, staying_state,
                  guest_first_name, guest_last_name, guest_street, guest_zip, guest_city, guest_country,
                  representative_first_name, representative_last_name,
                  representative_street, representative_zip, representative_city, representative_country,
                  representative_email, representative_phone, representative_payment_method,
                  representative_credit_card_number, organization_id)
values (111, 'dom-id-stay-111', '2021-11-13 10:00:00', '2021-11-20 10:00:00', 'CheckedIn',
        'John', 'Thompson', 'Hans-Mauracher-Straße 162', '4117', 'Wien', 'Austria',
        'John', 'Thompson', 'Hans-Mauracher-Straße 162', '4117', 'Wien', 'Austria',
        'j.thompson@randatmail.com', '137-3936-04', 'CreditCard', '1111 1111 1111 1111', ''),
       (222, 'dom-id-stay-222', '2021-11-10 10:00:00', '2021-11-15 10:00:00', 'CheckedIn',
        'Emma', 'Ross', 'Gösting 13b', '9542', 'Linz', 'Austria',
        'Emma', 'Ross', 'Gösting 13b', '9542', 'Linz', 'Austria',
        'e.ross@randatmail.com', '559-1716-40', 'Cash', '2222 2222 2222 2222', 'dom-id-org-111');

insert into season(id, season_id, name, from_Month, to_Month)
values (1, 'Season1', 'Summer', 04, 09),
       (2, 'Season2', 'Winter', 10, 03);

insert into price_per_season(category_id, season_id, price)
values (111, 1, 75),
       (111, 2, 70),
       (222, 1, 150),
       (222, 2, 140);

insert into stay_in_room (stay_id, room_id)
values (111, 114),
       (111, 115),
       (222, 223);

insert into bill (id, bill_id)
values (111, 'dom-id-bill-111'),
       (222, 'dom-id-bill-222');


insert into bill_entry (bill_id, entry_index, description, timestamp, amount, unit_price)
values (111, 0, '2x Extra Pillows', '2021-11-14 09:00:00', 2, 5),
       (111, 1, 'Room Service', '2021-11-17 07:00:00', 1, 150),
       (111, 2, '12x Overnight Stay Single Bed', '2021-11-20 09:45:00', 12, 50),
       (222, 0, 'Party Room', '2021-11-14 09:00:00', 1, 500),
       (222, 1, '10x Beer', '2021-11-14 09:00:00', 10, 5),
       (222, 2, '3x Bottles of Wine', '2021-11-14 09:00:00', 3, 12);

insert into organization (id, organization_id, organization_name, organization_street, organization_zip, organization_city, organization_country, discount)
values (111, 'dom-id-org-111', 'Blum GmbH', 'Im Städtle 40', '6973', 'Höchst', 'Austria', 15),
       (222, 'dom-id-org-222', 'Doppelmayr/Garaventa Group', 'Konrad-Doppelmayr-Straße 1', '6922', 'Wolfurt', 'Austria', 10),
       (333, 'dom-id-org-333', 'Bachmann electronic GmbH', 'Kreuzäckerweg 33', '6800', 'Feldkirch', 'Austria', 5);

COMMIT;