package com.test.mybatis;

import com.moblog.dao.AdminDao;
import com.moblog.dao.BlogDao;
import com.moblog.dao.UserDao;
import com.moblog.domain.Article;
import com.moblog.domain.User;
import com.moblog.domain.admin.ReArticle;
import com.moblog.domain.admin.ReUser;
import com.moblog.domain.blog.ReArticleList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/24
 * @version 0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring.xml" })
public class TestOne {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private BlogDao blogDao;

    @Test
    public void test1(){
        List<User> users = userDao.findAll();
        for (User user:users){
            System.out.println(user.toString());
        }
    }

    @Test
    public void test2(){
        List<ReUser> accountList = adminDao.findAccountList((1 - 1) * 10);
        for (ReUser a: accountList){
            System.out.println(a.toString());
        }
        int accountSize = adminDao.findAccountSize();
        System.out.println(accountSize);
    }

    @Test
    public void test3(){
        List<Article> userArticle = adminDao.findUserArticle(1, 0);
        for(Article a: userArticle){
            System.out.println(a.toString());
        }
    }

    @Test
    public void test4(){
        List<ReArticleList> reArticleLists = blogDao.findHomePageArticle(0, 10);
        for (ReArticleList r : reArticleLists){
            System.out.println(r.toString());
        }
    }

    @Test
    public void test5(){
        List<ReArticleList> reArticleLists = blogDao.findSearchArticle("5", 0, 10);
        for (ReArticleList r : reArticleLists){
            System.out.println(r.toString());
        }
    }

    @Test
    public void test6(){
        com.moblog.domain.blog.ReArticle reArticleLists = blogDao.findArticle(1);
        System.out.println(reArticleLists);
    }

}
