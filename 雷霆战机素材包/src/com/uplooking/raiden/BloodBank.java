package com.uplooking.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 加血的类
 * @author Administrator
 *
 */
public class BloodBank extends ActionComponent{

	// 常量的定义
	public static final int BLOOD_BANK_WIDTH = 60;
	
	public static final int BLOOD_BANK_HEIGHT = 60;
	
	// 加血的图片
	static Image bloodImg ;
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	static {
		bloodImg = tk.createImage(
				BloodBank.class.getClassLoader().getResource("blood1.png")
				);
	}
	
	// 构造方法
	public BloodBank(int x,int y) {
		super(x,y);
	}
	
	public void paint(Graphics g) {
		g.drawImage(bloodImg, this.getX(), this.getY(), 
				BLOOD_BANK_WIDTH, BLOOD_BANK_HEIGHT, null);
		
		// 每次画出加血图片，调用移动的方法
		move();
	}
	
	// 移动的方法
	public void move() {
		int y = this.getY() + 10;
		
		this.setY(y);
		
		if(y > 730) {
			this.setLive(false);
		}
	}
	
	// 碰撞检测的方法
	public void collideWithHero(Hero hero) {
		Rectangle bloodRect = this.getRect(BLOOD_BANK_WIDTH, BLOOD_BANK_HEIGHT);
		Rectangle heroRect = hero.getRect(Hero.HERO_WIDTH, Hero.HERO_HEIGHT);
		
		if(bloodRect.intersects(heroRect)) {
			//
			this.setLive(false);
			hero.life += 20;
			
			if(hero.life > 100) {
				hero.life = 100;
			}
		}
	}
}











