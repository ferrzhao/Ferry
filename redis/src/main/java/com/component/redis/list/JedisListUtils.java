package com.component.redis.list;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.component.redis.JedisUtil;

public class JedisListUtils {

	
	/**
	 * 在list左侧加入元素
	 * @param ip
	 * @param port
	 * @param key
	 * @param value
	 * @return long
	 */
	public static long leftPull(String ip, int port, String key, String value){
		
		Jedis jedis = JedisUtil.getJedis(ip, port);
		try{
			System.out.println(value);
			return jedis.lpush(key, value);
		}finally{
			JedisUtil.close(jedis);
		}
	}
	
	/**
	 * 在list左侧移除元素
	 * @param ip
	 * @param port
	 * @param key
	 * @return
	 */
	public static String leftPop(String ip, int port, String key){
		
		Jedis jedis = JedisUtil.getJedis(ip, port);
		try{
			return jedis.lpop(key);
		} finally{
			JedisUtil.close(jedis);
		}
	}
	
	/**
	 * 在list右侧加入元素
	 * @param ip
	 * @param port
	 * @param key
	 * @param value
	 * @return long
	 */
	public static long rightPull(String ip, int port, String key, String value){
		
		Jedis jedis = JedisUtil.getJedis(ip, port);
		try{
			return jedis.rpush(key, value);
		}finally{
			JedisUtil.close(jedis);
		}
	}
	
	/**
	 * 在list右侧移除元素
	 * @param ip
	 * @param port
	 * @param key
	 * @return
	 */
	public static String rightPop(String ip, int port, String key){
		
		Jedis jedis = JedisUtil.getJedis(ip, port);
		try{
			return jedis.rpop(key);
		} finally{
			JedisUtil.close(jedis);
		}
	}
	
	/**
	 * 返回list的大小
	 * @param ip
	 * @param port
	 * @param key
	 * @return
	 */
	public static long getSize(String ip, int port , String key){
		
		Jedis jedis = JedisUtil.getJedis(ip, port);
		try{
			return jedis.llen(key);
		} finally{
			JedisUtil.close(jedis);
		}
	}
	
	/**
	 * 左侧阻塞删除元素， 直接另外客户端加入元素到list时解除阻塞
	 * @param ip
	 * @param port
	 * @param key
	 * @return
	 */
	public static String blockLeftPop(String ip, int port, String key){
		
		Jedis jedis = JedisUtil.getJedis(ip, port);
		try{
			 List<String> result = jedis.blpop(5, key);
			 if(result != null){
				 return result.get(1);
			 }
			 return null;
		} finally{
			JedisUtil.close(jedis);
		}
	}
	
	/**
	 * 右侧阻塞删除元素， 直接另外客户端加入元素到list时解除阻塞
	 * @param ip
	 * @param port
	 * @param key
	 * @return
	 */
	public static String blockRightPop(String ip, int port, String key){
		
		Jedis jedis = JedisUtil.getJedis(ip, port);
		try{
			 List<String> result = jedis.brpop(5, key);
			 System.out.println(result);
			 if(result != null){
				 return result.get(1);
			 }
			 return null;
		} finally{
			JedisUtil.close(jedis);
		}
	}
}
