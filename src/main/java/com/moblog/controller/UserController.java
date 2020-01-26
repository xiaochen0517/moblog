package com.moblog.controller;

import com.moblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findall")
    @ResponseBody
    private String findAll(){
        String jsonstr = userService.findAll();
        System.out.println(jsonstr);
        return jsonstr;
    }

}
