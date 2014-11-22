package com.board.dao;

import java.util.List;

import com.board.vo.Board;

public interface CommonBoardDao {
	List<Board> getBoardList();
	Board getBoard(Board board);
	void writeBoard(Board board);
}
