package com.test.demo.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;


/**
 * 实体类相关工具类 
 * 快速对实体的常驻字段，如：createUser、createHost、updateUser等值快速注入
 */
public class EntityUtils {
	/**
	 * 快速将bean的createUser、createHost、createTime、updateUser、updateHost、updateTime附上相关值
	 */
	public static <T> void setCreatAndUpdatInfo(T entity) {
//		setCreateInfo(entity);
//		setUpdatedInfo(entity);
	}
	
	/**
	 * 快速将bean的createUser、createHost、createTime附上相关值
	 */
	public static <T> void setCreateInfo(T entity){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String hostIp = "";
		String name = "";
		String id = "";
		if(request!=null) {
			hostIp = String.valueOf(request.getHeader("userHost"));
			name = String.valueOf(request.getHeader("userName"));
			name = URLDecoder.decode(name);
			id = String.valueOf(request.getHeader("userId"));
		}
		// 默认属性
		String[] fields = {"createName","createUser","createHost","createTime"};
		Field field = ReflectionUtils.getAccessibleField(entity, "createTime");
		// 默认值
		Object [] value = null;
		if(field!=null&&field.getType().equals(Date.class)){
			value = new Object []{name,id,hostIp,new Date()};
		}
		// 填充默认属性值
		setDefaultValues(entity, fields, value);
	}

	/**
	 * 快速将bean的updateUser、updateHost、updateTime附上相关值
	 */
	public static <T> void setUpdatedInfo(T entity){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String hostIp = "";
		String name = "";
		String id = "";
		if(request!=null) {
			hostIp = String.valueOf(request.getHeader("userHost"));
			name = String.valueOf(request.getHeader("userName"));
			name = URLDecoder.decode(name);
			id = String.valueOf(request.getHeader("userId"));
		}
		// 默认属性
		String[] fields = {"updateName","updateUser","updateHost","updateTime"};
		Field field = ReflectionUtils.getAccessibleField(entity, "updateTime");
		Object [] value = null;
		if(field!=null&&field.getType().equals(Date.class)){
			value = new Object []{name,id,hostIp,new Date()};
		}
		// 填充默认属性值
		setDefaultValues(entity, fields, value);
	}
	/**
	 * 依据对象的属性数组和值数组对对象的属性进行赋值
	 * @param entity 对象
	 * @param fields 属性数组
	 * @param value 值数组
	 */
	private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
		for(int i=0;i<fields.length;i++){
			String field = fields[i];
			if(ReflectionUtils.hasField(entity, field)){
				ReflectionUtils.invokeSetter(entity, field, value[i]);
			}
		}
	}
	/**
	 * 根据主键属性，判断主键是否值为空
	 * @param entity
	 * @param field
	 * @return 主键为空，则返回false；主键有值，返回true
	 */
	public static <T> boolean isPKNotNull(T entity,String field){
		if(!ReflectionUtils.hasField(entity, field)) {
			return false;
		}
		Object value = ReflectionUtils.getFieldValue(entity, field);
		return value!=null&&!"".equals(value);
	}
}
