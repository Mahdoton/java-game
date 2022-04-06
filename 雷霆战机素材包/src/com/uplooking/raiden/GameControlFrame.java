package com.uplooking.raiden;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameControlFrame extends JFrame{

	public GameControlFrame() {
		
		
		JButton exitBtn = new JButton("退出游戏");
		//
		exitBtn.setLocation(80, 260);
		exitBtn.setSize(150,30);
		// 设置按钮的背景色
		exitBtn.setBackground(Color.BLACK);
		
		// 设置按钮的前景色
		exitBtn.setForeground(Color.WHITE);
		
		// 点击"退出按钮"按钮，结束游戏
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		this.setLocation(500, 350);
		
		this.setSize(300,370);
		
		// 设置为无边框
		this.setUndecorated(true);
		
		// 获取JFrame的内容面板
		Container contentPane = this.getContentPane();
		// 设置内容面板的背景色
		contentPane.setBackground(new Color(20,20,20));
		// 设置为手动布局
		contentPane.setLayout(null);
		
		contentPane.add(exitBtn);
		
		
		// 最初的时候，是不可见的
		this.setVisible(false);
	}
}








