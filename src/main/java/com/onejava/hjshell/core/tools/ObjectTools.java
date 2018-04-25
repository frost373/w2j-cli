package com.onejava.hjshell.core.tools;

import com.onejava.hjshell.util.ArrayUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ObjectTools {	
	
	/**
	 * 根据class的路径生成class
	 * @param classPath
	 * @return
	 */
	public static Class<?> getTheClass(String classPath) throws ClassNotFoundException{
		Class<?> actionClass = null;
		actionClass=Class.forName(classPath);
		return actionClass;
	}
	
	/**
	 * 把Class实例化为Object对象
	 * @param theClass
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object getTheObject(Class<?> theClass) throws InstantiationException, IllegalAccessException{
		Object obj = null;
		obj = theClass.newInstance();	
		return 	obj;
	}
	
	
	/**
	 * 根据Class的路径，实例化一个Object对象
	 * @param classPath
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object getTheObject(String classPath) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return getTheObject(getTheClass(classPath));	
	}
	
	/**
	 * 获得方法Method
	 * @param obj
	 * @param methodName
	 * @param paramTypes
	 * @return Method
	 * @throws Exception 
	 */
	public static Method getMethod(Object obj,String methodName,Class<?>[] paramTypes) throws Exception{
		Method method =null;
		method = obj.getClass().getMethod(methodName, paramTypes);
		return method;
	}
	
	/**
	 * 获得方法Method (单参数)
	 * @param obj
	 * @param methodName
	 * @param paramType
	 * @return
	 * @throws Exception 
	 */
	public static Method getMethod(Object obj,String methodName,Class<?> paramType) throws Exception{
		Class<?>[]  paramTypes = new Class<?>[1];
		paramTypes[0] = paramType;
		return getMethod(obj,methodName,paramTypes);
	}
	
	/**
	 * 执行Object的方法(单参数)
	 * @param obj
	 * @param method
	 * @param param
	 * @throws Exception
	 */
	public static Object invokeObject(Object obj,Method method ,Object param ) throws Exception{
	    return	method.invoke(obj, param);
	}
	
	/**
	 * 执行Object的方法
	 * @param obj
	 * @param methodNames
	 * @param methods
	 * @throws Exception
	 */
	public static void invokeObject(Object obj,String methodName,Class<?>[] paramTypes,Object[] params ) throws Exception{
		//获取method
		Method method =getMethod(obj, methodName, paramTypes);	
		//执行方法
		method.invoke(obj, params);	
	}
	
	/**
	 * 执行Object的方法(单参数)
	 * @param obj
	 * @param methodName
	 * @param paramType
	 * @param param
	 * @throws Exception 
	 */
	public static void invokeMObject(Object obj,String methodName,Class<?> paramType,Object param) throws Exception{
		Class<?>[]  paramTypes = new Class<?>[1];
		paramTypes[0] = paramType;
		Object[] params = new Object[1];
		params[0] = param;
		invokeObject(obj, methodName, paramTypes, params);
	}

	/**
	 * 获得一个类中所有方法列表，直接反射获取，无缓存
	 *
	 * @param beanClass 类
	 * @param withSuperClassMethods 是否包括父类的方法列表
	 * @return 方法列表
	 * @throws SecurityException 安全检查异常
	 */
	public static Method[] getMethodsDirectly(Class<?> beanClass, boolean withSuperClassMethods) throws SecurityException {

		Method[] allMethods = null;
		Class<?> searchType = beanClass;
		Method[] declaredMethods;
		while (searchType != null) {
			declaredMethods = searchType.getDeclaredMethods();
			if (null == allMethods) {
				allMethods = declaredMethods;
			} else {
				allMethods = ArrayUtil.append(allMethods, declaredMethods);
			}
			searchType = withSuperClassMethods ? searchType.getSuperclass() : null;
		}

		return allMethods;
	}


	public static void getParam(Method method){
		Annotation[][] arrys3 = method.getParameterAnnotations();

	}
}
