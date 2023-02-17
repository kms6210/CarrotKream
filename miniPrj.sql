DROP SEQUENCE SEQ_TYPE_NO;
DROP SEQUENCE SEQ_ITEM_NO;
DROP SEQUENCE SEQ_REVIEW_NO;
DROP SEQUENCE SEQ_BID_NO;
DROP SEQUENCE SEQ_ROOM_NO;
DROP SEQUENCE SEQ_USER_USE_NO;
DROP SEQUENCE SEQ_ADMIN_USE_NO;
DROP SEQUENCE SEQ_USER_NO;
DROP SEQUENCE SEQ_CHAT_NO;
DROP SEQUENCE SEQ_PUBLIC_NO;
DROP SEQUENCE SEQ_FAQ_QUESTION_NO;
DROP SEQUENCE SEQ_QNA_QUESTION_NO;
DROP SEQUENCE SEQ_HINT_QUESTION_NO;
DROP SEQUENCE SEQ_STOP_NO;
DROP SEQUENCE SEQ_ADMIN_NO;

DROP TABLE "QNA";
DROP TABLE "LIKELIST";
DROP TABLE "REVIEW";
DROP TABLE "ITEM_TYPE";
DROP TABLE "HINT_TYPE";
DROP TABLE "NOTICE";
DROP TABLE "AUCTION";
DROP TABLE "FAQ";
DROP TABLE "BANNED";
DROP TABLE "USER_ACCOUNT";
DROP TABLE "CHAT_CONTENT";
DROP TABLE "QUALITY";
DROP TABLE "BID";
DROP TABLE "K_ADMIN";
DROP TABLE "ADMIN_ACCOUNT";
DROP TABLE "CHAT_ROOM";
DROP TABLE "ITEM";
DROP TABLE "K_USER";

CREATE SEQUENCE SEQ_TYPE_NO;
CREATE SEQUENCE SEQ_ITEM_NO;
CREATE SEQUENCE SEQ_REVIEW_NO;
CREATE SEQUENCE SEQ_BID_NO;
CREATE SEQUENCE SEQ_ROOM_NO;
CREATE SEQUENCE SEQ_USER_USE_NO;
CREATE SEQUENCE SEQ_ADMIN_USE_NO;
CREATE SEQUENCE SEQ_USER_NO;
CREATE SEQUENCE SEQ_CHAT_NO;
CREATE SEQUENCE SEQ_PUBLIC_NO;
CREATE SEQUENCE SEQ_FAQ_QUESTION_NO;
CREATE SEQUENCE SEQ_QNA_QUESTION_NO;
CREATE SEQUENCE SEQ_HINT_QUESTION_NO;
CREATE SEQUENCE SEQ_STOP_NO;
CREATE SEQUENCE SEQ_ADMIN_NO;

CREATE TABLE "K_USER" (
	"USER_NO"	NUMBER		NOT NULL,
	"ID"	VARCHAR2(20)		NOT NULL,
	"PWD"	VARCHAR2(20)		NOT NULL,
	"NICK"	VARCHAR2(20)		NOT NULL,
	"PHONE_NO"	CHAR(20)		NOT NULL,
	"TRUST_LEVEL"	NUMBER	DEFAULT 36.5	NOT NULL,
	"ADDRESS"	VARCHAR2(4000)		NOT NULL,
	"BALANCE"	NUMBER		NULL,
	"QUESTION_NO"	NUMBER		NOT NULL,
	"ANSWER"	VARCHAR2(4000)		NOT NULL,
	"USER_STATUS"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"SIGN_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "K_USER"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "K_USER"."ID" IS 'ID';

COMMENT ON COLUMN "K_USER"."PWD" IS 'PWD';

COMMENT ON COLUMN "K_USER"."NICK" IS 'NICK';

COMMENT ON COLUMN "K_USER"."PHONE_NO" IS '휴대폰 번호';

COMMENT ON COLUMN "K_USER"."TRUST_LEVEL" IS '신뢰도';

COMMENT ON COLUMN "K_USER"."ADDRESS" IS '주소';

COMMENT ON COLUMN "K_USER"."BALANCE" IS '잔액';

COMMENT ON COLUMN "K_USER"."QUESTION_NO" IS '힌트 질문 번호';

COMMENT ON COLUMN "K_USER"."ANSWER" IS '힌트 답변';

COMMENT ON COLUMN "K_USER"."USER_STATUS" IS '계정 상태';

COMMENT ON COLUMN "K_USER"."SIGN_DATE" IS '가입일';

CREATE TABLE "QNA" (
	"QUESTION_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"QUESTION"	VARCHAR2(4000)		NOT NULL,
	"ANSWER"	VARCHAR2(4000)		NOT NULL,
	"QUIT_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"WRITE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL,
	"ANSWER_DATE"	TIMESTAMP		NULL
);

COMMENT ON COLUMN "QNA"."QUESTION_NO" IS '질문 번호';

COMMENT ON COLUMN "QNA"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "QNA"."QUESTION" IS '질문';

COMMENT ON COLUMN "QNA"."ANSWER" IS '답변';

COMMENT ON COLUMN "QNA"."QUIT_YN" IS '삭제 여부';

COMMENT ON COLUMN "QNA"."WRITE_DATE" IS '작성일';

COMMENT ON COLUMN "QNA"."ANSWER_DATE" IS '답변일';

CREATE TABLE "LIKELIST" (
	"ITEM_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "LIKELIST"."ITEM_NO" IS '상품 번호';

COMMENT ON COLUMN "LIKELIST"."USER_NO" IS '유저 번호';

CREATE TABLE "REVIEW" (
	"REVIEW_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"ITEM_NO"	NUMBER		NOT NULL,
	"BUY_REVIEW"	VARCHAR2(4000)		NULL,
	"SELL_REVIEW"	VARCHAR2(4000)		NULL,
	"TRADE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL,
	"TRADE_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL
);

COMMENT ON COLUMN "REVIEW"."REVIEW_NO" IS '후기 번호';

COMMENT ON COLUMN "REVIEW"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "REVIEW"."ITEM_NO" IS '상품 번호';

COMMENT ON COLUMN "REVIEW"."BUY_REVIEW" IS '구매 후기';

COMMENT ON COLUMN "REVIEW"."SELL_REVIEW" IS '판매 후기';

COMMENT ON COLUMN "REVIEW"."TRADE_DATE" IS '거래일';

COMMENT ON COLUMN "REVIEW"."TRADE_YN" IS '거래 방식';

CREATE TABLE "ITEM" (
	"ITEM_NO"	NUMBER		NOT NULL,
	"TYPE_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"TITLE"	VARCHAR2(50)		NOT NULL,
	"CONTENT"	VARCHAR2(4000)		NOT NULL,
	"PRICE"	NUMBER		NOT NULL,
	"VIEW"	NUMBER	DEFAULT 0	NOT NULL,
	"TRADE_STATUS"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"WRITE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "ITEM"."ITEM_NO" IS '상품 번호';

COMMENT ON COLUMN "ITEM"."TYPE_NO" IS '분류 번호';

COMMENT ON COLUMN "ITEM"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "ITEM"."TITLE" IS '제목';

COMMENT ON COLUMN "ITEM"."CONTENT" IS '내용';

COMMENT ON COLUMN "ITEM"."PRICE" IS '가격';

COMMENT ON COLUMN "ITEM"."VIEW" IS '조회수';

COMMENT ON COLUMN "ITEM"."TRADE_STATUS" IS '거래 상태';

COMMENT ON COLUMN "ITEM"."WRITE_DATE" IS '작성 시간';

CREATE TABLE "ITEM_TYPE" (
	"TYPE_NO"	NUMBER		NOT NULL,
	"TYPE_NAME"	VARCHAR(20)		NULL
);

COMMENT ON COLUMN "ITEM_TYPE"."TYPE_NO" IS '분류 번호';

COMMENT ON COLUMN "ITEM_TYPE"."TYPE_NAME" IS '분류 이름';

CREATE TABLE "HINT_TYPE" (
	"QUESTION_NO"	NUMBER		NOT NULL,
	"QUESTION"	VARCHAR2(4000)		NOT NULL
);

COMMENT ON COLUMN "HINT_TYPE"."QUESTION_NO" IS '힌트 질문 번호';

COMMENT ON COLUMN "HINT_TYPE"."QUESTION" IS '질문';

CREATE TABLE "NOTICE" (
	"PUBLIC_NO"	NUMBER		NOT NULL,
	"TITLE"	VARCHAR2(50)		NOT NULL,
	"CONTENT"	VARCHAR2(4000)		NOT NULL,
	"QUIT_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"WRITE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "NOTICE"."PUBLIC_NO" IS '공지 번호';

COMMENT ON COLUMN "NOTICE"."TITLE" IS '제목';

COMMENT ON COLUMN "NOTICE"."CONTENT" IS '내용';

COMMENT ON COLUMN "NOTICE"."QUIT_YN" IS '삭제 여부';

COMMENT ON COLUMN "NOTICE"."WRITE_DATE" IS '작성일';

CREATE TABLE "AUCTION" (
	"ITEM_NO"	NUMBER		NOT NULL,
	"AUCTION_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"END_TIME"	TIMESTAMP		NOT NULL
);

COMMENT ON COLUMN "AUCTION"."ITEM_NO" IS '상품 번호';

COMMENT ON COLUMN "AUCTION"."AUCTION_YN" IS '경매 상태';

COMMENT ON COLUMN "AUCTION"."END_TIME" IS '경매 종료일';

CREATE TABLE "FAQ" (
	"QUESTION_NO"	NUMBER		NOT NULL,
	"QUESTION"	VARCHAR2(4000)		NOT NULL,
	"ANSWER"	VARCHAR2(4000)		NOT NULL
);

COMMENT ON COLUMN "FAQ"."QUESTION_NO" IS '질문 번호';

COMMENT ON COLUMN "FAQ"."QUESTION" IS '질문';

COMMENT ON COLUMN "FAQ"."ANSWER" IS '답변';

CREATE TABLE "BANNED" (
	"STOP_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"STOP_REASON"	VARCHAR2(4000)	DEFAULT '이용 약관을 위반하셨습니다.'	NOT NULL,
	"STOP_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL,
	"RELEASE_DATE"	TIMESTAMP		NOT NULL
);

COMMENT ON COLUMN "BANNED"."STOP_NO" IS '제재 번호';

COMMENT ON COLUMN "BANNED"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "BANNED"."STOP_REASON" IS '정지 사유';

COMMENT ON COLUMN "BANNED"."STOP_DATE" IS '정지일';

COMMENT ON COLUMN "BANNED"."RELEASE_DATE" IS '정지 해제일';

CREATE TABLE "USER_ACCOUNT" (
	"USE_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"TARGET_NO"	NUMBER		NOT NULL,
	"PRICE"	NUMBER	DEFAULT 0	NOT NULL,
	"USE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "USER_ACCOUNT"."USE_NO" IS '내역 번호';

COMMENT ON COLUMN "USER_ACCOUNT"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "USER_ACCOUNT"."TARGET_NO" IS '대상 번호';

COMMENT ON COLUMN "USER_ACCOUNT"."PRICE" IS '변동 금액';

COMMENT ON COLUMN "USER_ACCOUNT"."USE_DATE" IS '이용일';

CREATE TABLE "CHAT_CONTENT" (
	"CHAT_NO"	NUMBER		NOT NULL,
	"ROOM_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"CONTENT"	VARCHAR2(4000)		NOT NULL,
	"READ_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"WRITE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "CHAT_CONTENT"."CHAT_NO" IS '채팅 내역 번호';

COMMENT ON COLUMN "CHAT_CONTENT"."ROOM_NO" IS '채팅방 번호';

COMMENT ON COLUMN "CHAT_CONTENT"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "CHAT_CONTENT"."CONTENT" IS '내용';

COMMENT ON COLUMN "CHAT_CONTENT"."READ_YN" IS '읽기 여부';

COMMENT ON COLUMN "CHAT_CONTENT"."WRITE_DATE" IS '작성시간';

CREATE TABLE "QUALITY" (
	"ITEM_NO"	NUMBER		NOT NULL,
	"GRADE"	NUMBER	DEFAULT 0	NOT NULL,
	"QUIT_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"CHECK_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "QUALITY"."ITEM_NO" IS '상품 번호';

COMMENT ON COLUMN "QUALITY"."GRADE" IS '상품 등급';

COMMENT ON COLUMN "QUALITY"."QUIT_YN" IS '삭제 여부';

COMMENT ON COLUMN "QUALITY"."CHECK_DATE" IS '검증일';

CREATE TABLE "BID" (
	"BID_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"ITEM_NO"	NUMBER		NOT NULL,
	"PRICE"	NUMBER	DEFAULT 0	NOT NULL,
	"QUIT_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"BID_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "BID"."BID_NO" IS '입찰 번호';

COMMENT ON COLUMN "BID"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "BID"."ITEM_NO" IS '상품 번호';

COMMENT ON COLUMN "BID"."PRICE" IS '입찰가';

COMMENT ON COLUMN "BID"."QUIT_YN" IS '취소 여부';

COMMENT ON COLUMN "BID"."BID_DATE" IS '입찰 시간';

CREATE TABLE "K_ADMIN" (
	"ADMIN_NO"	NUMBER		NOT NULL,
	"ID"	VARCHAR2(20)		NOT NULL,
	"PWD"	VARCHAR2(20)		NOT NULL,
	"NICK"	VARCHAR2(20)		NOT NULL,
	"KEY"	VARCHAR2(20)		NOT NULL,
	"BALANCE"	NUMBER	DEFAULT 0	NOT NULL,
	"QUIT_YN"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"SIGN_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "K_ADMIN"."ADMIN_NO" IS '관리자 번호';

COMMENT ON COLUMN "K_ADMIN"."ID" IS 'ID';

COMMENT ON COLUMN "K_ADMIN"."PWD" IS 'PWD';

COMMENT ON COLUMN "K_ADMIN"."NICK" IS 'PWD';

COMMENT ON COLUMN "K_ADMIN"."KEY" IS '세션키';

COMMENT ON COLUMN "K_ADMIN"."BALANCE" IS '잔액';

COMMENT ON COLUMN "K_ADMIN"."QUIT_YN" IS '탈퇴 여부';

COMMENT ON COLUMN "K_ADMIN"."SIGN_DATE" IS '가입일';

CREATE TABLE "ADMIN_ACCOUNT" (
	"USE_NO"	NUMBER		NOT NULL,
	"ADMIN_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"PRICE"	NUMBER	DEFAULT 0	NOT NULL,
	"USE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "ADMIN_ACCOUNT"."USE_NO" IS '내역 번호';

COMMENT ON COLUMN "ADMIN_ACCOUNT"."ADMIN_NO" IS '관리자 번호';

COMMENT ON COLUMN "ADMIN_ACCOUNT"."USER_NO" IS '대상 번호';

COMMENT ON COLUMN "ADMIN_ACCOUNT"."PRICE" IS '변동 금액';

COMMENT ON COLUMN "ADMIN_ACCOUNT"."USE_DATE" IS '이용일';

CREATE TABLE "CHAT_ROOM" (
	"ROOM_NO"	NUMBER		NOT NULL,
	"ITEM_NO"	NUMBER		NOT NULL,
	"USER_NO"	NUMBER		NOT NULL,
	"CREATE_DATE"	TIMESTAMP	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "CHAT_ROOM"."ROOM_NO" IS '채팅방 번호';

COMMENT ON COLUMN "CHAT_ROOM"."ITEM_NO" IS '상품 번호';

COMMENT ON COLUMN "CHAT_ROOM"."USER_NO" IS '유저 번호';

COMMENT ON COLUMN "CHAT_ROOM"."CREATE_DATE" IS '생성 일자';

ALTER TABLE "K_USER" ADD CONSTRAINT "PK_K_USER" PRIMARY KEY (
	"USER_NO"
);

ALTER TABLE "QNA" ADD CONSTRAINT "PK_QNA" PRIMARY KEY (
	"QUESTION_NO"
);

ALTER TABLE "LIKELIST" ADD CONSTRAINT "PK_LIKELIST" PRIMARY KEY (
	"ITEM_NO",
	"USER_NO"
);

ALTER TABLE "REVIEW" ADD CONSTRAINT "PK_REVIEW" PRIMARY KEY (
	"REVIEW_NO"
);

ALTER TABLE "ITEM" ADD CONSTRAINT "PK_ITEM" PRIMARY KEY (
	"ITEM_NO"
);

ALTER TABLE "ITEM_TYPE" ADD CONSTRAINT "PK_ITEM_TYPE" PRIMARY KEY (
	"TYPE_NO"
);

ALTER TABLE "HINT_TYPE" ADD CONSTRAINT "PK_HINT_TYPE" PRIMARY KEY (
	"QUESTION_NO"
);

ALTER TABLE "NOTICE" ADD CONSTRAINT "PK_NOTICE" PRIMARY KEY (
	"PUBLIC_NO"
);

ALTER TABLE "AUCTION" ADD CONSTRAINT "PK_AUCTION" PRIMARY KEY (
	"ITEM_NO"
);

ALTER TABLE "FAQ" ADD CONSTRAINT "PK_FAQ" PRIMARY KEY (
	"QUESTION_NO"
);

ALTER TABLE "BANNED" ADD CONSTRAINT "PK_BANNED" PRIMARY KEY (
	"STOP_NO"
);

ALTER TABLE "USER_ACCOUNT" ADD CONSTRAINT "PK_USER_ACCOUNT" PRIMARY KEY (
	"USE_NO"
);

ALTER TABLE "CHAT_CONTENT" ADD CONSTRAINT "PK_CHAT_CONTENT" PRIMARY KEY (
	"CHAT_NO"
);

ALTER TABLE "QUALITY" ADD CONSTRAINT "PK_QUALITY" PRIMARY KEY (
	"ITEM_NO"
);

ALTER TABLE "BID" ADD CONSTRAINT "PK_BID" PRIMARY KEY (
	"BID_NO"
);

ALTER TABLE "K_ADMIN" ADD CONSTRAINT "PK_K_ADMIN" PRIMARY KEY (
	"ADMIN_NO"
);

ALTER TABLE "ADMIN_ACCOUNT" ADD CONSTRAINT "PK_ADMIN_ACCOUNT" PRIMARY KEY (
	"USE_NO"
);

ALTER TABLE "CHAT_ROOM" ADD CONSTRAINT "PK_CHAT_ROOM" PRIMARY KEY (
	"ROOM_NO"
);

ALTER TABLE "LIKELIST" ADD CONSTRAINT "FK_ITEM_TO_LIKELIST_1" FOREIGN KEY (
	"ITEM_NO"
)
REFERENCES "ITEM" (
	"ITEM_NO"
);

ALTER TABLE "LIKELIST" ADD CONSTRAINT "FK_K_USER_TO_LIKELIST_1" FOREIGN KEY (
	"USER_NO"
)
REFERENCES "K_USER" (
	"USER_NO"
);

ALTER TABLE "AUCTION" ADD CONSTRAINT "FK_ITEM_TO_AUCTION_1" FOREIGN KEY (
	"ITEM_NO"
)
REFERENCES "ITEM" (
	"ITEM_NO"
);

ALTER TABLE "QUALITY" ADD CONSTRAINT "FK_ITEM_TO_QUALITY_1" FOREIGN KEY (
	"ITEM_NO"
)
REFERENCES "ITEM" (
	"ITEM_NO"
);
