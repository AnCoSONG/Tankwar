package tankwar;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

public class TankFrame extends JFrame {
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 2834689951494434552L;
	static final int width = 650;
	static final int height = 690;
	
	Font font_menu = new Font("苹方 粗体", Font.PLAIN, 14);
	
	SetFrame sf;
	
	TankGamingPane pane2 = new TankGamingPane();
	TankStartPane pane0 = new TankStartPane();
	SelectPane pane1 = new SelectPane();
	CustomPanel pane3 = new CustomPanel();
	JPanel all;
	CardLayout cards = new CardLayout();
	//这些是坦克的图片链接 供转向时使用
	String tank_url_up = "img//p1tankU.gif";
	String tank_url_down = "img//p1tankD.gif";
	String tank_url_left = "img//p1tankL.gif";
	String tank_url_right = "img//p1tankR.gif";
	
	//敌方坦克的图片链接
	String enemy_url_left = "img//enemy1L.gif";
	String enemy_url_down = "img//enemy1D.gif";
	String enemy_url_right = "img//enemy1R.gif";
	String enemy_url_up = "img//enemy1U.gif";
	//map类属性
	static final int CITY_MAP = 0;
	static final int SEA_MAP = 1;
	
	//pause相关
	boolean pause = false;
	
	boolean tem = false;
	int temp = 0;
	/**
	 * 
	 * <p>Title: Tank大战界面类</p> 
	 * <p>Description: 画出界面以及刷新界面</p>
	 */
	public TankFrame() {
		// TODO Auto-generated constructor stub
		super("坦克大战 标准模式");
		setSize(width,height);
		setMediate(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		all = new JPanel(cards);
		all.add(pane0,"Card"+0);
		all.add(pane1, "Card"+1);
		all.add(pane2,"Card"+2);
		all.add(pane3,"Card"+3);
		
		/*
		 * 这之后是的大量代码 是添加监听器 以利用卡片布局
		 */
		Thread eventFocus = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				pane2.tank.addTankWinListener(new TankWinListener() {
					
					@Override
					public void win(WinEvent event) {
						// TODO Auto-generated method stub
						if(pane2.map.map_type == CITY_MAP) {
							pane2.initWholePane();
							cards.show(all, "Card"+0);
							pane0.requestFocus();
							JOptionPane.showMessageDialog(null, "You Win the Special Mode!", "Tips", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				
				pane2.enemyTank.addTankWinListener(new TankWinListener() {
					
					@Override
					public void win(WinEvent event) {
						// TODO Auto-generated method stub
						if(pane2.map.map_type == CITY_MAP) {
							pane2.initWholePane();
							cards.show(all, "Card"+0);
							pane0.requestFocus();
							JOptionPane.showMessageDialog(null, "Enemy Tank Win the Special Mode!\nYou Lose!", "Tips", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				
				pane2.enemyTank.addTankLiveListener(new TankLiveListener() {
					
					@Override
					public void dead(Even even) {
						// TODO Auto-generated method stub
						if(pane2.map.map_type == SEA_MAP) {
							pane2.initWholePane();
							cards.show(all, "Card"+0);
							pane0.requestFocus();
							JOptionPane.showMessageDialog(getContentPane(), "You Win", "TIPS", JOptionPane.INFORMATION_MESSAGE);
						}else if (pane2.map.map_type == CITY_MAP) {
							pane2.enemyTank.initEnemyTank();
						}
						
					}
				});
				pane2.tank.addTankLiveListener(new TankLiveListener() {
					
					@Override
					public void dead(Even even) {
						// TODO Auto-generated method stub
						pane2.initWholePane();
						cards.show(all, "Card"+0);
						pane0.requestFocus();
						JOptionPane.showMessageDialog(getContentPane(), "Game Over", "TIPS", JOptionPane.INFORMATION_MESSAGE);
						
					}
				});
				
				
				pane2.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						switch(e.getKeyCode()) {
						case KeyEvent.VK_J:
							pane2.tank.fire();
							break;
						case KeyEvent.VK_ESCAPE:
							cards.show(all, "Card"+1);
							pane2.initWholePane();
							pane1.requestFocus();
							break;
						case KeyEvent.VK_UP:
							pane2.tank.setImageIcon(tank_url_up);//这一步设置转向后的图片
							pane2.tank.move(Direction.UP,pane2.base.bsList);//这一步改变方向
							break;
						case KeyEvent.VK_DOWN:
							pane2.tank.setImageIcon(tank_url_down);
							pane2.tank.move(Direction.DOWN,pane2.base.bsList);
							break;
						case KeyEvent.VK_RIGHT:
							pane2.tank.setImageIcon(tank_url_right);
							pane2.tank.move(Direction.RIGHT,pane2.base.bsList);
							break;
						case KeyEvent.VK_LEFT:
							pane2.tank.setImageIcon(tank_url_left);
							pane2.tank.move(Direction.LEFT,pane2.base.bsList);
							break;
						case 107://加号
							//System.out.println("加子弹方法调用了");
							pane2.tank.addBullet();
							break;
						case 109://减号
							pane2.tank.reduceBullet();
							break;
						case KeyEvent.VK_SPACE:
							if(!pause) {
								pause = true;
								System.out.println("已暂停");
							}else {
								pause = false;
								System.out.println("已继续");
							}
						}
						
					}
				});
				
				pane1.button_map_sea.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						cards.show(all,"Card"+2);
						pane2.map.setMapType(SEA_MAP);
						pane2.tank.setMapType(SEA_MAP);
						pane2.enemyTank.setMapType(SEA_MAP);
						pane2.tools.setMapType(SEA_MAP);
						pane2.tools.randomXY();
//						pane2.base.setMapType(SEA_MAP);
//						pane2.base.setXY();
						//pane2.tank.bullet.setMapType(SEA_MAP);
						pane2.requestFocus();
					}
				});
				pane0.about.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JOptionPane.showMessageDialog(all, "制作 BY : AnCo 宋晋瑜\n 版本 : 1.5\n打包日期 : 06/09/2018\nJDK\u2122版本 : 1.8.0_162\n支付宝账号 : 18703412417\n  ··欢迎捐赠··","关于",
								JOptionPane.INFORMATION_MESSAGE);
					}
				});
				pane1.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							cards.show(all, "Card"+0);
							pane0.requestFocus();
							
						}
					}
				});
				pane1.button_custom_game.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//切换到customgame
						cards.show(all,"Card"+3);
						setTitle("坦克大战 自定义模式");
						pane3.selectPanel.requestFocus();
					}
				});

				pane3.selectPanel.backToStandard.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cards.show(all,"Card"+1);
						setTitle("坦克大战 标准模式");
						pane1.requestFocus();
					}
				});
				pane1.button_map_city.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						cards.show(all, "Card"+2);
						pane2.map.setMapType(CITY_MAP);
						pane2.tank.setMapType(CITY_MAP);
						pane2.enemyTank.setMapType(CITY_MAP);
						//pane2.tank.bullet.setMapType(CITY_MAP);
						//pane2.enemyTank.bullet.setMapType(CITY_MAP);
						pane2.tools.setMapType(CITY_MAP);
						pane2.tools.randomXY();
						pane2.requestFocus();
					}
				});
				pane1.button_return.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						cards.show(all, "Card"+0);
						pane0.requestFocus();
					}
				});
				
				pane0.edition.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						String temp = JOptionPane.showInputDialog(null,"输入当前版本号","内部窗口",JOptionPane.INFORMATION_MESSAGE);
						pane0.edition.setText(pane0.edition_name+temp);
					}
				});
				pane0.help.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						JOptionPane.showMessageDialog(null, "··操作说明··\n1.按上下左右移动\n2.按J攻击\n3.游戏界面点击空格暂停，再次点击继续\n4.标准模式下有道具，只有两种，自定义模式下没有道具\n(关于自定义模式的帮助请在自定义模式下进行)\n5.详情请看Readme文件", "操作说明", JOptionPane.QUESTION_MESSAGE);
					}
				});
				pane0.end_game.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						System.exit(0);
					}
				});
				
				pane0.set.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						sf = new SetFrame();
						sf.difficulty_box.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								// TODO Auto-generated method stub
								int choice = sf.difficulty_box.getSelectedIndex();
								if(choice > 0 ) {
									String temp = sf.difficulties[choice];
									pane0.hard.setText("难度 ：" + temp);
									switch (temp) {
									case "菜鸡" : pane2.enemyTank.setDifficulty(Difficulty.Fool); break;
									case "简单":pane2.enemyTank.setDifficulty(Difficulty.Easy); break;
									case "一般": pane2.enemyTank.setDifficulty(Difficulty.Normal);break;
									case "有点难": pane2.enemyTank.setDifficulty(Difficulty.Hard);break;
									case "地狱": pane2.enemyTank.setDifficulty(Difficulty.Hell); break;
									case "不可能赢" : pane2.enemyTank.setDifficulty(Difficulty.MustLose); break;
									}
								}
							}
						});
					}
				});
				
				pane0.start.addMouseListener(new MouseListener() { //单击画面即可切换面板 下一步要将这个监听器附加到按钮 “开始” “帮助” “操作说明” 
					//“结束” "设置"  等等按钮
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) { 
						// TODO Auto-generated method stub

						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						cards.show(all, "Card"+1);
						pane1.requestFocus();
					}
				});
				
			}
			
			//监听器 结束！
		});
		add(all);
		setVisible(true);
		eventFocus.start();
		
	}

	public static void main(String[] args) {
		BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;
		
		try {
			UIManager.put("RootPane.setupButtonVisible",false);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TankFrame tf = new TankFrame();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
						if(!tf.pause) {
							if(!tf.pane3.gamingPanel.pause) {
								tf.repaint();
							}
						}
					try {
						Thread.sleep(SleepTime.TIME);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
	private void setMediate(JFrame frame) {
		 int windowWidth = frame.getWidth(); //获得窗口宽
		 int windowHeight = frame.getHeight(); //获得窗口高
		 Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		 Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		 int screenWidth = screenSize.width; //获取屏幕的宽
		 int screenHeight = screenSize.height; //获取屏幕的高
		 frame.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
	}
}

class SetFrame extends JFrame{
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -2842312826785462133L;
	ImageIcon icon = new ImageIcon("set2.png");
	Font font = new Font("苹方 粗体", Font.PLAIN, 18);
	
	Difficulty difficulty;
	
	JPanel pane = new JPanel(null);
	
	String[] difficulties = {"选择一个难度","菜鸡","简单","一般","有点难","地狱","不可能赢"};
	
	JComboBox<String> difficulty_box;
	JLabel label = new JLabel("·····设置    Setting·····");
	JLabel label0 = new JLabel("难度 :");
	
	public SetFrame() {
		// TODO Auto-generated constructor stub
		
		super("设置");
		setLookAndFeel();
		setSize(400, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		difficulty_box = new JComboBox<String>(difficulties);
		difficulty_box.setBounds(100, 50, 200, 50);
		difficulty_box.setFont(font);
		label.setFont(font);
		label.setBounds(70, 0, 400, 50);
		label0.setFont(font);
		label0.setBounds(30, 50, 50, 50);
		pane.setBackground(new Color(250,235,215));
		pane.add(label);
		pane.add(difficulty_box);
		pane.add(label0);
		this.add(pane);
		setVisible(true);
	}
	
	public void setLookAndFeel() {
		BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;
		try {
			UIManager.put("RootPane.setupButtonVisible",false);
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


