package com.uplooking.raiden;

import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/**
 * 功能： 游戏结束显示"GAME OVER"功能
 * 
 *  OOP --> 定义类：属性（实例变量），类变量，常量，构造方法，方法
 *  类的继承、接口的实现、内部类、匿名类
 *  集合的应用、
 *  线程的应用：创建、启动、终止、休眠
 *  图形、图片、声音、颜色、字体、按钮
 *  
 * 
 * @author Administrator
 *
 */
public class RaidenGameMain extends JFrame {							

	GameControlFrame controlFrame = new GameControlFrame();
	
	
	
	// 构造方法
	public RaidenGameMain() {

		init();
		// this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		RaidenPanel raidenPanel = new RaidenPanel();

		this.setContentPane(raidenPanel);

		// 添加键盘监听器，匿名类的使用，适配器的使用
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				// 按F键，英雄王者归来
				if (e.getKeyCode() == KeyEvent.VK_F) {
					Hero.life = 100;
					raidenPanel.hero.setLive(true);
				}
				
				// 按esc键，显示一个游戏控制窗口
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					// 可见
					controlFrame.setVisible(true);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_1){
					Hero.index = 0;
				}
				if(e.getKeyCode() == KeyEvent.VK_2){
					Hero.index = 1;
				}
				if(e.getKeyCode() == KeyEvent.VK_3){
					Hero.index = 2;
				}
				
				if(e.getKeyCode() == KeyEvent.VK_F1){
					HeroMissile.index = 0;
				}
				if(e.getKeyCode() == KeyEvent.VK_F2){
					HeroMissile.index = 1;
				}
				if(e.getKeyCode() == KeyEvent.VK_F3){
					HeroMissile.index = 2;
				}
				if(e.getKeyCode() == KeyEvent.VK_F4){
					HeroMissile.index = 3;
				}
				if(e.getKeyCode() == KeyEvent.VK_F5){
					HeroMissile.index = 4;
				}
				if(e.getKeyCode() == KeyEvent.VK_F6){
					HeroMissile.index = 5;
				}
				if(e.getKeyCode() == KeyEvent.VK_F7){
					HeroMissile.index = 6;
				}
				if(e.getKeyCode() == KeyEvent.VK_F8){
					HeroMissile.index = 7;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// 按a键，打出十个子弹
				if(e.getKeyCode() == KeyEvent.VK_A) {
					raidenPanel.hero.superFire();
				}
			}
			
		});

		this.setVisible(true);
	}

	public void init() {
		this.setLocation(400, 0);

		this.setSize(500, 730);

		this.setTitle("雷电游戏-尚观科技2020");
		// 设置不允许调整界面大小
		this.setResizable(false);
		
		// 设置无边框
		this.setUndecorated(true);

		// 设置默认关闭操作
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 设置光标类型
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
	}

	public static void main(String[] args) {
		RaidenGameMain rm = new RaidenGameMain();
	}
}









