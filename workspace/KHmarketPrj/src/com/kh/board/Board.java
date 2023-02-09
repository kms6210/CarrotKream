package com.kh.board;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Board {
	// 게시글 번호, 제목, 내용, 작성일시, 댓글
	private int boardNum;
	private String title;
	private String content;
	private Timestamp time;
	private ArrayList<String> commentList;
	
	public Board(int boardNum, String title, String content, Timestamp time, ArrayList<String> commentList) {
		this.boardNum = boardNum;
		this.title = title;
		this.content = content;
		this.time = time;
		this.commentList = commentList;
	}
}
