package com.labelwall.common;

import com.labelwall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	private static JedisPool pool;//jedis连接池
	
	private static String host= PropertiesUtil.getProperty("redis.host","20");
	private static int port=Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
	
	
	private static Integer maxTotal= Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));//最大连接数
	private static Integer maxIdle= Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));//最大空闲状态的实例个数
	private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));//最下空闲状态的实例个数
	private static Boolean testOnBorrow= Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//从连接池取jedis实例时，是否需要验证，如果为true，则保证该实例是可用的
	private static Boolean testOnReturn= Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));//往连接池还jedis实例时，是否需要验证，如果为true，则放回的实例肯定是可以用的
	
	
	private static void initPool(){
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		config.setBlockWhenExhausted(true);//连接耗尽时，是否阻塞
		pool =new JedisPool(config, host, port, 1000*2);
	}
	static {
		initPool();
	}
	
	public static Jedis getJedis(){
		return pool.getResource();
	}
	public static void returnResource(Jedis jedis){
		pool.returnResource(jedis);
	}
	public static void returnBrokenResource(Jedis jedis){
		pool.returnBrokenResource(jedis);
	}
	
	
	
}
