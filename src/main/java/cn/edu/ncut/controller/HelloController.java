package cn.edu.ncut.controller;

import cn.edu.ncut.dto.Exposer;
import cn.edu.ncut.model.SecKillObj;
import cn.edu.ncut.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author NikoBelic
 * @create 16/6/27 21:41
 */
@Controller
public class HelloController
{
    @Autowired
    private SecKillService secKillService;
    @RequestMapping(value="/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("detailId")Long seckillId,Model model)
    {
        if (seckillId == null)
        {
            return "redirect:/seckill/list";
        }
        SecKillObj secKill = secKillService.getById(seckillId);
        if (secKill == null)
            return "forward:/seckill/list";
        model.addAttribute("seckill",secKill);
        return "detail";
    }
    // 返回Json
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})  // 通知浏览器这是一个json
    @ResponseBody //返回json数据
    public List<Exposer> exportSeckillURL(@PathVariable("id")long id)
    {
        List<Exposer> result = null;
        ///......
        return result;
    }

}
