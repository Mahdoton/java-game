package com.uplooking.raiden;
/**
 * 英雄的子弹类
 * @author Administrator
 *
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class HeroMissile extends ActionComponent{
	
	// 常量的定义
	public static final int HERO_MISSILE_WIDTH = 40;
	
	public static final int HERO_MISSILE_HEIGHT = 45;
	
	// 子弹的速度，常量
	public static final int SPEED = 20;
	
	
	// 用来创建子弹图片的工具包
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	// 所有英雄的子弹共用一个图片
	static Image[] missileImg = new Image[8];
	
	//
	static int index = 0;
	
	
	static {
		
		missileImg[0] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("herozidan.gif"));
		missileImg[1] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("herozidan1.gif"));
		missileImg[2] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("herozidan2.gif"));
		missileImg[3] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("HM.gif"));
		missileImg[4] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("HM2.gif"));
		missileImg[5] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("HM3.gif"));
		missileImg[6] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("HM4.gif"));
		missileImg[7] = tk.createImage(
				HeroMissile.class.getClassLoader().getResource("HM5.gif"));
	}
	
	// 构造方法，创建一个子弹的时候，就应该知道子弹在那里（坐标）
	public HeroMissile(int x,int y) {
		super(x,y);
	}
	
	// 子弹画出自己
	public void paint(Graphics g) {
		g.drawImage(missileImg[index], this.getX(), this.getY(), 
				HERO_MISSILE_WIDTH, HERO_MISSILE_HEIGHT, null);
		
		// 每次画子弹，移动的方法被调用
		move();
	}
	
	// 移动的方法
	public void move() {
		
		this.setY(this.getY()-HeroMissile.SPEED);
		
		
		// 判断，如果已经飞出游戏的上边界，子弹就死了
		if(this.getY() < 0) {
			// 子弹死了
			this.setLive(false);
		}
	}
	
	// 英雄子弹打敌人(打一个敌人)
	public boolean hitEnemy(Enemy enemy) {
		// 判断当前子弹的区域，是否和enemy区域相交，如果相交，打中了
		Rectangle missileRect = this.getRect(HERO_MISSILE_WIDTH, HERO_MISSILE_HEIGHT);
		Rectangle enemyRect = enemy.getRect(Enemy.ENEMY_WIDTH,Enemy.ENEMY_HEIGHT);
		
		if(missileRect.intersects(enemyRect)) {
			// 打中了 ,当前子弹死了，敌人也死了
			this.setLive(false);
			enemy.setLive(false);
			
			// 产生爆炸，并且将爆炸实例（对象）添加到爆炸集合中
			Explode explode = new Explode(this.getX(), this.getY());
			RaidenPanel.explodeList.add(explode);
			
			// 积分加上10
			RaidenPanel.score += 10; 
			
			return true;
		}
		// 没有打中，返回false
		return false;
	}
	
	// 英雄子弹打敌人(打多个敌人)
	public void hitEnemyList(List<Enemy> enemyList) {
		// 遍历集合，逐个打敌人
		for(Iterator<Enemy> it = enemyList.iterator();it.hasNext();) {
			Enemy enemy = it.next();
			boolean b = this.hitEnemy(enemy);
			// 如果已经打中一个敌人，方法返回，也就是不用再去打其他敌人了
			if(b) {
				return;
			}
		}
	}
	
	// 英雄的子弹打敌人的子弹
	public boolean hitEnemyMissile(EnemyMissile em) {
		Rectangle heroMissileRect = this.getRect(HERO_MISSILE_WIDTH, HERO_MISSILE_HEIGHT);
		Rectangle enemyMissileRect = 
				em.getRect(EnemyMissile.ENEMY_MISSILE_WIDTH, EnemyMissile.ENEMY_MISSILE_HEIGHT);
		if(heroMissileRect.intersects(enemyMissileRect)) {
			// 
			this.setLive(false);
			em.setLive(false);
			// 产生爆炸
			Explode explode = new Explode(getX(), getY());
			RaidenPanel.explodeList.add(explode);
			
			return true;
		}
		return false;
	}
	
	public void hitEnemyMissileList(List<EnemyMissile> emList) {
		for(Iterator<EnemyMissile> it = emList.iterator();it.hasNext();) {
			EnemyMissile enemyMissile = it.next();
			
			boolean b = hitEnemyMissile(enemyMissile);
			if(b) {
				return;
			}
		}
	}
	
	
}













