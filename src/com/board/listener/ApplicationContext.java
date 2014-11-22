package com.board.listener;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import com.board.annotation.Component;

public class ApplicationContext {

	Hashtable<String,Object> objTable = new Hashtable<String,Object>();
	
	//����Ʈ ��Ʈ�ѷ����� ���� ��ü
	public Object getBean(String key){
		
		return objTable.get(key);
		
	}
	public void addBean(String key, Object object){
		objTable.put(key, object);
	}

	
	//���÷��� api �� �̿� Component.java �� ��õ� Component �������̼� ��ü���� ���̽��� ������ class ��ü�� ������ 
	//Ÿ�Ե��� �����´�.
	public void prepareAnnotationObject(String basePackage) throws Exception {
		
		//basePackage ��Ű�� �������� �������̼� ������Ʈ�� �˻��Ѵ�.  "" = ��� ���
		Reflections reflections = new Reflections(basePackage);
		Set<Class<?>> types = reflections.getTypesAnnotatedWith(Component.class);
		
		String key = null;
		
		for(Class<?> clazz : types){
			
			//�������̼� �� ������ value ���� �����ͼ� �ʿ� ����
			key = clazz.getAnnotation(Component.class).value();
			//���� �ش� ��ü ���� ����
			objTable.put(key, clazz.newInstance());
		}
		
	}

	//������Ƽ ��ü(map) ���� ���� ��ü�� ���� �Ͽ� objTable �� ����
	public void preparePropertieObject(String propertiesPath) throws Exception{
		
		
		Context context = new InitialContext();
		Properties prop = new Properties();
		prop.load(new FileReader(propertiesPath));
				
		String key = null;
		String value = null;
		
		
		for(Object item : prop.keySet()){
			
			key = (String)item;
			value = prop.getProperty(key);
			System.out.println(key+"="+value);
			
			if(key.startsWith("jndi.")){
				
				//lookup() �� ��Ĺ ������ü�κ��� value �� �ش� �ϴ� ��ü�� ã�� �ν��Ͻ�ȭ �Ͽ� ��ȯ�Ѵ�.
				objTable.put(key, context.lookup(value));
				
			}else{
				objTable.put(key, Class.forName(value).newInstance());
				//value.getClass().newInstance();
			}
		}
	}
	//��ü ������ �ʿ��� ��� callSetter �� ȣ�� �Ѵ�.
	public void dependencyInjection() throws Exception {
		//jndi ��ü�� setter �޼ҵ��� �Ķ���͸� ���Խ�ų �ʿ䰡 ���⶧���� �н�.
		for(String  key : objTable.keySet()){
			if(!key.startsWith("jndi.")){
				callSetter(objTable.get(key));
			}
		}
	}
	
	private void callSetter(Object object) throws Exception {
		
		//setter �޼ҵ尡 �ִ� Ŭ���� ��ü�� ��� ���� ����
		System.out.println("objectTable ����� ��ü : " + object.getClass().getName());
		//Ŭ���� ��ü�� �����ϴ� �Ķ���� Ÿ���� ã�� ���� ����
		Object dependencyObject = null;
		
		for(Method  m : object.getClass().getMethods()){
			
			if(m.getName().startsWith("set")){
				
				System.out.println("������ü�� �ʿ��� �޼��� : " + m.getName());
				dependencyObject = objectByType(m.getParameterTypes()[0]);
				
				
				if(dependencyObject != null ){
					
					System.out.println("�˻��� ���� ��ü : "+ dependencyObject.getClass().getName());
					//setter �޼ҵ� ȣ�� ������ü�� �����Ѵ�
					m.invoke(object, dependencyObject);
					System.out.println("���ԿϷ�");
				}
				
			}
			
		}
		System.out.println("=======================================================================");
		
	}
	
	//setter �޼ҵ� �� �Ķ���� Ÿ���� ojTable �κ��� �˻��ؼ� ��ȯ�Ѵ�.
	private Object objectByType(Class<?> type) {
		
		for(Object object : objTable.values()){
			
			if(type.isInstance(object)){
				
				return object;
			}
		}
		
		return null;
	}
	
	
}
