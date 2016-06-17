package cn.edu.ncut.dao;

import cn.edu.ncut.model.SuccessKilledObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by NikoBelic on 16/6/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest
{

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long id = 1000L;
        long phone = 13522781970L;
        int insertCount = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void testQueryByIdWithSecKill() throws Exception {
        long id = 1000L;
        long phone = 13522781970L;
        SuccessKilledObj successKilledObj = successKilledDao.queryByIdWithSecKill(id, phone);
        System.out.println(successKilledObj);
        System.out.println(successKilledObj.getSeckillObj());
    }
}