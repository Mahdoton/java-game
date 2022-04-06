package com.uplooking.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

/**
 * 敌机类
 * @author Administrator
 *
 */
public class Enemy extends ActionComponent{
	
	// 敌机方向的属性:0 左；1 中；2右；
	private int dir;
	
	private int step = new Random().nextInt(20)+5;
	
	// 常量的定义
	public static final int ENEMY_WIDTH = 50;
	
	public static final int ENEMY_HEIGHT = 50;
	
	static Image[] enemyImg = new Image[3];
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	static {
		enemyImg[0] = tk.createImage(Enemy.class.getClassLoader().getResource("dijileft.gif"));
		enemyImg[1] = tk.createImage(Enemy.class.getClassLoader().getResource("diji.gif"));
		enemyImg[2] = tk.createImage(Enemy.class.getClassLoader().getResource("dijiright.gif"));
	}
	
	// 构造方法
	public Enemy(int x,int y,int dir) {
		super(x,y);
		this.dir = dir;
	}
	
	public void paint(Graphics g) {
		//
		switch(dir) {
		case 0:
			g.drawImage(enemyImg[0], this.getX(), this.getY(), ENEMY_WIDTH, ENEMY_HEIGHT, null);
			break;
		case 1:
			g.drawImage(enemyImg[1], this.getX(), this.getY(), ENEMY_WIDTH, ENEMY_HEIGHT, null);
			break;
		case 2:
			g.drawImage(enemyImg[2], this.getX(), this.getY(), ENEMY_WIDTH, ENEMY_HEIGHT, null);
			break;
		}
		// 每次画完以后，让敌人移动
		move();
	}
	
	// 移动的方法
	public void move() {
		
		this.setY(this.getY()+6);
		
		if(this.getY() > 730) {
			// 飞出游戏的下边界，敌人死了
			this.setLive(false);
		}
		
		if(dir == 0) {
			this.setX(this.getX()-5);;
		}
		if(dir == 2) {
			this.setX(this.getX()+5);
		}
		
		step--;
		if(step <= 0) {
			// 随机改变敌机方向，改变dir ： 0 1 2
			dir = new Random().nextInt(3);
			step = new Random().nextInt(20)+5;
		}
		
		// 敌人移动的时候，开火，火力太强
		if(new Random().nextInt(1000) > 980) {
			fire();
		}
		
	}
	
	// 敌人开火
	public void fire() {
		EnemyMissile em = new EnemyMissile(this.getX()+10, this.getY());
		RaidenPanel.enemyMissileList.add(em);
	}
	
	// 敌人和英雄相撞的方法
	public void collideWithHero(Hero hero) {
		//
		Rectangle heroRect = hero.getRect(Hero.HERO_WIDTH, Hero.HERO_HEIGHT);
		Rectangle enemyRect = this.getRect(ENEMY_WIDTH, ENEMY_HEIGHT);
		// 敌人撞到了英雄
		if(enemyRect.intersects(heroRect)) {
			this.setLive(false);
			
			hero.life -= 5;
			
			if(hero.life <= 0 ) {
				hero.setLive(false);
				hero.life = 0;
			}
			
			// 
			// 产生爆炸
			Explode explode = new Explode(getX(), getY());
			RaidenPanel.explodeList.add(explode);
		}
	}

}












