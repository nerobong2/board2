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
		System.out.println("Ŀ�ؼ� ��ü pool���� ���");
		//�̹� Ŀ�ؼ� ��ü�� �������!
		if(connList.size() > 0){
			
			System.out.println("Ŀ�ؼ� �����Ǿ�����");
			//ù��° Ŀ�ؼ� ��ü�� ������ ��ȿ�� üũ�� ����
			if(connList.get(0).isValid(10)){
				
				System.out.println("��밡�� Ŀ�ؼ�");
				return connList.remove(0);
			}else{
				System.out.println("���Ұ��� Ŀ�ؼ�");
			}
			
		}
		
		//Ŀ�ؼ� ��ü�� ������� ���ο� �ν��Ͻ��� �����ϰ� connList�� �߰�
		System.out.println("���ο� Ŀ�ؼ� �ν���Ʈ ���� �� ��ȯ pool �� ���");
		Connection conn = DriverManager.getConnection(dburl, id, password);
		connList.add(conn);
		
		return connList.get(0);
		
	}
	
	public void returnConnection(Connection conn){
		
		System.out.println("Ŀ�ؼ� ��ü ����� ��ȯ");
		connList.add(conn);
		
	}
	public ArrayList<Connection> getConnList(){
		
		return connList;
	}
}
