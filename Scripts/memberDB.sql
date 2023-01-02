CREATE TABLE surfmember(
	surfmember_idx NUMBER PRIMARY KEY
	, id varchar2(30)
	, name varchar2(30)
	, gender varchar2(3) NOT NULL CHECK (gender IN ('남', '여'))
	, phoneno NUMBER
	, career NUMBER DEFAULT 0
	, regdate DATE DEFAULT sysdate
);

CREATE SEQUENCE seq_surfmember
INCREMENT BY 1
START WITH 1;

CREATE TABLE profile(
	profile_idx NUMBER PRIMARY KEY
	, surfmember_idx NUMBER
	, name varchar2(30)
	, snsid varchar2(30)
	, spot varchar2(30)
	, board_name varchar2(30)
	, board_price NUMBER DEFAULT 0
	, career NUMBER DEFAULT 0
	, CONSTRAINT fk_surfmember_profile FOREIGN KEY (surfmember_idx) REFERENCES surfmember(surfmember_idx)
);

CREATE SEQUENCE seq_profile
INCREMENT BY 1
START WITH 1;

CREATE TABLE ridingrecord(
	record_idx NUMBER PRIMARY KEY
	, surfmember_idx NUMBER
	, riding_spot varchar2(30)
	, board_spec varchar2(50)
	, pado varchar(10)
	, image_name varchar(30)
	, regdate DATE DEFAULT sysdate
	, CONSTRAINT fk_surfmember_ridingrecord FOREIGN KEY (surfmember_idx) REFERENCES surfmember(surfmember_idx)
);

CREATE SEQUENCE seq_ridingrecord
INCREMENT BY 1
START WITH 1;

--컬럼 순서 변경 시 사용
ALTER TABLE surfmember MODIFY regdate visible;
--컬럼 추가
ALTER TABLE profile ADD filename varchar2(30);
--컬럼 삭제
ALTER TABLE profile drop COLUMN career;

--회원 데이터 추가
INSERT INTO surfmember(surfmember_idx, id, name, gender, phoneno, snsid, career)
VALUES(seq_surfmember.nextval, 'test20', '테스트20', '남', '01007345678', 'sns_id20', 1);
--라이딩 기록 추가
INSERT INTO ridingrecord(record_idx, surfmember_idx, riding_spot, board_spec, pado, image_name)
values(seq_ridingrecord.nextval, 3, '물치해변', '실버피쉬/9.4ft', '1.7m', '37.jpg');


--데이터 수정
UPDATE profile SET filename='4.jpg' WHERE profile_idx=4;


SELECT * FROM surfmember WHERE SURFMEMBER_IDX  LIKE '%2%';

SELECT surfmember_idx FROM surfmember WHERE surfmember_idx=seq_surfmember.currval;
SELECT seq_ridingrecord.currval FROM dual;
