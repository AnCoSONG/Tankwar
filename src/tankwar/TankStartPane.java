package tankwar;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import sun.audio.AudioPlayer;

public class TankStartPane extends JPanel {
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 6605720834070265367L;

	ImageIcon icon = new ImageIcon("set2.png");
	
	Font font_button = new Font("苹方 粗体", Font.PLAIN, 16);
	Font font_diff = new Font("苹方 常规", Font.PLAIN, 15);
	JButton start = new JButton("··开始··");
	JButton about = new JButton("··关于··");
	JButton help = new JButton("··帮助··");
	JButton end_game = new JButton("··结束游戏··");
	JLabel set = new JLabel(icon);
	JLabel hard = new JLabel("难度 : 默认");
	
	String edition_name = "版本号:";
	String edition_number = "1.2";
	JLabel edition = new JLabel(edition_name+edition_number);
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	Tank tank = new Tank(60,285,Direction.RIGHT,0);

	public TankStartPane() {
		// TODO Auto-generated constructor stub
		super(true);
		this.setLayout(null);
		this.setLookAndFeel();
		this.setBackground(Color.PINK); //将来用图片取代
		this.start.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
		this.start.setForeground(Color.WHITE);
		//this.start.setBackground(Color.CYAN);
		this.start.setFont(font_button);
		this.about.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
		this.about.setForeground(Color.WHITE);
		//this.about.setBackground(Color.CYAN);
		this.about.setFont(font_button);
		this.help.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
		this.help.setForeground(Color.WHITE);
		//this.help.setBackground(Color.CYAN);
		this.help.setFont(font_button);
		this.end_game.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		this.end_game.setForeground(Color.WHITE);
		this.hard.setForeground(Color.BLUE);
		this.hard.setFont(font_diff);
		this.hard.setBounds(470, 500, 150, 100);
		this.set.setBounds(545, 0, 50, 50);
		this.edition.setFont(font_button);
		
		this.edition.setForeground(Color.WHITE);
		this.edition.setBounds(30,0,100,30);
		//this.setForeground(Color.WHITE);
		//this.end_game.setBackground(Color.CYAN);
		this.end_game.setFont(font_button);
		this.start.setBounds(80, 350, 200, 70);
		this.about.setBounds(330, 350, 200, 70);
		this.help.setBounds(80, 450, 200, 70);
		this.end_game.setBounds(330, 450, 200, 70);
		FileInputStream in = null;
		try {
			in = new FileInputStream("img//start.wav");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//FIlename 是你加载的声音文件如(“game.wav”) 

		// 从输入流中创建一个AudioStream对象 

		AudioPlayer.player.start(in);
		
		//AudioPlayer.player.stop(as);//关闭音乐播放 
		this.add(start);
		this.add(about);
		this.add(help);
		this.add(end_game);
		this.add(set);
		this.add(hard);
		this.add(edition);
		
		tank.setImageIcon(tank.tank_url_right);
		
		this.setVisible(true);
	}
	
	

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(tk.getImage("timg.jpg"), 0, 0, null);
		g.drawImage(tk.getImage("logo1.png"), -50, 0, null);
		//g.drawImage(tk.getImage("set2.png"), 545, 0, null);
		System.out.println(tank.getPosition());
		g.drawImage(tank.tank_icon.getImage(), tank.position_x, tank.position_y, null);
		tank.moveInNoGamePanel();
		if(tank.position_x>=530&&tank.getPosition().y<=285) {
			tank.setDir(Direction.DOWN);
			tank.setImageIcon(tank.tank_url_down);
		}
		if(tank.position_x>=530&&tank.getPosition().y>=530) {
			tank.setDir(Direction.LEFT);
			tank.setImageIcon(tank.tank_url_left);
		}
		if(tank.getPosition().x<=10&&tank.getPosition().y>=530) {
			tank.setDir(Direction.UP);
			tank.setImageIcon(tank.tank_url_up);
		}
		if(tank.getPosition().x<=10&&tank.getPosition().y<=285) {
			tank.setDir(Direction.RIGHT);
			tank.setImageIcon(tank.tank_url_right);
		}
		
		
	}
	
	private void setLookAndFeel() {
		// TODO Auto-generated method stub
		try {
			BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
