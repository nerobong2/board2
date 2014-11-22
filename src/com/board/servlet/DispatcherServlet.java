package com.board.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.control.DataBinding;
import com.board.control.PageControl;
import com.board.control.ServletRequestDataBinder;
import com.board.listener.ApplicationContext;
import com.board.listener.WebAppListener;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DispatcherServlet...");
		
		HashMap<String,Object> model = new HashMap<String,Object>();
		
		ApplicationContext ctx = WebAppListener.getApplicationContext();
		//ServletContext ctx = request.getServletContext();
		HttpSession session  = request.getSession();
		
		
		model.put("session", session);
		model.put("request", request);
		model.put("response", response);
		
		String requestUrl = request.getRequestURI();
		String contextPath = request.getContextPath();
		String servletPath		= request.getServletPath();
		
		String controlInfo = servletPath.split("/")[1];
		
		System.out.println("��û url : " + controlInfo);
		
		PageControl	pageUrl = (PageControl) ctx.getBean(controlInfo);
		
		//1.�����͸� �ʿ�� �ϴ����� ���� ���θ� �Ǵ��Ͽ� ����.
		if( pageUrl instanceof DataBinding ){
			//DataBinding interface �� ������ ��� ����
			try {
				//������ pageUrl�� �ʿ�� �ϴ� �����Ͱ� {�̸�,Ÿ��} ���� ���ǵǾ��ִ�.
				//request �� ���� �Ķ���͸� �޾ƾ��Ѵ�.
				
				preparedRequestData(request,model,(DataBinding)pageUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String processResult = pageUrl.excute(model);
		
		//��Ʈ�ѷ����� model ��ü�� ���� ������ jsp���� ȭ�� ������ �ʿ��� �����͵��� ������Ʈ�� ��´�.
		for(String key : model.keySet()){
			request.setAttribute(key, model.get(key));
		}
		
		
		
		if(processResult.startsWith("foward:")){
			
			System.out.println(processResult.split(":")[1] + " -- > fowarding");
			RequestDispatcher rd = request.getRequestDispatcher(processResult.split(":")[1]);
			
			rd.forward(request, response);
			
			
		}else{
			//�ٸ��������� ��ȯ��
			System.out.println(contextPath+"/"+processResult.split(":")[1] + " -- > sendRedirect");
			response.sendRedirect(contextPath+"/"+processResult.split(":")[1]);
			
		}
		
	}

	//��û�� ���� �����͸� �ʿ�� �ϴ°�ü�� �־ �𵨿� ���� �ؾ��Ѵ�.
	private void preparedRequestData(HttpServletRequest request,
			HashMap<String, Object> model, DataBinding pageUrl) throws Exception{
		Object [] dataBinders = pageUrl.getDataBinders();
		
		
		String dataName = null;
		Class<?> dataType = null;
		Object dataObject = null;
		
		//������ ��Ʈ�ѿ� ���ǵ� ������Ÿ���� �����ͽ��� ���� 
		for(int i = 0 ;  i < dataBinders.length ; i+=2){
			
			
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			
			dataObject = ServletRequestDataBinder.bind(request,dataType,dataName);
			model.put(dataName, dataObject);
			
		}

		
		
	}

	
}
