package com.uplooking.raiden;
/**
 * 英雄的类  OOAD --> OOP  
 * @author Administrator
 *
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

public class Hero extends ActionComponent{

	
	
	
	// 是否开火的一个标识，true开火；false不开火
	private boolean fireFlag;
	
	// 上一次开火产生的子弹
	private HeroMissile oldMissile;
	
	// 常量
	public static final int HERO_WIDTH = 100;
	public static final int HERO_HEIGHT = 100;
	
	// 类变量
	public static int life = 100;
	// 类变量
	static Image heroImg[] = new Image[3];
	
	static int index = 0;
	
	
	// 声音播放器
	static AudioClip ac ;
	
	static {
		Toolkit tk = Toolkit.getDefaultToolkit();
		heroImg[0] = tk.createImage(Hero.class.getClassLoader().getResource("hero2.gif"));
		heroImg[1] = tk.createImage(Hero.class.getClassLoader().getResource("hero3.gif"));
		heroImg[2] = tk.createImage(Hero.class.getClassLoader().getResource("hero4.gif"));
		
		// 开火的声音，加载
		ac = Applet.newAudioClip(Hero.class.getClassLoader().getResource("zzam.au"));
	}
	
	public Hero(int x,int y) {
		// 通过super关键字，显示调用父类的构造方法
		super(x,y);
	}
	
	// 画自己
	public void paint(Graphics g) {
		
		// 判断英雄是不是活的，如果已经牺牲了，那么就不画了
		if(!this.isLive()) {
			return;
		}
		
		g.drawImage(heroImg[index], this.getX()-25, this.getY()-20, HERO_WIDTH, HERO_HEIGHT, null);
		
		// 每次移动时，判断是否开火
		if(fireFlag) {
			fire();
		}
	}
	
	// 移动的方法（飞行）
	public void move(int x,int y) {
		this.setX(x);
		this.setY(y);;
	}
	// 鼠标按下处理
	public void mousePressed(MouseEvent e) {
		// 鼠标按下，开火
		fireFlag = true;
	}
	
	// 鼠标释放时，处理
	public void mouseReleased(MouseEvent e) {
		fireFlag = false;
	}
	
	// 英雄开火的方法，不能让子弹过于密集，飞出一段距离后，再开火
	public void fire() {
		//
		
		if(oldMissile == null) {
			// 创建一个子弹，子弹的坐标应该和英雄的坐标一致
			HeroMissile missile = new HeroMissile(this.getX()+8, this.getY());
			// 将子弹加入到集合中
			RaidenPanel.heroMissileList.add(missile);
			
			oldMissile = missile;
		}else {
			if(Math.abs(oldMissile.getX()-this.getX())>50 || Math.abs(oldMissile.getY()-this.getY())>50) {
				HeroMissile missile = new HeroMissile(this.getX()+8, this.getY());
				// 将子弹加入到集合中
				RaidenPanel.heroMissileList.add(missile);
				
				oldMissile = missile;
			}
		}
		// 播放开火的声音，启动线程的方式来播放声音
		new HeroThread().start();
	}
	
	// 超级火力，打出十个子弹
	public void superFire() {
		for(int i = 1;i<=10;i++) {
			HeroMissile missile = new HeroMissile(i*45, this.getY());
			// 将子弹加入到集合中
			RaidenPanel.heroMissileList.add(missile);
		}
		
	}
	
	// 内部类，线程类
	class HeroThread extends Thread{

		@Override
		public void run() {
			// 播放开火的声音
			ac.play();
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}










