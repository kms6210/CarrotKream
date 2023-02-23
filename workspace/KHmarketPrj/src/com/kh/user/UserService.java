package com.kh.user;

import java.sql.Connection;
import java.sql.SQLException;

import com.kh.main.Main;

public class UserService {
	
	
	private User user = new User();
	
    
    
    public void userPage(Connection conn , int ARAMDOM) throws Exception{
		boolean back = false;
		while(!back) {
			System.out.println("==================");
			System.out.println("유저 페이지");
			System.out.println("------------------");
			
			if(Main.login_member_no == 0) {
				System.out.println("안녕하세요");
				System.out.println("방문자 "+ ARAMDOM  +" 님\n");
				System.out.println("1. 회원가입\n"
						 		 + "2. 로그인\n"
						 		 + "3. 아이디 찾기(힌트)\n"
								 + "4. 비밀번호 찾기(아이디 & 힌트)\n"
								 + "99. 메인으로");
				System.out.print("번호를 입력하세요 : ");
				String input = Main.SC.nextLine();
				System.out.println("");
				
				switch (input) {
				case "1" : if(user.join(conn) == 0) { throw new Exception("※ 회원가입 실패 ※"); } else { System.out.println("\n※ 회원등록 완료 ※"); } break;
				case "2" : if(user.login(conn) == 0) { throw new Exception("※ 로그인 실패 ※"); } break;
				case "3" : user.findId(conn); break;
				case "4" : user.findPwd(conn); break;
				case "99" : back = true; break;
				default : System.out.println("잘못된 입력입니다.");
				}
			}
			else {
				
				System.out.println("안녕하세요");
				System.out.println("");
				System.out.println(
						"1. 회원 탈퇴\n"
								+ "2. 질문하기\n"
								+ "3. 내가 한 질문 조회/삭제\n"
								+ "33. 로그아웃\n"
								+ "99. 메인으로\n"
								+ "999. 유저조회(테스트용)");
				
				System.out.print("번호를 입력하세요 : ");
				String input = Main.SC.nextLine();
				
				
				switch (input) {
				case "1" : if(user.dropUser(conn) == 0) {throw new Exception("회원가입 실패"); } break;
				case "2" : if(user.askQuestion(conn) == 0) { throw new Exception("질문 등록 실패"); } break;
				case "3" : user.QuestionList(conn); break;
                case "33" : System.out.println("로그아웃 완료\n"); Main.login_member_no = 0; back = true; break;
				case "99" : back = true; break;
				case "999" : if(user.userList(conn) == null) { throw new Exception("유저목록 불러오기 실패"); } break;
				default: System.out.println("잘못 입력하셨습니다….");
					
				}
			}
		}
		
	}
	

	
}
