package com.moblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moblog.dao.BlogDao;
import com.moblog.domain.blog.ReArticle;
import com.moblog.domain.blog.ReArticleList;
import com.moblog.service.BlogService;
import com.moblog.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/2/19
 * @version 0.1
 */
@Component
public class BlogServiceImpl implements BlogService {

    private final static String TAG = "BlogServiceImpl";

    private final static int pageSize = 10;

    @Autowired
    private BlogDao blogDao;

    @Override
    public String homePageArticle(int page) {
        //计算页数
        int pageStart = (page - 1) * pageSize;
        //获取数据
        List<ReArticleList> reArticleLists = blogDao.findHomePageArticle(pageStart, pageSize);
        //获取文章条数
        int articleSize = blogDao.findAllArticleSize();
        //计算页面数量
        int pageSizes = (articleSize / pageSize) + (articleSize % pageSize > 0 ? 1 : 0);
        //包装json
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 200);
        jsonMap.put("articlesize", articleSize);
        jsonMap.put("pagesize", pageSizes);
        jsonMap.put("articlelist", reArticleLists);

        String jsonStr = JSONObject.toJSONString(jsonMap);
        Log.d(TAG, "jsonstr --> " + jsonStr);

        return jsonStr;
    }

    @Override
    public String searchArticle(String keyword, int page) {
        Map<String, Object> map = new HashMap<>();
        //对上传的数据进行过滤
        if (keyword == null || keyword.equals("") || page < 1) {
            //返回空
            map.put("status", 404);
            return JSONObject.toJSONString(map);
        }
        //进行查找
        int searchSize = blogDao.findSearchArticleSize(keyword);
        if (searchSize <= 0){
            //查询不到数据
            map.put("status", 200);
            map.put("articlesize", 0);
            return JSONObject.toJSONString(map);
        }
        //查询有数据
        //计算分页
        int start = (page-1)*pageSize;
        List<ReArticleList> reArticleLists = blogDao.findSearchArticle(keyword, start, pageSize);
        int pageSizes = (searchSize / pageSize) + (searchSize % pageSize > 0 ? 1 : 0);
        map.put("status", 200);
        map.put("articlesize", searchSize);
        map.put("pagesize", pageSizes);
        map.put("articlelist", reArticleLists);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr --> "+jsonstr);
        return jsonstr;
    }

    @Override
    public String article(int id) {
        Map<String, Object> map = new HashMap<>();
        //判断参数
        if (id < 1){
            map.put("status", 404);
            return JSONObject.toJSONString(map);
        }
        //获取数据
        ReArticle reArticle = blogDao.findArticle(id);
        if (reArticle == null || reArticle.getTitle().equals("")){
            //没有数据
            map.put("status", 405);
            return JSONObject.toJSONString(map);
        }
        //返回数据
        map.put("status", 200);
        map.put("article", reArticle);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, jsonstr);
        return jsonstr;
    }
}
