package cn.edu.ncut.dao;

import cn.edu.ncut.model.SecKillObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置Spring和Junit整合,Junit启动时加载SpringIOC容器
 * spring-test,junit
 * Created by NikoBelic on 16/6/13.
 */
@RunWith(SpringJUnit4ClassRunner.class) // Junit启动时加载SpringIOC容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})// 告诉Junit  Springh配置文件
public class SecKillDaoTest
{
    // 注入Dao实现类依赖
    @Resource
    private SecKillDao secKillDao;


    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        SecKillObj secKillObj = secKillDao.queryById(id);
        System.out.println(secKillObj.getName());
        System.out.println(secKillObj);
    }

    @Test
    public void testQueryAll() throws Exception {
        // Caused by: org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [1, 0, param1, param2]
        // Java没有保存形参的记录  在运行时参数queryAll(int offset,int limit) -> queryAll(arg0,arg1)
        // 所以说一个参数肯定没问题,但是多个参数需要手动设定.使用Mybatis提供的@Param告知java在运行期间参数的名称,在Dao中添加@Param.
        List<SecKillObj> secKillObjs = secKillDao.queryAll(0, 100);
        for (SecKillObj secKillObj : secKillObjs)
        {
            System.out.println(secKillObj);
        }
    }

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = secKillDao.reduceNumber(1000L,killTime);
        System.out.println("updateCount = " + updateCount);
    }


}