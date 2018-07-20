package com.hnair.wallet.admincenter.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIANYINGDA at 7/20/2018 10:37 PM
 */
@Controller
public class WalletErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping("/error")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        switch (status) {
            case 403:
                return "403";
            case 404:
                return "error/404";
            case 500:
                return "500";
        }

        return "index";
    }
}