package com.kh.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.kh.admin.Admin;
import com.kh.main.Main;

public class UserInput {
	
	public boolean tf = false;
	
	private String idCheck = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,20}$";
	private String pwdCheck = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$"; // 영문, 숫자, 특수문자 , 8~15
	private String PhoneCheck = "^01([0-9])-?([0-9]{4})-?([0-9]{4})$"; // 01로 시작하는 전화번호 -는 랜덤
	private String qusCheck = "[0-9]+";
	private String userId = null;
	private String userPwd = null;
	private String userPhone = null;
	private String userQuestion = null;

	
	// 회원 가입 정보입력
	public UserData UserJoinInput(Connection conn) throws Exception {
		System.out.println("┌─────────────────────────────────────────────────┐");
		System.out.println("		   🥕 회원가입 🥕");
		System.out.println("		   -----------\n");
		do {
			tf = false;
			while(!tf) {
				System.out.print("		아이디 : ");
				userId = Main.SC.nextLine();
				try {
					tf = applyIdRule(userId, conn);
				} catch (Exception e) {
					
				}
			}
		} while(false);	
		
		do {
			tf = false;
			while(!tf) {
				System.out.print("		비밀번호 : ");
				userPwd = Main.SC.nextLine();
				tf = applyjoinRule(userPwd);
				if(tf) { showPwdGrade(userPwd); }
			}
		} while(false);	
								
		System.out.print("		닉네임 : ");
		String userNick = Main.SC.nextLine();
		
		do {
			tf = false;
			while(!tf) {
				System.out.print("\n		전화번호 : ");
				String userPhone = Main.SC.nextLine();
				tf = applyPhonNoRule(userPhone);
				if(tf) {
					userPhone = castPhonNo(userPhone);
					if(phoneOverlapCheck(userPhone,conn)) {
						System.out.println("		⚠이미 등록된 전화 번호입니다.");
						tf = false;
					}
				}
			}
		} while(false);	
		
		System.out.print("\n		주소 : ");
		String userAddress = Main.SC.nextLine();
				
		do {
			tf = false;
			while(!tf) {
				if(showHintList(conn) == 0) {throw new Exception("힌트 목록을 불러오지 못했습니다.");}
				System.out.print("		힌트 질문 번호 : ");
				userQuestion = Main.SC.nextLine();
				tf = applyQuestionRule(userQuestion);
			}
		} while(false);	
		
		System.out.print("\n		힌트 답변 : ");
		String userAnswer = Main.SC.nextLine();	
		
		System.out.println("\n└─────────────────────────────────────────────────┘");
		
		UserData data = new UserData();
		data.setUserId(userId);
		data.setUserPwd(userPwd);
		data.setUserNick(userNick);
		data.setUserPhone(userPhone);
		data.setUserAddress(userAddress);
		data.setUserQuestion(userQuestion);
		data.setUserAnswer(userAnswer);
		
		return data;
		
	}
	
	// 아이디 유효성 체크
	public boolean applyIdRule(String userId, Connection conn) throws Exception {
		if (userId.matches(idCheck)) {
			if(!idOverlapCheck(userId, conn)) {
				System.out.println();
				return true;
			} else {
				System.out.println("		⚠이미 사용중인 아이디입니다\n");
				return false;
			}
			
		} else { 
			System.out.println("		⚠아이디는 5~20자 \n	→ 한글자 이상의 영문과 숫자로 이루어져야 합니다\n");
			return false;
		}
	}
	
	// 아이디 중복체크
	private boolean idOverlapCheck(String userId, Connection conn) throws Exception {
		String sql = "SELECT ID FROM K_USER WHERE UPPER(ID) = UPPER( ? )";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	// 비밀번호 유효성 체크 (특수문자포함, 8~15자리 ...)
	public boolean applyjoinRule(String userPwd) {
		if (userPwd.matches(pwdCheck)) {
			return true;
		} else {
			System.out.println("		⚠비밀번호는 8~15자 \n	→ 영문, 숫자, 특수문자를 포함해야합니다\n");
			return false;
		}
	}
	
	// 비번 설정시 보안 등급 출력 (ex. 낮음 중간 높음 ...)
		public void showPwdGrade(String userPwd) {
			if(userPwd.length() == 15) {
				System.out.println("		[보안등급 높음 ✔✔✔]\n"); 
			} else if (userPwd.length() >= 10 && userPwd.length() < 15 ) {
				System.out.println("		[보안등급 보통 ✔✔]\n");
			} else if (userPwd.length() < 10) {
				System.out.println("		[보안등급 낮음 ✔]\n");
			}
		}
	
	// 전화번호 유효성 체크
	public boolean applyPhonNoRule(String userPhone) {
		if(userPhone.matches(PhoneCheck)) {
			return true;
		}
		else {
			System.out.println("		⚠전화번호는 11~13자 \n		→ 01로 시작하는 숫자나 \n		→ 01*-****-**** 형식으로 입력하세요");
			return false;
		}
	}
	
	// 전화번호 중복체크
	private boolean phoneOverlapCheck(String userPhone, Connection conn) throws Exception {
		String sql = "SELECT PHONE_NO FROM K_USER WHERE PHONE_NO = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userPhone);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}
	
	//전화번호 형태 변환
	public String castPhonNo(String userPhone) {
		this.userPhone = userPhone.replaceAll("-", "");
		String s1 = this.userPhone.substring(0, 3);
		String s2 = this.userPhone.substring(3, 7);
		String s3 = this.userPhone.substring(7, 11);
		
		this.userPhone = s1 + "-" + s2 + "-" + s3;
		return this.userPhone;
	}
	
	// 힌트 질문 목록 불러오기
	public int showHintList(Connection conn) throws Exception {
		String sql = "SELECT QUESTION_NO, QUESTION FROM HINT_TYPE";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int i = 0;
		System.out.println("\n ┌------------------┤ 질문 목록 ├------------------┐\n");
		while(rs.next()) {
			String question = rs.getString("QUESTION");
			System.out.println("	"+(++i) +". "+question+" ");
		}
		System.out.println("\n └----------------------------------------------┘");
		System.out.println("");
		return i;
	}
		
	// 힌트 번호 유효성 체크
	private boolean applyQuestionRule(String userQuestion)	{
		if(userQuestion.matches(qusCheck)) {
			return true;
		} else {
			System.out.println("		⚠숫자만 입력하세요\n");
			return false;
		}
	}
	
	//로그인 정보 입력
	public UserData UserLoginInput(Connection conn, String userId) throws Exception {
		
		System.out.print("		비밀번호ㅤ: ");
		String userPwd = Main.SC.nextLine();
		System.out.println("\n└─────────────────────────────────────────────────┘");
		
		UserData data = new UserData();
		data.setUserId(userId);
		data.setUserPwd(userPwd);
		return data;
	}
	
	
	// 아이디 찾기 입력 받기
	public UserData findUserIdInput(String phoneNo, String question) {
		System.out.println("\n		    질문 : "+question);
		System.out.print("\n		    답변 : ");
		String joinAnswer = Main.SC.nextLine();
		
		UserData data = new UserData();
		
		data.setUserPhone(phoneNo);
		data.setUserAnswer(joinAnswer);
		return data;
	}
	
	
	//전화번호 입력 받기
	public String phoneNoInput() {
        boolean check = false;
        String joinPhoneNo = null;
        
        
        while(!check) {
            System.out.print("\n	 가입시 입력한 전화번호 : ");
            joinPhoneNo = Main.SC.nextLine();
            if(applyPhonNoRule(joinPhoneNo)) {
                joinPhoneNo = joinPhoneNo.replaceAll("-", "");
                String s1 = joinPhoneNo.substring(0, 3);
                String s2 = joinPhoneNo.substring(3, 7);
                String s3 = joinPhoneNo.substring(7, 11);
                
                joinPhoneNo = s1 + "-" + s2 + "-" + s3;
            }
                    
            if(joinPhoneNo.matches(PhoneCheck)) {
                return joinPhoneNo;
            }
            
        }
		return joinPhoneNo;
	}
		
    // 유저 정보 출력
    public static int userInfo(Connection conn) throws Exception {
        String sql = "SELECT * FROM K_USER WHERE USER_NO = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, Main.login_member_no);
        ResultSet rs = pstmt.executeQuery();
        
        int check = 0;
        if(rs.next()) {
            check++;
            Main.login_member_no = rs.getInt("USER_NO");
            String nick = rs.getString("NICK");
            String balance = rs.getString("BALANCE");
            String trustLevel = rs.getString("TRUST_LEVEL");
            
            System.out.println("\n              "+nick + " 님 환영합니다.");
            System.out.println("\n               매너온도  : "+ trustLevel);
            if(balance != null) {
                System.out.println("\n                  잔액  : "+ balance);
            } else {
                System.out.println("\n               생성된 계좌가 없습니다.");
            }
            
        }
        return check;
    }
    
    // 비밀번호 찾기 임시 비밀번호
    public StringBuffer ramdomPwd() {
		Random ramdom = new Random();
		StringBuffer buf = new StringBuffer();
		
		for(int i = 0 ; i < 15 ; i++) {
			if(ramdom.nextBoolean()) {
				buf.append((char)((int)(ramdom.nextInt(26))+97));
			} else {
				buf.append((ramdom.nextInt(10)));
			}
			
		}
		return buf;
	}
	
}
