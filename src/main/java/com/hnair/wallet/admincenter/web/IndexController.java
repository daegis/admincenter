package com.hnair.wallet.admincenter.web;

import com.hnair.wallet.admincenter.vo.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 7/20/2018 5:09 PM
 */
@Controller
@Slf4j
public class IndexController {

    @Value("${currentEnv}")
    private String environment;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/bgMain")
    public String toBackgroundMain(Model model) {
        TestVo vo = new TestVo();
        vo.setAge(30);
        vo.setName("lxy");
        List<String> list = new LinkedList<>();
        list.add("a");
        list.add("bbbb");
        list.add("ccccccc");
        vo.setJobs(list);
        model.addAttribute("vo", vo);
        return "welcome";
    }
}
