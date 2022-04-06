package com.uplooking.raiden;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * 爆炸类
 * @author Administrator
 *
 */
public class Explode extends ActionComponent{
	
	private int index; // 0
	
	private int count = 4;
	
	// 定义常量
	public static final int EXPLODE_WIDTH = 100;
	
	public static final int EXPLODE_HEIGHT = 100;
	
	// 爆炸的图片
	static Image[] explodeImg = new Image[9];
	
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	// 声音播放器
	static AudioClip ac ;
	
	static {
		explodeImg[0] = tk.createImage(
				Explode.class.getClassLoader().getResource("b0.gif")
				);
		
		explodeImg[1] = tk.createImage(
				Explode.class.getClassLoader().getResource("b1.gif")
				);
		
		explodeImg[2] = tk.createImage(
				Explode.class.getClassLoader().getResource("b2.gif")
				);
		
		explodeImg[3] = tk.createImage(
				Explode.class.getClassLoader().getResource("b3.gif")
				);
		explodeImg[4] = tk.createImage(
				Explode.class.getClassLoader().getResource("b4.gif")
				);
		explodeImg[5] = tk.createImage(
				Explode.class.getClassLoader().getResource("b5.gif")
				);
		explodeImg[6] = tk.createImage(
				Explode.class.getClassLoader().getResource("b6.gif")
				);
		explodeImg[7] = tk.createImage(
				Explode.class.getClassLoader().getResource("b7.gif")
				);
		explodeImg[8] = tk.createImage(
				Explode.class.getClassLoader().getResource("b8.gif")
				);
		
	
		ac = Applet.newAudioClip(
				Explode.class.getClassLoader().getResource("missle.au")
				);
	}

	public Explode(int x,int y) {
		// 显示调用父类的构造方法，并且必须是代码的第一行
		super(x,y);
		
		// 播放一下爆炸声音
		new ExplodeThread().start();
	}
	
	// 画出爆炸的方法
	public void paint(Graphics g) {
		if(index == explodeImg.length) {
			// 一组图片都画完了，爆炸就结束了 
			this.setLive(false);
			return;
			
		}
		g.drawImage(explodeImg[index], this.getX(), this.getY(), 
				EXPLODE_WIDTH, EXPLODE_HEIGHT, null);
		
		count--; // 20 --> 0
		if(count == 0) {
			index++;
			count = 4;
		}
	}
	
	// 通过线程类来播放爆炸的声音，开发内部类
	class ExplodeThread extends Thread{

		@Override
		public void run() {
			// 播放爆炸声音
			ac.play();
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ac.stop();
		}
		
	}
	
	
}










