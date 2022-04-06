package com.fw.raiden;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

@SuppressWarnings("deprecation")
public class Hero {
	// 战机所处的位置
	int x;
	int y;
	
	// 战机的宽度和高度,是一个常量
	public static final int HERO_WIDTH = 150;
	public static final int HERO_HEIGHT =150;	
	
	// 构造方法
	public Hero(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	// 表示生死状态
	boolean live = true;
	
	// 表示英雄战机生命值
	int life = 1000;
	
	// 开火标识
	static boolean fireFlag = false;
	
	// 定义一个子弹引用,表示是上一发子弹,对象均为引用类型,类似于指针
	HeroMissile oldMissile;
	
	// 战机图片
	static Image heroImg;
	
	// 加载开火声音
	static AudioClip ac;
	
	// 工具包类
	static Toolkit tk =Toolkit.getDefaultToolkit();
	
	static {
		ac = Applet.newAudioClip(Hero.class.getClassLoader().getResource("zzam.au"));
		heroImg = tk.createImage(Hero.class.getClassLoader().getResource("hero010.png"));
	}
	
	// 画出战机
	public void paint(Graphics g) {
		// 如果英雄战机已经死亡,那么不再画出英雄战机
		if(!live) {
			return;
		}
		
		g.drawImage(heroImg, x, y, HERO_WIDTH, HERO_HEIGHT, null);
		
		// 如果开火标识为真,调用fire方法进行开火
		if(fireFlag) {
			fire();
		}
	}
	
	// bj001 机身200*200
//	public void mouseMoved(MouseEvent e) {
//		x = e.getX() - 100;
//		y = e.getY() - 130;
//	}
	
	// hero007 机身300*300
//	public void mouseMoved(MouseEvent e) {
//		x = e.getX() - 157;
//		y = e.getY() - 209;
//	}
	
	// hero010 机身150*150
	public void mouseMoved(MouseEvent e) {
		x = e.getX() - 85;
		y = e.getY() - 130;
	}
	
	// hero012 机身170*100
//	public void mouseMoved(MouseEvent e) {
//		x = e.getX() - 92;
//		y = e.getY() - 85;
//	}
		
	
	// 开火方法
	public void fire() {
		
		// 判断上一发子弹是否飞出一定距离或者为空
		if(oldMissile == null){
			// 播放开火声音
			ac.play();
			// hero010战机
			HeroMissile missile = new HeroMissile(x+17,y - 60);
			RaidenGamePanel.herMissileList.add(missile);
			oldMissile = missile;
			
			// hreo007战机
//			HeroMissile missile = new HeroMissile(x+99,y+15);
			
			// hero012战机
//			HeroMissile missile = new HeroMissile(x + 35,y - 90);
			
		}else if(Math.abs(y - oldMissile.y ) >100 || oldMissile.live == false){//解决战机与敌机重合而无法开火的bug
			// 播放开火声音
			ac.play();
			// hero010战机
			HeroMissile missile = new HeroMissile(x+17,y - 60);
			RaidenGamePanel.herMissileList.add(missile);
			oldMissile = missile;
			
			// hreo007战机
//			HeroMissile missile = new HeroMissile(x+99,y+15);

			// hero012战机
//			HeroMissile missile = new HeroMissile(x + 35,y - 90);
			
		}
	}
	
	// 获取英雄战机区域
	public Rectangle getRect() {
		return new Rectangle(x,y,HERO_WIDTH,HERO_HEIGHT);
	}
	
	// 超级火力方法
	public void superFire(){
		for(int i = 0;i<10;i++) {
			HeroMissile missile = new HeroMissile(130*i,y - 60);
			RaidenGamePanel.herMissileList.add(missile);
		}
	}
	
}
