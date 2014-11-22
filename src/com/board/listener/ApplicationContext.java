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
	
	//프론트 컨트롤러에서 사용될 객체
	public Object getBean(String key){
		
		return objTable.get(key);
		
	}
	public void addBean(String key, Object object){
		objTable.put(key, object);
	}

	
	//리플렉션 api 를 이용 Component.java 에 명시된 Component 에노테이션 객체인터 페이스를 구현한 class 객체를 구현한 
	//타입들을 가져온다.
	public void prepareAnnotationObject(String basePackage) throws Exception {
		
		//basePackage 패키지 하위에서 에노테이션 컴포넌트를 검색한다.  "" = 모든 경로
		Reflections reflections = new Reflections(basePackage);
		Set<Class<?>> types = reflections.getTypesAnnotatedWith(Component.class);
		
		String key = null;
		
		for(Class<?> clazz : types){
			
			//에노테이션 내 정의한 value 값을 가져와서 맵에 저장
			key = clazz.getAnnotation(Component.class).value();
			//현재 해당 객체 생성 저장
			objTable.put(key, clazz.newInstance());
		}
		
	}

	//프로퍼티 객체(map) 으로 부터 객체를 생성 하여 objTable 에 저장
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
				
				//lookup() 는 톰캣 서버객체로부터 value 에 해당 하는 객체를 찾아 인스턴스화 하여 반환한다.
				objTable.put(key, context.lookup(value));
				
			}else{
				objTable.put(key, Class.forName(value).newInstance());
				//value.getClass().newInstance();
			}
		}
	}
	//객체 주입이 필요한 경우 callSetter 를 호출 한다.
	public void dependencyInjection() throws Exception {
		//jndi 객체는 setter 메소드의 파라미터를 주입시킬 필요가 없기때문에 패스.
		for(String  key : objTable.keySet()){
			if(!key.startsWith("jndi.")){
				callSetter(objTable.get(key));
			}
		}
	}
	
	private void callSetter(Object object) throws Exception {
		
		//setter 메소드가 있는 클래스 객체를 담기 위한 변수
		System.out.println("objectTable 저장된 객체 : " + object.getClass().getName());
		//클래스 객체에 존재하는 파라미터 타입을 찾아 담을 변수
		Object dependencyObject = null;
		
		for(Method  m : object.getClass().getMethods()){
			
			if(m.getName().startsWith("set")){
				
				System.out.println("의존객체가 필요한 메서드 : " + m.getName());
				dependencyObject = objectByType(m.getParameterTypes()[0]);
				
				
				if(dependencyObject != null ){
					
					System.out.println("검색된 의존 객체 : "+ dependencyObject.getClass().getName());
					//setter 메소드 호출 의존객체를 주입한다
					m.invoke(object, dependencyObject);
					System.out.println("주입완료");
				}
				
			}
			
		}
		System.out.println("=======================================================================");
		
	}
	
	//setter 메소드 에 파라미터 타입을 ojTable 로부터 검색해서 반환한다.
	private Object objectByType(Class<?> type) {
		
		for(Object object : objTable.values()){
			
			if(type.isInstance(object)){
				
				return object;
			}
		}
		
		return null;
	}
	
	
}
