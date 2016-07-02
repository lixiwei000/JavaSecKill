package cn.edu.ncut.dao.cache;

import cn.edu.ncut.dao.SecKillDao;
import cn.edu.ncut.model.SecKillObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by NikoBelic on 16/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class RedisDaoTest
{
    @Autowired
    RedisDao redisDao;
    @Autowired
    SecKillDao secKillDao;
    private long seckillId = 1003;
    @Test
    public void testSecKill() throws Exception
    {
        SecKillObj secKillObj = redisDao.getSecKill(seckillId);
        System.out.println("从Redis读取" + secKillObj);
        if (secKillObj == null)
        {
            secKillObj = secKillDao.queryById(seckillId);
            System.out.println("从Mysql查询:" + secKillObj);
            String result = redisDao.putSecKill(secKillObj);
            System.out.println("result:" + result);
            SecKillObj newSecKillObj = redisDao.getSecKill(seckillId);
            System.out.println("从Redis再次读取:" + newSecKillObj);
        }
    }
}