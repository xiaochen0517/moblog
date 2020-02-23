package com.moblog.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
public class HeaderFilter implements Filter {

    private static final String TAG = "HeaderFilter";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String originHeader = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "0");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed", "1");
        response.setHeader("XDomainRequestAllowed", "1");
        //获取请求ip
//        Log.d(TAG, "访问来源IP-->"+request.getRemoteAddr());
        //获取请求路径
        String uri = request.getRequestURI();
        Log.d(TAG, "访问uri-->" + uri);
        if (uri.contains("index") ||
                uri.equalsIgnoreCase("/moblog/") ||
                uri.contains("css") ||
                uri.contains("js") ||
                uri.contains("images") ||
                uri.contains("fonts") ||
                uri.contains("login") ||
                uri.contains("register") ||
                uri.contains("test") ||
                uri.contains("util") ||
                uri.contains("/blog/")) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");

            if (username != null && !username.equals("")) {
                //已登录
                Log.d(TAG, "用户名-->" + username);
                chain.doFilter(request, response);
            } else {
                if(uri.contains("/blog/") ||
                        uri.contains("/user/")){
                    response.setHeader("content-type", "text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", 499);
                    out.print(JSONObject.toJSONString(map));
                    return;
                }
                //未登录，跳转到登录页面
                response.setStatus(302);
                response.setHeader("location", "/moblog/");
            }
        }
    }

    @Override
    public void destroy() {

    }
}

