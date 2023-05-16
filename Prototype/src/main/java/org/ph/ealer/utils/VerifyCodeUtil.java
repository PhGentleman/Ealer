package org.ph.ealer.utils;

import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class VerifyCodeUtil {
	private int w = 80;
	private int h = 35;
 
	private Random random = new Random();
	private String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312" };
	private String codes = "123456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ";
	private String text;
	
 	private Color randomColor() {
		int red = random.nextInt(150);
		int green = random.nextInt(150);
		int blue = random.nextInt(150);
		return new Color(red, green, blue);
	}

	private Font randomFont() {
		int index = random.nextInt(fontNames.length);
		String fontName = fontNames[index];
		int style = random.nextInt(4);
		int size = random.nextInt(5) + 24;
 
		return new Font(fontName, style, size);
	}

	private void drawLine(BufferedImage image) {
		int num = 3;
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		for (int i = 0; i < num; i++) {
			int x1 = random.nextInt(w);
			int y1 = random.nextInt(h);
			int x2 = random.nextInt(w);
			int y2 = random.nextInt(h);
			g2.setStroke(new BasicStroke(1.5F));
			g2.setColor(Color.blue);
			g2.drawLine(x1, y1, x2, y2);
		}
	}
 
	private char randomChar() {
		int index = random.nextInt(codes.length());
		return codes.charAt(index);
	}
 
	public BufferedImage createImage() {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.fillRect(0, 0, w, h);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String s = randomChar() + "";
			sb.append(s);
			float x = i * 1.0F * w / 4;
			g2.setFont(randomFont());
			g2.setColor(randomColor());
			g2.drawString(s, x, h - 5);
		}
		this.text = sb.toString();
		drawLine(image);
		return image;
	}
	
	public String getText() {
		return this.text;
	}

	public static boolean codeVerify(HttpSession session, String userCode) {
		try {
			String verifyCode = (String) session.getAttribute("verifyCode");
			if (verifyCode == null) {
				return false;
			}
			verifyCode = verifyCode.toLowerCase();
			userCode = userCode.toLowerCase();
			if (userCode.equals(verifyCode))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void output(BufferedImage bi, OutputStream fos) {
		 try {
			 ImageIO.write(bi, "JPEG", fos);
		 }  catch (IOException ioException){
			 ioException.printStackTrace();
		 }
	}
}
