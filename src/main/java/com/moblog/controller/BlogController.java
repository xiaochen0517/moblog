package com.moblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.moblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/19
 * @version 0.1
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 获取主页文章列表
     * @return 文章列表信息
     */
    @RequestMapping(value = "/homearticle",method = RequestMethod.GET)
    @ResponseBody
    private String homePageArticle(int page){
        if (page > 0){
            return blogService.homePageArticle(page);
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("status", 404);
            return JSONObject.toJSONString(map);
        }
    }

    /**
     * 搜索
     * @return 搜索结果列表
     */
    @RequestMapping(value = "/searcharticle", method = RequestMethod.GET)
    @ResponseBody
    private String searchArticle(String keyword, int page){
        return blogService.searchArticle(keyword, page);
    }

    /**
     * 获取文章信息
     * @param id 文章id
     * @return
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    @ResponseBody
    private String article(int id){
        return blogService.article(id);
    }
}
