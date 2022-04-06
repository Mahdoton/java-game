package com.fw.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Enemy {
	
	// 定义敌机的初始位置
	int x;
	int y;
	
	// 构造方法
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// 表示敌机生死状态
	boolean lives = true;
	
	// 定义敌机的宽高
	public static final int HERO_ENEMY_WIDTH = 100;
	public static final int HERO_ENEMY_HEIGHT = 100;
	
	// 定义一个表示方向的值
	int dir = 0;
	
	// 定义每次改变方向后飞行的距离
	int n = new Random().nextInt(20)+10;

	// 定义加载敌机图片的数组变量
	static Image[] img = new Image[3];
	
	// 工具包类
	static Toolkit tk =Toolkit.getDefaultToolkit();
	
	// 静态块加载敌机图片
	static {
		img[0] = tk.createImage(Enemy.class.getClassLoader().getResource("dijileft.gif"));
		img[1] = tk.createImage(Enemy.class.getClassLoader().getResource("diji.gif"));
		img[2] = tk.createImage(Enemy.class.getClassLoader().getResource("dijiright.gif"));
	}

	// 画出敌机
	public void paint(Graphics g) {
		
		if(n<=0) {
			// 每次画敌机的时候,随机改变敌机的方向，方向改变之后,飞行一段距离之后再改变方向
			dir =  new Random().nextInt(3);
			n = new Random().nextInt(20)+10;
		}
		n--;
		
		// 画出敌机
		g.drawImage(img[dir], x, y, HERO_ENEMY_WIDTH, HERO_ENEMY_HEIGHT, null);
		
		// 敌机移动
		move();	
		
	}
	
	// 移动方法
	public void move() {
		y += 5;
		if(dir==0) {
			x -=5;
		}
		if(dir==2) {
			x +=5;
		}
		
		if(y>1000) {
			lives=false;
		}
		
		// 每次移动时开火
		int i = new Random().nextInt(1000);
		if(i > 990) {
			fire();
		}
	}
	
	// 获取敌机区域
	public Rectangle getRect() {
		return new Rectangle(x,y,HERO_ENEMY_WIDTH,HERO_ENEMY_HEIGHT);
	}
	
	// 敌机开火
	public void fire() {
	
		// 产生一个子弹
		EnemyMissile enemyMissile = new EnemyMissile(x + 40,y + 60);
		RaidenGamePanel.enemyMissileList.add(enemyMissile);
		
	}
		
}
