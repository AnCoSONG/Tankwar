package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class SelectPane extends JPanel {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1108761336724303677L;

	JButton button_return;
	
	JButton button_map_city;
	JButton button_map_sea;
	JButton button_custom_game;
	
	Font return_font = new Font("苹方 粗体", Font.PLAIN, 15);
	Font map_font = new Font("苹方 特粗", Font.PLAIN, 20);
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	public SelectPane() {
		// TODO Auto-generated constructor stub
		super(true);
		this.setLayout(null);
		this.setLookAndFeel();
		this.setBackground(Color.PINK);
		
		//new object
		button_return = new JButton("··返 回 开 始 界 面(ESC键返回)··");
		button_return.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red) );
		button_return.setFont(return_font);
		button_return.setBounds(0, 525, 600, 85);
		button_map_city = new JButton("·····夺旗模式·····");
		button_map_city.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue) );
		button_map_city.setFont(map_font);
		button_map_city.setBounds(0, 0, 600, 75);
		button_map_sea = new JButton("·····战斗模式·····");
		button_map_sea.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		button_map_sea.setFont(map_font);
		button_map_sea.setBounds(0, 250, 600, 75);
		button_custom_game = new JButton("自定义游戏");
		button_custom_game.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
		button_custom_game.setFont(map_font);
		button_custom_game.setBounds(420,400,200,75);
		//add image
		this.add(button_custom_game);
		this.add(button_map_city);
		this.add(button_map_sea);
		this.add(button_return);
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(tk.getImage("city.jpg"), 0, 75, null);
		g.drawImage(tk.getImage("sea.jpg"), 0, 325, null);
	}



	private void setLookAndFeel() {
		
		
		try {
			BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
