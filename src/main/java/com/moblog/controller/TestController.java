package com.moblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/home")
    private String home(){
        return "home";
    }

}
