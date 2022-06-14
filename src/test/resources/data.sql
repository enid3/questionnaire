-- users
INSERT INTO users (id, email, first_name, last_name, password, phone_number) VALUES (8, 'todd@page.com', 'Todd', 'Page', '$2a$10$OZ1IjzCVj9J8hNNSAZSire3riukhKnIKCiFNtT61opltNuHCSOXKK', '8222689288');
INSERT INTO users (id, email, first_name, last_name, password, phone_number) VALUES (9, 'eleanor@mays.com', 'Eleanor', 'Mays', '$2a$10$QTrqlGK0JcWkKp4q81bTZOscltSgf3mBPoqPPOoox0MXC0oLcekKG', '5347764548');
INSERT INTO users (id, email, first_name, last_name, password, phone_number) VALUES (11, 'john@doe.com', 'John', 'Doe', '$2a$10$FhRtbOkfJKfoEpJSJ1dHUu8ac7r0BsksplegDpiL6tfU1/8OwNHaS', '1234567789');

-- fields
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (12, true, true, 'sinlilinetext r a', 0, 11);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (13, false, true, 'multiline r', 1, 11);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (14, true, false, 'radio a', 2, 11);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (15, false, false, 'checkbox ', 3, 11);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (16, true, true, 'combobox r a', 4, 8);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (17, false, true, 'date r', 5, 8);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (18, true, false, 'combobox a', 4, 8);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (19, true, false, 'radiobutton a', 2, 9);
INSERT INTO field (id, is_active, is_required, label, type, owner_id) VALUES (20, false, false, 'checkbox', 3, 9);

-- fields options
INSERT INTO field_options (field_id, options) VALUES (14, 'opt1');
INSERT INTO field_options (field_id, options) VALUES (14, 'opt2');
INSERT INTO field_options (field_id, options) VALUES (15, 'a');
INSERT INTO field_options (field_id, options) VALUES (15, 'b');
INSERT INTO field_options (field_id, options) VALUES (15, 'c');
INSERT INTO field_options (field_id, options) VALUES (16, 'a');
INSERT INTO field_options (field_id, options) VALUES (16, 'b');
INSERT INTO field_options (field_id, options) VALUES (16, 'c');
INSERT INTO field_options (field_id, options) VALUES (16, 'd');
INSERT INTO field_options (field_id, options) VALUES (18, 'optioin 1');
INSERT INTO field_options (field_id, options) VALUES (18, 'option 3');
INSERT INTO field_options (field_id, options) VALUES (18, 'option 2');
INSERT INTO field_options (field_id, options) VALUES (19, 'radio 2');
INSERT INTO field_options (field_id, options) VALUES (19, 'rafio 1');
INSERT INTO field_options (field_id, options) VALUES (20, 'done');

-- responses
INSERT INTO response (id) VALUES (21);
INSERT INTO response (id) VALUES (22);
INSERT INTO response (id) VALUES (23);
INSERT INTO response (id) VALUES (24);
INSERT INTO response (id) VALUES (25);
INSERT INTO response (id) VALUES (26);
INSERT INTO response (id) VALUES (27);
INSERT INTO response (id) VALUES (28);



-- response values
INSERT INTO response_values (response_id, value, field_id) VALUES (21, 'a', 16);
INSERT INTO response_values (response_id, value, field_id) VALUES (21, 'option 3', 18);
INSERT INTO response_values (response_id, value, field_id) VALUES (22, 'a', 16);
INSERT INTO response_values (response_id, value, field_id) VALUES (22, 'option 3', 18);
INSERT INTO response_values (response_id, value, field_id) VALUES (23, 'd', 16);
INSERT INTO response_values (response_id, value, field_id) VALUES (23, 'option 3', 18);
INSERT INTO response_values (response_id, value, field_id) VALUES (24, 'radio 2', 19);
INSERT INTO response_values (response_id, value, field_id) VALUES (25, 'rafio 1', 19);
INSERT INTO response_values (response_id, value, field_id) VALUES (26, 'radio 2', 19);
INSERT INTO response_values (response_id, value, field_id) VALUES (27, 'opt1', 14);
INSERT INTO response_values (response_id, value, field_id) VALUES (27, 'singleline text sample', 12);
INSERT INTO response_values (response_id, value, field_id) VALUES (28, 'opt1', 14);
INSERT INTO response_values (response_id, value, field_id) VALUES (28, 'another text', 12);