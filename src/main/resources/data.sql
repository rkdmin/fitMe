INSERT INTO member(id, email, email_key, email_status, password, roles, username, member_detail_id, member_size_id)
VALUES (999, '22rkdmin@naver.com', '20f0aacf-5b98-4cdd-a299-e78ecb041e93', 'F', '$2a$10$MeN68YSZKO110N0Ww0NwUO8CKg9QqaSsDRxqAc3PUpEYqHv30ynOO', 'ROLE_ADMIN', 'admin', null, null);

INSERT INTO brand(brand_name, url, phone)
VALUES ('몽블랑', 'https://www.montblanc.com/kr/ko/home.html', '010-1234-1234');
INSERT INTO brand(brand_name, url, phone)
VALUES ('라코스테', 'https://www.sadf.com/kr/ko/home.html', '010-2222-1234');

INSERT INTO seller(company_name, seller_name, address, phone, business_number, email)
VALUES ('어쩌구컴퍼티', '홍길동', '경기도 수원시 팔달구 2가', '031-123-1234', '123', '123@gmail.com');
INSERT INTO seller(company_name, seller_name, address, phone, business_number, email)
VALUES ('어쩌구컴퍼티2', '홍길동2', '경기도 수원시 팔달구 2가', '031-123-1234', '123', '123@gmail.com');

INSERT INTO model(height, top_size, bottom_size, shoes_size, model_name)
VALUES (173, 55, 30, 250, '김모델');

INSERT INTO category(category_name)
VALUES ('상의');
INSERT INTO category(category_name)
VALUES ('하의');
INSERT INTO category(category_name)
VALUES ('신발');
