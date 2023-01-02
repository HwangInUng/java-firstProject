CREATE TABLE shopadmin(
	admin_idx NUMBER PRIMARY KEY
	, id varchar(20) unique
	, pass varchar(64)
);

CREATE SEQUENCE seq_shopadmin
INCREMENT BY 1
START WITH 1;

DROP TABLE shopadmin;

DELETE FROM shopadmin WHERE id='admin3';

--가상의 연간현황 테이블 생성
CREATE TABLE total(
	total_idx NUMBER PRIMARY KEY
	, name varchar2(20)
	, nomal NUMBER DEFAULT 0
	, couple NUMBER DEFAULT 0
	, single NUMBER DEFAULT 0
	, board NUMBER DEFAULT 0
	, food NUMBER DEFAULT 0
	, etc NUMBER DEFAULT 0
);

CREATE SEQUENCE seq_total
INCREMENT BY 1
START WITH 1;

--현황등록
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '1월', 80, 20, 15, 7500000, 1500000, 1000000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '2월', 60, 10, 5, 4000000, 700000, 500000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '3월', 55, 8, 7, 3000000, 550000, 300000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '4월', 73, 18, 23, 7000000, 1200000, 1130000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '5월', 90, 34, 17, 6000000, 1540000, 890000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '6월', 99, 26, 32, 8300000, 1760000, 800000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '7월', 112, 32, 23, 8420000, 1983000, 975000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '8월', 150, 54, 45, 11900000, 2430000, 1320000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '9월', 101, 22, 10, 4500000, 1130000, 845000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '10월', 76, 14, 8, 2500000, 760000, 540000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '11월', 40, 10, 4, 1800000, 670000, 450000);
INSERT INTO total(total_idx, name, nomal, couple, single, board, food, etc)
values(seq_total.nextval, '12월', 15, 2, 1, 0, 430000, 300000);

select * from total order by total_idx ASC;

SELECT sum(nomal) AS nomal, sum(couple) AS couple, sum(single) AS single
FROM total;