package com.moblog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moblog.dao.BlogDao;
import com.moblog.domain.Blogroll;
import com.moblog.domain.HomePhoto;
import com.moblog.domain.blog.*;
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

    private final static String status = "status";

    @Autowired
    private BlogDao blogDao;


    @Override
    public String homePageArticle(int page) {
        Map<String, Object> jsonMap = new HashMap<>();
        //计算页数
        int pageStart = (page - 1) * pageSize;
        //获取文章条数
        int articleSize = blogDao.findAllArticleSize();
        //判断是否有数据
        if (articleSize < 1) {
            // 无数据
            jsonMap.put("status", 200);
            jsonMap.put("articlesize", 0);
            return JSONObject.toJSONString(jsonMap);
        }
        //获取数据
        List<ReArticleList> reArticleLists = blogDao.findHomePageArticle(pageStart, pageSize);
        //计算页面数量
        int pageSizes = (articleSize / pageSize) + (articleSize % pageSize > 0 ? 1 : 0);
        //包装json
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
        if (searchSize <= 0) {
            //查询不到数据
            map.put("status", 200);
            map.put("articlesize", 0);
            return JSONObject.toJSONString(map);
        }
        //查询有数据
        //计算分页
        int start = (page - 1) * pageSize;
        List<ReArticleList> reArticleLists = blogDao.findSearchArticle(keyword, start, pageSize);
        int pageSizes = (searchSize / pageSize) + (searchSize % pageSize > 0 ? 1 : 0);
        map.put("status", 200);
        map.put("articlesize", searchSize);
        map.put("pagesize", pageSizes);
        map.put("articlelist", reArticleLists);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr --> " + jsonstr);
        return jsonstr;
    }

    @Override
    public String article(int id) {
        Map<String, Object> map = new HashMap<>();
        //判断参数
        if (id < 1) {
            map.put("status", 404);
            return JSONObject.toJSONString(map);
        }
        //获取数据
        ReArticle reArticle = blogDao.findArticle(id);
        if (reArticle == null || reArticle.getTitle().equals("")) {
            //没有数据
            map.put("status", 405);
            return JSONObject.toJSONString(map);
        }
        //将文章浏览量加1
        int returns = blogDao.addArticleBrowse(id);
        Log.d(TAG, "文章id-->" + id + "  浏览量-->" + returns);
        reArticle.setBrowse(reArticle.getBrowse() + 1);
        //返回数据
        map.put("status", 200);
        map.put("article", reArticle);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, jsonstr);
        return jsonstr;
    }

    @Override
    public String recommend() {
        Map<String, Object> map = new HashMap<>();
        List<Recommend> lists = blogDao.findRecommend();
        if (lists == null || lists.size() < 1) {
            map.put("status", 404);
            return JSONObject.toJSONString(map);
        }
        map.put("status", 200);
        map.put("lists", lists);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr --> " + jsonstr);
        return jsonstr;
    }

    @Override
    public String comment(int aid, int page) {
        Map<String, Object> map = new HashMap<>();
        // 检查数据
        if (aid < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        if (page < 1) {
            map.put(status, 405);
            return JSONObject.toJSONString(map);
        }
        // 计算页数
        int start = (page - 1) * pageSize;
        // 查询评论总数
        int commentSize = blogDao.findArticleReCommentSize(aid);
        if (commentSize == 0) {
            // 无评论
            map.put(status, 200);
            map.put("commentsize", 0);
            return JSONObject.toJSONString(map);
        }
        // 查询
        List<ReComment> articleReComment = blogDao.findArticleReComment(aid, start, pageSize);
        // 返回数据
        map.put(status, 200);
        map.put("commentsize", commentSize);
        map.put("pagesize", (commentSize / pageSize) + (commentSize % pageSize > 0 ? 1 : 0));
        map.put("comment", articleReComment);
        String jsonStr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr-->" + jsonStr);
        return jsonStr;
    }

    @Override
    public String blogRoll() {
        Map<String, Object> map = new HashMap<>();
        // 获取友链数据
        int blogRollSize = blogDao.findBlogRollSize();
        if (blogRollSize < 1) {
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        // 获取友链
        List<Blogroll> blogRoll = blogDao.findBlogRoll();
        map.put(status, 200);
        map.put("blogrollsize", blogRollSize);
        map.put("blogrolls", blogRoll);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr-->" + jsonstr);
        return jsonstr;
    }

    @Override
    public String records() {
        Map<String, Object> map = new HashMap<>();
        // 获取备案数据
        String records = blogDao.findRecords();
        if (records == null){
            records = "";
        }
        map.put(status, 200);
        map.put("records", records);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr-->" + jsonstr);
        return jsonstr;
    }

    @Override
    public String homePhotos() {
        Map<String, Object> map = new HashMap<>();
        // 获取备案数据
        List<HomePhoto> homePhotos = blogDao.findHomePhotos();
        if (homePhotos.size() < 1){
            map.put(status, 404);
            return JSONObject.toJSONString(map);
        }
        map.put(status, 200);
        map.put("homephotosize", homePhotos.size());
        map.put("homephotos", homePhotos);
        String jsonstr = JSONObject.toJSONString(map);
        Log.d(TAG, "jsonstr-->" + jsonstr);
        return jsonstr;
    }

    @Override
    public String perMsg() {
        RePerMsg rePerMsg = new RePerMsg();
        // 获取数据
        String perPhoto = blogDao.findPerPhoto();
        String perContent = blogDao.findPerContent();
        if (perPhoto == null || perContent == null){
            rePerMsg.setStatus(404);
            return JSONObject.toJSONString(rePerMsg);
        }
        rePerMsg.setStatus(200);
        rePerMsg.setPerphoto(perPhoto);
        rePerMsg.setPercontent(perContent);
        String jsonstr = JSONObject.toJSONString(rePerMsg);
        Log.d(TAG, "jsonstr-->" + jsonstr);
        return jsonstr;
    }
}
