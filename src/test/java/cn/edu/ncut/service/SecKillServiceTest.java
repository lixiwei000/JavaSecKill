package cn.edu.ncut.service;

import cn.edu.ncut.dto.Exposer;
import cn.edu.ncut.dto.SecKillExecution;
import cn.edu.ncut.exception.RepeatKillException;
import cn.edu.ncut.exception.SecKillCloseException;
import cn.edu.ncut.model.SecKillObj;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by NikoBelic on 16/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml","classpath:spring/spring-dao.xml"})
public class SecKillServiceTest
{
    /**
     * 当前采用sl4j,但只是一个接口,他的视线是logback
     * 打开LogBack官网
     * 可以定义输出到文件.规范.远程数据库.远程MQ
     * 先使用最简单的配置
     * 新建在logback配置文件,默认在classpath下,加载固定名称的logback.xml配置
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillService secKillService;



    @Test
    public void testGetSecKillList() throws Exception
    {
        List<SecKillObj> list = secKillService.getSecKillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1000;
        SecKillObj secKill = secKillService.getById(id);
        logger.info("SecKillObj={}",secKill);
    }

    /**
     * 测试代码完整逻辑
     * 注意可重复执行
     * @throws Exception
     */
    @Test
    public void testSecKilLogic() throws Exception
    {
        long id = 1002;
        Exposer exposer = secKillService.exportSecKillUrl(id);

        if (exposer.isExposed())
        {
            logger.info("exposer={}",exposer);
            long phone = 13522782223L;
            String md5 = exposer.getMd5();
            try
            {
                SecKillExecution execution = secKillService.executeSecKill(id, phone, md5);
                logger.info("execution={}",execution);
            }catch (RepeatKillException e)  // 当出现业务允许的异常,认为JunitTest通过
            {
                logger.error(e.getMessage());
            }catch (SecKillCloseException e)
            {
                logger.error(e.getMessage());
            }
        }
        else
        {
            // 秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void testExecuteSecKill() throws Exception
    {
        long id = 1002;
        long phone = 13522782223L;
        String md5 = "be59647414d11ddca6ae907254daccc8";
        // cn.edu.ncut.exception.SecKillException: SecKill Data Rewrite

    }
    @Test
    public void testExecuteSecKillProcedure() throws Exception
    {
        long seckillId = 1003;
        long userPhone = 1352278197;
        Exposer exposer = secKillService.exportSecKillUrl(seckillId);
        if (exposer.isExposed())
        {
            String md5 = exposer.getMd5();
            SecKillExecution execution = secKillService.executeSecKillProcedure(seckillId, userPhone, md5);
            logger.info(execution.getStateInfo());
        }
    }
}