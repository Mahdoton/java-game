package com.uplooking.raiden;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;

/**
 * 游戏主面板类
 * 
 * @author Administrator
 *
 */
public class RaidenPanel extends JPanel {

	// 定义一个标识变量，true：开始游戏；false 没有开始游戏
	private boolean gameStart; // false

	// 声音播放器
	static AudioClip ac;
	// 背景音乐的URL
	static URL url;

	// 背景图片的URL
	static URL bjUrl;

	// 工具包类，能够创建图片
	static Toolkit tk = Toolkit.getDefaultToolkit();

	// 背景图片
	static Image bjImg;

	// 背景图片的y坐标
	int y = -1460;
	
	// 游戏结束文字的y坐标
	int gameOverY = 730;
	// 游戏结束变量：true 结束；false 没有结束
	boolean gameOver = false;

	// 创建一个英雄类的实例
	Hero hero = new Hero(200, 400);

	// 创建一个英雄的子弹
	// HeroMissile heroMissile = new HeroMissile(200, 300);
	// 英雄子弹的集合
	static ArrayList<HeroMissile> heroMissileList = new ArrayList<>();

	// 敌人的集合
	static ArrayList<Enemy> enemyList = new ArrayList<>();

	// 创建一个爆炸的集合
	static ArrayList<Explode> explodeList = new ArrayList<>();

	// 创建一个敌人的子弹的集合
	static ArrayList<EnemyMissile> enemyMissileList = new ArrayList<>();

	// 游戏积分的静态变量
	public static int score = 0;

	// 加血的集合
	static ArrayList<BloodBank> bloodList = new ArrayList<>();

	static {
		url = RaidenPanel.class.getClassLoader().getResource("Every Breath You Take.mid");

		ac = Applet.newAudioClip(url);

		// 背景图片URL
		bjUrl = RaidenPanel.class.getClassLoader().getResource("bj005.jpg");
		// 创建背景图片
		bjImg = tk.createImage(bjUrl);
	}

	// 构造方法，创建对象（实例）的时候，自动调用
	public RaidenPanel() {

		// 给面板添加鼠标监听器，匿名类，适配器
		this.addMouseMotionListener(new MouseAdapter() {

			// 鼠标移动时调用
			@Override
			public void mouseMoved(MouseEvent e) {
				// System.out.println("x = "+e.getX()+" y = "+e.getY());
				hero.move(e.getX() - 26, e.getY() - 30);
			}

			// 鼠标拖动（点击并移动）时调用
			@Override
			public void mouseDragged(MouseEvent e) {
				hero.move(e.getX() - 26, e.getY() - 30);
			}

		});

		// 鼠标点击事件监听器
		this.addMouseListener(new MouseAdapter() {

			// 鼠标按下事件处理
			@Override
			public void mousePressed(MouseEvent e) {
				// 点击鼠标，游戏开始
				if (!gameStart) {
					// 启动线程
					new RaidenThread().start();
					gameStart = true;
				}
				hero.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				hero.mouseReleased(e);
			}

		});

	}

	@Override
	public void paint(Graphics g) {
		// 判断敌人的集合中有没有敌人，如果没有敌人，加N多的敌人
		addEnemy();

		this.addBlood();
		
		// super.paint(g);
		// 先画出背景图片
		g.drawImage(bjImg, 0, y, 500, 730 * 3, this);

		// 红颜色的文字
		Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.setFont(new Font("宋体", Font.BOLD, 20));

		if (!gameStart) {
			g.drawString("你能过1000分吗？来挑战吧", 50, 200);

			g.drawString("点击鼠标开始游戏！", 80, 350);
		}
		
		// 英雄牺牲了，画出  “GAME OVER”  文字
		if(!hero.isLive()) {
			g.setFont(new Font("宋体", Font.BOLD,35));
			
			g.drawString("GAME OVER", 170,gameOverY);
			
			gameOverY -= 2;
			
			if(gameOverY < 300) {
				// 游戏结束
				gameOver = true;
			}
		}
		
		g.setFont(new Font("宋体", Font.BOLD,25));
		// 画出积分值
		g.drawString("积分:" + score, 10, 30);

		g.drawString("生命值:" + Hero.life, 380, 30);

		// 画出英雄的生命值的边框
		g.drawRect(450, 40, 20, 200);

		g.setColor(Color.YELLOW);

		// g.drawRect(452, 42, 16, 198);
		// 填充矩形
		g.fillRect(452, 42, 16, 198 * Hero.life / 100);

		/*
		 * g.drawString("这是我的雷电游戏", 200, 200);
		 * 
		 * g.drawString("集合中子弹的个数："+heroMissileList.size(), 200, 300);
		 * g.drawString("集合中敌人的个数："+enemyList.size(), 200, 100);
		 * 
		 * g.drawString("集合中爆炸的个数："+explodeList.size(), 200, 250);
		 */

		g.setColor(oldColor);
		// 画英雄
		hero.paint(g);

		// 画出集合中所有的英雄的子弹，
		this.paintHeroMissile(g);

		// 画敌人
		this.paintEnemy(g);

		// 画爆炸
		this.paintExplode(g);

		// 画敌人的子弹
		this.paintEnemyMissile(g);

		// 画加血集合
		this.paintBloodBankList(g);
	}

	// 添加敌人到集合中的方法
	private void addEnemy() {
		if (enemyList.isEmpty()) {
			int count = new Random().nextInt(10) + 10;

			for (int i = 0; i < count; i++) {
				int x = new Random().nextInt(500);
				int y = new Random().nextInt(300) - 400;
				int dir = new Random().nextInt(3);

				Enemy enemy = new Enemy(x, y, dir);
				enemyList.add(enemy);
			}
		}
	}
	
	// 添加加血的类的实例到集合中
	private void addBlood() {
		
		if(bloodList.isEmpty()) {
			
			int i = new Random().nextInt(5000);
			if(i > 4950) {
				int x = new Random().nextInt(450);
				
				BloodBank blood = new BloodBank(x,-100);
				
				bloodList.add(blood);
			}
			
			
		}
		
	}

	// 画出英雄的子弹的方法
	private void paintBloodBankList(Graphics g) {

		for (Iterator<BloodBank> it = bloodList.iterator(); it.hasNext();) {
			BloodBank bloodBank = it.next();
			// 
			if (bloodBank.isLive()) {
				bloodBank.paint(g);
				
				// 碰撞检测
				bloodBank.collideWithHero(hero);
				
			} else {
				// 移除
				it.remove();
			}
		}
	}

	// 画出英雄的子弹的方法
	private void paintHeroMissile(Graphics g) {

		for (Iterator<HeroMissile> it = heroMissileList.iterator(); it.hasNext();) {
			HeroMissile heroMissile = it.next();
			// 判断一下子弹是不是活的，如果是活的，就可以画；如歌子弹死了，就应该从集合中移除掉
			if (heroMissile.isLive()) {
				heroMissile.paint(g);
				// 打集合中的所有敌人
				heroMissile.hitEnemyList(enemyList);

				//
				heroMissile.hitEnemyMissileList(enemyMissileList);

			} else {
				// 移除子弹
				it.remove();
			}
		}
	}

	// 画出集合中的敌人方法
	private void paintEnemy(Graphics g) {

		for (Iterator<Enemy> it = enemyList.iterator(); it.hasNext();) {
			Enemy enemy = it.next();
			// 判断敌人是不是死了，如果是活的，就画出敌人；如果是死了，从集合中移除敌人
			if (enemy.isLive()) {
				enemy.paint(g);

				// 撞英雄
				enemy.collideWithHero(hero);

			} else {
				it.remove();
			}

		}
	}

	private void paintExplode(Graphics g) {
		for (Iterator<Explode> it = explodeList.iterator(); it.hasNext();) {
			Explode explode = it.next();
			if (explode.isLive()) {
				explode.paint(g);
			} else {
				it.remove();
			}
		}
	}

	private void paintEnemyMissile(Graphics g) {
		for (Iterator<EnemyMissile> it = enemyMissileList.iterator(); it.hasNext();) {
			EnemyMissile enemyMissile = it.next();

			if (enemyMissile.isLive()) {
				enemyMissile.paint(g);
				// 打英雄
				enemyMissile.hitHero(hero);
			} else {
				it.remove();
			}
		}
	}

	// 内部类，线程类
	class RaidenThread extends Thread {

		@Override
		public void run() {
			// 循环播放背景音乐
			ac.loop();

			// 不断地重画，内部类调用外部类方法
			// 死循环
			while (true) {
				if(gameOver) {
					// 游戏结束，停止背景音乐播放
					ac.stop();
					// 播放游戏结束的音乐
					// (作业)
					return;
					
				}
				// y++
				y += 2;
				if (y >= 0) {
					y = -1460;
				}
				RaidenPanel.this.repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
