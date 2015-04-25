package com.component.redis.list;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import com.component.redis.JedisConfig;
import com.component.redis.JedisPoolManager;

public class JedisListTest {

	private static String ip = "127.0.0.1";
	
	private static int port = 6379;
	
	private static String key = "test";
	
	private static AtomicLong value = new AtomicLong(0);
	
	public static void main(String[] args) {

		JedisConfig jc = new JedisConfig();
		jc.setIp(ip);
		jc.setPort(port);
		jc.setMaxIdel(2);
		jc.setMinIdel(2);
		jc.setMaxTotal(20);
		jc.setTimeout(1000);
		
		List<JedisConfig> list = new ArrayList<JedisConfig>();
		list.add(jc);
		JedisPoolManager.getInstance().initJedisPools(list);
		
		ExecutorService pushPool = Executors.newFixedThreadPool(5, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "Push Thread " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		});
		
		for(int i = 0 ; i < 6; i++){
			pushPool.execute(new Runnable() {				
				@Override
				public void run() {
					while(true){
						System.out.println(Thread.currentThread().getName() + " push value: " 
								+ value.addAndGet(1));
					JedisListUtils.leftPull(ip, port, key, String.valueOf(value.get()));
						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		
		ExecutorService popPool = Executors.newFixedThreadPool(5, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "Pop Thread " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			}
		});
		
		for(int i = 0 ; i< 5; i++){
			popPool.execute(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						System.out.println(Thread.currentThread().getName() 
								+ " pop value: " + JedisListUtils.blockRightPop(ip, port, key));
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		
	}
	
	
	class LeftPuller implements Runnable{
		
		@Override
		public void run() {
			
		}
	}
	
	class RightPoper implements Runnable{
		
		@Override
		public void run() {
			while(true){
				JedisListUtils.rightPop(ip, port, key);
			}
		}
	}
	
}
