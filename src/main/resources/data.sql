-- noinspection SqlWithoutWhereForFile

delete from stay_in_room;
delete from stay;
delete from room;

delete from booking_for_category;
delete from booking;
delete from category;

delete from season;
delete from bill_entry;
delete from organization;
delete from final_bill_entry;
delete from final_bill;
delete from bill;


insert into booking (id, booking_id, check_in, check_out, booking_State, guest_first_name, guest_last_name, guest_street, guest_zip,
                     guest_city, guest_country, representative_first_name, representative_last_name,
                     representative_street, representative_zip, representative_city, representative_country,
                     representative_email, representative_phone, representative_payment_method, representative_credit_card_number, organization_id)
values (111, 'dom-id-book-111', '2022-02-01 10:00:00', '2021-03-01 10:00:00','booked', 'Peter', 'Mayer',
        'Bachgasse 5', '6850', 'Dornbirn', 'Austria', 'Peter', 'Mayer',
        'Bachgasse 5', '6850', 'Dornbirn', 'Austria', 'p.mayer@vol.at', '0699987654', 'CreditCard', '4111 1111 1111 1111', ''),
       (222, 'dom-id-book-222', '2022-12-26 10:00:00', '2022-12-30 10:00:00','booked', 'Eva', 'Müller',
        'Naflastraße 1', '6800', 'Feldkirch', 'Austria', 'Eva', 'Müller',
        'Naflastraße 1', '6800', 'Feldkirch', 'Austria', 'eva.m@hotmail.com', '0664123456', 'Cash', '4111 1111 1111 1111', 'dom-id-org-111'),
       (333, 'dom-id-book-333', '2022-03-01 10:00:00', '2022-03-10 10:00:00','booked', 'John', 'Smith',
        '3278 Alfred Drive', '11201', 'New York', 'United States', 'John', 'Smith',
        '3278 Alfred Drive', '11201', 'New York', 'United States', 'smith@gmail.com', '718-254-5328', 'Cash', '5555 5555 5555 4444', '');

insert into category (id, category_id, title, description)
values (111, 'dom-id-cat-111', 'Single Room', 'A room assigned to one person with one bed.'),
       (222, 'dom-id-cat-222', 'Double Room', 'A room assigned to two persons with a double bed.'),
       (333, 'dom-id-cat-333', 'Family Room', 'A room assigned to a maximum of five persons with a mixture of single and double beds.'),
       (444, 'dom-id-cat-444', 'Suite', 'A luxurious apartment assigned to a maximum of six persons with several bedrooms.');

insert into booking_for_category (booking_id, category_id, number_of_rooms)
values (111, 111, 3),
       (222, 111, 2),
       (222, 222, 1),
       (333, 333, 1);

insert into room (id, room_id, category_id)
values (111, 'R111', 111),
       (112, 'R112', 111),
       (113, 'R113', 111),
       (114, 'R114', 111),
       (115, 'R115', 111),
       (116, 'R116', 111),
       (117, 'R117', 111),
       (118, 'R118', 111),
       (119, 'R119', 111),
       (120, 'R120', 111),

       (221, 'R221', 222),
       (222, 'R222', 222),
       (223, 'R223', 222),
       (224, 'R224', 222),
       (225, 'R225', 222),
       (226, 'R226', 222),
       (227, 'R227', 222),
       (228, 'R228', 222),
       (229, 'R229', 222),
       (230, 'R230', 222),
       (231, 'R231', 222),
       (232, 'R232', 222),
       (233, 'R233', 222),
       (234, 'R234', 222),
       (235, 'R235', 222),

       (331, 'R331', 333),
       (332, 'R332', 333),
       (333, 'R333', 333),
       (334, 'R334', 333),
       (335, 'R335', 333),

       (441, 'R441', 444),
       (442, 'R442', 444),
       (443, 'R443', 444);

insert into stay (id, stay_id, check_in, check_out, staying_state,
                  guest_first_name, guest_last_name, guest_street, guest_zip, guest_city, guest_country,
                  representative_first_name, representative_last_name,
                  representative_street, representative_zip, representative_city, representative_country,
                  representative_email, representative_phone, representative_payment_method,
                  representative_credit_card_number, organization_id)
values (111, 'dom-id-stay-111', '2022-01-20 10:00:00', '2022-02-01 10:00:00', 'CheckedIn',
        'Jonas', 'Tiefenthaler', 'Hans-Mauracher-Straße 162', '4117', 'Wien', 'Austria',
        'Jonas', 'Tiefenthaler', 'Hans-Mauracher-Straße 162', '4117', 'Wien', 'Austria',
        'j.thiefenthaler@outlook.com', '069954321', 'CreditCard', '1111 1111 1111 1111', null),
       (222, 'dom-id-stay-222', '2022-01-20 10:00:00', '2022-02-10 10:00:00', 'CheckedIn',
        'Emma', 'Bauer', 'Gösting 13b', '9542', 'Linz', 'Austria',
        'Emma', 'Bauer', 'Gösting 13b', '9542', 'Linz', 'Austria',
        'emma.b@outlook.com', '0664123987', 'Cash', '2222 2222 2222 2222', 'dom-id-org-222'),
       (333,'dom-id-stay-333', '2022-01-28 10:00:00', '2022-02-04 10:00:00', 'CheckedIn',
        'Jonas', 'Keckeis', 'Keckweg 15', '6900', 'Bregenz', 'Austria',
        'Jonas', 'Keckeis', 'Keckweg 15', '6900', 'Bregenz', 'Austria',
        'jonas.k@hotmail.com', '06991906931', 'Cash', '3333 3333 3333 3333', null);

insert into season(id, season_id, name, from_Month, to_Month)
values (1, 'Season1', 'Summer', 04, 09),
       (2, 'Season2', 'Winter', 10, 03);

insert into price_per_season(category_id, season_id, price)
values (111, 1, 60),
       (111, 2, 75),
       (222, 1, 120),
       (222, 2, 150),
       (333, 1, 200),
       (333, 2, 250),
       (444, 1, 400),
       (444, 2, 500);

insert into stay_in_room (stay_id, room_id)
values (111, 114),
       (111, 115),
       (222, 223),
       (333, 441);

insert into bill (id, bill_id)
values (111, 'dom-id-bill-111'),
       (222, 'dom-id-bill-222'),
       (333, 'dom-id-bill-333');

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

insert into final_bill(id, representative_first_name, representative_last_name,
                       representative_street, representative_zip, representative_city, representative_country,
                       representative_email, representative_phone, representative_payment_method,
                       representative_credit_card_number, bill_id, final_index)
values (111, 'Jonas', 'Keckeis', 'Keckweg 15', '6900', 'Bregenz', 'Austria',
        'jonas.k@hotmail.com', '06991906931', 'Cash', '3333 3333 3333 3333', 333, 0);

insert into final_bill_entry(final_bill_id, final_index, description, timestamp, amount, unit_price)
values (111, 0, 'Mineral Water', '2022-01-23 09:00:00', 3, 31);


COMMIT;