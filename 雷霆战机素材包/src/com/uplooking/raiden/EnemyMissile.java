package com.uplooking.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 敌人的子弹类
 * @author Administrator
 *
 */
public class EnemyMissile extends ActionComponent{
	
	public static final int ENEMY_MISSILE_WIDTH = 15;
	
	public static final int ENEMY_MISSILE_HEIGHT = 25;
	
	static Image enemyMissileImg ;
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	static {
		enemyMissileImg = tk.createImage(
				EnemyMissile.class.getClassLoader().getResource("dijizidan.gif")
				);
	}

	public EnemyMissile(int x,int y) {
		super(x,y);
	}
	
	// 画出敌人的子弹
	public void paint(Graphics g) {
		g.drawImage(enemyMissileImg, this.getX(), getY(), 
				ENEMY_MISSILE_WIDTH, ENEMY_MISSILE_HEIGHT, null);
		
		// 每次画完后，调用移动的方法
		move();
	}
	
	public void move() {
		int y = this.getY()+18;
		
		this.setY(y);
		
		// 判断子弹是不是飞出了游戏的下边界
		if(y > 730) {
			// 飞出边界，死了
			this.setLive(false);
		}
	}
	
	/**
	 * 打英雄的方法
	 * @param hero 英雄的实例
	 */
	public void hitHero(Hero hero) {
		//
		Rectangle heroRect = hero.getRect(Hero.HERO_WIDTH, Hero.HERO_HEIGHT);
		
		Rectangle missileRect = this.getRect(ENEMY_MISSILE_WIDTH, ENEMY_MISSILE_HEIGHT);
		
		if(missileRect.intersects(heroRect)) {
			// 打中了，敌人的子弹死了，英雄死了
			this.setLive(false);
			
			
			Hero.life -= 10;
			
			
			if(Hero.life <= 0) {
				hero.setLive(false);
				Hero.life = 0;
				
			}
			
			// 产生爆炸
			Explode explode = new Explode(getX(), getY());
			RaidenPanel.explodeList.add(explode);
		}
		
	}
}















