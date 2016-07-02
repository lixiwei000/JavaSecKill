package cn.edu.ncut.dao.cache;

import cn.edu.ncut.model.SecKillObj;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author NikoBelic
 * @create 16/7/1 15:26
 */
public class RedisDao
{
    private final JedisPool jedisPool;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RuntimeSchema<SecKillObj> schema = RuntimeSchema.createFrom(SecKillObj.class);

    public RedisDao(String ip,int port) {
        jedisPool = new JedisPool(ip,port);
    }

    public SecKillObj getSecKill(long seckillId)
    {
        // Redis逻辑操作
        try
        {
            Jedis jedis = jedisPool.getResource();
            try
            {
                // Redis并没有实现内部序列化
                // get -> byte[] -> 反序列化 -> Object(SecKillObj)
                // 采用自定义序列化   可以使用implements Serialize 或 开源社区更加高效的序列化方法 jvm-serializers (github)
                String key = "seckillId:" + seckillId;
                // 从缓存中获取数据
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null)
                {
                    // 空对象
                    SecKillObj secKillObj = schema.newMessage();
                    // seckillObj反序列化
                    // 与Jdk自带的序列化相比,空间减少90%,速度提高两个数量级
                    ProtostuffIOUtil.mergeFrom(bytes,secKillObj,schema);
                    return secKillObj;
                }
            }
            finally
            {
                jedis.close();
            }
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    public String putSecKill(SecKillObj secKillObj)
    {
        // set Object(SecKillObj) -> 序列化 -> byte[]
        try
        {
            Jedis jedis = jedisPool.getResource();
            String key = "seckillId:" + secKillObj.getSeckillId();
            try
            {
                byte[] bytes = ProtostuffIOUtil.toByteArray(secKillObj, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时时间
                int timeout = 60 * 60; // 一小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            }
            finally
            {
                jedis.close();
            }
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
