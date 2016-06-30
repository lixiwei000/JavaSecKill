package cn.edu.ncut.controller;

import cn.edu.ncut.dto.Exposer;
import cn.edu.ncut.dto.SecKillExecution;
import cn.edu.ncut.dto.SecKillResult;
import cn.edu.ncut.enums.SecKillStateEnum;
import cn.edu.ncut.exception.RepeatKillException;
import cn.edu.ncut.exception.SecKillCloseException;
import cn.edu.ncut.model.SecKillObj;
import cn.edu.ncut.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author NikoBelic
 * @create 16/6/28 12:06
 */
@Controller
@RequestMapping("/seckill") // url: /模块/资源/{id}/细分
public class SecKillController
{
    @Autowired
    SecKillService secKillService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 显示商品列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model)
    {
        List<SecKillObj> list = secKillService.getSecKillList();
        model.addAttribute("list",list);
        // list.jsp + model = ModelAndView
        return "list";//     /WEB-INF/jsp/list.jsp
    }

    /**
     * 显示商品详情
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model)
    {
        if (seckillId == null)
            return "redirect:/seckill/list";
        SecKillObj secKillObj = secKillService.getById(seckillId);
        if (secKillObj == null)
            return "forward:/seckill/list";
        model.addAttribute("secKillObj",secKillObj);
        return "detail";
    }

    /**
     * Ajax请求得到秒杀地址
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId)
    {
        SecKillResult<Exposer> result;
        try
        {
            Exposer exposer = secKillService.exportSecKillUrl(seckillId);
            result = new SecKillResult<Exposer>(true,exposer);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
            result = new SecKillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    /**
     * 执行秒杀
     * @param seckillId
     * @param md5
     * @param phone
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<SecKillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone",required = false) Long phone)
    {
        SecKillResult<SecKillExecution> result;
        // 也可以使用SpringMVC valid验证
        if (phone == null)
        {
            return new SecKillResult<SecKillExecution>(false,"未注册");
        }
        try
        {
            SecKillExecution execution = secKillService.executeSecKill(seckillId,phone,md5);
            return new SecKillResult<SecKillExecution>(true,execution);
        }
        catch (RepeatKillException e)
        {
            SecKillExecution execution = new SecKillExecution(seckillId, SecKillStateEnum.REPEAT_KILL);
            return new SecKillResult<SecKillExecution>(true,execution);
        }
        catch (SecKillCloseException e)
        {
            SecKillExecution execution = new SecKillExecution(seckillId, SecKillStateEnum.END);
            return new SecKillResult<SecKillExecution>(true,execution);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
            SecKillExecution execution = new SecKillExecution(seckillId, SecKillStateEnum.INNER_ERROR);
            return new SecKillResult<SecKillExecution>(true,execution);
        }
    }
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SecKillResult<Long> time()
    {
        Date now = new Date();
        return new SecKillResult(true,now.getTime());
    }
}
