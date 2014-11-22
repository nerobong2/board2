package com.board.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnectionPool {

	
	String	driver;
	String 	dburl;
	String 	id;
	String 	password;
	
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String driver, String dburl, String id,String password) throws ClassNotFoundException {
		
		System.out.println("DBConnectionPool()...");
		
		this.driver 	= driver;
		this.dburl		= dburl;
		this.id			= id;
		this.password	=	password;
		
		
		Class.forName(driver);
		
		
	}
	
	public Connection getConnection() throws SQLException{
		System.out.println("커넥션 객체 pool에서 얻기");
		//이미 커넥션 객체가 있을경우!
		if(connList.size() > 0){
			
			System.out.println("커넥션 생성되어있음");
			//첫번째 커넥션 객체를 가져와 유효성 체크후 리턴
			if(connList.get(0).isValid(10)){
				
				System.out.println("사용가능 커넥션");
				return connList.remove(0);
			}else{
				System.out.println("사용불가능 커넥션");
			}
			
		}
		
		//커넥션 객체가 없을경우 새로운 인스턴스를 생성하고 connList에 추가
		System.out.println("새로운 커넥션 인스턴트 생성 후 반환 pool 에 담기");
		Connection conn = DriverManager.getConnection(dburl, id, password);
		connList.add(conn);
		
		return connList.get(0);
		
	}
	
	public void returnConnection(Connection conn){
		
		System.out.println("커넥션 객체 사용후 반환");
		connList.add(conn);
		
	}
	public ArrayList<Connection> getConnList(){
		
		return connList;
	}
}
