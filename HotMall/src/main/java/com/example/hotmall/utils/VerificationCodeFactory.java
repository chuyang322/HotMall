package com.example.hotmall.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class VerificationCodeFactory {

    private static final Random random = new Random();
    private static final String[] fontNames = { "宋体", "华文楷体", "黑体", "Georgia", "微软雅黑", "楷体_GB2312" };

    public static String create(final int width, final int height, final String imgType, OutputStream outputStream) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            String s = randomChar() + ""; // 随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
            stringBuilder.append(s); // 添加到StringBuilder里面
            int x = (int) (i * 1.0F * width / 4); // 定义字符的x坐标
            graphics.setFont(randomFont()); // 设置字体，随机
            graphics.setColor(randomColor()); // 设置颜色，随机
            graphics.drawString(s, x, height - 5);
        }

        String signCode = stringBuilder.toString();

        // 定义干扰线
        int num = random.nextInt(5) % 3 + 3;
        Graphics2D graphics1 = (Graphics2D) bufferedImage.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics1.setColor(randomColor());
            graphics1.drawLine(x1, y1, x2, y2);
        }

        graphics.dispose();
        graphics1.dispose();
        try {
            ImageIO.write(bufferedImage, imgType, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return signCode;
    }

    // 随机字体
    private static Font randomFont() {
        int index = random.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = random.nextInt(4);
        int size = random.nextInt(20) % 6 + 15;
        return new Font(fontName, style, size);
    }

    // 随机颜色
    private static Color randomColor() {
        int r = random.nextInt(225);
        int g = random.nextInt(225);
        int b = random.nextInt(225);
        return new Color(r, g, b);
    }

    // 随机字符
    private static char randomChar() {

        String str = "0123456789ABCdefghiDEFGHIJopPQRVWXYZabcjklSTUmnqrstKLMNOvuwxyz";

        return str.charAt(random.nextInt(str.length()));
    }

}
