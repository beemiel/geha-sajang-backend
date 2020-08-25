
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

INSERT INTO host
VALUES (1, 'main', 'maxmax@gmail.com', 'max', '123456', true, false, '', '', '', '', '', '', false, now(),now(), null),
       (2, 'main', 'joyjoy@gmail.com', 'joy', '123456', true, false,'', '', '', '', '', '', false, now(),now(),now()),
       (3, 'main', 'lean@gmail.com', 'lena', '123456', true, false,'', '', '', '', '', '', false, now(),now(),now()),
       (4, 'main', 'lynn@gmail.com', 'lynn', '123456', true, false, '', '', '', '', '', '', false , now(),now(), null),
       (5, 'main', '4incense@gmail.com', '4incense', '123456', true, false, '', '', '', '', '', '', false , now(),now(),null);


INSERT INTO host_auth_key
VALUES (1, 4, 'testtestkeykey', now()),
       (2, 5, 'testkey', '2020-08-26');
