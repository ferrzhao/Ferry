package com.component.redis;

import redis.clients.jedis.Jedis;

public class JedisUtil {
	
	/**
	 * 获取链接池
	 * @param ip
	 * @param port
	 * @return
	 */
	public static Jedis getJedis(String ip, int port){
		
		return JedisPoolManager.getInstance().getJedisPool(ip, port).getResource();
	}
	
	/**
	 * 将jedis对象返回给链接池
	 * @param jedis
	 */
	public static void close(Jedis jedis){
		
		if(jedis != null){
			jedis.close();
		}
	}
}
