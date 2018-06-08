package org.sc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.sc.client.SecurityConfigVo;
import org.sc.service.RedisService;

@Service  
public class RedisServiceImpl implements RedisService{  
	private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired 
    private RedisTemplate<String, ?> redisTemplate;  
      
    @Override  
    public boolean set(final String key, final Object value) {  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            @Override  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                connection.set(serializer.serialize(key), serializer.serialize(JSON.toJSONString(value,SerializerFeature.WriteMapNullValue)));  
                return true;  
            }  
        });  
        logger.debug("===========redis-set");
        return result;
    }  
  
    public String get(final String key){  
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] value =  connection.get(key.getBytes());  
                return serializer.deserialize(value);  
            }  
        });  
        logger.debug("===========redis-get");
        return result;  
    }  
  
    @Override  
    public boolean expire(final String key, long expire) {  
    	logger.debug("===========redis-expire");
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);  
    }  
  
    @Override  
    public <T> boolean setList(String key, List<T> list) {  
        String value = JSON.toJSONString(list);  
        return set(key,value);  
    }  
  
    @Override  
    public  List getList(String key,Class clz) {  
        String json = get(key);  
        if(json!=null){  
        	json = json.substring(0, json.length() -1);
        	json = json.substring(1, json.length());
        	json = json.replaceAll("\\\\", "");
            List list = JSON.parseArray(json, clz);  
            return list;  
        }  
        return null;  
    }  
  
    @Override  
    public long lpush(final String key, Object obj) {  
        final String value = JSON.toJSONString(obj);  
        long result = redisTemplate.execute(new RedisCallback<Long>() {  
            @Override  
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
  
    @Override  
    public long rpush(final String key, Object obj) {  
        final String value = JSON.toJSONString(obj);  
        long result = redisTemplate.execute(new RedisCallback<Long>() {  
            @Override  
            public Long doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));  
                return count;  
            }  
        });  
        return result;  
    }  
  
    @Override  
    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {  
            @Override  
            public String doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] res =  connection.lPop(serializer.serialize(key));  
                return serializer.deserialize(res);  
            }  
        });  
        return result;
    }

	@Override
	public boolean checkKey(String key) {
		logger.debug("===========redis-checkKey");
		return redisTemplate.hasKey(key);
	}

	@Override
	public <T> T getMyObject(String key, Class<T> clzs) {
        String result =  get(key);
        if(StringUtils.isEmpty(result)){
        	try {
				return clzs.newInstance();
			} catch (Exception e) {
				return null;
			}
        }else{
        	return (T) JSON.parseObject(result,clzs);  
        }
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public Boolean setHash(String rediskey, String key, Object value) {
		String result=JSON.toJSONString(value); 
		if(hasHashKey(rediskey, key)){
			deleteHash(rediskey, key);
		}
		return 	redisTemplate.opsForHash().putIfAbsent(rediskey,key.trim(),result);
	}

	@Override
	public Long deleteHash(String rediskey, String key) {
		return redisTemplate.opsForHash().delete(rediskey, key.trim());
	}

	@Override
	public Boolean hasHashKey(String rediskey, String key) {
		return redisTemplate.opsForHash().hasKey(rediskey, key.trim());
	}

	@Override
	public Set<Object> getHashKeyList(String rediskey) {
		return redisTemplate.opsForHash().keys(rediskey);
	}

	@Override
	public <T> T getHashVal(String rediskey, String key, Class<T> clzs) {
		if(StringUtils.isEmpty(key)){
			return null;
		}
		Object object = redisTemplate.opsForHash().get(rediskey, key.trim());
		String result = String.valueOf(object);
		return JSON.parseObject(JSON.toJSONString(result), clzs);
		//return DataUtil.getObjectByJsonStr(result, clzs);
	}

	@Override
	public <T> List<T> getHashValList(String rediskey, Class<T> clzs) {
		List<Object> tempList=redisTemplate.opsForHash().values(rediskey);
		List<T>  resList = new ArrayList<T>();
		for (Object obj : tempList) {
			T t = JSON.parseObject(JSON.toJSONString(obj), clzs);
			//T t =DataUtil.getObjectByJsonStr(String.valueOf(obj), clzs);
			resList.add(t);
		}
		return resList;
	}
	
}  