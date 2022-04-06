package com.fw.raiden;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

/**
 *游戏内容面板
 *@author DELL 
 */
@SuppressWarnings("deprecation")
public class RaidenGamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// 构造方法,用于调用线程
	public RaidenGamePanel() {
		// 启动线程
		MyGameThread my = new MyGameThread();
		my.start();	
		
	}
	
	// 定义一个变量,表示的是背景图片的 y 坐标, y代表面板顶端到图片顶端的距离
	int y= -2400; // 说明面板顶端到图片顶端的距离是-2400,也就是说图片顶端在面板顶端的上面,所以面板里的内容实际上是图片中间部分的内容
		
	// 游戏結束,文字显示的起始位置
	int gameOverStrY = 1000;
	
	// 游戏結束的变量
	boolean gameFlag = false;
	
	// 定义和加载游戏背景图片
	static Image bjImg;
	
	// 通过系统的工具包类,来完成图片的加载和创建
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	
	// 加载并播放背景音乐
	static AudioClip ac;
	
	// 静态块
	static {
		// 加载音乐
		ac = Applet.newAudioClip(RaidenGamePanel.class.getClassLoader().getResource("Every Breath You Take.mid"));
		
		// 加载背景图片
			bjImg = tk.createImage(RaidenGamePanel.class.getClassLoader().getResource("bj002.jpg"));
	}
	
	// 创建战机对象
	static Hero myHero = new Hero(300,700);
	
	// 创建战机的子弹集合
	static List<HeroMissile> herMissileList = new ArrayList<>();
	
	// 创建敌机集合
	static List<Enemy> enemyList = new ArrayList<>();
	
	// 创建爆炸集合
	static List<Explode> explodeList = new ArrayList<>();

	// 创建敌机子弹集合
	static List<EnemyMissile> enemyMissileList = new ArrayList<>();
	
	@Override
	public void paint(Graphics g) {
		
		// 给敌机增加数量
		if(enemyList.isEmpty()) {
			// 创建一个随机数  代表随机产生的战机的数量
			int n = new Random().nextInt(20)+10;
			// 遍历循环 逐个将敌机加入到集合中
			for(int i =0;i<n;i++) {
				// 随机创建每架敌机的初始横纵坐标
				int x = new Random().nextInt(658);
				int y = new Random().nextInt(400)-400;
				// 随机创建敌机对象
				Enemy en = new Enemy(x,y);
				// 将敌机对象加入到集合中
				enemyList.add(en);
			}
		}
		
		// 画出背景图 
		g.drawImage(bjImg, 0, y, 800, 1200*3, this);
		
		// 画英雄战机
		myHero.paint(g);
		
		// 画出英雄战机的子弹
		// 遍历集合,逐个画出英雄战机的子弹
		for(Iterator<HeroMissile> it = herMissileList.iterator();it.hasNext();) {
			// 英雄战机子弹恒等于下一个英雄战机子弹集合中的下一个元素
			HeroMissile missile = it.next();
			// 如果英雄战机子弹的存活状态为真  那么就画出英雄战机子弹
			if(missile.live ) {
				missile.paint(g);
				// 调用击打敌机的方法
				missile.hitEnemyList(enemyList);
			}else {
			// 如果英雄战机子弹的存活状态为假  那么就从集合中移除掉该子弹
				it.remove();
			}
		}
		
		// 画出敌机
		for(Iterator<Enemy> it = enemyList.iterator();it.hasNext();) {
			// 敌机对象恒等于下一个敌机集合中的下一个元素
			Enemy enemy = it.next();
			// 如果敌机的存活状态为真  那么画出敌机
			if(enemy.lives ) {
				enemy.paint(g);
			// 如果敌机的存活状态为假  那就从集合中移除掉该敌机
			}else {
				it.remove();
			}
		}

		// 画出爆炸
		for(Iterator<Explode> it = explodeList.iterator();it.hasNext();) {
			// 爆炸对象恒等于下一个敌机集合中的下一个元素
			Explode explode = it.next();
			// 如果爆炸的存活状态为真  那么画出爆炸
			if(explode.live) {
				explode.paint(g);
			// 如果爆炸的存活状态为假  那就从集合中移除掉该爆炸
			}else {
				it.remove();
			}
		}
		
		// 画出敌机子弹
		for(Iterator<EnemyMissile> it = enemyMissileList.iterator();it.hasNext();) {
			// 敌机子弹对象恒等于下一个敌机子弹集合中的下一个元素
			EnemyMissile enemyMissile = it.next();
			// 如果敌机子弹的存活状态为真  那么画出敌机子弹
			if(enemyMissile.live) {
				enemyMissile.paint(g);
				// 调用打英雄战机的方法
				enemyMissile.hitHero(myHero);
			// 如果敌机的存活状态为假  那就从集合中移除掉该敌机
			}else {
				it.remove();
			}
		}	
			
		// 画出一段文字,显示子弹集合中的元素数量
		// 设置颜色
		g.setColor(Color.BLACK);
		// 设置字体
		g.setFont(new Font("宋体", Font.BOLD , 30));
		g.drawString("战机子弹的总数量是："+ herMissileList.size(), 20, 50);
		// 画出一段文字,显示// 画出英雄战机的生命值
		g.drawString("英雄的生命值："+ myHero.life, 500, 50);
		
		// 画出一段文字,显示游戏结束界面
		if(!myHero.live) {
			// 设置颜色
			g.setColor(Color.RED);
			// 设置字体
			g.setFont(new Font("宋体", Font.BOLD , 60));
			// 将文字画出
			g.drawString("GAME OVER", 250, gameOverStrY);
			// 设置初始文字位置
			gameOverStrY -= 5;
			// 当文字位置距离主界面顶端为500单位时  游戏结束的标志为真
			if(gameOverStrY <= 500) {
				gameFlag = true;
			}
		}	
}
	
		// 开发一个线程类,用来不断增加Y坐标的值，是一个内部类
		class MyGameThread extends Thread{
			public void run() {
				//播放背景音乐
				ac.loop();
				
				while(true){
					// 如果游戏结束标志为真,停止线程
					if(gameFlag) {
						// 停止播放音乐
						ac.stop();
						// 停止线程
						return;
					}
					
					// 滚动背景图片
					y += 3; // 如果要缩短面板顶端至图片顶端的距离,需要加正数,才能使面板顶端至图片顶端的距离逐渐缩小
					
					// 重新调用paint方法
					RaidenGamePanel.this.repaint();
					
					// 当图片顶端到达主界面顶端时 重置背景图片顶端至主界面顶端的距离  这就是每游戏运行一段时间后页面会闪一下的原因
					if(y >= 0){
						y = -2400;
					}
					try {
						// 休眠30毫秒 然后继续画出所有元素
						sleep(30); // 30毫秒   1秒=1000毫秒
					} catch (InterruptedException e) {
						// 捕获异常并打印栈堆信息
						e.printStackTrace();
					}
					
				}
			}
		}
		
	
}
