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
		
		//�������� DBConnectionPool ��ü ����
		/*System.out.println("������ db Ŀ�ؼ� ��ü �ڿ� ����");
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
			//���赵
			String resource = "com/board/dao/mybatis-config.xml";
			
			System.out.println(1);
			
			//��Ʈ�� ��ü�� ����				//ibatis ���� ��ü ������ġ ���� 
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			System.out.println(2);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			System.out.println(3);
			//objTable�� mybatis ���� ��ü SqlSessionFactory ����
			context.addBean("sqlSessionFactory", sqlSessionFactory);
			
			System.out.println(4);
			ServletContext ctx = e.getServletContext();
			String propertiesPath = ctx.getRealPath(
					ctx.getInitParameter("propertiesCongifLocation"));
			
			System.out.println(5);
			//objectTable �� ���� ��Ƽ ��ü ���� (DataSource ��ü : jndi)
			context.preparePropertieObject(propertiesPath);
			
			System.out.println(6);
			//objectTable �� �������̼� �����ʹ� ��ü ���� ����(dao,control)
			context.prepareAnnotationObject("");
			
			System.out.println(7);
			//di ����
			context.dependencyInjection();
					
			System.out.println(8);
			
			
			//dependencyinjection �ϱ� �� �������� ��ü����
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
		
		
		//�غ�� ��üȮ�ο�
		
		for(String key : context.objTable.keySet()){
			
			System.out.println("��ü�̸� : " + key);
			System.out.println("����Ȱ�ü : " + context.objTable.get(key));
		}
		
		//jndi (�������� �����ϴ� Ŀ�ؼ� Ǯ ����ϴ� ���) ������ web.xml �ʱ�ȭ �Ķ���ͷ� DBConnection �Ķ���� �������� 
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
