package com.labelwall.util;

import com.labelwall.common.RedisPool;
import redis.clients.jedis.Jedis;

public class RedisPoolUtil {
	/**
	 * 存储key
	 * @param key
	 * @param value
	 * @return
	 */
	public static String set(String key,String value){
		Jedis jedis= null;
		String result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	/**
	 * 获取value
	 * @param key
	 * @return
	 */
	public static String get(String key){
		Jedis jedis= null;
		String result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	/**
	 * 存储key，并设置有效期
	 * @param key
	 * @param value
	 * @param exTime
	 * @return
	 */
	public static String setEx(String key,String value,int exTime){
		Jedis jedis= null;
		String result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.setex(key, exTime, value);
		} catch (Exception e) {
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	/**
	 * 设置key的有效期
	 * @param key
	 * @param exTime
	 * @return
	 */
	public static Long expire(String key,int exTime){
		Jedis jedis= null;
		Long result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.expire(key, exTime);
		} catch (Exception e) {
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	public static Long del(String key){
		Jedis jedis= null;
		Long result = null;
		
		try {
			jedis = RedisPool.getJedis();
			result = jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			RedisPool.returnBrokenResource(jedis);
			return result;
		}
		RedisPool.returnResource(jedis);
		return result;
	}
}
