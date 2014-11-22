package com.board.listener;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.board.db.DBConnectionPool;

public class WebAppListener implements ServletContextListener{

	
	DBConnectionPool conPool;
	static ApplicationContext context;
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		
		//내가만든 DBConnectionPool 객체 사용시
		/*System.out.println("앱종료 db 커넥션 객체 자원 해제");
		ArrayList<Connection> list =  conPool.getConnList();
		
		for(Connection con : list){
			
			try {
				
				con.close();
				
			} catch (SQLException e1) {
				
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			}
		}*/
		
	}
	
	static public  ApplicationContext getApplicationContext(){
		return context;
	}
	
	
	@Override
	public void contextInitialized(ServletContextEvent e)  {
		
		System.out.println("contextInitialized...");
		
		
		
		try{
			
			context = new ApplicationContext(); 
			//설계도
			String resource = "com/board/dao/mybatis-config.xml";
			
			System.out.println(1);
			
			//스트림 객체로 전달				//ibatis 제공 객체 파일위치 지정 
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			System.out.println(2);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			System.out.println(3);
			//objTable에 mybatis 제공 객체 SqlSessionFactory 저장
			context.addBean("sqlSessionFactory", sqlSessionFactory);
			
			System.out.println(4);
			ServletContext ctx = e.getServletContext();
			String propertiesPath = ctx.getRealPath(
					ctx.getInitParameter("propertiesCongifLocation"));
			
			System.out.println(5);
			//objectTable 에 프로 퍼티 객체 저장 (DataSource 객체 : jndi)
			context.preparePropertieObject(propertiesPath);
			
			System.out.println(6);
			//objectTable 에 에노테이션 컴포터는 객체 생성 저장(dao,control)
			context.prepareAnnotationObject("");
			
			System.out.println(7);
			//di 구현
			context.dependencyInjection();
					
			System.out.println(8);
			
			
			//dependencyinjection 하기 전 수동으로 객체주입
			/*InitialContext initailContext = new InitialContext();
			
			DataSource ds = (DataSource)initailContext.lookup("java:comp/env/jdbc/boarddb");
			
			UserDao userDao = new UserDao(ds);
			
			BoardDao boardDao = new BoardDao(ds);

			ctx.setAttribute("login.do", new LoginControl().setDao(userDao, boardDao));
			ctx.setAttribute("signUp.do", new SignUpControl().setDao(userDao,boardDao));
			ctx.setAttribute("main.do", new MainControl().setDao(userDao,boardDao));
			ctx.setAttribute("logout.do", new LogoutControl().setDao(userDao,boardDao));
			ctx.setAttribute("view.do", new BoardViewControl().setDao(userDao,boardDao));
			ctx.setAttribute("write.do", new BoardWriteControl().setDao(userDao,boardDao));*/
			
			
		}catch(Exception e2){
			e2.printStackTrace();
		}
		
		
		//준비된 객체확인용
		
		for(String key : context.objTable.keySet()){
			
			System.out.println("객체이름 : " + key);
			System.out.println("저장된객체 : " + context.objTable.get(key));
		}
		
		//jndi (서버에서 관리하는 커넥션 풀 사용하는 기술) 적용전 web.xml 초기화 파라미터로 DBConnection 파라미터 쓰기위해 
	/*	String driver	 = ctx.getInitParameter("driver");
		String dburl	 = ctx.getInitParameter("dburl");
		String id		 = ctx.getInitParameter("id");
		String password	 = ctx.getInitParameter("password");
		
		try {
			
			conPool = new DBConnectionPool(driver,dburl,id,password);
			
		} catch (ClassNotFoundException e1) {
			
			
			e1.printStackTrace();
		}
		*/
		
		
		
		
	}

}
