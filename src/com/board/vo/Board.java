package com.board.vo;

import java.sql.Date;
import java.util.ArrayList;

public class Board {
	
	private int boardNo;
	private String boardSubject;
	private String boardContent;
	private Date regDate;
	private int writerNo;
	private String writerName;
	
	
	public String getWriterName() {
		return writerName;
	}
	public Board setWriterName(String writerName) {
		this.writerName = writerName;
		return this;
	}
	
	
	public int getBoardNo() {
		return boardNo;
	}
	public Board setBoardNo(int boardNo) {
		this.boardNo = boardNo;
		return this;
	}
	public String getBoardSubject() {
		return boardSubject;
	}
	public Board setBoardSubject(String boardSubject) {
		this.boardSubject = boardSubject;
		return this;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public Board setBoardContent(String boardContent) {
		this.boardContent = boardContent;
		return this;
	}
	public Date getRegDate() {
		return regDate;
	}
	public Board setRegDate(Date regDate) {
		this.regDate = regDate;
		return this;
	}
	public int getWriterNo() {
		return writerNo;
	}
	public Board setWriterNo(int writerNo) {
		this.writerNo = writerNo;
		return this;
	}
}
