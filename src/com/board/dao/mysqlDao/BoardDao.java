package com.board.dao.mysqlDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.board.annotation.Component;
import com.board.dao.CommonBoardDao;
import com.board.vo.Board;


@Component("boardDao")
public class BoardDao implements CommonBoardDao {

	//jndi 사용시
	/*DataSource ds;
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}*/
	
	SqlSessionFactory sessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		this.sessionFactory = sqlSessionFactory;
	}
	
	//게시글 전체 불러오기
	public List<Board> getBoardList() {
		
		SqlSession session = sessionFactory.openSession();
		
		try{
			return session.selectList("com.board.dao.boardDao.getBoardList");
		}finally{
			session.close();
		}
		
		//DBConnectionPool connPool;
		/*Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null ;			
		
		String sql = " select t1.boardNo, t1.boardSubject, t1.regDate, t2.userName "
				+ " from  board t1, user t2"
				+ " where t1.writerNo = t2.userNo"
				+ " order by t1.boardNo asc";
		
		ArrayList<Board> boardList = new ArrayList<Board>();
		
		try{
			
			conn 	= ds.getConnection();
			pstmt 	= conn.prepareStatement(sql);
			rs 		= pstmt.executeQuery();
			
			while(rs.next()){
				Board board = new Board().setBoardNo(rs.getInt("boardNo"))
										.setBoardSubject(rs.getString("boardSubject"))
										.setWriterName(rs.getString("userName"))
										.setRegDate(rs.getDate("regDate"));
						
				boardList.add(board);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
			
		}finally{
			try{rs.close();pstmt.close();conn.close();}catch(Exception e){}
		}
		
		return boardList;*/
		
	}
	//게시글 상세보기
	public Board getBoard(Board board) {
		
		
		SqlSession session = sessionFactory.openSession();
		try{
			return session.selectOne("com.board.dao.boardDao.getBoard", board);
		}finally{
			session.close();
		}
		
		/*Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = " select t1.boardNo, t1.boardSubject, t1.regDate,t1.boardContent, t2.userName "
				+ " from  board t1, user t2"
				+ " where t1.writerNo = t2.userNo"
				+ " and t1.boardNo = ?";
		
		try{
			
			conn 	= ds.getConnection();
			pstmt 	= conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoardNo());
			
			rs 		= pstmt.executeQuery();
			
			while(rs.next()){
				board = new Board().setBoardNo(rs.getInt("boardNo"))
										.setBoardSubject(rs.getString("boardSubject"))
										.setWriterName(rs.getString("userName"))
										.setBoardContent(rs.getString("boardContent"))
										.setRegDate(rs.getDate("regDate"));
						
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();pstmt.close();conn.close();}catch(Exception e){}
		}
		
		return board;*/
	}
	public void writeBoard(Board board) {
		
		System.out.println(board.getWriterNo() + " 번 회원 \n 제목: " + board.getBoardSubject()
				 + "\n내용 : " + board.getBoardContent());
		
		SqlSession session = sessionFactory.openSession();
		
		try{
			session.insert("com.board.dao.boardDao.writeBoard", board);
			session.commit();
		}catch(Exception d){
			d.printStackTrace();
		}finally{
			session.close();
		}
		
		// TODO Auto-generated method stub
		/*Connection conn = null;
		PreparedStatement pstmt = null;
		
		System.out.println(board.toString());
		
		String query = " insert into Board(boardSubject,boardContent,writerNo,regDate)"
				 	 + " values(?,?,?,now())";
		
		try{
			
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBoardSubject());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getWriterNo());
			pstmt.execute();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}finally{
			try{pstmt.close();conn.close();}catch(Exception e){}
		}*/
		
		
	}
}
