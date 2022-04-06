package com.fw.raiden;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;


/**
 * 子弹类
 * @author DELL
 * 
 */
public class HeroMissile {
	
	// 定义子弹类的属性
	int x;
	int y;
	
	// 构造方法
	public HeroMissile(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	// 定义是否爆炸的标识
	static boolean bangFlag = false;
	
	// 子弹类的宽高属性
	public static final int HERO_MISSILE_WIDTH = 117;
	public static final int HERO_MISSILE_HEIGHT = 117;
	
	// 定义子弹图片
	static Image heroMissileImg;
	
	// 定义子弹生死状态的变量
	boolean live = true;

	// 工具类
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	// 静态加载块
	static {
//		heroMissileImg = tk.createImage(RaidenGamePanel.class.getClassLoader().getResource("bang001.png"));
		
		// 010战机
		heroMissileImg = tk.createImage(RaidenGamePanel.class.getClassLoader().getResource("zidan0011.png"));
		
		// 007战机
//		heroMissileImg = tk.createImage(RaidenGamePanel.class.getClassLoader().getResource("zidan002.png"));
		
		// 012战机
//		heroMissileImg = tk.createImage(RaidenGamePanel.class.getClassLoader().getResource("zidan003.png"));
	}
	
	// 画出英雄战机子弹
	public void paint(Graphics g) {
		// 画出背景图
		g.drawImage(heroMissileImg, x, y, HERO_MISSILE_WIDTH, HERO_MISSILE_HEIGHT, null);
		
		// 英雄战机子弹移动
		move();
	}
	
	// 让子弹移动
	public void move() {
		y -= 40;
//		x += 40;
		
		if(y <= -20) {
			//子弹飞出边界
			live = false;
		}
	}
	
	
	// 获取子弹区域
	public Rectangle getRect() {
		return new Rectangle(x,y,HERO_MISSILE_WIDTH,HERO_MISSILE_HEIGHT);
	}
	
	// 打一个敌机的方法
	@SuppressWarnings("deprecation")
	public boolean hitEnemy(Enemy enemy) {
		Rectangle missileRect = this.getRect();
		Rectangle enemyRect = enemy.getRect();
		
		// 判断两个区域是否相交
		if(missileRect.intersects(enemyRect)) {
			
			// 打中了 ,敌人死 ,子弹死
			this.live =false;
			enemy.lives =false;
			
			// 爆炸声
			Explode.ac.play();
			
			// 产生爆炸
			Explode exp = new Explode(x,y);
			RaidenGamePanel.explodeList.add(exp);
			
			// 打中敌机
			return true;
			
		}
		// 没打中敌机
		return false;
	}
	
	// 打一群敌机的方法
	public void hitEnemyList(List<Enemy> enemyList) {
		
		// 将敌机集合的元素一个一个拿出来打
		for(Iterator<Enemy> it = enemyList.iterator();it.hasNext();) {
			
			Enemy  enemy = it.next();
			
			boolean b =hitEnemy(enemy);
			
			// 如果打中集合中的某个敌机,那么后面的敌机就不能再打了,因为子弹已经死了
			if(b) {
				return;
			}
		}
	}
		
}
