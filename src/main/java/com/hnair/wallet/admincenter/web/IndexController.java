package com.hnair.wallet.admincenter.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 7/20/2018 5:09 PM
 */
@Controller
@Slf4j
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/bgMain")
    public String toBackgroundMain(){
        return "welcome";
    }
}
