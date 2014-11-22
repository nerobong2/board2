package com.board.control;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class ServletRequestDataBinder {

		public static Object bind(HttpServletRequest request, Class<?> dataType, String dataName){
			
			Object dataObject = null;
			
			try{
				//�Ϲ��� (int,float,long,double,date,String,boolean) �ϰ�� �� �ٷ� �ش簴ü ���� �ϰ� ������Ʈ�κ��� ���� ���� �ϰ� ��ȯ
				if(isPrimitiveType(dataType)){
					
					return  createValueObject(dataType,request.getParameter(dataName));
					
				}
				
				
				//��������� ��ü�� ��� 
				
				//1. ��û �Ķ���� �̸����� �迭������
				Set<String> paramNames = request.getParameterMap().keySet();
				
				for(String param : paramNames){
					
					System.out.println("��û�Ķ���� name : "+param + "�� : "+ request.getParameterMap().get(param));
					
				}
				
				//2. ������ ��Ʈ�ѷ��� ���ǵ� ������ Ÿ�� �� ��ü ����
				dataObject = dataType.newInstance();
				
				System.out.println(dataObject.getClass());
				
				//3. �޼ҵ� ��ü ���� (�ϳ��� �Ķ���Ϳ� �� ���� �ϳ��� ���� �޼ҵ尡 ���ϵȴ�.)
				Method m = null;
			
				//4. Ŭ���� Ÿ�԰� �Ķ���� �̸��� �޴� findSetter�� ȣ�� �ش� �޼ҵ带 ã�´�.
				for(String paramName : paramNames){
					
					m = findSetter(dataType,paramName);
					
					System.out.println("�κ�ũ�ϰԵ� �޼ҵ� : "+m.getName());
					
					//�ؿ� if �� �ؼ�   ------ > dataObject.m�޼���(createValueObject(Ÿ��,��)))
					if(m!=null){
						m.invoke(dataObject, createValueObject(m.getParameterTypes()[0],request.getParameter(paramName)));
					}
						
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return dataObject;
			
		}
		//�ش� �Ϲ� ��ü ���� ��ȯ
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
		
		//������ �� �˻�
		private static boolean isPrimitiveType(Class<?> dataType) {
			
			//�Ϲݵ����� Ÿ���� ���.
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
				
				
				//set �޼ҵ尡 �ƴҰ��� ����.
				if(!m.getName().startsWith("set"))continue;
				
				
				propName = m.getName().substring(3);
				
				
				if(propName.toLowerCase().equals(paramName.toLowerCase())){
					
					return m;
				}
			}
			
			return null;
		}
}
