
INSERT INTO house (house_id, uuid, name, city, street, postcode, detail, main_number)
VALUES (1, 'uuid1', '게스트 하우스1',  'city1', 'street1', 'postcode1', 'detail1', '010-1234-5678'),
       (2, 'uuid2', '하우스 게스트2',  'city2', 'street2', 'postcode2', 'detail2', '010-1111-1111'),
       (3, 'uuid3', '라면 게스트',  'city3', 'street3', 'postcode3', 'detail3', '010-1222-2222'),
       (4, 'uuid4', '조용님 의원',  'city4', 'street4', 'postcode4', 'detail4', '010-3333-3333');

INSERT INTO house_extra_info (house_extra_info_id, title, house_id)
VALUES (1, '추가 정보 1', 1),
       (2, '추가 정보 2', 1),
       (3, '추가 정보 3', 1),
       (4, '추가 정보 1', 2),
       (5, '추가 정보 2', 2),
       (6, '추가 정보 1', 3);

INSERT INTO terms (terms_id, type, contents)
VALUES (1, '이용약관', '이용약관 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.'),
       (2, '개인정보', '개인정보 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.'),
       (3, '마케팅', '마케팅 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.');

INSERT INTO room VALUES (1, 4, 6, 'room memo1', 'test_room_1', 15000, 20000, 'MULTIPLE', 1, now(), null, null);
INSERT INTO room VALUES (2, 4, 6, 'room memo2', 'test_room_2', 30000, 40000, 'DORMITORY', 2, now(), null, null);
INSERT INTO room VALUES (3, 4, 6, 'room memo3', 'test_room_3', 30000, 40000, 'MULTIPLE', 2, now(), null, null);
INSERT INTO room VALUES (4, 2, 3, 'room memo4', 'test_room_4', 40000, 50000, 'MULTIPLE', 1, now(), null, null);
INSERT INTO room VALUES (5, 2, 3, 'room memo5', 'test_room_5', 40000, 50000, 'MULTIPLE', 1, now(), null, now());

INSERT INTO unbooked_room (entry_date, today_amount, room_id)
VALUES ('2020-08-01', 15000, 1),
       ('2020-08-01', 30000, 2),
       ('2020-08-01', 30000, 2),
       ('2020-08-01', 30000, 2),
       ('2020-08-01', 30000, 2),
       ('2020-08-01', 30000, 2),
       ('2020-08-01', 30000, 2),
       ('2020-08-01', 30000, 3),
       ('2020-08-01', 40000, 4),
       ('2020-08-01', 40000, 5),

       ('2020-08-02', 15000, 1),
       ('2020-08-02', 30000, 2),
       ('2020-08-02', 30000, 2),
       ('2020-08-02', 30000, 2),
       ('2020-08-02', 30000, 2),
       ('2020-08-02', 30000, 2),
       ('2020-08-02', 30000, 2),
       ('2020-08-02', 30000, 3),
       ('2020-08-02', 40000, 4),
       ('2020-08-01', 40000, 5),


       ('2020-08-03', 15000, 1),
       ('2020-08-03', 30000, 2),
       ('2020-08-03', 30000, 2),
       ('2020-08-03', 30000, 2),
       ('2020-08-03', 30000, 2),
       ('2020-08-03', 30000, 2),
       ('2020-08-03', 30000, 2),
       ('2020-08-03', 30000, 3),
       ('2020-08-03', 40000, 4),
       ('2020-08-01', 40000, 5);


    INSERT INTO booked_room
VALUES (1,1),
       (2,2),
       (3,3);

INSERT INTO host
VALUES (1, 'main', 'maxmax@4incense.com', 'max', '123456', true, false, '', '', '', '', '', '', false, now(), now(), null),
       (2, 'main', 'joyjoy@4incense.com', 'joy', '123456', true, false,'', '', '', '', '', '', false, now(), now(), now()),
       (3, 'main', 'lena@4incense.com', 'lena', '123456', true, false,'', '', '', '', '', '', false, now(), now(), now()),
       (4, 'main', 'lynn@4incense.com', 'lynn', '123456', true, false, '', '', '', '', '', '', false, now(), now(), null),
       (5, 'main', 'official@4incense.com', '4incense', '$2a$10$U368wBp94Ag7n3BK5tTa/uUhHaiCTilnVPAI/zE2RkU7K208vsdVW', true, true, '', '', '', '', '', '', true, now(), now(), null),
       (6, 'main', 'bluuminn@gmail.com', 'maxious', '$2a$10$XnAZV.f/q0Sl4sc1wXCKEOa.j.mH9ZTnqkl.nReKAzuE/x4AeNBj.', false, true, '', '', '', '', '', '', true, now(), now(), null);


INSERT INTO host_auth_key
VALUES (1, 1, 'maxmax@4incense.com', '2020-09-10'),
       (2, 4, 'lynn@4incense.com', now()),
       (3, 5, 'testkey', '2020-08-26');

INSERT INTO host_house
VALUES (1, 6, 1),
       (2, 5, 2);

INSERT INTO guest
VALUES (1, 'test@naver.com', 'test memo', 'test', '01000000000'),
       (2, 'test2@naver.com', 'test2 memo', 'test', '01011111111'),
       (3, 'test3@naver.com', 'test3 memo', 'test2', '01022222222');


INSERT INTO booking
VALUES (1, '2020-07-05', '2020-07-08', 2, 0, '요구사항11', 1, 1, null, null, null),
       (2, '2020-08-09', '2020-08-13', 3, 0, '요구사항22', 1, 1, null, null, null),
       (3, '2020-08-09', '2020-08-13', 4, 0, '요구사항22', 2, 1, null, null, null),
       (4, '2020-08-09', '2020-08-13', 5, 0, '요구사항22', 2, 2, null, null, null),
       (5, '2020-08-09', '2020-08-13', 6, 0, '요구사항22', 3, 2, null, null, null),
       (6, '2020-08-15', '2020-08-17', 5, 0, '요구사항이다', 2, 2, null, null, null),
       (7, '2020-08-18', '2020-08-17', 1, 0, '요구사항..', 2, 2, null, null, null);
