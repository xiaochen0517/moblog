package com.moblog.controller;

import com.moblog.util.ColorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 功能：
 *
 * @author MoChen
 * Date  2020/1/26
 * @version 0.1
 */
@Controller
@RequestMapping("/util")
public class UtilController {

    @RequestMapping("/vcode")
    private void getVCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //验证码
        StringBuilder identcode = new StringBuilder();

        int width = 100;
        int height = 40;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);

        //字符集
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgjijklmnopqrstuvwxyz";

        //随机数
        Random ran = new Random();

        //绘制验证码
        for (int i = 0; i < 4; i++) {
            //获取随机字符
            int index = ran.nextInt(str.length());
            char ch = str.charAt(index);
            //保存字符
            identcode.append(ch);
            //获取随机色
            Color randomColor = ColorUtils.getRandomColor(ran);
            g.setColor(randomColor);
            //设置字体
            Font font = new Font("宋体", Font.BOLD, height / 2);
            g.setFont(font);
            //写入验证码
            g.drawString(ch + "", (i == 0) ? width / 4 * i + 2 : width / 4 * i, height - height / 4);
        }

        //干扰线
        for (int i = 0; i < 10; i++) {
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);
            Color randomColor = ColorUtils.getRandomColor(ran);
            g.setColor(randomColor);
            g.drawLine(x1, x2, y1, y2);
        }

        ImageIO.write(image, "jpg", response.getOutputStream());
        //将验证码写入session
        System.out.println(identcode);
        HttpSession session = request.getSession();
        session.setAttribute("vcode", identcode.toString());
    }

}
