CREATE TABLE topitem(
	topitem_idx NUMBER PRIMARY KEY
	, topitem_name varchar2(30)
);

CREATE SEQUENCE seq_topitem
INCREMENT BY 1
START WITH 1;

CREATE TABLE subitem(
	subitem_idx NUMBER PRIMARY KEY
	, topitem_idx NUMBER
	, subitem_name varchar2(30)
	, CONSTRAINT fk_topitem_subitem FOREIGN KEY (topitem_idx) REFERENCES topitem(topitem_idx)
);

CREATE SEQUENCE seq_subitem
INCREMENT BY 1
START WITH 1;

CREATE TABLE goods(
	goods_idx NUMBER PRIMARY KEY
	, subitem_idx NUMBER
	, goods_name varchar2(30)
	, goods_brand varchar2(30)
	, goods_price NUMBER DEFAULT 0
	, goods_stock NUMBER DEFAULT 0
	, CONSTRAINT fk_subitem_goods FOREIGN KEY (subitem_idx) REFERENCES subitem(subitem_idx)
);

CREATE SEQUENCE seq_goods
INCREMENT BY 1
START WITH 1;

--DB 추가
INSERT INTO goods(goods_idx, subitem_idx, goods_name, goods_brand, goods_price, goods_stock)
values(seq_goods.nextval, 10, '사이드 핀키', 'FCS', 4000, 20);
--DB 수정
UPDATE subitem SET subitem_name='기타' WHERE subitem_name='기';

select t.topitem_idx as topitem_idx, topitem_name, s.subitem_idx as subitem_idx, subitem_name,
goods_idx, goods_name, goods_brand, goods_price, goods_stock
from topitem t, subitem s, goods g
where  t.topitem_idx = s.topitem_idx and s.subitem_idx = g.subitem_idx 
order by t.topitem_idx, s.subitem_idx, goods_idx asc;

SELECT * FROM topitem t, subitem s, goods g
WHERE t.topitem_idx = s.topitem_idx AND s.subitem_idx = g.SUBITEM_IDX 
AND goods_name LIKE '%9%';