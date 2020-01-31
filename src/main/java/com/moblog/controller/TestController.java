package com.moblog.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/login")
    @ResponseBody
    private String login(@RequestBody String str, String username, String password){
        System.out.println("username-->"+username);
        System.out.println("password-->"+password);
        System.out.println("body-->"+str);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("resultCode", "123");
        resultMap.put("name", username);
        resultMap.put("age", "32");
        return JSONObject.toJSONString(resultMap);
    }

}
