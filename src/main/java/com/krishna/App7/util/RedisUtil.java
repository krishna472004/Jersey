package com.krishna.App7.util;

import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisUtil 
{
	private static Jedis redis;
	
	static
	{
		redis=new Jedis("localhost",6379);
	}
	
	
	public static void hset(String key,Map<String,String> map)
	{
		redis.hset(key,map);
	}
	
	public static Map<String,String> hgetAll(String key)
	{
		return redis.hgetAll(key);
	}
	
	public static boolean exists(String key)
	{
		return redis.exists(key);
	}
	
	public static void del(String key)
	{
		redis.del(key);
	}
	
	public static void setExpiry(String key,int val)
	{
		redis.expire(key,val);
	}
	
	public static void set(String key,String json)
	{
		redis.set(key,json);
	}
	
	public static String get(String key)
	{
		return redis.get(key);
	}
	
}
