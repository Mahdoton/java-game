package com.uplooking.raiden;

import java.awt.Rectangle;

/**
 * 父类
 * @author Administrator
 *
 */
public class ActionComponent {

	private int x;
	
	private int y;
	
	private boolean live = true;
	
	// 构造方法是不能被继承的
	public ActionComponent(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	// 获取所在区域的方法
	public Rectangle getRect(int w, int h) {
		return new Rectangle(x,y,w,h);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	
}
