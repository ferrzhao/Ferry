package com.component.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolManager {
	
	private static Map<String, JedisPool> jedisPoolMap = new ConcurrentHashMap<String, JedisPool>();
	
	private static final JedisPoolManager instance = new JedisPoolManager();
	
	private JedisPoolManager(){};
	
	public static JedisPoolManager getInstance(){
		
		return instance;
	}
	
	/**
	 * 初始化链接池，支持多个redis的初始化
	 * @param configs
	 */
	public void initJedisPools(List<JedisConfig> configs){
		
		if(configs == null){
			throw new IllegalArgumentException("jedis pool configs is null");
		}
		
		for(JedisConfig conf : configs){
			
			String key = conf.getIp() + conf.getPort();
			
			JedisPoolConfig jpc = new JedisPoolConfig();
			jpc.setMaxTotal(conf.getMaxTotal());
			jpc.setMaxIdle(conf.getMaxIdel());
			jpc.setMinIdle(conf.getMinIdel());
			jpc.setMaxWaitMillis(conf.getTimeout());
			jpc.setTestWhileIdle(true);
			
			JedisPool pool = new JedisPool(jpc, conf.getIp(), conf.getPort());
			pool.returnResource(pool.getResource());
			
			jedisPoolMap.put(key, pool);
		}
	}
	
	/**
	 * 获取当前缓存的连接池的大小
	 * @return
	 */
	public int size(){
		
		return jedisPoolMap.size();
	}
	
	/**
	 * 根据ip与端口号获取对应redis的连接池
	 * @param ip
	 * @param port
	 * @return
	 */
	public JedisPool getJedisPool(String ip, int port){
		
		return jedisPoolMap.get(ip + port);
	}
	
	/**
	 * 关闭连接池
	 */
	public void clear(){
		
		for(String key : jedisPoolMap.keySet()){
			jedisPoolMap.remove(key).destroy();;
		}
	}
		
}
