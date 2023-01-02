CREATE TABLE step(
	step_idx NUMBER PRIMARY KEY
	, step_name varchar2(30)
);

CREATE SEQUENCE seq_step
INCREMENT BY 1
START WITH 1;

CREATE TABLE reservation(
	rsv_idx NUMBER PRIMARY KEY
	, step_idx number
	, rsv_name varchar2(30)
	, rsv_number NUMBER DEFAULT 0
	, rsv_time NUMBER CHECK (rsv_time IN (10, 12, 3))
	, CONSTRAINT fk_step_reservation FOREIGN KEY (step_idx) REFERENCES step(step_idx)
);


CREATE SEQUENCE seq_reservation
INCREMENT BY 1
START WITH 1;

--DB 추가
INSERT INTO reservation(rsv_idx, step_idx, rsv_name, rsv_number, rsv_time)
values(seq_reservation.nextval, 2, '예약자40', 2, 12);
--DB 수정
UPDATE reservation SET regdate='입문' WHERE step_name='입';
--컬럼 추가
ALTER TABLE reservation ADD regdate DATE DEFAULT sysdate;

select rsv_idx, step_idx, rsv_name, rsv_number, rsv_time, to_char(regdate, 'dd') AS day
from reservation WHERE to_char(regdate, 'yyyymm')='202212' order by regdate ASC;

select * from step s, reservation r where s.step_idx = r.step_idx
and to_char(regdate, 'yyyymmdd')='20221227' order by rsv_time desc;

select rsv_time, step_name, count(rsv_number) from step s, reservation r where s.step_idx = r.step_idx
and to_char(regdate, 'yyyymmdd')='20221227' group by rsv_time, step_name;