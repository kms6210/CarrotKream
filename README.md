# team1Project : KHmarket

[작업할 package]
1. 강우석 : item
2. 김도연 : admin
3. 김민성 : main, account, auction, center
4. 김영광 : user
5. 이승권 : mutualAction

[칼럼명]
@@_YN, @@_STATUS = 'N' (진행중) / 'Y' (완료시)

1. 탈퇴한 아이디 ( USER_STATUS = 'Q' ) - K_USER
2. 정지된 아이디 ( USER_STATUS = 'S' ) - K_USER
2. 삭제된 상품 ( TRADE_STATUS = 'D' ) - ITEM
3. 구매 상품 (TRADE_STATUS = 'B') - ITEM
4. 판매 상품 (TRADE_STATUS = 'S') - ITEM
5. 경매 상품 (TRADE_STATUS = 'A') - ITEM
6. 입찰 삭제 (QUIT_YN = 'D') - BID
