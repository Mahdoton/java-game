package com.fw.raiden;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

@SuppressWarnings("deprecation")
public class Explode {
	// 位置
	int x;
	int y;
	
	// 表示爆炸的生死状态
	boolean live = true;
	
	// 大小
	int w = 60;
	int h = 60;
	
	// 构造方法
	public Explode(int x,int y){
		ac.play();
		this.x=x;
		
		this.y=y;
	}
	
	// 数组下标
	int index = 0;
	
	// 每张图片画n次
	int n = 3;
	
	// 爆炸图片
	// 定义加载敌机图片的数组变量
	static Image[] img = new Image[11];
	
	// 加载爆炸声音
	static AudioClip ac;
	
	
	// 工具包类
	static Toolkit tk =Toolkit.getDefaultToolkit();
	
	// 静态块加载敌机图片
	static {
		ac = Applet.newAudioClip(Hero.class.getClassLoader().getResource("missle.au"));
		img[0] = tk.createImage(Enemy.class.getClassLoader().getResource("b0.gif"));
		img[1] = tk.createImage(Enemy.class.getClassLoader().getResource("b1.gif"));
		img[2] = tk.createImage(Enemy.class.getClassLoader().getResource("b2.gif"));
		img[3] = tk.createImage(Enemy.class.getClassLoader().getResource("b3.gif"));
		img[4] = tk.createImage(Enemy.class.getClassLoader().getResource("b1.gif"));
		img[5] = tk.createImage(Enemy.class.getClassLoader().getResource("b2.gif"));
		img[6] = tk.createImage(Enemy.class.getClassLoader().getResource("b3.gif"));
		img[7] = tk.createImage(Enemy.class.getClassLoader().getResource("b4.gif"));
		img[8] = tk.createImage(Enemy.class.getClassLoader().getResource("b5.gif"));
		img[9] = tk.createImage(Enemy.class.getClassLoader().getResource("b6.gif"));
		img[10] = tk.createImage(Enemy.class.getClassLoader().getResource("b7.gif"));
	}
	
	
	// 画爆炸
	public void paint(Graphics g) {
		// 如果爆炸状态为死,那么不再画出爆炸
		if(index == img.length) {
			live = false;
			return;
		}
		
		g.drawImage(img[index], x, y, w, h, null);
		
		// 让每一个图片都画3次
		if(n<=0) {
			index++;
			n = 3;
		}
		n--;
	}	
	
}
