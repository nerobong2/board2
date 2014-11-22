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
		
		System.out.println("요청 url : " + controlInfo);
		
		PageControl	pageUrl = (PageControl) ctx.getBean(controlInfo);
		
		//1.데이터를 필요로 하는지에 대한 여부를 판단하여 실행.
		if( pageUrl instanceof DataBinding ){
			//DataBinding interface 를 구현한 경우 실행
			try {
				//각각의 pageUrl엔 필요로 하는 데이터가 {이름,타입} 으로 정의되어있다.
				//request 로 부터 파라미터를 받아야한다.
				
				preparedRequestData(request,model,(DataBinding)pageUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String processResult = pageUrl.excute(model);
		
		//컨트롤러에서 model 객체에 담은 각각의 jsp에서 화면 구성에 필요한 데이터들을 리퀘스트에 담는다.
		for(String key : model.keySet()){
			request.setAttribute(key, model.get(key));
		}
		
		
		
		if(processResult.startsWith("foward:")){
			
			System.out.println(processResult.split(":")[1] + " -- > fowarding");
			RequestDispatcher rd = request.getRequestDispatcher(processResult.split(":")[1]);
			
			rd.forward(request, response);
			
			
		}else{
			//다른페이지로 전환시
			System.out.println(contextPath+"/"+processResult.split(":")[1] + " -- > sendRedirect");
			response.sendRedirect(contextPath+"/"+processResult.split(":")[1]);
			
		}
		
	}

	//요청에 따른 데이터를 필요로 하는객체에 넣어서 모델에 셋팅 해야한다.
	private void preparedRequestData(HttpServletRequest request,
			HashMap<String, Object> model, DataBinding pageUrl) throws Exception{
		Object [] dataBinders = pageUrl.getDataBinders();
		
		
		String dataName = null;
		Class<?> dataType = null;
		Object dataObject = null;
		
		//각각의 컨트롤에 정의된 데이터타입을 바인터스에 저장 
		for(int i = 0 ;  i < dataBinders.length ; i+=2){
			
			
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			
			dataObject = ServletRequestDataBinder.bind(request,dataType,dataName);
			model.put(dataName, dataObject);
			
		}

		
		
	}

	
}
