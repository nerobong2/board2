package com.board.control;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class ServletRequestDataBinder {

		public static Object bind(HttpServletRequest request, Class<?> dataType, String dataName){
			
			Object dataObject = null;
			
			try{
				//일반형 (int,float,long,double,date,String,boolean) 일경우 는 바로 해당객체 생성 하고 리퀘스트로부터 값을 저장 하고 반환
				if(isPrimitiveType(dataType)){
					
					return  createValueObject(dataType,request.getParameter(dataName));
					
				}
				
				
				//사용자정의 객체일 경우 
				
				//1. 요청 파라미터 이름들을 배열로저장
				Set<String> paramNames = request.getParameterMap().keySet();
				
				for(String param : paramNames){
					
					System.out.println("요청파라미터 name : "+param + "값 : "+ request.getParameterMap().get(param));
					
				}
				
				//2. 각각의 컨트롤러에 정의된 데이터 타입 의 객체 생성
				dataObject = dataType.newInstance();
				
				System.out.println(dataObject.getClass());
				
				//3. 메소드 객체 생성 (하나의 파라미터에 에 대한 하나의 셋터 메소드가 리턴된다.)
				Method m = null;
			
				//4. 클래스 타입과 파라미터 이름을 받는 findSetter를 호출 해당 메소드를 찾는다.
				for(String paramName : paramNames){
					
					m = findSetter(dataType,paramName);
					
					System.out.println("인보크하게될 메소드 : "+m.getName());
					
					//밑에 if 문 해석   ------ > dataObject.m메서드(createValueObject(타입,값)))
					if(m!=null){
						m.invoke(dataObject, createValueObject(m.getParameterTypes()[0],request.getParameter(paramName)));
					}
						
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return dataObject;
			
		}
		//해당 일반 객체 생성 반환
		private static Object createValueObject(Class<?> dataType,
				String parameter) {
			// TODO Auto-generated method stub
			
			if(dataType.getName().equals("int")|| dataType == Integer.class){
				
				return new Integer(parameter);
			}else if(dataType.getName().equals("float")|| dataType == Float.class){
				
				return new Float(parameter);
			}else if(dataType.getName().equals("double")|| dataType == Double.class){
				
				return new Double(parameter);
			}else if(dataType.getName().equals("long") || dataType == Long.class){
				
				return new Long(parameter);
			}else if(dataType.getName().equals("boolean") || dataType == Boolean.class){
				
				return new Boolean(parameter);
			}else if(dataType == Date.class){
				
				return java.sql.Date.valueOf(parameter);
			}else{
				
				return parameter;
			}
			
		}
		
		//데이터 형 검사
		private static boolean isPrimitiveType(Class<?> dataType) {
			
			//일반데이터 타입일 경우.
			if(dataType.getName().equals("int") || 
			   dataType == Integer.class ||
			   dataType.getName().equals("float") ||
			   dataType == Float.class ||
			   dataType.getName().equals("double") ||
			   dataType == Double.class ||
			   dataType.getName().equals("long") || 
			   dataType == Long.class ||
			   dataType.getName().equals("boolean") ||
			   dataType == Boolean.class ||
			   dataType == Date.class || dataType == String.class){
				
				
				return true;
				
			}
				
			return false;
		}
		private static Method findSetter(Class<?> dataType, String paramName){
			
			Method [] methods = dataType.getMethods();
			
		
			String propName = null;
			
			for(Method m : methods){
				
				
				//set 메소드가 아닐경우는 무시.
				if(!m.getName().startsWith("set"))continue;
				
				
				propName = m.getName().substring(3);
				
				
				if(propName.toLowerCase().equals(paramName.toLowerCase())){
					
					return m;
				}
			}
			
			return null;
		}
}
