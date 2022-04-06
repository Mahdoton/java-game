package com.fw.raiden;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
public class RaidenGameMain extends JFrame {
	/**
	 * 我们游戏的主界面
	 * @author DELL
	 */
	private static final long serialVersionUID = 1L;
	//构造方法,当创建类的对象的时候，也就是new的时候自动调用
	public RaidenGameMain() {
		
		// 设置显示的位置,设置窗口的坐标: x y
		this.setLocation(550,10);
		
		// 设置窗口的大小: 宽 高
		this.setSize(800,1000);
		
		// 设置窗口关闭程序
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 设置窗口的标题
		this.setTitle("咕咕的压箱战机");
		
		// 设置游戏窗口不允许调整大小
		this.setResizable(false);
		
		// 设置游戏内部为十字光标
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		// 将Panel放在主界面中
		this.setContentPane(new RaidenGamePanel());
		
		// 为游戏窗口添加鼠标移动监听器,处理鼠标移动的操作
		this.addMouseMotionListener(new MouseMotionListener() {
			
			// 创建鼠标移动事件
			public void mouseMoved(MouseEvent e) {
				RaidenGamePanel.myHero.mouseMoved(e);
			}
			
			// 创建鼠标拖拽事件
			public void mouseDragged(MouseEvent e) {
				RaidenGamePanel.myHero.mouseMoved(e);
			}
			
		 });
		
		// 为游戏窗口添加添加鼠标监听器,监测鼠标的按下与松开状况
		// this指向本类实例对象
		this.addMouseListener(new MouseAdapter() {
			// 关于mousePressed方法的具体实现可以参考word文档
			@Override
			// 绑定鼠标按下的事件
			public void mousePressed(MouseEvent e) {
				// myHero是静态变量  可以全局访问
				// 将开火标志置为真
				Hero.fireFlag = true;
			
			}
			@Override
			// 绑定鼠标释放的事件
			public void mouseReleased(MouseEvent e) {

				// 将开火标志置为假
				Hero.fireFlag = false;
				
			}
		});
		
		// 设置键盘监听器
		this.addKeyListener(new KeyAdapter() {
			@Override
			// 绑定键盘按下事件 
			public void keyPressed(KeyEvent e) {
				// 当S键被按下时触发超级火力模式
				if(e.getKeyCode() == KeyEvent.VK_S) {
					// 调用超级火力
					RaidenGamePanel.myHero.superFire();
				}
			}
		});
		
		// 设置窗口的可见性,默认为不可见的（一定要在主界面类的构造函数的末尾再设置！！！！！！！！）
		this.setVisible(true);
	}
		
	//输入main，使用快捷键Alt + / ,选择main method
	public static void main(String[] args) {
		new RaidenGameMain();
	}
		
}
