package com.fw.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class EnemyMissile {
	
	// 表示敌机子弹生死状态
	boolean live = true;
	
	// 敌机子弹坐标
	int x;
	int y;
	
	// 敌机子弹宽高
	int w = 20;
	int h = 30;
	
	// 构造方法
	public EnemyMissile(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	// 定义加载敌机子弹的变量
	static Image img ;
	
	// 工具包类
	static Toolkit tk =Toolkit.getDefaultToolkit();
	
	// 静态块加载敌机子弹图片
	static {
		img = tk.createImage(Enemy.class.getClassLoader().getResource("dijizidan.gif"));
	}	
	
	// paint方法,画敌机子弹
	public void paint(Graphics g) {
		g.drawImage(img, x, y, w, h, null);
		
		move();
		
	}	
	
	// 移动方法
	public void move() {
		
		// 敌机子弹向下移动
		y += 15;
		
		// 飞出界面下边界,消亡
		if(y >= 1030 ) {
			live = false;
		}
	}
	
	// 获取敌机子弹区域
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	}
	
	// 打英雄战机的方法
	public void hitHero(Hero hero) {
		Rectangle enemyMissileRect = this.getRect();
		Rectangle heroRect = hero.getRect();
		
		if(enemyMissileRect.intersects(heroRect)) {
			this.live = false;
			int heroLife = hero.life - 10;
			if(heroLife<=0) {
				hero.live = false;
				
				heroLife = 0;
			}
			hero.life = heroLife;
			// 产生爆炸
			Explode exp = new Explode(x,y);
			RaidenGamePanel.explodeList.add(exp);
		}
	}

}
