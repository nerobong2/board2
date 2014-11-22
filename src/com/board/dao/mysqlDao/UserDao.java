package com.board.dao.mysqlDao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.board.annotation.Component;
import com.board.dao.CommonUserDao;
import com.board.vo.User;

@Component("userDao")
public class UserDao implements CommonUserDao{
	
	
	//jndi ����
	/*DataSource ds;
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}*/
	
	SqlSessionFactory sessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		this.sessionFactory = sqlSessionFactory;
	}
	
	//ȸ�� ���� ���� Ȯ��
	public User isSignUp(User user) {
		SqlSession session = sessionFactory.openSession();
		try{
			return session.selectOne("com.board.dao.userDao.isSignUp", user);
		}finally{
			session.close();
		}
		
		/*Connection 			conn = null;
		PreparedStatement 	pstmt = null;
		ResultSet 			rs     = null;
		
		String 				sql = " select * from user"
								+ " where userId = ?";
			try{
				
				conn  = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getUserId());
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					user.setUserNo(rs.getInt("userNo"))
						.setUserId(rs.getString("userId"))
						.setUserName(rs.getString("userName"))
						.setUserPassword(rs.getString("userPassword"))
						.setRegDate(rs.getDate("regDate"));
					
				}
				
			
			}catch(Exception e){
				
				e.printStackTrace();
			}finally{
				
				try{rs.close();pstmt.close();conn.close();}catch(Exception e){}
			}
		
		return user;*/
	}
	//ȸ�� ���� ���
	public void userSignUp(User user) {
		SqlSession session = sessionFactory.openSession();
		try{
			
			session.selectOne("com.board.dao.userDao.userSignUp");
		}finally{
			session.close();
		}


		/*Connection 			conn = null;
		PreparedStatement 	pstmt = null;
		
		String				sql	= " insert into user(userId,userName,userPassword,regDate)"
								 +" values (?,?,?,now())";
		try{
			
			conn  = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getUserPassword());
			
			pstmt.execute();
			System.out.println("ȸ������ ����!!");
			
		}catch(Exception e){
			
			e.printStackTrace();
		}finally{
			
			try{pstmt.close();conn.close();}catch(Exception e){}
		}
		
			*/
		
	}
	//�α��� ��� 
	public User userLogin(User user) {
		SqlSession session = sessionFactory.openSession();
		try{
			return session.selectOne("com.board.dao.userDao.userLogin", user);
		}finally{
			session.close();
		}

		
		/*Connection 			conn = null;
		PreparedStatement 	pstmt = null;
		ResultSet 			rs     = null;
		
		
		
		String 				sql = " select * from user"
								+ " where userId = ? and userPassword = ? ";
			try{
				
				conn  = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getUserPassword());
				
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					user.setUserNo(rs.getInt("userNo"))
							.setUserId(rs.getString("userId"))
							.setUserName(rs.getString("userName"))
							.setUserPassword(rs.getString("userPassword"))
							.setRegDate(rs.getDate("regDate"));
					
					
				}
				
			
			}catch(Exception e){
				
				e.printStackTrace();
			}finally{
				
				try{rs.close();pstmt.close();conn.close();}catch(Exception e){}
			}
		
			
		return user;*/
	}
	
}
