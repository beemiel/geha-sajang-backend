
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
