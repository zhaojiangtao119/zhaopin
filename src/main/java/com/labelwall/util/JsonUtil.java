package com.labelwall.util;

import com.labelwall.mall.entity.User;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Json序列化工具类
 * @author Administrator
 *
 */

public class JsonUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	static {
		// 对象的所有字段全部列入
		objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
		// 取消默认的将date转换为timestamps
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 忽略将空Bean转Json的错误
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		// 同意时间格式
		objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

		// 忽略在json字符串中存在但是在对象属性中不存在的情况
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	/**
	 * 对象转json
	 * @param obj
	 * @return
	 */
	public static <T> String objToString(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 对象转为格式化的json
	 * @param obj
	 * @return
	 */
	public static <T> String objToStringPretty(T obj) {
		if (obj == null) {
			return null;
		}
		try {
			return obj instanceof String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T stringToObj(String str,Class<T> clazz){
		if(StringUtils.isEmpty(str) || clazz==null){
			return null;
		} 
		try {
			return clazz.equals(String.class) ? (T)str : objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T stringToObj(String str,TypeReference<T> typeReference){
		if(StringUtils.isEmpty(str) || typeReference==null){
			return null;
		}
		try {
			return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str, typeReference));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T stringToObj(String str,Class<?> collectionClass,Class<?>... elementClasses){
		JavaType javaType=objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
		try {
			return objectMapper.readValue(str, javaType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public  void test() {
		User user=new User();
		user.setId(1);
		user.setUsername("张三");
		User user2=new User();
		user2.setId(2);
		user2.setUsername("李四");
		
//		String userString=JsonUtil.objToString(user);
//		System.out.println(userString);
//		User StringUser=JsonUtil.stringToObj(userString, User.class);
//		System.out.println(StringUser.getUsername()+"***"+StringUser.getId());
		
		List<User> list=new ArrayList<>();
		list.add(user);
		list.add(user2);
		String listString= JsonUtil.objToStringPretty(list);
//		System.out.println(listString);
//		List<User> result=JsonUtil.stringToObj(listString, new TypeReference<List<User>>() {});
//		System.out.println(result);
		List<User> result= JsonUtil.stringToObj(listString, List.class, User.class);
		System.out.println(result);
	}
}
