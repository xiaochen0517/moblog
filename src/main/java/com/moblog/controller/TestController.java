package com.moblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.moblog.util.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTML;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 * 测试 controller
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static final String TAG = "测试接口Controller";

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

    @RequestMapping("/test1")
    @ResponseBody
    private String test1(HttpServletRequest request){
        HttpSession session = request.getSession();
        String id = session.getId();
        Log.d(TAG, "sessionid -- >"+id);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("resultCode", "123");
        resultMap.put("name", "username");
        resultMap.put("age", "32");
        return JSONObject.toJSONString(resultMap);
    }

}
