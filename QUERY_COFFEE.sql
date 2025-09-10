
USE my_coffeeshop;

INSERT INTO category (category_name, category_description, category_classify)
VALUES
('Bánh ngọt', 'Các loại bánh ngọt', 'Bánh'),
('Trà Sữa', 'Các loại trà sữa', 'Nước'),
('Trà', 'Các loại trà ô lông', 'Nước'),
('Cà phê', 'Các loại cà phê', 'Nước');


INSERT INTO product (product_name, product_description, product_img, product_inventory_quantity, product_price, category_id)
VALUES 
('Cà phê đen', 'Cà phê đen', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850276/capheden_rumclc.jpg', 100, 35000, 4),
('Cà phê nâu', 'Cà phê nâu', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850276/caphenau_e0eeiv.jpg', 100, 35000, 4),
('Cà phê sữa', 'Cà phê sữa', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850276/caphesua_svrvdm.jpg', 100, 35000, 4), 
('Capuchino', 'Capuchino', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850276/capuchino_ub4ehr.jpg', 100, 35000, 4), 
('Espresso', 'Espresso', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850277/espresso_kyisli.jpg', 100, 35000, 4), 
('Latte', 'Latte', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850278/latte_rr0xcw.jpg', 100, 35000, 4), 
('Trà ô lông bưởi', 'Trà ô lông bưởi', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850283/olong-buoi_lcjom7.jpg', 100, 50000, 3),
('Trà ô lông cam', 'Trà ô lông cam', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850283/olong-cam_ygr0xo.jpg', 100, 50000, 3),
('Trà ô lông đào', 'Trà ô lông đào', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850283/olong-thanh-dao_k6zl1n.jpg', 100, 50000, 3),
('Trà ô lông gạo sữa', 'Trà ô lông gạo sữa', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850285/olong-gao-suatuoi_wtqyvn.jpg', 100, 50000, 3),
('Trà ô lông mơ đào', 'Trà ô lông mơ đào', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850290/olong-m%C6%A1-%C4%91%C3%A0o_gpybsh.jpg', 100, 50000, 3),
('Trà ô lông nhiệt đới', 'Trà ô lông nhiệt đới', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850285/olong-nhietdoi_njzqiu.jpg', 100, 50000, 3),
('Trà ô lông vải', 'Trà ô lông vải', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850284/olong-vao_myjzva.jpg', 100, 50000, 3),
('Trà sữa đường nâu', 'Trà sữa đường nâu', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850291/trasua-%C4%91%C6%B0%E1%BB%9Dng-n%C3%A2u_sch51c.jpg', 100, 40000, 2),
('Trà sữa khoai môn', 'Trà sữa khoai môn', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850288/trasua-khoaimon_myuglj.jpg', 100, 40000, 2),
('Trà sữa matcha', 'Trà sữa matcha', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850285/trasua-matcha_hbstkb.jpg', 100, 40000, 2),
('Trà sữa ô lông nhài', 'Trà sữa ô lông nhài', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850289/trasua-olong-nh%C3%A0i_c6gyhw.jpg', 100, 40000, 2),
('Trà sữa truyền thống', 'Trà sữa truyền thống', 'https://res.cloudinary.com/dvpbtas1x/image/upload/v1747850291/trasua-truyenthong_znngkr.jpg', 100, 40000, 2)
;

INSERT INTO topping (topping_name, topping_price)
VALUES 
('Trân châu đen', 5000),
('Trân châu trắng', 5000),
('Đào(2 miếng)', 5000),
('Vải(2 miếng)', 5000),
('Kem phô mai', 5000)
;

INSERT INTO discount_code (discount_code_id, discount_code_name, percent_off, usage_limit, valid_from, valid_until)
VALUES (1, 'SUMMER20', 20.0, 100, '2025-05-01', '2025-06-30');

INSERT INTO bill (bill_creation_date, bill_total, payment_method, customer_id, employee_id)
VALUES 
('2025-05-22', 35000, 'Quét mã QR', 1, 1),
('2025-05-22', 40000, 'Quét mã QR', 1, 1),
('2025-05-22', 50000, 'Quét mã QR', 1, 1)
;

INSERT INTO bill_detail (product_quantity, sub_total, bill_id, product_id)
VALUES 
(2, 70000, 1, 1),
(1, 40000, 2, 18),
(1, 50000, 1, 7)
;

INSERT INTO order_online (delivery_address, delivery_time, note_of_cus, order_date, paid, payment_method, status_delivery,total_ord, transaction_code, customer_id)
VALUES ('456 Nguyễn Trãi, Q.5', '14:30:00', 'Không đá', '2025-05-22', 1, 'Quét mã QR',0, 90000, 'TXN123456', 1);

INSERT INTO order_online_detail (order_detail_id, ice, quantity, size, sweet, unit_price, ord_onl_id, product_id)
VALUES (1, 'Bình thường', 1, 'L', 'Ít', 45000, 1, 18);


INSERT INTO order_online_detail_topping (order_detail_id, toppingid, quantity)
VALUES 
(1, 1, 1);  -- 1 phần trân châu đen cho đơn online

INSERT INTO cart(discount_code, payment_method, quantity, ship_cost, total, customer_id)
VALUES ('SUMMER20', 'Quét mã QR', 2, 10000, 80000, 1);

INSERT INTO cartitem (cart_item_id, ice, price, quantity, size, sweet, cart_id, product_id)
VALUES 
(1, 'Ít đá', 35000, 1, 'L', 'Vừa', 1, 1),
(2, 'Nhiều đá', 30000, 1, 'M', 'Ít đường', 1, 1);

INSERT INTO cartitem_topping (cartitem_id, topping_id, quantity)
VALUES 
(1, 1, 1),  -- Trân châu đen
(2, 1, 2);  -- Trân châu đen x2


INSERT INTO transaction_history (id, date, plus_point, time, customer_id, order_id)
VALUES 
(1, '2025-05-22', 10, '14:30:00', 1, 1);

